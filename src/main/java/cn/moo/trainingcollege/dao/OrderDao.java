package cn.moo.trainingcollege.dao;

import cn.moo.trainingcollege.entity.OrderAccountEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Map;

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

    public List<Integer> getStudentJoinedCourseMonth(String studentId);

    public double getStudentConsume(String studentId);

    /**
     * 获得某个课程的学生人数
     */
    public  int getStudentNumOfOrgan(String organId);

    public double getCourseNormalIncome(int courseId);

    public double getCourseQuitIncome(int courseId);

    public List<Integer> getStudentCourseLine(String studentId);

    public Map<String, Object> getSiteTopOrgan();
}
