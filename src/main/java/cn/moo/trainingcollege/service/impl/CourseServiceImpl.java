package cn.moo.trainingcollege.service.impl;

import cn.moo.trainingcollege.dao.CourseDao;
import cn.moo.trainingcollege.entity.CourseEntity;
import cn.moo.trainingcollege.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by chenmuen on 2017/2/12.
 */
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseDao courseDao;

    @Override
    public CourseEntity getCourse(int courseId) {
        return courseDao.getByColumn("id", courseId);
    }

    @Override
    public List<CourseEntity> getCourseList(String keyword) {
        return courseDao.getListByLikeColumn("title", keyword);
    }

    @Override
    public void addCourse(CourseEntity course) {
        courseDao.add(course);
    }

    @Override
    public void updateCourse(CourseEntity course) {
        courseDao.update(course);
    }

    @Override
    public void approveApplication(int courseId, boolean isApproved) {
        CourseEntity course = courseDao.getByColumn("id", courseId);
        course.setState(isApproved?1:-1);
    }
}
