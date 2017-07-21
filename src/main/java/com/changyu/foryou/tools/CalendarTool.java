package com.changyu.foryou.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CalendarTool {
    public static Date getMondayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 00, 00, 00);
        return c.getTime();
    }

    public static Date getSundayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 23, 59, 59);
        return c.getTime();
    }

    public static Date getTodayStart() {
        Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 00, 00, 00);
        return c.getTime();
    }

    public static Date getTodayEnd() {
        Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 23, 59, 59);
        return c.getTime();
    }

    public static Date getFirstDayOfThisMonth() {
        Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 00, 00, 00);
        return c.getTime();
    }

    public static Date getFirstDayOfThisMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 00, 00, 00);
        return c.getTime();
    }

    public static Date getLastDayOfThisMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.getMaximum(Calendar.DATE));
        return calendar.getTime();
    }

    public static Date getLastDayOfThisMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, c.getMaximum(Calendar.DATE));
        return c.getTime();
    }

    public static Map<String, Date> getFirstAndLastDayOfMonth(String month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = sdf.parse(month);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Map<String, Date> map = new HashMap<String, Date>();
        map.put("monthStart", getFirstDayOfThisMonth(date));
        map.put("monthEnd", getLastDayOfThisMonth(date));
        return map;
    }

    public static Boolean checkIsThisMonth(String monthStr) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;//月份从0开始

        int indexYear = monthStr.indexOf(String.valueOf(year));
        int indexMonth = monthStr.lastIndexOf(String.valueOf(month));

        if (indexYear == -1 || indexMonth == -1) {
            //说明不是当前年月
            return false;
        }
        return true;
    }
}
