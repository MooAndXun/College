package cn.moo.trainingcollege.dao.impl;

import cn.moo.trainingcollege.dao.BaseDao;
import cn.moo.trainingcollege.dao.StudentDao;
import cn.moo.trainingcollege.entity.StudentEntity;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by chenmuen on 2017/2/12.
 */
@Repository
public class StudentDaoImpl extends BaseDaoImpl<StudentEntity> implements StudentDao {
    @Override
    public List getSiteStudentLine() {
        String sql =
                "SELECT DATE_FORMAT(created_at, '%c') AS month, COUNT(DISTINCT id) AS num " +
                "FROM student " +
                "WHERE created_at > DATE_SUB(NOW(), INTERVAL 1 YEAR) " +
                "GROUP BY DATE_FORMAT(created_at, '%y%m') " +
                "ORDER BY DATE_FORMAT(created_at, '%y%m') ";
        return getIntTimeLine(sql);
    }

    @Override
    public List<Double> getSiteMemberYearToYearRate() {
        List<Integer> currentYearData = getSiteStudentLine();

        Session session = sessionFactory.getCurrentSession();
        String sql = "SELECT DATE_FORMAT(created_at, '%c') AS month, COUNT(DISTINCT id) AS num " +
                        "FROM student " +
                        "WHERE created_at <= DATE_SUB(NOW(), INTERVAL 1 YEAR) " +
                        "  AND created_at > DATE_SUB(NOW(), INTERVAL 2 YEAR)" +
                        "GROUP BY DATE_FORMAT(created_at, '%y%m') " +
                        "ORDER BY DATE_FORMAT(created_at, '%y%m') ";

        List<Map<String, Object>> data = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

        List<Integer> lastYearData = getMonthTimeLine(data, false, "num");

        List<Double> resultList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            int currentMonthData = currentYearData.get(i);
            int lastMonthData = lastYearData.get(i);

            if(lastMonthData!=0) {
                double rate = (double)(currentMonthData-lastMonthData)/(double)lastMonthData;
                resultList.add(rate);
            }else {
                resultList.add(0.0);
            }
        }
        return resultList;
    }
}
