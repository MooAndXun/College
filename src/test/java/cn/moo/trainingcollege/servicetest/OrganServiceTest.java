package cn.moo.trainingcollege.servicetest;

import cn.moo.trainingcollege.BaseTest;
import cn.moo.trainingcollege.entity.OrganizationEntity;
import cn.moo.trainingcollege.service.OrganService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by 小春 on 2017/2/20.
 */
public class OrganServiceTest extends BaseTest {
    @Autowired
    OrganService organService;

    @Test
    public void addOrganTest(){
        OrganizationEntity organ = new OrganizationEntity();
        organ.setPassword("123456");
        organ.setName("英语教育");
        organService.addOrgan(organ);
    }
}
