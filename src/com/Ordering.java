package com;

import java.util.Date;

/**
 * Created by SOTRUDNIK on 25.06.2017.
 */
public class Ordering {

    private Integer id_or;
    private String description;
    private String customerInt;
    private Date createddate, enddate;
    private String cost;
    private String status;

    public Ordering(Integer id_or, String description, String customerInt, Date createddate, Date enddate, String cost, String status) {
        this.id_or = id_or;
        this.description = description;
        this.customerInt = customerInt;
        this.createddate = createddate;
        this.enddate = enddate;
        this.cost = cost;
        this.status = status;
    }

    public Ordering() {
    }

    public Integer getId_or() {
        return id_or;
    }

    public void setId_or(Integer id_or) {
        this.id_or = id_or;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomerInt() {
        return customerInt;
    }

    public void setCustomerInt(String customerInt) {
        this.customerInt = customerInt;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ordering)) return false;

        Ordering ordering = (Ordering) o;

        if (!getId_or().equals(ordering.getId_or())) return false;
        if (!getDescription().equals(ordering.getDescription())) return false;
        if (!getCustomerInt().equals(ordering.getCustomerInt())) return false;
        return getCost().equals(ordering.getCost());
    }

    @Override
    public int hashCode() {
        int result = getId_or().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getCustomerInt().hashCode();
        result = 31 * result + getCost().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Ordering{" +
                "id_or=" + id_or +
                ", description='" + description + '\'' +
                ", customerInt=" + customerInt +
                ", createddate=" + createddate +
                ", enddate=" + enddate +
                ", cost=" + cost +
                ", status=" + status +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return (Ordering) super.clone();
    }
}
