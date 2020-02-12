package com.mrdu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mrdu.bean.TestBean;
import com.mrdu.bean.TestID;
import com.mrdu.bean.TestResultBean;
import com.mrdu.bean.TestResultResponse;
import com.mrdu.bean.UserBean;
import com.mrdu.tests.GradualFormFragment;
import com.mrdu.tests.HongbiaoqingFragment;
import com.mrdu.tests.PointDetectFormFragment;
import com.mrdu.tests.QuestionareFragment;
import com.mrdu.tests.ResultUploadingFragment;
import com.mrdu.tests.ShortEmotionFragment;
import com.mrdu.tests.StroopFormFragment;
import com.mrdu.util.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@SuppressLint("HandlerLeak")
public class TestActivity extends Activity {
    private Context mcontext = this;

    //UI
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private TextView testIntro;
    private TextView testName;
    private Button startTest;
    private ImageView picture;
    private LinearLayout linearLayout;

    //数据
    private int testTypeID;
    private UserBean user;
    private TestResultBean testResult;
    public TestBean testBean;

    //网络请求
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");//JSON类型
    private OkHttpClient client = new OkHttpClient();//网络请求
    private Intent nextActivity;

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {//网络请求成功
                TestResultResponse beck = null;
                String responseResult = (String) msg.obj;
                Log.v("TestActivityResponse", responseResult);
                beck = JsonToTestResultResponse(responseResult);
                if (beck != null) {
                    if (beck.status == 201) {
                        Log.e("TestAvtivity", "数据上传成功");
                        Toast.makeText(mcontext, "数据上传成功", Toast.LENGTH_SHORT).show();
                    } else if (beck.status == 403) {
                        Log.e("TestAvtivity", "数据上传失败");
                        Toast.makeText(mcontext, "数据上传失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("TestAvtivity", "数据上传失败");
                    Toast.makeText(mcontext, "数据上传失败", Toast.LENGTH_SHORT).show();
                }
                TestActivity.this.startActivity(nextActivity);
                TestActivity.this.finish();
            } else if (msg.what == 2) {//网络请求失败
                Toast.makeText(mcontext, "网络请求失败！", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private TestResultResponse JsonToTestResultResponse(String responseResult) {
        TestResultResponse response = new TestResultResponse();
        try {
            JSONObject json = new JSONObject(responseResult);
            response.status = (int) json.get("status");
            response.error=(String)json.get("error");
            Log.v("TestActivityResponse", String.valueOf(response.status));
            Log.v("TestActivityResponse", String.valueOf(response.error));
        } catch (JSONException e) {
            Log.e("TestActivityResponse", "服务器返回数据解析错误");
            e.printStackTrace();
            return null;
        }
        return response;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //resultutil = new ResultUtilSQL(this);
        //初始化数据
        try {
            initData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //初始化控件
        InitView();
        //初始化事件
        initEvent();


    }

    private void initEvent() {
        startTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTest(testTypeID);
            }
        });
    }

    private void initData() throws JSONException {
        Intent intent = this.getIntent();
        testTypeID = intent.getIntExtra("type", TestID.NONE);
        nextActivity = new Intent(TestActivity.this, TestResultActivity.class);
        MyApplication app = (MyApplication) this.getApplication();
        user = app.getUser();
        switch (testTypeID) {
            case TestID.JBFS:
                testBean=new TestBean(user.user_id,TestID.JBFS);
                postTestGetImage("JBFS");
                testResult = new TestResultBean("JBFS");
                break;
            case TestID.DTCFS:
                testBean=new TestBean(user.user_id,TestID.DTCFS);
                postTestGetImage("DTCFS");
                testResult = new TestResultBean("DTCFS");
                break;
            case TestID.BECK:
                testResult = new TestResultBean("BECK");
                break;
            case TestID.HBQCS:
                testResult = new TestResultBean("HBQCS");
                break;
            case TestID.STROOP:
                testBean=new TestBean(user.user_id,TestID.STROOP);
                postTestGetImage("STROOP");
                testResult = new TestResultBean("STROOP");
                break;
            case TestID.DZSB:
                testBean=new TestBean(user.user_id,TestID.DZSB);
                postTestGetImage("DZSB");
                testResult = new TestResultBean("DZSB");
                break;
            default:
                onBackPressed();
                break;
        }
    }

    private void InitView() {

        // [1]获取Fragment的管理者
        fragmentManager = getFragmentManager();
        // [2]开启事务
        transaction = fragmentManager.beginTransaction();

        testIntro = (TextView) findViewById(R.id.tv_test_intro);
        testName = (TextView) findViewById(R.id.tv_test_name);
        startTest = (Button) findViewById(R.id.bt_startTest);
        picture = (ImageView) findViewById(R.id.iv_picture);
        linearLayout = (LinearLayout) findViewById(R.id.frag);
        //随机图片
        Random random = new Random();
        int num = random.nextInt(9) + 1;
        String src = "test_intro" + num;
        String typ = "drawable";
        String packge = "com.mrdu";
        int rid = getResources().getIdentifier(src, typ, packge);
        picture.setBackgroundResource(rid);
        //测试介绍
        switch (testTypeID) {
            case TestID.JBFS:
                testIntro.setText(R.string.test_jbfs);
                testName.setText("渐变范式");
                break;
            case TestID.DTCFS:
                testIntro.setText(R.string.test_dtcfs);
                testName.setText("点探测范式");
                break;
            case TestID.BECK:
                testIntro.setText(R.string.test_beck);
                testName.setText("贝克抑郁量表");
                break;
            case TestID.HBQCS:
                testIntro.setText(R.string.test_hbqcs);
                testName.setText("宏表情测试");
                break;
            case TestID.STROOP:
                testIntro.setText(R.string.test_stroop);
                testName.setText("Stroop测试");
                break;
            case TestID.DZSB:
                testIntro.setText(R.string.test_dzsb);
                testName.setText("短暂识别");
                break;
            default:
                onBackPressed();
                break;
        }
        SpannableStringBuilder span = new SpannableStringBuilder("缩进" + testIntro.getText());
        span.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        testIntro.setText(span);
    }

    void startTest(int testType) {

        // [3]动态替换
        linearLayout.setBackground(getResources().getDrawable(R.color.background_blue));
        switch (testType) {
            case TestID.JBFS:
                transaction.replace(R.id.frag, new GradualFormFragment());
                break;
            case TestID.DTCFS:
                transaction.replace(R.id.frag, new PointDetectFormFragment());
                break;
            case TestID.BECK:
                transaction.replace(R.id.frag, new QuestionareFragment());
                break;
            case TestID.HBQCS:
                transaction.replace(R.id.frag, new HongbiaoqingFragment());
                break;
            case TestID.STROOP:
                transaction.replace(R.id.frag, new StroopFormFragment());
                break;
            case TestID.DZSB:
                transaction.replace(R.id.frag, new ShortEmotionFragment());
                break;
            default:
                Toast.makeText(mcontext, "此测试暂未开放", Toast.LENGTH_SHORT).show();
                onBackPressed();
                break;
        }
        // [4]最后一步 记得commit
        transaction.commit();
    }

    public void onAnswer(TestBean result) {
        // 加载圈
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frag, new ResultUploadingFragment());
        transaction.commit();

        testResult.setResult(result);
        Log.v("TestActivity","test_score:"+String.valueOf(testResult.test_score));
        Log.v("TestActivity","test_id:"+String.valueOf(testResult.test_id));
        nextActivity.putExtra("result", new TestResultBean(testResult));
        String resultJson = new Gson().toJson(testResult);
        Log.v("TestActivityResult:", resultJson);
        postTestResult(resultJson);

    }


    private void postTestResult(String resultJson) {
        RequestBody requestBody = RequestBody.create(JSON, resultJson);
        final Request request = new Request.Builder()
                .url(getResources().getString(R.string.url_result))
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handler.obtainMessage(1, response.body().string()).sendToTarget();
                Log.e("TestActivity", "网络请求成功");
            }
        });
    }

    //请求网络图片uri
    private void postTestGetImage(String testType) throws JSONException {

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("user_id",user.user_id);
        jsonObject.put("test_type",testType);
        RequestBody requestBody=RequestBody.create(JSON, String.valueOf(jsonObject));
        Request request=new Request.Builder()
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
                    Log.v("GrandResponse:",jsonArray.toString());
                    testBean.setPictureUri(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                handler.sendEmptyMessage(3);
            }
        });
    }
}
