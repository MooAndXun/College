package cn.moo.trainingcollege.dao.impl;

import cn.moo.trainingcollege.dao.CourseDao;
import cn.moo.trainingcollege.entity.CourseEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by chenmuen on 2017/2/12.
 */
@Repository
public class CourseDaoImpl extends BaseDaoImpl<CourseEntity> implements CourseDao {
}
