package cn.moo.trainingcollege.utils;

/**
 * Created by 小春 on 2017/6/19.
 */
public class CounterUtil {
    private static int access_num = 0;

    public static void add(){
        access_num++;
    }

    public static void returnZero(){
        access_num = 0;
    }

    public static int getNum(){
        return access_num;
    }

}
