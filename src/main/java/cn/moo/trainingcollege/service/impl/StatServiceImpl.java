package cn.moo.trainingcollege.service.impl;

import cn.moo.trainingcollege.dao.CourseDao;
import cn.moo.trainingcollege.dao.OrderDao;
import cn.moo.trainingcollege.dao.SettlementDao;
import cn.moo.trainingcollege.dao.StudentDao;
import cn.moo.trainingcollege.entity.CourseEntity;
import cn.moo.trainingcollege.entity.OrderAccountEntity;
import cn.moo.trainingcollege.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 小春 on 2017/3/11.
 */
@Service
@Transactional
public class StatServiceImpl implements StatService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    StudentDao studentDao;
    @Autowired
    SettlementDao settlementDao;
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
        List<OrderAccountEntity> list = orderDao.getStudentCourseList(studentId);
        if (list == null){
            list = new ArrayList<OrderAccountEntity>();
        }
        int score = 0;
        int count = 0;
        for (OrderAccountEntity entity: list) {
            if(entity.getScore() > -1){
                score += entity.getScore();
                count++;
            }
        }

        return count==0?0:(score/count);
    }

    @Override
    public double getStudentConsume(String studentId) {
        return orderDao.getStudentConsume(studentId);
    }

    @Override
    public List<Integer> getStudentCourseLine(String studentId) {
        return orderDao.getStudentCourseLine(studentId);
    }

    /**
     * 获得学生成绩分布
     * @param studentId
     * @return 分布数组，分别成绩在0-60，60-70，70-80，80-90，90-100的个数
     */
    @Override
    public List<Integer> getStudentScoreDistribution(String studentId) {
        List<OrderAccountEntity> list = orderDao.getStudentCourseList(studentId);
        if (list == null){
            list = new ArrayList<OrderAccountEntity>();
        }
        int one = 0;
        int two = 0;
        int three = 0;
        int forth = 0;
        int five = 0;
        for (OrderAccountEntity entity: list) {
            if(entity.getScore() >= 90){
                five++;
            }else if(entity.getScore() >= 80){
                forth++;
            }else if(entity.getScore() >= 70){
                three++;
            }else if(entity.getScore() >=60){
                two++;
            }else if(entity.getScore() >= 0){
                one++;
            }
        }
        List<Integer> counts = new ArrayList<>();
        counts.add(one);
        counts.add(two);
        counts.add(three);
        counts.add(forth);
        counts.add(five);

        return counts;
    }

    @Override
    public int getOrganCourseCount(String organId) {
        return courseDao.getCounts("organId",organId);
    }

    @Override
    public int getOrganMemberCount(String organId) {
        return orderDao.getStudentNumOfOrgan(organId);
    }

    @Override
    public double getOrganIncome(String organId) {
        return settlementDao.getOrganIncome(organId);
    }

    @Override
    public List<Double> getOrganIncomeLine(String organId) {
        List<Double> list = settlementDao.getOrganIncomeLine(organId);
        return list;
    }

    @Override
    public List<Integer> getOrganMemberLine(String organId) {
        List<Integer> list = settlementDao.getOrganMemberLine(organId);
        return list;
    }

    @Override
    public Map<String, Object> getOrganTopCourse(String organId) {

        return settlementDao.getOrganTopCourse(organId);
    }

    @Override
    public double getSiteIncome() {
        return settlementDao.getSiteIncome();
    }

    @Override
    public int getSiteMemberCount() {
        return studentDao.getCounts();
    }

    @Override
    public int getSiteCourseCount() {
        int result = courseDao.getCounts("state",1) + courseDao.getCounts("state",0);
        return result;
    }

    @Override
    public List<Double> getSiteIncomeLine() {
        List<Double> list = settlementDao.getSiteIncomeLine();
        return list;
    }

    @Override
    public List<Integer> getSiteMemberLine() {
        return studentDao.getSiteStudentLine();
    }

    @Override
    public Map<String, Object> getSiteTopOrgan() {
        return orderDao.getSiteTopOrgan();
    }

    private List<Double> changeSizeOfList(List<Double> list){
        int count = list.size()>12?12:list.size();
        List<Double> result = new ArrayList<Double>();
        for (int i = 0; i < 12-count; i++) {
            result.add(0.0);
        }
        for (Double num:list) {
            result.add(num);
        }
        return result;
    }
}
