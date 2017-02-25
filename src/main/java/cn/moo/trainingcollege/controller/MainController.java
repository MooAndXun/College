package cn.moo.trainingcollege.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by chenmuen on 2017/2/24.
 */
@Controller
@RequestMapping("/")
public class MainController {

    @RequestMapping("components")
    public String index(Model model){
        model.addAttribute("name", "陈沐恩");
        model.addAttribute("sex", "男");
        return "components";
    }
}
