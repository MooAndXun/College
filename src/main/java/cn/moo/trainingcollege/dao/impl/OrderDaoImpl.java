package cn.moo.trainingcollege.dao.impl;

import cn.moo.trainingcollege.dao.OrderDao;
import cn.moo.trainingcollege.entity.OrderAccountEntity;
import org.apache.commons.collections.map.HashedMap;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

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
            names.add((String)object.get("name"));
            nums.add(((BigInteger)object.get("num")).intValue());
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("names", names);
        resultMap.put("nums", nums);

        return resultMap;
    }
}
