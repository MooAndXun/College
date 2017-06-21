package cn.moo.trainingcollege.daotest;

import cn.moo.trainingcollege.BaseTest;
import cn.moo.trainingcollege.dao.OrderDao;
import cn.moo.trainingcollege.utils.StatTimeType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by chenmuen on 2017/6/17.
 */
public class OrderDaoTest extends BaseTest {
    @Autowired
    OrderDao orderDao;

    @Test
    public void getSiteQuitRateTest() {
        System.out.println(orderDao.getSiteQuitRate(StatTimeType.YEAR, null));
        System.out.println(orderDao.getSiteQuitRate(StatTimeType.MONTH, null));
        System.out.println(orderDao.getSiteQuitRate(StatTimeType.WEEK, null));
    }

    @Test
    public void getTeacherRankTest() {
        System.out.println(orderDao.getTeacherIncomeRank(StatTimeType.YEAR, null));
    }

    @Test
    public void getRankTest() {
        System.out.println(orderDao.getSiteOrderRank(StatTimeType.YEAR, null));
        System.out.println(orderDao.getSiteIncomeRank(StatTimeType.YEAR, null));
    }
}
