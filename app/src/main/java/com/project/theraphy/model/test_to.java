package com.project.theraphy.model;

import java.io.Serializable;
import java.util.List;

public class test_to implements Serializable {
    private boolean resulted;
    private List<String> test_results;
    private String test_pid;
    private String test_name;
    private List<test_item> test;

    public boolean isResulted() {
        return resulted;
    }

    public void setResulted(boolean resulted) {
        this.resulted = resulted;
    }

    public List<String> getTest_results() {
        return test_results;
    }

    public void setTest_results(List<String> test_results) {
        this.test_results = test_results;
    }

    public String getTest_pid() {
        return test_pid;
    }

    public void setTest_pid(String test_pid) {
        this.test_pid = test_pid;
    }

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public List<test_item> getTest() {
        return test;
    }

    public void setTest(List<test_item> test) {
        this.test = test;
    }
    public void setAnswer(String answer, int position) {
        this.test.get(position).setAnswer(answer);
    }
}
