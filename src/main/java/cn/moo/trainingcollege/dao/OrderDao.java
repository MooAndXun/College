package cn.moo.trainingcollege.dao;

import cn.moo.trainingcollege.entity.OrderAccountEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by 小春 on 2017/2/21.
 */
public interface OrderDao extends BaseDao<OrderAccountEntity> {
    public OrderAccountEntity getByStudentandCourse(String studentId,int courseId);

    public OrderAccountEntity getOverOrder(String studentId, int courseId);

    /**
     * 获得会员所参与且无退课的课程列表
     * @param studentId
     * @return
     */
    public List<OrderAccountEntity> getStudentCourseList(String studentId);

    /**
     * 获得某个课程的学生人数
     */
    public  int getStudentNumofCourse(int courseId);

    public double getCourseNormalIncome(int courseId);

    public double getCourseQuitIncome(int courseId);
}
