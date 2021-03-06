package cn.moo.trainingcollege.servicetest;

import cn.moo.trainingcollege.BaseTest;
import cn.moo.trainingcollege.dao.OrderDao;
import cn.moo.trainingcollege.service.OrderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by 小春 on 2017/2/21.
 */
public class OrderTest extends BaseTest {
    @Autowired
    OrderService orderService;

    @Autowired
    OrderDao orderDao;

    @Test
    public void addOrderTest(){
        orderService.orderCourse("S000002",1);
    }

    @Test
    public void payTest(){
        orderService.pay(2);
    }

    @Test
    public void scoreTest(){
        orderService.score(2,100);
    }

    @Test
    public void quitTest(){
        orderService.quitCourse(2);
    }

    @Test
    public void getListTest(){
        System.out.println(orderService.getStudentOrder("S000002").size());
    }

    @Test
    public void getOrderTest(){
        System.out.println(orderService.getOrder(3).getStudentId());
    }

    @Test
    public void getTopOrgan() {
        System.out.println(orderDao.getSiteTopOrgan());
    }
}
