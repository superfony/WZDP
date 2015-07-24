package com.epsmart.wzdp.activity.fragment;

import android.support.v4.app.FragmentActivity;
/**
 * @author fony
 *
 */

public interface OnLeftMenuSelectedListener {
	void onSupplyTopMenuSelected(FragmentActivity activity, int position);
	void onMenuSelected(FragmentActivity activity, int position);
}
