package com.hrm.entity;


import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class Train implements Serializable{
    private Integer id;
    private String name;
    private String remark;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date;
    private Employee teacher;

    public Train() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date createDate) {
        this.date = createDate;
    }

    public Employee getTeacher() {
        return teacher;
    }

    public void setTeacher(Employee employee) {
        this.teacher = employee;
    }

    public String toString() {
        return "Train [id=" + id + ", name=" + name + ", remark=" + remark
                + ", createDate=" + date + ", employee=" + teacher + "]";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName () {
        return this.name;
    }
}