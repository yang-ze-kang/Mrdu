package com.mrdu;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.mrdu.view.MyBackTitleBar;

public class EmotionTrainActivity extends Activity {
	Context mContext=this;
	private MyBackTitleBar myBackTitleBar;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emotiontrain );
		//初始化标题
		myBackTitleBar=(MyBackTitleBar)findViewById(R.id.my_back_title);
		myBackTitleBar.setTitle("情绪锻炼");

		Button buttona=(Button) this.findViewById(R.id.JBFS);
		Button buttonb=(Button) this.findViewById(R.id.DZSB);

		final Context context = this;
		//点击按钮 跳转到activity_mylife.xml页面
		buttonb.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context,MyLifeActivity.class ));
			}
		});
		}
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();return true;
		case R.id.personal:
			startActivity(new Intent(mContext, PersonalActivity.class));
			return true;
		}
		return true;
	}

}
