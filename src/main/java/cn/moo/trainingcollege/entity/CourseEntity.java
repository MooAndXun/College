package cn.moo.trainingcollege.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by chenmuen on 2017/3/1.
 */
@Entity
@Table(name = "course", schema = "training", catalog = "")
public class CourseEntity {
    private int id;
    private String teacher;
    private String organId;
    private String endTime;
    private String startTime;
    private double price;
    private int state;
    private String name;
    private String description;
    private OrganizationEntity organizationByOrganId;
    private Collection<OrderAccountEntity> orderAccountsById;
    private Collection<OrderCashEntity> orderCashesById;
    private Collection<SettlementEntity> settlementsById;
    private boolean isSettled;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "teacher")
    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Basic
    @Column(name = "organ_id")
    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    @Basic
    @Column(name = "end_time")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "start_time")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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
    @Column(name = "state")
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseEntity that = (CourseEntity) o;

        if (id != that.id) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (state != that.state) return false;
        if (teacher != null ? !teacher.equals(that.teacher) : that.teacher != null) return false;
        if (organId != null ? !organId.equals(that.organId) : that.organId != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (teacher != null ? teacher.hashCode() : 0);
        result = 31 * result + (organId != null ? organId.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + state;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "organ_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    public OrganizationEntity getOrganizationByOrganId() {
        return organizationByOrganId;
    }

    public void setOrganizationByOrganId(OrganizationEntity organizationByOrganId) {
        this.organizationByOrganId = organizationByOrganId;
    }

    @OneToMany(mappedBy = "courseByCourseId")
    public Collection<OrderAccountEntity> getOrderAccountsById() {
        return orderAccountsById;
    }

    public void setOrderAccountsById(Collection<OrderAccountEntity> orderAccountsById) {
        this.orderAccountsById = orderAccountsById;
    }

    @OneToMany(mappedBy = "courseByCourseId")
    public Collection<OrderCashEntity> getOrderCashesById() {
        return orderCashesById;
    }

    public void setOrderCashesById(Collection<OrderCashEntity> orderCashesById) {
        this.orderCashesById = orderCashesById;
    }

    @OneToMany(mappedBy = "courseByCourseId")
    public Collection<SettlementEntity> getSettlementsById() {
        return settlementsById;
    }

    public void setSettlementsById(Collection<SettlementEntity> settlementsById) {
        this.settlementsById = settlementsById;
    }

    @Basic
    @Column(name = "is_settled")
    public boolean isSettled() {
        return isSettled;
    }

    public void setSettled(boolean settled) {
        isSettled = settled;
    }
}
