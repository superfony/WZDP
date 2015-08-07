package com.epsmart.wzcc.widget;



import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.epsmart.wzcc.R;


public class XGCustomProgressDialog extends Dialog {
	@SuppressWarnings("unused")
	private Context context = null;
	private static XGCustomProgressDialog customProgressDialog = null;

	public XGCustomProgressDialog(Context context) {
		super(context);
		this.context = context;
	}

	public XGCustomProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	public static XGCustomProgressDialog createDialog(Activity activity) {
		customProgressDialog = new XGCustomProgressDialog(activity, R.style.XGCustomProgressDialog);
		customProgressDialog.setContentView(R.layout.xgcustomprogressdialog);
		  /**设置透明度*/
		  Window window = customProgressDialog.getWindow();
		  WindowManager.LayoutParams lp = window.getAttributes();
		  lp.alpha = 0.5f;// 透明度
		  lp.dimAmount = 0.8f;// 黑暗度
		  window.setAttributes(lp);
		  window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
		  window.getAttributes().gravity = Gravity.CENTER;

		return customProgressDialog;
	}

	public void onWindowFocusChanged(boolean hasFocus) {

		if (customProgressDialog == null) {
			return;
		}

		ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.xgloadingImageView);
		AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
		animationDrawable.start();
	}

	/**
	 * 
	 * [Summary] setTitile 标题
	 * 
	 * @param strTitle
	 * @return
	 * 
	 */
	public XGCustomProgressDialog setTitile(String strTitle) {
		return customProgressDialog;
	}

	/**
	 * 
	 * [Summary] setMessage 提示内容
	 * 
	 * @param strMessage
	 * @return
	 * 
	 */
	public XGCustomProgressDialog setMessage(String strMessage) {
		TextView tvMsg = (TextView) customProgressDialog.findViewById(R.id.xgid_tv_loadingmsg);

		if (tvMsg != null) {
			tvMsg.setText(strMessage);
		}

		return customProgressDialog;
	}
	
	@Override
	public void setCanceledOnTouchOutside(boolean cancel) {
		super.setCanceledOnTouchOutside(cancel);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
