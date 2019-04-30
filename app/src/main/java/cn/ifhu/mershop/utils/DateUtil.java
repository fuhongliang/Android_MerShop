package cn.ifhu.mershop.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author tommy
 * @date 18/1/2
 */

public class DateUtil {
    public static final String YEAR_MONTH_FORMAT = "yyyy-MM";
    public static final String YEAR_MONTH_DAY_FORMAT = "yyyy-MM-dd";
    public static final String YEAR_MONTH_DAY_MINUTE = "yyyy-MM-dd hh:mm";

    public static String getCurDateMonth() {
        DateTime dateTime = new DateTime(new Date());
        return dateTime.toString(YEAR_MONTH_FORMAT);
    }

    public static String getCurDateMonth(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(YEAR_MONTH_FORMAT);
    }

    public static String getDateToString(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(YEAR_MONTH_DAY_MINUTE);
    }

    public static String getLongToString(Long date) {
        DateTime dateTime = new DateTime(date * 1000L);
        return dateTime.toString(YEAR_MONTH_DAY_FORMAT);
    }


    public static boolean isRecentDay(long time) {
        Calendar meetingTime = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
        meetingTime.setTimeInMillis(time);
        Calendar currentDay = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
        long currentTime = System.currentTimeMillis();
        currentDay.setTimeInMillis(currentTime);

        if (meetingTime.getTimeInMillis() < currentDay.getTimeInMillis()) {
            return true;
        } else {
            return false;
        }
    }


}
