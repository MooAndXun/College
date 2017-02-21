package cn.moo.trainingcollege.service;

import org.springframework.stereotype.Service;

/**
 * Created by chenmuen on 2017/2/11.
 */
@Service
public interface OrderService {
    /**
     * 退课退50%的费用
     * @param orderId
     */
    public void quitCourse(int orderId);

    public void quitCourseCash(int orderId);

    public void cancelCourse(int orderId);

    public void orderCourse(String studentId, int courseId);

    public void orderCourseCash(String studentName, int courseId);

    public void pay(int orderId);

    public void score(String studentId, int courseId, int score);

    public void scoreCash(String studentName, int courseId, int score);
}
