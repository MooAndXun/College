package cn.moo.trainingcollege.service.impl;

import cn.moo.trainingcollege.dao.OrganDao;
import cn.moo.trainingcollege.entity.OrganizationEntity;
import cn.moo.trainingcollege.service.OrganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 小春 on 2017/2/20.
 */
@Service
@Transactional
public class OrganServiceImpl implements OrganService {
    @Autowired
    OrganDao organDao;

    @Override
    public boolean checkLogin(String organId, String password) {
        OrganizationEntity organization = organDao.getById(organId);
        if(organization == null){
            return false;
        }else if(organization.getPassword().equals(password)){
            return true;
        }
        return false;
    }

    @Override
    public String addOrgan(OrganizationEntity organization) {
        int count = organDao.getCounts();
        count++;
        String id = "O" + String.format("%06d",count);
        organization.setId(id);
        organDao.add(organization);

        // (返回用户ID)
        return id;
    }

    @Override
    public void updateOrgan(OrganizationEntity organization) {
        organDao.update(organization);
    }

    @Override
    public OrganizationEntity getOrgan(String organId) {
        return organDao.getById(organId);
    }
}
