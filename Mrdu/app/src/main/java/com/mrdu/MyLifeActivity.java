package com.mrdu;

import android.app.Activity;
import android.os.Bundle;

import com.mrdu.view.MyBackTitleBar;


public class MyLifeActivity extends Activity{

	private MyBackTitleBar myBackTitleBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mylife);

		//初始化标题
		myBackTitleBar=(MyBackTitleBar)findViewById(R.id.my_back_title);
		myBackTitleBar.setTitle("我的生活");
	}

	
}
