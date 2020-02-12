package com.mrdu;

import com.mrdu.bean.UserBean;
import com.mrdu.view.MyBackTitleBar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegistActivity extends Activity implements OnClickListener {

    private Context mContext = this;

    private EditText et_userName;
    private EditText et_emailNum;
    private EditText et_keynum;
    private EditText et_password1;
    private EditText et_password2;

    private Button bt_regist;
    private Button bt_getkey;
    private MyBackTitleBar myBackTitleBar;

    private String email;
    private String phoneNum;
    private String password;
    private String keyNum;
    private int time = 30;

    UserBean ub = new UserBean();
    private OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {//0号功能，接受验证码请求内容
                String resString = (String) msg.obj;
                int status = 0;
                try {
                    JSONObject jsonObject = new JSONObject(resString);
                    status = jsonObject.getInt("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status == 200) {
                    showText("验证码发送成功");
                } else if (status == 403) {
                    showText("验证码发送失败");
                }

            }
            if (msg.what == 1) {//1号功能，处理注册请求内容
                String retString = (String) msg.obj;
                JSONObject jsonObject = null;
                int status = 0;
                try {
                    jsonObject = new JSONObject(retString);
                    status = jsonObject.getInt("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status == 200) {//注册成功
                    showText("注册成功，请登录");
                    RegistActivity.this.startActivity(new Intent(mContext, LoginActivity.class));
                    RegistActivity.this.finish();
                } else if (status == 403) {//注册失败
                    try {
                        String error = jsonObject.getString("error");
                        if (error.equals("User Exists")) {
                            showText("用户名已存在");
                        } else if (error.equals("identifying code input error")) {
                            showText("验证码错误");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (status == 500) {
                    showText("网络错误");
                }
//                Log.v("regist", retString);
//                UserBean userBean = UserBean.responseUser(retString);
//                if (userBean != null) {
//                    if (userBean.status == 201) {
//                        Toast.makeText(mContext, "注册成功，请登录", Toast.LENGTH_SHORT).show();
//                        RegistActivity.this.startActivity(new Intent(mContext, LoginActivity.class));
//                        RegistActivity.this.finish();
//                    } else if (userBean.status == 403) {
//                        Toast.makeText(mContext, userBean.error, Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(mContext, "网络信号差，注册失败！", Toast.LENGTH_SHORT).show();
//                }

            } else if (msg.what == 2) {//2号功能，线程吐司
                showText(msg.obj.toString());
            } else if (msg.what == 3) {//3，4号功能，重新发送验证码倒计时
                bt_getkey.setText("重新发送（" + time + ")");
            } else if (msg.what == 4) {
                bt_getkey.setText("获取验证码");
                bt_getkey.setClickable(true);
                time = 30;
            }
        }
    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();
    }


    //初始化控件
    private void initView() {
        // 找到控件
        myBackTitleBar = (MyBackTitleBar) findViewById(R.id.my_back_title);
        myBackTitleBar.setTitle("注册");

        et_userName = (EditText) findViewById(R.id.et_user_name);
        et_emailNum = (EditText) findViewById(R.id.et_email);
        et_keynum = (EditText) findViewById(R.id.et_keynum);
        et_password1 = (EditText) findViewById(R.id.et_password1);
        et_password2 = (EditText) findViewById(R.id.et_password2);

        bt_regist = (Button) findViewById(R.id.bt_regist);
        bt_getkey = (Button) findViewById(R.id.bt_getkey);
        //监听
        bt_regist.setOnClickListener(this);
        bt_getkey.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_regist:
                // 注册按钮
                regist();
                break;
            case R.id.bt_getkey:
                //获取验证码
                email = null;
                email = et_emailNum.getText().toString();
                if (email.isEmpty()) {
                    showText("请输入您的邮箱");
                } else {
                    bt_getkey.setClickable(false);
                    postVerRequest(email);
                    //重新获取验证码倒计时
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (; time > 0; time--) {
                                handler.sendEmptyMessage(3);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            handler.sendEmptyMessage(4);
                        }
                    }).start();
                }
        }
    }

    private void regist() {
        String code = et_keynum.getText().toString().trim();
        String verification = et_keynum.getText().toString().trim();
        String username = et_userName.getText().toString().trim();
        String password1 = et_password1.getText().toString().trim();
        String password2 = et_password2.getText().toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password1)) {
            showText("学号或密码不能为空");
            return;
        } else if (!password1.equals(password2)) {
            showText("两次输入的密码不一致！");
            return;
        }else if(TextUtils.isEmpty(verification)){
            showText("请先验证邮箱");
            return;
        }
        ub.addUserBean(username, password1);
        postRegistRequest(ub, code);

    }

    //获取验证码请求
    private void postVerRequest(String email) {
        //请求内容
        Log.e("Regist", "email:" + email);
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .build();
        final Request request = new Request.Builder()
                .url(getResources().getString(R.string.url) + "email/")
                .post(requestBody)
                .build();
        //请求任务
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                handler.obtainMessage(2, "网络信号差").sendToTarget();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                Log.e("Regist", "Verification respond:" + responseStr);
                handler.obtainMessage(0, responseStr).sendToTarget();
            }
        });
    }


    //注册请求
    private void postRegistRequest(UserBean ub, String code) {
        //请求内容
        RequestBody requestBody = new FormBody.Builder()
                .add("username", ub.username)
                .add("email", email)
                .add("password", ub.password)
                .add("code", code)
                .build();
        final Request request = new Request.Builder()
                .url(getResources().getString(R.string.url_regist))
                .post(requestBody)
                .build();
        //请求任务
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                handler.obtainMessage(2, "网络信号差").sendToTarget();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                Log.e("yang", responseStr);
                handler.obtainMessage(1, responseStr).sendToTarget();
            }
        });
    }

    //吐司
    private void showText(String s) {
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
    }

}
