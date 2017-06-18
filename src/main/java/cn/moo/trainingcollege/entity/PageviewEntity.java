package cn.moo.trainingcollege.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by chenmuen on 2017/6/19.
 */
@Entity
@Table(name = "pageview", schema = "training", catalog = "")
public class PageviewEntity {
    private int id;
    private int num;
    private Timestamp recordDate;
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
    @Column(name = "num")
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Basic
    @Column(name = "record_date")
    public Timestamp getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Timestamp recordDate) {
        this.recordDate = recordDate;
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

        PageviewEntity that = (PageviewEntity) o;

        if (id != that.id) return false;
        if (num != that.num) return false;
        if (recordDate != null ? !recordDate.equals(that.recordDate) : that.recordDate != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + num;
        result = 31 * result + (recordDate != null ? recordDate.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
