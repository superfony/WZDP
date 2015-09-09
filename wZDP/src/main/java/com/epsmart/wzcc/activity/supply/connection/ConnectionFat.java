package com.epsmart.wzcc.activity.supply.connection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.RequestParamConfig;
import com.epsmart.wzcc.activity.fragment.CommonFragment;
import com.epsmart.wzcc.activity.pagination.PaginationWidget;
import com.epsmart.wzcc.activity.search.QueryDialogListener;
import com.epsmart.wzcc.activity.supply.bean.Field;
import com.epsmart.wzcc.bean.Pagination;
import com.epsmart.wzcc.bean.QueryCondition;
import com.epsmart.wzcc.bean.ViewBuildBak;
import com.epsmart.wzcc.bean.WorkOrder;
import com.epsmart.wzcc.http.request.BaseRequest.RequestType;
import com.epsmart.wzcc.http.request.RequestAction;

import java.util.ArrayList;

/*
 * 物资交接、验收列表界面
 */
public class ConnectionFat extends CommonFragment {
    private View view;
    // 初始化分页标签
    private ViewBuildBak viewBuild;
    // 申明PopupWindow对象引用
    private String TAG = ConnectionFat.class.getName();
    @Override
    public void onAttach(Activity activity) {
        queryCondition = new QueryCondition();
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pull_fragment_item, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPage(savedInstanceState);// 数据进行请求的页面
        activity.setSmShow();
        appContext.wzdpType = WZDPTYPE.PRODUCTION;
        search_lay.setVisibility(View.VISIBLE);
        search_lay.setOnClickListener(searchLis);
        search_btn.setOnClickListener(searchLis);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        title_image.setBackgroundResource(R.drawable.connection_title);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        search_lay.setVisibility(View.INVISIBLE);
        super.onPause();
    }

    /**
     * 初始化分页控件
     *
     * @param savedInstanceState
     */
    public void initPage(Bundle savedInstanceState) {

        // 列表分页控件初始化设置
        if (null == paginationWidget) {
            viewBuild = new ViewBuildBak();
            paginationWidget = new Pagination();
            paginationWidget.setFootView(activity.getLayoutInflater().inflate(
                    R.layout.listview_footer, null));
            paginationWidget.init(activity,
                    activity.findViewById(R.id.contentRoot), viewBuild);
            paginationWidget.getHttpModule().setServiceNameSpace(
                    RequestParamConfig.serviceNameSpace);
            paginationWidget.getHttpModule().setServiceUrl(
                    RequestParamConfig.ServerUrl);
            paginationWidget.setRequestType(RequestType.THRIFT);// TODO
            initPaginationWidget(paginationWidget);
            paginationWidget.setDifferent("1");
            paginationWidget.loadPaginationData();
        } else {
            ArrayList<WorkOrder> arraylist = (ArrayList<WorkOrder>) paginationWidget.tableBodyAdapter.getDataCache();
            RequestAction requestAction = paginationWidget.requestAction;
            Object tag = paginationWidget.lv_page_body.getTag();
            viewBuild = new ViewBuildBak();
            paginationWidget = new Pagination();
            paginationWidget.setFootView(activity.getLayoutInflater().inflate(
                    R.layout.listview_footer, null));
            paginationWidget.initArray(activity,
                    activity.findViewById(R.id.contentRoot), viewBuild, arraylist);
            paginationWidget.getHttpModule().setServiceNameSpace(
                    RequestParamConfig.serviceNameSpace);
            paginationWidget.getHttpModule().setServiceUrl(
                    RequestParamConfig.ServerUrl);
            paginationWidget.setRequestType(RequestType.THRIFT);
            initPaginationWidget(paginationWidget);
            paginationWidget.requestAction = requestAction;
            paginationWidget.lv_page_body.setTag(tag);
        }

    }

    /**
     * 查询条件
     */
    protected QueryCondition queryCondition;

    protected void initPaginationWidget(
            final PaginationWidget<WorkOrder> paginationWidget) {
        processQueryCondition(queryCondition);
        paginationWidget.setPageSize(RequestParamConfig.pagesize);
        paginationWidget.setServiceName(RequestParamConfig.receiveList);
        paginationWidget
                .setPageBodyOnItemClickListener(paginationWidget.new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int position, long arg3) {

                      if (position == 0
                                || paginationWidget.getPageBodyDatas().size() + 1 == position) {
                            return;
                        }
                        WorkOrder workOrder = paginationWidget
                                .getPageBodyDatas().get(position - 1);
                              //发货通知编号
                        Field DELIVERINFORMCOD = workOrder.fields.get("DELIVERINFORMCOD");
                        Intent intent = new Intent();
                        intent.putExtra("DELIVERINFORMCOD",DELIVERINFORMCOD.fieldContent);
                        intent.setClass(activity, ConnectionAct.class);
                        activity.startActivity(intent);
                    }
                });

    }

    /**
     * 设置查询条件
     * @param condition
     */
    public void processQueryCondition(QueryCondition condition) {
        requestPram.methodName = RequestParamConfig.receiveList;
        requestPram.userid = "130";//((AppContext)activity.getApplication()).user.getUid();
        paginationWidget.requestAction.setReqPram(requestPram);
    }

    {
        listener = new QueryDialogListener() {
            @Override
            public void doQuery(String req) {
                requestPram.param = req;
                paginationWidget.getRequestAction().reset();
                processQueryCondition(null);
                paginationWidget.loadPaginationData();
            }
        };
    }
}