package cn.moo.trainingcollege.service;

import cn.moo.trainingcollege.entity.OrderAccountEntity;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void score(int orderId, int score);

    public void scoreCash(int orderId, int score);

    public List<OrderAccountEntity> getStudentOrder(String studentId);

    public OrderAccountEntity getOrder(int orderId);

    public OrderAccountEntity getOrder(String studentId, int courseId);

    public OrderAccountEntity getQuitOrCancelOrder(String studentId, int courseId);
}
