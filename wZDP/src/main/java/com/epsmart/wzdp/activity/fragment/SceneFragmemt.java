package com.epsmart.wzdp.activity.fragment;

import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.epsmart.wzdp.R;
import com.epsmart.wzdp.activity.CommonActivity;
import com.epsmart.wzdp.common.Common;
import com.epsmart.wzdp.common.PermissHelp;
/**
 *@author hqq
 */

public class SceneFragmemt extends CommonFragment implements
		OnLeftMenuSelectedListener {

	@Override
	public void onMenuSelected(FragmentActivity activity, int position) {
		clearStack();
		switch (position) {
		case 0:
			if(!PermissHelp.isPermiss("016")){
				PermissHelp.showToast(activity);
				return;
			}
			CommonActivity.state=3;
			break;
		case 1:
			if(!PermissHelp.isPermiss("017")){
				PermissHelp.showToast(activity);
				return;
			}
			CommonActivity.state=4;
			break;
		case 2:
			if(!PermissHelp.isPermiss("018")){
				PermissHelp.showToast(activity);
				return;
			}
			CommonActivity.state=5;
			break;
		default:
			break;
		
	}
		
	}

	

	@Override
	public void onSupplyTopMenuSelected(FragmentActivity activity, int position) {
		
		
	}
	//清除栈空间
	private void clearStack() {
		int backStackCount = getFragmentManager().getBackStackEntryCount();
			Log.i("SceneFragment", "......backStackCount......"+backStackCount);
			for(int i = 0;i < backStackCount; i++){
				getFragmentManager().popBackStack();
			}
	}
	
	//栈底保留两个
	public void clearStackOther(){
		int backStackCount = getFragmentManager().getBackStackEntryCount();
			Log.i("SceneFragment", ".....backStackCount...."+backStackCount);
			for(int i = 2; i < backStackCount; i++){
				getFragmentManager().popBackStack();
			}
	}
	
	
}






