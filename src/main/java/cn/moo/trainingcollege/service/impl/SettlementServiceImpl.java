package cn.moo.trainingcollege.service.impl;

import cn.moo.trainingcollege.dao.CourseDao;
import cn.moo.trainingcollege.dao.OrderDao;
import cn.moo.trainingcollege.dao.SettlementDao;
import cn.moo.trainingcollege.entity.CourseEntity;
import cn.moo.trainingcollege.entity.SettlementEntity;
import cn.moo.trainingcollege.service.SettlementService;
import cn.moo.trainingcollege.utils.Constant;
import cn.moo.trainingcollege.utils.TimeUtil;
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

    @Autowired
    SettlementDao settlementDao;

    @Override
    public void settlement(int courseId) {
        CourseEntity courseEntity = courseDao.getByColumn("id", courseId);
        double normalIncome = orderDao.getCourseNormalIncome(courseId);
        double quitIncome = orderDao.getCourseQuitIncome(courseId);
        double income = normalIncome+quitIncome;

        SettlementEntity settlementEntity = new SettlementEntity();
        settlementEntity.setCourseId(courseId);
        settlementEntity.setMoney(income*(1- Constant.COMMISSION_RATE));
        settlementEntity.setCommission(income*Constant.COMMISSION_RATE);
        settlementEntity.setOrganizationId(courseEntity.getOrganId());
        settlementEntity.setCreatedAt(TimeUtil.getCurrentTime());
        settlementDao.add(settlementEntity);

        courseEntity.setSettled(true);
        courseDao.update(courseEntity);
    }
}
