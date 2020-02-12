package com.mrdu.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.mrdu.PersonalActivity;
import com.mrdu.R;

public class MyUserTitleBar {

    private static Activity mActivity;
    /**
     * @see [自定义标题栏]
     * @param activity
     * @p
     */
    public static void getTitleBar(Activity activity) {
        mActivity = activity;
        activity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        activity.setContentView(R.layout.my_user_title);
        activity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.my_user_title);
//        ImageView imageView = (ImageView) activity.findViewById(R.user_id.iv_user);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                mActivity.startActivity(new Intent(mActivity, PersonalActivity.class));
//            }
//        });
    }
}
