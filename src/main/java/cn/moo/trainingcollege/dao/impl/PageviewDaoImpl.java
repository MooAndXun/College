package cn.moo.trainingcollege.dao.impl;

import cn.moo.trainingcollege.dao.PageviewDao;
import cn.moo.trainingcollege.entity.PageviewEntity;
import cn.moo.trainingcollege.utils.StatTimeType;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 小春 on 2017/6/19.
 */
@Repository
public class PageviewDaoImpl extends BaseDaoImpl<PageviewEntity> implements PageviewDao {
    @Override
    public List<Integer> getPageViewCount(StatTimeType statTimeType) {
        Session session = sessionFactory.getCurrentSession();
        String sql;

        List<Map<String, Object>> data;
        switch (statTimeType) {
            case YEAR:
                sql = "SELECT DATE_FORMAT(created_at, '%Y') AS year, num\n" +
                        "FROM pageview\n" +
                        "WHERE created_at > DATE_SUB(NOW(), INTERVAL 3 YEAR)\n" +
                        "GROUP BY DATE_FORMAT(created_at, '%y')\n" +
                        "ORDER BY DATE_FORMAT(created_at, '%y');";
                data = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                return getYearTimeLine(data, false, "num");
            case MONTH:
                sql = "SELECT DATE_FORMAT(created_at, '%c') AS month, num\n" +
                        "FROM pageview\n" +
                        "WHERE created_at > DATE_SUB(NOW(), INTERVAL 12 MONTH)\n" +
                        "GROUP BY DATE_FORMAT(created_at, '%y%m')\n" +
                        "ORDER BY DATE_FORMAT(created_at, '%y%m');";
                data = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                return getMonthTimeLine(data, false, "num");
            case WEEK:
                sql = "SELECT DATE_FORMAT(created_at, '%v') AS week, num\n" +
                        "FROM pageview\n" +
                        "WHERE created_at > DATE_SUB(NOW(), INTERVAL 8 WEEK)\n" +
                        "  AND YEAR(created_at) = YEAR(NOW())\n" +
                        "GROUP BY DATE_FORMAT(created_at, '%x%v')\n" +
                        "ORDER BY DATE_FORMAT(created_at, '%x%v');";
                data = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                return getWeekTimeLine(data, false, "num");
        }
        return new ArrayList<>();
    }
}
