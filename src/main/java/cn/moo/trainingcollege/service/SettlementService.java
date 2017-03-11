package cn.moo.trainingcollege.service;

/**
 * Created by chenmuen on 2017/3/8.
 */
public interface SettlementService {
    /**
     * 结算已结束课程
     * @param courseId
     */
    public void settlement(int courseId);

}