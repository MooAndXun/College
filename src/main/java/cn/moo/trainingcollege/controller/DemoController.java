package cn.moo.trainingcollege.controller;

import cn.moo.trainingcollege.entity.OrganizationEntity;
import cn.moo.trainingcollege.entity.StudentEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小春 on 2017/2/25.
 */

@Controller
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping("/page")
    public String demo(){
        return "demo";
    }

    @RequestMapping("/json")
    @ResponseBody
    public List<OrganizationEntity> getJson(){
        OrganizationEntity entity = new OrganizationEntity();
        entity.setName("name");
        entity.setPassword("123456");
        entity.setId("O0000002");
        entity.setBalance(0);
        List<OrganizationEntity> list = new ArrayList<OrganizationEntity>();
        list.add(entity);
        list.add(entity);
        list.add(entity);
        list.add(entity);
        list.add(entity);
        list.add(entity);
        return list;

    }
}
