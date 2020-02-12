package com.mrdu.tests;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import com.mrdu.R;
import com.mrdu.TestActivity;
import com.mrdu.bean.QuestionBean;
import com.mrdu.bean.TestBean;
import com.mrdu.bean.TestID;
import com.mrdu.bean.UserBean;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PointDetectFormFragment extends Fragment implements
        OnClickListener {

    //app
    private Context mContent;
    private TestActivity testActivity;

    //view
    private Questionare qn;
    private ImageView photo1;
    private ImageView photo2;
    private ImageView photo3;
    private Button clickhere;
    private Rect happyRect;
    private Rect normalRect;
    private Rect sadRect;
    private boolean start;

    //data
    private long lastTime;
    private long currentTime;
    private UserBean user;
    private TestBean testBean;
    private int[] height = new int[3];
    private int[] width = new int[3];
    private int bh;
    private int bw;

    //web
    private OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");//JSON类型
    private Bitmap bitmapHappy;
    private Bitmap bitmapNormal;
    private Bitmap bitmapSad;

    private long starttime;
    private Handler h = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            clickhere.setVisibility(View.VISIBLE);
            photo1.setVisibility(View.INVISIBLE);
            photo2.setVisibility(View.INVISIBLE);
            photo3.setVisibility(View.INVISIBLE);
            starttime = System.currentTimeMillis();
        }

    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what < 4) {
                switch (msg.what) {
                    case 1:
                        bitmapHappy = (Bitmap) msg.obj;
                        height[0] = bitmapHappy.getHeight();
                        width[0] = bitmapHappy.getWidth();
                        photo1.setImageBitmap(bitmapHappy);
                        break;
                    case 2:
                        bitmapNormal = (Bitmap) msg.obj;
                        height[1] = bitmapNormal.getHeight();
                        width[1] = bitmapNormal.getWidth();
                        photo2.setImageBitmap(bitmapNormal);
                        break;
                    case 3:
                        bitmapSad = (Bitmap) msg.obj;
                        height[2] = bitmapSad.getHeight();
                        width[2] = bitmapSad.getWidth();
                        photo3.setImageBitmap(bitmapSad);
                        lastTime = SystemClock.currentThreadTimeMillis();
                        setQuestion();
                        break;
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // [1]通过打气筒把一个布局转换成view对象
        View view = inflater.inflate(R.layout.fragment_pointdetect, container,
                false);
        photo1 = (ImageView) view.findViewById(R.id.iv_photo1);
        photo2 = (ImageView) view.findViewById(R.id.iv_photo2);
        photo3 = (ImageView) view.findViewById(R.id.iv_photo3);
        clickhere = (Button) view.findViewById(R.id.bt_clickhere);
        clickhere.setOnClickListener(this);

        //app
        mContent = getActivity();
        testActivity = (TestActivity) getActivity();
        //data
        testBean = testActivity.testBean;
        bw = clickhere.getWidth();
        bh = clickhere.getHeight();

        clickhere.setText("开始测试");
        start = false;

        Log.v("DTCFS", "number of questions:" + String.valueOf(testBean.getNumberOfQuestions()));
        return view;
    }

    private void setImages() {
        int i = testBean.getCurrentQuestion();
        postSetImage(1, i);
        postSetImage(2, (i + 1) % testBean.getNumberOfQuestions());
        postSetImage(3, (i + 2) % testBean.getNumberOfQuestions());
    }

    private void setQuestion() {

        photo1.setVisibility(View.VISIBLE);
        photo2.setVisibility(View.VISIBLE);
        photo3.setVisibility(View.VISIBLE);

        // 获取窗口宽高
        int ww = this.getView().getWidth();
        int wh = this.getView().getHeight();

        Random rand = new Random();

//        // 获取开心图片的宽高
//        int height = bitmapHappy.getHeight();
//        int width = bitmapHappy.getWidth();
        // 准备开心Rect
        int randx = rand.nextInt(ww - width[0]);
        int randy = rand.nextInt(wh - height[0]);
        happyRect = new Rect(randx, randy, randx + width[0], randy + height[0]);
        // 设置开心的位置
        photo1.setX(happyRect.left);
        photo1.setY(happyRect.top);

        // 获取一般图片的宽高
//        height = bitmapNormal.getHeight();
//        width = bitmapNormal.getWidth();
        // 准备一般Rect
        do {
            randx = rand.nextInt(ww - width[1]);
            randy = rand.nextInt(wh - height[1]);
            normalRect = new Rect(randx, randy, randx + width[1], randy + height[1]);
            // 不要与快乐相碰撞
        } while (normalRect.intersect(happyRect));
        // 设置一般的位置
        photo2.setX(normalRect.left);
        photo2.setY(normalRect.top);

//        // 获取悲伤图片的宽高
//        height = bitmapSad.getHeight();
//        width = bitmapSad.getWidth();
        // 准备悲伤Rect
        do {
            randx = rand.nextInt(ww - width[2]);
            randy = rand.nextInt(wh - height[2]);
            sadRect = new Rect(randx, randy, randx + width[2], randy + height[2]);
            // 不要与快乐，悲伤相碰撞
        } while (sadRect.intersect(happyRect) || sadRect.intersect(normalRect));
        // 设置悲伤的位置
        photo3.setX(sadRect.left);
        photo3.setY(sadRect.top);

        // 设置按钮位置，不可见
        int x, y;
        clickhere.setVisibility(View.INVISIBLE);
        if ((testBean.getCurrentQuestion() + 1) % 3 == 1) {
            x = happyRect.left
                    + rand.nextInt(happyRect.right - happyRect.left - bw);
            y = happyRect.top
                    + rand.nextInt(happyRect.bottom - happyRect.top - bh);
        } else if ((testBean.getCurrentQuestion() + 1) % 3 == 2) {
            x = normalRect.left
                    + rand.nextInt(normalRect.right - normalRect.left - bw);
            y = normalRect.top
                    + rand.nextInt(normalRect.bottom - normalRect.top - bh);
        } else {
            x = sadRect.left + rand.nextInt(sadRect.right - sadRect.left - bw);
            y = sadRect.top + rand.nextInt(sadRect.bottom - sadRect.top - bh);
        }
        clickhere.setX(x);
        clickhere.setY(y);
        new Thread(new Runnable() {
            @Override
            public void run() {
                h.sendEmptyMessageDelayed(1, 3000);
            }
        }).start();

    }

    @Override
    public void onClick(View v) {
        if (!start) {
            setImages();
            clickhere.setText("点我");
            clickhere.setVisibility(View.INVISIBLE);
            start = true;
        } else {
            currentTime = SystemClock.currentThreadTimeMillis();
            long time = currentTime - lastTime;
            switch (v.getId()) {
                case R.id.bt_clickhere:
                    photo1.setVisibility(View.INVISIBLE);
                    photo2.setVisibility(View.INVISIBLE);
                    photo3.setVisibility(View.INVISIBLE);
                    testBean.doAnswer(1, time);
                    break;
            }

            if (testBean.getCurrentQuestion() < 8) {
                Log.v("DTCFS", "Now question:" + String.valueOf(testBean.getCurrentQuestion()));
                setImages();
            } else {
                testActivity.onAnswer(testBean);
            }

        }
    }


    //请求网络图片
    private void postSetImage(final int pos, int i) {
        Request request = new Request.Builder()
                .url(testBean.getPictureUri(i)).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(4);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                handler.obtainMessage(pos, bitmap).sendToTarget();
            }
        });
    }
}
