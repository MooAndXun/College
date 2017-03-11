package cn.moo.trainingcollege.service.impl;

import cn.moo.trainingcollege.dao.OrderDao;
import cn.moo.trainingcollege.entity.OrderAccountEntity;
import cn.moo.trainingcollege.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 小春 on 2017/3/11.
 */
@Service
public class StatServiceImpl implements StatService {
    @Autowired
    OrderDao orderDao;
    @Override
    public int getStudentCourseCount(String studentId) {
        List<OrderAccountEntity> list = orderDao.getStudentCourseList(studentId);
        if (list == null){
            list = new ArrayList<OrderAccountEntity>();
        }
        return list.size();
    }

    @Override
    public int getStudentAverageScore(String studentId) {
        return 0;
    }

    @Override
    public List<Integer> getStudentScoreDistribution(String studentId) {
        return null;
    }

    @Override
    public int getOrganCourseCount(String organId) {
        return 0;
    }

    @Override
    public int getOrganMemberCount(String organId) {
        return 0;
    }

    @Override
    public int getOrganIncome(String organId) {
        return 0;
    }

    @Override
    public List<Integer> getOrganIncomeLine(String organId) {
        return null;
    }

    @Override
    public List<Integer> getOrganMemberLine(String organId) {
        return null;
    }

    @Override
    public Map<String, Object> getOrganTopCourse(String organId) {
        return null;
    }

    @Override
    public int getSiteIncome() {
        return 0;
    }

    @Override
    public int getSiteMemberCount() {
        return 0;
    }

    @Override
    public int getSiteCourseCount() {
        return 0;
    }

    @Override
    public List<Integer> getSiteIncomeLine() {
        return null;
    }

    @Override
    public List<Integer> getSiteMemberLine() {
        return null;
    }
}
