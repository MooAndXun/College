package cn.moo.trainingcollege.service.impl;

import cn.moo.trainingcollege.dao.StudentDao;
import cn.moo.trainingcollege.entity.StudentEntity;
import cn.moo.trainingcollege.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by chenmuen on 2017/2/12.
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentDao studentDao;

    @Override
    public boolean checkLogin(String id, String password) {
        StudentEntity student = studentDao.getById(id);
        if(student==null) {
            return false;
        } else if (student.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addStudent(StudentEntity student) {
        studentDao.add(student);
    }

    @Override
    public StudentEntity getStudent(String studentId) {
        return studentDao.getById(studentId);
    }

    @Override
    public void updateStudent(StudentEntity student) {
        studentDao.update(student);
    }
}
