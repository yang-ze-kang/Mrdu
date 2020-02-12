package com.mrdu;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mrdu.bean.TestID;
import com.mrdu.helper.MyNet;
import com.mrdu.util.TimeUtils;

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

public class TestSelectionActivity extends Activity implements OnClickListener {
	Context mContext=this;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_testselection);
		ActionBar actionbar = getActionBar();
		Button bt_dtcfs = ((Button) findViewById(R.id.bt_dtcfs));
		Button bt_dzsb = ((Button) findViewById(R.id.bt_dzsb));
		Button bt_jbfs = ((Button) findViewById(R.id.bt_jbfs));
		Button bt_stroop = ((Button) findViewById(R.id.bt_stroop));
		Button bt_hbqcs = ((Button) findViewById(R.id.bt_hbqcs));
		Button bt_wjdc = ((Button) findViewById(R.id.bt_wjdc));
		Button bt_qtlb = ((Button) findViewById(R.id.bt_qtlb));
		// 设置点击事件
		actionbar.setDisplayHomeAsUpEnabled(true);
		bt_dtcfs.setOnClickListener(this);
		bt_dzsb.setOnClickListener(this);
		bt_jbfs.setOnClickListener(this);
		bt_stroop.setOnClickListener(this);
		bt_hbqcs.setOnClickListener(this);
		bt_wjdc.setOnClickListener(this);
		bt_qtlb.setOnClickListener(this);
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
		Intent i = new Intent(this,TestActivity.class);
		int value = TestID.NONE;
		switch (v.getId()) {
		case R.id.bt_dtcfs:
			value = TestID.DTCFS;
			new Thread(new Runnable(){
                @Override
                public void run() {
                	SimpleDateFormat df = TimeUtils.getFormater();
        			String i = MyNet.post(MyNet.connecturl+"/testwithapp", "{testtype:'1', date:'"+df.format(new Date())+"',userid:'"+"1"+"'}");
        			System.out.println(""+i);
                }
            }).start();
			break;
		case R.id.bt_jbfs:
			value = TestID.JBFS;
			new Thread(new Runnable(){
                @Override
                public void run() {
                	SimpleDateFormat df = TimeUtils.getFormater();
        			String i = MyNet.post(MyNet.connecturl+"/testwithapp", "{testtype:'2', date:'"+df.format(new Date())+"',userid:'"+"1"+"'}");
        			System.out.println(""+i);
                }
            }).start();
			break;
		case R.id.bt_wjdc:
			value = TestID.BECK;
			new Thread(new Runnable(){
                @Override
                public void run() {
                	SimpleDateFormat df = TimeUtils.getFormater();
        			String i = MyNet.post(MyNet.connecturl+"/testwithapp", "{testtype:'3', date:'"+df.format(new Date())+"',userid:'"+"1"+"'}");
        			System.out.println(""+i);
                }
            }).start();
			break;
		case R.id.bt_hbqcs:
			value = TestID.HBQCS;
			new Thread(new Runnable(){
                @Override
                public void run() {
                	SimpleDateFormat df = TimeUtils.getFormater();
        			String i = MyNet.post(MyNet.connecturl+"/testwithapp", "{testtype:'4', date:'"+df.format(new Date())+"',userid:'"+"1"+"'}");
        			System.out.println(""+i);
                }
            }).start();
			break;
		case R.id.bt_stroop:
			value =TestID.STROOP;
			new Thread(new Runnable(){
                @Override
                public void run() {
                	SimpleDateFormat df = TimeUtils.getFormater();
        			String i = MyNet.post(MyNet.connecturl+"/testwithapp", "{testtype:'5', date:'"+df.format(new Date())+"',userid:'"+"1"+"'}");
        			System.out.println(""+i);
                }
            }).start();
			break;
		case R.id.bt_dzsb:
			value = TestID.DZSB;
			new Thread(new Runnable(){
                @Override
                public void run() {
                	SimpleDateFormat df = TimeUtils.getFormater();
        			String i = MyNet.post("http://192.168.1.102:8080/test", "{testtype:'6', date:'"+df.format(new Date())+"',userid:'"+"1"+"'}");
        			System.out.println(""+i);
        			String ii =  "{testtype:'6', date:'"+df.format(new Date())+"',userid:'"+"1"+"'}";
        			System.out.println(""+ii);
                }
            }).start();
			break;
		case R.id.bt_qtlb:
			//value = TestID.BECK;
			startActivity(new Intent(mContext, OtherTestSelectionActivity.class));
			return;
		}
		i.putExtra("type", value);
		this.startActivity(i);
	}
}
