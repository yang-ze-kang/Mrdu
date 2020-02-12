package com.mrdu.util;

import java.text.SimpleDateFormat;

public class TimeUtils {
    public static SimpleDateFormat getFormater(){
        return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    }
    public static SimpleDateFormat getUploadTimeFormater(){
        return new SimpleDateFormat("yyyy.MM.dd HH:mm");
    }
    public static SimpleDateFormat getTFormater(){
        return new SimpleDateFormat("yyyy.MM.dd ");
    }
}
