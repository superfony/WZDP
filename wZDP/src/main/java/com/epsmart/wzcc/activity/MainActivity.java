package com.epsmart.wzcc.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.activity.more.MoreAct;
import com.epsmart.wzcc.activity.supply.SupplyActivity;
import com.epsmart.wzcc.activity.supply.info.InfoActivity;
import com.epsmart.wzcc.db.DatabaseHelper;
import com.epsmart.wzcc.db.table.AppHeadTable;
import com.epsmart.wzcc.db.table.SimpleData;
import com.epsmart.wzcc.http.BaseHttpModule;
import com.j256.ormlite.dao.Dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
    //文件下载保存路径
    private String savePath = "";
    private Dialog downloadDialog;
    // 进度条
    private ProgressBar mProgress;
    // 进度值
    private int progress;
    // 显示下载数值
    private TextView mProgressText;
    // 终止标记
    private boolean interceptFlag;
    // 下载线程
    private Thread downLoadThread;
    // 临时下载文件路径
    private String tmpFilePath = "";
    // 下载文件大小
    private String apkFileSize;
    // 已下载文件大小
    private String tmpFileSize;
    private String txtFilePath;
    private static final int DOWN_NOSDCARD = 0;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    mProgress.setProgress(progress);
                    mProgressText.setText(tmpFileSize + "/" + apkFileSize);
                    break;
                case DOWN_OVER:
                    downloadDialog.dismiss();
//                    readFile();
                    try {
                        DatabaseHelper Helper = DatabaseHelper.getHelper(activity);
                        Dao dao = Helper.getDao(SimpleData.class);
                        SimpleData simpleData=new SimpleData(100);
                        dao.create(simpleData);
                        readTxtFile("admin.txt");
                    }catch (SQLException s){
                        s.printStackTrace();
                    }
                    break;
                case DOWN_NOSDCARD:
                    downloadDialog.dismiss();
                    Toast.makeText(activity, "无法下载文件，请检查SD卡是否挂载", 3000).show();
                    break;
            }
        }
    };

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
                try {
                    DatabaseHelper dbhelper = DatabaseHelper.getHelper(activity);
                    //用于测试创建数据库
                    Dao dao = dbhelper.getDao(SimpleData.class);
                    SimpleData simpleData = new SimpleData(10000);
                    dao.create(simpleData);
                    readTxtFile("admin.txt");
                }catch (SQLException s){
                    s.printStackTrace();
                }
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
//                try {
//                    DatabaseHelper dbhelper = DatabaseHelper.getHelper(activity);
//                    //用于测试创建数据库
//                    Dao dao = dbhelper.getDao(SimpleData.class);
//                    SimpleData simpleData = new SimpleData(10000);
//                    dao.create(simpleData);
//                    readTxtFile("admin.txt");
//                }catch (SQLException s){
//                    s.printStackTrace();
//                }
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
                Intent intent = new Intent(activity,
                        InfoActivity.class);
                activity.startActivity(intent);
            }
        });
        //  数据同步
        data.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                showDownloadDialog();
            }
        });
// 设置
        setting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(activity,
                        MoreAct.class);
                activity.startActivity(intent);
            }
        });

    }

    /**
     * 显示下载对话框
     */
    private void showDownloadDialog() {
        LayoutInflater inflater = LayoutInflater.from(activity);
        LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.update_progress, null);

        downloadDialog = new AlertDialog.Builder(activity).create();
        downloadDialog.show();
        downloadDialog.getWindow().setContentView(layout);
        mProgress = (ProgressBar) layout.findViewById(R.id.update_progress);
        mProgressText = (TextView) layout
                .findViewById(R.id.update_progress_text);
        Button update_load = (Button) layout.findViewById(R.id.update_load);
        update_load.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                downloadDialog.dismiss();
                interceptFlag = true;
            }
        });
        downloadDialog.setCanceledOnTouchOutside(false);
        downloadfile();

    }

    private void downloadfile() {
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    private Runnable mdownApkRunnable = new Runnable() {




        @Override
        public void run() {
            {
                try {
                    String apkName =  "admin.txt";
                    String tmpApk =  "admin.tmp";
                    // 判断是否挂载了SD卡
                    String storageState = Environment.getExternalStorageState();
                    if (storageState.equals(Environment.MEDIA_MOUNTED)) {
                        savePath = Environment.getExternalStorageDirectory()
                                .getAbsolutePath() + "/epsmart/app/";
                        Log.i("", ".....savePath.>>" + savePath);
                        File file = new File(savePath);
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        txtFilePath = savePath + apkName;
                        tmpFilePath = savePath + tmpApk;
                    }
                    if (txtFilePath == null || "".equals(txtFilePath)) {
                        mHandler.sendEmptyMessage(DOWN_NOSDCARD);
                        return;
                    }
                    File ApkFile = new File(txtFilePath);
                    if (ApkFile.exists()) {
                        downloadDialog.dismiss();
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        return;
                    }
                    File tmpFile = new File(tmpFilePath);
                    FileOutputStream fos = new FileOutputStream(tmpFile);
                    URL url = new URL("http://127.0.0.1:8553/lnptgl/uploads/apps/admin.txt");
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.connect();
                    int length = conn.getContentLength();
                    InputStream is = conn.getInputStream();
                    DecimalFormat df = new DecimalFormat("0.00");
                    apkFileSize = df.format((float) length / 1024 / 1024) + "MB";
                    int count = 0;
                    byte buf[] = new byte[1024];
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        tmpFileSize = df.format((float) count / 1024 / 1024) + "MB";
                        progress = (int) (((float) count / length) * 100);
                        mHandler.sendEmptyMessage(DOWN_UPDATE);
                        if (numread <= 0) {
                            // 下载完成 - 将临时下载文件转成APK文件
                            if (tmpFile.renameTo(ApkFile)) {
                                // 通知安装
                                mHandler.sendEmptyMessage(DOWN_OVER);
                            }
                            break;
                        }
                        fos.write(buf, 0, numread);
                    } while (!interceptFlag);// 点击取消就停止下载

                    fos.close();
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    };

    public void readTxtFile(String fileName) {
//        File file = new File(savePath + fileName);
        try {
            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream(fileName);
//                    new FileInputStream(fileName);
            BufferedReader rd = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            String tempLine =  rd.readLine();
            Log.w("MainActivity", "wzcc_path=" + activity.getApplicationContext().getDatabasePath("wzcc.db"));
            SQLiteDatabase  sqLiteDatabase=SQLiteDatabase.openOrCreateDatabase(String.valueOf(activity.getApplicationContext().getDatabasePath("wzcc.db")), null);


            while (!TextUtils.isEmpty(tempLine)) {
                Log.w("MainActivity", "tempLine=" + tempLine);
               // dao.executeRawNoArgs(tempLine);
                sqLiteDatabase.execSQL(tempLine);
                tempLine = rd.readLine();
            }

            DatabaseHelper dbhelper = DatabaseHelper.getHelper(activity);
            Dao dao = dbhelper.getDao(AppHeadTable.class);
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
