package cn.moo.trainingcollege.dao;

import cn.moo.trainingcollege.entity.OrderAccountEntity;
import cn.moo.trainingcollege.utils.StatTimeType;

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

    // New
    public Map<String, Object> getSiteOrderRank(StatTimeType statTimeType);

    public Map<String, Object> getSiteIncomeRank(StatTimeType statTimeType);

    public Map<String, Object> getSiteQuitRank(StatTimeType statTimeType);

    public Map<String, Object> getSiteSatisfactionRank(StatTimeType statTimeType);

    public List<Double> getSiteQuitRate(StatTimeType statTimeType);

    public List<Double> getSiteSatisfactionRate(StatTimeType statTimeType);

    public Map<String, Object> getTeacherIncomeRank(StatTimeType statTimeType);

    public List<Double> getIncomeQuota(StatTimeType statTimeType, String organId);
}
