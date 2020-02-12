package com.mrdu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.mrdu.bean.TestID;
import com.mrdu.bean.TestResultBean;
import com.mrdu.helper.MyNet;
import com.mrdu.util.TimeUtils;
import com.mrdu.view.MyBackTitleBar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestResultActivity extends Activity {
	private TextView tvTestName;
	private TextView tvScore;
	private TextView tvTestIntro;

	SpannableString spannableString;
	AbsoluteSizeSpan fenSize;
	ForegroundColorSpan fencolor;
	private ForegroundColorSpan healthcolor;
	
	//数据
	private TestResultBean testResult;
	private int score;
	private int testId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_testresult);


		Intent i = getIntent();
		testResult = (TestResultBean) i.getSerializableExtra("result");
		score= (int) testResult.test_score;
		testId=(int) testResult.test_id;
		Log.v("TestResult","score:"+String.valueOf(score));
		Log.v("TestResult","TestType:"+String.valueOf(testId));

		initView();


	}

	public void initView(){
		//初始化标题
		MyBackTitleBar myBackTitleBar=(MyBackTitleBar) findViewById(R.id.my_back_title);
		myBackTitleBar.setTitle("测试结果");

		tvTestName=(TextView) findViewById(R.id.tv_test_name);
		tvScore=(TextView)findViewById(R.id.tv_score);
		tvTestIntro=(TextView)findViewById(R.id.tv_test_intro);
		//设置结果UI
		spannableString = new SpannableString(String.valueOf(score)+"分");
		fenSize = new AbsoluteSizeSpan(40,true);
		fencolor = new ForegroundColorSpan(getResources().getColor(R.color.black));
		if(score<10){
			spannableString.setSpan(fenSize,1,2,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
			spannableString.setSpan(fencolor,1,2,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		}else{
			spannableString.setSpan(fenSize,2,3,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
			spannableString.setSpan(fencolor,2,3,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		}
		healthcolor=null;
		switch (testResult.test_id) {
			case TestID.BECK:
				showQuestionareResult(testResult);
				break;
			case TestID.JBFS:
				showGradualFormResult(testResult);
				break;
			case TestID.DTCFS:
				showPointDetectFormResult(testResult);
				break;
			case TestID.HBQCS:
				showHongBiaoQingCeShiResule(testResult);
				break;
			case TestID.DZSB:
				showShortEmotionResult(testResult);
				break;
			case TestID.STROOP:
				showStroopResult(testResult);
				break;
			default:

		}
	}

	private void showStroopResult(TestResultBean testResult) {
		tvTestName.setText("Stroop测试");
		if (score <= 13) {
			healthcolor = new ForegroundColorSpan(getResources().getColor(R.color.healthState1));
		} else if (score <= 19) {
			healthcolor = new ForegroundColorSpan(getResources().getColor(R.color.healthState2));
		} else if (score <= 28) {
			healthcolor = new ForegroundColorSpan(getResources().getColor(R.color.healthState3));
		} else {
			healthcolor = new ForegroundColorSpan(getResources().getColor(R.color.healthState4));
		}
		if(score<10){
			spannableString.setSpan(healthcolor,0,1,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		}else{
			spannableString.setSpan(healthcolor,0,2,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		}
		tvScore.setText(spannableString);
		tvTestIntro.setText("抑郁情况认定结果待写");
		//tv2.setText("您的得分为：" + testResult.test_score);
		// TODO Auto-generated method stub
		new Thread(new Runnable(){
            @Override
            public void run() {
            	SimpleDateFormat df = TimeUtils.getFormater();
    			String i = MyNet.post("http://192.168.1.103:8080/testwithapp", "{testtype:'1', date:'"+df.format(new Date(0))+"',userid:'"+"1"+"'}");
    			System.out.println(""+i);
            }
        }).start();
//		tv2.setText("您的得分为：65" +"\n该测试下60分为测试合格，该测试判断您情绪识别中的抗干扰能力\n您目前情绪识别抗干扰能力正常，较健康");
	}

	private void showShortEmotionResult(TestResultBean testResult) {
		int score = (int) testResult.test_score;
		tvTestName.setText("短暂识别");
		if (score <= 13) {
			healthcolor = new ForegroundColorSpan(getResources().getColor(R.color.healthState1));
		} else if (score <= 19) {
			healthcolor = new ForegroundColorSpan(getResources().getColor(R.color.healthState2));
		} else if (score <= 28) {
			healthcolor = new ForegroundColorSpan(getResources().getColor(R.color.healthState3));
		} else {
			healthcolor = new ForegroundColorSpan(getResources().getColor(R.color.healthState4));
		}
		if(score<10){
			spannableString.setSpan(healthcolor,0,1,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		}else{
			spannableString.setSpan(healthcolor,0,2,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		}
		tvScore.setText(spannableString);
		tvTestIntro.setText("抑郁情况认定结果待写");
		// TODO Auto-generated method stub
		//tv2.setText("您的得分为：" + testResult.test_score);
//		tv2.setText("您的得分为：57" +"\n该测试下60分为测试合格，该测试检验您对短暂出现的表情的识别能力\n您目前可能有较强的抑郁倾向，请再多次尝试各项类型测试，若均得分较低请及时就医");
	}

	private void showHongBiaoQingCeShiResule(TestResultBean testResult) {
		int score = (int) testResult.test_score;
		tvTestName.setText("宏表情测试");
		if (score <= 13) {
			healthcolor = new ForegroundColorSpan(getResources().getColor(R.color.healthState1));
		} else if (score <= 19) {
			healthcolor = new ForegroundColorSpan(getResources().getColor(R.color.healthState2));
		} else if (score <= 28) {
			healthcolor = new ForegroundColorSpan(getResources().getColor(R.color.healthState3));
		} else {
			healthcolor = new ForegroundColorSpan(getResources().getColor(R.color.healthState4));
		}
		if(score<10){
			spannableString.setSpan(healthcolor,0,1,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		}else{
			spannableString.setSpan(healthcolor,0,2,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		}
		tvScore.setText(spannableString);
		tvTestIntro.setText("抑郁情况认定结果待写");
	}

	private void showPointDetectFormResult(TestResultBean testResult) {
		int score = (int) testResult.test_score;
		tvTestName.setText("点探测范式");
		if (score <= 13) {
			healthcolor = new ForegroundColorSpan(getResources().getColor(R.color.healthState1));
		} else if (score <= 19) {
			healthcolor = new ForegroundColorSpan(getResources().getColor(R.color.healthState2));
		} else if (score <= 28) {
			healthcolor = new ForegroundColorSpan(getResources().getColor(R.color.healthState3));
		} else {
			healthcolor = new ForegroundColorSpan(getResources().getColor(R.color.healthState4));
		}
		if(score<10){
			spannableString.setSpan(healthcolor,0,1,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		}else{
			spannableString.setSpan(healthcolor,0,2,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		}
		tvScore.setText(spannableString);
		tvTestIntro.setText("抑郁情况认定结果待写");
//		tv1.setText("点探测范式测试结束");
		//tv2.setText("您的得分为：" + testResult.test_score);
//		tv2.setText("您的得分为: 85" +"\n该测试下60分为测试合格，该测试根据您的注意偏向检验您是否患有抑郁症\n您目前的注意偏向正常，十分健康");
	}

	private void showGradualFormResult(TestResultBean testResult) {
		int score = (int) testResult.test_score;
		tvTestName.setText("渐变范式");
		if (score <= 13) {
			healthcolor = new ForegroundColorSpan(getResources().getColor(R.color.healthState1));
		} else if (score <= 19) {
			healthcolor = new ForegroundColorSpan(getResources().getColor(R.color.healthState2));
		} else if (score <= 28) {
			healthcolor = new ForegroundColorSpan(getResources().getColor(R.color.healthState3));
		} else {
			healthcolor = new ForegroundColorSpan(getResources().getColor(R.color.healthState4));
		}
		if(score<10){
			spannableString.setSpan(healthcolor,0,1,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		}else{
			spannableString.setSpan(healthcolor,0,2,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		}
		tvScore.setText(spannableString);
		tvTestIntro.setText("抑郁情况认定结果待写");
	}

	private void showQuestionareResult(TestResultBean testResult) {
		int score = (int) testResult.test_score-20;
		Log.e("yang","show questionare result:"+String.valueOf(score));
		tvTestName.setText("贝克抑郁自评量表");
		if (score <= 13) {
            healthcolor = new ForegroundColorSpan(getResources().getColor(R.color.healthState1));
		} else if (score <= 19) {
            healthcolor = new ForegroundColorSpan(getResources().getColor(R.color.healthState2));
		} else if (score <= 28) {
            healthcolor = new ForegroundColorSpan(getResources().getColor(R.color.healthState3));
		} else {
            healthcolor = new ForegroundColorSpan(getResources().getColor(R.color.healthState4));
		}
		if(score<10){
			spannableString.setSpan(healthcolor,0,1,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		}else{
			spannableString.setSpan(healthcolor,0,2,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		}
		tvScore.setText(spannableString);
		tvTestIntro.setText("得分小于等于 13 分被认为精神状态良好\n" +
				"在 8 到 20分被认为有轻度情绪不良\n" +
				"在 20 到 35 分被认为可能有中度抑郁\n" +
				 "大于 35 分则被认为患有严重的抑郁症");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		}
		return true;
	}

	@Override
	public void onBackPressed() {
		startActivity(new Intent(this, MainActivity.class));
	}
}
