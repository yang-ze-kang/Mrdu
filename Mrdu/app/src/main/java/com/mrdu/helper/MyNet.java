package com.mrdu.helper;

import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import android.content.Context;

public class MyNet {
	public static String connecturl = "http://10.200.162.30:8080";
	public static String post(String url, String params) {

		
		DefaultHttpClient httpclient = null;
		  try {

		   httpclient = new DefaultHttpClient();//创建对象
		   HttpPost httpost = new HttpPost(url);//创建httppost对象，URL为地址
		   HttpParams paramss = new BasicHttpParams();//创建基本参数对象
		   
		   //调整配置参数
		   HttpConnectionParams.setConnectionTimeout(paramss, 60000*5);
		   HttpConnectionParams.setSoTimeout(paramss, 60000*5);
		   
		   httpost.setParams(paramss);//装载参数
		   
		   //设置传给服务器的参数
		   MultipartEntity entity = new MultipartEntity();
		   entity.addPart("submitData",new StringBody(params, Charset.forName("UTF-8")));
		   httpost.setEntity(entity);
		   //传输数据给服务器并获取回应
		   //System.out.println("之前啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊");
		   HttpResponse response = httpclient.execute(httpost);
		   //System.out.println("之后噢噢噢噢哦哦哦哦哦哦哦哦哦哦哦哦哦哦哦哦哦哦");
		   if (response.getStatusLine().getStatusCode() == 200) {
		    String ss=EntityUtils.toString(response.getEntity(),"UTF-8");
		   // System.out.println(ss);
		    if (entity != null) {
		     entity.consumeContent();
		    }
		    return ss;
		   }

		  } catch (Exception e) {
		   e.printStackTrace();
		  } finally {
		   if (null != httpclient)
		    httpclient.getConnectionManager().shutdown();//释放连接
		  }
		  return null;
		 }
		 
}
