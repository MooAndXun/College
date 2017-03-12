package cn.moo.trainingcollege.servicetest;

import cn.moo.trainingcollege.BaseTest;
import cn.moo.trainingcollege.service.StatService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

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

    @Test
    public void getStudentAverageScore(){
        int score = statService.getStudentAverageScore("S000002");
        System.out.println(score);
    }

    @Test
    public  void getStudentScoreDistribution(){
        List<Integer> list = statService.getStudentScoreDistribution("S000002");
        for (Integer i:list) {
            System.out.println(i);
        }
    }

    @Test
    public void getOrganCourseCount(){
        System.out.println(statService.getOrganCourseCount("O000002"));
    }

    @Test
    public void getOrganMemberCount(){
        System.out.println(statService.getOrganMemberCount("O000001"));
    }

    @Test
    public void getOrganIncome(){
        System.out.println(statService.getOrganIncome("O000001"));
    }

    @Test
    public void getSiteIncome(){
        System.out.println(statService.getSiteIncome());
    }

    @Test
    public void getOrganIncomeLine(){
        List<Double> list = statService.getOrganIncomeLine("O000001");
        for (Double num:list) {
            System.out.println(num);
        }
    }

    @Test
    public void getOrganMemberLine(){
        List<Integer> list = statService.getOrganMemberLine("O000001");
        for (Integer num:list) {
            System.out.println(num);
        }
    }

    @Test
    public void getOrganTopCourse(){
        Map<String,Object> map = statService.getOrganTopCourse("O000002");
        List<String> list = (List<String>)map.get("names");
        for (String name:list) {
            System.out.println(name);
        }
    }
}


