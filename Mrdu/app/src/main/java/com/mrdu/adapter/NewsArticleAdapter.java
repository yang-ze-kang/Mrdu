package com.mrdu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.mrdu.R;

import java.util.List;
import java.util.Random;

public class NewsArticleAdapter extends RecyclerView.Adapter<NewsArticleAdapter.VH> {


    private List<String> datas;
    private int itemCount;
    private Context mContext;


    static class VH extends RecyclerView.ViewHolder {

        private final TextView text;
        private final RoundedImageView image;

        public VH(View itemView) {
            super(itemView);
            text=(TextView)itemView.findViewById(R.id.news_article_item_text);
            image=(RoundedImageView)itemView.findViewById(R.id.news_article_item_image);
        }

        public void setText(String data){
            text.setText(data);
        }
    }

    public NewsArticleAdapter(Context mContext,List<String> data){
        this.mContext=mContext;
        this.datas =data;
        itemCount=data.size();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_article_item,parent,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
            String data=datas.get(position);
            holder.text.setText(data);
            String src="test_intro"+String.valueOf(position+1);
            int rid=mContext.getResources().getIdentifier(src,"drawable","com.mrdu");
            holder.image.setImageResource(rid);
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }
}
