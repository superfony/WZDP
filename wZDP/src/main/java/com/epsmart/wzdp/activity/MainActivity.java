package com.epsmart.wzdp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.epsmart.wzdp.R;
import com.epsmart.wzdp.activity.achar.DoStatisticsAct;
import com.epsmart.wzdp.activity.more.MoreAct;
import com.epsmart.wzdp.activity.supply.SupplyMenuActivity;
import com.epsmart.wzdp.bean.PermissionHandler;
import com.epsmart.wzdp.bean.PermissionResponse;
import com.epsmart.wzdp.bean.RequestPram;
import com.epsmart.wzdp.common.PermissHelp;
import com.epsmart.wzdp.http.BaseHttpModule;
import com.epsmart.wzdp.http.BaseHttpModule.RequestListener;
import com.epsmart.wzdp.http.ModuleResponseProcessor;
import com.epsmart.wzdp.http.request.BaseRequest.RequestType;
import com.epsmart.wzdp.http.request.HttpException;
import com.epsmart.wzdp.http.request.RequestAction;
import com.epsmart.wzdp.http.response.model.Response;
import com.epsmart.wzdp.http.response.model.StatusEntity;
import com.epsmart.wzdp.http.xml.parser.BaseXmlParser;
import com.epsmart.wzdp.updata.UpdateManager;
import com.epsmart.wzdp.view.CircleImageView;
import com.epsmart.wzdp.view.CircleLayout;

/**
 *首页显示
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
		ImageView install = (ImageView) findViewById(R.id.install_image);// 设置按钮

		install.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(activity, MoreAct.class);
				activity.startActivity(intent);
			}
		});

		CircleLayout circleMenu = (CircleLayout) findViewById(R.id.main_circle_layout);
		TextView selectedTextView = (TextView) findViewById(R.id.main_selected_textView);
		selectedTextView.setText(((CircleImageView) circleMenu
				.getSelectedItem()).getName());

		supply = (CircleImageView) circleMenu.findViewById(R.id.supply_image);

		supply.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				provenance = "supply";
				Intent intent = new Intent(MainActivity.this,
						SupplyMenuActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});



		online = (CircleImageView) circleMenu.findViewById(R.id.online_image);// 在线统计
		online.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				provenance = "statistics";
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DoStatisticsAct.class);
				MainActivity.this.startActivity(intent);
			}
		});
		CircleImageView conference = (CircleImageView) circleMenu
				.findViewById(R.id.conference_image);// 视频会
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
