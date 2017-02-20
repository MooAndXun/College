package cn.moo.trainingcollege.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by 小春 on 2017/2/20.
 */
@Entity(name = "course")
public class CourseEntity {
    private int id;
    private Timestamp endTime;
    private int price;
    private Timestamp startTime;
    /**
     * 0 未审批
     * 1 审批通过
     * -1 审批拒绝
     */
    private int state;
    private String teacher;
    private String title;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "end_time", nullable = false)
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "price", nullable = false)
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Basic
    @Column(name = "start_time", nullable = false)
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "state", nullable = false)
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Basic
    @Column(name = "teacher", nullable = false, length = 255)
    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseEntity that = (CourseEntity) o;

        if (id != that.id) return false;
        if (price != that.price) return false;
        if (state != that.state) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (teacher != null ? !teacher.equals(that.teacher) : that.teacher != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + state;
        result = 31 * result + (teacher != null ? teacher.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}