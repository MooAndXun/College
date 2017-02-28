package cn.moo.trainingcollege.service;

import cn.moo.trainingcollege.entity.OrganizationEntity;
import org.springframework.stereotype.Service;

/**
 * Created by chenmuen on 2017/2/10.
 */
@Service
public interface OrganService {
    public boolean checkLogin(String organId, String password);

    /**
     * 注册机构用户
     * @param organization
     * @return 用户ID
     */
    public String addOrgan(OrganizationEntity organization);

    public void updateOrgan(OrganizationEntity organization);

    public OrganizationEntity getOrgan(String organId);
    
}
