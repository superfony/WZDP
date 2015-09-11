package com.epsmart.wzcc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.epsmart.wzcc.R;

public class AboutUsAct extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		initWidget();
	}

	private void initWidget() {
		TextView version_num = (TextView) findViewById(R.id.version_num);
		version_num.setText("1.0");
		TextView release_date = (TextView) findViewById(R.id.release_date);
		release_date.setText("2015-10-10");
		TextView contact_num = (TextView) findViewById(R.id.contact_num);
		contact_num.setText("0531-67983175");
	}
}
