package cn.moo.trainingcollege.dao.impl;

import cn.moo.trainingcollege.dao.SettlementDao;
import cn.moo.trainingcollege.entity.SettlementEntity;
import org.hibernate.Session;
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

    @Override
    public List<Double> getOrganIncomeLine(String organId) {
        String sql = "SELECT SUM(money) AS income FROM settlement WHERE organization_id = '"+organId+"' AND created_at > DATE_SUB(NOW(),INTERVAL 1 YEAR) GROUP BY DATE_FORMAT(created_at,'%y%m') ORDER BY DATE_FORMAT(created_at,'%y%m');";
        Session session = sessionFactory.getCurrentSession();
        List<Double> list = (List<Double>)session.createSQLQuery(sql).list();
        return list;

    }

    @Override
    public List<BigInteger> getOrganMemberLine(String organId) {
        String sql = " SELECT COUNT(DISTINCT student_id) as num FROM order_account WHERE is_paid = 1 " +
                "AND quit_state = 0 " +
                "AND created_at > DATE_SUB(NOW(),INTERVAL 1 YEAR) " +
                "AND course_id IN (SELECT id FROM course WHERE organ_id = '"+organId+"') " +
                "GROUP BY DATE_FORMAT(created_at,'%y%m') " +
                "ORDER BY DATE_FORMAT(created_at,'%y%m')";
        Session session = sessionFactory.getCurrentSession();
        List list = session.createSQLQuery(sql).list();
        return list;
    }

    @Override
    public List<Double> getSiteIncomeLine() {
        String sql = "SELECT SUM(commission) AS income FROM settlement WHERE  created_at > DATE_SUB(NOW(),INTERVAL 1 YEAR) GROUP BY DATE_FORMAT(created_at,'%y%m') ORDER BY DATE_FORMAT(created_at,'%y%m');";
        Session session = sessionFactory.getCurrentSession();
        List<Double> list = (List<Double>)session.createSQLQuery(sql).list();
        return list;
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


}
