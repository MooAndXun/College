package cn.moo.trainingcollege.controller;

import cn.moo.trainingcollege.entity.CourseEntity;
import cn.moo.trainingcollege.entity.OrderAccountEntity;
import cn.moo.trainingcollege.entity.OrderCashEntity;
import cn.moo.trainingcollege.entity.StudentEntity;
import cn.moo.trainingcollege.service.CourseService;
import cn.moo.trainingcollege.service.ManagerService;
import cn.moo.trainingcollege.service.OrderService;
import cn.moo.trainingcollege.service.StudentService;
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

    @Autowired
    private StudentService studentService;

    /*------------- Page -------------*/
    // DONE
    @RequestMapping("/all")
    public String courseAllPage(@RequestParam(required = false) String keyword, Model model, HttpSession session) {
        String userId = getUserId(session);
        if (keyword == null) {
            model.addAttribute("courseList", MapUtil.beanListToMap(courseService.search("", userId)));
        } else {
            model.addAttribute("courseList", MapUtil.beanListToMap(courseService.search(keyword, userId)));
        }
        return "course-all";
    }

    // DONE
    @RequestMapping("/detail")
    public String courseDetailPage(@RequestParam int id, HttpSession session, Model model) {
        CourseEntity courseEntity = courseService.getCourse(id);
        String userId = (String) session.getAttribute("user");
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
        String userId = (String) session.getAttribute("user");
        List<CourseEntity> courseEntityList = courseService.getJoinedCourseList(userId);
        List<Map> courseMapList = new ArrayList<>();
        for (CourseEntity courseEntity :
                courseEntityList) {
            int courseId = courseEntity.getId();
            OrderAccountEntity orderAccountEntity = orderService.getOrder(userId, courseId);

            Map<String, Object> courseMap = MapUtil.beanToMap(courseEntity);
            courseMap.put("isPaid", orderAccountEntity.isPaid());
            courseMap.put("isOver", TimeUtil.dateStringToTimestamp(courseEntity.getEndTime()).before(TimeUtil.getCurrentTime()));
            courseMap.put("orderId", orderAccountEntity.getId());
            courseMap.put("price", orderAccountEntity.getPrice());
            courseMap.put("score", orderAccountEntity.getScore());
            courseMapList.add(courseMap);
        }

        model.addAttribute("courseList", courseMapList);
        return "course-mine";
    }

    //DONE
    @RequestMapping("/over")
    public String courseOverPage(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("user");
        List<CourseEntity> courseEntityList = courseService.getStudentEndedCourseList(userId);

        List<Map> courseMapList = new ArrayList<>();
        for (CourseEntity courseEntity :
                courseEntityList) {
            int courseId = courseEntity.getId();
            OrderAccountEntity orderAccountEntity = orderService.getQuitOrCancelOrder(userId, courseId);

            Map<String, Object> courseMap = MapUtil.beanToMap(courseEntity);
            courseMap.put("isCancel", orderAccountEntity.isCancel());
            courseMap.put("isQuit", orderAccountEntity.getQuitState() == 1);
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
        String organId = (String) session.getAttribute("user");
        List<CourseEntity> courseEntityList = courseService.getOrganNoOverCourseList(organId);
        List<Map> courseMapList = new ArrayList<>();
        for (CourseEntity courseEntity : courseEntityList) {
            courseMapList.add(MapUtil.beanToMap(courseEntity));
        }

        model.addAttribute("courseList", courseMapList);

        return "course-manage";
    }

    // DONE
    @RequestMapping("/manage/over")
    public String courseManageOverPage(HttpSession session, Model model) {
        String organId = (String) session.getAttribute("user");
        List<CourseEntity> courseEntityList = courseService.getOrganOverCourseList(organId);
        List<Map> courseMapList = new ArrayList<>();
        for (CourseEntity courseEntity : courseEntityList) {
            Map<String, Object> map = MapUtil.beanToMap(courseEntity);
            if(courseEntity.isSettled()){
                map.put("income", courseService.getSettlementIncome(courseEntity.getId()));
            }
            courseMapList.add(map);
        }
        model.addAttribute("courseList", courseMapList);

        return "course-manage-over";
    }

    // DONE
    @RequestMapping("/manage/detail")
    public String courseManageDetailPage(@RequestParam int id, Model model) {
        CourseEntity courseEntity = courseService.getCourse(id);
        List<Map> studentMapList = getJoinedStudentList(id);
        List<Map> nomMemberMapList = getJoinedNonMemberList(id);

        model.addAttribute("course", MapUtil.beanToMap(courseEntity));
        model.addAttribute("studentList", studentMapList);
        model.addAttribute("nonMemberList", nomMemberMapList);

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

    // DONE
    @RequestMapping("/join")
    public String joinPage(HttpSession session, Model model) {
        String organId = getUserId(session);
        List<CourseEntity> courseEntityList = courseService.getOrganCourseList(organId);
        model.addAttribute("courseList", MapUtil.beanListToMap(courseEntityList));
        return "course-join";
    }

    /*------------- Action -------------*/
    // DONE
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String courseAdd(@ModelAttribute CourseEntity courseEntity, HttpSession session) {
        String organId = (String) session.getAttribute("user");
        courseEntity.setOrganId(organId);
        courseService.addCourse(courseEntity);

        return "redirect:/course/manage";
    }

    // DONE
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String courseEdit(@ModelAttribute CourseEntity courseEntity, HttpSession session) {
        CourseEntity originEntity = courseService.getCourse(courseEntity.getId());
        originEntity.setName(courseEntity.getName());
        originEntity.setStartTime(courseEntity.getStartTime());
        originEntity.setEndTime(courseEntity.getEndTime());
        originEntity.setDescription(courseEntity.getDescription());
        originEntity.setTeacher(courseEntity.getTeacher());
        originEntity.setPrice(courseEntity.getPrice());
        courseService.updateCourse(originEntity);

        return "redirect:/course/edit?id=" + courseEntity.getId();
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
        StudentEntity studentEntity = studentService.getStudent(userId);
        if(studentEntity.getState()==0||studentEntity.getState()==2) {
            setErrorMessege(redirectAttributes, "账户未激活或被冻结，请充值以激活账户");
            return "redirect:/user/topup";
        } else {
            orderService.orderCourse(userId, id);
            setMessege(redirectAttributes, "预订成功");
            return "redirect:/course/mine";
        }
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public String pay(@RequestParam int id, RedirectAttributes redirectAttributes, HttpSession session) {
        String userId = getUserId(session);
        StudentEntity studentEntity = studentService.getStudent(userId);
        OrderAccountEntity orderAccountEntity = orderService.getOrder(id);
        if(studentEntity.getBalance()-orderAccountEntity.getPrice()>=0) {
            orderService.pay(id);
            setMessege(redirectAttributes, "支付成功");
            return "redirect:/course/mine";
        } else {
            setErrorMessege(redirectAttributes, "余额不足");
            return "redirect:/user/topup";
        }
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public String cancel(@RequestParam int id, RedirectAttributes redirectAttributes) {
        orderService.cancelCourse(id);
        setMessege(redirectAttributes, "退订成功");
        return "redirect:/course/mine";
    }

    @RequestMapping(value = "/quit", method = RequestMethod.POST)
    public String quit(@RequestParam int id, @RequestParam int courseId, RedirectAttributes redirectAttributes) {
        orderService.quitCourse(id);
        setMessege(redirectAttributes, "退课成功");
        if(courseId==-1) {
            return "redirect:/course/mine";
        } else {
            return "redirect:/course/manage/detail?id="+courseId;
        }
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public String join(@RequestParam(required = false) String id,
                       @RequestParam(required = false) String name,
                       @RequestParam int courseId,
                       @RequestParam String type,
                       RedirectAttributes redirectAttributes) {
        CourseEntity courseEntity = courseService.getCourse(courseId);
        if (TimeUtil.isBeforeToday(courseEntity.getEndTime())) {
            redirectAttributes.addFlashAttribute("errorMessage", "该课程已过期");
        } else {
            switch (type) {
                case "member":
                    System.out.println(studentService);
                    StudentEntity studentEntity = studentService.getStudent(id);
                    if (studentEntity == null) {
                        redirectAttributes.addFlashAttribute("errorMessage", "该会员不存在");

                    } else if (orderService.getOrder(id, courseId) != null) {
                        redirectAttributes.addFlashAttribute("errorMessage", "会员已加入该课程");
                    } else {
                        orderService.orderCourse(id, courseId);
                        redirectAttributes.addFlashAttribute("message", "加入课程成功");

                    }
                    break;
                case "non-member":
                    orderService.orderCourseCash(name, courseId);
                    redirectAttributes.addFlashAttribute("message", "加入课程成功");
                    break;
                default:
                    redirectAttributes.addFlashAttribute("errorMessage", "参数错误");
                    break;
            }
        }

        return "redirect:/course/join";
    }

    @RequestMapping(value = "score", method = RequestMethod.POST)
    public String score(@RequestParam int orderId,
                        @RequestParam int courseId,
                        @RequestParam int score,
                        RedirectAttributes redirectAttributes) {
        orderService.score(orderId, score);
        redirectAttributes.addFlashAttribute("message", "登记成绩成功");
        return "redirect: /course/manage/detail?id="+courseId;
    }


    /*------------- Other -------------*/
    public List<Map> getJoinedStudentList(int id) {
        List<StudentEntity> studentList = courseService.getJoinedStudent(id);
        List<Map> studentMapList = new ArrayList<>();
        for (StudentEntity studentEntity : studentList) {
            String studentId = studentEntity.getId();
            OrderAccountEntity orderAccountEntity = orderService.getOrder(studentId, id);
            if (orderAccountEntity == null) {
                studentEntity = null;
            } else {
                Map<String, Object> studentMap = MapUtil.beanToMap(studentEntity);
                studentMap.put("orderId", orderAccountEntity.getId());
                studentMap.put("score", orderAccountEntity.getScore());
                studentMap.put("joinTime", TimeUtil.timestampToDateString(orderAccountEntity.getCreatedAt()));
                studentMapList.add(studentMap);
            }
        }
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i) == null) {
                studentList.remove(i);
                i--;
            }
        }
        return studentMapList;
    }

    public List<Map> getJoinedNonMemberList(int id) {
        List<OrderCashEntity> orderCashEntities = orderService.getOrderCashEntityList(id);
        List<Map> studentMapList = new ArrayList<>();

        System.out.println(orderCashEntities);
        if(orderCashEntities!=null) {
            for (OrderCashEntity orderCashEntity : orderCashEntities) {
                Map<String, Object> studentMap = MapUtil.beanToMap(orderCashEntity);
                studentMap.put("name", orderCashEntity.getStudentName());
                studentMap.put("orderId", studentMap.get("id"));
                studentMap.put("joinTime", TimeUtil.timestampToDateString(orderCashEntity.getCreatedAt()));
                studentMapList.add(studentMap);
            }
        }

        return studentMapList;
    }
}
