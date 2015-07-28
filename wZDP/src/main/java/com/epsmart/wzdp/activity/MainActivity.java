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
 * @author fony 四大模块，默认请求权限列表信息
 */
public class MainActivity extends ClientActivity {
	private String TAG = MainActivity.class.getName();
	public static String provenance;
	private BaseHttpModule httpModule;
	private Activity activity;
	// public static User user;
	private CircleImageView supply;// 供应商现场管理
	private CircleImageView online;// 在线分析
	private PackageManager pm = null;

	/********** 测试数据 ********/
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
				if (!PermissHelp.isPermiss("001")) {
					PermissHelp.showToast(activity);
					return;
				}
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
				if (!PermissHelp.isPermiss("029")) {
					PermissHelp.showToast(activity);
					return;
				}
				provenance = "statistics";
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DoStatisticsAct.class);
				MainActivity.this.startActivity(intent);
			}
		});

		CircleImageView conference = (CircleImageView) circleMenu
				.findViewById(R.id.conference_image);// 视频会议
		final int POSITION_TOP_LEFT = 1;

		// getServiceConnect(mHandler);// AIDL 方式获取 功能权限
		/*********** 测试 *********/
		reqPermission();


	}




	@Override
	protected void onStart() {
		super.onStart();
	}

	/* 请求权限 */
	private void reqPermission() {
		if (appContext.user == null) {
			Toast.makeText(this, "获取用户信息失败！", Toast.LENGTH_SHORT).show();
			return;
		} else {
			Toast.makeText(this, "数据配置完毕！", Toast.LENGTH_SHORT).show();
		}
		RequestPram requestPram = new RequestPram();
		showModuleProgressDialog("提示", "数据配置中...");
		httpModule = new BaseHttpModule(this);
		httpModule.init();
		httpModule.setRequestListener(requestListener);
		httpModule.setServiceNameSpace(RequestParamConfig.serviceNameSpace);
		httpModule.setServiceUrl(RequestParamConfig.ServerUrl);

		RequestAction requestAction = new RequestAction();
		requestAction.reset();

		requestPram.bizId = 1004;
		requestPram.methodName = RequestParamConfig.userLogin;
		requestPram.pluginId = 119;//
		requestPram.userName = appContext.user.getUid();
		/******** 测试代码 获取用户权限***start ******/
		// requestAction.putParam("txt", "permission.txt");//TODO 权限测试文件
		/******** 测试代码 获取用户权限****end *****/
		requestAction.setReqPram(requestPram);
		httpModule.executeRequest(requestAction, new PermissionHandler(),
				new ProcessResponse(), RequestType.THRIFT);

	}

	/* 处理表单提交返回消息 */
	class ProcessResponse implements ModuleResponseProcessor {
		@Override
		public void processResponse(BaseHttpModule httpModule, Object parseObj) {
			if (parseObj instanceof StatusEntity) {
				mHandler.obtainMessage(1, parseObj).sendToTarget();
			} else if (parseObj instanceof PermissionResponse) {
				AppContext app_context = (AppContext) activity.getApplication();
				app_context.permission = (PermissionResponse) parseObj;
				PermissHelp.permiss = app_context.permission;
				mHandler.sendEmptyMessage(11);
			}
		}
	}

	protected ProgressDialog progressDialog;
	/* 处理网络错误 */
	protected RequestListener requestListener = new RequestListener() {
		@Override
		public void onSuccess(Response response) {
			if (progressDialog != null && progressDialog.isShowing())
				progressDialog.dismiss();
			httpModule.processResponse(httpModule, response,
					getXmlParser(response), httpModule.getResponseProcess());
		}

		@Override
		public void onFail(Exception e) {
			if (progressDialog != null && progressDialog.isShowing())
				progressDialog.dismiss();
			String msg = "未知错误";
			if (e instanceof HttpException) {
				HttpException he = (HttpException) e;
				msg = he.getMessage();
			}

			Message obtainMessage = mHandler.obtainMessage(0);
			obtainMessage.obj = msg;
			obtainMessage.sendToTarget();
		}
	};

	protected BaseXmlParser getXmlParser(Response response) {
		Log.i("MainActivity",
				"response for permission=" + response.getResponseAsString());
		response.setResponseAsString(response.getResponseAsString().replace(
				"&", "#"));
		return httpModule.getBaseXmlParser(response,
				httpModule.getParseHandler());
	}

	public void showModuleProgressDialog(String title, String msg) {
		progressDialog = ProgressDialog.show(this, title, msg, true);
	}

	protected void closeDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}

	}

	/** 处理网络异常等信息 **/
	private BaseHandler mHandler = new BaseHandler();

	private class BaseHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			closeDialog();
			switch (msg.what) {
			case 0:
				Toast.makeText(activity, msg.obj + "",
						Toast.LENGTH_LONG).show();
				break;
			case 1:
				StatusEntity se = (StatusEntity) msg.obj;
				break;
			case 111:
				reqPermission();
				break;
			case 11:

				break;
			case 2:

				break;
			default:
				break;
			}
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



	// 推出弹出框
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
