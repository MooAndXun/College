package cn.moo.trainingcollege.servicetest;

import cn.moo.trainingcollege.BaseTest;
import cn.moo.trainingcollege.service.StudentService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by chenmuen on 2017/2/12.
 */
public class StudentServiceTest extends BaseTest{
    @Autowired
    StudentService studentService;

    @Test
    public void getStudentTest(){
        System.out.println(studentService.getStudent("S1").getId());
    }

}
