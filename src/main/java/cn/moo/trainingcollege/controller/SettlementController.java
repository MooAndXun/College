package cn.moo.trainingcollege.controller;

import cn.moo.trainingcollege.entity.CourseEntity;
import cn.moo.trainingcollege.service.CourseService;
import cn.moo.trainingcollege.service.SettlementService;
import cn.moo.trainingcollege.utils.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by chenmuen on 2017/3/11.
 */
@Controller
@RequestMapping("/settlement")
public class SettlementController extends BaseController {
    @Autowired
    CourseService courseService;

    @Autowired
    SettlementService settlementService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String settlementPage(Model model) {
        List<CourseEntity> courseEntityList = courseService.getUnSettledCourseList();
        model.addAttribute("courseList", MapUtil.beanListToMap(courseEntityList));
        return "settlement";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String settlement(@RequestParam("id") int courseId, Model model) {
        settlementService.settlement(courseId);
        return "settlement";
    }
}
