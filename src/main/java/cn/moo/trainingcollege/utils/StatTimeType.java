package cn.moo.trainingcollege.utils;

/**
 * Created by chenmuen on 2017/6/14.
 */
public enum StatTimeType {
    YEAR,   //按年统计，近3年
    MONTH,  //按月统计，近12个月
    WEEK;    //按周统计，近4周

    public static StatTimeType getStatTimeType(String type) {
        switch (type) {
            case "year":
                return YEAR;
            case "month":
                return MONTH;
            case "week":
                return WEEK;
            default:
                return YEAR;
        }
    }
}
