package com.mrdu.util;

import com.mrdu.bean.UserBean;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SaveUtils {
	//保存用户名，密码
	public static void saveUserInfo(Context context,UserBean ub) {
		
		try {
			// 获取sharedPreferences
			SharedPreferences sharedPreferences = context.getSharedPreferences(
					"saveinfo", Context.MODE_PRIVATE);
			// 获取编辑器并添加数据并提交
			Editor editor = sharedPreferences.edit();

			editor.putString("username", ub.username);
			editor.putString("password", ub.password);

			editor.commit();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 清除保存的用户名密码
	public static void clearUserInfo(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"saveinfo", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.remove("password");
		editor.commit();
	}

	// 获取用户名密码
	public static UserBean getUserInfo(Context context) {

		try {

			// 1.通过Context对象创建一个SharedPreference对象
			SharedPreferences sharedPreferences = context.getSharedPreferences(
					"saveinfo", Context.MODE_PRIVATE);
			// 2.通过sharedPreference获取存放的数据
			String username = sharedPreferences.getString("username", "");
			String password = sharedPreferences.getString("password", "");
			
			return new UserBean(username,password);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
