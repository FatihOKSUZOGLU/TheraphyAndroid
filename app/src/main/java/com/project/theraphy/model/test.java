package com.project.theraphy.model;

import java.io.Serializable;
import java.util.List;

public class test implements Serializable {
    private String testname;
    private String testid;
    private List<test_item> items;

    public String getTestid() {
        return testid;
    }

    public void setTestid(String testid) {
        this.testid = testid;
    }

    public List<test_item> getItems() {
        return items;
    }

    public void setItems(List<test_item> items) {
        this.items = items;
    }

    public String getTestname() {
        return testname;
    }

    public void setTestname(String testname) {
        this.testname = testname;
    }
}
