package com.mrdu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.mrdu.R;
import com.mrdu.bean.VideoBean;

import java.util.List;

public class NewsVideoAdapter extends RecyclerView.Adapter<NewsVideoAdapter.VH> {


    private Context mContext;
    private int itemNum;
    private List<VideoBean> mVideoData;
    private OnClickPlayListener listener;


    public NewsVideoAdapter(Context mContext,List<VideoBean> mVideoData){
        this.mContext=mContext;
        this.mVideoData=mVideoData;
        itemNum=mVideoData.size();
    }

    public void setListener(OnClickPlayListener listener){
        this.listener=listener;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.news_video_item,parent,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        final VideoBean videoBean=mVideoData.get(position);
         holder.imageIntro.setImageResource(videoBean.mImageId);
         holder.imagePlay.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 listener.onPlayClick(holder.itemVideo,videoBean.mVideoPath);
             }
         });
    }

    @Override
    public int getItemCount() {
        return itemNum;
    }


    static class VH extends RecyclerView.ViewHolder {

        FrameLayout itemVideo;
        FrameLayout videoRoot;
        ImageView imageIntro;
        ImageView imagePlay;

        public VH(View itemView) {
            super(itemView);
            itemVideo=(FrameLayout) itemView.findViewById(R.id.item_video);
            videoRoot=(FrameLayout)itemView.findViewById(R.id.item_video_root);
            imageIntro=(ImageView)itemView.findViewById(R.id.item_image_intro);
            imagePlay=(ImageView)itemView.findViewById(R.id.item_image_play);
        }
    }

    public interface OnClickPlayListener{
        void onPlayClick(View view,String videoPath);
    }
}
