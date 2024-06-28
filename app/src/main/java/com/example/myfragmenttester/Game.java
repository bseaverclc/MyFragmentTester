package com.example.myfragmenttester;

import java.io.Serializable;
import java.util.*;
import java.text.*;

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
        if (dict.get("type") instanceof Integer) {
            type = (int) dict.get("type");
        } else {
            type = 0;
        }
        teams = (List<String>) dict.get("teams");
        setWins = (List<Integer>) dict.get("setWins");

        if (dict.get("intDate") instanceof Integer) {
            intDate = (int) dict.get("intDate");
            date = new Date((long) intDate * 1000);
        } else {
            SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yy HH:mm aa");
            String dateString = (String) dict.get("date");
            String timeString = (String) dict.get("time");
            System.out.println(dateString + " " + timeString);
            String fullDate = dateString + " " + timeString;

            try {
                date = formatter1.parse(fullDate);
                intDate = (int) (date.getTime() / 1000);

                SimpleDateFormat dateFormatter = new SimpleDateFormat();
                dateFormatter.applyPattern("MM/dd/yy HH:mm aa");
                String convertedDate = dateFormatter.format(date);
                System.out.println(convertedDate);
            } catch (ParseException e) {
                System.out.println("Error Reading date");
                date = new Date();
                intDate = (int) (date.getTime() / 1000);
            }
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
}




