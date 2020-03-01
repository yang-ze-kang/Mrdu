package com.mrdu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class Test extends Activity {

    private TextView tvTitle;
    private WebView wbTestNum;
    private WebView wbAverage;
    private WebView wbRecent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        initView();
        initEvent();


    }


    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/myttf.ttf");
        tvTitle.setTypeface(typeface);

        wbRecent = (WebView) findViewById(R.id.wb_jianbian);
        wbRecent.getSettings().setAllowFileAccess(true);
        wbRecent.getSettings().setJavaScriptEnabled(true);
        wbRecent.loadUrl("file:///android_asset/htmls/test_recent.html");

        wbTestNum = (WebView) findViewById(R.id.wb_test_num);
        wbTestNum.getSettings().setAllowFileAccess(true);
        wbTestNum.getSettings().setJavaScriptEnabled(true);
        wbTestNum.loadUrl("file:///android_asset/htmls/test_num.html");

        wbAverage = (WebView) findViewById(R.id.wb_average);
        wbAverage.getSettings().setAllowFileAccess(true);
        wbAverage.getSettings().setJavaScriptEnabled(true);
        wbAverage.loadUrl("file:///android_asset/htmls/test_average.html");

    }

    private void initEvent() {

        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Test", "OnClick");
                JSONArray array = new JSONArray();
                for (int i = 1; i <= 7; i++) {
                    Random random = new Random();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("date", i);
                        Double a = random.nextDouble();
                        a = (a * 100) % 100;
                        jsonObject.put("score", a);
                        int b = (new Double(a)).intValue();
                        jsonObject.put("count", b);
                        a = random.nextDouble();
                        jsonObject.put("time", a * 60);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    array.put(jsonObject);
                }
                wbTestNum.loadUrl("javascript:createChart(" + String.valueOf(array) + ")");
                wbRecent.loadUrl("javascript:createChart(" + String.valueOf(array) + ")");

            }
        });

        wbTestNum.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                JSONArray array = new JSONArray();
                for (int i = 1; i <= 7; i++) {
                    Random random = new Random();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        int a = random.nextInt(100);
                        jsonObject.put("count", a);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    array.put(jsonObject);
                }
                wbTestNum.loadUrl("javascript:createChart(" + String.valueOf(array) + ")");
            }
        });

        wbRecent.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                JSONArray array = new JSONArray();
                for (int i = 1; i <= 7; i++) {
                    Random random = new Random();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("date", i);
                        Double a = random.nextDouble();
                        a = (a * 100) % 100;
                        jsonObject.put("score", a);
                        a = random.nextDouble();
                        jsonObject.put("time", a * 60);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    array.put(jsonObject);
                }
                wbRecent.loadUrl("javascript:createChart(" + String.valueOf(array) + ")");
            }
        });

        wbAverage.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                JSONArray array = new JSONArray();
                for (int i = 1; i <= 5; i++) {
                    Random random = new Random();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        int a = random.nextInt(100);
                        jsonObject.put("score", a);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    array.put(jsonObject);
                }
                wbAverage.loadUrl("javascript:createChart(" + String.valueOf(array) + ")");
            }
        });

    }

}
