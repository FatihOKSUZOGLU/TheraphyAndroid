package com.project.theraphy.model;

public class Intern extends therapist{

    private String senior_id;

    public  Intern(){
        super();
    }


    public String getSenior_id() {
        return senior_id;
    }

    public void setSenior_id(String senior_id) {
        this.senior_id = senior_id;
    }
}
