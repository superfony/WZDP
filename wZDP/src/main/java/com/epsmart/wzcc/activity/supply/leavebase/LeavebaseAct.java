package com.epsmart.wzcc.activity.supply.leavebase;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.AppContext;
import com.epsmart.wzcc.activity.RequestParamConfig;
import com.epsmart.wzcc.activity.netbase.BaseNetAct;
import com.epsmart.wzcc.activity.supply.leavebase.parcelable.LeaveEntityContent;
import com.epsmart.wzcc.activity.supply.leavebase.parcelable.LeaveItemBean;
import com.epsmart.wzcc.activity.supply.leavebase.parcelable.LeavebaseResponse;
import com.epsmart.wzcc.bean.RequestPram;
import com.epsmart.wzcc.common.Common;
import com.epsmart.wzcc.db.DatabaseHelper;
import com.epsmart.wzcc.db.table.LeaveDetailTable;
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
 *  出库详情信息
 *  本类实现根据发货通知单号获取详情数据
 */
public class LeavebaseAct extends BaseNetAct {
	public  String RESERV_NO;



	public LeavebaseResponse getLeavebaseResponse() {
		return leavebaseResponse;
	}

	public void setLeavebaseResponse(LeavebaseResponse leavebaseResponse) {
		this.leavebaseResponse = leavebaseResponse;
	}

	public LeavebaseResponse leavebaseResponse;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity=this;
		RESERV_NO=getIntent().getStringExtra("RESERV_NO").toString();
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
			requestPram.param = RESERV_NO;
			requestPram.methodName = RequestParamConfig.getWarehouseDetail;
			requestAction.setReqPram(requestPram);

			httpModule.executeRequest(requestAction, new LeaveDetailHandler(), new ProcessResponseHead(),
					BaseRequest.RequestType.THRIFT);
		}else {
			Log.w("LeavebaseAct", "RESERV_NO=" + RESERV_NO);
			querySqliteData();
		}

	}

	// 查询本地
	public void querySqliteData() {
		try {
			//**********************查询数据库*******************************
			DatabaseHelper dbhelper = DatabaseHelper.getHelper(activity);
//			Dao dao = dbhelper.getDao(LeaveHeadTable.class);
//			QueryBuilder builder = dao.queryBuilder();
//			LeaveHeadTable leaveHeadTable = (LeaveHeadTable) builder.where().eq("RESERV_NO", RESERV_NO).queryForFirst();

			Dao detailDao=dbhelper.getDao(LeaveDetailTable.class);
			QueryBuilder detailbuild=detailDao.queryBuilder();
			ArrayList<LeaveDetailTable> leavedetailTableArrayList =  ( ArrayList<LeaveDetailTable>)detailbuild.where().eq("RESERV_NO", RESERV_NO).query();
			//*****************************************************


			LeavebaseResponse leavebaseResponse = new LeavebaseResponse();
			leavebaseResponse.entity = new LeaveEntityContent();
			leavebaseResponse.entity.leaveItemBeansList = new ArrayList<LeaveItemBean>();

			for(int i=0;i<leavedetailTableArrayList.size();i++){
				LeaveDetailTable leaveDetailTable=leavedetailTableArrayList.get(i);
				LeaveItemBean leaveItemBean=new LeaveItemBean();
				leaveItemBean.RESERV_NO=leaveDetailTable.RESERV_NO;
				leaveItemBean.MATERIALTEXT=leaveDetailTable.MATERIALTEXT;
				leaveItemBean.MATERIAL=leaveDetailTable.MATERIAL;
				leaveItemBean.PLANT=leaveDetailTable.PLANT;
				leaveItemBean.RES_ITEM=leaveDetailTable.RES_ITEM;
				leaveItemBean.OPEN_QUAN=leaveDetailTable.OPEN_QUAN;
				leaveItemBean.STGE_LOC=leaveDetailTable.STGE_LOC;
				leaveItemBean.ENTRY_QNT=leaveDetailTable.ENTRY_QNT;
				leaveItemBean.BATCH=leaveDetailTable.BATCH;
				leavebaseResponse.entity.leaveItemBeansList.add(leaveItemBean);
			}

			LeavebaseAct.this.leavebaseResponse = leavebaseResponse;
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
			}else if(parseObj instanceof LeavebaseResponse){
				LeavebaseAct.this.leavebaseResponse =(LeavebaseResponse)parseObj;
				mHandler.obtainMessage(0, null).sendToTarget();
			}
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			closeDialog();
			if(msg.what==0){
				Common.replaceRightFragment(activity, new LeaveDetailFat(), false, R.id.info_container);
			}
		}
	};
}
