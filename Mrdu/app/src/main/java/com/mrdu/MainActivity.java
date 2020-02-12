package com.mrdu;



import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;

import com.moxun.tagcloudlib.view.TagCloudView;
import com.mrdu.bean.UserBean;
import com.mrdu.helper.MyDatabase;


import com.mrdu.util.MyApplication;

import com.mrdu.view.MyUserTitleBar;
import com.mrdu.view.TextTagsAdapter;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Context mcontext = this;
	private MyDatabase db;
	TextTagsAdapter textTagsAdapter;
	private TagCloudView tagCloudView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyUserTitleBar.getTitleBar(this);
		setContentView(R.layout.activity_main);
		// Toast.makeText(mcontext, "jinru", 0).show();

		//初始化卫星菜单控件
		IniteStatelliteMenu();

		//初始化立体选择控件
		textTagsAdapter = new TextTagsAdapter(new String[6]);
		tagCloudView = (TagCloudView) findViewById(R.id.tag_cloud);
		tagCloudView.setAdapter(textTagsAdapter);

		//TextView textview = (TextView) findViewById(R.id.textView3);
		MyApplication app = (MyApplication) MainActivity.this.getApplication();
		UserBean user = app.getUser();
	}

	void IniteStatelliteMenu(){

		ImageView icon = new ImageView(this);
		icon.setImageDrawable(getResources().getDrawable(R.drawable.action_new));
		FloatingActionButton actionButton=new FloatingActionButton.Builder(this)
					.setContentView(icon)
				.setLayoutParams(new FloatingActionButton.LayoutParams(250,250))
				.setBackgroundDrawable(R.drawable.button_blue_touch).build();

		SubActionButton.Builder itemBuilder=new SubActionButton.Builder(this);
		TextView textView1=new TextView(this);
		TextView textView2=new TextView(this);
		TextView textView3=new TextView(this);
		TextView textView4=new TextView(this);
		textView1.setText("科普");
		textView1.setTextColor(getResources().getColor(R.color.floralwhite));
		textView1.setTypeface(Typeface.SANS_SERIF,Typeface.ITALIC);
		textView2.setText("练习");
		textView2.setTextColor(getResources().getColor(R.color.floralwhite));
		textView2.setTypeface(Typeface.SANS_SERIF,Typeface.ITALIC);
		textView3.setText("结果");
		textView3.setTextColor(getResources().getColor(R.color.floralwhite));
		textView3.setTypeface(Typeface.SANS_SERIF,Typeface.ITALIC);
		textView4.setText("我的");
		textView4.setTextColor(getResources().getColor(R.color.floralwhite));
		textView4.setTypeface(Typeface.SANS_SERIF,Typeface.ITALIC);

		SubActionButton button1=itemBuilder.setContentView(textView1)
				.setLayoutParams(new FrameLayout.LayoutParams(200,200))
				.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_blue)).build();
		SubActionButton button2=itemBuilder.setContentView(textView2).build();
		SubActionButton button3=itemBuilder.setContentView(textView3).build();
		SubActionButton button4=itemBuilder.setContentView(textView4).build();


		FloatingActionMenu actionMenu=new FloatingActionMenu.Builder(this)
				.setRadius(400)
				.addSubActionView(button4)
				.addSubActionView(button3)
				.addSubActionView(button2)
				.addSubActionView(button1)
				.attachTo(actionButton).build();

		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(mcontext, NewsActivity.class));
			}
		});
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(mcontext, EmotionTrainActivity.class));
			}
		});
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(mcontext, ResultActivity.class));
			}
		});
		button4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(mcontext, PersonalActivity.class));
			}
		});
	}

	
	
	@Override
	protected void onResume() {
		super.onResume();
		MyApplication app = (MyApplication) MainActivity.this.getApplication();
		UserBean user = app.getUser();
		if (user == null) {
			finish();
		}
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int itemId = item.getItemId();

		// 检测菜单栏目的个人中心点击事件
		if (itemId == R.id.personal) {
			// 跳转到个人中心
			startActivity(new Intent(mcontext, PersonalActivity.class));
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



	private boolean mIsExit;

	@Override
	/**
	 * 双击返回键退出
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mIsExit) {
				this.finish();

			} else {
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
				mIsExit = true;
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						mIsExit = false;
					}
				}, 2000);
			}
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
