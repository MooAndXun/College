package cn.moo.trainingcollege.schedule;

import cn.moo.trainingcollege.dao.PageviewDao;
import cn.moo.trainingcollege.dao.StudentDao;
import cn.moo.trainingcollege.entity.PageviewEntity;
import cn.moo.trainingcollege.entity.StudentEntity;
import cn.moo.trainingcollege.utils.CounterUtil;
import cn.moo.trainingcollege.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by chenmuen on 2017/3/13.
 */
@Component
public class Schedule {
    @Autowired
    StudentDao studentDao;
    @Autowired
    PageviewDao pageviewDao;

    @Scheduled(cron = "0 0 12 * * ?")
    public void checkMemberState() {
        List<StudentEntity> list = studentDao.getListByColumn("state", 1);
        for (StudentEntity studentEntity : list) {
            switch (studentEntity.getState()) {
                case 1:
                    if (beforeCurrent(studentEntity.getCreatedAt()) > 365) {
                        if (studentEntity.getBalance() < 50) {
                            studentEntity.setState(2);
                            System.out.println("会员" + studentEntity.getId() + "已被冻结");
                        }
                    }
                    break;
                case 2:
                    if (beforeCurrent(studentEntity.getCreatedAt()) > 730) {
                        studentEntity.setState(3);
                        System.out.println("会员" + studentEntity.getId() + "已被注销");
                    }
                    break;
                default:
                    break;
            }
        }
    }


    public void saveAccessNumber(){
        PageviewEntity entity = new PageviewEntity();
        entity.setNum(CounterUtil.getNum());
        entity.setRecordDate(TimeUtil.getCurrentTime());
        pageviewDao.add(entity);
        CounterUtil.returnZero();
    }

    private int beforeCurrent(Timestamp lastTime) {
        Timestamp current = TimeUtil.getCurrentTime();
        int day = (new Long((current.getTime() - lastTime.getTime()) / 1000 / 60 / 60 / 24)).intValue();
        return day;
    }
}
