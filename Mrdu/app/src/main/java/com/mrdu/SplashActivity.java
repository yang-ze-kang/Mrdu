package com.mrdu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.mrdu.bean.JsonBean;
import com.mrdu.bean.UserBean;
import com.mrdu.util.MyApplication;
import com.mrdu.util.SaveUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SplashActivity extends Activity {
    Context mContext = this;
    ImageView imageView;
    UserBean ub = new UserBean();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                if (!ub.username.equals("") && !ub.password.equals("")) {
                    postLogInRequest();
                } else {
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                    SplashActivity.this.finish();
                }
            } else if (msg.what == 1) {
                String returnMessage = (String) msg.obj;
                try {
                    JSONObject jsonObject = new JSONObject(returnMessage);
                    int status = jsonObject.getInt("status");
                    if (status == 200) {
                        MyApplication app = (MyApplication) SplashActivity.this.getApplication();
                        ub.user_id = jsonObject.getInt("user_id");
                        app.setUser(ub);
                        startActivity(new Intent(mContext, MainActivity.class));
                        finish();
                    } else if (status == 403) {
                        String error = jsonObject.getString("error");
                        Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        ub = SaveUtils.getUserInfo(mContext);

        initView();

        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessageDelayed(0, 1500);
            }
        }).start();


    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.imageView1);
        Random random = new Random();
        int a = random.nextInt(5) + 1;
        String src = "splash" + a;
        String type = "drawable";
        String packge = "com.mrdu";
        int rid = getResources().getIdentifier(src, type, packge);
        imageView.setBackgroundResource(rid);
    }

    private void postLogInRequest() {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(60000, TimeUnit.MILLISECONDS)
                .build();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", ub.username);
            jsonObject.put("password", ub.password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(jsonObject));
        //发起请求
        Log.e("SplashActivity", "request body:" + String.valueOf(jsonObject));
        Request request = new Request.Builder()
                .url(getResources().getString(R.string.url_log_in))
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("SplashActivity", "request failure" + e);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                Log.e("SplashActivity", "Response:" + responseStr);
                handler.obtainMessage(1, responseStr).sendToTarget();
            }
        });
    }
}
