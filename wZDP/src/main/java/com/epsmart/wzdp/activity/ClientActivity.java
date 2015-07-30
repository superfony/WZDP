package com.epsmart.wzdp.activity;

import org.json.JSONException;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.epsmart.wzdp.aidl.AppInfo;
import com.epsmart.wzdp.aidl.ExecuteServiceAIDL;
import com.epsmart.wzdp.aidl.ICallBack;
import com.epsmart.wzdp.aidl.UserInfo;
import com.epsmart.wzdp.bean.User;
import com.epsmart.wzdp.common.PermissHelp;

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
