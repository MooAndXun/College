package cn.moo.trainingcollege.service.impl;

import cn.moo.trainingcollege.dao.BalanceDao;
import cn.moo.trainingcollege.dao.CourseDao;
import cn.moo.trainingcollege.dao.ManagerDao;
import cn.moo.trainingcollege.dao.OrderDao;
import cn.moo.trainingcollege.entity.BalanceEntity;
import cn.moo.trainingcollege.entity.CourseEntity;
import cn.moo.trainingcollege.entity.ManagerEntity;
import cn.moo.trainingcollege.entity.OrderAccountEntity;
import cn.moo.trainingcollege.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 小春 on 2017/2/21.
 */

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    ManagerDao managerDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    OrderDao orderDao;
    @Autowired
    BalanceDao balanceDao;

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
    public String addManager(ManagerEntity manager) {
        managerDao.add(manager);

        // TODO(返回用户ID)
        return null;
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

    /*
    quitState:
        0 默认
        -1 申请退课
        1 批准退课
        2 拒接退课
     */
    @Override
    public void approveQuit(int orderId, boolean isApprove) {
        OrderAccountEntity order = orderDao.getByColumn("id",orderId);
        if(isApprove){
            order.setQuitState(1);
            double settlement = order.getPrice()*0.5;
            BalanceEntity balanceEntity = balanceDao.getByColumn("id",1);
            balanceEntity.setBalance(balanceEntity.getBalance() - settlement);
            balanceDao.update(balanceEntity);
        }else{
            order.setQuitState(2);
        }
        orderDao.update(order);
    }

}
