package com.domain.operationrobot.app.order;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.domain.library.base.AbsActivity;
import com.domain.library.widgets.DatePickerView;
import com.domain.operationrobot.R;
import com.domain.operationrobot.listener.ThrottleLastClickListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SelectTimeActivity extends AbsActivity {
  private static final int MAX_MINUTE = 59;
  private static final int MAX_HOUR   = 23;
  private static final int MIN_MINUTE = 0;
  private static final int MIN_HOUR   = 0;
  private static final int MAX_MONTH  = 12;
  ThrottleLastClickListener listener = new ThrottleLastClickListener() {
    @Override
    public void onViewClick(View v) {
      switch (v.getId()) {
        case R.id.iv_back:
          finish();
          break;
        case R.id.tv_complete:
          break;
      }
    }
  };
  private DatePickerView year_pv;
  private DatePickerView month_pv;
  private DatePickerView day_pv;
  private Calendar       selectedCalender, startCalendar, endCalendar;
  private int startYear, startMonth, startDay, startHour, startMinute, endYear, endMonth, endDay, endHour, endMinute;
  private boolean spanYear, spanMon, spanDay, spanHour, spanMin;
  private ArrayList<String> year, month, day;

  @Override
  protected int getLayoutId() {
    return R.layout.activity_select_time;
  }

  @Override
  protected void newInstancePresenter() {
    selectedCalender = Calendar.getInstance();
    startCalendar = Calendar.getInstance();
    endCalendar = Calendar.getInstance();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    String now = sdf.format(new Date());
    try {
      startCalendar.setTime(sdf.parse("2015-01-01"));
      endCalendar.setTime(sdf.parse(now));
    } catch (ParseException e) {
      e.printStackTrace();
    }


    startYear = startCalendar.get(Calendar.YEAR);
    startMonth = startCalendar.get(Calendar.MONTH) + 1;
    startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
    endYear = endCalendar.get(Calendar.YEAR);
    endMonth = endCalendar.get(Calendar.MONTH) + 1;
    endDay = endCalendar.get(Calendar.DAY_OF_MONTH);
    spanYear = startYear != endYear;
    spanMon = (!spanYear) && (startMonth != endMonth);
    spanDay = (!spanMon) && (startDay != endDay);
    selectedCalender.setTime(startCalendar.getTime());
    initTimer();
  }

  @Override
  protected void initView() {
    findViewById(R.id.iv_back).setOnClickListener(listener);
    findViewById(R.id.tv_complete).setOnClickListener(listener);
    year_pv = (DatePickerView) findViewById(R.id.year_pv);
    month_pv = (DatePickerView) findViewById(R.id.month_pv);
    day_pv = (DatePickerView) findViewById(R.id.day_pv);
    loadComponent();
  }

  @Override
  protected void initData() {

  }

  @Override
  public void showEmptyView() {

  }

  private void initTimer() {
    initArrayList();
    if (spanYear) {
      for (int i = startYear; i <= endYear; i++) {
        year.add(String.valueOf(i) + "年");
      }
      for (int i = startMonth; i <= MAX_MONTH; i++) {
        month.add(formatTimeUnit(i) + "月");
      }
      for (int i = startDay; i <= startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
        day.add(formatTimeUnit(i) + "日");
      }
    } else if (spanMon) {
      year.add(String.valueOf(startYear) + "年");
      for (int i = startMonth; i <= endMonth; i++) {
        month.add(formatTimeUnit(i) + "月");
      }
      for (int i = startDay; i <= startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
        day.add(formatTimeUnit(i) + "日");
      }
    } else if (spanDay) {
      year.add(String.valueOf(startYear) + "年");
      month.add(formatTimeUnit(startMonth) + "月");
      for (int i = startDay; i <= endDay; i++) {
        day.add(formatTimeUnit(i) + "日");
      }
    }
  }

  /**
   * 将“0-9”转换为“00-09”
   */
  private String formatTimeUnit(int unit) {
    return unit < 10 ? "0" + String.valueOf(unit) : String.valueOf(unit);
  }

  private void initArrayList() {
    if (year == null) {
      year = new ArrayList<>();
    }
    if (month == null) {
      month = new ArrayList<>();
    }
    if (day == null) {
      day = new ArrayList<>();
    }
    year.clear();
    month.clear();
    day.clear();
  }

  private void loadComponent() {
    year_pv.setData(year);
    month_pv.setData(month);
    day_pv.setData(day);
    year_pv.setSelected(0);
    month_pv.setSelected(0);
    day_pv.setSelected(0);
    executeScroll();
  }

  private void executeScroll() {
    year_pv.setCanScroll(year.size() > 1);
    month_pv.setCanScroll(month.size() > 1);
    day_pv.setCanScroll(day.size() > 1);
  }
}
