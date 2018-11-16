/**
 * 
 */
package com.free.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @comment 时间函数工具类
 * 
 * @author hsiangcn@sina.com
 * @data 2018年11月16日
 */
public class DateUtils {

	private static Logger log = LoggerFactory.getLogger(DateUtils.class);

	private static final long ONE_MINUTE = 60000L;
	private static final long ONE_HOUR = 3600000L;
	private static final long ONE_DAY = 86400000L;
	private static final long ONE_WEEK = 604800000L;

	private static final String ONE_SECOND_AGO = "秒前";
	private static final String ONE_MINUTE_AGO = "分钟前";
	private static final String ONE_HOUR_AGO = "小时前";
	private static final String ONE_DAY_AGO = "天前";
	private static final String ONE_MONTH_AGO = "月前";
	private static final String ONE_YEAR_AGO = "年前";

	/**
	 *	时间格式		yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/**
	 *	时间格式		yyyy-MM-dd HH:mm
	 */
	public static final String MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
	/**
	 *	时间格式		yyyy-MM-dd HH:mm:ss
	 */
	public static final String HOUR_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/**
	 *	时间格式		yyyy-MM-dd
	 */
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	/**
	 *	时间格式		yyyy-MM
	 */
	public static final String MONTH_PATTERN = "yyyy-MM";
	/**
	 *	时间格式		yyyy
	 */
	public static final String YEAR_PATTERN = "yyyy";
	/**
	 *	时间格式		mm
	 */
	public static final String MINUTE_ONLY_PATTERN = "mm";
	/**
	 *	时间格式		HH
	 */
	public static final String HOUR_ONLY_PATTERN = "HH";

	/**
	 * 获取当前时间并格式化成String
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentDate(String format) {
		SimpleDateFormat sdf = null;
		if (StringUtils.isNotEmpty(format)) {
			sdf = new SimpleDateFormat(format);
		} else {
			sdf = new SimpleDateFormat(DateUtils.HOUR_PATTERN);
		}
		return sdf.format(new Date());
	}

	/**
	 * 	将date类型转换成String类型
	 * 		如果date为空，则获取当前时间
	 * 		如果	format为空，则使用yyyy-MM-dd HH:mm:ss格式化时间
	 * @param format
	 * @param date
	 * @return
	 */
	public static String getCurrentDate(String format, Date date) {
		if (null == date) {
			return getCurrentDate(format);
		}
		SimpleDateFormat sdf = null;
		if (StringUtils.isNotEmpty(format)) {
			sdf = new SimpleDateFormat(format);
		} else {
			sdf = new SimpleDateFormat(DateUtils.HOUR_PATTERN);
		}
		return sdf.format(date);
	}

	/**
	 * 	将String类型转换成Date类型
	 * 		如果date为空，则获取当前时间
	 * 		如果	format为空，则使用yyyy-MM-dd HH:mm:ss格式化时间
	 * @param format
	 * @param date
	 * @return
	 */
	public static Date parse(String format, String date) {
		if (null == date) {
			return new Date();
		}
		SimpleDateFormat sdf = null;
		if (StringUtils.isNotEmpty(format)) {
			sdf = new SimpleDateFormat(format);
		} else {
			sdf = new SimpleDateFormat(DateUtils.HOUR_PATTERN);
		}
		try {
			return sdf.parse(date);
		} catch (Exception e) {
			log.error("", e);
			return new Date();
		}
	}

	/**
	 * 	对时间增加或少N分钟
	 * 
	 * @param date 时间
	 * @param minute 分钟，整数往后推,负数往前移动 
	 * @return
	 */
	public static Date addTime (Date date, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(calendar.MINUTE, minute);
		return calendar.getTime();
	}
	
	/**
	 * 将时间转换成几秒前，几分钟前，几小时前，几天前，几月前，几年前的实现
	 * 
	 * @param date
	 * @return String
	 */
	public static String relativeDateFormat(Date date) {
		long delta = new Date().getTime() - date.getTime();
		if (delta < 1L * ONE_MINUTE) {
			long seconds = toSeconds(delta);
			return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
		}
		if (delta < 45L * ONE_MINUTE) {
			long minutes = toMinutes(delta);
			return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
		}
		if (delta < 24L * ONE_HOUR) {
			long hours = toHours(delta);
			return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
		}
		if (delta < 48L * ONE_HOUR) {
			return "昨天";
		}
		if (delta < 30L * ONE_DAY) {
			long days = toDays(delta);
			return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
		}
		if (delta < 12L * 4L * ONE_WEEK) {
			long months = toMonths(delta);
			return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
		} else {
			long years = toYears(delta);
			return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
		}
	}

	private static long toSeconds(long date) {
		return date / 1000L;
	}

	private static long toMinutes(long date) {
		return toSeconds(date) / 60L;
	}

	private static long toHours(long date) {
		return toMinutes(date) / 60L;
	}

	private static long toDays(long date) {
		return toHours(date) / 24L;
	}

	private static long toMonths(long date) {
		return toDays(date) / 30L;
	}

	private static long toYears(long date) {
		return toMonths(date) / 365L;
	}

	public static void main(String[] args) {
		System.out.println(DateUtils.getCurrentDate(DateUtils.DATE_TIME_PATTERN));
	}
}
