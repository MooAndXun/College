package cn.moo.trainingcollege.controller;

import org.springframework.ui.Model;

/**
 * Created by chenmuen on 2017/3/1.
 */
public abstract class BaseController {

    Model setNav(int nav, int subNav, Model model) {

        return model;
    }
}
