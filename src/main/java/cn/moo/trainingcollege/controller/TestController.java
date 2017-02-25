package cn.moo.trainingcollege.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 小春 on 2017/2/25.
 */

@Controller
@RequestMapping("/student")
public class TestController {

    @RequestMapping("/test")
    public String demo(){
        return "index";
    }
}
