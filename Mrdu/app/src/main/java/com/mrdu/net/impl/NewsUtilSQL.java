package com.mrdu.net.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.mrdu.bean.NewsBean;
import com.mrdu.helper.MyDatabase;
import com.mrdu.net.INewsUtil;
import com.mrdu.net.MyException;

public class NewsUtilSQL implements INewsUtil {
	private MyDatabase db;

	public NewsUtilSQL(Context context) {
		try {
			db = new MyDatabase(context);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}

	@Override
	public List<NewsBean> getAll() throws MyException {
		SQLiteDatabase wdb = db.getReadableDatabase();
		List<NewsBean> list = new ArrayList<NewsBean>();
		Cursor cs;
		try {
			cs = wdb.query(false, "news", null,null, null, null, null,
					null, null, null);
		} catch (Exception e) {
			throw new MyException("獲取新闻列表失败");
		}
		while (cs.moveToNext()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			NewsBean newsBean;
			try {
				newsBean = new NewsBean(cs.getInt(0), cs.getString(1), cs.getString(2), sdf.parse(cs.getString(3)));
				list.add(newsBean);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return list;
	}

	@Override
	public List<NewsBean> getById(int id) throws MyException {
		SQLiteDatabase wdb = db.getReadableDatabase();
		List<NewsBean> list = new ArrayList<NewsBean>();
		Cursor cs;
		try {
			cs = wdb.query(false, "news", null, "user_id = "+id, null, null, null,
					null, null, null);
		} catch (Exception e) {
			throw new MyException("獲取新闻列表失败");
		}
		while (cs.moveToNext()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			NewsBean newsBean;
			try {
				newsBean = new NewsBean(cs.getInt(0), cs.getString(1), cs.getString(2), sdf.parse(cs.getString(3)));
				list.add(newsBean);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return list;
	}


	@Override
	public void deleteById(int id) throws MyException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(NewsBean fb) throws MyException {
		// TODO Auto-generated method stub
		
	}

	public void newsAdd() throws MyException{
	try{
		SQLiteDatabase wdb = db.getWritableDatabase();
		wdb.execSQL("insert into news(title,text,postdate) values(?,?,datetime())",new Object[]{"抑郁症能治好吗","keyi"});
	}catch(Exception e)
	{
		e.printStackTrace();
		throw new MyException("发布失败");
	}
	}
	}




