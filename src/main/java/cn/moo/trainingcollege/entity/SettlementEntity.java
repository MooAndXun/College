package cn.moo.trainingcollege.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by 小春 on 2017/2/20.
 */
@Entity(name = "settlement")
public class SettlementEntity {
    private int id;
    private String organizationId;
    private int courseId;
    private int money;
    private int commission;
    private Timestamp createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "organization_id", nullable = false, length = 255)
    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    @Basic
    @Column(name = "course_id", nullable = false)
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "money", nullable = false)
    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Basic
    @Column(name = "commission", nullable = false)
    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }

    @Basic
    @Column(name = "created_at", nullable = false)
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

        SettlementEntity that = (SettlementEntity) o;

        if (id != that.id) return false;
        if (courseId != that.courseId) return false;
        if (money != that.money) return false;
        if (commission != that.commission) return false;
        if (organizationId != null ? !organizationId.equals(that.organizationId) : that.organizationId != null)
            return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (organizationId != null ? organizationId.hashCode() : 0);
        result = 31 * result + courseId;
        result = 31 * result + money;
        result = 31 * result + commission;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
