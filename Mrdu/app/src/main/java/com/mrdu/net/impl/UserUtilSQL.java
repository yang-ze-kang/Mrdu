package com.mrdu.net.impl;

import java.sql.Date;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mrdu.bean.UserBean;
import com.mrdu.helper.MyDatabase;
import com.mrdu.net.IUserUtil;
import com.mrdu.net.MyException;

public class UserUtilSQL implements IUserUtil {
	private MyDatabase db;
	
	public UserUtilSQL(Context context) {
		db = new MyDatabase(context);
	}

	@Override
	public UserBean login(UserBean u) throws MyException {
		SQLiteDatabase wdb = db.getReadableDatabase();
		Cursor cs;
		try{
			String sql = "select * from userinfo where username = '"+u.username +"' and password='"+u.password+"'";
			cs = wdb.rawQuery(sql, null);
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MyException("获取用户信息失败");
		}
		//Log.e("测试",cs.getInt(1)+"");
		if(cs.getCount() == 0)
			throw new MyException("用户名或密码错误");
		
		cs.moveToFirst();
		u.keepday+=1;
		Date date = new Date(0);
		u.lasttest = date.toString();
		u.user_id = cs.getInt(0);
		u.nickname = cs.getString(3);
		u.age = cs.getInt(4);
		u.sex = (char)(cs.getString(5).charAt(0));
		u.occupation = cs.getString(6);
		u.email = cs.getString(7);
		u.phonenum = cs.getString(8);
		return u;
	}

	@Override
	public UserBean Regist(UserBean u) throws MyException {

		u.nickname = "lisi";
		Date date = new Date(0);
		u.lasttest = date.toString();
		u.sex='m';
		u.keepday = 1;
		u.age =18;
		SQLiteDatabase wdb = db.getWritableDatabase();
		
		Cursor cs;
		try{
			cs = wdb.query(false, "userinfo", null, "username="+u.username, null, null, null, null, null, null);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MyException("查询用户失败失败");
		}
		
		//Log.e("测试",cs.getCount()+"" );
		if(cs.getCount()>=1)
			throw new MyException("用户已经存在");
		try {
			wdb.execSQL("insert into userinfo(username,password,nickname,lasttest,sex,keepday) values(?,?,?,?,?,?)", new Object[]{u.username,u.password,u.nickname,u.lasttest,u.sex,u.keepday});
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException("注册用户失败");
		}
		return u;
	}

	@Override
	public UserBean Update(UserBean u) throws MyException {
		// TODO Auto-generated method stub
		SQLiteDatabase wdb = db.getWritableDatabase();
		String sql = "update userinfo set nickname=?,sex=?,occupation=?,email=?,phonenum=? where id=?";
		try{
			wdb.execSQL(sql,new Object[]{u.nickname,u.sex,u.occupation,u.email,u.phonenum ,u.user_id});
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MyException("更新用户信息失败");
		}
		return null;
	}

}
