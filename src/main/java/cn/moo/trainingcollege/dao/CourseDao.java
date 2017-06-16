package cn.moo.trainingcollege.dao;

import cn.moo.trainingcollege.entity.CourseEntity;
import cn.moo.trainingcollege.utils.StatTimeType;

import java.util.List;
import java.util.Map;

/**
 * Created by chenmuen on 2017/2/12.
 */
public interface CourseDao extends BaseDao<CourseEntity> {
    public Map<String, Object> getSitePriceRank(StatTimeType statTimeType);
}