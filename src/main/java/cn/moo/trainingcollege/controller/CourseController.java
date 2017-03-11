package cn.moo.trainingcollege.controller;

import cn.moo.trainingcollege.entity.CourseEntity;
import cn.moo.trainingcollege.entity.OrderAccountEntity;
import cn.moo.trainingcollege.entity.StudentEntity;
import cn.moo.trainingcollege.service.CourseService;
import cn.moo.trainingcollege.service.ManagerService;
import cn.moo.trainingcollege.service.OrderService;
import cn.moo.trainingcollege.utils.MapUtil;
import cn.moo.trainingcollege.utils.TimeUtil;
import com.sun.deploy.util.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.ArrayUtils;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by chenmuen on 2017/2/27.
 */
@Controller
@RequestMapping("/course")
public class CourseController extends BaseController {
    @Autowired
    private
    CourseService courseService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ManagerService managerService;

    /*------------- Page -------------*/
    // DONE
    @RequestMapping("/all")
    public String courseAllPage(@RequestParam(required = false)String keywords, Model model, HttpSession session) {
        String userId = getUserId(session);
        if(keywords==null) {
            model.addAttribute("courseList", MapUtil.beanListToMap(courseService.search("", userId)));
        } else {
            model.addAttribute("courseList", MapUtil.beanListToMap(courseService.search(keywords, userId)));
        }
        return "course-all";
    }

    // DONE
    @RequestMapping("/detail")
    public String courseDetailPage(@RequestParam int id, HttpSession session, Model model){
        CourseEntity courseEntity = courseService.getCourse(id);
        String userId = (String)session.getAttribute("user");
        OrderAccountEntity order = orderService.getOrder(userId, id);

        List<Map> studentMapList = getJoinedStudentList(id);

        model.addAttribute("course", MapUtil.beanToMap(courseEntity));
        model.addAttribute("isJoined", order != null);
        model.addAttribute("isPaid", order != null && order.isPaid());
        model.addAttribute("orderId", order != null ? order.getId() : -1);
        model.addAttribute("price", order != null ? order.getPrice() : 0);
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
            courseMap.put("isOver", TimeUtil.dateStringToTimestamp(courseEntity.getEndTime()).before(TimeUtil.getCurrentTime()));
            courseMap.put("orderId", orderAccountEntity.getId());
            courseMap.put("price", orderAccountEntity.getPrice());
            courseMapList.add(courseMap);
        }

        model.addAttribute("courseList", courseMapList);
        return "course-mine";
    }

    //DONE
    @RequestMapping("/over")
    public String courseOverPage(HttpSession session, Model model) {
        String userId = (String)session.getAttribute("user");
        List<CourseEntity> courseEntityList = courseService.getStudentEndedCourseList(userId);

        List<Map> courseMapList = new ArrayList<>();
        for (CourseEntity courseEntity:
                courseEntityList) {
            int courseId = courseEntity.getId();
            OrderAccountEntity orderAccountEntity = orderService.getQuitOrCancelOrder(userId, courseId);

            Map<String, Object> courseMap = MapUtil.beanToMap(courseEntity);
            courseMap.put("isCancel", orderAccountEntity.isCancel());
            courseMap.put("isQuit", orderAccountEntity.getQuitState()==1);
            courseMap.put("orderId", orderAccountEntity.getId());
            courseMap.put("price", orderAccountEntity.getPrice());
            courseMapList.add(courseMap);
        }

        model.addAttribute("courseList", courseMapList);
        return "course-over";
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
    public String courseManageDetailPage(@RequestParam int id, Model model){
        CourseEntity courseEntity = courseService.getCourse(id);

        List<Map> studentMapList = getJoinedStudentList(id);
        model.addAttribute("course", MapUtil.beanToMap(courseEntity));
        model.addAttribute("studentList", studentMapList);

        return "course-manage-detail";
    }

    // DONE
    @RequestMapping("/approve")
    public String approvePage(Model model) {
        List<CourseEntity> courseEntityList = courseService.getUnApproveCourseList();
        model.addAttribute("courseList", MapUtil.beanListToMap(courseEntityList));
        return "course-approve";
    }

    // DONE
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String courseAddPage() {
        return "course-add";
    }



    // DONE
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String courseEditPage(@RequestParam("id") int courseId, ModelMap model) {
        Map<String, Object> courseMap = MapUtil.beanToMap(courseService.getCourse(courseId));
        model.addAttribute("course", courseMap);

        return "course-add";
    }

    /*------------- Action -------------*/
    // DONE
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String courseAdd(@ModelAttribute CourseEntity courseEntity, HttpSession session) {
        String organId = (String)session.getAttribute("user");
        courseEntity.setOrganId(organId);
        courseService.addCourse(courseEntity);

        return "redirect:/course/manage";
    }

    // DONE
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String courseEdit(@ModelAttribute CourseEntity courseEntity, HttpSession session) {
        courseEntity.setOrganId((String)session.getAttribute("user"));
        System.out.println(MapUtil.beanToMap(courseEntity));
        courseService.updateCourse(courseEntity);

        return "redirect:/course/edit?id="+courseEntity.getId();
    }


    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    public String approve(@RequestParam int id, HttpSession session, RedirectAttributes redirectAttributes) {
        managerService.approve(id, true);
        setMessege(redirectAttributes, "审批成功");
        return "redirect:/course/approve";
    }

    @RequestMapping(value = "/reserve", method = RequestMethod.POST)
    public String reserve(@RequestParam int id, HttpSession session, RedirectAttributes redirectAttributes) {
        String userId = getUserId(session);
        orderService.orderCourse(userId, id);

        setMessege(redirectAttributes, "预订成功");
        return "redirect:/course/all";
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public String pay(@RequestParam int id, RedirectAttributes redirectAttributes) {
        orderService.pay(id);
        setMessege(redirectAttributes, "支付成功");
        return "redirect:/course/mine";
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public String cancel(@RequestParam int id, RedirectAttributes redirectAttributes) {
        orderService.cancelCourse(id);
        setMessege(redirectAttributes, "退订成功");
        return "redirect:/course/mine";
    }

    @RequestMapping(value = "/quit", method = RequestMethod.POST)
    public String quit(@RequestParam int id,  RedirectAttributes redirectAttributes) {
        orderService.quitCourse(id);
        setMessege(redirectAttributes, "退课成功");
        return "redirect:/course/mine";
    }

    /*------------- Other -------------*/
    public List<Map> getJoinedStudentList(int id) {
        List<StudentEntity> studentList = courseService.getJoinedStudent(id);
        List<Map> studentMapList = new ArrayList<>();
        for (StudentEntity studentEntity : studentList) {
            String studentId = studentEntity.getId();
            OrderAccountEntity orderAccountEntity = orderService.getOrder(studentId, id);
            if(orderAccountEntity==null) {
                studentEntity = null;
            } else {
                Map<String, Object> studentMap = MapUtil.beanToMap(studentEntity);
                studentMap.put("score", orderAccountEntity.getScore());
                studentMap.put("joinTime", TimeUtil.timestampToDateString(orderAccountEntity.getCreatedAt()));
                studentMapList.add(studentMap);
            }
        }
        for (int i = 0; i < studentList.size(); i++) {
            if(studentList.get(i)==null) {
                studentList.remove(i);
                i--;
            }
        }
        return studentMapList;
    }
}
