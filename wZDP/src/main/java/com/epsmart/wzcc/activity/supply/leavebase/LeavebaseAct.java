package com.epsmart.wzcc.activity.supply.leavebase;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.RequestParamConfig;
import com.epsmart.wzcc.activity.netbase.BaseNetAct;
import com.epsmart.wzcc.activity.supply.leavebase.parcelable.LeavebaseResponse;
import com.epsmart.wzcc.bean.RequestPram;
import com.epsmart.wzcc.common.Common;
import com.epsmart.wzcc.http.BaseHttpModule;
import com.epsmart.wzcc.http.ModuleResponseProcessor;
import com.epsmart.wzcc.http.request.BaseRequest;
import com.epsmart.wzcc.http.request.RequestAction;
import com.epsmart.wzcc.http.response.model.StatusEntity;

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
		showModuleProgressDialog("");
		RequestAction requestAction = new RequestAction();
		requestAction.reset();
		RequestPram requestPram = new RequestPram();
		requestPram.param=RESERV_NO;
		requestPram.methodName = RequestParamConfig.getWarehouseDetail;
		requestAction.setReqPram(requestPram);

		httpModule.executeRequest(requestAction, new LeaveDetailHandler(), new ProcessResponseHead(),
				BaseRequest.RequestType.THRIFT);


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
