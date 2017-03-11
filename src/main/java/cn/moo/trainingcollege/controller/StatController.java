package cn.moo.trainingcollege.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by chenmuen on 2017/3/11.
 */
@Controller
@RequestMapping("/stat")
public class StatController extends BaseController {
    @RequestMapping("/student")
    public String StudentStat() {
        return "student-stat";
    }
    @RequestMapping("/organ")
    public String OrganStat() {
        return "organ-stat";
    }
    @RequestMapping("/site")
    public String SiteStat() {
        return "site-stat";
    }
}
