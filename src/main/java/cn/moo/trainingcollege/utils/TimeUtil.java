package cn.moo.trainingcollege.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by 小春 on 2017/2/20.
 */
public class TimeUtil {
    public static Timestamp getCurrentTime(){
        Calendar calendar = Calendar.getInstance();
        Timestamp currentTimestamp = new Timestamp(calendar.getTime().getTime());
        return currentTimestamp;
    }

    public static String timestampToDateString(Timestamp ts) {
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        tsStr = sdf.format(ts);
        return tsStr;
    }

    public static Timestamp dateStringToTimestamp(String dateStr) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        dateStr += " 00:00:00";
        try {
            ts = Timestamp.valueOf(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ts;
    }

    public static boolean isBeforeToday(String dateStr) {
        return dateStringToTimestamp(dateStr).before(getCurrentTime());
    }

    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static int getCurrentWeek() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
}
