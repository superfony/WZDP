package com.epsmart.wzcc.activity.supply.approval;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.RequestParamConfig;
import com.epsmart.wzcc.activity.netbase.BaseNetAct;
import com.epsmart.wzcc.activity.supply.approval.parcelable.ApprovalResponse;
import com.epsmart.wzcc.bean.RequestPram;
import com.epsmart.wzcc.common.Common;
import com.epsmart.wzcc.http.BaseHttpModule;
import com.epsmart.wzcc.http.ModuleResponseProcessor;
import com.epsmart.wzcc.http.request.BaseRequest;
import com.epsmart.wzcc.http.request.RequestAction;
import com.epsmart.wzcc.http.response.model.StatusEntity;

/**
 *  验收入库详情信息
 *  本类实现根据发货通知单号获取详情数据
 */
public class UnitInforActivity extends BaseNetAct {
	public  String DELIVERINFORMCOD;

	public String company_wz_value;
	public String company_gys_value;
	public String company_xmdw_value;
	public String company_jl_value;

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
		showModuleProgressDialog("");
		RequestAction requestAction = new RequestAction();
		requestAction.reset();
		RequestPram requestPram = new RequestPram();
		requestPram.param=DELIVERINFORMCOD;
		requestPram.methodName = RequestParamConfig.getHouseDetail;
		requestAction.setReqPram(requestPram);

		httpModule.executeRequest(requestAction, new ApprovalDetailHandler(), new ProcessResponseHead(),
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
			}else if(parseObj instanceof ApprovalResponse){
				UnitInforActivity.this.approvalResponse=(ApprovalResponse)parseObj;
				mHandler.obtainMessage(0, null).sendToTarget();
			}
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			closeDialog();
			if(msg.what==0){
				Common.replaceRightFragment(activity, new UnitInforFragment(), false, R.id.qty_m);
			}
		}
	};



}
