package com.epsmart.wzcc.activity.supply.connection;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.RequestParamConfig;
import com.epsmart.wzcc.activity.supply.approval.BaseFragment;
import com.epsmart.wzcc.activity.supply.approval.UnitInforActivity;
import com.epsmart.wzcc.activity.supply.approval.parcelable.HeadBean;
import com.epsmart.wzcc.bean.RequestPram;
import com.epsmart.wzcc.common.RequestXmlHelp;
import com.epsmart.wzcc.http.BaseHttpModule;
import com.epsmart.wzcc.http.ModuleResponseProcessor;
import com.epsmart.wzcc.http.request.BaseRequest;
import com.epsmart.wzcc.http.request.RequestAction;
import com.epsmart.wzcc.http.response.model.StatusEntity;
import com.epsmart.wzcc.http.xml.handler.DefaultSaxHandler;


/*
 *交接模块
 *验收信息
 */
public class ConnectionHeadinfoFat extends BaseFragment {
    private ProgressBar pb;
    private View view;
    private Activity activity;
    private TextView consingment_note;
    private TextView controct_code;
    private TextView pro_name;
    private TextView prucase_unit;
    private TextView supply_unit;
    private TextView supply_persion;
    private TextView deli_point;
    private TextView receiver_persion;
    private EditText fhfqz_et;
    private EditText shfqz_et;
    private EditText actualdate;

    private Button submit_btn;
    private HeadBean headBean;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        view = inflater.inflate(R.layout.connection_headinfo, container, false);

        initUI(view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;

    }

    private void initUI(View view) {
        consingment_note = (TextView) view.findViewById(R.id.consingment_note);
        controct_code = (TextView) view.findViewById(R.id.controct_code);
        pro_name = (TextView) view.findViewById(R.id.pro_name);
        prucase_unit = (TextView) view.findViewById(R.id.prucase_unit);
        supply_unit = (TextView) view.findViewById(R.id.supply_unit);
        supply_persion = (TextView) view.findViewById(R.id.supply_persion);
        deli_point = (TextView) view.findViewById(R.id.deli_point);
        receiver_persion = (TextView) view.findViewById(R.id.receiver_persion);
        fhfqz_et = (EditText) view.findViewById(R.id.fhfqz_et);
        shfqz_et = (EditText) view.findViewById(R.id.shfqz_et);
        actualdate = (EditText) view.findViewById(R.id.actualdate);

        fhfqz_et.setText(((ConnectionAct) activity).fhfqz_et_value);
        shfqz_et.setText(((ConnectionAct) activity).shfqz_et_value);
        actualdate.setText(((ConnectionAct) activity).actualdate_value);



        submit_btn = (Button) view.findViewById(R.id.submit_btn);

        headBean = ((ConnectionAct) activity).approvalResponse.entity.headBean;
        if (headBean == null)
            return;
        consingment_note.setText(headBean.DELIVERINFORMCOD);
        controct_code.setText(headBean.CONTRACTID);
        pro_name.setText(headBean.PROJECTNAME);
        prucase_unit.setText(headBean.PROJECTNAME);
        supply_unit.setText(headBean.SUPPLIERNAME);
        supply_persion.setText(headBean.SUPPLINKMAN);
        deli_point.setText(headBean.ACTUALDELIVERYPL);
        receiver_persion.setText(headBean.CARRLINKMAN);

        submit_btn.setOnClickListener(submitOlck);

    }

    View.OnClickListener submitOlck = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            reqHttp();
        }
    };

    @Override
    public void onPause() {

        ((ConnectionAct) activity).fhfqz_et_value = fhfqz_et.getText().toString();
        ((ConnectionAct) activity).shfqz_et_value = shfqz_et.getText().toString();
        ((ConnectionAct) activity).actualdate_value = actualdate.getText().toString();
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void reqHttp() {
        String fhfqz_et_value = fhfqz_et.getText().toString();
        String shfqz_et_value = shfqz_et.getText().toString();
        String actualdate_value = actualdate.getText().toString();// 日期

        String req = RequestXmlHelp.getHeadXML(RequestXmlHelp.getReqXML("DELIVERINFORMCOD", headBean.DELIVERINFORMCOD)
                .append(RequestXmlHelp.getReqXML("MWSN", System.currentTimeMillis() + ""))
                .append(RequestXmlHelp.getReqXML("DELIVERSIGN", "李六"))
                .append(RequestXmlHelp.getReqXML("CONSIGNEESIGNDAT", "张珊"))).toString();

         req = RequestXmlHelp.getHeadXML(RequestXmlHelp.getReqXML("DELIVERINFORMCOD", headBean.DELIVERINFORMCOD)
                .append(RequestXmlHelp.getReqXML("MWSN", System.currentTimeMillis()+""))
                .append(RequestXmlHelp.getReqXML("DELIVERSIGN",fhfqz_et_value))
                .append(RequestXmlHelp.getReqXML("CONSIGNEESIGNDAT", shfqz_et_value))).toString();
        showModuleProgressDialog("");
        RequestAction requestAction = new RequestAction();
        requestAction.reset();
        RequestPram requestPram = new RequestPram();
        requestPram.param = req;
        requestPram.userid ="100";
        requestPram.user_type = "5";
        requestPram.methodName = RequestParamConfig.pullCommit;
        requestAction.setReqPram(requestPram);
        httpModule.executeRequest(requestAction, new DefaultSaxHandler(),
                new ProcessResponse(), BaseRequest.RequestType.THRIFT);
    }

    /* 处理表单提交返回消息 */
    class ProcessResponse implements ModuleResponseProcessor {
        @Override
        public void processResponse(BaseHttpModule httpModule, Object parseObj) {
            if (parseObj instanceof StatusEntity) {
                mHandler.obtainMessage(0, parseObj).sendToTarget();
            }
        }
    }

    private BaseHandler mHandler = new BaseHandler();
    private class BaseHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            stopProgressDialog();
            switch (msg.what) {
                case 0:
                    Toast.makeText(activity, msg.obj + "",
                            Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    break;
                default:
                    break;
            }
        }
    }

}
