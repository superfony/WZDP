package com.epsmart.wzcc.activity.supply.service;

import android.view.View;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.CommonActivity;
import com.epsmart.wzcc.common.Constants;
import com.epsmart.wzcc.service.CommonService;

public class SupplyQualityService extends CommonService {

	public SupplyQualityService(CommonActivity activity, View view) {
		super(activity, view);
	}
	
	
	public void setTopTextViewBackgroundColor(View clickedView) {

		Constants.supplyTopTextView.setBackgroundDrawable(activity.getResources().getDrawable(
				R.drawable.selector_sidebar_button));
		Constants.supplyTopTextView.setFocusable(true);
		Constants.supplyTopTextView.setClickable(true);

		clickedView.setBackgroundDrawable(activity.getResources().getDrawable(
				R.drawable.sidebar_button_hover));
		clickedView.setFocusable(false);
		clickedView.setClickable(false);
		Constants.supplyTopTextView = clickedView;
	}
	

}
