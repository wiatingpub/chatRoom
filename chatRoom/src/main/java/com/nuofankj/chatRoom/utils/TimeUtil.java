package com.nuofankj.chatRoom.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author xifanxiaxue
 * @date 2020/6/4 8:40
 * @desc 时间工具类
 */
public class TimeUtil {

    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";

    /**
     * 获取当前时间的字符串
     *
     * @param format 字符串格式，如：yy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return sdf.format(timestamp);
    }

    public static String getCurrentTime() {
        return getCurrentTime(DEFAULT_TIME_PATTERN);
    }
}
