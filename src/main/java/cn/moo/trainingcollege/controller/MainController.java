package cn.moo.trainingcollege.controller;

import cn.moo.trainingcollege.entity.ManagerEntity;
import cn.moo.trainingcollege.entity.OrganizationEntity;
import cn.moo.trainingcollege.entity.StudentEntity;
import cn.moo.trainingcollege.service.ManagerService;
import cn.moo.trainingcollege.service.OrganService;
import cn.moo.trainingcollege.service.StudentService;
import org.codehaus.jackson.map.deser.ValueInstantiators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by chenmuen on 2017/2/27.
 */
@Controller
@RequestMapping("/")
public class MainController extends BaseController {
    @Autowired
    StudentService studentService;

    @Autowired
    OrganService organService;

    @Autowired
    ManagerService managerService;

    @RequestMapping("")
    public String indexPage(HttpSession session) {
        int userType = (int)session.getAttribute("userType");

        switch (userType) {
            case 0:
                return "redirect:/course/all";
            case 1:
                return "redirect:/course/manage";
            case 2:
                return "redirect:/course/approve";
            default:
                return "redirect:/login";
        }

    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String id,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        if(id==null||id.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage","账号为空");
            return "redirect:login";
        }

        char type = id.charAt(0);
        switch (type) {
            case 'S':
                if(studentService.getStudent(id)==null){
                    redirectAttributes.addFlashAttribute("message", "查无此ID");
                } else if(studentService.checkLogin(id, password)) {
                    session.setAttribute("user", id);
                    session.setAttribute("userType", 0);
                    redirectAttributes.addFlashAttribute("message", "登录成功");
                    return "redirect:/course/all";
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "密码错误");
                }
                break;
            case 'O':
                if(organService.getOrgan(id)==null){
                    redirectAttributes.addFlashAttribute("errorMessage", "查无此ID");
                } else if(organService.checkLogin(id, password)) {
                    session.setAttribute("user", id);
                    session.setAttribute("userType", 1);
                    redirectAttributes.addFlashAttribute("message", "登录成功");
                    return "redirect:/course/manage";
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "密码错误");
                }
                break;
            case 'M':
                if(managerService.getManager(id)==null){
                    redirectAttributes.addFlashAttribute("errorMessage", "查无此ID");
                } else if(managerService.checkLogin(id, password)) {
                    session.setAttribute("user", id);
                    session.setAttribute("userType", 2);
                    redirectAttributes.addFlashAttribute("message", "登录成功");
                    return "redirect:/course/approve";
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "密码错误");
                }
                break;
            default:
                redirectAttributes.addFlashAttribute("errorMessage", "查无此ID");
                break;

        }

        return "redirect:/login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage() {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam String name,
                           @RequestParam String password,
                           @RequestParam int type,
                           RedirectAttributes redirectAttributes) {
        String id = null;

        switch (type) {
            case 0:
                StudentEntity studentEntity = new StudentEntity();
                studentEntity.setName(name);
                studentEntity.setPassword(password);
                studentService.addStudent(studentEntity);
                break;
            case 1:
                OrganizationEntity organizationEntity = new OrganizationEntity();
                organizationEntity.setName(name);
                organizationEntity.setPassword(password);
                organService.addOrgan(organizationEntity);
                break;
            case 2:
                ManagerEntity managerEntity = new ManagerEntity();
                managerEntity.setName(name);
                managerEntity.setPassword(password);
                managerService.addManager(managerEntity);
                break;
        }

        return "redirect:/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session){
        session.invalidate();

        return "redirect:/login";
    }

}
