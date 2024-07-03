package com.example.myfragmenttester;



import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {



    GsonBuilder gsonb = new GsonBuilder();
    Gson mGson = gsonb.create();

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    MyViewPageAdapter myViewPageAdapter;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppData.myGames = readJSON2("myGames2");

        setContentView(R.layout.activity_main);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().hide();
//        }

        drawerLayout = findViewById(R.id.my_drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        NavigationView mNavigationView = (NavigationView) findViewById(R.id.navigationView);

        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("EVS");


        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);
        myViewPageAdapter = new MyViewPageAdapter(this);
        viewPager2.setAdapter(myViewPageAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionsmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            System.out.println(item.getItemId());
            return true;
        }
        int id = item.getItemId();
        if(id == R.id.saveButton){
            writeJson2("myGames2");
        }
        if(id == R.id.newButton){
            tabLayout.getTabAt(0).select();
            viewPager2.setCurrentItem(0);
          //  (ScoreboardFragment)(viewPager2.getFocusedChild()).create
        Fragment x = getSupportFragmentManager().findFragmentByTag("f0");
        ScoreboardFragment y = (ScoreboardFragment) x;
        AppData.savedOnce = false;
        y.createGame();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if (id == R.id.publicGames) {
            // DO your stuff
            System.out.println("public games clicked");
            AppData.publicGames.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
            Collections.reverse(AppData.publicGames);
            Intent intent = new Intent(this, PublicGames.class);
            //intent.putExtra("meet", meet);
            startActivity(intent);
           drawerLayout.closeDrawers();
        }
        if (id == R.id.myGames) {
            // DO your stuff
            System.out.println("myGames clicked");
            AppData.myGames.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
            Collections.reverse(AppData.myGames);
            Intent intent = new Intent(this, MyGames.class);
            //intent.putExtra("meet", meet);
            startActivity(intent);
            drawerLayout.closeDrawers();
        }
        if (id == R.id.settings) {
            // DO your stuff
//            Game readGame = readJSON("myGames");
//            if (readGame!=null) {
//                System.out.println("Read a team from phone " + readGame.getSets().get(0).getPointHistory().size());
//            }
//            else{
//                System.out.println("Read a null from phone");
//            }


            System.out.println("myGames size:  " + AppData.myGames.size());
            for(Game pg: AppData.myGames){
                System.out.println("Red Team " + pg.getTeams().get(0));
                System.out.println("myGames size:  " + AppData.myGames.size());
            }

            System.out.println("settings clicked");

            Intent intent = new Intent(this, Settings.class);
            //intent.putExtra("meet", meet);
            startActivity(intent);
            drawerLayout.closeDrawers();
        }

        return true;
    }



    public boolean writeJSON(Game g, String yourSettingName)
    {
        SharedPreferences mSettings = getSharedPreferences("YourPreferenceName", Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSettings.edit();
        try {
            String writeValue = mGson.toJson(g);
            mEditor.putString(yourSettingName, writeValue);
            mEditor.commit();
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public Game readJSON(String yourSettingName)
    {
        SharedPreferences mSettings = getSharedPreferences("YourPreferenceName", Context.MODE_PRIVATE);

        String loadValue = mSettings.getString(yourSettingName, "");
        Game g = mGson.fromJson(loadValue, Game.class);
        return g;
    }

    public boolean writeJson2(String yourSettingName){
        SharedPreferences mSettings = getSharedPreferences("YourPreferenceName", Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSettings.edit();


        Wrapper wrapper = new Wrapper();
        if (!AppData.savedOnce) {
            AppData.myGames.add(AppData.game);
            AppData.savedOnce = true;
        }
        wrapper.setDataList(AppData.myGames);

        try {
            String writeValue = mGson.toJson(wrapper);
            mEditor.putString(yourSettingName, writeValue);
            mEditor.commit();
            return true;
        }
        catch(Exception e)
        {
            return false;
        }

    }

    public ArrayList<Game> readJSON2(String yourSettingName)
    {
        SharedPreferences mSettings = getSharedPreferences("YourPreferenceName", Context.MODE_PRIVATE);

        String loadValue = mSettings.getString(yourSettingName, "");
        Wrapper w = mGson.fromJson(loadValue, Wrapper.class);
        return w.dataList;
    }
}