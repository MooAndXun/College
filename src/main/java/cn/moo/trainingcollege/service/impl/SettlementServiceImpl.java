package cn.moo.trainingcollege.service.impl;

import cn.moo.trainingcollege.dao.CourseDao;
import cn.moo.trainingcollege.dao.OrderDao;
import cn.moo.trainingcollege.entity.CourseEntity;
import cn.moo.trainingcollege.entity.SettlementEntity;
import cn.moo.trainingcollege.service.SettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by chenmuen on 2017/3/11.
 */
@Service
@Transactional
public class SettlementServiceImpl implements SettlementService {
    @Autowired
    CourseDao courseDao;

    @Autowired
    OrderDao orderDao;

    @Override
    public void settlement(int courseId) {
        CourseEntity courseEntity = courseDao.getByColumn("id", courseId);

        

        SettlementEntity settlementEntity = new SettlementEntity();
        settlementEntity.setCourseId(courseId);
    }
}
