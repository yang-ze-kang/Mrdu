package com.mrdu;

import java.util.ArrayList;
import java.util.List;

import com.mrdu.adapter.NewsViewPagerAdapter;
import com.mrdu.bean.NewsBean;
import com.mrdu.fragment.ArticleFragment;
import com.mrdu.fragment.VideoFragment;
import com.mrdu.view.MyBackTitleBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


public class NewsActivity extends FragmentActivity implements ViewPager.OnPageChangeListener ,View.OnClickListener {
	Context mcontext=this;
	NewsBean fbb = null;
	boolean sfbj=false;
	private MyBackTitleBar myBackTitleBar;//标题
	ViewPager vp;//翻页
	List<Fragment> fragments = new ArrayList<>();
	//控件
	private Button artitle;
	private Button video;
	private LinearLayout news;
	private FrameLayout videoFullFrag;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_main);
		InitView();
	}

	private void InitView() {
		videoFullFrag=(FrameLayout)findViewById(R.id.full_video);
		//初始化标题栏
		myBackTitleBar=(MyBackTitleBar)findViewById(R.id.my_back_title);
		myBackTitleBar.setTitle("健康科普");
		//初始化控件
		news=(LinearLayout)findViewById(R.id.news);
		artitle=(Button)findViewById(R.id.bt_artitle);
		video=(Button)findViewById(R.id.bt_video);
		artitle.setOnClickListener(this);
		video.setOnClickListener(this);
		//初始化翻页
		vp=(ViewPager)findViewById(R.id.vp);
		fragments.add(new ArticleFragment());
		fragments.add(new VideoFragment());
		vp.setAdapter(new NewsViewPagerAdapter(getSupportFragmentManager(),fragments));
		vp.setOnPageChangeListener(this);
		vp.setCurrentItem(0);
	}

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
		switch (position){
			case 0:
				artitle.setBackground(getResources().getDrawable(R.drawable.news_button_on_background));
				video.setBackground(getResources().getDrawable(R.drawable.news_button_off_background));
				break;
			case 1:
				artitle.setBackground(getResources().getDrawable(R.drawable.news_button_off_background));
				video.setBackground(getResources().getDrawable(R.drawable.news_button_on_background));
				break;
		}
    }

    @Override
    public void onPageScrollStateChanged(int state) {
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
			startActivity(new Intent(mcontext, PersonalActivity.class));
			return true;
		}
		return true;
	}
	@Override
	protected void onResume() {
		super.onResume();
		//refresh(extra);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (sfbj){
			this.finish();
		}
		
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()){
			case R.id.bt_artitle:
				vp.setCurrentItem(0);
				break;
			case R.id.bt_video:
				vp.setCurrentItem(1);
				break;
		}
	}
    public void hideView(){
	    if(news.getVisibility()==View.VISIBLE){
            news.setVisibility(View.GONE);
        }
        if(videoFullFrag.getVisibility()==View.GONE){
            videoFullFrag.setVisibility(View.VISIBLE);
        }
    }
    public void showView(){
        if(news.getVisibility()==View.GONE){
            news.setVisibility(View.VISIBLE);
        }
        if(videoFullFrag.getVisibility()==View.VISIBLE){
            videoFullFrag.setVisibility(View.GONE);
        }
    }
}
