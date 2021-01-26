package com.project.theraphy.model;

import java.io.Serializable;
import java.util.List;

public class therapist extends user implements Serializable {

    private String tid;

    private String specialist;

    private List<String> education;

    private boolean intern;

    private String intern_id;

    private double score;

    private int scoreCount;

    private String about;

    private List<request> requests;

    private List<certificate> certificates;

    private List<String> expertise;

    private List<comment> comments;

    private List<String> patients;

    private List<String> all_patients;



    public therapist(){
        super();

    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }


    public String getabout() {
        return about;
    }

    public void setabout(String about) {
        this.about = about;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public List<request> getRequests() {
        return requests;
    }

    public void setRequests(List<request> requests) {
        this.requests = requests;
    }

    public List<certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<certificate> certificates) {
        this.certificates = certificates;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public List<String> getExpertise() {
        return expertise;
    }

    public void setExpertise(List<String> expertise) {
        this.expertise = expertise;
    }

    public List<comment> getComments() {
        return comments;
    }

    public void setComments(List<comment> comments) {
        this.comments = comments;
    }

    public boolean isIntern() {
        return intern;
    }

    public void setIntern(boolean intern) {
        this.intern = intern;
    }

    public List<String> getPatients() {
        return patients;
    }

    public void setPatients(List<String> patients) {
        this.patients = patients;
    }

    public String getIntern_id() {
        return intern_id;
    }

    public void setIntern_id(String intern_id) {
        this.intern_id = intern_id;
    }

    public int getScoreCount() {
        return scoreCount;
    }

    public void setScoreCount(int scoreCount) {
        this.scoreCount = scoreCount;
    }

    public List<String> getAll_patients() {
        return all_patients;
    }

    public void setAll_patients(List<String> all_patients) {
        this.all_patients = all_patients;
    }

    public List<String> getEducation() {
        return education;
    }

    public void setEducation(List<String> education) {
        this.education = education;
    }
}
