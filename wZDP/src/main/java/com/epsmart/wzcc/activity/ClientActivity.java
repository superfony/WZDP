package com.epsmart.wzcc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

/**
 * 作为基类的ClientActivity
 */

public class ClientActivity extends Activity {
    public static final String TAG = "ClientActivity";


    private Handler handler;
    protected AppContext appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext = (AppContext) getApplication();
    }


}
