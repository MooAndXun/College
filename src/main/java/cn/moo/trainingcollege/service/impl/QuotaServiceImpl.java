package cn.moo.trainingcollege.service.impl;

import cn.moo.trainingcollege.dao.CourseDao;
import cn.moo.trainingcollege.dao.OrderDao;
import cn.moo.trainingcollege.service.OrderService;
import cn.moo.trainingcollege.service.QuotaService;
import cn.moo.trainingcollege.utils.StatTimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by chenmuen on 2017/6/15.
 */
@Service
@Transactional
public class QuotaServiceImpl implements QuotaService {
    private final OrderDao orderDao;
    private final CourseDao courseDao;

    @Autowired
    public QuotaServiceImpl(CourseDao courseDao, OrderDao orderDao) {
        this.courseDao = courseDao;
        this.orderDao = orderDao;
    }

    @Override
    public Map<String, Object> getSiteQuitRateRank() {
        //TODO
        return null;
    }

    @Override
    public Map<String, Object> getSiteSatisfactionRank() {
        //TODO
        return null;
    }

    @Override
    public Map<String, Object> getSiteOrderRank(StatTimeType statTimeType) {
        return orderDao.getSiteOrderRank(statTimeType);
    }

    @Override
    public Map<String, Object> getSitePriceRank(StatTimeType statTimeType) {
        return courseDao.getSitePriceRank(statTimeType);
    }

    @Override
    public Map<String, Object> getSiteCourseIncomeRank(StatTimeType statTimeType) {
        return orderDao.getSiteIncomeRank(statTimeType);
    }

    @Override
    public List<Double> getQuitRate(StatTimeType statTimeType) {
        return orderDao.getSiteQuitRate(statTimeType);
    }

    @Override
    public List<Double> getSatisfactionRate(StatTimeType statTimeType) {
        return orderDao.getSiteSatisfactionRate(statTimeType);
    }

    @Override
    public List<Double> getConsumptionConversionRate(StatTimeType statTimeType) {
        // TODO
        return null;
    }

    @Override
    public List<Double> getOrderConversionRate(StatTimeType statTimeType) {
        // TODO
        return null;
    }

    @Override
    public List<Double> getSiteIncomeYearToYearRate() {
        // TODO
        return null;
    }

    @Override
    public List<Double> getSiteMemberYearToYearRate() {
        // TODO
        return null;
    }
}
