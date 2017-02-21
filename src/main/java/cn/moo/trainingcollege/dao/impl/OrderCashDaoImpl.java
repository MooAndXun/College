package cn.moo.trainingcollege.dao.impl;

import cn.moo.trainingcollege.dao.OrderCashDao;
import cn.moo.trainingcollege.entity.OrderCashEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 小春 on 2017/2/21.
 */
@Repository
public class OrderCashDaoImpl extends BaseDaoImpl<OrderCashEntity> implements OrderCashDao {

    @Override
    public OrderCashEntity getByStudentAndCourse(String studentName, int courseId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(OrderCashEntity.class);
        criteria.add(Restrictions.eq("studentName", studentName));
        criteria.add(Restrictions.eq("courseId", courseId));
        List<OrderCashEntity> list =  criteria.list();
        if ((list.size()) == 0){
            return null;
        }else{
            return (OrderCashEntity) list.get(0);
        }
    }
}
