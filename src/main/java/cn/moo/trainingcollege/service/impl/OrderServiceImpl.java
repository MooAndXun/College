package cn.moo.trainingcollege.service.impl;

import cn.moo.trainingcollege.dao.*;
import cn.moo.trainingcollege.entity.*;
import cn.moo.trainingcollege.service.OrderService;
import cn.moo.trainingcollege.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 小春 on 2017/2/21.
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
        double settlement = order.getPrice()*0.5;
        order.setDropped(true);
        BalanceEntity balanceEntity = balanceDao.getByColumn("id",1);
        balanceEntity.setBalance(balanceEntity.getBalance() - settlement);
        orderDao.update(order);
        balanceDao.update(balanceEntity);

    }

    @Override
    public void quitCourseCash(int orderId) {
        OrderCashEntity orderCashEntity = orderCashDao.getByColumn("id",orderId);
        orderCashEntity.setDropped(true);
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
        orderCashDao.add(orderCash);
    }

    @Override
    public void pay(int orderId) {
        OrderAccountEntity orderAccountEntity = orderDao.getByColumn("id",orderId);
        orderAccountEntity.setPaid(true);
        double price = orderAccountEntity.getPrice();
        BalanceEntity balanceEntity = balanceDao.getByColumn("id",1);
        balanceEntity.setBalance(balanceEntity.getBalance()+price);
        orderDao.update(orderAccountEntity);
        balanceDao.update(balanceEntity);
    }

    @Override
    public void score(String studentId, int courseId, int score) {
        OrderAccountEntity order = orderDao.getByStudentandCourse(studentId,courseId);
        order.setScore(score);
        orderDao.update(order);
    }

    @Override
    public void scoreCash(String studentName, int courseId, int score) {
        OrderCashEntity order = orderCashDao.getByStudentAndCourse(studentName,courseId);
        order.setScore(score);
        orderCashDao.update(order);
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
