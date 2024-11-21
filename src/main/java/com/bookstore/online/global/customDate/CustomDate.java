package com.bookstore.online.global.customDate;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class
CustomDate {

  //현재 날짜 및 시간 구하는 코드
  public static String LocalDateTimeFormat() {
    String pattern = "yyyy-MM-dd HH:mm:ss";
    Calendar now = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String nowDateTime = simpleDateFormat.format(now.getTime());
    return nowDateTime;
  }

  //현재 날짜 구하는 코드
  public static String LocalDateFormat() {
    String pattern = "yyyy-MM-dd";
    Calendar now = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String nowDateTime = simpleDateFormat.format(now.getTime());
    return nowDateTime;
  }


}
