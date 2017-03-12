package cn.moo.trainingcollege.servicetest;

import cn.moo.trainingcollege.BaseTest;
import cn.moo.trainingcollege.dao.OrderDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by chenmuen on 2017/3/11.
 */
public class SettlementServiceTest extends BaseTest {
    @Autowired
    OrderDao orderDao;

    @Test
    public void testGetNormalIncome() {
        System.out.println(orderDao.getCourseNormalIncome(4));
    }
}
