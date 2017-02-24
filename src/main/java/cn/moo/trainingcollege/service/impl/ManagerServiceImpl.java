package cn.moo.trainingcollege.service.impl;

import cn.moo.trainingcollege.dao.CourseDao;
import cn.moo.trainingcollege.dao.ManagerDao;
import cn.moo.trainingcollege.entity.CourseEntity;
import cn.moo.trainingcollege.entity.ManagerEntity;
import cn.moo.trainingcollege.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by 小春 on 2017/2/21.
 */
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    ManagerDao managerDao;
    @Autowired
    CourseDao courseDao;

    @Override
    public boolean checkLogin(String id,String password) {
        ManagerEntity manager = managerDao.getById(id);
        if(manager == null){
            return false;
        }else if(manager.getPassword().equals(password)){
            return true;
        }
        return false;
    }

    @Override
    public void addManager(ManagerEntity manager) {
        managerDao.add(manager);
    }

    @Override
    public void updateManager(ManagerEntity manager) {
        managerDao.update(manager);
    }

    @Override
    public void approve(int courseId, boolean isApprove) {
        CourseEntity courseEntity = courseDao.getByColumn("id",courseId);
        courseEntity.setState(isApprove?1:-1);
        courseDao.update(courseEntity);
    }

    @Override
    public void approveQuit(int orderId, boolean isApprove) {
        //TODO
    }

}
