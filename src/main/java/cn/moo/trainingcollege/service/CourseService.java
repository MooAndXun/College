package cn.moo.trainingcollege.service;

import cn.moo.trainingcollege.entity.CourseEntity;
import cn.moo.trainingcollege.entity.OrderAccountEntity;
import cn.moo.trainingcollege.entity.StudentEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenmuen on 2017/2/10.
 */
public interface CourseService {
    public CourseEntity getCourse(int courseId);

    public List<CourseEntity> getCourseList(String keyword);

    public List<CourseEntity> getJoinedCourseList(String studentId);

    public List<CourseEntity> getStudentEndedCourseList(String studentId);

    public void addCourse(CourseEntity course);

    public void updateCourse(CourseEntity course);

    public List<CourseEntity> getOrganCourseList(String organId);

    public List<StudentEntity> getJoinedStudent(int courseId);

    /**
     * 获得尚未结束的课程列表
     * @param keyword
     * @return
     */
    public List<CourseEntity> getUnclosedCourseList(String keyword);

    public List<CourseEntity> search(String keyword, String studentId);

    /**
     * 获得未被审批的课程列表
     * @return
     */
    public List<CourseEntity> getUnApproveCourseList();

    /**
     * 获得已结束但未被结算的课程
     * @return
     */
    public List<CourseEntity> getUnSettledCourseList();

    public List<CourseEntity> getOrganOverCourseList(String organId);

    public List<CourseEntity> getOrganNoOverCourseList(String organId);

    public double getSettlementIncome(int courseId);
}
