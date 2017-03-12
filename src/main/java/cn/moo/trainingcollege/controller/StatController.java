package cn.moo.trainingcollege.controller;

import cn.moo.trainingcollege.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by chenmuen on 2017/3/11.
 */
@Controller
@RequestMapping("/stat")
public class StatController extends BaseController {
    @Autowired
    StatService statService;

    @RequestMapping("/student")
    public String studentStat(HttpSession session, Model model) {
        String userId = getUserId(session);
        int courseCount = statService.getStudentCourseCount(userId);
        int scoreAvg = statService.getStudentAverageScore(userId);
        model.addAttribute("courseCount", courseCount);
        model.addAttribute("scoreAvg", scoreAvg);

        return "student-stat";
    }
    @RequestMapping("/organ")
    public String organStat(HttpSession session, Model model) {
        String userId = getUserId(session);
        double income = statService.getOrganIncome(userId);
        int courseCount = statService.getOrganCourseCount(userId);
        int memberCount = statService.getOrganMemberCount(userId);
        model.addAttribute("income", income);
        model.addAttribute("courseCount", courseCount);
        model.addAttribute("memberCount", memberCount);
        return "organ-stat";
    }
    @RequestMapping("/site")
    public String siteStat(HttpSession session, Model model) {
        double income = statService.getSiteIncome();
        int courseCount = statService.getSiteCourseCount();
        int memberCount = statService.getSiteMemberCount();
        model.addAttribute("income", income);
        model.addAttribute("courseCount", courseCount);
        model.addAttribute("memberCount", memberCount);
        return "site-stat";
    }

    /*------------- Action -------------*/
    @RequestMapping("/student/score/bar")
    @ResponseBody
    public List studentScore(@RequestParam String studentId) {
        List<Integer> scoreList = statService.getStudentScoreDistribution(studentId);
        return scoreList;
    }

    @RequestMapping("/student/course/line")
    @ResponseBody
    public List studentCourse(@RequestParam String studentId) {
        return null;
    }

    @RequestMapping("/organ/income/line")
    @ResponseBody
    public List organIncome(@RequestParam String organId) {
        List<Double> incomeList = statService.getOrganIncomeLine(organId);
        return incomeList;
    }

    @RequestMapping("/organ/member/line")
    @ResponseBody
    public List organMember(@RequestParam String organId) {
        List<Integer> incomeList = statService.getOrganMemberLine(organId);
        return incomeList;
    }

    @RequestMapping("/organ/member/bar")
    @ResponseBody
    public Map organRate(@RequestParam String organId) {
        Map<String, Object> map = statService.getOrganTopCourse(organId);
        return map;
    }

    @RequestMapping("/site/income/line")
    @ResponseBody
    public List siteIncome() {
        List<Double> incomeList = statService.getSiteIncomeLine();
        return incomeList;
    }

    @RequestMapping("/site/member/line")
    @ResponseBody
    public List siteMember() {
        List<Integer> memberList = statService.getSiteMemberLine();
        return memberList;
    }
}
