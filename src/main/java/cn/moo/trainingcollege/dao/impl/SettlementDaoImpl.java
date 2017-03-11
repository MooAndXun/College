package cn.moo.trainingcollege.dao.impl;

import cn.moo.trainingcollege.dao.SettlementDao;
import cn.moo.trainingcollege.entity.SettlementEntity;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 小春 on 2017/3/11.
 */
@Repository
public class SettlementDaoImpl extends BaseDaoImpl<SettlementEntity> implements SettlementDao{
    @Override
    public double getOrganIncome(String organId) {
        String sql = "select SUM(money) as income FROM settlement where organization_id = '" + organId +"';";
        Session session = sessionFactory.getCurrentSession();
        List list = session.createSQLQuery(sql).list();
        double income = (double)list.get(0);
        return income;
    }

    @Override
    public double getSiteIncome() {
        String sql = "select SUM(commission) as income FROM settlement;";
        Session session = sessionFactory.getCurrentSession();
        List list = session.createSQLQuery(sql).list();
        double income = (double)list.get(0);
        return income;
    }
}
