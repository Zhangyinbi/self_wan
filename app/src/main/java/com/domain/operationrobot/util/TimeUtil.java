package com.domain.operationrobot.util;

import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.R;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间转换工具
 */
public class TimeUtil {

  public static final String FORMAT_DATE_EN = "yyyy-MM-dd";
  public static final String FORMAT_DATE_CN = "yyyy年MM月dd日";

  public static final String FORMAT_TIME_CN   = "yyyy年MM月dd HH时mm分ss秒";
  public static final String FORMAT_TIME_CN_2 = "yyyy年MM月dd HH时mm分";
  public static final String FORMAT_TIME_EN   = "yyyy-MM-dd HH:mm:ss";
  public static final String FORMAT_TIME_EN_2 = "yyyy-MM-dd HH:mm";

  public static final String FORMAT_DAY_CN   = "HH时mm分ss秒";
  public static final String FORMAT_DAY_CN_2 = "HH时mm分";
  public static final String FORMAT_DAY_EN   = "HH:mm:ss";
  public static final String FORMAT_DAY_EN_2 = "HH:mm";
  public static final String FORMAT_DAY_EN_3 = "mm:ss";

  private TimeUtil() {
  }

  public static String formatTimeMillis(long time) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    return sdf.format(time);
  }

  /**
   * 时间转化为显示字符串
   *
   * @param timeStamp 单位为秒
   */
  public static String getTimeStr(long timeStamp) {
    if (timeStamp == 0) {
      return "";
    }
    if (((System.currentTimeMillis()) - timeStamp) < 60000) {
      return "刚刚";
    }
    timeStamp = timeStamp / 1000;
    Calendar inputTime = Calendar.getInstance();
    inputTime.setTimeInMillis(timeStamp * 1000);
    Date currenTimeZone = inputTime.getTime();
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    if (calendar.before(inputTime)) {
      //今天23:59在输入时间之前，解决一些时间误差，把当天时间显示到这里
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + BaseApplication.getInstance()
                                                                          .getResources()
                                                                          .getString(R.string.time_year) + "MM" + BaseApplication.getInstance()
                                                                                                                                 .getResources()
                                                                                                                                 .getString(
                                                                                                                                   R.string.time_month)
        + "dd" + BaseApplication.getInstance()
                                .getResources()
                                .getString(R.string.time_day));
      return sdf.format(currenTimeZone);
    }
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    if (calendar.before(inputTime)) {
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
      return sdf.format(currenTimeZone);
    }
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    if (calendar.before(inputTime)) {
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

      return BaseApplication.getInstance()
                            .getResources()
                            .getString(R.string.time_yesterday) + " " + sdf.format(currenTimeZone);
    } else {
      calendar.set(Calendar.DAY_OF_MONTH, 1);
      calendar.set(Calendar.MONTH, Calendar.JANUARY);
      if (calendar.before(inputTime)) {
        SimpleDateFormat sdf = new SimpleDateFormat("M" + BaseApplication.getInstance()
                                                                         .getResources()
                                                                         .getString(R.string.time_month) + "d" + BaseApplication.getInstance()
                                                                                                                                .getResources()
                                                                                                                                .getString(
                                                                                                                                  R.string.time_day));
        return sdf.format(currenTimeZone);
      } else {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + BaseApplication.getInstance()
                                                                            .getResources()
                                                                            .getString(R.string.time_year) + "MM" + BaseApplication.getInstance()
                                                                                                                                   .getResources()
                                                                                                                                   .getString(
                                                                                                                                     R.string.time_month)
          + "dd" + BaseApplication.getInstance()
                                  .getResources()
                                  .getString(R.string.time_day));
        return sdf.format(currenTimeZone);
      }
    }
  }

  /**
   * 时间转化为聊天界面显示字符串
   *
   * @param timeStamp 单位为秒
   */
  public static String getChatTimeStr(long timeStamp) {
    if (timeStamp == 0) {
      return "";
    }
    Calendar inputTime = Calendar.getInstance();
    inputTime.setTimeInMillis(timeStamp * 1000);
    Date currenTimeZone = inputTime.getTime();
    Calendar calendar = Calendar.getInstance();
    if (calendar.before(inputTime)) {
      //当前时间在输入时间之前
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + BaseApplication.getInstance()
                                                                          .getResources()
                                                                          .getString(R.string.time_year) + "MM" + BaseApplication.getInstance()
                                                                                                                                 .getResources()
                                                                                                                                 .getString(
                                                                                                                                   R.string.time_month)
        + "dd" + BaseApplication.getInstance()
                                .getResources()
                                .getString(R.string.time_day));
      return sdf.format(currenTimeZone);
    }
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    if (calendar.before(inputTime)) {
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
      return sdf.format(currenTimeZone);
    }
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    if (calendar.before(inputTime)) {
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
      return BaseApplication.getInstance()
                            .getResources()
                            .getString(R.string.time_yesterday) + " " + sdf.format(currenTimeZone);
    } else {
      calendar.set(Calendar.DAY_OF_MONTH, 1);
      calendar.set(Calendar.MONTH, Calendar.JANUARY);
      if (calendar.before(inputTime)) {
        SimpleDateFormat sdf = new SimpleDateFormat("M" + BaseApplication.getInstance()
                                                                         .getResources()
                                                                         .getString(R.string.time_month) + "d" + BaseApplication.getInstance()
                                                                                                                                .getResources()
                                                                                                                                .getString(
                                                                                                                                  R.string.time_day)
          + " HH:mm");
        return sdf.format(currenTimeZone);
      } else {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + BaseApplication.getInstance()
                                                                            .getResources()
                                                                            .getString(R.string.time_year) + "MM" + BaseApplication.getInstance()
                                                                                                                                   .getResources()
                                                                                                                                   .getString(
                                                                                                                                     R.string.time_month)
          + "dd" + BaseApplication.getInstance()
                                  .getResources()
                                  .getString(R.string.time_day) + " HH:mm");
        return sdf.format(currenTimeZone);
      }
    }
  }

  /**
   * @param timeStamp 毫秒
   * @return 将时间转化为还有多少天数
   */
  public static BigDecimal getTimeDay(BigDecimal timeStamp) {
    BigDecimal dayNum = (timeStamp.divide(BigDecimal.valueOf(60 * 60 * 24), 0, BigDecimal.ROUND_DOWN));
    return dayNum;
  }

  /**
   * @param timeStamp 毫秒
   * @return 18天12小时48分50秒
   */
  public static String getTime(BigDecimal timeStamp) {
    BigDecimal day = timeStamp.divide(BigDecimal.valueOf(24 * 60 * 60), 0, BigDecimal.ROUND_DOWN);
    BigDecimal hour = timeStamp.divide(BigDecimal.valueOf(60 * 60), 0, BigDecimal.ROUND_DOWN)
                               .subtract(day.multiply(BigDecimal.valueOf(24)));
    BigDecimal min = (timeStamp.divide(BigDecimal.valueOf((60)), 0, BigDecimal.ROUND_DOWN)).subtract(day.multiply(BigDecimal.valueOf(24 * 60)))
                                                                                           .subtract(hour.multiply(BigDecimal.valueOf(60)));
    BigDecimal sec = (timeStamp.subtract(day.multiply(BigDecimal.valueOf(24 * 60 * 60)))
                               .subtract(hour.multiply(BigDecimal.valueOf(60 * 60)))
                               .subtract(min.multiply(BigDecimal.valueOf(60))));
    String time = "";

    if (day.compareTo(BigDecimal.valueOf(0)) > 0) {
      time += day + "天";
    }
    if (hour.compareTo(BigDecimal.valueOf(0)) > 0) {
      time += hour + "小时";
    }
    if (min.compareTo(BigDecimal.valueOf(0)) > 0) {
      time += min + "分";
    }
    if (sec.compareTo(BigDecimal.valueOf(0)) > 0) {
      time += sec + "秒";
    }
    return time;
  }

  public static String strToDate(String time) {
    try {
      SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日");
      @SuppressWarnings("unused")
      long lcc = Long.valueOf(time);
      int i = Integer.parseInt(time);
      String times = sdr.format(new Date(i * 1000L));
      return times;
    }catch (NumberFormatException e){
      return  time;
    }

  }

  public static long getTime(String user_time) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date d;
    long l = 0;
    try {
      d = sdf.parse(user_time);
      l = d.getTime();
    } catch (ParseException e) {
      // TODO Auto-generated catch block e.printStackTrace();
    }
    return l;
  }
}
