package com.project.theraphy.model;

import java.util.List;

public class patient extends user {
    private String pid;

    private String current_t;

    private List<request> requests;

    private int chat_sessions_count;

    private String current_chatid;

    private String about;

    public patient(){
        super();

    }
    public patient(String _name,int _age , String _mail, String _phone, String _password ,
                   String _gender , String _photoURL , String _pid , String _current_t ,
                   List<request> _requests , int _chat_sessions, String _current_chatid,
                   String _about){
        super();
        this.setName(_name);
        this.setAge(_age);
        this.setEmail(_mail);
        this.setPhone(_phone);
        this.setPassword(_password);
        this.setGender(_gender);
        this.setphotourl(_photoURL);
        this.pid=_pid;
        this.current_t=_current_t;
        this.requests=_requests;
        this.chat_sessions_count=_chat_sessions;
        this.current_chatid=_current_chatid;
        this.about=_about;
    }

    public String getId() {
        return pid;
    }

    public void setId(String id) {
        this.pid = id;
    }

    public String getCurrent_t() {
        return current_t;
    }

    public void setCurrent_t(String current_t) {
        this.current_t = current_t;
    }

    public List<request> getRequests() {
        return requests;
    }

    public void setRequests(List<request> _requests) {
        this.requests = _requests;
    }

    public void addrequest(request _requests) {
        requests.add(_requests);
    }



    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getCurrent_chatid() {
        return current_chatid;
    }

    public void setCurrent_chatid(String current_chatid) {
        this.current_chatid = current_chatid;
    }

    public int getChat_sessions_count() {
        return chat_sessions_count;
    }

    public void setChat_sessions_count(int chat_sessions_count) {
        this.chat_sessions_count = chat_sessions_count;
    }
}
