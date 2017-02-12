package cn.moo.trainingcollege.service;

/**
 * Created by chenmuen on 2017/2/11.
 */
public interface OrderService {
    public boolean quitCourse(int orderId);

    public boolean cancelCourse(int orderId);

    public boolean orderCourse(String studentId, int courseId);

    public boolean pay(int orderId);

    public boolean approveApplication(String orderId);

    public boolean updateApplication(String orderId);
}
