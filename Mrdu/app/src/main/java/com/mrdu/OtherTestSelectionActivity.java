package com.mrdu;

import com.mrdu.bean.TestID;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OtherTestSelectionActivity extends Activity implements OnClickListener {
	Context mContext=this;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_testformselection);
		ActionBar actionbar = getActionBar();
		Button bt_beck = ((Button) findViewById(R.id.bt_beck));
		Button bt_phq = ((Button) findViewById(R.id.bt_phq));
		Button bt_gad = ((Button) findViewById(R.id.bt_gad));
		Button bt_boensi = ((Button) findViewById(R.id.bt_boensi));
		Button bt_sds = ((Button) findViewById(R.id.bt_sds));
		// 设置点击事件
		actionbar.setDisplayHomeAsUpEnabled(true);
		bt_beck.setOnClickListener(this);
		bt_phq.setOnClickListener(this);
		bt_gad.setOnClickListener(this);
		bt_boensi.setOnClickListener(this);
		bt_sds.setOnClickListener(this);

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		case R.id.personal:
			startActivity(new Intent(mContext, PersonalActivity.class));
			return true;
		}
		return true;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i = new Intent(this,TestActivity.class);
		int value = TestID.NONE;
		value = TestID.BECK;
		i.putExtra("type", value);
		this.startActivity(i);
	}
}
