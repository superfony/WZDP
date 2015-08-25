package com.epsmart.wzcc.activity.supply.connection;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.supply.approval.ApprovalAdapter;
import com.epsmart.wzcc.activity.supply.approval.BaseFragment;
import com.epsmart.wzcc.activity.supply.approval.UnitInforActivity;
import com.epsmart.wzcc.activity.supply.approval.parcelable.ItemBean;

import java.util.ArrayList;
import java.util.List;
/*
 * 验收明细
 */
public class ConnectionDetailFat extends BaseFragment {
	private View view;
	private ListView listView;
	private ConnectionAdapter connectionAdapter;
	private Activity activity;
	private List<ItemBean> list = new ArrayList<ItemBean>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
				parent.removeView(view);
		}
		view = inflater.inflate(R.layout.connection_detail_list, null);
		initUI();
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
//		reqHttp();
	}

	private void initUI() {
		listView = (ListView) view.findViewById(R.id.connectiondetail_list);
		list=((ConnectionAct)activity).approvalResponse.entity.itemBeansList;
		connectionAdapter = new ConnectionAdapter(activity, list);
		listView.setAdapter(connectionAdapter);
//      listView.setOnItemClickListener(itemOcl);
	}
}
