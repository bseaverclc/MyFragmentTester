package com.evs.myfragmenttester;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {



    GsonBuilder gsonb = new GsonBuilder();
    Gson mGson = gsonb.create();

    TabLayout tabLayout;
    TabItem homeTab;
    ViewPager2 viewPager2;
    MyViewPageAdapter myViewPageAdapter;

    Menu menu;

    boolean first = true;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        System.out.println("database reference : " + mDatabase);
        readGamesFromFirebase();

        //clearJSON2("myGames2");
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

        viewPager2 = findViewById(R.id.view_pager);
        myViewPageAdapter = new MyViewPageAdapter(this);
        viewPager2.setAdapter(myViewPageAdapter);
        tabLayout = findViewById(R.id.tab_layout);
        homeTab = findViewById(R.id.HomeTab);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
                if(tab.getPosition()>0){
                    ((LinearLayout) tabLayout.getTabAt(0).view).setVisibility(View.GONE);
                }
                if(tab.getPosition() == 1){
                    getSupportActionBar().setTitle(Html.fromHtml("<small>Scoreboard</small>"));

                }
                if(tab.getPosition() == 2){
                    getSupportActionBar().setTitle(Html.fromHtml("<small>Game Stats</small>"));
                }
                if(tab.getPosition() == 3){
                    getSupportActionBar().setTitle(Html.fromHtml("<small>Point History</small>"));
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }



    public void hideTabBar(){
        //tabLayout.setVisibility(View.GONE);
        ((LinearLayout) tabLayout.getTabAt(1).view).setVisibility(View.GONE);
        ((LinearLayout) tabLayout.getTabAt(2).view).setVisibility(View.GONE);
        ((LinearLayout) tabLayout.getTabAt(3).view).setVisibility(View.GONE);
    }
    public void showTabBar(){
        //tabLayout.setVisibility(View.VISIBLE);
        ((LinearLayout) tabLayout.getTabAt(0).view).setVisibility(View.GONE);
        ((LinearLayout) tabLayout.getTabAt(1).view).setVisibility(View.VISIBLE);
        ((LinearLayout) tabLayout.getTabAt(2).view).setVisibility(View.VISIBLE);
        ((LinearLayout) tabLayout.getTabAt(3).view).setVisibility(View.VISIBLE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // always start on scoreboard screen after opening screen has been shown
      if(AppData.game!= null) {

              tabLayout.getTabAt(1).select();

      }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionsmenu, menu);
        this.menu = menu;
        menu.findItem(R.id.settingsButton).setVisible(false);
        menu.findItem(R.id.saveButton).setVisible(false);
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
            if(AppData.canEdit) {
                if (AppData.game != null && AppData.game.isPublicGame()) {

                } else {
                    writeJson2("myGames2");
                }
            }

        }
        if(id == R.id.newButton){
            AlertDialog.Builder newGameAlert = new AlertDialog.Builder(this);
            newGameAlert.setTitle("Are you sure you want to create a new game?");
            newGameAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    tabLayout.getTabAt(1).select();
                    Fragment x = getSupportFragmentManager().findFragmentByTag("f1");
                    ScoreboardFragment y = (ScoreboardFragment) x;
                    AppData.savedOnce = false;
                    // if not first time to scoreboard screen
                    if (y != null) {
                        y.createGame();
                    }
                }
            });

            newGameAlert.setNegativeButton("No", null);
            newGameAlert.show();

        }

        if (id == R.id.settingsButton) {
            // DO your stuff
//            Game readGame = readJSON("myGames");
//            if (readGame!=null) {
//                System.out.println("Read a team from phone " + readGame.getSets().get(0).getPointHistory().size());
//            }
//            else{
//                System.out.println("Read a null from phone");
//            }


//            System.out.println("myGames size:  " + AppData.myGames.size());
//            for(Game pg: AppData.myGames){
//                System.out.println("Red Team " + pg.getTeams().get(0));
//                System.out.println("myGames size:  " + AppData.myGames.size());
//            }

            System.out.println("settings clicked");

            Intent intent = new Intent(this, Settings.class);
            //intent.putExtra("meet", meet);
            startActivity(intent);
            //drawerLayout.closeDrawers();
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
            Collections.sort(AppData.publicGames, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
            Collections.reverse(AppData.publicGames);
            Intent intent = new Intent(this, PublicGames.class);
            //intent.putExtra("meet", meet);
            startActivity(intent);
           drawerLayout.closeDrawers();
        }
        if (id == R.id.myGames) {
            // DO your stuff
            System.out.println("myGames clicked");
            Collections.sort(AppData.myGames, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
            Collections.reverse(AppData.myGames);
            Intent intent = new Intent(this, MyGames.class);
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

    public  boolean writeJson2(String yourSettingName){
        SharedPreferences mSettings = getSharedPreferences("YourPreferenceName", Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSettings.edit();


        Wrapper wrapper = new Wrapper();
//        if (!AppData.savedOnce && AppData.game != null) {
//            AppData.myGames.add(AppData.game);
//            AppData.savedOnce = true;
//        }
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
           if(w!= null){
            return w.dataList;
            }
            else{
            return new ArrayList<Game>();
        }
    }

    public void clearJSON2(String yourSettingName){
        SharedPreferences mSettings = getSharedPreferences("YourPreferenceName", Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSettings.edit();


           // String writeValue = mGson.toJson(wrapper);
            mEditor.putString(yourSettingName, "");
            mEditor.commit();
           // return true;

    }

    private void readGamesFromFirebase(){
        System.out.println("In Read Games from firebase");
        mDatabase.child("games").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                System.out.println("heard game added on firebase");
                String key = snapshot.getKey();


                Map<String, Object> theMap = (Map<String, Object>) snapshot.getValue();
                //System.out.println(key);
                Game aGame = new Game(key, theMap);


                // trying to get everything from the game in one place
//                Map<String, Object> theMapSets = (Map<String, Object>) theMap.get("sets");
//                for (String blah : theMapSets.keySet()) {
//                    ASet theSet = new ASet(blah, (Map<String, Object>)(theMapSets.get(blah)));
//
//                    Map<String, Object> theMapPoints = (Map<String, Object>)((Map<String, Object>)theMapSets.get(blah)).get("pointHistory");
//                    if(theMapPoints != null) {
//                        for (String blah2 : theMapPoints.keySet()) {
//                            theSet.addPoint(blah2, (Map<String, Object>) theMapPoints.get(blah2));
//                        }
//                    }
//                    aGame.addSet(theSet);
//
//                }



                mDatabase.child("games").child(key).child("sets").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot2, @Nullable String previousChildName) {
                        System.out.println("heard set added on firebase");
                        ASet aSet = new ASet(snapshot2.getKey(), (Map<String, Object>)snapshot2.getValue());
                        aGame.addSet(aSet);

                        mDatabase.child("games").child(key).child("sets").child(snapshot2.getKey()).child("pointHistory").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot3, @Nullable String previousChildName) {
                                System.out.println("heard Point added on firebase");
                                if(aSet.getPointHistory() != null) {
                                    for (Point p : aSet.getPointHistory()) {
                                        if (p.getUid() == snapshot3.getKey()) {
                                            return;
                                        }
                                    }
                                }
                                aSet.addPoint(snapshot3.getKey(), (Map <String,Object>) snapshot3.getValue());
                                System.out.println("aSet redPoints: " + aSet.getRedStats().get("redScore"));
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot4, @Nullable String previousChildName) {


                                for(Point p: aSet.getPointHistory()) {
                                    if (p.getUid().equals(snapshot4.getKey())) {
                                        p.updatePoint((Map<String,Object>)snapshot4.getValue());
                                        System.out.println("heard a point change");
                                        break;
                                    }
                                }



                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                                String pKey = snapshot.getKey();
                                if(AppData.game != null && AppData.game.getUid().equals(aGame.getUid()) && AppData.canEdit){
                                    // I made the change, so do nothing
                                    return;
                                }
                                for(int i = aSet.getPointHistory().size() -1; i >=0; i--){
                                    if(aSet.getPointHistory().get(i).getUid().equals(pKey)){
                                        aSet.getPointHistory().remove(i);
                                        System.out.println("heard point removed");
                                        //updateScreen();
                                        break;
                                    }
                                }
                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        // set changed
                        String setKey = snapshot.getKey();
//                        if(AppData.game != null && AppData.game.getUid().equals(aGame.getUid()) && AppData.canEdit){
//                            // I made the change to the set, so do nothing
//                            return;
//                        }
                        for(ASet s: aGame.getSets()){
                            if(s.getUid().equals(setKey)){
                                s.updateSet((Map<String, Object>)snapshot.getValue());

                                System.out.println("heard a set change");
//                                if(AppData.game != null && AppData.game.getUid().equals(aGame.getUid())){
//                                    if(ScoreboardFragment.scoreboardFragment != null) {
//                                        ScoreboardFragment.scoreboardFragment.updateScreen();
//                                    }
//
//                                }
//                                if(PublicGamesAdapter.publicGamesAdapter != null){
//                                    PublicGamesAdapter.publicGamesAdapter.notifyDataSetChanged();
//                                }
//                                Intent intent = new Intent();
//                                intent.setAction("setUpdated");
//                                getContext().sendBroadcast(intent);
                            }
                        }
                        // ASet aSet = new ASet(setKey, (Map<String, Object>)snapshot.getValue());

//                        mDatabase.child("games").child(key).child("sets").child(setKey).child("pointHistory").addChildEventListener(new ChildEventListener() {
//                            @Override
//                            public void onChildAdded(@NonNull DataSnapshot snapshot3, @Nullable String previousChildName) {
//                                System.out.println("Adding points to changed set");
//                                if (aSet.getPointHistory() != null) {
//                                    if (AppData.game.getUid().equals())
//                                }
//                                for (Point p : aSet.getPointHistory()) {
//                                    if (p.getUid() == snapshot3.getKey()) {
//                                        return;
//                                    }
//                                }
//
//                                aSet.addPoint(snapshot3.getKey(), (Map<String, Object>) snapshot3.getValue());
//                                System.out.println("aSet redPoints: " + aSet.getRedStats().get("redScore"));
//                            }
//                            });


                        //aGame.addSet(aSet);



                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                AppData.publicGames.add(aGame);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                String key = snapshot.getKey();
                if(AppData.game != null && AppData.game.getUid().equals(key) && AppData.canEdit){
                    // I made the change, so do nothing
                    return;
                }
                else{
                for(Game g: AppData.publicGames){
                    if(g.getUid().equals(key)){
                        g.updateGame((Map<String,Object>)snapshot.getValue());
                        System.out.println("Heard a game change");
                        //If I'm on the scoreboard screen and on this game, refresh the screen
                        if(AppData.game != null && AppData.game.getUid().equals(g.getUid())){
                            if(ScoreboardFragment.scoreboardFragment != null) {
                                ScoreboardFragment.scoreboardFragment.updateScreen();
                            }

                        }
                        //If I am on the public games screen, refresh the screen
                        if(PublicGamesAdapter.publicGamesAdapter != null){
                            PublicGamesAdapter.publicGamesAdapter.notifyDataSetChanged();
                        }
                        break;
                    }
                }
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                System.out.println("Heard game removed");
                for(int i = 0; i<AppData.publicGames.size(); i++){
                    Game g = AppData.publicGames.get(i);
                    if(g.getUid().equals(snapshot.getKey())){
                        AppData.publicGames.remove(i);
                        System.out.println("removed game");
                        if(AppData.game != null && AppData.game.getUid().equals(g.getUid())){
                            if(ScoreboardFragment.scoreboardFragment != null) {
                                //ScoreboardFragment.scoreboardFragment.createGame();
                                ScoreboardFragment.scoreboardFragment.gameGoneAlert();
                            }

                        }
                        //If I am on the public games screen, refresh the screen
                        if(PublicGamesAdapter.publicGamesAdapter != null){
                            PublicGamesAdapter.publicGamesAdapter.notifyDataSetChanged();
                        }
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}