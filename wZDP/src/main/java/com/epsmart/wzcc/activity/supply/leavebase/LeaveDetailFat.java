package com.epsmart.wzcc.activity.supply.leavebase;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.RequestParamConfig;
import com.epsmart.wzcc.activity.supply.approval.BaseFragment;
import com.epsmart.wzcc.activity.supply.approval.BatchAdapter;
import com.epsmart.wzcc.activity.supply.approval.BatchHandler;
import com.epsmart.wzcc.activity.supply.approval.parcelable.BatchBean;
import com.epsmart.wzcc.activity.supply.approval.parcelable.BatchResponse;
import com.epsmart.wzcc.activity.supply.leavebase.parcelable.LeaveEntityContent;
import com.epsmart.wzcc.activity.supply.leavebase.parcelable.LeaveItemBean;
import com.epsmart.wzcc.bean.RequestPram;
import com.epsmart.wzcc.common.RequestXmlHelp;
import com.epsmart.wzcc.http.BaseHttpModule;
import com.epsmart.wzcc.http.ModuleResponseProcessor;
import com.epsmart.wzcc.http.request.BaseRequest;
import com.epsmart.wzcc.http.request.RequestAction;
import com.epsmart.wzcc.http.response.model.StatusEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/*
 *   出库明细
 */
public class LeaveDetailFat extends BaseFragment {
    private View view;
    private ListView listView;
    private LeavebaseAdapter leavebaseAdapter;
    private Activity activity;
    private List<LeaveItemBean> list = new ArrayList<LeaveItemBean>();
    private CheckBox checkBox;
    private Button button;
    private ListView batch_list;
    private BatchAdapter batchAdapter;
    private ArrayList<BatchBean> batchlist = new ArrayList<BatchBean>();
    private int checkNum; // 记录选中的条目数量
    private TextView select_count;
    private EditText posting_date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        view = inflater.inflate(R.layout.leavebasedetailcheckbox, null);
        initUI();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }


    private void initUI() {
        listView = (ListView) view.findViewById(R.id.stat_jz);
        if (((LeavebaseAct) activity).leavebaseResponse.entity.leaveItemBeansList == null)
            return;
        list = ((LeavebaseAct) activity).leavebaseResponse.entity.leaveItemBeansList;
        leavebaseAdapter = new LeavebaseAdapter(activity, list);
        listView.setAdapter(leavebaseAdapter);
        checkBox = (CheckBox) view.findViewById(R.id.all_checkbox);
        button = (Button) view.findViewById(R.id.pl_btn);
        button.setOnClickListener(btn_olc);
        //      listView.setOnItemClickListener(itemOcl);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < list.size(); i++) {
//                        list.get(i).isCheckbox = true;
                        ((LeaveItemBean) leavebaseAdapter.getItem(i)).isCheckbox = true;
                        checkNum++;
                    }
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).isCheckbox = false;
                        checkNum--;
                    }
                }
                osHandler.sendEmptyMessage(0);

            }
        });

    }

    // 出库数据提交
    View.OnClickListener submit_olc = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dateDialog();
        }
    };

    Dialog dateDialog;

    private void dateDialog() {
        {

            LayoutInflater inflater = LayoutInflater.from(activity);
            LinearLayout layout = (LinearLayout) inflater.inflate(
                    R.layout.sr_confirm_dialog, null);

            dialog = new AlertDialog.Builder(activity).create();
            dialog.setCancelable(true);
            dialog.show();
            dialog.getWindow().setContentView(layout);

            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).isCheckbox()==true) {
                    checkNum++;
                }

            }
            posting_date = (EditText) layout.findViewById(R.id.posting_date);//过账日期
            TextView all_count = (TextView) layout.findViewById(R.id.all_count);//总项数
            all_count.setText(Integer.toString(list.size()));
            select_count = (TextView) layout.findViewById(R.id.select_count);//已选项
            select_count.setText(Integer.toString(checkNum));
            Button confirm_btn = (Button) layout.findViewById(R.id.confirm_btn);//确认按钮

            posting_date.setFocusable(true);
            //            posting_date.setBackgroundColor(Color.WHITE);
            posting_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDate(v);
                }
            });
            confirm_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String date = posting_date.getText().toString();
                    if (checkNum > 0 && null!=date) {
                        submit_req(date);
                        dialog.dismiss();
                    }else {
                        Toast.makeText(activity, "请填写时间并至少选择一项", Toast.LENGTH_LONG).show();
                    }


                    //TODO
                }
            });
        }
    }

    View.OnClickListener btn_olc = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog();
        }
    };


    Dialog dialog;

    private void dialog() {
        LayoutInflater inflater = LayoutInflater.from(activity);
        LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.batch_configing_dialog, null);
        dialog = new AlertDialog.Builder(activity).create();
        dialog.setCancelable(true);
        dialog.show();
        dialog.getWindow().setContentView(layout);

        batch_list = (ListView) layout.findViewById(R.id.batch_list);
        batchAdapter = new BatchAdapter(activity, batchlist);
        batch_list.setAdapter(batchAdapter);
        batch_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BatchBean batchBean = (BatchBean) batchAdapter.getItem(position);

                for (int i = 0; i < list.size(); i++) {
                    LeaveItemBean itemBean = list.get(i);
                    if (itemBean.isCheckbox) {
                        list.get(i).STGE_LOC = batchBean.STGE_LOC;
                    }
                }
                ((LeavebaseAct) activity).leavebaseResponse.entity.leaveHeadBean.STGE_LOC = batchBean.STGE_LOC;
                osHandler.sendEmptyMessage(0);
                dialog.cancel();
            }
        });
        reqHttp();
    }

    android.os.Handler osHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            stopProgressDialog();
            if (msg.what == 0) {
                leavebaseAdapter.notifyDataSetChanged();
            } else if (msg.what == 1) {
                // Toast.makeText(activity, ((BatchResponse) msg.obj).message, Toast.LENGTH_LONG).show();
                batchAdapter.updateListView(((BatchResponse) msg.obj).itemBeansList);
            } else if (msg.what == 2) {

                Toast.makeText(activity, msg.obj.toString(), Toast.LENGTH_LONG).show();
            }
        }
    };


    private void reqHttp() {
        showModuleProgressDialog("");
        RequestAction requestAction = new RequestAction();
        requestAction.reset();
        RequestPram requestPram = new RequestPram();
        requestPram.userid = "130";
        requestPram.methodName = RequestParamConfig.warehouseInfo;
        requestAction.setReqPram(requestPram);

        httpModule.executeRequest(requestAction, new BatchHandler(), new ProcessResponseBatch(),
                BaseRequest.RequestType.THRIFT);


    }

    private void submit_req(String date) {

        String req = RequestXmlHelp.getHeadDetailXML(RequestXmlHelp.getReqXML("PSTNG_DATE", date)
                        .append(RequestXmlHelp.getReqXML("MWSN", System.currentTimeMillis() + ""))
                        .append(RequestXmlHelp.getReqXML("STGE_LOG", ((LeavebaseAct) activity).leavebaseResponse.entity.leaveHeadBean.STGE_LOC))
                        .append(RequestXmlHelp.getReqXML("RESERV_NO", ((LeavebaseAct) activity).leavebaseResponse.entity.leaveHeadBean.RESERV_NO))
                , stringBufferGet(((LeavebaseAct) activity).leavebaseResponse.entity));
        System.out.print(",,,,,,,,,,,,,,,,date>>>>>>>>>"+date);
        showModuleProgressDialog("");
        RequestAction requestAction = new RequestAction();
        requestAction.reset();
        RequestPram requestPram = new RequestPram();
        requestPram.userid = "130";
        requestPram.methodName = RequestParamConfig.pullwarehourseSubmit;
        requestPram.param = req;
        requestAction.setReqPram(requestPram);
        httpModule.executeRequest(requestAction, null, new ProcessResponseBatch(),
                BaseRequest.RequestType.THRIFT);


    }

    public StringBuffer stringBufferGet(LeaveEntityContent entityContent) {
        StringBuffer reqXML = new StringBuffer();
        ArrayList<LeaveItemBean> list = entityContent.leaveItemBeansList;
        for (int i = 0; i < list.size(); i++) {
            LeaveItemBean itemBean = list.get(i);
            reqXML.append(RequestXmlHelp.getDetailXML(RequestXmlHelp.getReqXML("RES_ITEM", itemBean.RES_ITEM)
                    .append(RequestXmlHelp.getReqXML("BATCH", itemBean.BATCH))
                    .append(RequestXmlHelp.getReqXML("ENTRY_QNT", itemBean.ENTRY_QNT))));
        }

        return reqXML;
    }


    class ProcessResponseBatch implements ModuleResponseProcessor {
        @Override
        public void processResponse(BaseHttpModule httpModule, Object parseObj) {

            if (parseObj instanceof StatusEntity) {
                StatusEntity staty = (StatusEntity) parseObj;
                String result = staty.result;
                String message = staty.message;
                if ("1".equals(result) && !TextUtils.isEmpty(message)) {
                    osHandler.obtainMessage(2, message).sendToTarget();
                }
            } else if (parseObj instanceof BatchResponse) {
                osHandler.obtainMessage(1, (BatchResponse) parseObj).sendToTarget();
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        title_image.setBackgroundResource(R.drawable.sr_ruku_title);
        present_lay.setVisibility(View.VISIBLE);
        present_btn.setOnClickListener(submit_olc);
    }

    public void selectDate(View v) {
        final EditText et = (EditText) v;
        et.setInputType(InputType.TYPE_NULL);
        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month,
                                  int dayOfMonth) {
                et.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
            }
        };
        Calendar calendar = Calendar.getInstance();
        Dialog dialog = new DatePickerDialog(activity, dateListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

}
