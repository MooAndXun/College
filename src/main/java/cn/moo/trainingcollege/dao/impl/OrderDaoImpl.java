package cn.moo.trainingcollege.dao.impl;

import cn.moo.trainingcollege.dao.OrderDao;
import cn.moo.trainingcollege.entity.OrderAccountEntity;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        List<OrderAccountEntity> list =  criteria.list();
        if ((list.size()) == 0){
            return null;
        }else{
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
        List<OrderAccountEntity> list =  criteria.list();
        if ((list.size()) == 0){
            return null;
        }else{
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
        String sql = "SELECT COUNT(course_id) FROM `order_account` WHERE student_id = '"+studentId+"' AND is_cancel=0 AND quit_state=0 AND created_at > DATE_SUB(NOW(),INTERVAL 1 YEAR) GROUP BY DATE_FORMAT(created_at,'%y%m') ORDER BY DATE_FORMAT(created_at,'%y%m');";
        Session session = sessionFactory.getCurrentSession();
        List<Integer> list = (List<Integer>)session.createSQLQuery(sql).list();
        return list;
    }

    @Override
    public int getStudentNumofCourse(int courseId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(OrderAccountEntity.class);
        criteria.add(Restrictions.eq("courseId", courseId));
        criteria.add(Restrictions.eq("paid", true));
        criteria.add(Restrictions.eq("quitState", 0));
        int count = Integer.parseInt(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
        return count;
    }

    @Override
    public double getCourseNormalIncome(int courseId) {
        String hql = "SELECT SUM(price) FROM order_account WHERE courseId = :courseId AND cancel = false AND quitState = 0 AND paid = true";

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql).setInteger("courseId", courseId);
        Object result = query.uniqueResult();
        return result==null?0:(double)result;
    }

    @Override
    public double getCourseQuitIncome(int courseId) {
        String hql = "SELECT SUM(price) FROM order_account WHERE courseId = :courseId AND quitState = 1";

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql).setInteger("courseId", courseId);
        Object result = query.uniqueResult();
        return result==null?0:(((double)result)/2);
    }
}
