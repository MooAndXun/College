package cn.moo.trainingcollege.servicetest;

import cn.moo.trainingcollege.BaseTest;
import cn.moo.trainingcollege.service.ManagerService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by 小春 on 2017/2/24.
 */
public class ManagerTest extends BaseTest
{
    @Autowired
    ManagerService managerService;
    @Test
    public void quitTest(){
        managerService.approveQuit(2,true);
    }
}
