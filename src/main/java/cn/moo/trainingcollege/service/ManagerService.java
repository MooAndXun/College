package cn.moo.trainingcollege.service;

import cn.moo.trainingcollege.entity.ManagerEntity;

/**
 * Created by chenmuen on 2017/2/12.
 */
public interface ManagerService {
    public boolean checkLogin(String id,String password);

    public void addManager(ManagerEntity manager);

    public void updateManager(ManagerEntity manager);

    public void approve(int courseId, boolean isApprove);

    public void approveQuit(int orderId, boolean isApprove);
}
