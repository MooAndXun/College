package cn.moo.trainingcollege.servicetest;

import cn.moo.trainingcollege.BaseTest;
import cn.moo.trainingcollege.service.QuotaService;
import cn.moo.trainingcollege.utils.StatTimeType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by chenmuen on 2017/6/20.
 */
public class QuotaTest extends BaseTest {
    @Autowired
    QuotaService quotaService;

    @Test
    public void testRank() {
        System.out.println(quotaService.getSitePriceRank(StatTimeType.YEAR));
    }

    @Test
    public void testRate() {
        System.out.println(quotaService.getSatisfactionRate(StatTimeType.MONTH));
        System.out.println(quotaService.getQuitRate(StatTimeType.MONTH));
    }
}
