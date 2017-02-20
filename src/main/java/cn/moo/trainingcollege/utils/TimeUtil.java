package cn.moo.trainingcollege.utils;

import java.sql.Timestamp;
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
}
