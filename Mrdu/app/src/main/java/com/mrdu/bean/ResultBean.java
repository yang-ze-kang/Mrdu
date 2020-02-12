package com.mrdu.bean;

import java.io.Serializable;

public class ResultBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8755576812304084337L;
	public int id;
	public int uid;
	public int tid;
	public double score;
	
	public ResultBean(int id, int uid, int tid, double score) {
		this.id = id;
		this.uid = uid;
		this.tid = tid;
		this.score = score;
	}
	
}
