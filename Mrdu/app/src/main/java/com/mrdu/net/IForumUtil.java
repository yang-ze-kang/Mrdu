package com.mrdu.net;

import java.util.List;

import com.mrdu.bean.ForumBean;

public interface IForumUtil {
	//获得所有主题帖
	List<ForumBean> getAll() throws MyException;
	//按照主体帖id获得回复帖
	List<ForumBean> getById(int id)  throws MyException;
	//上传一篇帖子
	void put(ForumBean fb) throws MyException;
	//根据帖子ID删除帖子
	void deleteById(int id) throws MyException;
	//根据帖子ID更新帖子
	void update(ForumBean fb) throws MyException;
}
