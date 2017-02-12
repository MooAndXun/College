package cn.moo.trainingcollege.service;

import cn.moo.trainingcollege.entity.OrganizationEntity;

/**
 * Created by chenmuen on 2017/2/10.
 */
public interface OrganService {
    public boolean checkLogin(String organId, String password);

    public boolean addOrgan();

    public boolean updateOrgan();

    public OrganizationEntity getOrgan(String organId);

    public boolean pay(String studentId, int courseId);
}
