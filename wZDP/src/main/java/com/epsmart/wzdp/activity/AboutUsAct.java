package com.epsmart.wzdp.activity;

import com.epsmart.wzdp.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutUsAct extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		initWidget();
	}

	private void initWidget() {
		TextView version_num = (TextView) findViewById(R.id.version_num);
		version_num.setText("1.4.2");
		TextView release_date = (TextView) findViewById(R.id.release_date);
		release_date.setText("2015-07-01");
		TextView contact_num = (TextView) findViewById(R.id.contact_num);
		contact_num.setText("0531-67983175");
	}
}
