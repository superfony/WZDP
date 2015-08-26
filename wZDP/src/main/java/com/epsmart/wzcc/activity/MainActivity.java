package com.epsmart.wzcc.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.renderscript.BaseObj;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.supply.SupplyActivity;
import com.epsmart.wzcc.activity.supply.SupplyMenuActivity;
import com.epsmart.wzcc.activity.test.Return;
import com.epsmart.wzcc.db.DatabaseHelper;
import com.epsmart.wzcc.db.dao.DaoManager;
import com.epsmart.wzcc.db.table.AppHeadTable;
import com.epsmart.wzcc.http.BaseHttpModule;
import com.epsmart.wzcc.updata.UpdateManager;
import com.epsmart.wzcc.view.CircleImageView;
import com.j256.ormlite.dao.Dao;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

/**
 * 首页显示
 */
public class MainActivity<E> extends ClientActivity {
    private String TAG = MainActivity.class.getName();
    public static String provenance;
    private BaseHttpModule httpModule;
    private Activity activity;
    private PackageManager pm = null;
    private ImageButton transfer, ruku, chuku, info, data, setting;

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
        //
        info.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

            }
        });
        //  数据同步
        data.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                try {
                    DatabaseHelper dbhelper = DatabaseHelper.getHelper(activity);
                    Dao dao = dbhelper.getDao(AppHeadTable.class);

                    readTxtFile("admin.txt", dao);

                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }
        });
// 设置
        setting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

            }
        });

    }


    public void readTxtFile(String fileName, Dao dao) {
        try {
            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream(fileName);

            BufferedReader rd = new BufferedReader(new InputStreamReader(in,
                    "UTF-8"));
            String tempLine =  rd.readLine();
            while (!TextUtils.isEmpty(tempLine)) {
                Log.w("MainActivity","tempLine="+tempLine);
                dao.executeRawNoArgs(tempLine);
                tempLine = rd.readLine();
            }
            List<AppHeadTable> appHeadTableslist = dao.queryForAll();
            Log.w("MainActivity", "appHeadList.size=" + appHeadTableslist.size());
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
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


}
