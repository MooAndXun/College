package cn.moo.trainingcollege.service.impl;

import cn.moo.trainingcollege.service.QuotaService;
import cn.moo.trainingcollege.utils.StatTimeType;
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
    @Override
    public Map<String, Object> getSiteQuitRateRank() {
        return null;
    }

    @Override
    public Map<String, Object> getSiteSatisfactionRank() {
        return null;
    }

    @Override
    public Map<String, Object> getSiteOrderRank(StatTimeType statTimeType) {
        return null;
    }

    @Override
    public Map<String, Object> getSitePriceRank(StatTimeType statTimeType) {
        return null;
    }

    @Override
    public Map<String, Object> getSiteCourseIncomeRank(StatTimeType statTimeType) {
        return null;
    }

    @Override
    public List<Double> getQuitRate(StatTimeType statTimeType) {
        return null;
    }

    @Override
    public List<Double> getSatisfactionRate(StatTimeType statTimeType) {
        return null;
    }

    @Override
    public List<Double> getConsumptionConversionRate(StatTimeType statTimeType) {
        return null;
    }

    @Override
    public List<Double> getOrderConversionRate(StatTimeType statTimeType) {
        return null;
    }

    @Override
    public List<Double> getSiteIncomeYearToYearRate() {
        return null;
    }

    @Override
    public List<Double> getSiteMemberYearToYearRate() {
        return null;
    }
}
