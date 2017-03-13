package cn.moo.trainingcollege.dao.impl;

import cn.moo.trainingcollege.dao.BaseDao;
import cn.moo.trainingcollege.dao.StudentDao;
import cn.moo.trainingcollege.entity.StudentEntity;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by chenmuen on 2017/2/12.
 */
@Repository
public class StudentDaoImpl extends BaseDaoImpl<StudentEntity> implements StudentDao {
    @Override
    public List getSiteStudentLine() {
        String sql =
                "SELECT DATE_FORMAT(created_at, '%c') AS month, COUNT(DISTINCT id) AS num " +
                "FROM student " +
                "WHERE created_at > DATE_SUB(NOW(), INTERVAL 1 YEAR) " +
                "GROUP BY DATE_FORMAT(created_at, '%y%m') " +
                "ORDER BY DATE_FORMAT(created_at, '%y%m') ";
        return getIntTimeLine(sql);
    }
}
