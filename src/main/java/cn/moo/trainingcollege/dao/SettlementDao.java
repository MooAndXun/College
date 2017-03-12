package cn.moo.trainingcollege.dao;

import cn.moo.trainingcollege.entity.SettlementEntity;

import java.util.List;

/**
 * Created by 小春 on 2017/3/11.
 */
public interface SettlementDao extends BaseDao<SettlementEntity> {
    public double getOrganIncome(String organId);

    public double getSiteIncome();

    public List<Double> getOrganIncomeLine(String organId);
}
