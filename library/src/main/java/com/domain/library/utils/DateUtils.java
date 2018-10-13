package com.domain.library.utils;


import android.content.Context;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static int sp2px(Context context, int sp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (sp * scale + 0.5f);
    }

    public static String formatTime(Long publish_time) {
        return getFormatString(publish_time, "yyyy-MM-dd HH:mm");
    }

    public static String formatTimeAll(Long publish_time) {
        return getFormatString(publish_time, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatTime(Long publish_time, String format) {
        return getFormatString(publish_time, format);
    }

    private static String getFormatString(long time, String format) {
        try {
            Date date = new Date(time);
            SimpleDateFormat normalFormat = new SimpleDateFormat(format);
            return normalFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDateAndWeek(Long timeMillis) {
        StringBuilder sb = new StringBuilder();
        String time = sb.append(DateUtils.formatTime(timeMillis, "yyyy.MM.dd"))
                .append(" ")
                .append(DateUtils.getWeek(timeMillis))
                .append(" ")
                .append(DateUtils.formatTime(timeMillis, "HH:mm")).toString();
        return time;
    }

    private static String getWeek(long timeStamp) {
        int myDate = 0;
        String week = null;
        Calendar cd = Calendar.getInstance();
        cd.setTime(new Date(timeStamp));
        myDate = cd.get(Calendar.DAY_OF_WEEK);
        if (myDate == 1) {
            week = "周日";
        } else if (myDate == 2) {
            week = "周一";
        } else if (myDate == 3) {
            week = "周二";
        } else if (myDate == 4) {
            week = "周三";
        } else if (myDate == 5) {
            week = "周四";
        } else if (myDate == 6) {
            week = "周五";
        } else if (myDate == 7) {
            week = "周六";
        }
        return week;
    }

}
