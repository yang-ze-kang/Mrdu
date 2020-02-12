package com.mrdu.net.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mrdu.bean.ForumBean;
import com.mrdu.bean.ResultBean;
import com.mrdu.helper.MyDatabase;
import com.mrdu.net.IResultUtil;
import com.mrdu.net.MyException;

public class ResultUtilSQL implements IResultUtil {
	private MyDatabase db;
	
	public ResultUtilSQL(Context context) {
		
		//创建本地数据库
		try{
			db = new MyDatabase(context);
		}catch(Exception ex){
			System.out.println(ex.toString());
		}
		
	}

	@Override
	public List<ResultBean> getResultsById(int id) throws MyException {
		SQLiteDatabase wdb = db.getReadableDatabase();
		Cursor cs;
		List<ResultBean> list = new ArrayList<ResultBean>();
		
		try{
			cs = wdb.query(false, "testtype", null, "uid="+id, null, null, null, null, null, null);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MyException("获取用户成绩失败");
		}
		
		while (cs.moveToNext()) {
			ResultBean resultBean;
			try {
				resultBean = new ResultBean(cs.getInt(0), cs.getInt(1),cs.getInt(2),cs.getDouble(3));
				list.add(resultBean);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return list;
	}

	@Override
	public void put(ResultBean rb) throws MyException {
		//测试数据
		
		SQLiteDatabase wdb = db.getWritableDatabase();
		try {
			wdb.execSQL("insert into testtype(uid,tid,score) values(?,?,?)", new Object[]{rb.uid,rb.tid,rb.score});
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException("插入用户成绩失败");
		}
		
	}

}
