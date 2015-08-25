package com.epsmart.wzcc.activity.supply;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.supply.approval.ApprovalFragment;
import com.epsmart.wzcc.activity.supply.approval.parcelable.ApprovalResponse;
import com.epsmart.wzcc.activity.supply.connection.ConnectionFat;
import com.epsmart.wzcc.activity.supply.fragment.EquipmentFragment;
import com.epsmart.wzcc.activity.supply.fragment.ItemFragment;
import com.epsmart.wzcc.activity.supply.fragment.PointFragment;
import com.epsmart.wzcc.activity.supply.fragment.ProductionFragment;
import com.epsmart.wzcc.activity.supply.leavebase.LeavebaseFragment;
import com.fony.menu.widget.slidingmenu.fragment.SlidingMenuFragment;

/**
 * 
 * @author fony sdingmenu菜单 切换的实现
 * 
 */

public class SupplyMenuFragment extends SlidingMenuFragment {
	private View contextView = null;
	private LayoutInflater inflater = null;
	public ImageView approval_left;
	public ImageView connection_left;
	public ImageView wareout_left;
	private Fragment newContent = null;

	public void setNewContent(Fragment newContent) {
		this.newContent = newContent;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		this.inflater = inflater;
		contextView = inflater.inflate(R.layout.slide_menu_support, container,
				false);


		connection_left = (ImageView) contextView.findViewById(R.id.connection_left);
		approval_left = (ImageView) contextView.findViewById(R.id.approval_left);
		wareout_left = (ImageView) contextView.findViewById(R.id.wareout_left);
		if (newContent instanceof ApprovalFragment) {
			approval_left.setBackgroundResource(R.drawable.sr_ruku_down);

		} else if (newContent instanceof ConnectionFat) {
			connection_left.setBackgroundResource(R.drawable.sr_transfer_down);
		} else if (newContent instanceof LeavebaseFragment) {
			wareout_left.setBackgroundResource(R.drawable.sr_chuku_down);
		}
		connection_left.setOnClickListener(click_image);
		approval_left.setOnClickListener(click_image);
		wareout_left.setOnClickListener(click_image);
		return contextView;
	}

	OnClickListener click_image = new OnClickListener() {
		@Override
		public void onClick(View v) {

			int id = v.getId();
			if (id == R.id.approval_left) {
				newContent = new ApprovalFragment();// 验收
				approval_left.setBackgroundResource(R.drawable.sr_ruku_down);
				connection_left.setBackgroundResource(R.drawable.sr_transfer_up);
				wareout_left.setBackgroundResource(R.drawable.sr_chuku_up);
			} else if (id == R.id.connection_left) {//  交接
				newContent = new ConnectionFat();
				connection_left.setBackgroundResource(R.drawable.sr_transfer_down);
				approval_left.setBackgroundResource(R.drawable.sr_ruku_up);
				wareout_left.setBackgroundResource(R.drawable.sr_chuku_up);
			} else if (id == R.id.wareout_left) {
				newContent = new LeavebaseFragment();
				approval_left.setBackgroundResource(R.drawable.sr_ruku_up);
				connection_left.setBackgroundResource(R.drawable.sr_transfer_up);
				wareout_left.setBackgroundResource(R.drawable.sr_chuku_down);
			}

			if (newContent != null)
				switchFragment(newContent);
		}
	};

	public void setActivity(SupplyActivity mActivity) {
		this.mActivity = mActivity;
	}

	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof SupplyActivity) {
			SupplyActivity fca = (SupplyActivity) getActivity();
			fca.switchContent(fragment);
		}
	}

}
