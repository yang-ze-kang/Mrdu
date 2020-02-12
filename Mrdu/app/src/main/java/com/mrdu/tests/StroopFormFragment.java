package com.mrdu.tests;

import java.io.IOException;
import java.io.InputStream;
import java.util.PriorityQueue;
import java.util.Random;

import com.mrdu.R;
import com.mrdu.TestActivity;
import com.mrdu.bean.QuestionBean;
import com.mrdu.bean.TestBean;
import com.mrdu.bean.TestID;
import com.mrdu.bean.UserBean;
import com.mrdu.util.MyApplication;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StroopFormFragment extends Fragment implements OnClickListener {

    //app
    private Context mContent;
    private TestActivity testActivity;

    //view
    private ImageView photo;
    private Questionare qn;
    private Button answerButton1;
    private Button answerButton2;
    private Button answerButton3;
    private TextView disturb;
    private Rect disRect;

    //data
    private long lastTime;
    private long currentTime;
    private UserBean user;
    private TestBean testBean;

    //网络
    private OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");//JSON类型

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    photo.setImageBitmap(bitmap);
                    lastTime = SystemClock.currentThreadTimeMillis();
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // [1]通过打气筒把一个布局转换成view对象
        View view = inflater.inflate(R.layout.fragment_stroop, container, false);
        photo = (ImageView) view.findViewById(R.id.iv_photo);
        answerButton1 = (Button) view.findViewById(R.id.JBFS);
        answerButton2 = (Button) view.findViewById(R.id.DZSB);
        answerButton3 = (Button) view.findViewById(R.id.DTCFS);
        answerButton1.setOnClickListener(this);
        answerButton2.setOnClickListener(this);
        answerButton3.setOnClickListener(this);
        disturb = (TextView) view.findViewById(R.id.textViewDisturb);
        //app
        mContent = getActivity();
        testActivity = (TestActivity) getActivity();
        //data
        testBean = testActivity.testBean;

        setQuestion();
        return view;
    }

    private void setQuestion() {
        postSetImage();
        disturb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 100);
        Random r = new Random();
        int did = r.nextInt(5);
        switch (did) {
            case 0:
                disturb.setText("高兴");
                break;
            case 1:
                disturb.setText("悲伤");
                break;
            case 2:
                disturb.setText("恐惧");
                break;
            case 3:
                disturb.setText("厌恶");
                break;
            case 4:
                disturb.setText("愤怒");
                break;
            case 5:
                disturb.setText("中性");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        currentTime = SystemClock.currentThreadTimeMillis();
        long time = lastTime - currentTime;
        switch (v.getId()) {
            case R.id.JBFS:
                testBean.doAnswer(1, time);
                break;
            case R.id.DZSB:
                testBean.doAnswer(2, time);
                break;
            case R.id.DTCFS:
                testBean.doAnswer(3, time);
                break;
        }
        if (testBean.getCurrentQuestion() < testBean.getNumberOfQuestions()) {
            setQuestion();
        } else {
            testActivity.onAnswer(testBean);
        }
    }

    //请求网络图片
    private void postSetImage() {
        Request request = new Request.Builder()
                .url(testBean.getPictureUri(testBean.getCurrentQuestion())).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                handler.obtainMessage(1, bitmap).sendToTarget();
            }
        });
    }

}
