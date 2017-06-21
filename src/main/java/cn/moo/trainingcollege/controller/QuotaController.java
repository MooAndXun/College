package cn.moo.trainingcollege.controller;

import cn.moo.trainingcollege.service.QuotaService;
import cn.moo.trainingcollege.utils.StatTimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenmuen on 2017/6/19.
 */
@Controller
@RequestMapping("/quota")
public class QuotaController {
    @Autowired
    QuotaService quotaService;

    @RequestMapping("/course")
    public String courseQuotaPage() {
        return "quota-course";
    }

    @RequestMapping("/course_num_rank")
    @ResponseBody
    public Map courseNumRank(String type) {
        return quotaService.getSiteOrderRank(StatTimeType.getStatTimeType(type));
    }

    @RequestMapping("/course_price_rank")
    @ResponseBody
    public Map coursePriceRank(String type) {
        return quotaService.getSitePriceRank(StatTimeType.getStatTimeType(type));
    }

    @RequestMapping("/course_income_rank")
    @ResponseBody
    public Map courseIncomeRank(String type) {
        return quotaService.getSiteCourseIncomeRank(StatTimeType.getStatTimeType(type));
    }

    @RequestMapping("/course_quit_rank")
    @ResponseBody
    public Map courseQuitRank(String type) {
        return quotaService.getSiteQuitRateRank(StatTimeType.getStatTimeType(type));
    }

    @RequestMapping("/course_satisfaction_rank")
    @ResponseBody
    public Map courseSatisfactionRank(String type) {
        return quotaService.getSiteSatisfactionRank(StatTimeType.getStatTimeType(type));
    }

    @RequestMapping("/course_quit_satisfaction")
    @ResponseBody
    public Map courseQuitSatisfaction(String type) {
        List quitList = quotaService.getQuitRate(StatTimeType.getStatTimeType(type));
        List satisfactionList = quotaService.getSatisfactionRate(StatTimeType.getStatTimeType(type));

        Map<String, Object> result = new HashMap<>();
        result.put("quit", quitList);
        result.put("satisfaction", satisfactionList);
        return result;
    }

    @RequestMapping("/teacher_rank")
    @ResponseBody
    public Map teacherRank(String type) {
        return quotaService.getTeacherIncomeRank(StatTimeType.getStatTimeType(type));
    }

    @RequestMapping("/member_income/year_to_year")
    @ResponseBody
    public Map memberAndIncomeYearToYear() {
        List memberList = quotaService.getSiteMemberYearToYearRate();
        List incomeList = quotaService.getSiteIncomeYearToYearRate();

        Map<String, Object> result = new HashMap<>();
        result.put("member", memberList);
        result.put("income", incomeList);
        return result;
    }

    @RequestMapping("/member_income")
    @ResponseBody
    public Map memberAndIncome(String type) {
        List memberList = quotaService.getMemberQuota(StatTimeType.getStatTimeType(type), null);
        List incomeList = quotaService.getIncomeQuota(StatTimeType.getStatTimeType(type), null);

        Map<String, Object> result = new HashMap<>();
        result.put("member", memberList);
        result.put("income", incomeList);
        return result;
    }

}
