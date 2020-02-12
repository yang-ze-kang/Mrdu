package com.mrdu.bean;

import android.net.Uri;

public class VideoBean {
    public int mImageId;
    public String mVideoPath;
    public Uri mVideoUri;
    public VideoBean(int mImageId,String mVideoPath){
        this.mImageId=mImageId;
        this.mVideoPath=mVideoPath;
        mVideoUri=Uri.parse(mVideoPath);
    }

}
