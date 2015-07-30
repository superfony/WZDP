package com.epsmart.wzcc.activity.achar.service;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.epsmart.wzcc.activity.achar.CommonBarAct;
import com.epsmart.wzcc.activity.achar.FragmentCharShow;
import com.epsmart.wzcc.activity.achar.SelectPicPopupWindow;
import com.epsmart.wzcc.activity.search.QueryDialogListener;
import com.epsmart.wzcc.activity.supply.bean.BasicEntity;
import com.epsmart.wzcc.activity.ui.IndicatorFragmentActivity;
import com.epsmart.wzcc.activity.ui.IndicatorFragmentActivity.MyAdapter;
import com.epsmart.wzcc.activity.ui.IndicatorFragmentActivity.TabInfo;
import com.epsmart.wzcc.bean.RequestPram;
import com.epsmart.wzcc.http.BaseHttpModule;
import com.epsmart.wzcc.http.ModuleResponseProcessor;
import com.epsmart.wzcc.http.request.BaseRequest.RequestType;
import com.epsmart.wzcc.http.request.RequestAction;

/**
 * 报表显示的基类
 */

@SuppressLint("NewApi")
public class BaseShowCharAct extends CommonBarAct {
	// 显示区域
	protected View showLinelay;
	protected String title;
	protected RequestAction requestAction;
	
	protected ArrayList<TabInfo> mTabs = new ArrayList<IndicatorFragmentActivity.TabInfo>();
	// 自定义的弹出框类
	SelectPicPopupWindow menuWindow;
	// viewpager
	protected ViewPager mPager;

	// viewpager adapter
	protected MyAdapter myAdapter = null;
	
	protected LinearLayout bottom_btns;
	protected Button download_btn;// 下载按钮
	protected Button select_btn;// 选择按钮
	protected Button upload_btn;// 上传按钮

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	
	
	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		return super.onCreateView(name, context, attrs);
	}



	@Override
	protected void onResume() {
		super.onResume();
	}

	{
		listener = new QueryDialogListener() {
			@Override
			public void doQuery(String req) {
				loadData(new RequestPram());
			}
		};
	}

	/** 数据请求 */
	public void loadData(RequestPram requestPram) {
		showModuleProgressDialog("提示", "数据请求中请稍后...");
		httpModule.executeRequest(requestAction, new CharXmlHandler(),
				new ProcessResponse(), RequestType.THRIFT);
	}
	
	class ProcessResponse implements ModuleResponseProcessor {
		@Override
		public void processResponse(BaseHttpModule httpModule, Object parseObj) {
			if (parseObj instanceof CharResponse) {
				mHandler.obtainMessage(0, parseObj).sendToTarget();
			}
		}
	}
	protected FlowHandler mHandler = new FlowHandler();
	public class FlowHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0) {
				onSuccess((CharResponse) msg.obj);
			}
			closeDialog();
		}
	}
	
	
	
  /**
   *表格返回数据的处理
   * @param charResponse
   */
	private ArrayList<BasicEntity> list;
	private void onSuccess(CharResponse charResponse) {
		WindowManager wm = activity.getWindowManager();
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		AbstractChart charShow = null;
		 list = charResponse.entityList;
		//((ViewGroup)showLinelay).removeAllViews();
		mTabs.clear();
		for (int i = 0; i < list.size(); i++) {
			BasicEntity entiry = list.get(i);
			String charType = entiry.fields.get("chartype").fieldContent;
			title = entiry.fields.get("title").fieldContent;
			if (charType.equals("linechar")) {
				charShow = new LineCharShow(activity, title);
			} else if (charType.equals("barchar")) {
				charShow = new BarCharShow(activity, title);

			} else if (charType.equals("table")) {
				charShow = new ProTableViewShow(activity, title);
			}
			charShow.execute(entiry);
			entiry=null;
			FragmentCharShow fragment = new FragmentCharShow(charShow.getview());
			mTabs.add(new TabInfo(i, title, fragment));
		}
		if (myAdapter == null) {
			initViews(mTabs);
		} else {
			myAdapter.notifyDataSetChanged();
		}
	}
	// 
	protected void initViews(ArrayList<TabInfo> mTabs) {

	}

	protected void clearAll() {

	}
	
	protected void initViews(FragmentCharShow fragment){
		
		
	}

	OnClickListener itemsOnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {

		}
	};

}
