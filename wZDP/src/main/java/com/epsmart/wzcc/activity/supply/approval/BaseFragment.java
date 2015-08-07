package com.epsmart.wzcc.activity.supply.approval;

import android.annotation.SuppressLint;
import com.epsmart.wzcc.R;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.epsmart.wzcc.widget.XGCustomProgressDialog;


@SuppressLint("NewApi")
public class BaseFragment extends Fragment {
	private XGCustomProgressDialog progressDialog = null;


	protected Activity activity;
	protected  ImageView title_image;

	protected RelativeLayout.LayoutParams layoutParams;

	@SuppressLint("NewApi")
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity=activity;
	}
	

	@SuppressLint("NewApi")
	protected void startProgressDialog(String msg) {
		if (progressDialog == null) {
			progressDialog = XGCustomProgressDialog.createDialog(getActivity());
			progressDialog.setMessage(msg);
		}
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
	}

	protected void stopProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}




	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

		}
	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ActionBar actionBar = activity.getActionBar();
		View view = actionBar.getCustomView();
		title_image = (ImageView) view.findViewById(R.id.title_image);
	}


}
