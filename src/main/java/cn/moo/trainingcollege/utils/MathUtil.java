package cn.moo.trainingcollege.utils;

import java.math.BigDecimal;

/**
 * Created by chenmuen on 2017/6/21.
 */
public class MathUtil {
    public static double getDoubleWithRound(double originData) {
        BigDecimal b = new BigDecimal(originData);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
