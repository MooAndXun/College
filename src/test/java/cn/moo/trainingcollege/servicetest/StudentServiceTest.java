package cn.moo.trainingcollege.servicetest;

import cn.moo.trainingcollege.BaseTest;
import cn.moo.trainingcollege.entity.StudentEntity;
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
        System.out.println(studentService.getStudent("S000002").getId());
    }

    @Test
    public void topupTest(){
        studentService.topUp("S000002",1000);
        System.out.println(studentService.getStudent("S000002").getExp());
    }
    @Test
    public void cancelTest(){
        studentService.cancel("S1");
    }

    @Test
    public void addStudent(){
        StudentEntity student = new StudentEntity();
        student.setPassword("111111");
        student.setName("test");
        student.setAccount("23232323444044");
        String id = studentService.addStudent(student);
        System.out.println(id);
    }
}
