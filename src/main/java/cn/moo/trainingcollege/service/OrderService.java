package cn.moo.trainingcollege.service;

import org.springframework.stereotype.Service;

/**
 * Created by chenmuen on 2017/2/11.
 */
@Service
public interface OrderService {
    /**
     * 取消（未支付）
     * @param orderId
     */
    public void quitCourse(int orderId);

    /**
     * 退课退50%的费用
     * @param orderId
     */
    public void cancelCourse(int orderId);

    public void orderCourse(String studentId, int courseId);

    public void pay(int orderId);

    public void score(String studentId, int courseId, int score);
}
