package cn.moo.trainingcollege.service;

import java.util.List;
import java.util.Map;

/**
 * Created by chenmuen on 2017/3/9.
 */
public interface StatService {
    /**
     * 获得学生参加过的课程总数
     * @param studentId
     * @return
     */
    public int getStudentCourseCount(String studentId);

    /**
     * 获得学生所有课程的平均分
     * @param studentId
     * @return
     */
    public int getStudentAverageScore(String studentId);

    /**
     * 获得学生成绩分布
     * @param studentId
     * @return 分布数组，分别成绩在0-60，60-70，70-80，80-90，90-100的个数
     */
    public List<Integer> getStudentScoreDistribution(String studentId);

    /**
     * 获得机构课程总数
     * @param organId
     * @return
     */
    public int getOrganCourseCount(String organId);

    /**
     * 获得机构的学员总数
     * @param organId
     * @return
     */
    public int getOrganMemberCount(String organId);

    /**
     * 获得机构总收入
     * @param organId
     * @return
     */
    public int getOrganIncome(String organId);

    /**
     * 获得机构的收入曲线
     * @param organId
     * @return 收入List，过去十二个月的收入，顺序为从前到后
     */
    public List<Integer> getOrganIncomeLine(String organId);

    /**
     * 获得机构的参加课程人数曲线（各个月之间不用去重）
     * @param organId
     * @return 人数List，过去十二个月加入的人数，顺序为从前到后
     */
    public List<Integer> getOrganMemberLine(String organId);

    /**
     * 获得机构参与人数最高的课程名及其人数
     * @param organId
     * @return 拥有两个List的Map，names-课程名列表List<String>，members-成员名列表List<Integet>，排序为从高到低
     */
    public Map<String, Object> getOrganTopCourse(String organId);

    /**
     * 获得网站总收入
     * @return
     */
    public int getSiteIncome();

    /**
     * 获得网站总人数
     * @return
     */
    public int getSiteMemberCount();

    /**
     * 获得网站课程总数
     * @return
     */
    public int getSiteCourseCount();

    /**
     * 获得过去十二个月网站收入曲线
     * @return 收入List，过去十二个月的收入，排序为从前到后
     */
    public List<Integer> getSiteIncomeLine();

    /**
     * 获得过去十二个月的加入人数
     * @return 人数List，过去十二个月加入的人数，排序为从前到后
     */
    public List<Integer> getSiteMemberLine();

}
