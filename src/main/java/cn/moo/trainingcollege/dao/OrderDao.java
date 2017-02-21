package cn.moo.trainingcollege.dao;

import cn.moo.trainingcollege.entity.OrderAccountEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Created by 小春 on 2017/2/21.
 */
public interface OrderDao extends BaseDao<OrderAccountEntity> {
    public OrderAccountEntity getByStudentandCourse(String studentId,int courseId);
}
