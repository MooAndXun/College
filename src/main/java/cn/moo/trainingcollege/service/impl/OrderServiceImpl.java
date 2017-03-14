package cn.moo.trainingcollege.service.impl;

import cn.moo.trainingcollege.dao.*;
import cn.moo.trainingcollege.entity.*;
import cn.moo.trainingcollege.service.OrderService;
import cn.moo.trainingcollege.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by chenmuen on 2017/2/21.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderDao orderDao;
    @Autowired
    OrderCashDao orderCashDao;
    @Autowired
    StudentDao studentDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    BalanceDao balanceDao;

    /**
     * 退课退50%的费用
     * @param orderId
     */
    @Override
    public void quitCourse(int orderId) {
        OrderAccountEntity order = orderDao.getByColumn("id",orderId);
        StudentEntity studentEntity = studentDao.getById(order.getStudentId());

        double settlement = order.getPrice()*0.5;
        BalanceEntity balanceEntity = balanceDao.getByColumn("id",1);
        balanceEntity.setBalance(balanceEntity.getBalance() - settlement);
        studentEntity.setBalance(studentEntity.getBalance() + settlement);

        order.setQuitState(1);
        balanceDao.update(balanceEntity);
        orderDao.update(order);

    }

    @Override
    public void quitCourseCash(int orderId) {
        OrderCashEntity orderCashEntity = orderCashDao.getByColumn("id",orderId);
        orderCashEntity.setQuitState(true);
        orderCashDao.update(orderCashEntity);
    }

    /**
     * 取消（未支付）
     * @param orderId
     */
    @Override
    public void cancelCourse(int orderId) {
        OrderAccountEntity order = orderDao.getByColumn("id",orderId);
        order.setCancel(true);
        orderDao.update(order);
    }

    @Override
    public void orderCourse(String studentId, int courseId) {
        StudentEntity student = studentDao.getById(studentId);
        CourseEntity course = courseDao.getByColumn("id",courseId);
        OrderAccountEntity order = new OrderAccountEntity();
        order.setStudentId(studentId);
        order.setCourseId(courseId);
        order.setScore(-1);
        order.setPrice(getPrice(student.getLevel(),course.getPrice()));
        order.setCreatedAt(TimeUtil.getCurrentTime());
        orderDao.add(order);
    }

    @Override
    public void orderCourseCash(String studentName, int courseId) {
        CourseEntity course = courseDao.getByColumn("id",courseId);
        OrderCashEntity orderCash = new OrderCashEntity();
        orderCash.setPrice(course.getPrice());
        orderCash.setCreatedAt(TimeUtil.getCurrentTime());
        orderCash.setCourseId(courseId);
        orderCash.setStudentName(studentName);
        orderCash.setScore(-1);
        orderCashDao.add(orderCash);
    }

    @Override
    public void pay(int orderId) {
        OrderAccountEntity orderAccountEntity = orderDao.getByColumn("id",orderId);
        StudentEntity studentEntity = studentDao.getById(orderAccountEntity.getStudentId());
        orderAccountEntity.setPaid(true);
        double price = orderAccountEntity.getPrice();
        BalanceEntity balanceEntity = balanceDao.getByColumn("id",1);
        balanceEntity.setBalance(balanceEntity.getBalance()+price);
        studentEntity.setBalance(studentEntity.getBalance()-price);
        orderDao.update(orderAccountEntity);
        balanceDao.update(balanceEntity);
        studentDao.update(studentEntity);
    }

    @Override
    public void score(int orderId, int score) {
        OrderAccountEntity order = orderDao.getByColumn("id",orderId);
        order.setScore(score);
        orderDao.update(order);
    }

    @Override
    public void scoreCash(int orderId, int score) {
        OrderCashEntity order = orderCashDao.getByColumn("id",orderId);
        order.setScore(score);
        orderCashDao.update(order);
    }

    @Override
    public List<OrderAccountEntity> getStudentOrder(String studentId) {
        return (List<OrderAccountEntity>)orderDao.getListByColumn("studentId",studentId);
    }

    @Override
    public OrderAccountEntity getOrder(int orderId) {
        return orderDao.getByColumn("id",orderId);
    }

    @Override
    public OrderAccountEntity getOrder(String studentId, int courseId) {
        return orderDao.getByStudentandCourse(studentId,courseId);
    }

    @Override
    public OrderAccountEntity getQuitOrCancelOrder(String studentId, int courseId) {
        return orderDao.getOverOrder(studentId,courseId);
    }

    @Override
    public List<OrderCashEntity> getOrderCashEntityList(int courseId) {
        return orderCashDao.getListByColumn("courseId", courseId);
    }

    private double getPrice(int level, double price){
        switch (level){
            case 1: return price;
            case 2: return price*0.95;
            case 3: return price*0.85;
            case 4: return price*0.75;
            default: return price;
        }
    }
}
