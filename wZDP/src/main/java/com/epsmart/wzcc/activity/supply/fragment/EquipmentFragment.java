package com.epsmart.wzcc.activity.supply.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.PerferenceModel;
import com.epsmart.wzcc.activity.RequestParamConfig;
import com.epsmart.wzcc.activity.achar.service.CharResponse;
import com.epsmart.wzcc.activity.fragment.SupplyFragmemt;
import com.epsmart.wzcc.activity.supply.bean.BasicEntity;
import com.epsmart.wzcc.activity.supply.bean.BasicResponse;
import com.epsmart.wzcc.activity.supply.bean.BasicResponseNew;
import com.epsmart.wzcc.activity.supply.bean.Field;
import com.epsmart.wzcc.activity.supply.fragment.parser.BasicResponseHandlerNew;
import com.epsmart.wzcc.bean.RequestPram;
import com.epsmart.wzcc.common.RequestXmlHelp;
import com.epsmart.wzcc.http.BaseHttpModule;
import com.epsmart.wzcc.http.ModuleResponseProcessor;
import com.epsmart.wzcc.http.request.BaseRequest.RequestType;
import com.epsmart.wzcc.http.request.RequestAction;
import com.epsmart.wzcc.view.MyHScrollView;
import com.epsmart.wzcc.view.MyHScrollView.OnScrollChangedListener;

/*
 * 关键点见证Fragment
 */
public class EquipmentFragment extends SupplyFragmemt {
	// private Activity activity;
	private View view;
	// 初始化分页标签
	private String TAG = EquipmentFragment.class.getName();
	private ListView mListView1;//
	private RelativeLayout mHead;//
	private LinearLayout title_relalay;
	private MyAdapter myAdapter;
	private HorizontalScrollView headSrcrollView;
	private List<String[]> als = new ArrayList<String[]>();
	private List<String> uploadFiles;
	private String templetaddress;
	private File file;
	private TextView tv;
	private TextView tv01;
	private BasicEntity entiry;
	protected List<String[]> list1 = null;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.supply_equipment, container, false);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ActionBar actionBar = activity.getActionBar();
		View view = actionBar.getCustomView();
		ImageView title_image = (ImageView) view.findViewById(R.id.title_image);
		title_image.setBackgroundResource(R.drawable.equipment_cs);
		
		initview();
		init();
	}
	
	public void initview() {
		mHead = (RelativeLayout) getView().findViewById(R.id.head);
		mHead.setFocusable(true);
		mHead.setClickable(true);
		mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		mListView1 = (ListView) getView().findViewById(R.id.listView1);
		mListView1.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		myAdapter = new MyAdapter(getActivity(), R.layout.list_item, als);
		mListView1.setAdapter(myAdapter);
		
	}

	private void init() {
		headSrcrollView = (HorizontalScrollView) mHead
				.findViewById(R.id.horizontalScrollView1);
		headSrcrollView.setHorizontalFadingEdgeEnabled(false);
		headSrcrollView.scrollTo(0, 0);

		// 设置行标题的
		tv01 = (TextView) mHead.findViewById(R.id.textView01);

		title_relalay = (LinearLayout) mHead.findViewById(R.id.title_relalay);
	}

	class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {
		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			try {
				headSrcrollView.onTouchEvent(arg1);
			} catch (Exception e) {
			}
			return false;
		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		search_lay.setVisibility(View.VISIBLE);
		search_lay.setOnClickListener(searchLis);
		search_btn.setOnClickListener(searchLis);
	}

	/** 请求模板路径 */
	public void loadDataDown(RequestPram requestPram) {
		showModuleProgressDialog("提示", "获取模板地址...");
		RequestAction requestAction = new RequestAction();
		requestAction.reset();
		requestPram.param = RequestXmlHelp.getCommonXML(RequestXmlHelp
				.getReqXML("plan", "7"));

		requestPram.bizId = 1004;
		requestPram.password = "password";
		requestPram.pluginId = 119;
		String uid = PerferenceModel.getPM(activity).getValue(
				"uuid_my", "");
		requestPram.userid = uid;
		requestPram.methodName = RequestParamConfig.ehvTempDownload;
		requestAction.setReqPram(requestPram);
		// super.loadData(requestPram);
		httpModule.executeRequest(requestAction, new BasicResponseHandlerNew(),
				new ProcessResponse(), RequestType.THRIFT);
	}

	class ProcessResponse implements ModuleResponseProcessor {
		@Override
		public void processResponse(BaseHttpModule httpModule, Object parseObj) {
			closeDialog();
			if (parseObj instanceof BasicResponseNew) { // 请求文件下载路径
				mHandler.obtainMessage(0, parseObj).sendToTarget();
			} else if (parseObj instanceof BasicResponse) {
				mHandler.obtainMessage(1, parseObj).sendToTarget();
			} else if (parseObj instanceof CharResponse) { // 请求表格内容
				mHandler.obtainMessage(3, parseObj).sendToTarget();
			}
		}

	}

	private FlowHandler mHandler = new FlowHandler();
	private BasicResponse response;
	private BasicResponseNew responseNew;

	private class FlowHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Log.d("", ".......msg.what..>>" + msg.what);
			if (msg.what == 0) {// 请求文件下载路径
			// downTemp((BasicResponseNew) msg.obj);
			} else if (msg.what == 1) {// 返回数据为空 或 查询异常信息
				onSuccess((BasicResponse) msg.obj);
			} else if (msg.what == 3) {
				onSuccessTable((CharResponse) msg.obj);
			}
		}
	}

	// 查询
	protected OnClickListener searchLisp = new OnClickListener() {
		@Override
		public void onClick(View v) {

		}
	};

	public class MyAdapter extends BaseAdapter {
		public List<ViewHolder> mHolderList = new ArrayList<ViewHolder>();

		int id_row_layout;
		LayoutInflater mInflater;
		private List<String[]> list;
		private Context context;

		public MyAdapter(Context context, int id_row_layout, List<String[]> list) {
			super();
			this.id_row_layout = id_row_layout;
			this.list = list;
			mInflater = LayoutInflater.from(context);
			this.context = context;

		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parentView) {
			ViewHolder holder = null;
			String[] svalue = list.get(position);
			if (convertView == null) {
				convertView = mInflater.inflate(id_row_layout, null);

				LinearLayout linlay = (LinearLayout) convertView
						.findViewById(R.id.value_linlay);
				holder = new ViewHolder(svalue.length, linlay, context);
				holder.txt1 = (TextView) convertView
						.findViewById(R.id.textView1);
				MyHScrollView scrollView1 = (MyHScrollView) convertView
						.findViewById(R.id.listHorizontalScrol);
				holder.scrollView = scrollView1;

				((MyHScrollView) headSrcrollView)
						.AddOnScrollChangedListener(new OnScrollChangedListenerImp(
								scrollView1));
				convertView.setTag(holder);

				mHolderList.add(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (position % 2 == 0) {
				convertView.setBackgroundColor(Color.WHITE);
			} else {
				convertView.setBackgroundColor(Color.parseColor("#ffddac"));
			}

			final String[] args = list.get(position);
			holder.txt1.setText(args[0]);
			holder.putvalue(args);
			return convertView;
		}

		class OnScrollChangedListenerImp implements OnScrollChangedListener {
			MyHScrollView mScrollViewArg;

			public OnScrollChangedListenerImp(MyHScrollView scrollViewar) {
				mScrollViewArg = scrollViewar;
			}

			@Override
			public void onScrollChanged(int l, int t, int oldl, int oldt) {
				mScrollViewArg.smoothScrollTo(l, t);
			}
		}

		class ViewHolder {
			TextView txt1;
			TextView[] textViewArray;
			HorizontalScrollView scrollView;

			ViewHolder(int length, LinearLayout linearlay, Context context) {
				textViewArray = new TextView[length];
				TextView tv = null;
				LinearLayout.LayoutParams row_lp = new LinearLayout.LayoutParams(
						190, 56);

				for (int i = 1; i < length; i++) {
					tv = new TextView(context);
					linearlay.addView(tv, row_lp);
					tv.setTextSize(15);
					textViewArray[i - 1] = tv;

				}
			}

			public void putvalue(String[] args) {
				for (int i = 1; i < args.length; i++) {
					textViewArray[i - 1].setText(args[i]);

				}
			}
		}
	}

	/**
	 * 绘制图表
	 * 
	 * @param response
	 */
	private ArrayList<BasicEntity> list;

	protected List<String[]> getStringList(String str) {
		List<String[]> values = new ArrayList<String[]>();
		if (str == null)
			return null;

		String array[] = str.split("#");
		for (int i = 0; i < array.length; i++) {
			values.add((array[i] + " ").split("@\\*"));
		}
		return values;
	}

	private void onSuccessTable(CharResponse charResponse) {

		String title = null;
		if (null == charResponse.entityList
				|| charResponse.entityList.size() < 1) {
//			Toast.makeText(this, "无数据！！", Toast.LENGTH_SHORT).show();
			return;
		}
		list = charResponse.entityList;
		entiry = list.get(0);
		title = entiry.fields.get("title").fieldContent;
		tv.setText(title);
		tv.setTextSize(18);

		// 获取行标题
		String[] lineTitle = null;
		// 画表格
		Field field = null;
		LinearLayout.LayoutParams row_lp = new LinearLayout.LayoutParams(190,
				56);

		field = entiry.fields.get("lineTitle");// 报表用到
		if (field != null) {
			lineTitle = field.fieldContent.split(",");
		}
		tv01.setText(lineTitle[0]);
		title_relalay.removeAllViews();
		for (int i = 1; i < lineTitle.length; i++) {
			TextView tv = new TextView(getActivity());
			tv.setText(lineTitle[i]);
			tv.setTextSize(15);
			title_relalay.addView(tv, row_lp);
		}
		field = entiry.fields.get("yValue");// 报表用到
		list1 = getStringList(field.fieldContent);
		Log.d("HossoolList", ".........t z g z ..");
		als.clear();
		als.addAll(list1);
		myAdapter.notifyDataSetChanged();

	}

	private void onSuccess(BasicResponse response) {
		Toast.makeText(activity, response.message, Toast.LENGTH_LONG).show();
	}

	// private void downTemp(BasicResponseNew response) {
	// closeDialog();
	// this.responseNew = response;
	// if (null == response.basicEntityList
	// || response.basicEntityList.size() < 1)
	// return;
	// templetaddress = response.basicEntityList.get(0).fields
	// .get("templetaddress").fieldContent;
	// final String filePath = Environment.getExternalStorageDirectory()
	// .getAbsolutePath();
	// if (TextUtils.isEmpty(templetaddress))
	// templetaddress =
	// "/opt/Tomcat6/webapps/wzdp/wzdp/app/downloadApp/Testdown.xls";
	// }
}
