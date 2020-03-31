package com.rights.order.common.util;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Date: 2017-08-14 19:09
 * version: 1.0
 *
 * @author muyunlong
 */
public final class DateUtil {

    private static final String DT_STANDARD_DATE            = "yyyy-MM-dd";
    private static final String DT_STANDARD_SHORT_DATE      = "yyyyMMdd";
    private static final String DT_STANDARD_LITTLE_DATE     = "yyMMdd";
    private static final String DT_STANDARD_DATETIME        = "yyyy-MM-dd HH:mm:ss";
    private static final String DT_STANDARD_SHORT_DATETIME  = "yyyyMMddHHmmss";
    private static final String DT_STANDARD_LITTLE_DATETIME = "yyMMddHHmmss";
    private static final String DT_STANDARD_TIME            = "HH:mm:ss";
    private static final String DT_STANDARD_SHORT_TIME      = "HHmmss";
    private static final String DT_SHORT_D                  = "dd";


    private DateUtil() {
    }


    /**
     * <p>获取当前日期</p>
     *
     * @return 当前日期 （yyyy-MM-dd）
     * @deprecated replaced by {@link #today()}
     */
    @Deprecated
    public static String getCurrDate() {
        return LocalDate.now().toString(DT_STANDARD_DATE);
    }

    /**
     * <p>获取当前日期</p>
     *
     * @return 当前日期 （yyyyMMdd）
     * @deprecated replaced by {@link #shortToday()}
     */
    @Deprecated
    public static String getCurrShortDate() {
        return new DateTime().toString(DT_STANDARD_SHORT_DATE);
    }

    /**
     * <p>获取当前日期</p>
     *
     * @return 当前日期 （yyyyMMdd）
     * @deprecated replaced by {@link #shortToday()}
     */
    @Deprecated
    public static String getCurr6ShortDate() {
        return new DateTime().toString(DT_STANDARD_SHORT_DATE);
    }


    /**
     * <p>获取当前时间</p>
     *
     * @return 当前时间 （yyyy-MM-dd HH:mm:ss）
     * @deprecated replaced by {@link #now()}
     */
    @Deprecated
    public static String getCurrDateTimeString() {
        return DateTime.now().toString(DT_STANDARD_DATETIME);
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间(Date类型) curr date time
     */
    public static Date getCurrDateTime() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * <p>获取当前时间</p>
     *
     * @return 当前时间 （yyyyMMddHHmmss）
     * @deprecated replaced by {@link #shortNow()}
     */
    @Deprecated
    public static String getCurrShortDateTime() {
        return DateTime.now().toString(DT_STANDARD_SHORT_DATETIME);
    }

    /**
     * <p>获取当前时间</p>
     *
     * @return 当前时间 （yyMMddHHmmss）
     * @deprecated replaced by {@link #littleNow()}
     */
    @Deprecated
    public static String getCurr12ShortDateTime() {
        return DateTime.now().toString(DT_STANDARD_LITTLE_DATETIME);
    }

    /**
     * <p>获取当前时间（HH:mm:ss）</p>
     *
     * @return 获取当前时间 （HH:mm:ss）
     * @deprecated replaced by {@link #time()}
     */
    @Deprecated
    public static String getCurrTime() {
        return LocalTime.now().toString(DT_STANDARD_TIME);
    }

    /**
     * <p>获取当前时间（HHmmss）</p>
     *
     * @return 获取当前时间 （HHmmss）
     * @deprecated replaced by {@link DateUtil#shortTime()}
     */
    public static String getCurrShortTime() {
        return LocalTime.now().toString(DT_STANDARD_SHORT_TIME);
    }

    /**
     * <p>获取今天剩余的秒数</p>
     *
     * @return 秒数 int
     */
    public static int oddSecondOfDay() {
        DateTime start = new DateTime();
        DateTime end = new DateTime().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        return Seconds.secondsBetween(start, end).getSeconds();
    }

    /**
     * <p>获取本周剩余的秒数</p>
     *
     * @return 秒数 int
     */
    public static int oddSecondOfWeek() {
        DateTime start = new DateTime();
        DateTime end = new DateTime().dayOfWeek().withMaximumValue().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        return Seconds.secondsBetween(start, end).getSeconds();
    }

    /**
     * <p>获取本月剩余的秒数</p>
     *
     * @return 秒数 int
     */
    public static int oddSecondOfMonth() {
        DateTime start = new DateTime();
        DateTime end = new DateTime().dayOfMonth().withMaximumValue().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        return Seconds.secondsBetween(start, end).getSeconds();
    }

    /**
     * <p>到指定日期剩余的秒数</p>
     *
     * @param date yyyy-MM-dd
     * @return 秒数 int
     */
    public static int oddSecondOfDate(String date) {
        DateTime start = new DateTime();
        DateTime end = getMinDateTime(date);
        return Seconds.secondsBetween(start, end).getSeconds();
    }

    /**
     * <p>到指定日期剩余的秒数</p>
     *
     * @param dateTime yyyy-MM-dd HH:mm:ss
     * @return 秒数 int
     */
    public static int oddSecondOfDateTime(String dateTime) {
        DateTime start = new DateTime();
        DateTime end = strToDateTime(dateTime, DT_STANDARD_DATETIME);
        return Seconds.secondsBetween(start, end).getSeconds();
    }

    /**
     * <p>获取今年剩余的秒数</p>
     *
     * @return 秒数 int
     */
    public static int oddSecondOfYear() {
        DateTime start = new DateTime();
        DateTime end = new DateTime().dayOfYear().withMaximumValue().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        return Seconds.secondsBetween(start, end).getSeconds();
    }

    /**
     * 获取今天剩余分钟数
     *
     * @return 分钟数 int
     */
    public static int oddMinuteOfDay() {
        DateTime start = new DateTime();
        DateTime end = new DateTime().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        return Minutes.minutesBetween(start, end).getMinutes();
    }

    /**
     * 获取今天剩余分钟数
     *
     * @param dateTime date time
     * @return 分钟数 int
     */
    public static int oddMinuteOfNow(String dateTime) {
        DateTime start = strToDateTime(dateTime, DT_STANDARD_DATETIME);
        DateTime end = new DateTime();
        return Minutes.minutesBetween(start, end).getMinutes();
    }

    /**
     * <p>今天距离指定日期剩余天数</p> 例如：
     * <pre>
     *     {
     *         当前日期：2017-08-11
     *         oddDayOfDate(2017-08-01) = -10
     *         oddDayOfDate(2017-08-15) = 3
     *     }
     * </pre>
     *
     * @param date 日期参数（yyyy-MM-dd）
     * @return 天数 int
     */
    public static int oddDayOfDate(String date) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime currDate = new DateTime();
        DateTime paramDate = fmt.parseDateTime(date);

        return Days.daysBetween(currDate, paramDate).getDays();
    }

    /**
     * 计算两个日期相差的天数
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 天数 int
     */
    public static int oddDayOfDay(String startDate, String endDate) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime paramStartDate = fmt.parseDateTime(startDate);
        DateTime paramEndDate = fmt.parseDateTime(endDate);
        return Days.daysBetween(paramStartDate, paramEndDate).getDays();
    }

    /**
     * <p>是否包含在区间范围内（yyyy-MM-dd）</p>
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return true /false
     */
    public static boolean intervalDate(String start, String end) {

        DateTime startTime = getMinDateTime(start);
        DateTime endTime = getMaxDateTime(end);

        Interval interval = new Interval(startTime, endTime);
        return interval.contains(new DateTime());
    }

    /**
     * <p>是否包含在区间范围内（yyyy-MM-dd HH:mm:ss）</p>
     *
     * @param start 开始日期 yyyy-MM-dd HH:mm:ss
     * @param end   结束日期 yyyy-MM-dd HH:mm:ss
     * @return true /false
     */
    public static boolean intervalDateTime(String start, String end) {
        DateTime startTime = strToDateTime(start, DT_STANDARD_DATETIME);
        DateTime endTime = strToDateTime(end, DT_STANDARD_DATETIME);

        Interval interval = new Interval(startTime, endTime);
        return interval.contains(new DateTime());
    }

    /**
     * <p>获取指定日期开始时间(yyyy-MM-dd 00:00:00)</p>
     * <pre>
     *     getMinDateTime("2017-01-01") = "2017-01-01 00:00:00"
     * </pre>
     *
     * @param date (yyyy-MM-dd)
     * @return yyyy -MM-dd 00:00:00
     */
    public static DateTime getMinDateTime(String date) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        return fmt.parseDateTime(date).withHourOfDay(00).withMinuteOfHour(00).withSecondOfMinute(00);
    }

    /**
     * <p>获取指定日期开始时间(yyyy-MM-dd 23:59:59)</p>
     * <pre>
     *     getMaxDateTime("2017-01-01") = "2017-01-01 23:59:59"
     * </pre>
     *
     * @param date (yyyy-MM-dd)
     * @return endTime max date time
     */
    public static DateTime getMaxDateTime(String date) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        return fmt.parseDateTime(date).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
    }


    /**
     * <p>是否包含在指定的区间范围内</p>
     *
     * @param start 开始时间 HH:mm:ss
     * @param end   结束时间 HH:mm:ss
     * @return true /false
     */
    public static boolean intervalTime(String start, String end) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String dateStart = getCurrDate() + " " + start;
        String dateEnd = getCurrDate() + " " + end;
        DateTime startTime = fmt.parseDateTime(dateStart);
        DateTime endTime = fmt.parseDateTime(dateEnd);

        Interval interval = new Interval(startTime, endTime);
        return interval.contains(new DateTime());
    }

    /**
     * 字符串转换为DateTime
     *
     * @param dateTime 日期
     * @param format   格式化
     * @return dateTime date time
     */
    public static DateTime strToDateTime(String dateTime, String format) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
        return fmt.parseDateTime(dateTime);
    }

    /**
     * 参数日期是否在开当前日期之后
     *
     * @param date 日期【yyyy-MM-dd HH:mm:ss】
     * @return true /false
     */
    public static boolean isAfterNow(String date) {
        DateTime dateTime = strToDateTime(date, DT_STANDARD_DATETIME);
        return dateTime.isAfterNow();
    }

    /**
     * 参数日期是否在开当前日期之后
     *
     * @param date 日期【yyyy-MM-dd HH:mm:ss】
     * @return true /false
     */
    public static boolean isBeforeNow(String date) {
        DateTime dateTime = strToDateTime(date, DT_STANDARD_DATETIME);
        return dateTime.isBeforeNow();
    }

    /**
     * Is before today boolean
     *
     * @param date date
     * @return the boolean
     */
    public static boolean isBeforeToday(String date) {
        DateTime dateTime = strToDateTime(date, DT_STANDARD_DATE);
        return dateTime.isBeforeNow();
    }

    /**
     * Is after today boolean
     *
     * @param date date
     * @return the boolean
     */
    public static boolean isAfterToday(String date) {
        DateTime dateTime = strToDateTime(date, DT_STANDARD_DATE);
        return dateTime.isAfterNow();
    }


    /**
     * 当前时间是否包含在区间
     *
     * @param startTime 开始时间
     * @param minute    偏移分钟数
     * @return true /false
     */
    public static boolean isOverTime(Date startTime, int minute) {
        DateTime start = new DateTime(startTime);
        DateTime end = start.plusMinutes(30);
        Interval interval = new Interval(start, end);
        return interval.containsNow();
    }


    /**
     * 当前时间是否包含在区间
     *
     * @param startTime 开始时间
     * @param minute    偏移分钟数
     * @return true /false
     */
    public static boolean isOverTime(String startTime, int minute) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DT_STANDARD_DATETIME);
        DateTime start = dateTimeFormatter.parseDateTime(startTime);
        DateTime end = start.plusMinutes(minute);
        Interval interval = new Interval(start, end);
        return interval.containsNow();
    }


    /**
     * 获取在指定日期范围内的所有weekDay所在的日期
     *
     * @param startDate 开始日期 yyyy-MM-dd
     * @param endDate   结束日期 yyyy-MM-dd
     * @param weekDay   星期
     * @return 日期集合 duration week days
     */
    public static Map<String, Object> getDurationWeekDays(String startDate, String endDate, int weekDay) {
        DateTime startD = getMinDateTime(startDate);
        DateTime endD = getMaxDateTime(endDate);

        Map<String, Object> dateMap = new HashMap(16);

        return getDurationWeekDay(dateMap, startD, endD, weekDay);
    }

    /**
     * 获取在指定日期范围内的所有weekDay所在的日期
     *
     * @param dateMap   集合
     * @param startDate 开始日期 yyyy-MM-dd
     * @param endDate   结束日期 yyyy-MM-dd
     * @param weekdate  星期
     * @return 日期集合
     */
    private static Map<String, Object> getDurationWeekDay(Map<String, Object> dateMap, DateTime startDate, DateTime endDate, int weekdate) {
        if (startDate.isAfter(endDate)) {
            return dateMap;
        }
        DateTime tomorrowD = startDate;
        if (startDate.getDayOfWeek() == weekdate) {
            dateMap.put(tomorrowD.toString("yyyy-MM-dd"), "");
        }
        tomorrowD = startDate.plusDays(1);
        dateMap = getDurationWeekDay(dateMap, tomorrowD, endDate, weekdate);
        return dateMap;
    }

    /**
     * 获取最小的时间
     *
     * @param text   日期
     * @param format 格式
     * @return 日期 00:00:00
     */
    public static String getMinDateTime(String text, String format) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
        DateTime startTime = fmt.parseDateTime(text).withHourOfDay(00).withMinuteOfHour(00).withSecondOfMinute(00);
        return startTime.toString("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取最大的时间
     *
     * @param text   日期
     * @param format 格式
     * @return 日期 23:59:59
     */
    public static String getMaxDateTime(String text, String format) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
        DateTime startTime = fmt.parseDateTime(text).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        return startTime.toString("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 当前时间向后延期几个月
     *
     * @param months 月份
     * @return 偏移后日期 string
     */
    public static String plusMonth(int months) {
        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusMonths(months).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        return dateTime.toString("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 当前时间向后延期几天
     *
     * @param days 天数
     * @return 偏移后日期 string
     */
    public static String plusDay(int days) {
        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusDays(days).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        return dateTime.toString("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 当前时间向后延期几年
     *
     * @param years 天数
     * @return 偏移后日期 string
     */
    public static String plusYear(int years) {
        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusYears(years).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        return dateTime.toString("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * * 指定日期向后延期几年
     *
     * @param startDate startDate
     * @param years     i
     * @return startDate string
     */
    public static String plusYear(String startDate, int years) {
        DateTime dateTime = DateTime.parse(startDate).plusYears(years);
        return dateTime.toString("yyyy-MM-dd");
    }

    /**
     * Plus minutes string
     *
     * @param minutes minutes
     * @return the string
     */
    public static String plusMinutes(int minutes) {
        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusMinutes(minutes);
        return dateTime.toString("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 判断给定的时间与参数时间相差的分钟数
     *
     * @param paramDate 参数日期（yyyy—MM-dd）
     * @param format    参数格式 （yyyyMMddHHmmss或者yyyy-MM-dd HH:mm:ss）
     * @return 分钟数 int
     */
    public static int diffMinutesNow(String paramDate, String format) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
        DateTime paramDateTime = fmt.parseDateTime(paramDate);
        DateTime currDateTime = new DateTime();
        return Minutes.minutesBetween(currDateTime, paramDateTime).getMinutes();
    }

    /**
     * 当前日期偏移
     *
     * @param days   偏移量
     * @param format 日期格式
     * @return 偏移后日期 string
     */
    public static String addCurrDate(int days, String format) {
        LocalDate localDate = new LocalDate();
        return localDate.plusDays(days).toString(format);
    }

    /**
     * 根据日期获取星期几
     *
     * @param date 日期（yyyy-MM-dd）
     * @return 星期 week of date
     */
    public static int getWeekOfDate(String date) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(DT_STANDARD_DATE);
        DateTime dateTime = fmt.parseDateTime(date);
        return dateTime.getDayOfWeek();
    }

    /**
     * 获取今天本月的几号
     *
     * @return 日期 day of month
     */
    public static int getDayOfMonth() {
        LocalDate localDate = new LocalDate();
        return localDate.getDayOfMonth();
    }

    /**
     * 获取今天星期几
     *
     * @return 7 :星期日 1:星期一 2:星期二 3:星期三
     */
    public static int getDayOfWeek() {
        LocalDate localDate = new LocalDate();
        return localDate.getDayOfWeek();
    }

    /**
     * 获得今天星期几
     *
     * @return 1 :星期日 2:星期一 3:星期二 4:星期三
     */
    public static String getCurrWeek() {
        Calendar cal = Calendar.getInstance();
        return String.valueOf(cal.get(Calendar.DAY_OF_WEEK));
    }

    /**
     * Gets month of year *
     *
     * @return the month of year
     */
    public static int getMonthOfYear() {
        LocalDate localDate = new LocalDate();
        return localDate.getMonthOfYear();
    }

    /**
     * 对日期进行格式化
     *
     * @param date 日期
     * @param sf   日期格式
     * @return 字符串 string
     */
    public static String formatDate(Date date, String sf) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(sf);
        return dateFormat.format(date);
    }

    /**
     * 时间戳转字符串
     *
     * @param v  时间戳
     * @param sf 格式化类型
     * @return 时间 string
     */
    public static String format(long v, String sf) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(sf);
        return dateFormat.format(v);
    }

    /**
     * 时间戳转字符串
     *
     * @param v 时间戳
     * @return 时间 string
     */
    public static String format(long v) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DT_STANDARD_DATETIME);
        return dateFormat.format(v);
    }

    /**
     * 字符串转时间戳
     *
     * @param v 字符串
     * @return 时间 long
     * @throws ParseException parse exception
     */
    public static long parse(String v) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DT_STANDARD_DATETIME);
        return dateFormat.parse(v).getTime();
    }

    /**
     * 字符串转时间戳
     *
     * @param v  日期
     * @param sf 日期格式
     * @return 字符串 long
     * @throws ParseException parse exception
     */
    public static long parse(String v, String sf) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DT_STANDARD_DATETIME);
        return dateFormat.parse(v).getTime();
    }

    /**
     * 字符串转换为日期
     *
     * @param dateString 日期格式字符串
     * @param sf         日期格式化定义
     * @return 转换后的日期 date
     */
    public static Date stringToDate(String dateString, String sf) {
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat sdf = new SimpleDateFormat(sf);
        Date dt = sdf.parse(dateString, pos);
        return dt;
    }

    /**
     * 月份偏移
     *
     * @param startDate 开始日期
     * @param i         偏移量
     * @return yyyy -MM-dd HH:mm:ss
     */
    public static String plusMonths(String startDate, int i) {
        DateTime date = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).plusMonths(i)
                .withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        return date.toString("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期偏移
     *
     * @param startDate 开始日期
     * @param i         偏移量
     * @return yyyy -MM-dd HH:mm:ss
     */
    public static String plusDays(String startDate, int i) {
        DateTime date = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).plusDays(i)
                .withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        return date.toString("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期偏移
     *
     * @param i 偏移量
     * @return yyyy -MM-dd HH:mm:ss
     */
    public static String plusDays(int i) {
        DateTime date = DateTime.now().plusDays(i)
                .withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        return date.toString("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 解析开始日期
     *
     * @param startDate 开始日期 yyyy-MM-dd
     * @return yyyy -MM-dd HH:mm:ss
     */
    public static String parseStartDate(String startDate) {
        DateTime date = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyy-MM-dd"));
        return date.toString("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 解析结束日期
     *
     * @param endDate 结束日期 yyyy-MM-dd
     * @return yyyy -MM-dd HH:mm:ss
     */
    public static String parseEndDate(String endDate) {
        DateTime date = DateTime.parse(endDate, DateTimeFormat.forPattern("yyyy-MM-dd")).withHourOfDay(23)
                .withMinuteOfHour(59).withSecondOfMinute(59);
        return date.toString("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * <p>获取当前日期</p>
     *
     * @return 当前日期 （dd）
     */
    public static String getCurrDateD() {
        return LocalDate.now().toString(DT_SHORT_D);
    }

    /**
     * yyyyMMdd 形式转为 yyyy-MM-dd
     *
     * @param date date
     * @return string string
     */
    public static String strToStrDate(String date) {
        DateTimeFormatter sdf = DateTimeFormat.forPattern("yyyyMMdd");
        DateTime newDate = sdf.parseDateTime(date);
        return newDate.toString("yyyy-MM-dd");
    }

    /**
     * 获取日期偏移量
     *
     * @param days 日期
     * @return string string
     */
    public static String plusDayOfNow(int days) {
        LocalDate localDate = new LocalDate();
        LocalDate now = localDate.plusDays(days);
        return now.toString("yyyyMMdd");
    }

    /**
     * HHmmss 形式转为 HH:mm:ss
     *
     * @param time time
     * @return string string
     */
    public static String strToStrTime(String time) {
        DateTimeFormatter sdf = DateTimeFormat.forPattern("HHmmss");
        DateTime newDate = sdf.parseDateTime(time);
        return newDate.toString("HH:mm:ss");
    }




    public static String getLastDayOfMonth(int month){
        DateTime dateTime = new DateTime();
        String date = dateTime.plusMonths(month).dayOfMonth().withMaximumValue().toString();
        return getMaxDateTime(date, "yyyy-MM-dd HH:mm:ss");
    }
    //=====================================================================================

    /**
     * 获取当前时间
     *
     * @return 格式 ： yyyy-MM-dd HH:mm:ss
     */
    public static String now() {
        DateTime dateTime = new DateTime();
        return dateTime.toString(DT_STANDARD_DATETIME);
    }

    /**
     * 获取当前时间
     *
     * @return 格式 ： yyyyMMddHHmmss
     */
    public static String shortNow() {
        DateTime dateTime = new DateTime();
        return dateTime.toString(DT_STANDARD_SHORT_DATETIME);
    }

    /**
     * Little now string
     *
     * @return the string
     */
    public static String littleNow() {
        DateTime dateTime = new DateTime();
        return dateTime.toString(DT_STANDARD_LITTLE_DATETIME);
    }

    /**
     * 获取当前时间
     *
     * @return 格式 ： yyyy-MM-dd
     */
    public static String today() {
        DateTime dateTime = new DateTime();
        return dateTime.toString(DT_STANDARD_DATE);
    }

    /**
     * 获取当前时间
     *
     * @return 格式 ： yyyyMMdd
     */
    public static String shortToday() {
        DateTime dateTime = new DateTime();
        return dateTime.toString(DT_STANDARD_SHORT_DATE);
    }

    /**
     * 获取当前时间
     *
     * @return 格式 ： yyyyMMdd
     */
    public static String shortToday(int d) {
        DateTime dateTime = new DateTime();
        return dateTime.plusDays(d).toString(DT_STANDARD_SHORT_DATE);
    }

    /**
     * 获取当前时间
     *
     * @return 格式 ： yyMMdd
     */
    public static String littleToday() {
        DateTime dateTime = new DateTime();
        return dateTime.toString(DT_STANDARD_LITTLE_DATE);
    }

    /**
     * Time string
     *
     * @return the HH:mm:ss
     */
    public static String time() {
        DateTime dateTime = new DateTime();
        return dateTime.toString(DT_STANDARD_TIME);
    }

    /**
     * Short time string
     *
     * @return the HHmmss
     */
    public static String shortTime() {
        DateTime dateTime = new DateTime();
        return dateTime.toString(DT_STANDARD_SHORT_TIME);
    }

    /**
     * Spend ms long
     *
     * @param preTime pre time
     * @return the long
     */
    public static long spendMs(long preTime) {
        return System.currentTimeMillis() - preTime;
    }

    //=====================================================================================

    /**
     * 对字符串类型日期进行格式化
     *
     * @param date 日期
     * @param pattern1   传参日期格式
     * @param pattern2   要转换的日期格式
     * @return 字符串 string
     */
    public static String formatDate(String date, String pattern1, String pattern2) {
        if (date == null) {
            return "";
        }
        DateTimeFormatter sdf = DateTimeFormat.forPattern(pattern1);
        DateTime newDate = sdf.parseDateTime(date);
        return newDate.toString(pattern2);
    }

    /**
     * Main
     *
     * @param args args
     */
    public static void main(String[] args) {
        System.out.println(plusDayOfNow(-1));
    }
}
