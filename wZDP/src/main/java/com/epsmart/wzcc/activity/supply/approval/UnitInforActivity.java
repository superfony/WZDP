package com.epsmart.wzcc.activity.supply.approval;

import android.os.Bundle;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.netbase.BaseNetAct;
import com.epsmart.wzcc.common.Common;

/**
 *  验收入库详情信息
 */
public class UnitInforActivity extends BaseNetAct {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity=this;
		setContentView(R.layout.approval);
		Common.replaceRightFragment(activity, new UnitInforFragment(), false, R.id.qty_m);
	}
}
