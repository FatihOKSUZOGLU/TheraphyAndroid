package com.project.theraphy.model;

public class request {

    private String pid;

    private patient r_patient;

    private String tid;

    private String t_name;

    private int request_type;

    private int confirmation; // 0 = waiting // 1 = accept // 2 = reject

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public int getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(int confirmation) {
        this.confirmation = confirmation;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public int getRequest_type() {
        return request_type;
    }

    public void setRequest_type(int request_type) {
        this.request_type = request_type;
    }

    public patient getR_patient() {
        return r_patient;
    }

    public void setR_patient(patient r_patient) {
        this.r_patient = r_patient;
    }

}