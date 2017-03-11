package cn.moo.trainingcollege.dao.impl;

import cn.moo.trainingcollege.dao.OrderDao;
import cn.moo.trainingcollege.entity.OrderAccountEntity;
import org.hibernate.Criteria;
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
    public int getStudentNumofCourse(int courseId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(OrderAccountEntity.class);
        criteria.add(Restrictions.eq("courseId", courseId));
        criteria.add(Restrictions.eq("paid", true));
        criteria.add(Restrictions.eq("quitState", 0));
        int count = Integer.parseInt(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
        return count;
    }
}
