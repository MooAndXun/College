package cn.moo.trainingcollege.servicetest;

import cn.moo.trainingcollege.BaseTest;
import cn.moo.trainingcollege.entity.CourseEntity;
import cn.moo.trainingcollege.service.CourseService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by 小春 on 2017/2/21.
 */
public class CourseTest extends BaseTest {
    @Autowired
    CourseService courseService;

    @Test
    public void getJoinedCourseListTest(){
        List<CourseEntity> list = courseService.getJoinedCourseList("S000002");
        System.out.println(list.size());
    }

    @Test
    public void getListTest(){
        System.out.println(courseService.getJoinedStudent(1).size());
        System.out.println(courseService.getOrganCourseList("O000002").get(0).toString());
    }
}
