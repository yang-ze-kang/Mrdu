package com.mrdu.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moxun.tagcloudlib.view.TagsAdapter;
import com.mrdu.R;
import com.mrdu.TestActivity;
import com.mrdu.bean.TestID;
import com.mrdu.helper.MyNet;
import com.mrdu.util.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by moxun on 16/1/19.
 */

public  class TextTagsAdapter extends TagsAdapter {

    private List<String> dataSet = new ArrayList<>();
    private String[] testName= {"贝克抑郁自评量表","渐变范式","短暂识别",
            "点探测范式","Stroop测试","宏表情测试"};
    //,"贝克抑郁自评量表","PHQ-9量表","GAD-7量表","伯恩斯忧郁症清单","SDS抑郁自评量表"};
    private String[] testActivity={"","","","",""
    ,"","","","","",""};

    public TextTagsAdapter(@NonNull String... data) {
        dataSet.clear();
        Collections.addAll(dataSet, data);
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final Context context, final int position, ViewGroup parent) {
        TextView tv = new TextView(context);
        tv.setText(testName[position]);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(R.color.lightyellow);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
 //               Log.e("Click", "Tag " + position + " clicked.");
 //               Toast.makeText(context, "Tag " + position + " clicked", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context,TestActivity.class);
                int value = TestID.NONE;
                switch (position) {
                    case 0:
                        value = TestID.BECK;
                        new Thread(new Runnable(){
                            @Override
                            public void run() {
                                SimpleDateFormat df = TimeUtils.getFormater();
                                String i = MyNet.post(MyNet.connecturl+"/testwithapp", "{testtype:'3', date:'"+df.format(new Date())+"',userid:'"+"1"+"'}");
                                System.out.println(""+i);
                            }
                        }).start();
                        break;
                    case 1:
                        value = TestID.JBFS;
                        new Thread(new Runnable(){
                            @Override
                            public void run() {
                                SimpleDateFormat df = TimeUtils.getFormater();
                                String i = MyNet.post(MyNet.connecturl+"/testwithapp", "{testtype:'2', date:'"+df.format(new Date())+"',userid:'"+"1"+"'}");
                                System.out.println(""+i);
                            }
                        }).start();
                        break;
                    case 2:
                        value = TestID.DZSB;
                        new Thread(new Runnable(){
                            @Override
                            public void run() {
                                SimpleDateFormat df = TimeUtils.getFormater();
                                String i = MyNet.post("http://192.168.1.102:8080/test", "{testtype:'6', date:'"+df.format(new Date())+"',userid:'"+"1"+"'}");
                                System.out.println(""+i);
                                String ii =  "{testtype:'6', date:'"+df.format(new Date())+"',userid:'"+"1"+"'}";
                                System.out.println(""+ii);
                            }
                        }).start();
                        break;
                    case 3:
                        value = TestID.DTCFS;
                        new Thread(new Runnable(){
                            @Override
                            public void run() {
                                SimpleDateFormat df = TimeUtils.getFormater();
                                String i = MyNet.post(MyNet.connecturl+"/testwithapp", "{testtype:'1', date:'"+df.format(new Date())+"',userid:'"+"1"+"'}");
                                System.out.println(""+i);
                            }
                        }).start();
                        break;
                    case 4:
                        value =TestID.STROOP;
                        new Thread(new Runnable(){
                            @Override
                            public void run() {
                                SimpleDateFormat df = TimeUtils.getFormater();
                                String i = MyNet.post(MyNet.connecturl+"/testwithapp", "{testtype:'5', date:'"+df.format(new Date())+"',userid:'"+"1"+"'}");
                                System.out.println(""+i);
                            }
                        }).start();
                        break;
                    case 5:
                        value = TestID.HBQCS;
                        new Thread(new Runnable(){
                            @Override
                            public void run() {
                                SimpleDateFormat df = TimeUtils.getFormater();
                                String i = MyNet.post(MyNet.connecturl+"/testwithapp", "{testtype:'4', date:'"+df.format(new Date())+"',userid:'"+"1"+"'}");
                                System.out.println(""+i);
                            }
                        }).start();
                        break;
                    //case R.user_id.bt_qtlb:
                    default:
                        //value = TestID.BECK;
                        //context.startActivity(new Intent(context, OtherTestSelectionActivity.class));
                        value = TestID.NONE;
                        value = TestID.BECK;
                        i.putExtra("type", value);
                        return;
                }
                i.putExtra("type", value);
                context.startActivity(i);
            }
        });
        return tv;
    }

    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public int getPopularity(int position) {
        return position % 7;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {

    }


}
