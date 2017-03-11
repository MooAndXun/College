package cn.moo.trainingcollege.controller;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by chenmuen on 2017/3/1.
 */
public abstract class BaseController {

    Model setNav(int nav, int subNav, Model model) {

        return model;
    }

    public String getUserId(HttpSession session) {
        return (String)session.getAttribute("user");
    }

    public int getUserType(HttpSession session) {
        return (int)session.getAttribute("userType");
    }

    public void setMessege(RedirectAttributes redirectAttributes, String message) {
        redirectAttributes.addFlashAttribute("message", message);
    }
}
