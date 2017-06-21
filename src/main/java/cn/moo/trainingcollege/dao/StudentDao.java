package cn.moo.trainingcollege.dao;

import cn.moo.trainingcollege.entity.StudentEntity;
import cn.moo.trainingcollege.utils.StatTimeType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by chenmuen on 2017/2/12.
 */
public interface StudentDao extends BaseDao<StudentEntity> {
    public List getSiteStudentLine();

    public List<Double> getSiteMemberYearToYearRate();

    public List<Double> getConsumptionConversionRate(StatTimeType statTimeType);
}
