package com.hrm.entity;

import java.io.Serializable;

public class Counter implements Serializable {
    private String name;
    private Integer count;

    public Counter () {
        super();
    }

    public Counter(String name, Integer count) {
        super();
        setName(name);
        setCount(count);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String toString() {
        return "Counter [name=" + name + ", count=" + count + "]";
    }
}
