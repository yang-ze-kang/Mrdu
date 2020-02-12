package com.mrdu.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mrdu.NewsActivity;
import com.mrdu.R;
import com.mrdu.adapter.NewsVideoAdapter;
import com.mrdu.bean.VideoBean;
import com.mrdu.view.MyVideoView;

import java.util.LinkedList;
import java.util.List;

public class VideoFragment extends Fragment {

    private Context mContext;
    private RecyclerView rv;
    private SwipeRefreshLayout refresh;
    private List<VideoBean> videoData;
    private String videoPath = "http://dn-chunyu.qbox.me/fwb/static/images/home/video/video_aboutCY_A.mp4";
    private NewsVideoAdapter mAdapter;
    private MyVideoView videoView;
    private View lastView;
    private FrameLayout videoFrame =null;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==001){
                refresh.setRefreshing(false);
                Toast.makeText(mContext,"更新完成",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        initData();//初始化数据
        View view = inflater.inflate(R.layout.news_list, container, false);
        //初始化列表
        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(mAdapter);
        refresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        initEvent();//初始化事件
        return view;
    }

    private void initEvent() {

        mAdapter.setListener(new NewsVideoAdapter.OnClickPlayListener() {
            @Override
            public void onPlayClick(View view, String videoPath) {
                showVideo(view, videoPath);
            }
        });

        videoView.setListener(new MyVideoView.IFullScreenListener() {
            View v = null;
            @Override
            public void onClickFull(boolean isFull) {
                if(isFull){
                    removeVideoView();
                    videoView.stop();
                    v =((NewsActivity)mContext).findViewById(R.id.full_video);
                    if (v != null) {
                        v.setVisibility(View.VISIBLE);
                        videoFrame = (FrameLayout) v;
                        videoFrame.removeAllViews();
                        videoFrame.addView(videoView, new ViewGroup.LayoutParams(-1, -1));
                        videoView.setVideoPath(videoPath);
                        videoView.start();
                    }
                }else{
                    FrameLayout fram=(FrameLayout) v;
                    fram.removeAllViews();
                    v.setVisibility(View.GONE);
                    ((NewsActivity)mContext).showView();
                }
            }
        });
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessageDelayed(001,1500);
                    }
                }).start();
            }
        });
    }

    private void initData() {
        mContext = this.getActivity();
        videoData = new LinkedList<>();
        int imagId[] = new int[]{R.drawable.test_intro1, R.drawable.test_intro2, R.drawable.test_intro3,
                R.drawable.test_intro4, R.drawable.test_intro5};
        for (int i = 0; i < 10; i++) {
            VideoBean videoBean = new VideoBean(imagId[i % imagId.length], videoPath);
            videoData.add(videoBean);
        }
        mAdapter = new NewsVideoAdapter(mContext, videoData);
        videoView=new MyVideoView(mContext);
    }

    //播放视频
    private void showVideo(View view, final String videoPath) {

        View v = null;
        removeVideoView();
        videoView.stop();
        v = view.findViewById(R.id.item_image_intro);
        if (v != null) v.setVisibility(View.INVISIBLE);
        v = view.findViewById(R.id.item_image_play);
        if (v != null) v.setVisibility(View.INVISIBLE);
        v = view.findViewById(R.id.item_video_root);
        if (v != null) {
            v.setVisibility(View.VISIBLE);
            videoFrame = (FrameLayout) v;
            videoFrame.removeAllViews();
            videoFrame.addView(videoView, new ViewGroup.LayoutParams(-1, -1));
            videoView.setVideoPath(videoPath);
            videoView.start();
        }
        lastView = view;
    }

    private void removeVideoView() {
        View v;
        if (lastView != null) {
            v = lastView.findViewById(R.id.item_image_intro);
            if (v != null) v.setVisibility(View.VISIBLE);
            v = lastView.findViewById(R.id.item_image_play);
            if (v != null) v.setVisibility(View.VISIBLE);
            v = lastView.findViewById(R.id.item_video_root);
            if (v != null) {
                FrameLayout ll = (FrameLayout) v;
                ll.removeAllViews();
                v.setVisibility(View.GONE);
            }
        }
    }

}
