package cn.moo.trainingcollege.dao.impl;

import cn.moo.trainingcollege.dao.CourseDao;
import cn.moo.trainingcollege.entity.CourseEntity;
import cn.moo.trainingcollege.utils.StatTimeType;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenmuen on 2017/2/12.
 */
@Repository
public class CourseDaoImpl extends BaseDaoImpl<CourseEntity> implements CourseDao {
    @Override
    public Map<String, Object> getSitePriceRank(StatTimeType statTimeType) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "";

        switch (statTimeType) {
            case YEAR:
                sql = "SELECT course.name AS name, price FROM course\n" +
                        "WHERE TO_DAYS(NOW()) - TO_DAYS(start_time) <= 365\n" +
                        "ORDER BY price DESC LIMIT 5";
                break;
            case MONTH:
                sql = "SELECT course.name AS name, price FROM course\n" +
                        "WHERE DATE_FORMAT(start_time,'%Y-%m')=DATE_FORMAT(NOW(),'%Y-%m')\n" +
                        "ORDER BY price DESC LIMIT 5";
                break;
            case WEEK:
                sql = "SELECT course.name AS name, price FROM course\n" +
                        "WHERE YEARWEEK(DATE_FORMAT(start_time,'%Y-%m-%d')) = YEARWEEK(NOW())\n" +
                        "ORDER BY price DESC LIMIT 5";
                break;
            default:
                break;
        }

        List<Map> data = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

        List<String> names = new ArrayList<>();
        List<Double> prices = new ArrayList<>();

        for (Map object : data) {
            names.add((String)object.get("name"));
            prices.add((Double)object.get("price"));
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("names", names);
        resultMap.put("prices", prices);

        return resultMap;
    }
}
