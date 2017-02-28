package cn.moo.trainingcollege.service;

import cn.moo.trainingcollege.entity.ManagerEntity;

/**
 * Created by chenmuen on 2017/2/12.
 */
public interface ManagerService {
    public boolean checkLogin(String id,String password);

    /**
     * 注册经理用户
     * @param manager
     * @return 用户ID
     */
    public String addManager(ManagerEntity manager);

    public void updateManager(ManagerEntity manager);

    public void approve(int courseId, boolean isApprove);

    public void approveQuit(int orderId, boolean isApprove);
}
