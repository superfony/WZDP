/*
 * @version 1.0
 * @Date 2011-11-29
 * @author Anders.Zhang
 * Copyright Shandong Easecom Sci&Trading Co.,Ltd.
 */

// ~ Package Information
// ============================================================================

package com.epsmart.wzcc.activity.pagination;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.AppContext;
import com.epsmart.wzcc.activity.RequestParamConfig;
import com.epsmart.wzcc.activity.adapter.CommonAdapter;
import com.epsmart.wzcc.activity.supply.bean.Field;
import com.epsmart.wzcc.bean.ViewCreator;
import com.epsmart.wzcc.bean.WorkOrder;
import com.epsmart.wzcc.bean.WorkOrderResponse;
import com.epsmart.wzcc.common.StringUtils;
import com.epsmart.wzcc.db.DatabaseHelper;
import com.epsmart.wzcc.db.table.AppHeadTable;
import com.epsmart.wzcc.db.table.LeaveHeadTable;
import com.epsmart.wzcc.http.BaseHttpModule;
import com.epsmart.wzcc.http.BaseHttpModule.RequestListener;
import com.epsmart.wzcc.http.ModuleResponseProcessor;
import com.epsmart.wzcc.http.request.BaseRequest.RequestType;
import com.epsmart.wzcc.http.request.HttpException;
import com.epsmart.wzcc.http.request.RequestAction;
import com.epsmart.wzcc.http.request.model.PageBean;
import com.epsmart.wzcc.http.response.model.PagerResponse;
import com.epsmart.wzcc.http.response.model.Response;
import com.epsmart.wzcc.http.response.model.StatusEntity;
import com.epsmart.wzcc.http.xml.handler.BaseParserHandler;
import com.epsmart.wzcc.http.xml.parser.BaseXmlParser;
import com.epsmart.wzcc.widget.PullToRefreshListView;
import com.epsmart.wzcc.widget.PullToRefreshListView.OnPositionChangedListener;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 分页控件
 *
 * @param <T>
 * @author fony
 */
public class PaginationWidget<T> {

    private static final String TAG = "PaginationWidget";
    /**
     * 每页的数据量在SharedPreferences中的Key
     */
    public static final String PAGESIZE_KEY = "pageSize";
    public static final String DATA_KEY = "pagination";


    /**
     * 分页控件所在Activity
     */
    protected Context context;
    protected View paginationView;
    /**
     * 表格数据域部分
     */
    public PullToRefreshListView lv_page_body;

    protected View footView;

    protected ProgressBar lvNews_foot_progress;
    protected TextView lvNews_foot_more;

    public static int action;

    private ArrayList<T> lvList = new ArrayList<T>();
    /**
     * 表格数据域适配器
     */
    public CommonAdapter<T> tableBodyAdapter;
    protected ViewCreator<T> viewCreator;
    /**
     * 分页按钮,首页,前一页,下一页,末页
     */
    protected Button btn_firstPage, btn_prevPage, btn_nextPage, btn_lastPage;
    /**
     * 当前页,总页数,已查看记录数,总记录数
     */
    protected TextView txt_currentPage, txt_pageCount, txt_page_allCount;
    public RequestAction requestAction = null;
    /**
     * 数据加载进度提示
     */
    protected ProgressDialog progressDialog;
    /**
     * 表格数据域列表项点击事件处理监听器
     */
    protected OnItemClickListener pageBodyOnItemClickListener;
    /**
     * 访问的webservice服务名称
     */
    protected String serviceName;
    protected BaseHttpModule httpModule = null;
    protected OnItemLongClick onLongClick;

    protected String different;

    public String getDifferent() {
        return different;
    }

    public void setDifferent(String different) {
        this.different = different;
    }
    /**
     * 网络请求返回的结果
     */
    protected RequestListener requestListener = new RequestListener() {

        @Override
        public void onSuccess(Response response) {
            httpModule.processResponse(httpModule, response,
                    getXmlParser(response), httpModule.getResponseProcess());
        }

        @Override
        public void onFail(Exception e) {
            if (null != progressDialog && progressDialog.isShowing())
                progressDialog.cancel();

            String msg = "未知错误";
            if (e instanceof HttpException) {
                HttpException he = (HttpException) e;
                msg = he.getMessage();
            }

            Message obtainMessage = mHandler.obtainMessage(2);
            obtainMessage.obj = msg;
            obtainMessage.sendToTarget();
        }
    };

    public interface DataObserver {
        /**
         * @param key      fragment的key
         * @param outState
         */
        void restoreInstanceState(String key, Bundle outState);
    }

    protected BaseParserHandler parseHandler;
    protected RequestType requestType;
    protected DataObserver dataObserver;

    class ProcessResponse implements ModuleResponseProcessor {

        @Override
        public void processResponse(BaseHttpModule httpModule, Object parseObj) {

            if (parseObj instanceof PagerResponse) {
                mHandler.obtainMessage(0, parseObj).sendToTarget();
            } else if (parseObj instanceof StatusEntity) {
                mHandler.obtainMessage(1, parseObj).sendToTarget();
            }
        }
    }

    public BaseXmlParser getXmlParser(Response response) {
        return httpModule.getBaseXmlParser(response,
                httpModule.getParseHandler());
    }

    private PaginationHandler mHandler = new PaginationHandler(
            PaginationWidget.this);

    private static class PaginationHandler extends Handler {
        @SuppressWarnings("rawtypes")
        WeakReference<PaginationWidget> mPagination;

        @SuppressWarnings("rawtypes")
        PaginationHandler(PaginationWidget widget) {
            mPagination = new WeakReference<PaginationWidget>(widget);
        }

        @SuppressWarnings("rawtypes")
        @Override
        public void handleMessage(Message msg) {

            PaginationWidget widget = mPagination.get();
            if (null == widget)
                return;
            if (msg.what == 0) {
                widget.success(msg.obj);
            } else if (msg.what == 1) {
                widget.fail(msg.obj);
            } else if (msg.what == 2) {
                widget.failure((String) msg.obj);
            }
        }

    }

    public void failure(String msg) {
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    public void success(Object object) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
        PagerResponse response = (PagerResponse) object;
        if (null == requestAction.pageBean) {
            return;
        }

        int allCount = response.pageBean.getAllCount();

        requestAction.pageBean.setAllCount(allCount);
        int count = 0;

        /** 没有记录或记录为空的时候 */
        if (response.pageBean.getPageDatas() != null) {
            count = ((List<T>) response.pageBean.getPageDatas()).size();
        }
        Log.w("count", "count="+count );
        if (action == UIHelper.LISTVIEW_ACTION_REFRESH) {
            tableBodyAdapter.removeAll();
        }
        if (count == 0 || allCount == 0) {
            Toast tost = Toast.makeText(context, "没有更多记录！", Toast.LENGTH_LONG);
            tost.setGravity(Gravity.CENTER, 0, 0);
            tost.show();

            lv_page_body.setTag(UIHelper.LISTVIEW_DATA_EMPTY);
            lvNews_foot_more.setText(R.string.load_empty);

        }
        if (count < RequestParamConfig.pagesize && count > -1||(requestAction.pageBean.getCurrentPage()== requestAction.pageBean.getPageCount())&&count>-1) {// 末页返还
            lv_page_body.setTag(UIHelper.LISTVIEW_DATA_FULL);
            tableBodyAdapter.add((List<T>) response.pageBean.getPageDatas());

            tableBodyAdapter.notifyDataSetChanged();
            lvNews_foot_more.setText(R.string.load_full);// 已加载全部
        } else if (count == RequestParamConfig.pagesize) {
            lv_page_body.setTag(UIHelper.LISTVIEW_DATA_MORE);
            tableBodyAdapter.add((List<T>) response.pageBean.getPageDatas());
            tableBodyAdapter.notifyDataSetChanged();
            lvNews_foot_more.setText(R.string.load_more);
        }
        lvNews_foot_progress.setVisibility(ProgressBar.GONE);

        if (action == UIHelper.LISTVIEW_ACTION_REFRESH) {
            lv_page_body.onRefreshComplete("最近更新："
                    + new Date().toLocaleString());
            lv_page_body.setSelection(0);
        } else if (action == UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG) {
            lv_page_body.onRefreshComplete();
            lv_page_body.setSelection(0);
        }

    }

    private void fail(Object object) {
        String msg = ((StatusEntity) object).message;
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        lvNews_foot_more.setText(msg);
        lvNews_foot_progress.setVisibility(View.GONE);
    }

    @SuppressWarnings("unused")
    private void hideProgress() {
        if (null != progressDialog && progressDialog.isShowing())
            progressDialog.cancel();
    }

    public PaginationWidget() {

    }

    /**
     * 网络请求初始化
     *
     * @param context
     * @param container
     * @param viewCreator
     * @return
     */
    public PaginationWidget<T> init(Context context, View container,
                                    ViewCreator<T> viewCreator) {
        this.context = context;
        this.paginationView = container;
        this.viewCreator = viewCreator;

        if (null == requestAction) {
            requestAction = new RequestAction();
            requestAction.isPageBeanEnable = true;
        }

        if (null == httpModule) {
            httpModule = new BaseHttpModule(context);
            httpModule.init();
            httpModule.setRequestListener(requestListener);
        }
        setUpViews();
        bindEvents();
        initData();
        return this;
    }


    public PaginationWidget<T> initArray(Context context, View container,
                                         ViewCreator<T> viewCreator, ArrayList<T> arraylist) {
        this.context = context;
        this.paginationView = container;
        this.viewCreator = viewCreator;

        if (null == requestAction) {
            requestAction = new RequestAction();
            requestAction.isPageBeanEnable = true;
        }

        if (null == httpModule) {
            httpModule = new BaseHttpModule(context);
            httpModule.init();
            httpModule.setRequestListener(requestListener);
        }
        setUpViews();
        bindEvents();
        initData(arraylist);
        return this;
    }

    public void setFootView(View footView) {
        this.footView = footView;

    }

    public PaginationWidget<T> init(Context context, View container,
                                    ViewCreator<T> viewCreator, int pageSize) {
        requestAction.pageBean.setPageSize(pageSize);
        return init(context, container, viewCreator);
    }

    private void setUpViews() {

        lv_page_body = (PullToRefreshListView) paginationView
                .findViewById(R.id.lv_pagination_widget_data_body);
        lvNews_foot_more = (TextView) footView
                .findViewById(R.id.listview_foot_more);
        lvNews_foot_progress = (ProgressBar) footView
                .findViewById(R.id.listview_foot_progress);
        lv_page_body.addFooterView(footView);

    }

    private void bindEvents() {
        lv_page_body.setOnItemClickListener(new OnItemClickListener());
        //lv_page_body.setOnItemLongClickListener(new OnItemLongClick());
        // 列表加载更多的操作
        lv_page_body.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                lv_page_body.onScrollStateChanged(view, scrollState);

                // 数据为空--不用继续下面代码了
                if (tableBodyAdapter.getDataCache().size() == 0)
                    return;
                // 判断是否滚动到底部
                boolean scrollEnd = false;
                try {
                    if (view.getPositionForView(footView) == view
                            .getLastVisiblePosition())
                        scrollEnd = true;
                } catch (Exception e) {
                    scrollEnd = false;
                }

                int lvDataState = StringUtils.toInt(lv_page_body.getTag());

                if (scrollEnd && lvDataState == UIHelper.LISTVIEW_DATA_MORE) {
                    Log.w("scrollEnd=",""+requestAction.pageBean.getCurrentPage());
                    lv_page_body.setTag(UIHelper.LISTVIEW_DATA_LOADING);
                    lvNews_foot_more.setText(R.string.load_ing);// 设置 显示“加载中。。。”
                    lvNews_foot_progress.setVisibility(View.VISIBLE);
                    action = UIHelper.LISTVIEW_ACTION_SCROLL;
                    requestAction.pageBean
                            .setCurrentPage(requestAction.pageBean
                                    .getCurrentPage() + 1);
                    loadPaginationData();

                }
            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                lv_page_body.onScroll(view, firstVisibleItem, visibleItemCount,
                        totalItemCount);
            }
        });
        // 刷新的操作
        lv_page_body
                .setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
                    public void onRefresh() {
                        action = UIHelper.LISTVIEW_ACTION_REFRESH;
                        lvNews_foot_more.setText(null);
                        lvNews_foot_progress.setVisibility(ProgressBar.GONE);
                        requestAction.pageBean.setCurrentPage(1);
                        loadPaginationData();
                    }
                });

    }

    private void initData() {
        tableBodyAdapter = new CommonAdapter<T>(context, viewCreator,
                new ArrayList<T>());
        lv_page_body.setAdapter(tableBodyAdapter);
        //add
        lv_page_body.setOnPositionChangedListener((OnPositionChangedListener) context);
        //new
    }

    private void initData(ArrayList<T> arraylist) {
        tableBodyAdapter = new CommonAdapter<T>(context, viewCreator,
                arraylist);
        lv_page_body.setAdapter(tableBodyAdapter);
        //add
        lv_page_body.setOnPositionChangedListener((OnPositionChangedListener) context);
        //new
        if (arraylist.size() < 5) {
            lv_page_body.setTag(UIHelper.LISTVIEW_DATA_FULL);
            lvNews_foot_more.setText(R.string.load_full);// 已加载全部
            lvNews_foot_progress.setVisibility(ProgressBar.GONE);
        }
    }

    // 点击事件
    public class OnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                long arg3) {
            if (null != pageBodyOnItemClickListener) {
                pageBodyOnItemClickListener.onItemClick(arg0, arg1, position,
                        arg3);
            }
        }
    }

    // 长按事件
    public class OnItemLongClick implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
            if (null != onLongClick) {
                onLongClick.onItemLongClick(arg0, arg1, arg2, arg3);
            }
            return false;
        }

    }

    /**
     * 这里进行数据分离：在线 、离线 模式
     */
    public void loadPaginationData() {
       boolean isOnline=((AppContext)context.getApplicationContext()).isNetworkConnected();
         if(isOnline) {
             requestAction.serviceName = serviceName;
             httpModule.executeRequest(requestAction, parseHandler,
                     new ProcessResponse(), requestType);
         }else{
             if("1".equals(getDifferent())){
                 querySqliteData();
             }else {
                 querySqliteDatas();
             }
         }
    }

    /**
     * 离线模式 查询本地数据库  这里进行分页查询 查询总记录数// TODO
     */
    public void querySqliteData() {
                try {
                    DatabaseHelper dbhelper = DatabaseHelper.getHelper(context);
                    Dao dao = dbhelper.getDao(AppHeadTable.class);
                    QueryBuilder builder = dao.queryBuilder();
                    builder.offset((requestAction.pageBean
                            .getCurrentPage() - 1) * 5);//表示查询的起始位置
                    builder.limit(5);//表示总共获取的对象数量
                    List<AppHeadTable> list = builder.query();
                    int allcount = dao.queryForAll().size();
                    Log.w("PaginationWight", "query.list=" + list.size());
                    Log.w("PaginationWight", "allcount=" + allcount);

                    PagerResponse workOrderResponse = new PagerResponse();
                    workOrderResponse.pageBean = new PageBean();

                    workOrderResponse.pageBean.setAllCount(allcount);

                    List<WorkOrder> workOrderList = new ArrayList<WorkOrder>();


                    for (int i = 0; i < list.size(); i++) {
                        WorkOrder workOrder = new WorkOrder();
                        workOrder.fields = new HashMap<String, Field>();
                        workOrder.fieldKeys = new ArrayList<String>();

                        AppHeadTable appHeadTable = list.get(i);
                        Field field = new Field();
                        field.fieldEnName = "PROJECTNAME";
                        field.fieldChName="项目名称";
                        field.fieldContent = appHeadTable.getPROJECTNAME();
                        workOrder.fields.put("PROJECTNAME", field);

                        field = new Field();
                        field.fieldEnName = "DELIVERINFORMCOD";
                        field.fieldChName="发货通知编号";
                        field.fieldContent = appHeadTable.getDELIVERINFORMCOD();
                        workOrder.fields.put("DELIVERINFORMCOD", field);

                        field = new Field();
                        field.fieldEnName = "SUPPLIERNAME";
                        field.fieldChName="供应商";
                        field.fieldContent = appHeadTable.getSUPPLIERNAME();
                        workOrder.fields.put("SUPPLIERNAME", field);


                        field = new Field();
                        field.fieldEnName = "ZDJZT";
                        field.fieldChName="状态";
                        field.fieldContent = appHeadTable.getZDJZT();
                        workOrder.fields.put("ZDJZT", field);

                        workOrderList.add(workOrder);
                    }
                    workOrderResponse.pageBean.setPageDatas(workOrderList);
                    mHandler.obtainMessage(0, workOrderResponse).sendToTarget();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

    }


    /**
     * 离线模式 查询本地数据库  这里进行分页查询 查询总记录数// TODO
     */
    public void querySqliteDatas() {
        try {
            DatabaseHelper dbhelper = DatabaseHelper.getHelper(context);
            Dao dao = dbhelper.getDao(LeaveHeadTable.class);
            QueryBuilder builder = dao.queryBuilder();
            builder.offset((requestAction.pageBean
                    .getCurrentPage()-1) * 5);//表示查询的起始位置
            builder.limit(5);//表示总共获取的对象数量
            List<LeaveHeadTable> list = builder.query();
            int allcount = dao.queryForAll().size();
            Log.w("PaginationWight", "query.list=" + list.size());
            Log.w("PaginationWight", "allcount=" + allcount);


            WorkOrderResponse workOrderResponse = new WorkOrderResponse();
            workOrderResponse.pageBean = new PageBean();

            workOrderResponse.pageBean.setAllCount(allcount);
            WorkOrder workOrder = new WorkOrder();
            List<WorkOrder> workOrderList = new ArrayList<WorkOrder>();
            workOrder.fields = new HashMap<String, Field>();
            workOrder.fieldKeys = new ArrayList<String>();

            for (int i = 0; i < list.size(); i++) {
                LeaveHeadTable leaveHeadTable = list.get(i);
                Field field = new Field();
                field.fieldEnName = "RESERV_NO";
                field.fieldChName="预留号";
                field.fieldContent = leaveHeadTable.getRESERV_NO();
                workOrder.fields.put("RESERV_NO", field);

                field = new Field();
                field.fieldEnName = "ORDERID";
                field.fieldChName="工单";
                field.fieldContent = leaveHeadTable.getORDERID();
                Log.w("PaginationWight", "  field.ORDERID=" +   field.fieldContent);
                workOrder.fields.put("ORDERID", field);

                field = new Field();
                field.fieldEnName = "NETWORK";
                field.fieldChName="网络号";
                field.fieldContent = leaveHeadTable.getNETWORK();
                Log.w("PaginationWight", "  field.NETWORK=" +   field.fieldContent);
                workOrder.fields.put("NETWORK", field);

                workOrderList.add(workOrder);
            }
            Log.w("PaginationWight", "workOrderList.size=" + workOrderList.size());
            workOrderResponse.pageBean.setPageDatas(workOrderList);
            mHandler.obtainMessage(0, workOrderResponse).sendToTarget();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private void showToast(int message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void showProgressDialog() {
        progressDialog = ProgressDialog.show(context, context.getResources()
                .getString(R.string.dataLoadingTitle), context.getResources()
                .getString(R.string.dataLoadingMsg), true);
        progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.cancel();
                    return true;
                }
                return false;
            }

        });
    }

    public void setPageSize(int pageSize) {
        requestAction.pageBean.setPageSize(pageSize);
    }

    public PageBean getPageBean() {
        if (null == requestAction.pageBean) {
            requestAction.pageBean = new PageBean();
        }
        return requestAction.pageBean;
    }

    public List<T> getPageBodyDatas() {
        return tableBodyAdapter.getDataCache();
    }

    /**
     * 调整分页底部的数据指示器
     */
    public void adjustPageIndicator() {
    }


    public void setPageBodyOnItemClickListener(
            OnItemClickListener pageBodyOnItemClickListener) {
        this.pageBodyOnItemClickListener = pageBodyOnItemClickListener;
    }

//	public void setPageOnLongClick(OnItemLongClick onLongClick){
//		this.onLongClick = onLongClick;
//		
//	}

    /**
     * 设置查询参数.
     *
     * @param key
     * @param value
     */
    public void putQueryString(String key, String value) {
        requestAction.putParam(key, value);
    }

    public void notifyDataSetChanged() {
        tableBodyAdapter.notifyDataSetChanged();
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public RequestAction getRequestAction() {
        return requestAction;
    }

    public void setDataObserver(DataObserver dataObserver) {
        this.dataObserver = dataObserver;
    }

    protected String key;

    public void setKey(String key) {
        this.key = key;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public BaseHttpModule getHttpModule() {
        return httpModule;
    }

    public void setParseHandler(BaseParserHandler parseHandler) {
        this.parseHandler = parseHandler;
    }

}
