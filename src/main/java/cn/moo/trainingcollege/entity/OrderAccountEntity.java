package cn.moo.trainingcollege.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by chenmuen on 2017/2/24.
 */
@Entity(name = "order_account")
public class OrderAccountEntity {
    private int id;
    private String studentId;
    private int courseId;
    private double price;
    private int quitState;
    private boolean isPaid;
    private boolean isCancel;
    private int score;
    private Timestamp createdAt;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "student_id")
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Basic
    @Column(name = "course_id")
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "quit_state")
    public int getQuitState() {
        return quitState;
    }

    public void setQuitState(int quitState) {
        this.quitState = quitState;
    }

    @Basic
    @Column(name = "is_paid")
    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    @Basic
    @Column(name = "is_cancel")
    public boolean isCancel() {
        return isCancel;
    }

    public void setCancel(boolean cancel) {
        isCancel = cancel;
    }

    @Basic
    @Column(name = "score")
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Basic
    @Column(name = "created_at")
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderAccountEntity that = (OrderAccountEntity) o;

        if (id != that.id) return false;
        if (courseId != that.courseId) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (quitState != that.quitState) return false;
        if (isPaid != that.isPaid) return false;
        if (isCancel != that.isCancel) return false;
        if (score != that.score) return false;
        if (studentId != null ? !studentId.equals(that.studentId) : that.studentId != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (studentId != null ? studentId.hashCode() : 0);
        result = 31 * result + courseId;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + quitState;
        result = 31 * result + (isPaid ? 1 : 0);
        result = 31 * result + (isCancel ? 1 : 0);
        result = 31 * result + score;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
