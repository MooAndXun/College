package cn.moo.trainingcollege.controller;

import cn.moo.trainingcollege.entity.CourseEntity;
import cn.moo.trainingcollege.entity.OrderAccountEntity;
import cn.moo.trainingcollege.entity.StudentEntity;
import cn.moo.trainingcollege.service.CourseService;
import cn.moo.trainingcollege.service.OrderService;
import cn.moo.trainingcollege.utils.MapUtil;
import cn.moo.trainingcollege.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

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

    @RequestMapping("/mine")
    public String courseMinePage(HttpSession session, Model model) {
        String userId = (String)session.getAttribute("user");
        List<CourseEntity> courseEntityList = courseService.getJoinedCourseList(userId);

        List<Map> courseMapList = new ArrayList<>();
        for (CourseEntity courseEntity:
             courseEntityList) {
            int courseId = courseEntity.getId();
            OrderAccountEntity orderAccountEntity = orderService.getOrder(userId, courseId);

            Map<String, Object> courseMap = MapUtil.beanToMap(courseEntity);
            courseMap.put("isPaid", orderAccountEntity.isPaid());
            courseMapList.add(courseMap);
        }

        model.addAttribute("courseList", courseMapList);
        return "course-mine";
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

        for (StudentEntity studentEntity : studentList) {
            String studentId = studentEntity.getId();
        }
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
        Map<String, Object> courseMap = MapUtil.beanToMap(courseService.getCourse(courseId));
        courseMap.replace("startTime", TimeUtil.timestampToDateString((Timestamp)courseMap.get("startTime")));
        courseMap.replace("endTime", TimeUtil.timestampToDateString((Timestamp)courseMap.get("endTime")));

        model.addAttribute("course", courseMap);


        return "course-add";
    }

    @RequestMapping("/approve")
    public String approvePage(Model model) {
        List<CourseEntity> courseEntityList = courseService.getUnApproveCourseList();
        model.addAttribute("courseList", courseEntityList);
        return "approve-course";
    }
}
