package cn.moo.trainingcollege.service;

import cn.moo.trainingcollege.utils.StatTimeType;

import java.util.List;
import java.util.Map;

/**
 * Created by chenmuen on 2017/6/15.
 */
public interface QuotaService {
    /**
     *
     * @param statTimeType 时间层次类型
     * @return
     */
    public Map<String, Object> getSiteQuitRateRank(StatTimeType statTimeType);

    /**
     *
     * @param statTimeType 时间层次类型
     * @return
     */
    public Map<String, Object> getSiteSatisfactionRank(StatTimeType statTimeType);

    /**
     *
     * @param statTimeType 时间层次类型
     * @return
     */
    public Map<String, Object> getSiteOrderRank(StatTimeType statTimeType);

    /**
     *
     * @param statTimeType 时间层次类型
     * @return
     */
    public Map<String, Object> getSitePriceRank(StatTimeType statTimeType);

    /**
     *
     * @param statTimeType 时间层次类型
     * @return
     */
    public Map<String, Object> getSiteCourseIncomeRank(StatTimeType statTimeType);

    /**
     *
     * @param statTimeType 时间层次类型
     * @return
     */
    public List<Double> getQuitRate(StatTimeType statTimeType);

    /**
     *
     * @param statTimeType 时间层次类型
     * @return
     */
    public List<Double> getSatisfactionRate(StatTimeType statTimeType);

    /**
     *
     * @param statTimeType 时间层次类型
     * @return
     */
    public List<Double> getConsumptionConversionRate(StatTimeType statTimeType);

    /**
     *
     * @param statTimeType 时间层次类型
     * @return
     */
    public List<Double> getOrderConversionRate(StatTimeType statTimeType);

    /**
     *
     * @return
     */
    public List<Double> getSiteIncomeYearToYearRate();

    /**
     *
     * @return
     */
    public List<Double> getSiteMemberYearToYearRate();

    /**
     *
     * @param statTimeType 时间层次类型
     * @return
     */
    public Map<String, Object> getTeacherIncomeRank(StatTimeType statTimeType);

    public List<Integer> getMemberQuota(StatTimeType statTimeType, String organId);

    public List<Double> getIncomeQuota(StatTimeType statTimeType, String organId);
}
