package cn.moo.trainingcollege.service.impl;

import cn.moo.trainingcollege.dao.*;
import cn.moo.trainingcollege.dao.impl.SettlementDaoImpl;
import cn.moo.trainingcollege.service.QuotaService;
import cn.moo.trainingcollege.utils.MathUtil;
import cn.moo.trainingcollege.utils.StatTimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chenmuen on 2017/6/15.
 */
@Service
@Transactional
public class QuotaServiceImpl implements QuotaService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    StudentDao studentDao;
    @Autowired
    SettlementDao settlementDao;
    @Autowired
    PageviewDao pageviewDao;

    @Override
    public Map<String, Object> getSiteQuitRateRank(StatTimeType statTimeType, String organId) {
        return orderDao.getSiteQuitRank(statTimeType, organId);
    }

    @Override
    public Map<String, Object> getSiteSatisfactionRank(StatTimeType statTimeType, String organId) {
        return orderDao.getSiteSatisfactionRank(statTimeType, organId);
    }

    @Override
    public Map<String, Object> getSiteOrderRank(StatTimeType statTimeType, String organId) {
        return orderDao.getSiteOrderRank(statTimeType, organId);
    }

    @Override
    public Map<String, Object> getSitePriceRank(StatTimeType statTimeType, String organId) {
        return courseDao.getSitePriceRank(statTimeType, organId);
    }

    @Override
    public Map<String, Object> getSiteCourseIncomeRank(StatTimeType statTimeType, String organId) {
        return orderDao.getSiteIncomeRank(statTimeType, organId);
    }

    @Override
    public List<Double> getQuitRate(StatTimeType statTimeType, String organId) {
        return orderDao.getSiteQuitRate(statTimeType, organId);
    }

    @Override
    public List<Double> getSatisfactionRate(StatTimeType statTimeType, String organId) {
        return orderDao.getSiteSatisfactionRate(statTimeType, organId);
    }

    @Override
    public List<Double> getConsumptionConversionRate(StatTimeType statTimeType) {
        return studentDao.getConsumptionConversionRate(statTimeType);
    }

    @Override
    public List<Double> getOrderConversionRate(StatTimeType statTimeType) {
        List<Integer> pageViewList = pageviewDao.getPageViewCount(statTimeType);
        List<Integer> orderCountList = orderDao.getOrderCountQuota(statTimeType);

        List<Double> resultList = new ArrayList<>();
        for (int i = 0; i < pageViewList.size(); i++) {
            if(pageViewList.get(i)!=0) {
                resultList.add(MathUtil.getDoubleWithRound((double)orderCountList.get(i)/(double)pageViewList.get(i)));
            } else {
                resultList.add(0.0);
            }
        }

        return resultList;
    }

    @Override
    public List<Double> getSiteIncomeYearToYearRate() {
        return settlementDao.getSiteIncomeYearToYearRate();
    }

    @Override
    public List<Double> getSiteMemberYearToYearRate() {
        return studentDao.getSiteMemberYearToYearRate();
    }

    @Override
    public Map<String, Object> getTeacherIncomeRank(StatTimeType statTimeType, String organId) {
        return orderDao.getTeacherIncomeRank(statTimeType, organId);
    }

    @Override
    public List<Integer> getMemberQuota(StatTimeType statTimeType, String organId) {
        return courseDao.getMemberQuota(statTimeType, organId);
    }

    @Override
    public List<Double> getIncomeQuota(StatTimeType statTimeType, String organId) {
        return orderDao.getIncomeQuota(statTimeType, organId);
    }
}
