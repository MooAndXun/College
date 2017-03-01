package cn.moo.trainingcollege.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by 小春 on 2017/2/21.
 */
@Entity(name = "course")
public class CourseEntity {
    private int id;
    private Timestamp endTime;
    private double price;
    private Timestamp startTime;
    /**
     * 0 未审批
     * 1 审批通过
     * -1 审批拒绝
     */
    private int state;
    private String teacher;
    private String name;
    private String organId;
    private OrganizationEntity organizationByOrganId;
    private Collection<OrderAccountEntity> orderAccountsById;
    private Collection<OrderCashEntity> orderCashesById;
    private Collection<SettlementEntity> settlementsById;

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
    @Column(name = "price", nullable = false, precision = 0)
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "organ_id", nullable = false, length = 255)
    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseEntity that = (CourseEntity) o;

        if (id != that.id) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (state != that.state) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (teacher != null ? !teacher.equals(that.teacher) : that.teacher != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (organId != null ? !organId.equals(that.organId) : that.organId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + state;
        result = 31 * result + (teacher != null ? teacher.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (organId != null ? organId.hashCode() : 0);
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
}
