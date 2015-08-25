package com.epsmart.wzcc.activity.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.RequestParamConfig;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class TestWebServiceObj extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_web_service_obj);
        Log.w("wsdl", "envelope.getResponse()=============");
        try {
            SoapObject request = new SoapObject(RequestParamConfig.serviceNameSpace, RequestParamConfig.testWSDLOBJ);
            // webservice 传入对象对象方式调用

            Log.w("wsdl","RequestParamConfig.serviceNameSpace="+RequestParamConfig.serviceNameSpace);
            Log.w("wsdl","RequestParamConfig.testWSDLOBJ="+RequestParamConfig.testWSDLOBJ);
            PropertyInfo pi = new PropertyInfo();
            Return u = new Return();
            pi.setName("credentials"); //传入的对象名..
            pi.setValue(u);
            pi.setType(u.getClass());
            request.addProperty(pi);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(RequestParamConfig.ServerUrl);
            Log.w("wsdl","RequestParamConfig.ServerUrl="+RequestParamConfig.ServerUrl);
            androidHttpTransport.call(null, envelope);
            Object result = envelope.getResponse();
            Log.v("wsdl", "result=========" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_test_web_service_obj, menu);
        return true;
    }



}
