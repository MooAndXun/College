package cn.moo.trainingcollege.controller;

import cn.moo.trainingcollege.entity.CourseEntity;
import cn.moo.trainingcollege.entity.OrderAccountEntity;
import cn.moo.trainingcollege.entity.StudentEntity;
import cn.moo.trainingcollege.service.CourseService;
import cn.moo.trainingcollege.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by chenmuen on 2017/2/27.
 */
@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private
    CourseService courseService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/all")
    public String courseAllPage(Model model) {
        List<CourseEntity> courseEntityList = courseService.getCourseList("");
        model.addAttribute("courseList", courseEntityList);

        return "course-all";
    }

    @RequestMapping("/detail")
    public String courseDetailPage(@RequestParam int id, HttpSession session, Model model){
        CourseEntity courseEntity = courseService.getCourse(id);
        List<StudentEntity> studentList = courseService.getJoinedStudent(id);

        String studentId = (String)session.getAttribute("user");
        OrderAccountEntity order = orderService.getOrder(studentId, id);

        model.addAttribute("course", courseEntity);
        model.addAttribute("isPaid", order != null && order.isPaid());
        model.addAttribute("studentList", studentList);

        return "course-detail";
    }

    @RequestMapping("/manage")
    public String courseManagePage(HttpSession session, Model model) {
        String organId = (String)session.getAttribute("user");
        List<CourseEntity> courseEntityList = courseService.getOrganCourseList(organId);
        System.out.println(courseEntityList.get(0).toString());
        model.addAttribute("courseList", courseEntityList);

        return "course-manage";
    }

    @RequestMapping("/manage/detail")
    public String courseManageDetailPage(@RequestParam int id, HttpSession session, Model model){
        CourseEntity courseEntity = courseService.getCourse(id);
        List<StudentEntity> studentList = courseService.getJoinedStudent(id);

        // TODO(加入成绩状态的属性)

        model.addAttribute("course", courseEntity);
        model.addAttribute("studentList", studentList);

        return "course-manage-detail";
    }

    @RequestMapping("/add")
    public String courseAddPage() {
        return "course-add";
    }

    @RequestMapping("/edit")
    public String courseEditPage(@RequestParam("id") int courseId, Model model) {
        CourseEntity courseEntity = courseService.getCourse(courseId);
        model.addAttribute("course", courseEntity);

        // TODO(日期格式的处理)

        return "course-add";
    }

    @RequestMapping("/approve")
    public String approvePage() {
        return null;
    }
}
