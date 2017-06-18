package cn.moo.trainingcollege.dao.impl;

import cn.moo.trainingcollege.dao.SettlementDao;
import cn.moo.trainingcollege.entity.SettlementEntity;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        double income = list.get(0)!=null?(double)list.get(0):0;
        return income;
    }

    @Override
    public double getSiteIncome() {
        String sql = "select SUM(commission) as income FROM settlement;";
        Session session = sessionFactory.getCurrentSession();
        List list = session.createSQLQuery(sql).list();
        double income = list.get(0)!=null?(double)list.get(0):0;
        return income;
    }

    @Override
    public List<Double> getOrganIncomeLine(String organId) {
        String sql = "SELECT DATE_FORMAT(created_at,'%c') AS month, SUM(money) AS num FROM settlement WHERE organization_id = '"+organId+"' AND created_at > DATE_SUB(NOW(),INTERVAL 1 YEAR) GROUP BY DATE_FORMAT(created_at,'%y%m') ORDER BY DATE_FORMAT(created_at,'%y%m');";
        return getDoubleTimeLine(sql);

    }

    @Override
    public List<Integer> getOrganMemberLine(String organId) {
        String sql = " SELECT  DATE_FORMAT(created_at,'%c') AS month, COUNT(DISTINCT student_id) as num FROM order_account " +
                "WHERE is_paid = 1 " +
                "AND created_at > DATE_SUB(NOW(),INTERVAL 1 YEAR) " +
                "AND course_id IN (SELECT id FROM course WHERE organ_id = '"+organId+"') " +
                "GROUP BY DATE_FORMAT(created_at,'%y%m') " +
                "ORDER BY DATE_FORMAT(created_at,'%y%m')";
        return getIntTimeLine(sql);
    }

    @Override
    public List<Double> getSiteIncomeLine() {
        String sql = "SELECT DATE_FORMAT(created_at,'%c') AS month, SUM(commission) AS num FROM settlement WHERE  created_at > DATE_SUB(NOW(),INTERVAL 1 YEAR) GROUP BY DATE_FORMAT(created_at,'%y%m') ORDER BY DATE_FORMAT(created_at,'%y%m');";
        return getDoubleTimeLine(sql);
    }

    @Override
    public Map<String, Object> getOrganTopCourse(String organId) {
        String sql = "SELECT c.`name`, COUNT(o.student_id) as num FROM course c JOIN order_account o ON c.id = o.course_id WHERE c.organ_id = '"+organId+"' GROUP BY c.`name` ORDER BY num DESC LIMIT 5";
        Session session = sessionFactory.getCurrentSession();
        List<Object[]> list = (List<Object[]>)session.createSQLQuery(sql).list();
        Map<String, Object> map = new HashMap<>();
        List<String> courses = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();
        for (Object[] objects:list) {
            String name = objects[0].toString();
            int num = Integer.parseInt(objects[1].toString());
            courses.add(name);
            numbers.add(num);
        }
        map.put("names",courses);
        map.put("members",numbers);
        return map;
    }

    @Override
    public List<Double> getSiteIncomeYearToYearRate() {
        List<Double> currentYearData = getSiteIncomeLine();

        Session session = sessionFactory.getCurrentSession();
        String sql = "SELECT DATE_FORMAT(created_at,'%c') AS month, SUM(commission) AS income\n" +
                "FROM settlement\n" +
                "WHERE created_at <= DATE_SUB(NOW(),INTERVAL 1 YEAR)\n" +
                "  AND created_at > DATE_SUB(NOW(),INTERVAL 2 YEAR)\n" +
                "GROUP BY DATE_FORMAT(created_at,'%y%m')\n" +
                "ORDER BY DATE_FORMAT(created_at,'%y%m');";

        List<Map<String, Object>> data = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

        List<Double> lastYearData = getMonthTimeLine(data, true, "income");

        List<Double> resultList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            double currentMonthData = currentYearData.get(i);
            double lastMonthData = lastYearData.get(i);

            if(lastMonthData!=0) {
                double rate = (currentMonthData-lastMonthData)/lastMonthData;
                resultList.add(rate);
            }else {
                resultList.add(0.0);
            }
        }

        return resultList;
    }
}
