package com.epsmart.wzcc.activity.achar;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.RequestParamConfig;
import com.epsmart.wzcc.activity.achar.service.CharResponse;
import com.epsmart.wzcc.activity.fragment.CommonFragment.WZDPTYPE;
import com.epsmart.wzcc.activity.search.QueryDialogListener;
import com.epsmart.wzcc.activity.ui.IndicatorFragmentActivity;
import com.epsmart.wzcc.bean.RequestPram;
import com.epsmart.wzcc.http.BaseHttpModule;
import com.epsmart.wzcc.http.ModuleResponseProcessor;
import com.epsmart.wzcc.http.request.RequestAction;
/**
 * 合同结算情况统计
 *
 */

public class SettleCharAct extends IndicatorFragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar actionBar = activity.getActionBar();
		View view = actionBar.getCustomView();
		ImageView title_image = (ImageView) view.findViewById(R.id.title_image);
		title_image.setBackgroundResource(R.drawable.settle_cs);
		
		ScrollView sv = new ScrollView(activity);
		sv.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		sv.setFillViewport(true);

		LinearLayout lay = new LinearLayout(activity);
		lay.setOrientation(LinearLayout.VERTICAL);
		lay.setBackgroundColor(Color.WHITE);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		sv.addView(lay, params);
		//setContentView(sv);
		search_lay.setOnClickListener(searchLis);
		search_btn.setOnClickListener(searchLis);
		showLinelay = new LinearLayout(activity);
		((LinearLayout) showLinelay).setOrientation(LinearLayout.VERTICAL);
		lay.addView(showLinelay, params);
		initNet();
		loadData(new RequestPram());

	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		app_context.wzdpType=WZDPTYPE.SettleCharAct;
	}


	/** 数据请求 */
	public void loadData(RequestPram requestPram) {
		requestAction = new RequestAction();
		requestAction.reset();
		requestPram.bizId=1004;
		requestPram.password="password";
		requestPram.pluginId=119;
		//requestPram.userName=appContext.user.getUid();
		requestPram.methodName=RequestParamConfig.contructAnlysis;
		requestAction.setReqPram(requestPram);
		super.loadData(requestPram);
	}
	{
		listener = new QueryDialogListener() {
			@Override
			public void doQuery(String req) {
				RequestPram	requestPram=new RequestPram(); 
				requestPram.param = req;
				loadData(requestPram);
				
			}
		};
	}

	class ProcessResponse implements ModuleResponseProcessor {
		@Override
		public void processResponse(BaseHttpModule httpModule, Object parseObj) {
			if (parseObj instanceof CharResponse) {
				mHandler.obtainMessage(0, parseObj).sendToTarget();
			}
		}
	}


}
