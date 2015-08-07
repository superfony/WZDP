package com.epsmart.wzcc.activity.supply.approval;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.epsmart.wzcc.R;


/*
 * 验收明细
 */
public class StatisticalFat extends BaseFragment {
	private View view;
	private ListView listView;
//	private TargetAdapter targetAdapter;
//	private Activity activity;
//	private List<Target> list = new ArrayList<Target>();
//	private String type;
//	private UnitTarget resp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
				parent.removeView(view);
		}
		view = inflater.inflate(R.layout.stat_list, null);

		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
//		initUI();
//		reqHttp();
	}

	/*private void initUI() {
		listView = (ListView) view.findViewById(R.id.stat_jz);
//		targetAdapter = new TargetAdapter(activity, list);
//		listView.setAdapter(targetAdapter);
		listView.setOnItemClickListener(itemOcl);
	}

	OnItemClickListener itemOcl = new OnItemClickListener() {
		@SuppressLint("NewApi")
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent();
			intent.putExtra("unitid",((UnitInforActivity) getActivity()).unitid);
			type = resp.list.get(arg2).targetename;
			intent.putExtra("type", type);
			intent.putExtra("title",resp.list.get(arg2).targetchname);
			 if("overhaul".equals(type)){
				 intent.setClass(getActivity(), OverhaulAct.class);
				 intent.setAction(((UnitInforActivity) getActivity()).unitname);
					startActivity(intent);
			 }else {
				 intent.setClass(getActivity(), CounttgAct.class);
					startActivity(intent);
			}
		}
	};

	@SuppressLint("NewApi")
	private void reqHttp() {
		RequestParams params = new RequestParams();
		params.put("unitId", ((UnitInforActivity) getActivity()).unitid);// 机组标号
		startProgressDialog("数据加载中......");
		http.post(UrlConfig.unitzblist, params, new TargetHandler(), processor);
	}

	ResponseProcessor processor = new ResponseProcessor() {
		@Override
		public void onFailure(Throwable e, String reason) {
			stopProgressDialog();
			if (null == reason || "".equals(reason)) {
				reason = "机组指标列表获取失败";
			}
			ToastHelper.toast(activity, reason);
		}

		@Override
		public void processResponse(String response, Object data) {
			stopProgressDialog();
			 resp = (UnitTarget) data;
			System.out.println("指标工况信息 response=" + response);
			if ("1".equals(resp.requestcode)) {
				list = resp.list;
				handler.sendEmptyMessage(0);
			}
		}
	};

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			targetAdapter.updateListView(list);

		}
	};*/
}
