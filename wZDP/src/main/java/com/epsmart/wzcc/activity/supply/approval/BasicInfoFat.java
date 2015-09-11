package com.epsmart.wzcc.activity.supply.approval;

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
import com.epsmart.wzcc.activity.AppContext;
import com.epsmart.wzcc.activity.PerferenceModel;
import com.epsmart.wzcc.activity.RequestParamConfig;
import com.epsmart.wzcc.activity.supply.approval.parcelable.EntityContent;
import com.epsmart.wzcc.activity.supply.approval.parcelable.HeadBean;
import com.epsmart.wzcc.activity.supply.approval.parcelable.ItemBean;
import com.epsmart.wzcc.bean.RequestPram;
import com.epsmart.wzcc.common.RequestXmlHelp;
import com.epsmart.wzcc.db.DatabaseHelper;
import com.epsmart.wzcc.db.table.SubmitDateTable;
import com.epsmart.wzcc.http.BaseHttpModule;
import com.epsmart.wzcc.http.ModuleResponseProcessor;
import com.epsmart.wzcc.http.request.BaseRequest;
import com.epsmart.wzcc.http.request.RequestAction;
import com.epsmart.wzcc.http.response.model.StatusEntity;
import com.epsmart.wzcc.http.xml.handler.DefaultSaxHandler;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


/*
 *货物验收模块  验收信息
 */
public class BasicInfoFat extends BaseFragment {
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
    private EditText company_wz;
    private EditText company_gys;
    private EditText company_xmdw;
    private EditText company_jl;
    private EditText company_sgdw;
    private Button submit_btn;
    private HeadBean headBean;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        view = inflater.inflate(R.layout.approval_info, container, false);

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
        company_wz = (EditText) view.findViewById(R.id.company_wz);
        company_gys = (EditText) view.findViewById(R.id.company_gys);
        company_xmdw = (EditText) view.findViewById(R.id.company_xmdw);
        company_jl = (EditText) view.findViewById(R.id.company_jl);
        company_sgdw=(EditText) view.findViewById(R.id.company_sgdw);
        company_wz.setText(((UnitInforActivity) activity).company_wz_value);
        company_gys.setText(((UnitInforActivity) activity).company_gys_value);
        company_xmdw.setText(((UnitInforActivity) activity).company_xmdw_value);
        company_jl.setText(((UnitInforActivity) activity).company_jl_value);
        company_sgdw.setText(((UnitInforActivity) activity).company_sgdw_value);


        submit_btn = (Button) view.findViewById(R.id.submit_btn);
        headBean = ((UnitInforActivity) activity).approvalResponse.entity.headBean;
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
   //这里进行提交的操作
    View.OnClickListener submitOlck = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            reqHttp();


        }
    };

     private void submintOffline(String methodName,String userName,String param,String state){

         DatabaseHelper databaseHelper=DatabaseHelper.getHelper(activity);
         Dao dao= null;
         try {
             dao = databaseHelper.getDao(SubmitDateTable.class);
         } catch (SQLException e) {
             e.printStackTrace();
         }
         SubmitDateTable submitData = null;
         submitData = new SubmitDateTable();
         submitData.setMethodName(methodName);
         submitData.setParam(param);
         submitData.setUserName(userName);
         submitData.setDate(new Date());
         submitData.setState(state);// 缓存未上传
         try {
             dao.create(submitData);
             mHandler.obtainMessage(0, "离线数据保存成功！").sendToTarget();
         } catch (SQLException e) {
             mHandler.obtainMessage(0, "离线数据保存失败！").sendToTarget();
             e.printStackTrace();
         }
     }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {

        ((UnitInforActivity) activity).company_wz_value = company_wz.getText().toString();
        ((UnitInforActivity) activity).company_gys_value = company_gys.getText().toString();
        ((UnitInforActivity) activity).company_xmdw_value = company_xmdw.getText().toString();
        ((UnitInforActivity) activity).company_jl_value = company_jl.getText().toString();
        ((UnitInforActivity) activity).company_sgdw_value = company_sgdw.getText().toString();
        super.onPause();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void reqHttp() {
        ((UnitInforActivity) activity).company_wz_value = company_wz.getText().toString();
        ((UnitInforActivity) activity).company_gys_value = company_gys.getText().toString();
        ((UnitInforActivity) activity).company_xmdw_value = company_xmdw.getText().toString();
        ((UnitInforActivity) activity).company_jl_value = company_jl.getText().toString();
        String req = null;
//                RequestXmlHelp.getHeadDetailXML(RequestXmlHelp.getReqXML("DELIVERINFORMCOD", headBean.DELIVERINFORMCOD)
//                        .append(RequestXmlHelp.getReqXML("MWSN", System.currentTimeMillis() + ""))
//                        .append(RequestXmlHelp.getReqXML("MATCOMPANYSIGN", "李六"))
//                        .append(RequestXmlHelp.getReqXML("SUPPLIERDELIVERS", "李七"))
//                        .append(RequestXmlHelp.getReqXML("SGUNITSIGN", "李八"))
//                        .append(RequestXmlHelp.getReqXML("PROJECTRECEIVEDS", "张珊")),
//
//                RequestXmlHelp.getDetailXML(RequestXmlHelp.getReqXML("ACCEAMOUNT", "")
//                        .append(RequestXmlHelp.getReqXML("MATERIALID", "0345678")))
//
//        ).toString();

        // 正式库调用

        req = RequestXmlHelp.getHeadDetailXML(RequestXmlHelp.getReqXML("DELIVERINFORMCOD", headBean.DELIVERINFORMCOD)
                        .append(RequestXmlHelp.getReqXML("MWSN", System.currentTimeMillis() + ""))
                        .append(RequestXmlHelp.getReqXML("MATCOMPANYSIGN", ((UnitInforActivity) activity).company_wz_value))
                        .append(RequestXmlHelp.getReqXML("SUPPLIERDELIVERS", ((UnitInforActivity) activity).company_gys_value))
                        .append(RequestXmlHelp.getReqXML("SGUNITSIGN", ((UnitInforActivity) activity).company_xmdw_value))
                        .append(RequestXmlHelp.getReqXML("PROJECTRECEIVEDS", ((UnitInforActivity) activity).company_jl_value))
                        .append(RequestXmlHelp.getReqXML("STGE_LOC", headBean.STGE_LOC))//TODO改善
                ,
                stringBufferGet(((UnitInforActivity) activity).approvalResponse.entity)

        ).toString();


        showModuleProgressDialog("");
        RequestAction requestAction = new RequestAction();
        requestAction.reset();
        RequestPram requestPram = new RequestPram();
        requestPram.param = req;
        String uid = PerferenceModel.getPM(activity).getValue(
                "uuid_my", "");
        requestPram.userid = uid;
        requestPram.user_type = "6";// 操作类型
        requestPram.methodName = RequestParamConfig.pullCommit;
        requestAction.setReqPram(requestPram);

        Boolean isOnLine=((AppContext)activity.getApplicationContext()).isNetworkConnected();
          if(isOnLine) {
              httpModule.executeRequest(requestAction, new DefaultSaxHandler(),
                      new ProcessResponse(), BaseRequest.RequestType.THRIFT);
          }else{
              submintOffline(RequestParamConfig.pullCommit,"100",req,"6");
          }
    }

    public StringBuffer stringBufferGet(EntityContent entityContent) {
        StringBuffer reqXML = new StringBuffer();
        ArrayList<ItemBean> list = entityContent.itemBeansList;
        for (int i = 0; i < list.size(); i++) {
            ItemBean itemBean = list.get(i);
            reqXML.append(RequestXmlHelp.getDetailXML(RequestXmlHelp.getReqXML("ACCEAMOUNT", itemBean.ACCEAMOUNT)
                    .append(RequestXmlHelp.getReqXML("SUPPLYPLANCODE", itemBean.SUPPLYPLANCODE))));
        }

        return reqXML;
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
