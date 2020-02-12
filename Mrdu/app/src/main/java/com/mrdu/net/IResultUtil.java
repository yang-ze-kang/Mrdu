package com.mrdu.net;

import java.util.List;

import com.mrdu.bean.ResultBean;

public interface IResultUtil {
	//按照用户id获得所有测试结果
	List<ResultBean> getResultsById(int id) throws MyException;
	//上传一个测试结果
	void put(ResultBean rb) throws MyException;
}
