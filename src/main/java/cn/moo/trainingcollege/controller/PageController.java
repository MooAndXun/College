package cn.moo.trainingcollege.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * Created by chenmuen on 2017/2/24.
 */
@Controller
@RequestMapping("/page")
public class PageController extends BaseController {

    @RequestMapping("/components")
    public String components(Model model) {
        return "components";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        return "register";
    }

    @RequestMapping("/course/all")
    public String courseList(Model model) {
        List<Map> list = new ArrayList<>();
        Map<String, Object> course = new HashMap<>();
        course.put("id", 1);
        course.put("name", "演员的自我修养");
        course.put("startTime", "2015-10-10");
        course.put("endTime", "2015-10-10");
        course.put("teacher", "尹天仇");
        course.put("price", "360");
        list.add(course);

        model.addAttribute("courseList", list);
        return "course-all";
    }

    @RequestMapping("/course/manage")
    public String courseManage(Model model) {
        List<Map> list = new ArrayList<>();
        Map<String, Object> course = new HashMap<>();
        course.put("id", 1);
        course.put("state", 0);
        course.put("name", "演员的自我修养");
        course.put("startTime", "2015-10-10");
        course.put("endTime", "2015-10-10");
        course.put("teacher", "尹天仇");
        course.put("price", "360");
        list.add(course);

        model.addAttribute("courseList", list);
        return "course-manage";
    }

    @RequestMapping("/course/detail")
    public String courseDetail(Model model) {
        Map<String, String> course = new HashMap<>();
        course.put("id", "1");
        course.put("name", "演员的自我修养");
        course.put("startTime", "2015-10-10");
        course.put("endTime", "2015-10-11");
        course.put("teacher", "尹天仇");
        course.put("price", "360");
        course.put("description", "呵呵");

        model.addAttribute("course", course);
        return "course-detail";
    }

    @RequestMapping("/user/info")
    public String userInfo(Model model) {
        Map<String, Object> student = new HashMap<>();
        student.put("id", "S0000001");
        student.put("name", "史蒂夫·罗杰斯");
        student.put("account", "A00000001");
        student.put("level", "2");
        student.put("point", "2000");

        model.addAttribute("student", student);
        return "user-info";
    }

    @RequestMapping("/course/manage/detail")
    public String courseManageDetail(Model model) {
        Map<String, String> course = new HashMap<>();
        course.put("id", "1");
        course.put("name", "演员的自我修养");
        course.put("startTime", "2015-10-10");
        course.put("endTime", "2015-10-11");
        course.put("teacher", "尹天仇");
        course.put("price", "360");
        course.put("description", "呵呵");

        List<Object> studentList = new ArrayList<>();
        Map<String, Object> student = new HashMap<>();
        student.put("id", "S0000001");
        student.put("name", "史蒂夫·罗杰斯");
        student.put("joinTime", "2015-10-10");
        student.put("score", -1);

        Map<String, Object> student2 = new HashMap<>();
        student2.put("id", "S0000002");
        student2.put("name", "托尼·斯塔克");
        student2.put("joinTime", "2015-12-10");
        student2.put("score", 90);

        studentList.add(student);
        studentList.add(student2);

        model.addAttribute("studentList", studentList);
        model.addAttribute("course", course);
        return "course-manage-detail";
    }
}
