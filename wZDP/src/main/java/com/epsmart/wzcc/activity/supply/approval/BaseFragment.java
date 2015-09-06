package com.epsmart.wzcc.activity.supply.approval;

import android.annotation.SuppressLint;
import com.epsmart.wzcc.R;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.epsmart.wzcc.activity.RequestParamConfig;
import com.epsmart.wzcc.http.BaseHttpModule;
import com.epsmart.wzcc.http.request.HttpException;
import com.epsmart.wzcc.http.response.model.Response;
import com.epsmart.wzcc.http.response.model.StatusEntity;
import com.epsmart.wzcc.http.xml.parser.BaseXmlParser;
import com.epsmart.wzcc.widget.XGCustomProgressDialog;


@SuppressLint("NewApi")
public class BaseFragment extends Fragment {
	private XGCustomProgressDialog progressDialog = null;
	protected Activity activity;
	protected  ImageView title_image;

	protected BaseHttpModule httpModule;

	protected RelativeLayout.LayoutParams layoutParams;
	protected  RelativeLayout  search_lay,present_lay;
	protected Button search_btn,present_btn;

	@SuppressLint("NewApi")
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity=activity;
		init();
	}


	private void init() {
		if (httpModule == null) {
			httpModule = new BaseHttpModule(activity);
			httpModule.init();
			httpModule.setRequestListener(requestListener);
			httpModule.setServiceNameSpace(RequestParamConfig.serviceNameSpace);
			httpModule.setServiceUrl(RequestParamConfig.ServerUrl);
		}
	}

	protected BaseHttpModule.RequestListener requestListener = new BaseHttpModule.RequestListener() {
		@Override
		public void onSuccess(Response response) {
			if (progressDialog != null && progressDialog.isShowing())
				progressDialog.dismiss();
			httpModule.processResponse(httpModule, response,
					getXmlParser(response), httpModule.getResponseProcess());
		}

		@Override
		public void onFail(Exception e) {
			if (progressDialog != null && progressDialog.isShowing())
				progressDialog.dismiss();
			String msg = "未知错误";
			if (e instanceof HttpException) {
				HttpException he = (HttpException) e;
				msg = he.getMessage();
			}
			Message obtainMessage = mHandler.obtainMessage(0);
			obtainMessage.obj = msg;
			obtainMessage.sendToTarget();
		}
	};



	private BaseHandler mHandler = new BaseHandler();

	private class BaseHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 0:
					Toast.makeText(activity, msg.obj + "",
							Toast.LENGTH_LONG).show();
					break;
				case 1:
				case 2:// 删除的操作
					StatusEntity see = (StatusEntity) msg.obj;
					Toast.makeText(activity, see.message + "", Toast.LENGTH_LONG)
							.show();
					break;
				default:
					break;
			}
		}
	}
	protected BaseXmlParser getXmlParser(Response response) {
		response.setResponseAsString(response.getResponseAsString().replace(
				"&", "#"));
		return httpModule.getBaseXmlParser(response,
				httpModule.getParseHandler());
	}
	@SuppressLint("NewApi")
	protected void showModuleProgressDialog(String msg) {
		if (progressDialog == null) {
			progressDialog = XGCustomProgressDialog.createDialog(getActivity());
			progressDialog.setMessage(msg);
		}
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
	}

	protected void stopProgressDialog(){
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ActionBar actionBar = activity.getActionBar();
		View view = actionBar.getCustomView();
		title_image = (ImageView) view.findViewById(R.id.title_image);
		search_lay=(RelativeLayout)view.findViewById(R.id.search_lay);
		present_lay=(RelativeLayout)view.findViewById(R.id.present_lay);
		search_btn=(Button)view.findViewById(R.id.search_btn);
		present_btn=(Button)view.findViewById(R.id.present_btn);

	}
}
