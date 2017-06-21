package cn.moo.trainingcollege.dao.impl;

import cn.moo.trainingcollege.dao.OrderDao;
import cn.moo.trainingcollege.entity.OrderAccountEntity;
import cn.moo.trainingcollege.utils.StatTimeType;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 小春 on 2017/2/21.
 */
@Repository
public class OrderDaoImpl extends BaseDaoImpl<OrderAccountEntity> implements OrderDao {
    @Override
    public OrderAccountEntity getByStudentandCourse(String studentId, int courseId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(OrderAccountEntity.class);
        criteria.add(Restrictions.eq("cancel", false));
        criteria.add(Restrictions.eq("quitState", 0));
        criteria.add(Restrictions.eq("studentId", studentId));
        criteria.add(Restrictions.eq("courseId", courseId));
        List<OrderAccountEntity> list = criteria.list();
        if ((list.size()) == 0) {
            return null;
        } else {
            return (OrderAccountEntity) list.get(0);
        }
    }

    @Override
    public OrderAccountEntity getOverOrder(String studentId, int courseId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(OrderAccountEntity.class);
        criteria.add(Restrictions.or(
                Restrictions.eq("cancel", true),
                Restrictions.eq("quitState", 1)));
        criteria.add(Restrictions.eq("studentId", studentId));
        criteria.add(Restrictions.eq("courseId", courseId));
        List<OrderAccountEntity> list = criteria.list();
        if ((list.size()) == 0) {
            return null;
        } else {
            return (OrderAccountEntity) list.get(0);
        }
    }

    @Override
    public List<OrderAccountEntity> getStudentCourseList(String studentId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(OrderAccountEntity.class);
        criteria.add(Restrictions.eq("studentId", studentId));
        criteria.add(Restrictions.eq("paid", true));
        criteria.add(Restrictions.eq("quitState", 0));
        return criteria.list();
    }

    @Override
    public List<Integer> getStudentJoinedCourseMonth(String studentId) {
        String sql = "SELECT COUNT(course_id) FROM `order_account` WHERE student_id = '" + studentId + "' AND is_cancel=0 AND quit_state=0 AND created_at > DATE_SUB(NOW(),INTERVAL 1 YEAR) GROUP BY DATE_FORMAT(created_at,'%y%m') ORDER BY DATE_FORMAT(created_at,'%y%m');";
        Session session = sessionFactory.getCurrentSession();
        List<Integer> list = (List<Integer>) session.createSQLQuery(sql).list();
        return list;
    }

    @Override
    public double getStudentConsume(String studentId) {
        String sql1 = "SELECT SUM(price) AS consume FROM OrderAccountEntity WHERE studentId=:studentId AND paid=true";
        String sql2 = "SELECT SUM(price)*0.5 AS consume FROM OrderAccountEntity WHERE studentId=:studentId AND quitState=1";
        Session session = sessionFactory.getCurrentSession();
        Object result1 = session.createQuery(sql1).setString("studentId", studentId).uniqueResult();
        Object result2 = session.createQuery(sql2).setString("studentId", studentId).uniqueResult();

        double consume = 0;
        double quitConsume = 0;

        if (result1 != null)
            consume = (double) result1;
        if (result2 != null)
            consume = (double) result2;

        return consume + quitConsume;
    }

    @Override
    public int getStudentNumOfOrgan(String organId) {
        Session session = sessionFactory.getCurrentSession();
//        Criteria criteria = session.createCriteria(OrderAccountEntity.class);
//        criteria.add(Restrictions.eq("courseId", organId));
//        criteria.add(Restrictions.eq("paid", true));
//        criteria.add(Restrictions.eq("quitState", 0));
//        int count = Integer.parseInt(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());

        String sql = "SELECT COUNT(DISTINCT orderA.student_id) AS num FROM order_account AS orderA " +
                "JOIN course AS course ON orderA.course_id=course.id " +
                "WHERE course.organ_id = :organId " +
                "AND is_paid=1";
        int count = ((BigInteger) session.createSQLQuery(sql).setString("organId", organId).uniqueResult()).intValue();

        return count;
    }

    @Override
    public double getCourseNormalIncome(int courseId) {
        String hql = "SELECT SUM(price) FROM OrderAccountEntity WHERE courseId = :courseId AND cancel = false AND quitState = 0 AND paid = true";

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql).setInteger("courseId", courseId);
        Object result = query.uniqueResult();
        return result == null ? 0 : (double) result;
    }

    @Override
    public double getCourseQuitIncome(int courseId) {
        String hql = "SELECT SUM(price) FROM OrderAccountEntity WHERE courseId = :courseId AND quitState = 1";

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql).setInteger("courseId", courseId);
        Object result = query.uniqueResult();
        return result == null ? 0 : (((double) result) / 2);
    }

    @Override
    public List<Integer> getStudentCourseLine(String studentId) {
        String sql =
                "SELECT DATE_FORMAT(created_at, '%c') AS month, COUNT(DISTINCT course_id) AS num " +
                        "FROM order_account " +
                        "WHERE student_id = '" + studentId + "' " +
                        "      AND is_cancel = 0 " +
                        "      AND created_at > DATE_SUB(NOW(), INTERVAL 1 YEAR) " +
                        "GROUP BY DATE_FORMAT(created_at, '%y%m') " +
                        "ORDER BY DATE_FORMAT(created_at, '%y%m') ";

        return getIntTimeLine(sql);
    }

    @Override
    public Map<String, Object> getSiteTopOrgan() {
        String sql =
                "SELECT organization.name AS name, COUNT(DISTINCT student_id) AS num " +
                        "FROM organization " +
                        "  LEFT JOIN course ON organization.id = organ_id " +
                        "  LEFT JOIN order_account ON course_id = course.id " +
                        "  WHERE is_paid=1 " +
                        "GROUP BY organ_id, organization.name " +
                        "ORDER BY COUNT(DISTINCT student_id) DESC LIMIT 5";
        Session session = sessionFactory.getCurrentSession();
        List<Map> objects = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

        List<String> names = new ArrayList<>();
        List<Integer> nums = new ArrayList<>();
        for (Map object : objects) {
            names.add((String) object.get("name"));
            nums.add(((BigInteger) object.get("num")).intValue());
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("names", names);
        resultMap.put("nums", nums);

        return resultMap;
    }

    @Override
    public Map<String, Object> getSiteOrderRank(StatTimeType statTimeType, String organId) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "";

        switch (statTimeType) {
            case YEAR:
                sql = "SELECT course.name AS name, COUNT(DISTINCT student_id) AS num\n" +
                        "FROM order_account JOIN course ON order_account.course_id = course.id\n" +
                        "WHERE TO_DAYS(NOW()) - TO_DAYS(created_at) <= 365\n" +
                        "      AND is_paid=1\n" +
                        getOrganWhere(organId) +
                        "GROUP BY course_id\n" +
                        "ORDER BY COUNT(DISTINCT student_id) DESC LIMIT 5";
                break;
            case MONTH:
                sql = "SELECT course.name AS name, COUNT(DISTINCT student_id) AS num\n" +
                        "FROM order_account JOIN course ON order_account.course_id = course.id\n" +
                        "WHERE DATE_FORMAT(created_at,'%Y-%m')=DATE_FORMAT(NOW(),'%Y-%m') \n" +
                        "      AND is_paid=1\n" +
                        getOrganWhere(organId) +
                        "GROUP BY course_id\n" +
                        "ORDER BY COUNT(DISTINCT student_id) DESC LIMIT 5";
                break;
            case WEEK:
                sql = "SELECT course.name AS name, COUNT(DISTINCT student_id) AS num\n" +
                        "FROM order_account JOIN course ON order_account.course_id = course.id\n" +
                        "WHERE YEARWEEK(DATE_FORMAT(created_at,'%Y-%m-%d')) = YEARWEEK(NOW())\n" +
                        "      AND is_paid=1\n" +
                        getOrganWhere(organId) +
                        "GROUP BY course_id\n" +
                        "ORDER BY COUNT(DISTINCT student_id) DESC LIMIT 5;";
                break;
            default:
                break;
        }
        List<Map> data = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

        List<String> names = new ArrayList<>();
        List<Integer> nums = new ArrayList<>();
        for (Map object : data) {
            names.add((String) object.get("name"));
            nums.add(((BigInteger) object.get("num")).intValue());
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("names", names);
        resultMap.put("nums", nums);

        return resultMap;
    }

    @Override
    public Map<String, Object> getSiteIncomeRank(StatTimeType statTimeType, String organId) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "";

        switch (statTimeType) {
            case YEAR:
                sql = "SELECT course.name AS name, SUM(order_account.price) AS income\n" +
                        "FROM order_account JOIN course ON order_account.course_id = course.id\n" +
                        "WHERE TO_DAYS(NOW()) - TO_DAYS(created_at) <= 365\n" +
                        "      AND is_paid=1\n" +
                        getOrganWhere(organId) +
                        "GROUP BY course_id\n" +
                        "ORDER BY SUM(order_account.price) DESC LIMIT 5";
                break;
            case MONTH:
                sql = "SELECT course.name AS name, SUM(order_account.price) AS income\n" +
                        "FROM order_account JOIN course ON order_account.course_id = course.id\n" +
                        "WHERE date_format(created_at,'%Y-%m')=date_format(now(),'%Y-%m')\n" +
                        "      AND is_paid=1\n" +
                        getOrganWhere(organId) +
                        "GROUP BY course_id\n" +
                        "ORDER BY SUM(order_account.price) DESC LIMIT 5;";
                break;
            case WEEK:
                sql = "SELECT course.name AS name, SUM(order_account.price) AS income\n" +
                        "FROM order_account JOIN course ON order_account.course_id = course.id\n" +
                        "WHERE YEARWEEK(DATE_FORMAT(created_at,'%Y-%m-%d')) = YEARWEEK(NOW())\n" +
                        "      AND is_paid=1\n" +
                        getOrganWhere(organId) +
                        "GROUP BY course_id\n" +
                        "ORDER BY SUM(order_account.price) DESC LIMIT 5;";
                break;
            default:
                break;
        }
        List<Map> data = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

        List<String> names = new ArrayList<>();
        List<Double> incomes = new ArrayList<>();
        for (Map object : data) {
            names.add((String) object.get("name"));
            incomes.add((Double) object.get("income"));
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("names", names);
        resultMap.put("incomes", incomes);

        return resultMap;
    }

    public Map<String, Object> getSiteQuitRank(StatTimeType statTimeType, String organId) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "";

        switch (statTimeType) {
            case YEAR:
                sql = "SELECT course.name AS name, (COUNT(quit_state=1 or null)/COUNT(*)) AS rate\n" +
                        "FROM order_account JOIN course ON order_account.course_id = course.id\n" +
                        "WHERE TO_DAYS(NOW()) - TO_DAYS(created_at) <= 365\n" +
                        "      AND is_paid=1\n" +
                        getOrganWhere(organId) +
                        "GROUP BY course_id\n" +
                        "ORDER BY (COUNT(quit_state=1 or null)/COUNT(*)) DESC LIMIT 5";
                break;
            case MONTH:
                sql = "SELECT course.name AS name, (COUNT(quit_state=1 or null)/COUNT(*)) AS rate\n" +
                        "FROM order_account JOIN course ON order_account.course_id = course.id\n" +
                        "WHERE date_format(created_at,'%Y-%m')=date_format(now(),'%Y-%m')\n" +
                        "      AND is_paid=1\n" +
                        getOrganWhere(organId) +
                        "GROUP BY course_id\n" +
                        "ORDER BY (COUNT(quit_state=1 or null)/COUNT(*)) DESC LIMIT 5;";
                break;
            case WEEK:
                sql = "SELECT course.name AS name, (COUNT(quit_state=1 or null)/COUNT(*)) AS rate\n" +
                        "FROM order_account JOIN course ON order_account.course_id = course.id\n" +
                        "WHERE YEARWEEK(DATE_FORMAT(created_at,'%Y-%m-%d')) = YEARWEEK(NOW())\n" +
                        "      AND is_paid=1\n" +
                        getOrganWhere(organId) +
                        "GROUP BY course_id\n" +
                        "ORDER BY (COUNT(quit_state=1 or null)/COUNT(*)) DESC LIMIT 5;";
                break;
            default:
                break;
        }
        List<Map> data = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

        List<String> names = new ArrayList<>();
        List<Double> rates = new ArrayList<>();
        for (Map object : data) {
            names.add((String) object.get("name"));
            rates.add(((BigDecimal) object.get("rate")).doubleValue());
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("names", names);
        resultMap.put("rates", rates);

        return resultMap;
    }

    @Override
    public Map<String, Object> getSiteSatisfactionRank(StatTimeType statTimeType, String organId) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "";

        switch (statTimeType) {
            case YEAR:
                sql = "SELECT course.name AS name, (SUM(satisfaction)/(COUNT(satisfaction>0)*5)) AS rate\n" +
                        "FROM order_account JOIN course ON order_account.course_id = course.id\n" +
                        "WHERE TO_DAYS(NOW()) - TO_DAYS(created_at) <= 365\n" +
                        "      AND is_paid=1\n" +
                        getOrganWhere(organId) +
                        "GROUP BY course_id\n" +
                        "ORDER BY (SUM(satisfaction)/COUNT(satisfaction>0 or null)) DESC LIMIT 5";
                break;
            case MONTH:
                sql = "SELECT course.name AS name, (SUM(satisfaction)/(COUNT(satisfaction>0)*5)) AS rate\n" +
                        "FROM order_account JOIN course ON order_account.course_id = course.id\n" +
                        "WHERE date_format(created_at,'%Y-%m')=date_format(now(),'%Y-%m')\n" +
                        "      AND is_paid=1\n" +
                        getOrganWhere(organId) +
                        "GROUP BY course_id\n" +
                        "ORDER BY (SUM(satisfaction)/COUNT(satisfaction>0 or null)) DESC LIMIT 5;";
                break;
            case WEEK:
                sql = "SELECT course.name AS name, (SUM(satisfaction)/(COUNT(satisfaction>0)*5)) AS rate\n" +
                        "FROM order_account JOIN course ON order_account.course_id = course.id\n" +
                        "WHERE YEARWEEK(DATE_FORMAT(created_at,'%Y-%m-%d')) = YEARWEEK(NOW())\n" +
                        "      AND is_paid=1\n" +
                        getOrganWhere(organId) +
                        "GROUP BY course_id\n" +
                        "ORDER BY (SUM(satisfaction)/COUNT(satisfaction>0 or null)) DESC LIMIT 5;";
                break;
            default:
                break;
        }
        List<Map> data = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

        List<String> names = new ArrayList<>();
        List<Double> rates = new ArrayList<>();
        for (Map object : data) {
            names.add((String) object.get("name"));
            rates.add(((BigDecimal) object.get("rate")).doubleValue());
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("names", names);
        resultMap.put("rates", rates);

        return resultMap;
    }

    @Override
    public List<Double> getSiteQuitRate(StatTimeType statTimeType, String organId) {
        Session session = sessionFactory.getCurrentSession();
        String sql;

        List<Map<String, Object>> data;
        switch (statTimeType) {
            case YEAR:
                sql = "SELECT DATE_FORMAT(created_at, '%Y') AS year, (COUNT(quit_state=1 or null)/COUNT(*)) AS rate\n" +
                        "FROM order_account\n" +
                        "  JOIN course ON order_account.course_id = course.id\n" +
                        "WHERE created_at > DATE_SUB(NOW(), INTERVAL 3 YEAR)\n" +
                        getOrganWhere(organId) +
                        "GROUP BY DATE_FORMAT(created_at, '%y')\n" +
                        "ORDER BY DATE_FORMAT(created_at, '%y');";
                data = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                return getYearTimeLine(data, true, "rate");
            case MONTH:
                sql = "SELECT DATE_FORMAT(created_at, '%c') AS month, (COUNT(quit_state=1 or null)/COUNT(*)) AS rate\n" +
                        "FROM order_account\n" +
                        "JOIN course ON order_account.course_id = course.id\n" +
                        "WHERE created_at > DATE_SUB(NOW(), INTERVAL 12 MONTH)\n" +
                        getOrganWhere(organId) +
                        "GROUP BY DATE_FORMAT(created_at, '%y%m')\n" +
                        "ORDER BY DATE_FORMAT(created_at, '%y%m');";
                data = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                return getMonthTimeLine(data, true, "rate");
            case WEEK:
                sql = "SELECT DATE_FORMAT(created_at, '%v') AS week, (COUNT(quit_state=1 or null)/COUNT(*)) AS rate\n" +
                        "FROM order_account\n" +
                        "  JOIN course ON order_account.course_id = course.id\n" +
                        "WHERE created_at > DATE_SUB(NOW(), INTERVAL 8 WEEK)\n" +
                        "  AND YEAR(created_at) = YEAR(NOW())\n" +
                        getOrganWhere(organId) +
                        "GROUP BY DATE_FORMAT(created_at, '%x%v')\n" +
                        "ORDER BY DATE_FORMAT(created_at, '%x%v');";
                data = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                return getWeekTimeLine(data, true, "rate");
        }
        return new ArrayList<>();
    }

    @Override
    public List<Double> getSiteSatisfactionRate(StatTimeType statTimeType, String organId) {
        Session session = sessionFactory.getCurrentSession();
        String sql;

        List<Map<String, Object>> data;
        switch (statTimeType) {
            case YEAR:
                sql = "SELECT DATE_FORMAT(created_at, '%Y') AS year, (SUM(satisfaction)/(COUNT(satisfaction>0)*5)) AS rate\n" +
                        "FROM order_account\n" +
                        "  JOIN course ON order_account.course_id = course.id\n" +
                        "WHERE created_at > DATE_SUB(NOW(), INTERVAL 3 YEAR)\n" +
                        getOrganWhere(organId) +
                        "GROUP BY DATE_FORMAT(created_at, '%y')\n" +
                        "ORDER BY DATE_FORMAT(created_at, '%y');";
                data = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                return getYearTimeLine(data, true, "rate");
            case MONTH:
                sql = "SELECT DATE_FORMAT(created_at, '%c') AS month, (SUM(satisfaction)/(COUNT(satisfaction>0)*5)) AS rate\n" +
                        "FROM order_account\n" +
                        "JOIN course ON order_account.course_id = course.id\n" +
                        "WHERE created_at > DATE_SUB(NOW(), INTERVAL 12 MONTH)\n" +
                        getOrganWhere(organId) +
                        "GROUP BY DATE_FORMAT(created_at, '%y%m')\n" +
                        "ORDER BY DATE_FORMAT(created_at, '%y%m');";
                data = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                return getMonthTimeLine(data, true, "rate");
            case WEEK:
                sql = "SELECT DATE_FORMAT(created_at, '%v') AS week, (SUM(satisfaction)/(COUNT(satisfaction>0)*5)) AS rate\n" +
                        "FROM order_account\n" +
                        "  JOIN course ON order_account.course_id = course.id\n" +
                        "WHERE created_at > DATE_SUB(NOW(), INTERVAL 8 WEEK)\n" +
                        "  AND YEAR(created_at) = YEAR(NOW())\n" +
                        getOrganWhere(organId) +
                        "GROUP BY DATE_FORMAT(created_at, '%x%v')\n" +
                        "ORDER BY DATE_FORMAT(created_at, '%x%v');";
                data = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                return getWeekTimeLine(data, true, "rate");
        }
        return new ArrayList<>();
    }

    @Override
    public Map<String, Object> getTeacherIncomeRank(StatTimeType statTimeType, String organId) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "";

        switch (statTimeType) {
            case YEAR:
                sql = "SELECT teacher, SUM(order_account.price) AS income\n" +
                        "FROM order_account JOIN course ON order_account.course_id = course.id\n" +
                        "WHERE TO_DAYS(NOW()) - TO_DAYS(created_at) <= 365\n" +
                        "      AND is_paid=1\n" +
                        getOrganWhere(organId) +
                        "GROUP BY teacher\n" +
                        "ORDER BY SUM(order_account.price) DESC LIMIT 5";
                break;
            case MONTH:
                sql = "SELECT teacher, SUM(order_account.price) AS income\n" +
                        "FROM order_account JOIN course ON order_account.course_id = course.id\n" +
                        "WHERE date_format(created_at,'%Y-%m')=date_format(now(),'%Y-%m')\n" +
                        "      AND is_paid=1\n" +
                        getOrganWhere(organId) +
                        "GROUP BY teacher\n" +
                        "ORDER BY SUM(order_account.price) DESC LIMIT 5;";
                break;
            case WEEK:
                sql = "SELECT teacher, SUM(order_account.price) AS income\n" +
                        "FROM order_account JOIN course ON order_account.course_id = course.id\n" +
                        "WHERE YEARWEEK(DATE_FORMAT(created_at,'%Y-%m-%d')) = YEARWEEK(NOW())\n" +
                        "      AND is_paid=1\n" +
                        getOrganWhere(organId) +
                        "GROUP BY teacher\n" +
                        "ORDER BY SUM(order_account.price) DESC LIMIT 5;";
                break;
            default:
                break;
        }
        List<Map> data = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

        List<String> names = new ArrayList<>();
        List<Double> incomes = new ArrayList<>();
        for (Map object : data) {
            names.add((String) object.get("teacher"));
            incomes.add(((Double) object.get("income")));
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("teachers", names);
        resultMap.put("incomes", incomes);

        return resultMap;
    }

    @Override
    public List<Double> getIncomeQuota(StatTimeType statTimeType, String organId) {
        Session session = sessionFactory.getCurrentSession();
        String sql;

        String organWhere = getOrganWhere(organId);

        List<Map<String, Object>> data;
        switch (statTimeType) {
            case YEAR:
                sql = "SELECT DATE_FORMAT(created_at, '%Y') AS year, SUM(order_account.price) AS income\n" +
                        "FROM order_account\n" +
                        "  JOIN course ON order_account.course_id = course.id\n" +
                        "WHERE created_at > DATE_SUB(NOW(), INTERVAL 3 YEAR)\n" +
                        organWhere +
                        "GROUP BY DATE_FORMAT(created_at, '%y')\n" +
                        "ORDER BY DATE_FORMAT(created_at, '%y');";
                data = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                return getYearTimeLine(data, true, "income");
            case MONTH:
                sql = "SELECT DATE_FORMAT(created_at, '%c') AS month, SUM(order_account.price) AS income\n" +
                        "FROM order_account\n" +
                        "JOIN course ON order_account.course_id = course.id\n" +
                        "WHERE created_at > DATE_SUB(NOW(), INTERVAL 12 MONTH)\n" +
                        organWhere +
                        "GROUP BY DATE_FORMAT(created_at, '%y%m')\n" +
                        "ORDER BY DATE_FORMAT(created_at, '%y%m');";
                data = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                return getMonthTimeLine(data, true, "income");
            case WEEK:
                sql = "SELECT DATE_FORMAT(created_at, '%v') AS week, SUM(order_account.price) AS income\n" +
                        "FROM order_account\n" +
                        "  JOIN course ON order_account.course_id = course.id\n" +
                        "WHERE created_at > DATE_SUB(NOW(), INTERVAL 8 WEEK)\n" +
                        "  AND YEAR(created_at) = YEAR(NOW())\n" +
                        organWhere +
                        "GROUP BY DATE_FORMAT(created_at, '%x%v')\n" +
                        "ORDER BY DATE_FORMAT(created_at, '%x%v');";
                data = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                return getWeekTimeLine(data, true, "income");
        }
        return new ArrayList<>();
    }

    @Override
    public List<Integer> getOrderCountQuota(StatTimeType statTimeType) {
        Session session = sessionFactory.getCurrentSession();
        String sql;

        List<Map<String, Object>> data;
        switch (statTimeType) {
            case YEAR:
                sql = "SELECT DATE_FORMAT(created_at, '%Y') AS year, COUNT(DISTINCT order_account.id) AS num\n" +
                        "FROM order_account\n" +
                        "WHERE created_at > DATE_SUB(NOW(), INTERVAL 3 YEAR)\n" +
                        "GROUP BY DATE_FORMAT(created_at, '%y')\n" +
                        "ORDER BY DATE_FORMAT(created_at, '%y');";
                data = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                return getYearTimeLine(data, false, "num");
            case MONTH:
                sql = "SELECT DATE_FORMAT(created_at, '%c') AS month, COUNT(DISTINCT order_account.id) AS num\n" +
                        "FROM order_account\n" +
                        "WHERE created_at > DATE_SUB(NOW(), INTERVAL 12 MONTH)\n" +
                        "GROUP BY DATE_FORMAT(created_at, '%y%m')\n" +
                        "ORDER BY DATE_FORMAT(created_at, '%y%m');";
                data = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                return getMonthTimeLine(data, false, "num");
            case WEEK:
                sql = "SELECT DATE_FORMAT(created_at, '%v') AS week, COUNT(DISTINCT order_account.id) AS num\n" +
                        "FROM order_account\n" +
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
