package com.mrdu.tests;
import com.mrdu.R;
import com.mrdu.TestActivity;
import com.mrdu.bean.TestBean;
import com.mrdu.bean.TestID;
import com.mrdu.bean.UserBean;
import com.mrdu.util.MyApplication;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HongbiaoqingFragment extends Fragment implements OnClickListener {

	private Context mContext;
	private TestActivity testActivity;
	private UserBean user;
	private ImageView photo;
	private Button answerButton1;
	private Button answerButton2;
	private Button answerButton3;
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");//JSON类型
	private OkHttpClient client=new OkHttpClient();//网络请求

	private TestBean testBean;
	private long lastTime;
	private long currentTime;

	@SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what==1){//放置图片
				Bitmap bitmap=(Bitmap)msg.obj;
				photo.setImageBitmap(bitmap);
				lastTime=SystemClock.currentThreadTimeMillis();
			}else if(msg.what==2){//图片uri请求成功，放置测试
				startTest();
			}else if(msg.what==3){
				Toast.makeText(mContext,"网络请求失败！",Toast.LENGTH_SHORT).show();
				finishTest();
			}
		}
	};


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		   Bundle savedInstanceState) {
		mContext=getActivity();
		// 初始化布局
		View view = inflater.inflate(R.layout.fragment_hongbiaoqing,container, false);
		photo = (ImageView) view.findViewById(R.id.iv_photo);
		answerButton1 = (Button) view.findViewById(R.id.bt_positive);
		answerButton2 = (Button) view.findViewById(R.id.bt_normal);
		answerButton3 = (Button) view.findViewById(R.id.bt_negative);
		answerButton1.setOnClickListener(this);
		answerButton2.setOnClickListener(this);
		answerButton3.setOnClickListener(this);

		//初始化测试数据
		testActivity=(TestActivity) getActivity();

		MyApplication app=(MyApplication)mContext.getApplicationContext();
		user=app.getUser();
		testBean=new TestBean(user.user_id,TestID.HBQCS);


		try {
			postTestGetImage();//请求网络图片路径
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return view;
	}

	public void startTest(){
	    if(testBean.getNumberOfQuestions()>0)
    		postSetImage();
	    else{
			Toast.makeText(mContext,"网络请求错误!",Toast.LENGTH_SHORT).show();
			finishTest();
		}
	}


	//请求网络图片
	private void postSetImage() {
		Request request=new Request.Builder()
				.url(testBean.getPictureUri(testBean.getCurrentQuestion())).build();
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
				handler.sendEmptyMessage(3);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				InputStream inputStream=response.body().byteStream();
				Bitmap bitmap=BitmapFactory.decodeStream(inputStream);
				handler.obtainMessage(1,bitmap).sendToTarget();
			}
		});
	}

	//请求网络图片uri
	private void postTestGetImage() throws JSONException {
		//请求内容
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("user_id",user.user_id);
		jsonObject.put("test_type","HBQCS");
		RequestBody requestBody=RequestBody.create(JSON, String.valueOf(jsonObject));
		Request request=new Request.Builder()
				.url(getResources().getString(R.string.url_test))
				.post(requestBody)
				.build();
		//请求任务
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				handler.sendEmptyMessage(3);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				//Log.v("HBQCS","response:"+response.body().string());
				try {
					JSONArray jsonArray = new JSONArray(response.body().string());
					Log.v("GrandResponse:",jsonArray.toString());
					testBean.setPictureUri(jsonArray);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessage(2);
			}
		});
	}

	@Override
	public void onClick(View v) {
		TestActivity activity = (TestActivity) this.getActivity();
		currentTime=SystemClock.currentThreadTimeMillis();
		long reationTime=currentTime-lastTime;

		switch (v.getId()) {
		case R.id.bt_positive:
			testBean.doAnswer(1,reationTime);
			break;
		case R.id.bt_normal:
			testBean.doAnswer(2,reationTime);
			break;
		case R.id.bt_negative:
			testBean.doAnswer(3,reationTime);
			break;
		}

		if (testBean.getCurrentQuestion()<testBean.getNumberOfQuestions()) {
			postSetImage();
		} else {
			activity.onAnswer(testBean);
		}
	}

	//结束测试
	private void finishTest() {
		TestActivity activity = (TestActivity) this.getActivity();
		activity.finish();
	}

}
