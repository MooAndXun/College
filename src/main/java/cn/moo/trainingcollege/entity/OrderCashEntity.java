package cn.moo.trainingcollege.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by chenmuen on 2017/2/24.
 */
@Entity(name = "order_cash")
public class OrderCashEntity {
    private int id;
    private int courseId;
    private String studentName;
    private double price;
    private boolean quitState;
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
    @Column(name = "course_id")
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "student_name")
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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
    public boolean isQuitState() {
        return quitState;
    }

    public void setQuitState(boolean quitState) {
        this.quitState = quitState;
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

        OrderCashEntity that = (OrderCashEntity) o;

        if (id != that.id) return false;
        if (courseId != that.courseId) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (quitState != that.quitState) return false;
        if (score != that.score) return false;
        if (studentName != null ? !studentName.equals(that.studentName) : that.studentName != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + courseId;
        result = 31 * result + (studentName != null ? studentName.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (quitState ? 1 : 0);
        result = 31 * result + score;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
