package com.epsmart.wzcc.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.more.MoreAct;
import com.epsmart.wzcc.activity.supply.SupplyActivity;
import com.epsmart.wzcc.activity.supply.info.InfoActivity;
import com.epsmart.wzcc.activity.test.Return;
import com.epsmart.wzcc.http.BaseHttpModule;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * 首页显示
 */
public class MainActivity extends ClientActivity {
    private String TAG = MainActivity.class.getName();
    public static String provenance;
    private BaseHttpModule httpModule;
//    private Activity activity;
    private PackageManager pm = null;
    private  ImageButton transfer,ruku,chuku,info,data,setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_main);
       // UpdateManager.getUpdateManager().checkAppUpdate(this, false);// 检查是否更新
        transfer = (ImageButton) findViewById(R.id.transfer);//货物交接
        ruku = (ImageButton) findViewById(R.id.ruku);//验收入库
        chuku = (ImageButton) findViewById(R.id.chuku);//领料出库
        info = (ImageButton) findViewById(R.id.info);//库存信息
        data = (ImageButton) findViewById(R.id.data);//数据同步
        setting = (ImageButton) findViewById(R.id.setting);//系统设置

        //交接
        transfer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(activity,
                        SupplyActivity.class);
                intent.putExtra("tag", "0");
                activity.startActivity(intent);
            }
        });
        // 验收入库
        ruku.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(activity,
                        SupplyActivity.class);
                intent.putExtra("tag", "1");
                activity.startActivity(intent);
            }
        });
       //出库
        chuku.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(activity,
                        SupplyActivity.class);
                intent.putExtra("tag", "2");
                activity.startActivity(intent);
            }
        });



        info.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                				Intent intent = new Intent(activity, InfoActivity.class);
                				activity.startActivity(intent);
//                Common.replaceRightFragment(activity, new InfoFragment(), false,R.id.info_container);
            }
        });

        data.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //				Intent intent = new Intent(activity, .class);
                //				activity.startActivity(intent);
            }
        });

        setting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                				Intent intent = new Intent(activity, MoreAct.class);
                				activity.startActivity(intent);
            }
        });

//      new Thread(new Runnable() {
//          @Override
//          public void run() {
//              reqestWSDL();
//          }
//      }).start();

    }


    public void reqestWSDL(){
        Log.w("wsdl", "envelope.getResponse()=============");
        try {
            SoapObject request = new SoapObject(RequestParamConfig.serviceNameSpace, RequestParamConfig.testWSDLOBJ);
            // webservice 传入对象对象方式调用
            Log.w("wsdl","RequestParamConfig.serviceNameSpace="+RequestParamConfig.serviceNameSpace);
            Log.w("wsdl","RequestParamConfig.testWSDLOBJ="+RequestParamConfig.testWSDLOBJ);
            PropertyInfo pi = new PropertyInfo();
            Return u = new Return();
            u.setTYPE("126789");
            pi.setName("re"); //传入的对象名..
            pi.setValue(u);
            pi.setType(u.getClass());
            request.addProperty(pi);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER10);
            envelope.bodyOut = request;
           // envelope.addMapping(RequestParamConfig.serviceNameSpace,"Return",u.getClass());
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(RequestParamConfig.ServerUrl);

            androidHttpTransport.debug = true;
            Log.w("wsdl", "RequestParamConfig.ServerUrl=" + RequestParamConfig.ServerUrl);
            androidHttpTransport.call(null, envelope);
            Log.w("wsdl", "...........");
            SoapObject result = (SoapObject)envelope.getResponse();
            Log.w("wsdl", "result=========" +(result instanceof  SoapObject));
            Log.w("wsdl", "result=========" +result.getName()+"typevalue="+result.getProperty("TYPE"));
        } catch (Exception e) {
            Log.w("wsdl",".....e......"+e);
            e.printStackTrace();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            dialog();
            return false;
        }
        return false;

    }

    // 退出弹出框
    Dialog dialog;

    private void dialog() {

        LayoutInflater inflater = LayoutInflater.from(activity);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(
                R.layout.exit_dialog, null);

        dialog = new AlertDialog.Builder(activity).create();
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setContentView(layout);

        Button exit_cancel = (Button) layout.findViewById(R.id.exit_cancel);
        Button drop_out = (Button) layout.findViewById(R.id.drop_out);

        drop_out.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                activity.finish();
                //TODO
            }
        });
        exit_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

//
//    public static  Object GetWebServiceData(String iNameSpace,
//                                            String iWebserviceURL, String iSoapAction, String iMethodName,
//                                            PropertyInfo[] iPropertyInfo) {
//        Object result = null;
//        try {
//            SoapObject rpc = new SoapObject(iNameSpace, iMethodName);
//            for (int i = 0; i < iPropertyInfo.length; i++) {
//                rpc.addProperty(iPropertyInfo[i]);
//
//
//            }
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//                    SoapEnvelope.VER11);
//            envelope.bodyOut = rpc;
//
//            envelope.dotNet = true;
//            envelope.setOutputSoapObject(rpc);
//            HttpTransportSE ht = new HttpTransportSE(iWebserviceURL, 5000);
//            ht.debug = true;
//            ht.call(iSoapAction, envelope);
//            result = envelope.getResponse();
//        } catch (Exception e) {
////            Toast.makeText(CrashApplication.getContext(), "连接服务器失败,请检查网络设置！", Toast.LENGTH_SHORT)
////                    .show();
//        }
//
//        return result;
//    }



}
