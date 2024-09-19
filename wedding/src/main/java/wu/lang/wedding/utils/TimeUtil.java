package wu.lang.wedding.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author qq
 */
public class TimeUtil {

    /**
     * 1分钟的秒时间
     */
    public static final long ONE_MINUTE_IN_SECONDS = 60L;

    /**
     * 1小时的分钟时间
     */
    public static final long ONE_HOUR_IN_MINUTES = 60L;

    /**
     * 一分钟的毫秒时长
     */
    public static final long ONE_MINUTE_IN_MILLISECONDS = ONE_MINUTE_IN_SECONDS * 1000;

    /**
     * 一小时的毫秒时长
     */
    public static final long ONE_HOUR_IN_MILLISECONDS = 60L * ONE_MINUTE_IN_MILLISECONDS;
    /**
     * 一天的毫秒时长
     */
    public static final long ONE_DAY_IN_MILLISECONDS = 24L * ONE_HOUR_IN_MILLISECONDS;

    /**
     * 一天的分鐘时长
     */
    public static final int ONE_DAY_IN_MIN = (int) (24 * ONE_HOUR_IN_MINUTES);

    /**
     * 一天的秒时长
     */
    public static final long ONE_DAY_IN_SECONDS = ONE_DAY_IN_MIN * ONE_MINUTE_IN_SECONDS;

    /**
     * 1秒的时长
     */
    public static final long ONE_MILLS = 1000L;

    /**
     * 2015-02-23格式
     */
    public static final String DAY_FORMAT = "yyyy-MM-dd";

    /**
     * 2015-02-23 12点格式
     */
    public static final String HOUR_FORMAT = "yyyy-MM-dd HH点";

    /**
     * 2015-02-23 12:12:12格式
     */
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 判断两个时间是否是同一天
     *
     * @param sourceTime
     * @param targetTime
     * @return
     */
    public static boolean isSameDay(long sourceTime, long targetTime) {

        Instant instant1 = Instant.ofEpochMilli(sourceTime);
        LocalDateTime localDateTime1 = LocalDateTime.ofInstant(instant1, ZoneId.systemDefault());
        long day1 = localDateTime1.getLong(ChronoField.EPOCH_DAY);

        Instant instant2 = Instant.ofEpochMilli(targetTime);
        LocalDateTime localDateTime2 = LocalDateTime.ofInstant(instant2, ZoneId.systemDefault());
        long day2 = localDateTime2.getLong(ChronoField.EPOCH_DAY);

        return day1 == day2;

    }

    /**
     * 获取今天已经过去的分钟数
     *
     * @return
     */
    public static int getTodayOfMinute() {
        int nowOfMinutes = getNowOfMinutes();
        int zeroMinuteFromNow = dayZeroMinuteFromNow();
        return nowOfMinutes - zeroMinuteFromNow;
    }

    /**
     * 判断指定的时间是否是今天
     *
     * @param time 毫秒数
     * @return
     */
    public static boolean isToday(long time) {
        Instant instant = Instant.ofEpochMilli(time);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.toLocalDate().isEqual(LocalDate.now());
    }

    /**
     * 获取今天零点的分钟数
     */
    public static int dayZeroMinuteFromNow() {
        LocalDateTime localDateTime = LocalDate.now().atStartOfDay();
        return (int) (localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / 60000);
    }

    /**
     * 获取今天零点的秒数
     */
    public static int dayZeroSecondsFromNow() {
        return (int) (dayZeroMillsFromNow() / ONE_MILLS);
    }

    public static long dayZeroMillsFromNow() {
        LocalDateTime localDateTime = LocalDate.now().atStartOfDay();
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取time日零点毫秒时间
     *
     * @param time 时间 （毫秒）
     */
    public static long dayZeroMillsFromTime(long time) {

        Instant instant = Instant.ofEpochMilli(time);
        LocalDateTime dt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        return dt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取time日零点秒时间
     *
     * @param time 时间戳秒数
     */
    public static int dayZeroSecondsFromTime(int time) {
        return (int) (dayZeroMillsFromTime(time * 1000L) / 1000);
    }

    /**
     * 获取指定日期的时间戳
     *
     * @param year
     * @param month       从1开始
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @param milliSecond
     * @return
     */
    public static long getTimeInMillis(int year, int month, int day, int hour, int minute, int second,
                                       int milliSecond) {
        LocalDateTime ldt = LocalDateTime.of(year, month, day, hour, minute, second, milliSecond * 1000_000);
        return ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static long getNowOfMills() {
        return System.currentTimeMillis();
    }

    public static int getNowOfSeconds() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static int getNowOfMinutes() {
        return (int) (System.currentTimeMillis() / 1000 / 60);
    }

    /**
     * 今天星期几
     *
     * @return
     */
    public static int getNowWeek() {
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 昨天星期几
     */
    public static int getYesterDay() {
        int nowWeek = getNowWeek();
        switch (nowWeek) {
            case 1:
                return 7;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 4;
            case 6:
                return 5;
            case 7:
                return 6;
        }
        return 0;
    }

    /**
     * 一年中第几周
     */
    public static int getWeekOfYear(long time) {
        Instant instant = Instant.ofEpochMilli(time);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        return dateTime.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
    }

    /**
     * 获取距离time的自然天数
     *
     * @param time
     * @return
     */
    public static int getNaturalDayFromTime(long time) {

        Instant instant = Instant.ofEpochMilli(time);

        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        long day = ldt.getLong(ChronoField.EPOCH_DAY);

        long nowDay = LocalDate.now().getLong(ChronoField.EPOCH_DAY);

        return (int) (nowDay - day);

    }

    /**
     * 获取指定时间的自然天数
     *
     * @param time
     * @return
     */
    public static long getDay(long time) {

        Instant instant = Instant.ofEpochMilli(time);

        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        return ldt.getLong(ChronoField.EPOCH_DAY);
    }


    /**
     * 格式化时间
     *
     * @param time
     * @param format
     * @return
     */
    public static String timeFormat(long time, String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        return ldt.format(dtf);
    }

    public static String timeFormat(LocalDateTime time, String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return time.format(dtf);
    }

    /**
     * 解析时间
     *
     * @param text
     * @param format
     * @return
     */
    public static long timeParse(String text, String format) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);

        LocalDateTime ldt = LocalDateTime.parse(text, dtf);
        return ldt.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000;
    }

    /**
     * 将制定的毫秒转化成方便人识别的字符串
     * <p>有天显示天，有小时显示小时。。
     * 例如:1天20小时5分0秒,20小时0分0秒,1秒
     *
     * @param time
     * @return
     */
    public static String toHumanString(long time) {
        StringBuilder sb = new StringBuilder();
        // 获取剩余天数
        int day = (int) (time / ONE_DAY_IN_MILLISECONDS);
        // 1天及以上的显示剩余天
        if (day > 0) {
            sb.append(day).append("天");
//            time -= (day * ONE_DAY_IN_MILLISECONDS);
        } else {
            int hour = (int) (time / ONE_HOUR_IN_MILLISECONDS);
            // 1小时及以上或者前面显示了天数则后面需要小时
            if (hour > 0) {
                sb.append(hour).append("小时");
//                time -= (hour * ONE_HOUR_IN_MILLISECONDS);
            } else {
                int minute = (int) (time / ONE_MINUTE_IN_MILLISECONDS);
                if (minute > 0) {
                    sb.append(minute).append("分");
//                    time -= (minute * ONE_MINUTE_IN_MILLISECONDS);
                } else {
                    sb.append(time / 1000).append("秒");
                }
            }
        }

        return sb.toString();
    }

    /**
     * 判断今天是否为同一个月
     *
     * @return boolean
     */
    public static boolean isSameMonth(long oldTime) {
        LocalDate now = LocalDate.now();
        Instant instant = Instant.ofEpochMilli(oldTime);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        return now.getYear() == dateTime.getYear() && now.getMonthValue() == dateTime.getMonthValue();
    }


    /**
     * 判断今天是否为某年的同一周
     *
     * @return boolean
     */
    public static boolean isSameWeek(long oldTime) {
        LocalDateTime nowTime = LocalDateTime.now();
        Instant instant = Instant.ofEpochMilli(oldTime);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        int nowIndex = nowTime.getDayOfWeek().getValue();
        int oldIndex = dateTime.getDayOfWeek().getValue();
        if (nowTime.getYear() != dateTime.getYear() && oldIndex == nowIndex) {
            return false;
        }

        nowTime = nowTime.plusDays(7 - nowIndex).withHour(0).withMinute(0).withSecond(0).withNano(0);
        dateTime = dateTime.plusDays(7 - oldIndex).withHour(0).withMinute(0).withSecond(0).withNano(0);

        return dateTime.compareTo(nowTime) == 0;
    }

    /**
     * 验证日期字符串是否是YYYY-MM-dd格式或者YYYYMMdd
     *
     * @param str str
     * @return boolean
     */
    public static boolean checkDateFormat(String str) {
        boolean flag = false;
        String regex = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        Pattern pattern1 = Pattern.compile(regex);
        Matcher isNo = pattern1.matcher(str);
        if (isNo.matches()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 获取当前时间的小时数
     *
     * @return
     */
    public static int getNowHour() {
        return LocalDateTime.now().getHour();
    }

    /**
     * 获取当前时间分钟数
     *
     * @return
     */
    public static int getNowMinutes() {
        return LocalDateTime.now().getMinute();
    }

    /**
     * 获取每天0点开始，每过2个小时的时间戳
     *
     * @param point 间隔小时，例如:间隔2小时，就传2，单位：小时
     * @return
     */
    public static long getNowEvenTime(int point) {


        if (point <= 0) {
            return 0;
        }
        int hourTest = getNowHour();
        int mo = hourTest / point;
        int hour = (mo + 1) * point;
        int dayAdd = 0;
        if (hour >= 24) { //跨天
            hour = 0;
            //TODO  可能增加几天
            dayAdd = 1;
        }

        LocalDateTime localDateTime = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), hour, 0, 0);
        if (dayAdd > 0) {
            localDateTime = localDateTime.plusDays(dayAdd);
        }
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * 检测是否与当前为同一周
     *
     * @param time
     * @return
     */
    public static boolean getThisWeekMonday(long time) {
        //设置日期格式
        Calendar cld = Calendar.getInstance(Locale.CHINA);
        //以周一为首日
        cld.setFirstDayOfWeek(Calendar.MONDAY);
        //当前时间
        cld.setTimeInMillis(System.currentTimeMillis());
        //本周一
        cld.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cld.set(Calendar.HOUR_OF_DAY, 0);
        cld.set(Calendar.MINUTE, 0);
        cld.set(Calendar.SECOND, 0);
        cld.set(Calendar.MILLISECOND, 0);
        long startTime = cld.getTimeInMillis();
        //下周一
        cld.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cld.add(Calendar.DATE, 1);
        long endTime = cld.getTimeInMillis();
        if (time < startTime || time >= endTime) {
            return false;
        }
        return true;
    }

    public static long getNextWeekDayTime(int week) {
        Calendar cld = Calendar.getInstance(Locale.CHINA);
        cld.setFirstDayOfWeek(week);
        //下周一
        cld.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cld.add(Calendar.DATE, 1);
        cld.set(Calendar.HOUR_OF_DAY, 0);
        cld.set(Calendar.MINUTE, 0);
        cld.set(Calendar.SECOND, 0);
        cld.set(Calendar.MILLISECOND, 0);
        return cld.getTimeInMillis();
    }


    public static LocalDateTime getDateTimeOfMillis(long millis) {
        Instant instant = Instant.ofEpochMilli(millis);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔天数
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int betweenDay(long time1, long time2) {
        return (int) (Math.abs(time1 - time2) / ONE_DAY_IN_MILLISECONDS);
    }

    /**
     * 获取指定时间的零点时间
     *
     * @param time
     * @return
     */
    public static long getZeroClockTime(long time) {
        return getTimeInMillis(time, 0, 0, 0, 0);
    }

    /**
     * 获取在指定时间戳和指定小时，分钟，秒，毫秒数的时间
     *
     * @param time        时间戳
     * @param hour        小时(24小时制)
     * @param minute      分钟
     * @param second      秒
     * @param milliSecond 毫秒
     * @return
     */
    public static long getTimeInMillis(long time, int hour, int minute, int second, int milliSecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, milliSecond);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取两个时间的逻辑间隔天数,以源时间为基准,目标时间小于源时间则返回大于或等于天数，反之返回小于等于天数
     * <p>
     * 举例：sourceTime=今天凌晨0点0分1秒,targetTime=昨天晚上11点59分59秒,则返回1
     *
     * @param sourceTime
     * @param targetTime
     * @return
     */
    public static int getLogicIntervalDays(long sourceTime, long targetTime) {
        long source0ClockTime = getZeroClockTime(sourceTime);
        long target0ClockTime = getZeroClockTime(targetTime);

        return getRealIntervalDays(source0ClockTime, target0ClockTime);
    }

    /**
     * 获取两个时间的实际间隔天数
     *
     * @param sourceTime
     * @param targetTime
     * @return
     */
    public static int getRealIntervalDays(long sourceTime, long targetTime) {
        return (int) getIntervalTime(sourceTime, targetTime, ONE_DAY_IN_MILLISECONDS);
    }

    /**
     * 根据指定的时间单位获取相差的单位时间，如时间单位为一天的毫秒数则该函数跟{@link#getRealIntervalDays} 则是相同的效果
     *
     * @param sourceTime
     * @param targetTime
     * @param timeUnit   时间单位(毫秒)
     * @return
     */
    public static long getIntervalTime(long sourceTime, long targetTime, long timeUnit) {
        return (sourceTime - targetTime) / timeUnit;
    }

    /**
     * 本月首日0时时间戳
     */
    public static long getTimesMonthFirstDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTimeInMillis() / 1000;
    }

    /**
     * 距离指定时间的小时数
     */
    public static long getHourTime(long sourceTime, long targetTime) {
        long time = sourceTime - targetTime;
        int count = time % TimeUtil.ONE_HOUR_IN_MILLISECONDS == 0 ? 0 : 1;
        return time / TimeUtil.ONE_HOUR_IN_MILLISECONDS + count;
    }

    /**
     * 获取本月的最后一周周日时间
     */
    public static long getMonthLastDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int day = a.get(Calendar.DATE);
        Calendar cal = Calendar.getInstance();
        Calendar calendar = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
        for (int i = day; i > 0; i--) {
            calendar.set(Calendar.DATE, i);
            int week = calendar.get(Calendar.DAY_OF_WEEK);
            if (week == 1) {
                return calendar.getTimeInMillis();
            }
        }
        return 0;
    }

    /**
     * 月末时间戳
     *
     * @return
     */
    public static long realGetMonthLastDayTimestamp() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        cal.roll(Calendar.DATE, -1);
        long time = cal.getTimeInMillis();
//        System.out.println("月末时间戳," + TimeUtil.timeFormat(time, TimeUtil.DEFAULT_FORMAT));
        return time;
    }

    public static int getWeekDayByTime(long time) {
        //设置日期格式
        Calendar cld = Calendar.getInstance();
        Calendar cld1 = Calendar.getInstance();
        //当前时间
        cld.setTimeInMillis(time);
        return cld.get(Calendar.DAY_OF_WEEK);
    }

    public static void main(String[] args) {
//        long time = TimeUtil.dayZeroMillsFromNow();
//        System.out.println(time + "\n" + TimeUtil.timeFormat(time, TimeUtil.DEFAULT_FORMAT));
        TimeUtil.getNextWeekDayTime(Calendar.MONDAY);
    }

}
