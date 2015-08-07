package com.epsmart.wzcc.activity.supply.approval;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.epsmart.wzcc.R;


/*
 *验收信息
 */
public class BasicInfoFat extends BaseFragment {
	private ProgressBar pb;
	private View view;
	private Activity activity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
				parent.removeView(view);
		}
		view = inflater.inflate(R.layout.approval_info, container, false);


		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		//reqHttp();

//		pb = (ProgressBar) view.findViewById(R.id.pb_sjfdl);
//		mYesjtv = (TextView) view.findViewById(R.id.yesj_tv);


	}

	@SuppressLint("NewApi")
	private void reqHttp() {
//		RequestParams params = new RequestParams();
//		params.put("unitId", ((UnitInforActivity) getActivity()).unitid);// 机组标号
//		System.out.println("UrlConfig.unitdetail=" + UrlConfig.unitdetail);
//		startProgressDialog("数据加载中......");
//		http.post(UrlConfig.unitdetail, params, new UnitHandler(), processor);
	}

	/*ResponseProcessor processor = new ResponseProcessor() {
		@Override
		public void onFailure(Throwable e, String reason) {
			stopProgressDialog();
			System.out.print(reason);
			if (null == reason || "".equals(reason)) {
				reason = "实时工况基本信息加载失败！";
			}
			ToastHelper.toast(activity, reason);
		}

		@Override
		public void processResponse(String response, Object data) {
			stopProgressDialog();
			System.out.println("实时工况信息 response=" + response);
			RealResp resp = (RealResp) data;
			if ("1".equals(resp.requestcode)) {

				handler.obtainMessage(0, resp).sendToTarget();
				// handler.sendMessageDelayed(msg, delayMillis);
			}
		}
	};*/

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);


		}
	};


}
