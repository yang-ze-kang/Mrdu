package com.mrdu.fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mrdu.R;
import com.mrdu.adapter.NewsArticleAdapter;

import java.util.LinkedList;
import java.util.List;

public class ArticleFragment extends Fragment implements View.OnClickListener {

	RecyclerView rv;
    SwipeRefreshLayout swipeRefreshLayout;
    List<String> data;
    Context mContext;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==001){
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(mContext,"更新完成",Toast.LENGTH_SHORT).show();
            }
        }
    };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
	    mContext=this.getActivity();
		View view = inflater.inflate(R.layout.news_list, container,false);
		//初始化recycleView
        data=initData();
		rv=(RecyclerView) view.findViewById(R.id.rv);
		rv.setLayoutManager(new LinearLayoutManager(mContext));
		rv.setAdapter(new NewsArticleAdapter(mContext,data));
		//初始化下拉更新
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new swipeRefreshListener());
		return view;
	}

	class swipeRefreshListener implements SwipeRefreshLayout.OnRefreshListener{

        @Override
        public void onRefresh() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    handler.sendEmptyMessageDelayed(001,1500);
                }
            }).start();
        }
    }

	private List<String> initData() {
		List<String> data = new LinkedList<>();
		data.add("抑郁症发作是怎么样的？");
		data.add("远离抑郁：拯救不开心");
		data.add("抑郁症会影响外表吗？");
		data.add("走出抑郁：重建人生");
        data.add("微笑抑郁症");
        data.add("一图分清抑郁情绪和抑郁症");
        data.add("心理学：哪些信号表示抑郁症加重了？");
        data.add("对于抑郁症患者，最需要的是什么？");
		return data;
	}

	@Override
	public void onClick(View view) {

	}
}

