package com.mrdu.tests;

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

import com.mrdu.R;
import com.mrdu.TestActivity;
import com.mrdu.bean.QuestionBean;
import com.mrdu.bean.TestBean;
import com.mrdu.bean.TestID;
import com.mrdu.bean.UserBean;
import com.mrdu.util.MyApplication;

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

public class GradualFormFragment extends Fragment implements OnClickListener {

    private TestActivity testActivity;
    private Context mContext;

    //UI
    private ImageView photo;

    //数据
    private TestBean testBean;
    private UserBean user;
    private long lastTime;
    private long currentTime;

    //网络
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");//JSON类型

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                startTest();
            } else if (msg.what == 2) {
                Bitmap bitmap = (Bitmap) msg.obj;
                photo.setImageBitmap(bitmap);
                lastTime = SystemClock.currentThreadTimeMillis();
            }else if(msg.what==3){
                Toast.makeText(mContext,"网络信号差！",Toast.LENGTH_SHORT).show();
                testActivity.finish();
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // [1]通过打气筒把一个布局转换成view对象
        View view = inflater.inflate(R.layout.fragment_jianbianfanshi, container, false);
        photo = (ImageView) view.findViewById(R.id.iv_photo);
        Button answer1 = (Button) view.findViewById(R.id.JBFS);
        Button answer2 = (Button) view.findViewById(R.id.DZSB);
        Button answer3 = (Button) view.findViewById(R.id.DTCFS);
        Button answer4 = (Button) view.findViewById(R.id.HBQCS);
        Button answer5 = (Button) view.findViewById(R.id.STROOP);
        //Button answer6 = (Button) view.findViewById(R.user_id.button6);
        //Button answer7 = (Button) view.findViewById(R.user_id.button7);
        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);
        answer4.setOnClickListener(this);
        answer5.setOnClickListener(this);
        //answer6.setOnClickListener(this);
        //answer7.setOnClickListener(this);


        //初始化数据
        mContext = getActivity();
        testActivity = (TestActivity) getActivity();
        MyApplication app = (MyApplication) mContext.getApplicationContext();
        user = app.getUser();

        testBean = new TestBean(user.user_id, TestID.JBFS);
        try {
            postTestGetImage();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    public void startTest() {
        if (testBean.getNumberOfQuestions() > 0)
            postSetImage();
        else {
            Toast.makeText(mContext, "网络请求错误!", Toast.LENGTH_SHORT).show();
            testActivity.finish();
        }
    }



    @Override
    public void onClick(View v) {
        currentTime = SystemClock.currentThreadTimeMillis();
        long reationTime = currentTime - lastTime;

        switch (v.getId()) {
            case R.id.JBFS:
                testBean.doAnswer(1, reationTime);
                break;
            case R.id.DZSB:
                testBean.doAnswer(2, reationTime);
                break;
            case R.id.DTCFS:
                testBean.doAnswer(3, reationTime);
                break;
            case R.id.HBQCS:
                testBean.doAnswer(4, reationTime);
                break;
            case R.id.STROOP:
                testBean.doAnswer(5, reationTime);
                break;
	/*	case R.user_id.button6:
			testBean.doAnswer(6);
			break;
		case R.user_id.button7:
			testBean.doAnswer(7);
			break;*/
        }
        if (testBean.getCurrentQuestion() < testBean.getNumberOfQuestions()) {
            postSetImage();
        } else {
            testActivity.onAnswer(testBean);
        }
    }

    //请求网络图片uri
    private void postTestGetImage() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id", user.user_id);
        jsonObject.put("test_type", "JBFS");
        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(jsonObject));
        Request request = new Request.Builder()
                .url(getResources().getString(R.string.url_test))
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("GrandFormFragment","response failed");
                handler.sendEmptyMessage(3);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    Log.v("GrandFormFragment", jsonArray.toString());
                    testBean.setPictureUri(jsonArray);
                } catch (JSONException e) {
                    Log.v("GrandForm","json error");
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
            }
        });
    }

    //请求网络图片
    private void postSetImage() {
        Request request = new Request.Builder()
                .url(testBean.getPictureUri(testBean.getCurrentQuestion()))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(3);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                handler.obtainMessage(2, bitmap).sendToTarget();
            }
        });
    }
}
