package cn.moo.trainingcollege.dao;

import cn.moo.trainingcollege.entity.OrderCashEntity;

/**
 * Created by 小春 on 2017/2/21.
 */
public interface OrderCashDao extends BaseDao<OrderCashEntity> {
    public OrderCashEntity getByStudentAndCourse(String studentName, int courseId);
}
