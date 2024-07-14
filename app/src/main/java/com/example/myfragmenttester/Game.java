package com.example.myfragmenttester;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.*;
import java.text.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Game implements Serializable {
    private List<String> teams = new ArrayList<>();

    public List<Integer> getSetWins() {
        return setWins;
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<ASet> getSets() {
        return sets;
    }

    public String getUid() {
        return uid;
    }

    public boolean isPublicGame() {
        return publicGame;
    }

    public int getIntDate() {
        return intDate;
    }

    public Integer getType() {
        return type;
    }

    public List<String> getTeams() {
        return teams;
    }

    public void setType(int newType){type = newType;}
    public void setPublicGame(boolean vis){publicGame = vis;}

    public void setUid(String u){
        uid = u;
    }

    private List<Integer> setWins = Arrays.asList(0, 0);
    private Date date;
    private ArrayList<ASet> sets;
    private String uid = "";
    private boolean publicGame = true;
    private int intDate;
    private Integer type = 0;

    public Game(){
        System.out.println("default constructor being called");
    }



    public Game(String key, Map<String, Object> dict) {
        System.out.println("Key constructor being called");
        if (dict.containsKey("type")) {
            this.type = Math.toIntExact((Long)dict.get("type"));
        } else {
            this.type = 0;
        }
        this.teams = (List<String>) dict.get("teams");
        this.setWins = (List<Integer>) dict.get("setWins");

        if (dict.containsKey("intDate")) {
            this.intDate = Math.toIntExact((Long)dict.get("intDate"));
            this.date = new Date((long) this.intDate * 1000);
        } else {
            DateFormat formatter1 = new SimpleDateFormat("MM/dd/yy HH:mm aa");

            String dateString = (String) dict.get("date");
            String timeString = (String) dict.get("time");
            System.out.println(dateString + " " + timeString);
            String fullDate = dateString + " " + timeString;

            try {
                this.date = formatter1.parse(fullDate);
                this.intDate = (int) (this.date.getTime() / 1000);

                DateFormat dateFormatter = new SimpleDateFormat();
                // dateFormatter.setDateStyle(DateFormat.SHORT);
                //  dateFormatter.setTimeStyle(DateFormat.SHORT);

                String convertedDate = dateFormatter.format(this.date);
                System.out.println(convertedDate);
            } catch (ParseException e) {
                System.out.println("Error Reading date");
                this.date = new Date();
                this.intDate = (int) (this.date.getTime() / 1000);
            }
        }

        this.sets = new ArrayList<>();
        this.uid = key;
    }

    public Game(List<String> teams, Date date, boolean publicGame) {
        System.out.println("List Constructor being called");
        this.teams = teams;

        this.date = date;
        this.sets = new ArrayList<>();
        this.publicGame = publicGame;

        // convert Date to TimeInterval (typealias for Double)
        long timeInterval = this.date.getTime();

        // convert to Integer
        this.intDate = (int) (timeInterval / 1000);
        // uid = "";
    }

    public void updateGame(Map<String, Object> dict) {
        if (dict.containsKey("type")) {
            this.type = Math.toIntExact((Long)dict.get("type"));
        } else {
            this.type = 0;
        }
        teams = (List<String>) dict.get("teams");
        setWins = (List<Integer>) dict.get("setWins");

        if (dict.containsKey("intDate")) {
            this.intDate = Math.toIntExact((Long)dict.get("intDate"));
            this.date = new Date((long) this.intDate * 1000);
        } else {
            DateFormat formatter1 = new SimpleDateFormat("MM/dd/yy HH:mm aa");

            String dateString = (String) dict.get("date");
            String timeString = (String) dict.get("time");
            System.out.println(dateString + " " + timeString);
            String fullDate = dateString + " " + timeString;

            try {
                this.date = formatter1.parse(fullDate);
                this.intDate = (int) (this.date.getTime() / 1000);

                DateFormat dateFormatter = new SimpleDateFormat();
                // dateFormatter.setDateStyle(DateFormat.SHORT);
                //  dateFormatter.setTimeStyle(DateFormat.SHORT);

                String convertedDate = dateFormatter.format(this.date);
                System.out.println(convertedDate);
            } catch (ParseException e) {
                System.out.println("Error Reading date");
                this.date = new Date();
                this.intDate = (int) (this.date.getTime() / 1000);
            }

//            try {
//                date = formatter1.parse(fullDate);
//                intDate = (int) (date.getTime() / 1000);
//
//                SimpleDateFormat dateFormatter = new SimpleDateFormat();
//                dateFormatter.applyPattern("MM/dd/yy HH:mm aa");
//                String convertedDate = dateFormatter.format(date);
//                System.out.println(convertedDate);
//            } catch (ParseException e) {
//                System.out.println("Error Reading date");
//                date = new Date();
//                intDate = (int) (date.getTime() / 1000);
//            }
        }
    }

    public ASet addSet(String key, Map<String, Object> dict) {
        ASet createdSet = new ASet(key, dict);
        sets.add(createdSet);
        return createdSet;
    }

    public void addSet(ASet set) {

        sets.add(set);
    }


  public void saveGameToFirebase(){
          SimpleDateFormat formatter1 = new SimpleDateFormat("M/d/yy");
          String dateString = formatter1.format(date);

          formatter1 = new SimpleDateFormat("h:mm a");
          String timeString = formatter1.format(date);

          DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
          Map<String, Object> dict = new HashMap<>();
          dict.put("type", this.type != null ? this.type : 0);
          dict.put("teams", this.teams);
          dict.put("setWins", this.setWins);
          dict.put("date", dateString);
          dict.put("time", timeString);
          dict.put("publicGame", publicGame);
          dict.put("intDate", intDate);

          DatabaseReference gameRef = ref.child("games").push();
          uid = gameRef.getKey();
          gameRef.setValue(dict);

          for (ASet set : sets) {
              Map<String, Object> setDict = new HashMap<>();
              setDict.put("redStats", set.getRedStats());
              setDict.put("blueStats", set.getBlueStats());
              setDict.put("serve", set.getServe());
              setDict.put("redRotation", set.getRedRotation());
              setDict.put("blueRotation", set.getBlueRotation());
              setDict.put("redRotationPlusMinus", set.getRedRotationPlusMinus());
              setDict.put("blueRotationPlusMinus", set.getBlueRotationPlusMinus());
              //setDict.put("pointHistory", set.getPointHistory());

              setDict.put(("redAttack"), set.getRedAttack());
              setDict.put("redOne", set.getRedOne());
              setDict.put("redTwo", set.getRedTwo());
              setDict.put("redThree", set.getRedThree());
              setDict.put("redDigs", set.getRedDigs());

              setDict.put(("blueAttack"), set.getBlueAttack());
              setDict.put("blueOne", set.getBlueOne());
              setDict.put("blueTwo", set.getBlueTwo());
              setDict.put("blueThree", set.getBlueThree());
              setDict.put("blueDigs", set.getBlueDigs());

              DatabaseReference setsID = gameRef.child("sets").push();
              set.setUid(setsID.getKey());
              setsID.setValue(setDict);

              for (Point point : set.getPointHistory()) {
                  Map<String, Object> pointDict = new HashMap<>();
                  pointDict.put("serve", point.getServe());
                  pointDict.put("redRotation", point.getRedRotation());
                  pointDict.put("blueRotation", point.getBlueRotation());
                  pointDict.put("who", point.getWho());
                  pointDict.put("why", point.getWhy());
                  pointDict.put("score", point.getScore());

                  DatabaseReference pointRef = setsID.child("pointHistory").push();
                  point.setUid(pointRef.getKey());
                  pointRef.setValue(pointDict);
              }
          }
      }



    public void updateFirebase() {
        System.out.println("Calling updateFirebase from Game class");
        if(uid != null && uid.length()!=0){
        SimpleDateFormat formatter1 = new SimpleDateFormat();
        formatter1.applyPattern("MM/dd/yy");
        String dateString = formatter1.format(date);

        formatter1.applyPattern("hh:mm a");
        String timeString = formatter1.format(date);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("games").child(uid);

            Map<String, Object> dict = new HashMap<>();
            dict.put("type", this.type != null ? this.type : 0);
            dict.put("teams", this.teams);
            dict.put("setWins", this.setWins);
            dict.put("date", dateString);
            dict.put("time", timeString);
            dict.put("intDate", intDate);

            ref.updateChildren(dict);
            ref = ref.child("sets");

            for (ASet set : sets) {
                Map<String, Object> setDict = new HashMap<>();
                setDict.put("redStats", set.getRedStats());
                setDict.put("blueStats", set.getBlueStats());
                setDict.put("serve", set.getServe());
                setDict.put("redRotation", set.getRedRotation());
                setDict.put("blueRotation", set.getBlueRotation());
                setDict.put("redRotationPlusMinus", set.getRedRotationPlusMinus());
                setDict.put("blueRotationPlusMinus", set.getBlueRotationPlusMinus());
                //setDict.put("pointHistory", set.getPointHistory());

                setDict.put(("redAttack"), set.getRedAttack());
                setDict.put("redOne", set.getRedOne());
                setDict.put("redTwo", set.getRedTwo());
                setDict.put("redThree", set.getRedThree());
                setDict.put("redDigs", set.getRedDigs());

                setDict.put(("blueAttack"), set.getBlueAttack());
                setDict.put("blueOne", set.getBlueOne());
                setDict.put("blueTwo", set.getBlueTwo());
                setDict.put("blueThree", set.getBlueThree());
                setDict.put("blueDigs", set.getBlueDigs());

                ref.child(set.getUid()).updateChildren(setDict);

                for (Point point : set.getPointHistory()) {
                    Map<String, Object> pointDict = new HashMap<>();
                    pointDict.put("serve", point.getServe());
                    pointDict.put("redRotation", point.getRedRotation());
                    pointDict.put("blueRotation", point.getBlueRotation());
                    pointDict.put("who", point.getWho());
                    pointDict.put("why", point.getWhy());
                    pointDict.put("score", point.getScore());

                    if (point.getUid().isEmpty()) {
                        DatabaseReference pointRef = FirebaseDatabase.getInstance().getReference().child(set.getUid());
                        point.setUid(pointRef.push().getKey());
                        System.out.println("added point with key " + point.getUid());
                    }

                    ref.child(set.getUid()).child("pointHistory").child(point.getUid()).updateChildren(pointDict);
                }
            }

            System.out.println("updating game in firebase");
        }
    }


    public void deleteFromFirebase(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("games").child(uid);
        if(ref != null){
            ref.removeValue();
        }

    }



}




