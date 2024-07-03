package com.example.myfragmenttester;

import android.graphics.Color;

import com.google.firebase.database.collection.LLRBNode;

import java.util.ArrayList;

public class AppData {

    public static Game game;
    public static int selectedSet = 0;
    public static boolean canEdit = true;

    public static ArrayList<Game> publicGames = new ArrayList<Game>();
    public static ArrayList<Game> myGames = new ArrayList<Game>();

    public static int gameRedTeamColor = Color.RED;
    public static int gameBlueTeamColor = Color.rgb(200,200,255);

    public static boolean gameChanged = false;
    public static boolean savedOnce = false;
}
