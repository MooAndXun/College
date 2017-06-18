package cn.moo.trainingcollege.dao;

import cn.moo.trainingcollege.entity.SettlementEntity;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Created by 小春 on 2017/3/11.
 */
public interface SettlementDao extends BaseDao<SettlementEntity> {
    public double getOrganIncome(String organId);

    public double getSiteIncome();

    public List<Double> getOrganIncomeLine(String organId);

    public  List<Integer> getOrganMemberLine(String organId);

    public List<Double> getSiteIncomeLine();

    public Map<String,Object> getOrganTopCourse(String organId);

    public List<Double> getSiteIncomeYearToYearRate();
}
