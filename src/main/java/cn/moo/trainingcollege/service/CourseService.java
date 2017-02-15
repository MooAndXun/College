package cn.moo.trainingcollege.service;

import cn.moo.trainingcollege.entity.CourseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenmuen on 2017/2/10.
 */
@Service
public interface CourseService {
    public CourseEntity getCourse(int courseId);

    public List<CourseEntity> getCourseList(String keyword);

    public void addCourse(CourseEntity course);

    public void updateCourse(CourseEntity course);

    public void approveApplication(int courseId, boolean isApproved);
}
