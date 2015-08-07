package com.epsmart.wzcc.activity.netbase;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.AppContext;
import com.epsmart.wzcc.activity.RequestParamConfig;
import com.epsmart.wzcc.bean.RequestPram;
import com.epsmart.wzcc.http.BaseHttpModule;
import com.epsmart.wzcc.http.request.HttpException;
import com.epsmart.wzcc.http.response.model.Response;
import com.epsmart.wzcc.http.response.model.StatusEntity;
import com.epsmart.wzcc.http.xml.parser.BaseXmlParser;
import com.epsmart.wzcc.widget.XGCustomProgressDialog;

/**
 * Created by fony on 15-8-5.
 */
public class BaseNetAct extends FragmentActivity {

    protected BaseHttpModule httpModule;
    protected BaseNetAct activity;
    protected RequestPram requestPram;
    protected AppContext appContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        initActionBar();
        initNet();

    }

    protected void initNet() {
        if (httpModule == null) {
            httpModule = new BaseHttpModule(this);
            httpModule.init();
            httpModule.setRequestListener(requestListener);
            httpModule.setServiceNameSpace(RequestParamConfig.serviceNameSpace);
            httpModule.setServiceUrl(RequestParamConfig.ServerUrl);
        }
    }
    public void initActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_main);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        View view = actionBar.getCustomView();
        RelativeLayout exitBt = (RelativeLayout) view.findViewById(R.id.add_lay);

    }

    protected XGCustomProgressDialog progressDialog;
    /* 处理网络错误 */
    protected BaseHttpModule.RequestListener requestListener = new BaseHttpModule.RequestListener() {
        @Override
        public void onSuccess(Response response) {
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
            httpModule.processResponse(httpModule, response,
                    getXmlParser(response), httpModule.getResponseProcess());
        }

        @Override
        public void onFail(Exception e) {
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
            String msg = "未知错误";
            if (e instanceof HttpException) {
                HttpException he = (HttpException) e;
                msg = he.getMessage();
            }

            Message obtainMessage = mHandler.obtainMessage(0);
            obtainMessage.obj = msg;
            obtainMessage.sendToTarget();
        }
    };

    private BaseHandler mHandler = new BaseHandler();

    private class BaseHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(activity, msg.obj + "",
                            Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    StatusEntity se = (StatusEntity) msg.obj;
                    Toast.makeText(activity, se.message + "", Toast.LENGTH_LONG)
                            .show();
                    break;
                default:
                    break;
            }
        }
    }

    protected BaseXmlParser getXmlParser(Response response) {
        response.setResponseAsString(response.getResponseAsString().replace(
                "&", "#"));
        return httpModule.getBaseXmlParser(response,
                httpModule.getParseHandler());
    }




    public void showModuleProgressDialog(String msg) {
        if (progressDialog == null) {
            progressDialog = XGCustomProgressDialog.createDialog(activity);
            progressDialog.setMessage(msg);
        }
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void closeDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            activity.finish();
        }

        return super.onKeyDown(keyCode, event);
    }
}
