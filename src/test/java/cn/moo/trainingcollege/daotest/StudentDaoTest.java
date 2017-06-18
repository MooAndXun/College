package cn.moo.trainingcollege.daotest;

import cn.moo.trainingcollege.BaseTest;
import cn.moo.trainingcollege.dao.StudentDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by chenmuen on 2017/6/18.
 */
public class StudentDaoTest extends BaseTest {
    @Autowired
    StudentDao studentDao;

    @Test
    public void test() {
        System.out.println(studentDao.getSiteMemberYearToYearRate());
    }
}
