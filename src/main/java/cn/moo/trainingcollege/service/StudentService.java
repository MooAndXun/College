package cn.moo.trainingcollege.service;

import cn.moo.trainingcollege.entity.StudentEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by chenmuen on 2017/1/27.
 */
public interface StudentService {
    public boolean checkLogin(String id, String password);

    /**
     * 注册学生用户
     * @param student
     * @return 用户ID
     */
    public String addStudent(StudentEntity student);

    public StudentEntity getStudent(String studentId);

    public void updateStudent(StudentEntity student);

    public void topUp(String studentId, int money);

    public void cancel(String studentId);

    public void exchange(String studentId, int point);
}
