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
    CourseService courseService;

    @Autowired
    OrderService orderService;

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

        String studentId = (String)session.getAttribute("userId");
        OrderAccountEntity order = orderService.getOrder(studentId, id);

        model.addAttribute("course", courseEntity);
        model.addAttribute("isPaid", order.isPaid());
        model.addAttribute("studentList", studentList);

        return "course-detail";
    }

}
