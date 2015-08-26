package com.epsmart.wzcc.activity.supply.info;

import android.os.Bundle;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.netbase.BaseNetAct;
import com.epsmart.wzcc.common.Common;

/**
 * Created by Administrator on 2015-08-26.
 */
public class InfoActivity extends BaseNetAct{


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            activity = this;
            setContentView(R.layout.approval);
            Common.replaceRightFragment(activity, new InfoFragment(), false, R.id.info_container);
        }
}
