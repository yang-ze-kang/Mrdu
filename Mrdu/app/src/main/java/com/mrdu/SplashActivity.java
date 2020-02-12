package com.mrdu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class SplashActivity extends Activity {
	Context mcontext = this;
	ImageView imageView;
	Handler handler = new Handler() {
    	@Override
    	public void handleMessage(Message msg) {
    		mcontext.startActivity(new Intent(mcontext, LoginActivity.class));
    		SplashActivity.this.finish();
    	}
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        imageView=(ImageView) findViewById(R.id.imageView1);
        Random random = new Random();
        int a = random.nextInt(5) + 1;
        String src = "splash" + a;
        String type = "drawable";
        String packge = "com.mrdu";
        int rid = getResources().getIdentifier(src, type, packge);
        imageView.setBackgroundResource(rid);

        Runnable runnable=new Runnable(){
            @Override
            public void run() {
            	handler.sendEmptyMessageDelayed(0, 1500);
            }

        };
        new Thread(runnable).start();
        
        

    }
}
