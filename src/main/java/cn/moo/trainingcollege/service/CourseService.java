package cn.moo.trainingcollege.service;

import cn.moo.trainingcollege.entity.CourseEntity;

/**
 * Created by chenmuen on 2017/2/10.
 */
public interface CourseService {
    public CourseEntity getCourse(int courseId);

    public boolean addCourse();

    public boolean updateCourse();

    public boolean score(String studentId, int courseId, int score);

}
