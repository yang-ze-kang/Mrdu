package com.mrdu.net;

import com.mrdu.bean.UserBean;

public interface IUserUtil {
	//登录
	UserBean login(UserBean u) throws MyException;
	//注册
	UserBean Regist(UserBean u) throws MyException;

	UserBean Update(UserBean u)throws MyException;
}
