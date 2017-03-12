package cn.moo.trainingcollege.controller;

import cn.moo.trainingcollege.entity.OrganizationEntity;
import cn.moo.trainingcollege.entity.StudentEntity;
import cn.moo.trainingcollege.service.ManagerService;
import cn.moo.trainingcollege.service.OrganService;
import cn.moo.trainingcollege.service.StudentService;
import cn.moo.trainingcollege.utils.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by chenmuen on 2017/3/1.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    StudentService studentService;
    @Autowired
    OrganService organService;
    @Autowired
    ManagerService managerService;

    /*------------- Page -------------*/
    @RequestMapping("/info")
    public String userInfoPage(HttpSession session, Model model){
        String userId = (String)session.getAttribute("user");
        int userType = (int)session.getAttribute("userType");
        String pageStr = "";

        switch (userType) {
            case 0:
                pageStr = "user-info";
                StudentEntity studentEntity = studentService.getStudent(userId);
                model.addAttribute("student", MapUtil.beanToMap(studentEntity));
                break;
            case 1:
                pageStr = "organ-info";
                OrganizationEntity organizationEntity = organService.getOrgan(userId);
                model.addAttribute("organ", MapUtil.beanToMap(organizationEntity));
                break;
            case 2:

                break;
        }

        return pageStr;
    }

    @RequestMapping(value="edit")
    public String userEditPage(HttpSession session, Model model) {
        String userId = (String)session.getAttribute("user");
        int userType = (int)session.getAttribute("userType");
        String pageStr = "";

        switch (userType) {
            case 0:
                pageStr = "user-edit";
                StudentEntity studentEntity = studentService.getStudent(userId);
                model.addAttribute("student", MapUtil.beanToMap(studentEntity));
                break;
            case 1:
                pageStr = "organ-edit";
                OrganizationEntity organizationEntity = organService.getOrgan(userId);
                model.addAttribute("organ", MapUtil.beanToMap(organizationEntity));
                break;
            case 2:

                break;
        }

        return pageStr;
    }

    @RequestMapping(value = "/topup", method = RequestMethod.GET)
    public String topUpPage() {
        return "topup";
    }

    @RequestMapping(value = "/exchange", method = RequestMethod.GET)
    public String exchangePage() {
        return "exchange";
    }


    /*------------- Action -------------*/
    @RequestMapping(value = "/topup", method = RequestMethod.POST)
    public String topUp(@RequestParam int money, HttpSession session, RedirectAttributes redirectAttributes) {
        String userId = (String)session.getAttribute("user");
        int userType = (int)session.getAttribute("userType");

        if(userType==0) {
            studentService.topUp(userId, money);
            setMessege(redirectAttributes, "充值成功");
            return "redirect:/user/info";
        } else {
            setMessege(redirectAttributes, "没有权限，请登录");
            return "redirect:/login";
        }

    }

    @RequestMapping(value="edit/student", method = RequestMethod.POST)
    public String userEdit(StudentEntity student,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        if(student.getId().equals(session.getAttribute("user"))) {
            StudentEntity studentEntity = studentService.getStudent(student.getId());
            studentEntity.setName(student.getName());
            studentEntity.setAccount(student.getAccount());
            studentService.updateStudent(studentEntity);
            setMessege(redirectAttributes, "修改信息成功");
            return "redirect:/user/edit";
        }
        setMessege(redirectAttributes, "没有权限，请登录");
        return "redirect:/login";
    }

    @RequestMapping(value="/password", method = RequestMethod.POST)
    public String updatePassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        String userId = (String)session.getAttribute("user");
        int userType = (int)session.getAttribute("userType");

        switch (userType) {
            case 0:
                StudentEntity studentEntity = studentService.getStudent(userId);
                if(studentEntity.getPassword().equals(oldPassword)) {
                    studentEntity.setPassword(newPassword);
                    studentService.updateStudent(studentEntity);
                    setMessege(redirectAttributes, "修改密码成功");
                    return "redirect:/user/edit";
                } else {
                    setMessege(redirectAttributes, "原密码错误");
                    return "redirect:/user/edit";
                }
            case 1:
                OrganizationEntity organizationEntity = organService.getOrgan(userId);
                if(organizationEntity.getPassword().equals(oldPassword)) {
                    organizationEntity.setPassword(newPassword);
                    organService.updateOrgan(organizationEntity);
                    setMessege(redirectAttributes, "修改密码成功");
                    return "redirect:/organ/edit";
                } else {
                    setMessege(redirectAttributes, "原密码错误");
                    return "redirect:/organ/edit";
                }
            case 2:
                break;
        }

        return "redirect:/user/edit";
    }

    @RequestMapping(value = "/exchange", method = RequestMethod.POST)
    public String exchange(@RequestParam int point, HttpSession session, RedirectAttributes redirectAttributes) {
        String studentId = getUserId(session);
        studentService.exchange(studentId, point);

        redirectAttributes.addFlashAttribute("message", "兑换积分成功");
        return "redirect:/user/info";
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public String cancel(HttpSession session, RedirectAttributes redirectAttributes) {
        String studentId = getUserId(session);
        studentService.cancel(studentId);
        redirectAttributes.addFlashAttribute("message", "注销用户成功，该用户不可再被使用");
        return "redirect:/login";
    }
}
