package cn.moo.trainingcollege.dao_test;


import cn.moo.trainingcollege.BaseTest;
import cn.moo.trainingcollege.dao.UserDAO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Moo on 2017/1/13.
 */
public class UserDAOTest extends BaseTest {
    @Autowired
    UserDAO userDAO;

    @Test
    public void test() {
        System.out.println(userDAO.getById("chenmuen").getLogin());
    }
}
