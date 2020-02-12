package com.mrdu.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper {

	//old version
	public MyDatabase(Context context) {
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
				+ "Uid integer not null ,   "
				+ "Tid integer not null ,                                      "
				+ "score double not null,										"
				+ "foreign key (Uid) references userinfo(id))                                  ");
		db.execSQL("create table questions(               "
				+ "id integer primary key autoincrement,   "
				+ "Tid integer not null,                    "
				+ "question varchar(100) not null,      "
				+ "truea varchar(50) not null,          "
				+ "falsea1 varchar(50) not null,        "
				+ "falsea2 varchar(50),                 "
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
				+ "toid integer ,               "
				+ "isdeleted int, "
				+ "postdate date not null,"
				+ "foreign key (Uid) references userinfo(id),"
				+ "foreign key (toid) references post(id))           ");
		db.execSQL("create table news("
				+ "id integer primary key autoincrement,                    "
				+ "title varchar(50) not null,                           "
				+ "text varchar(2000),                                    "
				+ "postdate date not null)");

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
