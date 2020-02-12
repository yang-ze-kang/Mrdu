package com.mrdu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MyRoundedImageView extends ImageView {


    //圆角的半径，依次为左上角xy半径，右上角xy半径，右下角xy半径，左下角xy半径


    private float[] rids = {10.0f, 10.0f, 10.0f, 10.0f, 0.0f, 0.0f, 0.0f, 0.0f,};


    public MyRoundedImageView(Context context) {

        super(context);

    }


    public MyRoundedImageView(Context context, AttributeSet attrs) {

        super(context, attrs);

    }


    public MyRoundedImageView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

    }


    protected void onDraw(Canvas canvas) {

        Path path = new Path();

        int w = this.getWidth();

        int h = this.getHeight();

        /*向路径中添加圆角矩形。radii数组定义圆角矩形的四个圆角的x,y半径。radii长度必须为8*/

        path.addRoundRect(new RectF(0, 0, w, h), rids, Path.Direction.CW);

        canvas.clipPath(path);

        super.onDraw(canvas);

    }
}
