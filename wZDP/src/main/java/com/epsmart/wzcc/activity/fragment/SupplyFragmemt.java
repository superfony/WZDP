package com.epsmart.wzcc.activity.fragment;

import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.CommonActivity;
import com.epsmart.wzcc.activity.supply.fragment.ItemFragment;
import com.epsmart.wzcc.activity.supply.fragment.PointFragment;
import com.epsmart.wzcc.activity.supply.fragment.ProductionFragment;
import com.epsmart.wzcc.common.Common;
import com.epsmart.wzcc.common.PermissHelp;


public class SupplyFragmemt extends CommonFragment implements
		OnLeftMenuSelectedListener {

	@Override 
	public void onMenuSelected(FragmentActivity activity, int position) {
		 clearStack();
		CommonActivity.state=position;
		switch (position) {
		case 0:
			// 生产过程管理
			if(!PermissHelp.isPermiss("003")){
				PermissHelp.showToast(activity);
				return;
			}
			Common.replaceRightFragment(activity, new ProductionFragment(), false,R.id.fragment_right_container);
			break;
		case 1:
			// 质量监督管理
			if(!PermissHelp.isPermiss("004")){
				PermissHelp.showToast(activity);
				return;
			}
			Common.replaceRightFragment(activity, new ItemFragment(), false,R.id.fragment_right_container);
			break;
		case 2:
			// 关键点见证
			if(!PermissHelp.isPermiss("005")){
				PermissHelp.showToast(activity);
				return;
			}
			Common.replaceRightFragment(activity, new PointFragment(), false,R.id.fragment_right_container);

			break;
		default:
			break;
		
	}
		
	}

	@Override
	public void onSupplyTopMenuSelected(FragmentActivity activity, int position) {

	}
	/* 清除栈空间*/
	public void clearStack(){
		 int backStackCount = getFragmentManager().getBackStackEntryCount();
			Log.i("SupplyFragment", ".......clearStack..........>>="+backStackCount);
	        for(int i = 0; i < backStackCount; i++) {    
	            getFragmentManager().popBackStack();
	        }
	}
	
	/* 栈底保留两个*/
	public void clearStackOther(){
		 int backStackCount = getFragmentManager().getBackStackEntryCount();
			Log.i("SupplyFragment", ".....clearStackOther.......>>="+backStackCount);
	        for(int i = 2; i < backStackCount; i++) {    
	            getFragmentManager().popBackStack();
	        }
	}
	
	
}