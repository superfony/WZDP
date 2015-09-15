package com.epsmart.wzcc.activity.supply.connection;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.supply.approval.BaseFragment;
import com.epsmart.wzcc.activity.supply.approval.BasicInfoFat;
import com.epsmart.wzcc.activity.supply.approval.StatisticalFat;
import com.epsmart.wzcc.common.Common;

/**
 * 交接明细、验收信息
 */

public class ConnectionFragment extends BaseFragment {
	private View view;
	// 验收信息
	private Button basic_info;
	// 验收明细
	private Button stat_info;
	private String TAG = "ConnectionFragment";

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
				parent.removeView(view);
		}
		try {
			view = inflater.inflate(R.layout.connection_menu, container, false);
		} catch (Exception e) {

		}
		Common.replaceRightFragment(getActivity(), new ConnectionHeadinfoFat(), false,
				R.id.info_container);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		basic_info = (Button) view.findViewById(R.id.basic_info);
		stat_info = (Button) view.findViewById(R.id.statistical_info);
		basic_info.setEnabled(false);
		basic_info.setOnClickListener(frameBtnClick(basic_info));
		stat_info.setOnClickListener(frameBtnClick(stat_info));
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	private View.OnClickListener frameBtnClick(final Button btn) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				if (btn == basic_info) {
					basic_info.setEnabled(false);
					Common.replaceRightFragment(getActivity(),
							new ConnectionHeadinfoFat(), false, R.id.info_container);
				} else {
					basic_info.setEnabled(true);
				}
				if (btn == stat_info) {
					stat_info.setEnabled(false);
					Common.replaceRightFragment(getActivity(),
							new ConnectionDetailFat(), false, R.id.info_container);
				} else {
					stat_info.setEnabled(true);
				}
			}
		};
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		title_image.setBackgroundResource(R.drawable.connection_title);
	}

}
