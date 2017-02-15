package cn.moo.trainingcollege.service;

import cn.moo.trainingcollege.entity.OrganizationEntity;
import org.springframework.stereotype.Service;

/**
 * Created by chenmuen on 2017/2/10.
 */
@Service
public interface OrganService {
    public boolean checkLogin(String organId, String password);

    public void addOrgan(OrganizationEntity organization);

    public void updateOrgan(OrganizationEntity organization);

    public OrganizationEntity getOrgan(String organId);

    public void pay(String studentId, int courseId);
}
