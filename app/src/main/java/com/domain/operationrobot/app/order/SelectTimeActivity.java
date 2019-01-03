package com.domain.operationrobot.app.order;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.domain.library.base.AbsActivity;
import com.domain.library.widgets.DatePickerView;
import com.domain.operationrobot.R;
import com.domain.operationrobot.listener.ThrottleLastClickListener;
import com.domain.operationrobot.util.TimeUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SelectTimeActivity extends AbsActivity {
  private static final int    MAX_MINUTE   = 59;
  private static final int    MAX_HOUR     = 23;
  private static final int    MIN_MINUTE   = 0;
  private static final int    MIN_HOUR     = 0;
  private static final int    MAX_MONTH    = 12;
  private static       String START_TIME   = "2010-01-01";
  private static       String DEFAULT_TIME = "2018-01-01";
  private DatePickerView year_pv;
  private DatePickerView month_pv;
  private DatePickerView day_pv;
  private Calendar       startCalendar, endCalendar;
  private int startYear, startMonth, startDay, startHour, startMinute, endYear, endMonth, endDay, endHour, endMinute;
  private boolean spanYear, spanMon, spanDay, spanHour, spanMin;
  private ArrayList<String> year, month, day;
  private TextView mTv_start_time;
  private TextView mTv_end_time;
  ThrottleLastClickListener listener = new ThrottleLastClickListener() {
    @Override
    public void onViewClick(View v) {
      switch (v.getId()) {
        case R.id.iv_back:
          finish();
          break;
        case R.id.tv_complete:
          String startTime = mTv_start_time.getText()
                                           .toString()
                                           .trim();
          String endTime = mTv_end_time.getText()
                                       .toString()
                                       .trim();
          if (TextUtils.isEmpty(startTime)) {
            showToast("开始时间不能为空");
            return;
          }
          if (TextUtils.isEmpty(endTime)) {
            showToast("结束时间不能为空");
            return;
          }
          if (TimeUtil.getTime(endTime) - TimeUtil.getTime(startTime) <= 0) {
            showToast("结束时间必须大于开始时间");
            return;
          }
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
          String now = sdf.format(new Date());
          if (TimeUtil.getTime(endTime) - TimeUtil.getTime(now)>0){
            showToast("不能超过当前时间");
            return;
          }
          Intent intent = new Intent();
          intent.putExtra("startTime", startTime);
          intent.putExtra("endTime", endTime);
          setResult(RESULT_OK, intent);
          finish();
          break;
      }
    }
  };
  private String y = "2018", m = "01", d = "01";

  private boolean   startSelected;
  private ImageView mIv_clear;

  @Override
  protected int getLayoutId() {
    return R.layout.activity_select_time;
  }

  @Override
  protected void newInstancePresenter() {
    startCalendar = Calendar.getInstance();
    endCalendar = Calendar.getInstance();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    String now = sdf.format(new Date());
    try {
      startCalendar.setTime(sdf.parse(START_TIME));
      endCalendar.setTime(sdf.parse(now));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    endYear = endCalendar.get(Calendar.YEAR);
    endMonth = endCalendar.get(Calendar.MONTH) + 1;
    endDay = endCalendar.get(Calendar.DAY_OF_MONTH);

    resetTime();
  }

  private void resetTime() {
    startYear = startCalendar.get(Calendar.YEAR);
    startMonth = startCalendar.get(Calendar.MONTH) + 1;
    startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
    spanYear = startYear != endYear;
    spanMon = (!spanYear) && (startMonth != endMonth);
    spanDay = (!spanMon) && (startDay != endDay);
    initTimer();
  }

  @Override
  protected void initView() {
    findViewById(R.id.iv_back).setOnClickListener(listener);
    findViewById(R.id.tv_complete).setOnClickListener(listener);
    year_pv = (DatePickerView) findViewById(R.id.year_pv);
    month_pv = (DatePickerView) findViewById(R.id.month_pv);
    day_pv = (DatePickerView) findViewById(R.id.day_pv);
    mTv_start_time = findViewById(R.id.tv_start_time);
    mTv_end_time = findViewById(R.id.tv_end_time);
    loadComponent();
    startSelected = true;
    mTv_start_time.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startSelected = true;
        mTv_start_time.setBackgroundResource(R.drawable.shape_bottom_selected);
        mTv_end_time.setBackgroundResource(R.drawable.shape_bottom_normal);
        mTv_start_time.setTextColor(Color.parseColor("#23AF8C"));
        mTv_end_time.setTextColor(Color.parseColor("#C0C0C0"));
        String startTime = mTv_start_time.getText()
                                         .toString()
                                         .trim();
        setPickView(startTime);
      }
    });
    mTv_end_time.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startSelected = false;
        mTv_end_time.setBackgroundResource(R.drawable.shape_bottom_selected);
        mTv_start_time.setBackgroundResource(R.drawable.shape_bottom_normal);
        mTv_end_time.setTextColor(Color.parseColor("#23AF8C"));
        mTv_start_time.setTextColor(Color.parseColor("#C0C0C0"));
        String endTime = mTv_end_time.getText()
                                     .toString()
                                     .trim();
        setPickView(endTime);
      }
    });

    mIv_clear = findViewById(R.id.iv_clear);

    mIv_clear.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (startSelected) {
          mTv_start_time.setText("");
        } else {
          mTv_end_time.setText("");
        }
      }
    });

    mTv_start_time.setText(y + "-" + m + "-" + d);
    year_pv.setOnSelectListener(new DatePickerView.onSelectListener() {
      @Override
      public void onSelect(String text) {
        y = text.replace("年", "");
        if (startSelected) {
          mTv_start_time.setText(y + "-" + m + "-" + d);
        } else {
          mTv_end_time.setText(y + "-" + m + "-" + d);
        }
      }
    });
    month_pv.setOnSelectListener(new DatePickerView.onSelectListener() {
      @Override
      public void onSelect(String text) {
        m = text.replace("月", "");
        if (startSelected) {
          mTv_start_time.setText(y + "-" + m + "-" + d);
        } else {
          mTv_end_time.setText(y + "-" + m + "-" + d);
        }
      }
    });
    day_pv.setOnSelectListener(new DatePickerView.onSelectListener() {
      @Override
      public void onSelect(String text) {
        d = text.replace("日", "");
        if (startSelected) {
          mTv_start_time.setText(y + "-" + m + "-" + d);
        } else {
          mTv_end_time.setText(y + "-" + m + "-" + d);
        }
      }
    });
  }

  private void setPickView(String time) {
    if (!TextUtils.isEmpty(time)) {
      String[] split = time.split("-");
      year_pv.setSelected(split[0] + "年");
      month_pv.setSelected(split[1] + "月");
      day_pv.setSelected(split[2] + "日");
    }
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
    year_pv.setIsLoop(false);
    month_pv.setIsLoop(false);
    day_pv.setIsLoop(false);
    year_pv.setSelected("2018年");
    month_pv.setSelected("01月");
    day_pv.setSelected("01日");
    executeScroll();
  }

  private void executeScroll() {
    year_pv.setCanScroll(year.size() > 1);
    month_pv.setCanScroll(month.size() > 1);
    day_pv.setCanScroll(day.size() > 1);
  }
}
