package cn.moo.trainingcollege.schedule;

import cn.moo.trainingcollege.dao.StudentDao;
import cn.moo.trainingcollege.entity.StudentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by chenmuen on 2017/3/13.
 */
@Component
public class Schedule {
    @Autowired
    StudentDao studentDao;

    @Scheduled(cron="0 0 12 * * ?")
    public void checkMemberState() {
        List<StudentEntity> list = studentDao.getListByColumn("state", 1);
        for (StudentEntity studentEntity : list) {
            if(studentEntity.getBalance()<50) {
                studentEntity.setState(2);
                System.out.println("会员"+studentEntity.getId()+"已被冻结");
            }
        }
    }
}
