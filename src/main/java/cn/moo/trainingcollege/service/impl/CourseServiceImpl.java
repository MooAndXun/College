package cn.moo.trainingcollege.service.impl;

import cn.moo.trainingcollege.dao.CourseDao;
import cn.moo.trainingcollege.dao.StudentDao;
import cn.moo.trainingcollege.entity.CourseEntity;
import cn.moo.trainingcollege.entity.StudentEntity;
import cn.moo.trainingcollege.service.CourseService;
import cn.moo.trainingcollege.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenmuen on 2017/2/12.
 */
@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseDao courseDao;
    @Autowired
    StudentDao studentDao;

    @Override
    public CourseEntity getCourse(int courseId) {
        return courseDao.getByColumn("id", courseId);
    }

    @Override
    public List<CourseEntity> getCourseList(String keyword) {
        if(keyword==null||keyword.equals("")) {
            return courseDao.getAll();
        } else {
            List<CourseEntity> result = courseDao.getListByLikeColumn("name", keyword);
            if(result == null){
                result = new ArrayList<CourseEntity>();
            }
            return result;
        }
    }

    @Override
    public List<CourseEntity> getJoinedCourseList(String studentId) {
        String sql = "SELECT * FROM `course` WHERE id IN (SELECT course_id FROM `order_account` WHERE student_id = '"+studentId+"')";
        return (List<CourseEntity>) courseDao.doSqlQuery(sql);

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
    public List<CourseEntity> getOrganCourseList(String organId) {
        String sql = "SELECT * FROM `course` WHERE organ_id = '"+organId+"'";
        return (List<CourseEntity>) courseDao.doSqlQuery(sql);
    }

    @Override
    public List<StudentEntity> getJoinedStudent(int courseId) {
        String sql = "SELECT * FROM `student` WHERE id IN (SELECT student_id from order_account WHERE course_id = "+courseId+")";
        return (List<StudentEntity>) studentDao.doSqlQuery(sql);
    }

    @Override
    public List<CourseEntity> getUnclosedCourseList(String keyword) {
        List<CourseEntity> list = getCourseList(keyword);
        List<CourseEntity> result = new ArrayList<CourseEntity>();
        for (CourseEntity course:list) {
            if(TimeUtil.dateStringToTimestamp(course.getEndTime()).after(TimeUtil.getCurrentTime())){
                result.add(course);
            }
        }
        return result;
    }

    @Override
    public List<CourseEntity> getUnApproveCourseList() {
        return courseDao.getListByColumn("state",0);
    }
}
