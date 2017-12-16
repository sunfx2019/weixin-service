package com.yimai.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.ParsePosition;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 日期处理工具类
 * 
 * @author sunfx
 */

public class DateUtil {

	public static final String FORMAT_1 = "yyyy-MM-dd";
	public static final String FORMAT_2 = "yy-MM-dd";
	public static final String FORMAT_3 = "HH:mm:ss";
	public static final String FORMAT_4 = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYYMMDD = "yyyyMMdd";

	private static String defaultDatePattern = null;
	private static String timePattern = "HH:mm";
	//private static Calendar cale = Calendar.getInstance();

	private static SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_1);
	private static SimpleDateFormat sdf1 = new SimpleDateFormat(FORMAT_3);
	private static SimpleDateFormat sdf2 = new SimpleDateFormat(FORMAT_4);

	public static final String TS_FORMAT = DateUtil.getDatePattern() + " HH:mm:ss.S";

	private static Log log = LogFactory.getLog(DateUtil.class);

	// ~ Methods
	// ================================================================

	public DateUtil() {
	}

	/**
	 * 获得服务器当前日期及时间，以格式为：yyyy-MM-dd HH:mm:ss的日期字符串形式返回
	 */
	public static String getDateTime() {
		try {
			return sdf2.format(Calendar.getInstance().getTime());
		} catch (Exception e) {
			log.info("DateUtil.getDateTime():" + e.getMessage());
			return "";
		}
	}

	/**
	 * 获得服务器当前日期，以格式为：yyyy-MM-dd的日期字符串形式返回
	 */
	public static String getDate() {
		try {
			return sdf.format(Calendar.getInstance().getTime());
		} catch (Exception e) {
			log.info("DateUtil.getDate():" + e.getMessage());
			return "";
		}
	}

	/**
	 * 获得服务器当前时间，以格式为：HH:mm:ss的日期字符串形式返回
	 */
	public static String getTime() {
		String temp = " ";
		try {
			temp += sdf1.format(Calendar.getInstance().getTime());
			return temp;
		} catch (Exception e) {
			log.debug("DateUtil.getTime():" + e.getMessage());
			return "";
		}
	}

	/**
	 * 统计时开始日期的默认值
	 */
	public static String getStartDate() {
		try {
			return getYear() + "-01-01";
		} catch (Exception e) {
			log.debug("DateUtil.getStartDate():" + e.getMessage());
			return "";
		}
	}

	/**
	 * 统计时结束日期的默认值
	 */
	public static String getEndDate() {
		try {
			return getDate();
		} catch (Exception e) {
			log.debug("DateUtil.getEndDate():" + e.getMessage());
			return "";
		}
	}

	/**
	 * 获得服务器当前日期的年份
	 */
	public static String getYear() {
		try {
			return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		} catch (Exception e) {
			log.debug("DateUtil.getYear():" + e.getMessage());
			return "";
		}
	}

	/**
	 * 获得服务器当前日期的月份
	 */
	public static String getMonth() {
		try {
			java.text.DecimalFormat df = new java.text.DecimalFormat();
			df.applyPattern("00;00");
			return df.format((Calendar.getInstance().get(Calendar.MONTH) + 1));
		} catch (Exception e) {
			log.debug("DateUtil.getMonth():" + e.getMessage());
			return "";
		}
	}

	/**
	 * 获得服务器在当前月中天数
	 */
	public static String getDay() {
		try {
			return String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		} catch (Exception e) {
			log.debug("DateUtil.getDay():" + e.getMessage());
			return "";
		}
	}

	/**
	 * 比较两个日期相差的天数
	 * 
	 */
	public static int getMargin(String date1, String date2) {
		int margin;
		try {
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos1 = new ParsePosition(0);
			Date dt1 = sdf.parse(date1, pos);
			Date dt2 = sdf.parse(date2, pos1);
			long l = dt1.getTime() - dt2.getTime();
			margin = (int) (l / (24 * 60 * 60 * 1000));
			return margin;
		} catch (Exception e) {
			log.debug("DateUtil.getMargin():" + e.toString());
			return 0;
		}
	}

	/**
	 * 比较两个日期相差的天数
	 */
	public static double getDoubleMargin(String date1, String date2) {
		double margin;
		try {
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos1 = new ParsePosition(0);
			Date dt1 = sdf2.parse(date1, pos);
			Date dt2 = sdf2.parse(date2, pos1);
			long l = dt1.getTime() - dt2.getTime();
			margin = (l / (24 * 60 * 60 * 1000.00));
			return margin;
		} catch (Exception e) {
			log.debug("DateUtil.getMargin():" + e.toString());
			return 0;
		}
	}

	/**
	 * 比较两个日期相差的月数
	 */
	public static int getMonthMargin(String date1, String date2) {
		int margin;
		try {
			margin = (Integer.parseInt(date2.substring(0, 4)) - Integer.parseInt(date1.substring(0, 4))) * 12;
			margin += (Integer.parseInt(date2.substring(4, 7).replaceAll("-0", "-"))
					- Integer.parseInt(date1.substring(4, 7).replaceAll("-0", "-")));
			return margin;
		} catch (Exception e) {
			log.debug("DateUtil.getMargin():" + e.toString());
			return 0;
		}
	}

	/**
	 * 返回日期加X天后的日期
	 */
	public static String addDay(String date, int i) {
		try {
			GregorianCalendar gCal = new GregorianCalendar(Integer.parseInt(date.substring(0, 4)),
					Integer.parseInt(date.substring(5, 7)) - 1, Integer.parseInt(date.substring(8, 10)));
			gCal.add(GregorianCalendar.DATE, i);
			return sdf.format(gCal.getTime());
		} catch (Exception e) {
			log.debug("DateUtil.addDay():" + e.toString());
			return getDate();
		}
	}

	/**
	 * 返回日期加X月后的日期
	 */
	public static String addMonth(String date, int i) {
		try {
			GregorianCalendar gCal = new GregorianCalendar(Integer.parseInt(date.substring(0, 4)),
					Integer.parseInt(date.substring(5, 7)) - 1, Integer.parseInt(date.substring(8, 10)));
			gCal.add(GregorianCalendar.MONTH, i);
			return sdf.format(gCal.getTime());
		} catch (Exception e) {
			log.debug("DateUtil.addMonth():" + e.toString());
			return getDate();
		}
	}

	/**
	 * 返回日期加X年后的日期
	 */
	public static String addYear(String date, int i) {
		try {
			GregorianCalendar gCal = new GregorianCalendar(Integer.parseInt(date.substring(0, 4)),
					Integer.parseInt(date.substring(5, 7)) - 1, Integer.parseInt(date.substring(8, 10)));
			gCal.add(GregorianCalendar.YEAR, i);
			return sdf.format(gCal.getTime());
		} catch (Exception e) {
			log.debug("DateUtil.addYear():" + e.toString());
			return "";
		}
	}

	/**
	 * 返回某年某月中的最大天
	 */
	public static int getMaxDay(String year, String month) {
		int day = 0;
		try {
			int iyear = Integer.parseInt(year);
			int imonth = Integer.parseInt(month);
			if (imonth == 1 || imonth == 3 || imonth == 5 || imonth == 7 || imonth == 8 || imonth == 10
					|| imonth == 12) {
				day = 31;
			} else if (imonth == 4 || imonth == 6 || imonth == 9 || imonth == 11) {
				day = 30;
			} else if ((0 == (iyear % 4)) && (0 != (iyear % 100)) || (0 == (iyear % 400))) {
				day = 29;
			} else {
				day = 28;
			}
			return day;
		} catch (Exception e) {
			log.debug("DateUtil.getMonthDay():" + e.toString());
			return 1;
		}
	}

	/**
	 * 格式化日期
	 */
	@SuppressWarnings("static-access")
	public String rollDate(String orgDate, int Type, int Span) {
		try {
			String temp = "";
			int iyear, imonth, iday;
			int iPos = 0;
			char seperater = '-';
			if (orgDate == null || orgDate.length() < 6) {
				return "";
			}

			iPos = orgDate.indexOf(seperater);
			if (iPos > 0) {
				iyear = Integer.parseInt(orgDate.substring(0, iPos));
				temp = orgDate.substring(iPos + 1);
			} else {
				iyear = Integer.parseInt(orgDate.substring(0, 4));
				temp = orgDate.substring(4);
			}

			iPos = temp.indexOf(seperater);
			if (iPos > 0) {
				imonth = Integer.parseInt(temp.substring(0, iPos));
				temp = temp.substring(iPos + 1);
			} else {
				imonth = Integer.parseInt(temp.substring(0, 2));
				temp = temp.substring(2);
			}

			imonth--;
			if (imonth < 0 || imonth > 11) {
				imonth = 0;
			}

			iday = Integer.parseInt(temp);
			if (iday < 1 || iday > 31)
				iday = 1;

			Calendar orgcale = Calendar.getInstance();
			orgcale.set(iyear, imonth, iday);
			temp = this.rollDate(orgcale, Type, Span);
			return temp;
		} catch (Exception e) {
			return "";
		}
	}

	public static String rollDate(Calendar cal, int Type, int Span) {
		try {
			String temp = "";
			Calendar rolcale;
			rolcale = cal;
			rolcale.add(Type, Span);
			temp = sdf.format(rolcale.getTime());
			return temp;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 
	 * 返回默认的日期格式
	 * 
	 */
	public static synchronized String getDatePattern() {
		defaultDatePattern = "yyyy-MM-dd";
		return defaultDatePattern;
	}

	/**
	 * 将指定日期按默认格式进行格式代化成字符串后输出如：yyyy-MM-dd
	 */
	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(getDatePattern());
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * 取得给定日期的时间字符串，格式为当前默认时间格式
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}

	/**
	 * 取得当前时间的Calendar日历对象
	 */
	public Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(getDatePattern());
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));
		return cal;
	}

	/**
	 * 返回 今天是 2009月4月21日 星期三 下午 02:30:30 格式 时间
	 */
	public static final String getDateTime(Date data) {

		String datastr = getDateTime("yyyy-MM-dd HH:mm:ss", data);
		String year = datastr.substring(0, 4) + "年";
		String month = datastr.substring(5, 7) + "月";
		String day = datastr.substring(8, 10) + "日";

		// System.out.println(year + " " + month + " " + day);
		// System.out.println(data.toLocaleString());

		String time = datastr.substring(10, 16);
		return year + month + day + time;

	}

	/**
	 * 将日期类转换成指定格式的字符串形式
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * 将指定的日期转换成 yyyy-MM-dd HH:mm:ss 的字符串形式
	 */
	public static final String convertDateToYYYYMMDDHHMMSS(Date aDate) {
		return sdf2.format(aDate);
	}

	/**
	 * 获取年月日时间  yyyyMMdd
	 * @param date
	 * @return
	 */
	public static String convertDateToYYYYMMDD(Date date) {
		return convertToString(date, YYYYMMDD);
	}

	/**
	 * 将日期字符串按指定格式转换成日期类型
	 * 
	 * @param aMask
	 *            指定的日期格式，如:yyyy-MM-dd
	 * @param strDate
	 *            待转换的日期字符串
	 */

	public static final Date convertStringToDate(String aMask, String strDate) {

		SimpleDateFormat df = null;
		Date date = null;

		df = new SimpleDateFormat(aMask);

		if (log.isDebugEnabled()) {
			log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
		}
		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			log.error("ParseException: " + pe);
		}
		return (date);
	}

	/**
	 * 将日期字符串按默认格式转换成 yyyy-mm-dd 日期类型
	 */
	public static Date convertStringToDate(String strDate) {
		Date aDate = null;
		try {
			if (log.isDebugEnabled()) {
				log.debug("converting date with pattern: " + getDatePattern());
			}
			aDate = convertStringToDate(getDatePattern(), strDate);
		} catch (Exception pe) {
			log.error("Could not convert '" + strDate + "' to a date, throwing exception");
		}
		return aDate;
	}

	/**
	 * 返回一个JAVA简单类型的日期字符串
	 */
	public static String getSimpleDateFormat() {
		SimpleDateFormat formatter = new SimpleDateFormat();
		String NDateTime = formatter.format(new Date());
		return NDateTime;
	}

	/**
	 * 将两个字符串格式的日期进行比较
	 * 
	 * @param last
	 *            要比较的第一个日期字符串
	 * @param now
	 *            要比较的第二个日期格式字符串
	 * @return true(last 在now 日期之前),false(last 在now 日期之后)
	 */
	public static boolean compareTo(String last, String now) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date temp1 = formatter.parse(last);
			Date temp2 = formatter.parse(now);
			if (temp1.after(temp2))
				return false;
			else if (temp1.before(temp2))
				return true;
		} catch (ParseException e) {
			log.debug(e.getMessage());
		}
		return false;
	}

	protected Object convertToDate(Class<?> type, Object value) throws Exception {
		DateFormat df = new SimpleDateFormat(TS_FORMAT);
		if (value instanceof String) {
			try {
				if (StringUtils.isEmpty(value.toString())) {
					return null;
				}
				return df.parse((String) value);
			} catch (Exception pe) {
				throw pe;
			}
		} else {

			throw new Exception("Could not convert " + value.getClass().getName() + " to " + type.getName());
		}
	}

	/**
	 * 为查询日期添加最小时间
	 * 
	 * @param 目标类型Date
	 * @param 转换参数Date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Date addStartTime(Date param) {
		Date date = param;
		try {
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
			return date;
		} catch (Exception ex) {
			return date;
		}
	}

	/**
	 * 为查询日期添加最大时间
	 * 
	 * @param 目标类型Date
	 * @param 转换参数Date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Date addEndTime(Date param) {
		Date date = param;
		try {
			date.setHours(23);
			date.setMinutes(59);
			date.setSeconds(0);
			return date;
		} catch (Exception ex) {
			return date;
		}
	}

	/**
	 * 返回系统现在年份中指定月份的天数
	 * 
	 * @param 月份month
	 * @return 指定月的总天数
	 */
	@SuppressWarnings("deprecation")
	public static String getMonthLastDay(int month) {
		Date date = new Date();
		int[][] day = { { 0, 30, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
				{ 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } };
		int year = date.getYear() + 1900;
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
			return day[1][month] + "";
		} else {
			return day[0][month] + "";
		}
	}

	/**
	 * 返回指定年份中指定月份的天数
	 * 
	 * @param 年份year
	 * @param 月份month
	 * @return 指定月的总天数
	 */
	public static String getMonthLastDay(int year, int month) {
		int[][] day = { { 0, 30, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
				{ 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } };
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
			return day[1][month] + "";
		} else {
			return day[0][month] + "";
		}
	}

	/**
	 * 取得当前时间的日戳
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getTimestamp() {
		Date date = new Date();
		String timestamp = "" + (date.getYear() + 1900) + date.getMonth() + date.getDate() + date.getMinutes()
				+ date.getSeconds() + date.getTime();
		return timestamp;
	}

	/**
	 * 取得指定时间的日戳
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getTimestamp(Date date) {
		String timestamp = "" + (date.getYear() + 1900) + date.getMonth() + date.getDate() + date.getMinutes()
				+ date.getSeconds() + date.getTime();
		return timestamp;
	}

	/**
	 * 获得 tady 前一天的 当前时间
	 * 
	 * @param tady
	 * @return
	 */
	public static Date getYesterDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, -1); // c.add(Calendar.YEAR, -5);//得到前5年
		return c.getTime();
	}

	/**
	 * 获得 tady 明天的 当前时间
	 * 
	 * @return
	 */
	public static Date getTomorrowDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	// ============================================================================================
	// main
	// ============================================================================================
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		for(int i=0; i<10; i++){
			System.out.println(getDateTime());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Date:" + new Date().toLocaleString());
		// System.out.println("Date:" + new Date().toGMTString());
		// System.out.println("Date:" + new Date().toString());
		System.out.println("Date:" + DateUtil.getDate());
		System.out.println("Time:" + DateUtil.getTime());
		System.out.println("Timestamp:" + DateUtil.getTimestamp());
		System.out.println(DateUtil.convertStringToDate(DateUtil.FORMAT_4, "2018-08-08 00:00:00"));

	}
	
	public static String convertToString(Date date, String str) {
		if (date == null || StringUtil.isEmpty(str)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		return sdf.format(date);
	}

}
