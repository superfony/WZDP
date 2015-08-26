package com.epsmart.wzcc.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

/**
 * 作为基类的ClientActivity
 */

public class ClientActivity extends FragmentActivity {
    public static final String TAG = "ClientActivity";

    protected ClientActivity activity;
    private Handler handler;
    protected AppContext appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext = (AppContext) getApplication();
    }


}
