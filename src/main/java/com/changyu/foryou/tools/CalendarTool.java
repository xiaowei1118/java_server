package com.changyu.foryou.tools;

import java.util.Calendar;
import java.util.Date;

public class CalendarTool {
	public static Date getMondayOfThisWeek() {
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 00, 00, 00);
		//return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
		return c.getTime();
	}
	
	public static Date getSundayOfThisWeek() {
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 7);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 23, 59, 59);
		//return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
		return c.getTime();
	}
	
	public static Date getTodayStart(){
		Calendar c = Calendar.getInstance();
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 00, 00, 00);
		return c.getTime();
	}
	
	public static Date getTodayEnd(){
		Calendar c = Calendar.getInstance();
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 23, 59, 59);
		return c.getTime();
	}
	
	public static Date getFirstDayOfThisMonth(){
		Calendar c = Calendar.getInstance();
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 00, 00, 00);
		return c.getTime();
	}
	
	public static Date getLastDayOfThisMonth(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, calendar.getMaximum(Calendar.DATE));
		return calendar.getTime();
	}
}
