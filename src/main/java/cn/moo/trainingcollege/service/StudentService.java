package cn.moo.trainingcollege.service;

import cn.moo.trainingcollege.entity.StudentEntity;

/**
 * Created by chenmuen on 2017/1/27.
 */
public interface StudentService {
    public boolean checkLogin(String id, String password);

    public boolean addStudent();

    public StudentEntity getStudent(String studentId);

    public boolean updateStudent();

}
