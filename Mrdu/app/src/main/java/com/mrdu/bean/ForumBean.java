package com.mrdu.bean;

import java.io.Serializable;
import java.util.Date;

public class ForumBean implements Serializable{
	
	private static final long serialVersionUID = -4005915319474526116L;
	public ForumBean(int id, int uid, String uname, String title, String text,
			int to, Date time, int replys) {
		super();
		this.id = id;
		this.uid = uid;
		this.uname = uname;
		this.title = title;
		this.text = text;
		this.to = to;
		this.time = time;
		this.replys = replys;
	}
	public int id;
	public int uid;
	public String uname;
	public String title;
	public String text;
	public int to;
	public Date time;
	public int replys;
}
