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

    public void addCourse(CourseEntity course);

    public void updateCourse(CourseEntity course);

    public List<CourseEntity> getOrganCourseList(String organId);

    public List<StudentEntity> getJoinedStudent(int courseId);
}
