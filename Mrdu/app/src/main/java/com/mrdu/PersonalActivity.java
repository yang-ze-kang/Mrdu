package com.mrdu;

import com.mrdu.bean.ResultBean;
import com.mrdu.bean.UserBean;
import com.mrdu.net.MyException;
import com.mrdu.net.impl.ResultUtilSQL;
import com.mrdu.util.MyApplication;
import com.mrdu.util.SaveUtils;
import com.mrdu.view.MyBackTitleBar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PersonalActivity extends Activity{

	Context context = this;
	private MyBackTitleBar myBackTitleBar;
	TextView personInfor;
	TextView insertResult;
	TextView setting;
	TextView loginOut;
	TextView callUs;
	TextView textShow;
	TextView userName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        //初始化标题
        myBackTitleBar = (MyBackTitleBar) findViewById(R.id.my_back_title);
        myBackTitleBar.setTitle("个人中心");


        //初始化按键及监听器
        initView();
        MyClickListener myClickListener=new MyClickListener();
        personInfor.setOnClickListener(new MyClickListener());
        insertResult.setOnClickListener(myClickListener);
        setting.setOnClickListener(myClickListener);
        loginOut.setOnClickListener(myClickListener);
        callUs.setOnClickListener(myClickListener);



    }

    //初始化控件
    private void initView(){
        personInfor=(TextView)findViewById(R.id.tv_person_infor);
        insertResult=(TextView)findViewById(R.id.tv_insert_result);
        setting=(TextView)findViewById(R.id.tv_setting);
        loginOut=(TextView)findViewById(R.id.tv_logout);
        callUs=(TextView)findViewById(R.id.iv_call_us);
        textShow=(TextView)findViewById(R.id.tv_show);
        userName=(TextView)findViewById(R.id.tv_username);
        UserBean user=new UserBean();
        MyApplication app = (MyApplication)PersonalActivity.this.getApplication();
        user=app.getUser();
        userName.setText(user.nickname);
	}

    class MyClickListener implements View.OnClickListener {

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_person_infor://个人信息
                    startActivity(new Intent(context, CompletePersonalInformationActivity.class));
                    break;
                case R.id.tv_insert_result://插入成绩
                    // TODO Auto-generated method stub
                    ResultUtilSQL resultutilsql = new ResultUtilSQL(context);
                    //测试数据
                    ResultBean rb = new ResultBean(1, 1, 22, 2);
                    try {
                        resultutilsql.put(rb);
                        textShow.setText("插入成功");
                    } catch (MyException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        textShow.setText("插入失败");
                        //Toast.makeText(context, e.toString(), 1).show();
                    }
                    break;
                case R.id.tv_setting://设置
                    break;
                case R.id.tv_logout://退出登录
                    SaveUtils.clearUserInfo(context);
                    ((MyApplication) PersonalActivity.this.getApplication()).setUser(null);
                    startActivity(new Intent(context, LoginActivity.class));
                    PersonalActivity.this.finish();
                    break;
                case R.id.iv_call_us://联系我们
                    break;
            }

        }
    }

}

