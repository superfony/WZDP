package com.epsmart.wzcc.activity.supply;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.supply.fragment.EquipmentFragment;
import com.epsmart.wzcc.activity.supply.fragment.ItemFragment;
import com.epsmart.wzcc.activity.supply.fragment.PointFragment;
import com.epsmart.wzcc.activity.supply.fragment.ProductionFragment;
import com.fony.menu.widget.slidingmenu.fragment.SlidingMenuFragment;

/**
 * 
 * @author fony sdingmenu菜单 切换的实现
 * 
 */

public class SupplyMenuFragment extends SlidingMenuFragment {
	private View contextView = null;
	private LayoutInflater inflater = null;
	public ImageView transfer_left;
	public ImageView ruku_left;
	public ImageView chuku_left;
	private Fragment newContent = null;

	public void setNewContent(Fragment newContent) {
		this.newContent = newContent;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		this.inflater = inflater;
		contextView = inflater.inflate(R.layout.slide_menu_support, container,
				false);

		transfer_left = (ImageView) contextView.findViewById(R.id.transfer_left);
		ruku_left = (ImageView) contextView.findViewById(R.id.ruku_left);
		chuku_left = (ImageView) contextView.findViewById(R.id.chuku_left);

		if (newContent instanceof ProductionFragment) {
			ruku_left.setBackgroundResource(R.drawable.sr_ruku_down);
		} else if (newContent instanceof ItemFragment) {
			transfer_left.setBackgroundResource(R.drawable.sr_transfer_down);
		} else if (newContent instanceof EquipmentFragment) {
			chuku_left.setBackgroundResource(R.drawable.sr_chuku_down);
		}

		ruku_left.setOnClickListener(click_image);
		transfer_left.setOnClickListener(click_image);
		chuku_left.setOnClickListener(click_image);
		return contextView;
	}

	OnClickListener click_image = new OnClickListener() {
		@Override
		public void onClick(View v) {

			int id = v.getId();
			if (id == R.id.ruku_left) {
				newContent = new ProductionFragment();
				ruku_left.setBackgroundResource(R.drawable.sr_ruku_down);
				transfer_left.setBackgroundResource(R.drawable.sr_transfer_up);
				chuku_left.setBackgroundResource(R.drawable.sr_chuku_up);
			} else if (id == R.id.transfer_left) {
				newContent = new ItemFragment();
				transfer_left.setBackgroundResource(R.drawable.zl_down);
				ruku_left.setBackgroundResource(R.drawable.sr_ruku_up);
				chuku_left.setBackgroundResource(R.drawable.sr_chuku_up);
			} else if (id == R.id.chuku_left) {
				newContent = new PointFragment();
				chuku_left.setBackgroundResource(R.drawable.sr_chuku_down);
				ruku_left.setBackgroundResource(R.drawable.sr_ruku_down);
				transfer_left.setBackgroundResource(R.drawable.sr_transfer_down);
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
