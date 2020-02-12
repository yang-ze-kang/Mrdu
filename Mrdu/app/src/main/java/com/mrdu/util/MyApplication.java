package com.mrdu.util;

import com.mrdu.bean.UserBean;

import android.app.Application;

public class MyApplication extends Application {
	private UserBean user;

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}
}
