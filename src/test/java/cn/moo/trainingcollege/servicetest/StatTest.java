package cn.moo.trainingcollege.servicetest;

import cn.moo.trainingcollege.BaseTest;
import cn.moo.trainingcollege.service.StatService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by 小春 on 2017/3/11.
 */
public class StatTest extends BaseTest {
    @Autowired
    StatService statService;
    @Test
    public void getStudentCourseCount(){
        int count  = statService.getStudentCourseCount("S000002");
        System.out.println(count);
    }
}
