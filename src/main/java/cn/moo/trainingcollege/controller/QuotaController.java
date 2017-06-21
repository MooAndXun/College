package cn.moo.trainingcollege.controller;

import cn.moo.trainingcollege.service.QuotaService;
import cn.moo.trainingcollege.utils.StatTimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("/site")
    public String siteQuotaPage() {
        return "site-quota";
    }

    @RequestMapping("/organization")
    public String organQuotaPage() {
        return "organ-quota";
    }

    @RequestMapping("/course")
    public String quotaCoursePage() {
        return "quota-course";
    }

    @RequestMapping("/income")
    public String quotaIncomePage() {
        return "quota-income";
    }

    @RequestMapping("/teacher")
    public String quotaTeacherPage() {
        return "quota-teacher";
    }

    @RequestMapping("/feedback")
    public String quotaFeedbackPage() {
        return "quota-feedback";
    }

    @RequestMapping("/convert")
    public String quotaConvertPage() {
        return "quota-convert";
    }

    @RequestMapping("/organ")
    public String quotaOrganPage() {
        return "quota-organ";
    }

    @RequestMapping("/course_num_rank")
    @ResponseBody
    public Map courseNumRank(String type, @RequestParam(value = "organ_id", required = false, defaultValue = "") String organId) {
        return quotaService.getSiteOrderRank(StatTimeType.getStatTimeType(type), organId.length()==0?null:organId);
    }

    @RequestMapping("/course_price_rank")
    @ResponseBody
    public Map coursePriceRank(String type, @RequestParam(value = "organ_id", required = false, defaultValue = "") String organId) {
        return quotaService.getSitePriceRank(StatTimeType.getStatTimeType(type), organId.length()==0?null:organId);
    }

    @RequestMapping("/course_income_rank")
    @ResponseBody
    public Map courseIncomeRank(String type, @RequestParam(value = "organ_id", required = false, defaultValue = "") String organId) {
        return quotaService.getSiteCourseIncomeRank(StatTimeType.getStatTimeType(type), organId.length()==0?null:organId);
    }

    @RequestMapping("/course_quit_rank")
    @ResponseBody
    public Map courseQuitRank(String type, @RequestParam(value = "organ_id", required = false, defaultValue = "") String organId) {
        return quotaService.getSiteQuitRateRank(StatTimeType.getStatTimeType(type), organId.length()==0?null:organId);
    }

    @RequestMapping("/course_satisfaction_rank")
    @ResponseBody
    public Map courseSatisfactionRank(String type, @RequestParam(value = "organ_id", required = false, defaultValue = "") String organId) {
        return quotaService.getSiteSatisfactionRank(StatTimeType.getStatTimeType(type), organId.length()==0?null:organId);
    }

    @RequestMapping("/course_quit_satisfaction")
    @ResponseBody
    public Map courseQuitSatisfaction(String type, @RequestParam(value = "organ_id", required = false, defaultValue = "") String organId) {
        List quitList = quotaService.getQuitRate(StatTimeType.getStatTimeType(type), organId.length()==0?null:organId);
        List satisfactionList = quotaService.getSatisfactionRate(StatTimeType.getStatTimeType(type), organId.length()==0?null:organId);

        Map<String, Object> result = new HashMap<>();
        result.put("quit", quitList);
        result.put("satisfaction", satisfactionList);
        return result;
    }

    @RequestMapping("/teacher_rank")
    @ResponseBody
    public Map teacherRank(String type, @RequestParam(value = "organ_id", required = false, defaultValue = "") String organId) {
        return quotaService.getTeacherIncomeRank(StatTimeType.getStatTimeType(type), organId.length()==0?null:organId);
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
    public Map memberAndIncome(String type, @RequestParam(value = "organ_id", required = false, defaultValue = "") String organId) {
        List memberList = quotaService.getMemberQuota(StatTimeType.getStatTimeType(type), organId.length()==0?null:organId);
        List incomeList = quotaService.getIncomeQuota(StatTimeType.getStatTimeType(type), organId.length()==0?null:organId);

        Map<String, Object> result = new HashMap<>();
        result.put("member", memberList);
        result.put("income", incomeList);
        return result;
    }

    @RequestMapping("/conversion_rate")
    @ResponseBody
    public Map conversionRate(String type) {
        List consumeList = quotaService.getConsumptionConversionRate(StatTimeType.getStatTimeType(type));
        List orderList = quotaService.getOrderConversionRate(StatTimeType.getStatTimeType(type));

        Map<String, Object> result = new HashMap<>();
        result.put("consume", consumeList);
        result.put("order", orderList);
        return result;
    }

}
