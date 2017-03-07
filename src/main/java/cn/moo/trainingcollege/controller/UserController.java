package cn.moo.trainingcollege.controller;

import cn.moo.trainingcollege.entity.OrganizationEntity;
import cn.moo.trainingcollege.entity.StudentEntity;
import cn.moo.trainingcollege.service.ManagerService;
import cn.moo.trainingcollege.service.OrganService;
import cn.moo.trainingcollege.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping("/info")
    public String userInfoPage(HttpSession session, Model model){
        String userId = (String)session.getAttribute("user");
        int userType = (int)session.getAttribute("userType");
        String pageStr = "";

        switch (userType) {
            case 0:
                pageStr = "user-info";
                StudentEntity studentEntity = studentService.getStudent(userId);
                model.addAttribute("student", studentEntity);
                break;
            case 1:
                pageStr = "organ-info";
                OrganizationEntity organizationEntity = organService.getOrgan(userId);
                model.addAttribute("organ", organizationEntity);
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
                model.addAttribute("student", studentEntity);
                break;
            case 1:
                pageStr = "organ-edit";
                OrganizationEntity organizationEntity = organService.getOrgan(userId);
                model.addAttribute("organ", organizationEntity);
                break;
            case 2:

                break;
        }

        return pageStr;
    }

    @RequestMapping(value="edit/student", method = RequestMethod.POST)
    public String userEdit(StudentEntity student, HttpSession session, Model model) {
        if(student.getId().equals(session.getAttribute("user"))) {
            StudentEntity studentEntity = studentService.getStudent(student.getId());
            studentEntity.setName(student.getName());
            studentEntity.setAccount(student.getAccount());
            studentService.updateStudent(studentEntity);
            return "redirect:/user/edit";
        }
        return "redirect:/login";
    }

    @RequestMapping(value="/password", method = RequestMethod.POST)
    public String updatePassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 HttpSession session) {
        String userId = (String)session.getAttribute("user");
        int userType = (int)session.getAttribute("userType");

        switch (userType) {
            case 0:
                StudentEntity studentEntity = studentService.getStudent(userId);
                if(studentEntity.getPassword().equals(oldPassword)) {
                    studentEntity.setPassword(newPassword);
                    studentService.updateStudent(studentEntity);
                    return "redirect:/user/edit";
                } else {
                    return "redirect:/user/edit";
                }
            case 1:
                OrganizationEntity organizationEntity = organService.getOrgan(userId);
                if(organizationEntity.getPassword().equals(oldPassword)) {
                    organizationEntity.setPassword(newPassword);
                    organService.updateOrgan(organizationEntity);
                    return "redirect:/organ/edit";
                } else {
                    return "redirect:/organ/edit";
                }
            case 2:
                break;
        }

        return "redirect:/user/edit";
    }

    @RequestMapping(value = "/topup", method = RequestMethod.GET)
    public String topUpPage() {
        return "topup";
    }

    @RequestMapping(value = "/topup", method = RequestMethod.POST)
    public String topUp(@RequestParam int money, HttpSession session) {
        String userId = (String)session.getAttribute("user");
        int userType = (int)session.getAttribute("userType");

        if(userType==0) {
            studentService.topUp(userId, money);
            return "redirect:/user/info";
        } else {
            return "redirect:/login";
        }

    }
}
