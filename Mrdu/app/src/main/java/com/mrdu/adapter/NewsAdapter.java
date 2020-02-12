package com.mrdu.adapter;

import java.util.List;

import com.mrdu.R;
import com.mrdu.bean.ForumBean;
import com.mrdu.bean.NewsBean;
import com.mrdu.util.TimeUtils;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter{
	private List<NewsBean> list;
    private Context context;
    
    private boolean showdes = false;
    
    public NewsAdapter(Context context, List<NewsBean> list) {
        this.list = list;
        this.context = context;
    }
    
    public NewsAdapter(Context context, List<NewsBean> list, boolean showdes) {
		super();
		this.list = list;
		this.context = context;
		this.showdes = showdes;
	}

	@Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        try {
            if (convertView != null) {
                view = convertView;
            } else {
                view = View.inflate(context, R.layout.item_news, null);

            }
            TextView item_tv_des = (TextView) view.findViewById(R.id.item_tv_des);
            TextView item_tv_name = (TextView) view.findViewById(R.id.item_tv_name);
            TextView item_tv_saler = (TextView) view
                    .findViewById(R.id.item_tv_date);

            
            NewsBean productBean = list.get(position);
            item_tv_name.setText(productBean.title);
            if(showdes){
            item_tv_des.setText(productBean.text);
            }else {
            	item_tv_des.setVisibility(View.GONE);
            	
            }

            String time = TimeUtils.getTFormater().format(productBean.time);
            item_tv_saler.setText("" + time);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }
}
