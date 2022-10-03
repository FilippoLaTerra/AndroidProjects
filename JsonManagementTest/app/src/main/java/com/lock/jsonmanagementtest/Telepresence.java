package com.lock.jsonmanagementtest;


public class Telepresence {

    String _id, robot, user, who, where, whom, email, when, zoom, start, join;
    int duration, __v;

    public Telepresence(){
        super();
    }

    public Telepresence(String _id, String robot, String user, String who, String where, String whom, String email, String when, String zoom, String start, String join, int duration, int __v) {
        this._id = _id;
        this.robot = robot;
        this.user = user;
        this.who = who;
        this.where = where;
        this.whom = whom;
        this.email = email;
        this.when = when;
        this.zoom = zoom;
        this.start = start;
        this.join = join;
        this.duration = duration;
        this.__v = __v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getRobot() {
        return robot;
    }

    public void setRobot(String robot) {
        this.robot = robot;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getWhom() {
        return whom;
    }

    public void setWhom(String whom) {
        this.whom = whom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public String getZoom() {
        return zoom;
    }

    public void setZoom(String zoom) {
        this.zoom = zoom;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getJoin() {
        return join;
    }

    public void setJoin(String join) {
        this.join = join;
    }

    @Override
    public String toString() {
        return "Telepresence{" +
                "_id='" + _id + '\'' +
                ", robot='" + robot + '\'' +
                ", user='" + user + '\'' +
                ", who='" + who + '\'' +
                ", where='" + where + '\'' +
                ", whom='" + whom + '\'' +
                ", email='" + email + '\'' +
                ", when='" + when + '\'' +
                ", zoom='" + zoom + '\'' +
                ", start='" + start + '\'' +
                ", join='" + join + '\'' +
                ", duration=" + duration +
                ", __v=" + __v +
                '}';
    }
}

