package com.epsmart.wzcc.activity.supply.approval;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.AppContext;
import com.epsmart.wzcc.activity.PerferenceModel;
import com.epsmart.wzcc.activity.RequestParamConfig;
import com.epsmart.wzcc.activity.supply.approval.parcelable.BatchBean;
import com.epsmart.wzcc.activity.supply.approval.parcelable.BatchResponse;
import com.epsmart.wzcc.activity.supply.approval.parcelable.ItemBean;
import com.epsmart.wzcc.bean.RequestPram;
import com.epsmart.wzcc.db.DatabaseHelper;
import com.epsmart.wzcc.db.table.AppHeadTable;
import com.epsmart.wzcc.db.table.StockTable;
import com.epsmart.wzcc.http.BaseHttpModule;
import com.epsmart.wzcc.http.ModuleResponseProcessor;
import com.epsmart.wzcc.http.request.BaseRequest;
import com.epsmart.wzcc.http.request.RequestAction;
import com.epsmart.wzcc.http.response.model.StatusEntity;
import com.j256.ormlite.dao.Dao;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/*
 *   验收入库-验收明细
 */
public class StatisticalFat extends BaseFragment {
    private View view;
    private ListView listView;
    private ApprovalAdapter approvalAdapter;
    private Activity activity;
    private List<ItemBean> list = new ArrayList<ItemBean>();
    private CheckBox checkBox;
    private Button button;
    private ListView batch_list;
    private BatchAdapter batchAdapter;
    private ArrayList<BatchBean> batchlist = new ArrayList<BatchBean>();
//	private String type;
//	private UnitTarget resp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        view = inflater.inflate(R.layout.stat_list, null);
        initUI();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initUI() {
        listView = (ListView) view.findViewById(R.id.stat_jz);
        list = ((UnitInforActivity) activity).approvalResponse.entity.itemBeansList;
        approvalAdapter = new ApprovalAdapter(activity, list);
        listView.setAdapter(approvalAdapter);
        checkBox = (CheckBox) view.findViewById(R.id.all_checkbox);
        button = (Button) view.findViewById(R.id.pl_btn);
        button.setOnClickListener(btn_olc);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < list.size(); i++) {
                        ((ItemBean) approvalAdapter.getItem(i)).isCheckbox = true;
                    }
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        ((ItemBean) approvalAdapter.getItem(i)).isCheckbox = false;
                    }
                }
                osHandler.sendEmptyMessage(0);

            }
        });

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
                    ItemBean itemBean = list.get(i);
                    if (itemBean.isCheckbox) {
                        list.get(i).STGE_LOC = batchBean.STGE_LOC;
                    }
                }
                ((UnitInforActivity) activity).approvalResponse.entity.headBean.STGE_LOC = batchBean.STGE_LOC;
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
                approvalAdapter.updateListView(list);
            } else if (msg.what == 1) {
                batchAdapter.updateListView(((BatchResponse) msg.obj).itemBeansList);
            } else if (msg.what == 2) {
            }
        }
    };


    private void reqHttp() {
        showModuleProgressDialog("");
        RequestAction requestAction = new RequestAction();
        requestAction.reset();
        RequestPram requestPram = new RequestPram();
        String uid = PerferenceModel.getPM(activity).getValue(
                "uuid_my", "");
        requestPram.userid = uid;
        requestPram.methodName = RequestParamConfig.warehouseInfo;
        requestAction.setReqPram(requestPram);

        Boolean isOnLine = ((AppContext) activity.getApplicationContext()).isNetworkConnected();
        if (isOnLine) {
            httpModule.executeRequest(requestAction, new BatchHandler(), new ProcessResponseBatch(),
                    BaseRequest.RequestType.THRIFT);
        } else {
            try {
                DatabaseHelper dbhelper = DatabaseHelper.getHelper(activity);
                Dao dao = dbhelper.getDao(BatchBean.class);
                List<BatchBean> batchBeanList = dao.queryForAll();
                Log.w("query batch bean","batchBeanList.size="+batchBeanList.size());

                BatchResponse batchResponse = new BatchResponse();
                batchResponse.itemBeansList = (ArrayList)batchBeanList;
                batchResponse.message = "";
                batchResponse.result = "0";
                osHandler.obtainMessage(1, batchResponse).sendToTarget();
            } catch (Exception e) {

            }

        }

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
                try {
                    DatabaseHelper dbhelper = DatabaseHelper.getHelper(activity);
                    Dao dao = dbhelper.getDao(BatchBean.class);

                 ArrayList<BatchBean> arrayList=((BatchResponse) parseObj).itemBeansList;
                    Log.w("","BatchBean="+arrayList.size());
                    for (int i=0;i<arrayList.size();i++){

                        dao.create(arrayList.get(i));

                    }
                }catch (Exception e){
                }
                osHandler.obtainMessage(1, (BatchResponse) parseObj).sendToTarget();
            }
        }
    }

}
