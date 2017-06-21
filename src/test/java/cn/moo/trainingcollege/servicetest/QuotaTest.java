package cn.moo.trainingcollege.servicetest;

import cn.moo.trainingcollege.BaseTest;
import cn.moo.trainingcollege.service.QuotaService;
import cn.moo.trainingcollege.utils.StatTimeType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenmuen on 2017/6/20.
 */
public class QuotaTest extends BaseTest {
    @Autowired
    QuotaService quotaService;

    @Test
    public void testRank() {
        System.out.println(quotaService.getSitePriceRank(StatTimeType.YEAR, null));
    }

    @Test
    public void testRate() {
        System.out.println(quotaService.getSatisfactionRate(StatTimeType.MONTH, null));
        System.out.println(quotaService.getQuitRate(StatTimeType.YEAR, null));
    }

    @Test
    public void testMember() {
        System.out.println(quotaService.getMemberQuota(StatTimeType.MONTH, "O000001"));
    }

    @Test
    public void testIncome() {
        List memberList = quotaService.getMemberQuota(StatTimeType.getStatTimeType("month"), null);
        List incomeList = quotaService.getIncomeQuota(StatTimeType.getStatTimeType("month"), null);

        Map<String, Object> result = new HashMap<>();
        result.put("member", memberList);
        result.put("income", incomeList);

        System.out.println(result);
    }
}
