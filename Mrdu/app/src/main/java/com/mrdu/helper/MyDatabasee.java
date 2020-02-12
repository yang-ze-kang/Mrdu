package com.mrdu.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabasee extends SQLiteOpenHelper{
	//new version

	public MyDatabasee(Context context) {
		super(context, "data3.db", null, 1);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table userinfo(             "
				+ "id integer primary key autoincrement, "
				+ "username varchar(20) not null,     "
				+ "password varchar(20) not null,     "
				+ "nickname varchar(50) not null,     "
				+ "age varchar(3),                    "
				+ "sex char not null,                 "
				+ "occupation varchar(20),            "
				+ "email varchar(20),                 "
				+ "phonenum varchar(20),              "
				+ "lastTest date not null,            "
				+ "KeepDay int not null)");
		db.execSQL("create table testtype(                                   "
				+ "id integer primary key autoincrement,                      "
				+ "testname varchar(20) not null ,   "
				+ "picsign integer not null ,                                      "
				+ "resultname varchar(20) not null)");
		
		db.execSQL("create table test_result_grand(               "
				+ "id integer primary key autoincrement,   "
				+ "testid integer not null,                    "
				+"userid integer not null,"
				+ "date date not null,      "
				+ "option varchar(100) not null,          "
				+ "picture varchar(100) not null,        "
				+ "reactiontime varchar(10) not null,                 "
				+ "useroption varchar(10) not null,                 "	
				+ "rightoption varchar(10) not null,                 "
				+ "isright boolean not null,                 "
				+ "falsea3 varchar(50))                  "

		);
		db.execSQL("create table score(                 "
				+ "id integer primary key autoincrement, "
				+ "testname varchar(20) not null,     "
				+ "describe varchar(100))             ");
		db.execSQL("create table post("
				+ "id integer primary key autoincrement,                    "
				+ "Uid integer not null , "
				+ "title varchar(50) not null,                           "
				+ "text varchar(200),                                    "
				+ "toid integer ,               " + "isdeleted int, "
				+ "postdate date not null,"
				+ "foreign key (Uid) references userinfo(id),"
				+ "foreign key (toid) references post(id))           ");

		db.execSQL("create view forumbean as "
				+ "select post.[id],uid,nickname,title,text,toid,postdate "
				+ "from post,userinfo" + " where isdeleted is null"
				+ " and post.[uid] =userinfo.id ");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	
}
