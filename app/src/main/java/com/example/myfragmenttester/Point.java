package com.example.myfragmenttester;


//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
import java.util.Map;

public class Point {
    private String serve;
    private int redRotation;
    private int blueRotation;
    private String who;
    private String why;
    private String score;
    private String uid = "";

    public Point(String serve, int redRotation, int blueRotation, String who, String why, String score) {
        this.serve = serve;
        this.redRotation = redRotation;
        this.blueRotation = blueRotation;
        this.who = who;
        this.why = why;
        this.score = score;
    }

    public Point(String key, Map<String, Object> dict) {
        this.uid = key;
        this.serve = (String) dict.get("serve");
        this.redRotation = Math.toIntExact((Long)dict.get("redRotation"));
        this.blueRotation = Math.toIntExact((Long)dict.get("blueRotation"));
        this.who = (String) dict.get("who");
        this.why = (String) dict.get("why");
        this.score = (String) dict.get("score");
    }

    @Override
    public String toString() {
        return "Point{" +
                "serve='" + serve + '\'' +
                ", redRotation=" + redRotation +
                ", blueRotation=" + blueRotation +
                ", who='" + who + '\'' +
                ", why='" + why + '\'' +
                ", score='" + score + '\'' +
                '}';
    }

    public String getServe() {
        return serve;
    }

    public void setServe(String serve) {
        this.serve = serve;
    }

    public int getRedRotation() {
        return redRotation;
    }

    public void setRedRotation(int redRotation) {
        this.redRotation = redRotation;
    }

    public int getBlueRotation() {
        return blueRotation;
    }

    public void setBlueRotation(int blueRotation) {
        this.blueRotation = blueRotation;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getWhy() {
        return why;
    }

    public void setWhy(String why) {
        this.why = why;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    // Getters and Setters (if needed)
}

