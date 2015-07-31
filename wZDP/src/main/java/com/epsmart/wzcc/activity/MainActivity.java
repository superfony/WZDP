package com.epsmart.wzcc.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.epsmart.wzcc.R;
import com.epsmart.wzcc.http.BaseHttpModule;
import com.epsmart.wzcc.updata.UpdateManager;
import com.epsmart.wzcc.view.CircleImageView;

/**
 * 首页显示
 */
public class MainActivity extends ClientActivity {
    private String TAG = MainActivity.class.getName();
    public static String provenance;
    private BaseHttpModule httpModule;
    private Activity activity;
    private CircleImageView supply;// 供应商现场管理
    private CircleImageView online;// 在线分析
    private PackageManager pm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_main);
        UpdateManager.getUpdateManager().checkAppUpdate(this, false);// 检查是否更新
//		ImageView install = (ImageView) findViewById(R.id.install_image);// 设置按钮
//
//		install.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				Intent intent = new Intent(activity, MoreAct.class);
//				activity.startActivity(intent);
//			}
//		});


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
