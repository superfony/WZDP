package com.epsmart.wzcc.activity.achar;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.RequestParamConfig;
import com.epsmart.wzcc.activity.achar.service.CharResponse;
import com.epsmart.wzcc.activity.search.QueryDialogListener;
import com.epsmart.wzcc.activity.search.QueryScheduleDialogAchar;
import com.epsmart.wzcc.activity.ui.IndicatorFragmentActivity;
import com.epsmart.wzcc.bean.RequestPram;
import com.epsmart.wzcc.http.BaseHttpModule;
import com.epsmart.wzcc.http.ModuleResponseProcessor;
import com.epsmart.wzcc.http.request.RequestAction;

public class ScheduleAct extends IndicatorFragmentActivity {
	protected QueryScheduleDialogAchar queryProDialog;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar actionBar = activity.getActionBar();
		View view = actionBar.getCustomView();
		ImageView title_image = (ImageView) view.findViewById(R.id.title_image);
		title_image.setBackgroundResource(R.drawable.plan_title);
		
		showLinelay = new ScrollView(activity);
		showLinelay.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		((ScrollView) showLinelay).setFillViewport(true);

		search_btn.setVisibility(View.GONE);
//		search_lay.setOnClickListener(searchLisp);
//		search_btn.setOnClickListener(searchLisp);
		initNet();
		loadData(new RequestPram());
	}

	
	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	/** 数据请求 */
	public void loadData(RequestPram requestPram) {
		    requestAction = new RequestAction();
			requestAction.reset();
			requestPram.param = getIntent().getStringExtra("PSPID");
			requestPram.bizId=1004;
			requestPram.password="password";
			requestPram.pluginId=119;
			//requestPram.userName=appContext.user.getUid();
			requestPram.methodName=RequestParamConfig.ehvPlan;
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
	protected OnClickListener searchLisp=new OnClickListener() {
		@Override
		public void onClick(View v) {
			queryProDialog = new QueryScheduleDialogAchar(activity, listener);// TODO
			queryProDialog.show(v);
		}
	};
	class ProcessResponse implements ModuleResponseProcessor {
		@Override
		public void processResponse(BaseHttpModule httpModule, Object parseObj) {
			if (parseObj instanceof CharResponse) {
				mHandler.obtainMessage(0, parseObj).sendToTarget();
			}
		}
	}

}
