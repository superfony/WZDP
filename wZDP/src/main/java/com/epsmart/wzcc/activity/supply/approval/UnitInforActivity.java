package com.epsmart.wzcc.activity.supply.approval;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.AppContext;
import com.epsmart.wzcc.activity.RequestParamConfig;
import com.epsmart.wzcc.activity.netbase.BaseNetAct;
import com.epsmart.wzcc.activity.supply.approval.parcelable.ApprovalResponse;
import com.epsmart.wzcc.activity.supply.approval.parcelable.EntityContent;
import com.epsmart.wzcc.activity.supply.approval.parcelable.HeadBean;
import com.epsmart.wzcc.activity.supply.approval.parcelable.ItemBean;
import com.epsmart.wzcc.activity.supply.bean.Field;
import com.epsmart.wzcc.bean.RequestPram;
import com.epsmart.wzcc.bean.WorkOrder;
import com.epsmart.wzcc.bean.WorkOrderResponse;
import com.epsmart.wzcc.common.Common;
import com.epsmart.wzcc.db.DatabaseHelper;
import com.epsmart.wzcc.db.table.AppDetailTable;
import com.epsmart.wzcc.db.table.AppHeadTable;
import com.epsmart.wzcc.http.BaseHttpModule;
import com.epsmart.wzcc.http.ModuleResponseProcessor;
import com.epsmart.wzcc.http.request.BaseRequest;
import com.epsmart.wzcc.http.request.RequestAction;
import com.epsmart.wzcc.http.request.model.PageBean;
import com.epsmart.wzcc.http.response.model.StatusEntity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 验收入库详情信息
 * 本类实现根据发货通知单号获取详情数据
 */
public class UnitInforActivity extends BaseNetAct {
    public String DELIVERINFORMCOD;
    public String company_wz_value;
    public String company_gys_value;
    public String company_xmdw_value;
    public String company_jl_value;
    public String company_sgdw_value;


    public ApprovalResponse getApprovalResponse() {
        return approvalResponse;
    }

    public void setApprovalResponse(ApprovalResponse approvalResponse) {
        this.approvalResponse = approvalResponse;
    }

    public ApprovalResponse approvalResponse;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        DELIVERINFORMCOD = getIntent().getStringExtra("DELIVERINFORMCOD").toString();
        setContentView(R.layout.approval);
        reqHttp();
    }

    private void reqHttp() {

        boolean isOnline = ((AppContext) activity.getApplicationContext()).isNetworkConnected();
        if (isOnline) {
            showModuleProgressDialog("");
            RequestAction requestAction = new RequestAction();
            requestAction.reset();
            RequestPram requestPram = new RequestPram();
            requestPram.param = DELIVERINFORMCOD;
            requestPram.methodName = RequestParamConfig.getHouseDetail;
            requestAction.setReqPram(requestPram);
            httpModule.executeRequest(requestAction, new ApprovalDetailHandler(), new ProcessResponseHead(),
                    BaseRequest.RequestType.THRIFT);
        } else {
            Log.w("UnitInfoActivity","DELIVERINFORMCOD="+DELIVERINFORMCOD);
            querySqliteData();
        }
    }

    // 查询本地
    public void querySqliteData() {
        try {
            //**********************查询数据库*******************************
            DatabaseHelper dbhelper = DatabaseHelper.getHelper(activity);
            Dao dao = dbhelper.getDao(AppHeadTable.class);
            QueryBuilder builder = dao.queryBuilder();
            AppHeadTable appHeadTable = (AppHeadTable) builder.where().eq("DELIVERINFORMCOD", DELIVERINFORMCOD).queryForFirst();

            Dao detailDao=dbhelper.getDao(AppDetailTable.class);
            QueryBuilder detailbuild=detailDao.queryBuilder();
            ArrayList<AppDetailTable> appDetailTableList =  ( ArrayList<AppDetailTable>)detailbuild.where().eq("DELIVERINFORMCOD", DELIVERINFORMCOD).query();
            //*****************************************************


            ApprovalResponse approvalResponse = new ApprovalResponse();
            approvalResponse.entity = new EntityContent();
            approvalResponse.entity.headBean = new HeadBean();
            approvalResponse.entity.itemBeansList = new ArrayList<ItemBean>();


            approvalResponse.entity.headBean.DELIVERINFORMCOD=appHeadTable.DELIVERINFORMCOD;
            approvalResponse.entity.headBean.CONTRACTID=appHeadTable.CONTRACTID;
            approvalResponse.entity.headBean.PROJECTNAME=appHeadTable.PROJECTNAME;
            approvalResponse.entity.headBean.SUPPLIERNAME=appHeadTable.SUPPLIERNAME;
            approvalResponse.entity.headBean.SUPPLINKMAN=appHeadTable.SUPPLINKMAN;
            approvalResponse.entity.headBean.ACTUALDELIVERYPL=appHeadTable.ACTUALDELIVERYPL;
            approvalResponse.entity.headBean.CARRLINKMAN=appHeadTable.CARRLINKMAN;



           for(int i=0;i<appDetailTableList.size();i++){
               AppDetailTable appDetailTable=appDetailTableList.get(i);
               ItemBean itemBean=new ItemBean();
               itemBean.DELIVERINFORMCOD=appDetailTable.DELIVERINFORMCOD;
               itemBean.DELIVERYAMOUNT=appDetailTable.DELIVERYAMOUNT;
               itemBean.HANDOVERAMOUNT=appDetailTable.HANDOVERAMOUNT;
               itemBean.MATERIALTEXT=appDetailTable.MATERIALTEXT;
               itemBean.PLANDELIVERYAMOU=appDetailTable.PLANDELIVERYAMOU;
               approvalResponse.entity.itemBeansList.add(itemBean);
           }



            UnitInforActivity.this.approvalResponse = approvalResponse;
            mHandler.obtainMessage(0, null).sendToTarget();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    class ProcessResponseHead implements ModuleResponseProcessor {
        @Override
        public void processResponse(BaseHttpModule httpModule, Object parseObj) {

            if (parseObj instanceof StatusEntity) {
                StatusEntity staty = (StatusEntity) parseObj;
                String result = staty.result;
                String message = staty.message;
                if ("1".equals(result) && !TextUtils.isEmpty(message)) {
                    mHandler.obtainMessage(1, message).sendToTarget();
                }
            } else if (parseObj instanceof ApprovalResponse) {
                UnitInforActivity.this.approvalResponse = (ApprovalResponse) parseObj;
                mHandler.obtainMessage(0, null).sendToTarget();
            }
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            closeDialog();
            if (msg.what == 0) {
                Common.replaceRightFragment(activity, new UnitInforFragment(), false, R.id.qty_m);
            }
        }
    };
}
