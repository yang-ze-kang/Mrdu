package com.mrdu.tests;

import com.mrdu.R;
import com.mrdu.TestActivity;
import com.mrdu.bean.QuestionBean;
import com.mrdu.bean.TestBean;
import com.mrdu.bean.UserBean;
import com.mrdu.util.MyApplication;

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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

public class ShortEmotionFragment extends Fragment implements OnClickListener {

    //app
    private Context mContent;
    private TestActivity testActivity;

    private ImageView photo;

    private Button answerButton1;
    private Button answerButton2;
    private Button answerButton3;
    private TextView t;

    //data
    private long lastTime;
    private long currentTime;
    private UserBean user;
    private TestBean testBean;

    //web
    private OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");//JSON类型

    private Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            photo.setVisibility(View.INVISIBLE);
        }
    };
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                t.setText("判断何种情绪类别");
                answerButton1.setVisibility(View.VISIBLE);
                answerButton2.setVisibility(View.VISIBLE);
                answerButton3.setVisibility(View.VISIBLE);
                postSetImage();
            }else if(msg.what==2){
                Bitmap bitmap=(Bitmap)msg.obj;
                photo.setImageBitmap(bitmap);
                photo.setVisibility(View.VISIBLE);
                lastTime =SystemClock.currentThreadTimeMillis();
                h.sendEmptyMessageDelayed(1,500);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // [1]通过打气筒把一个布局转换成view对象
        View view = inflater.inflate(R.layout.fragment_shortemotion, container, false);
        photo = (ImageView) view.findViewById(R.id.iv_photo);
        answerButton1 = (Button) view.findViewById(R.id.JBFS);
        answerButton2 = (Button) view.findViewById(R.id.DZSB);
        answerButton3 = (Button) view.findViewById(R.id.DTCFS);
        answerButton1.setOnClickListener(this);
        answerButton2.setOnClickListener(this);
        answerButton3.setOnClickListener(this);
        t = (TextView) view.findViewById(R.id.textView1);

        //app
        testActivity=(TestActivity)getActivity();
        mContent = getActivity();
        MyApplication app = (MyApplication) mContent.getApplicationContext();
        user = app.getUser();
        //data
        testBean = testActivity.testBean;

        tips();
        return view;
    }

    private void tips() {
        t.setText("准备开始测试");
        answerButton1.setVisibility(View.INVISIBLE);
        answerButton2.setVisibility(View.INVISIBLE);
        answerButton3.setVisibility(View.INVISIBLE);
        int r = getResources().getIdentifier("happy2", "drawable", "com.mrdu");
        photo.setImageResource(r);
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessageDelayed(1, 2000);
            }

        }).start();
    }


    @Override
    public void onClick(View v) {
        currentTime=SystemClock.currentThreadTimeMillis();
        long time=currentTime- lastTime;
        switch (v.getId()) {
            case R.id.JBFS:
                testBean.doAnswer(1,time);
                break;
            case R.id.DZSB:
                testBean.doAnswer(1,time);
                break;
            case R.id.DTCFS:
                testBean.doAnswer(1,time);
                break;
        }
        if (testBean.getCurrentQuestion()<testBean.getNumberOfQuestions()) {
            postSetImage();
        } else {
            testActivity.onAnswer(testBean);
        }
    }

    //请求网络图片uri
    private void postTestGetImage() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id", user.user_id);
        jsonObject.put("test_type", "DZSB");
        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(jsonObject));
        Request request = new Request.Builder()
                .url(getResources().getString(R.string.url_test))
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(3);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    Log.v("GrandResponse:", jsonArray.toString());
                    testBean.setPictureUri(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //请求网络图片
    private void postSetImage() {
        Request request=new Request.Builder()
                .url(testBean.getPictureUri(testBean.getCurrentQuestion())).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream=response.body().byteStream();
                Bitmap bitmap=BitmapFactory.decodeStream(inputStream);
                handler.obtainMessage(2,bitmap).sendToTarget();
            }
        });
    }
}
