package com.mrdu.net;

import java.util.List;

import com.mrdu.bean.NewsBean;

public interface INewsUtil {
	//获得所有主题帖
	List<NewsBean> getAll() throws MyException;
	//按照主体帖id获得回复帖
	List<NewsBean> getById(int id)  throws MyException;
	//根据帖子ID删除帖子
	void deleteById(int id) throws MyException;
	//根据帖子ID更新帖子
	void update(NewsBean fb) throws MyException;
}
