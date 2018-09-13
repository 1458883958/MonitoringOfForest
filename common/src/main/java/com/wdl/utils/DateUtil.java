package com.wdl.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.utils
 * 创建者：   wdl
 * 创建时间： 2018/9/12 19:59
 * 描述：    时间转换类
 */
@SuppressWarnings("unused")
public class DateUtil {
    /**
     * 时间转换
     *
     * @param date Date
     * @return String
     */
    public static String format(Date date) {
        if (date == null) return null;
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
