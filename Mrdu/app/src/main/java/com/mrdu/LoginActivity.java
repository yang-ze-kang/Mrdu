package com.mrdu;


import com.mrdu.bean.UserBean;
import com.mrdu.util.MyApplication;
import com.mrdu.util.SaveUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends Activity implements OnClickListener {
    private final Context mContext = this;
    private EditText et_username;
    private EditText et_password;
    private CheckBox cb_remember;
    private Button bt_login;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private TextView tv_regist;
    Context mcontext = this;
    final OkHttpClient client = new OkHttpClient();
    private UserBean ub = new UserBean();

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                //处理response数据
                String returnMessage = (String) msg.obj;
                UserBean userBean = UserBean.responseUser(returnMessage);
                if (userBean != null) {
                    if (userBean.status == 201) {
                        MemoryPassword();
                        MyApplication app = (MyApplication) LoginActivity.this.getApplication();
                        ub=UserBean.responseUser(returnMessage);
                        app.setUser(ub);
                        startActivity(new Intent(mContext, MainActivity.class));
                        finish();
                    } else if (userBean.status == 403) {
                        Toast.makeText(mContext, userBean.error, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(mcontext,"网络信号差，登录失败！",Toast.LENGTH_SHORT).show();
                }

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        et_username = (EditText) findViewById(R.id.et_phone_num);
        et_password = (EditText) findViewById(R.id.et_password1);
        cb_remember = (CheckBox) findViewById(R.id.cb_remember);
        bt_login = (Button) findViewById(R.id.bt_login);
        tv_regist = (TextView) findViewById(R.id.tv_regist);
        bt_login.setOnClickListener(this);
        tv_regist.setOnClickListener(this);
        UserBean us = new UserBean();
        us = SaveUtils.getUserInfo(mcontext);

        if (!us.username.equals("") && !us.password.equals("")) {
            cb_remember.setChecked(true);
            et_username.setText(us.username);
            et_password.setText(us.password);
            try {
                login();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (!us.username.equals("")) {
            et_username.setText(us.username);
        }


    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_login:
                // 登录按钮
                try {
                    login();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_regist:
                // 注册按钮
                toRegist();
                break;
        }
    }

    private void toRegist() {
        // 打开注册界面
        Intent intent = new Intent(mContext, RegistActivity.class);
        startActivity(intent);

    }

    private void login() throws JSONException {
        // 记住学号和密码
        final String username = et_username.getText().toString().trim();
        final String password = et_password.getText().toString().trim();
        // 尝试登录，登录成功后保存
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(mContext, "学号或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        ub.addUserBean(username, password);
        postLogInRequest(ub);
    }

    private void postLogInRequest(UserBean ub)  {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", ub.username);
            jsonObject.put("password", ub.password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(jsonObject));
        //发起请求
        final Request request = new Request.Builder()
                .url(getResources().getString(R.string.url_log_in))
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr=response.body().string();
                Log.e("LogIn","LogIn Response:"+responseStr);
                mHandler.obtainMessage(1, responseStr).sendToTarget();
            }
        });
    }

    private void MemoryPassword() {
        // 保存用户名，如果记住密码，则也保存密码
        final boolean isrem = cb_remember.isChecked();
        if (!isrem) {
            ub.password = "";
        }
        SaveUtils.saveUserInfo(mContext, ub);
    }
}
