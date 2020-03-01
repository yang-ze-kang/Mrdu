package com.mrdu.bean;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class UserBean {

    public int user_id;        //数据库中的主键
    public String email;    //邮箱
    public String password;//密码
    public String username;    //用户名
    public int age;            //年龄
    public char sex;        //性别
    public String occupation;//职业
    public String phonenum;    //手机号码
    public String nickname;

    public String lasttest;
    public int keepday;
    public int status;//登录状态
    public String error;//登录失败


    public UserBean() {

    }

    public UserBean(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void addUserBean(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static UserBean responseUser(String returnMessage) {
        UserBean userBean = new UserBean();
        try {
            JSONObject jsonObject = new JSONObject(returnMessage);
            userBean.status = (int) jsonObject.get("status");
            //userBean.error = (String) jsonObject.get("error");
            if (userBean.status == 200) {
                userBean.user_id = (int) jsonObject.get("user_id");
                userBean.username = (String) jsonObject.get("username");
                userBean.password = (String) jsonObject.get("password");
            }
        } catch (JSONException e) {
            userBean = null;
            Log.v("UserBean", "json解析失败");
            e.printStackTrace();
        }
        return userBean;
    }
}
