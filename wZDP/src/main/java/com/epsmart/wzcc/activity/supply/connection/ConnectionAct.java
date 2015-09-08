package com.epsmart.wzcc.activity.supply.connection;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.AppContext;
import com.epsmart.wzcc.activity.RequestParamConfig;
import com.epsmart.wzcc.activity.netbase.BaseNetAct;
import com.epsmart.wzcc.activity.supply.approval.ApprovalDetailHandler;
import com.epsmart.wzcc.activity.supply.approval.parcelable.ApprovalResponse;
import com.epsmart.wzcc.activity.supply.approval.parcelable.EntityContent;
import com.epsmart.wzcc.activity.supply.approval.parcelable.HeadBean;
import com.epsmart.wzcc.activity.supply.approval.parcelable.ItemBean;
import com.epsmart.wzcc.bean.RequestPram;
import com.epsmart.wzcc.common.Common;
import com.epsmart.wzcc.db.DatabaseHelper;
import com.epsmart.wzcc.db.table.AppDetailTable;
import com.epsmart.wzcc.db.table.AppHeadTable;
import com.epsmart.wzcc.http.BaseHttpModule;
import com.epsmart.wzcc.http.ModuleResponseProcessor;
import com.epsmart.wzcc.http.request.BaseRequest;
import com.epsmart.wzcc.http.request.RequestAction;
import com.epsmart.wzcc.http.response.model.StatusEntity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *  交接模块
 *  本类实现根据发货通知单号获取详情数据
 */
public class ConnectionAct extends BaseNetAct {
	public  String DELIVERINFORMCOD;
	public String fhfqz_et_value ;
	public String shfqz_et_value ;
	public String actualdate_value;

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
		activity=this;
		DELIVERINFORMCOD=getIntent().getStringExtra("DELIVERINFORMCOD").toString();
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
//			Log.w("ConnectionAct", "DELIVERINFORMCOD=" + DELIVERINFORMCOD);
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


			approvalResponse.entity.headBean.DELIVERINFORMCOD=appHeadTable.DELIVERINFORMCOD;//发货通知单
			approvalResponse.entity.headBean.CONTRACTID=appHeadTable.CONTRACTID;//合同编号
			approvalResponse.entity.headBean.PROJECTNAME=appHeadTable.PROJECTNAME;//项目名称
			approvalResponse.entity.headBean.PURCHASECODE=appHeadTable.PURCHASECODE;//采购订单
			approvalResponse.entity.headBean.SUPPLIERNAME=appHeadTable.SUPPLIERNAME;//供应商名称
			approvalResponse.entity.headBean.SUPPLINKMAN=appHeadTable.SUPPLINKMAN;//供应商联系人
			approvalResponse.entity.headBean.ACTUALDELIVERYPL=appHeadTable.ACTUALDELIVERYPL;//实际交货地点
			approvalResponse.entity.headBean.CARRLINKMAN=appHeadTable.CARRLINKMAN;//承运商联系人



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

			ConnectionAct.this.approvalResponse = approvalResponse;
			mHandler.obtainMessage(0, null).sendToTarget();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	class ProcessResponseHead implements ModuleResponseProcessor {
		@Override
		public void processResponse(BaseHttpModule httpModule, Object parseObj) {

			if (parseObj instanceof StatusEntity) {
				StatusEntity staty = (StatusEntity)parseObj;
				String result = staty.result;
				String message = staty.message;
				if("1".equals(result) && !TextUtils.isEmpty(message)){
					mHandler.obtainMessage(1, message).sendToTarget();
				}
			}else if(parseObj instanceof ApprovalResponse){
				ConnectionAct.this.approvalResponse=(ApprovalResponse)parseObj;
				mHandler.obtainMessage(0, null).sendToTarget();
			}
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			closeDialog();
			if(msg.what==0){
				Common.replaceRightFragment(activity, new ConnectionFragment(), false, R.id.qty_m);
			}
		}
	};



}
