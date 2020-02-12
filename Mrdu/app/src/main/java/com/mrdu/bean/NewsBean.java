package com.mrdu.bean;

import java.io.Serializable;
import java.util.Date;

public class NewsBean implements Serializable{
	
	private static final long serialVersionUID = -4005915319474526116L;
	public NewsBean(int id, String title, String text, Date time) {
		super();
		this.id = id;
		this.title = title;
		this.text = text;
		this.time = time;
	}
	public int id;
	public String title;
	public String text;
	public Date time;
}
