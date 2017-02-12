package cn.moo.trainingcollege.entity;

import javax.persistence.*;

/**
 * Created by chenmuen on 2017/1/25.
 */
@Entity(name = "order")
public class OrderEntity {
    private int id;
    private String studentId;
    private String courseId;
    private int price;
    private byte isDropped;
    private byte isPaid;
    private byte isCancel;

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
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Basic
    @Column(name = "is_dropped")
    public byte getIsDropped() {
        return isDropped;
    }

    public void setIsDropped(byte isDropped) {
        this.isDropped = isDropped;
    }

    @Basic
    @Column(name = "is_paid")
    public byte getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(byte isPaid) {
        this.isPaid = isPaid;
    }

    @Basic
    @Column(name = "is_cancel")
    public byte getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(byte isCancel) {
        this.isCancel = isCancel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderEntity that = (OrderEntity) o;

        if (id != that.id) return false;
        if (price != that.price) return false;
        if (isDropped != that.isDropped) return false;
        if (isPaid != that.isPaid) return false;
        if (isCancel != that.isCancel) return false;
        if (studentId != null ? !studentId.equals(that.studentId) : that.studentId != null) return false;
        if (courseId != null ? !courseId.equals(that.courseId) : that.courseId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (studentId != null ? studentId.hashCode() : 0);
        result = 31 * result + (courseId != null ? courseId.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + (int) isDropped;
        result = 31 * result + (int) isPaid;
        result = 31 * result + (int) isCancel;
        return result;
    }
}
