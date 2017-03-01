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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    // DONE
    @RequestMapping("/all")
    public String courseAllPage(Model model) {
        List<CourseEntity> courseEntityList = courseService.getCourseList("");
        model.addAttribute("courseList", MapUtil.beanListToMap(courseEntityList));

        return "course-all";
    }

    // DONE
    @RequestMapping("/detail")
    public String courseDetailPage(@RequestParam int id, HttpSession session, Model model){
        CourseEntity courseEntity = courseService.getCourse(id);
        List<StudentEntity> studentList = courseService.getJoinedStudent(id);

        String studentId = (String)session.getAttribute("user");
        OrderAccountEntity order = orderService.getOrder(studentId, id);

        List<Map> studentMapList = new ArrayList<>();
        for (StudentEntity studentEntity : studentList) {
            OrderAccountEntity orderAccountEntity = orderService.getOrder(studentId, id);
            Map<String, Object> studentMap = MapUtil.beanToMap(studentEntity);
            studentMap.put("score", orderAccountEntity.getScore());
            studentMap.put("joinTime", TimeUtil.timestampToDateString(orderAccountEntity.getCreatedAt()));
            studentMapList.add(studentMap);
        }

        model.addAttribute("course", MapUtil.beanToMap(courseEntity));
        model.addAttribute("isPaid", order != null && order.isPaid());
        model.addAttribute("studentList", studentMapList);

        return "course-detail";
    }

    //DONE
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

    // DONE
    @RequestMapping("/manage")
    public String courseManagePage(HttpSession session, Model model) {
        String organId = (String)session.getAttribute("user");
        List<CourseEntity> courseEntityList = courseService.getOrganCourseList(organId);
        List<Map> courseMapList = new ArrayList<>();
        for (CourseEntity courseEntity : courseEntityList) {
            courseMapList.add(MapUtil.beanToMap(courseEntity));
        }

        model.addAttribute("courseList", courseMapList);

        return "course-manage";
    }

    // DONE
    @RequestMapping("/manage/detail")
    public String courseManageDetailPage(@RequestParam int id, HttpSession session, Model model){
        CourseEntity courseEntity = courseService.getCourse(id);
        List<StudentEntity> studentList = courseService.getJoinedStudent(id);

        List<Map> studentMapList = new ArrayList<>();

        for (StudentEntity studentEntity : studentList) {
            String studentId = studentEntity.getId();
            OrderAccountEntity orderAccountEntity = orderService.getOrder(studentId, id);
            Map<String, Object> studentMap = MapUtil.beanToMap(studentEntity);
            studentMap.put("isPaid", orderAccountEntity.isPaid());
            studentMap.put("score", orderAccountEntity.getScore());
            studentMap.put("joinTime", TimeUtil.timestampToDateString(orderAccountEntity.getCreatedAt()));
            studentMapList.add(studentMap);
        }
        // TODO(加入成绩状态的属性)

        model.addAttribute("course", MapUtil.beanToMap(courseEntity));
        model.addAttribute("studentList", studentMapList);

        return "course-manage-detail";
    }

    // DONE
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String courseAddPage() {
        return "course-add";
    }

    // DONE
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String courseAdd(@ModelAttribute CourseEntity courseEntity, HttpSession session) {
        String organId = (String)session.getAttribute("user");
        courseEntity.setOrganId(organId);
        courseService.addCourse(courseEntity);

        return "redirect:/course/manage";
    }

    // DONE
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String courseEditPage(@RequestParam("id") int courseId, ModelMap model) {
        Map<String, Object> courseMap = MapUtil.beanToMap(courseService.getCourse(courseId));
        model.addAttribute("course", courseMap);

        return "course-add";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String courseEdit(@ModelAttribute CourseEntity courseEntity, HttpSession session) {
        courseEntity.setOrganId((String)session.getAttribute("user"));
        System.out.println(MapUtil.beanToMap(courseEntity));
        courseService.updateCourse(courseEntity);

        return "redirect:/course/edit?id="+courseEntity.getId();
    }

    // DONE
    @RequestMapping("/approve")
    public String approvePage(Model model) {
        List<CourseEntity> courseEntityList = courseService.getUnApproveCourseList();
        model.addAttribute("courseList", MapUtil.beanListToMap(courseEntityList));
        return "course-approve";
    }
}
