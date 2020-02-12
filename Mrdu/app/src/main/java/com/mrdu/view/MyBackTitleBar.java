package com.mrdu.view;


import android.app.Activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import android.view.View.OnClickListener;

import android.view.Window;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mrdu.R;

import java.util.jar.Attributes;


/**

 * @author aaron

 *

 */

public class MyBackTitleBar extends LinearLayout {

//    private static Activity mActivity;
    private ImageView back;
    private TextView title;

    public MyBackTitleBar(final Context context, AttributeSet attrs) {
        super(context,attrs);
        View inflate = LayoutInflater.from(context).inflate(R.layout.my_back_title,this);
        back=(ImageView) inflate.findViewById(R.id.iv_back);
        title=(TextView)inflate.findViewById(R.id.tv_title);

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)context).finish();
            }
        });
    }

    //设置标题
    public  void setTitle(String s)
    {
        title.setText(s);
    }

//    public static void getTitleBar(Activity activity) {
//        mActivity = activity;
//        activity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
//        activity.setContentView(R.layout.my_back_title);
//        activity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
//                R.layout.my_back_title);
//        TextView textView = (TextView) activity.findViewById(R.user_id.tv_fanhui);
//        textView.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
//                mActivity.finish();
//            }
//        });
//    }
}
