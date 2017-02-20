package cn.moo.trainingcollege.service.impl;

import cn.moo.trainingcollege.dao.StudentDao;
import cn.moo.trainingcollege.dao.TopupDao;
import cn.moo.trainingcollege.entity.StudentEntity;
import cn.moo.trainingcollege.entity.TopupEntity;
import cn.moo.trainingcollege.service.StudentService;
import cn.moo.trainingcollege.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by chenmuen on 2017/2/12.
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentDao studentDao;
    @Autowired
    TopupDao topupDao;

    @Override
    public boolean checkLogin(String id, String password) {
        StudentEntity student = studentDao.getById(id);
        if(student==null) {
            return false;
        } else if (student.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addStudent(StudentEntity student) {
        int count = studentDao.getCounts();
        count++;
        String id = "S" + String.format("%06d", count);
        student.setId(id);
        studentDao.add(student);
    }

    @Override
    public StudentEntity getStudent(String studentId) {
        return studentDao.getById(studentId);
    }

    @Override
    public void updateStudent(StudentEntity student) {
        studentDao.update(student);
    }

    @Override
    public void topUp(String studentId, int money) {
        StudentEntity student = studentDao.getById(studentId);
        if(student != null) {
            student.setBalance(student.getBalance()+money);
            if((student.getState() == 0) && (money >= 1000)){
                student.setState(1);
            }
            if(student.getState() == 2 && (student.getBalance() >= 50)){
                student.setState(1);
            }
            student.setPoint(student.getPoint()+(money/100));
            student.setExp((student.getExp()+money));
            student.setLevel(getLevel(student.getExp()));
            studentDao.update(student);
            TopupEntity topupEntity = new TopupEntity();
            topupEntity.setCreatedAt(TimeUtil.getCurrentTime());
            topupEntity.setMoney(money);
            topupEntity.setStudentId(studentId);
            topupEntity.setType(1);
            topupDao.add(topupEntity);
        }
    }

    @Override
    public void cancel(String studentId) {
        StudentEntity student = studentDao.getById(studentId);
        if(student != null) {
            student.setState(3);
            studentDao.update(student);
        }
    }

    private int getLevel(int exp){
        if(exp<2000){
            return 1;
        }else if(exp<5000){
            return 2;
        }else if (exp<10000){
            return 3;
        }
        return 4;
    }
}