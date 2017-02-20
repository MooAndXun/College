package cn.moo.trainingcollege.entity;

import javax.persistence.*;

/**
 * Created by 小春 on 2017/2/20.
 */
@Entity(name = "balance")
public class BalanceEntity {
    private int id;
    private int balance;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "balance", nullable = false)
    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BalanceEntity that = (BalanceEntity) o;

        if (id != that.id) return false;
        if (balance != that.balance) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + balance;
        return result;
    }
}
