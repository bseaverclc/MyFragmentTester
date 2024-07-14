package com.example.myfragmenttester;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MyGames extends AppCompatActivity {

    ListView listView;
    MyGamesAdapter adapter;
    GsonBuilder gsonb = new GsonBuilder();
    Gson mGson = gsonb.create();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_games);
        System.out.println(AppData.myGames.size());

        Intent intent = getIntent();
        //selectedEvent = (String)intent.getSerializableExtra("Selected");
        //meet = (Meet)intent.getSerializableExtra("meet");

        adapter=new MyGamesAdapter(this, AppData.myGames);
        listView=(ListView)findViewById(R.id.myGamesListView);
        listView.setAdapter(adapter);

        setTitle("My Games");
        attachListener();
//        eventPosition = 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionsmenu, menu);
       // this.menu = menu;
        menu.findItem(R.id.settingsButton).setVisible(false);
        menu.findItem(R.id.saveButton).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.newButton){
            AppData.game = null;
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void attachListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("clicked on my game");

                //If it is labeled as a public game, check if it is in public games
                boolean alert = true;
                if(AppData.myGames.get(position).isPublicGame()) {
                    for (Game g : AppData.publicGames) {
                        if (g.getUid().equals(AppData.myGames.get(position).getUid())) {
                            System.out.println("Found game in public games");
                            alert = false;
                            break;
                        }
                    }
                    if (alert) {
                        AlertDialog.Builder gameGoneAlert = new AlertDialog.Builder(MyGames.this);
                        gameGoneAlert.setTitle("Game is no longer on database.  Converting to private game.");
                        gameGoneAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                AppData.game = AppData.myGames.get(position);
                                AppData.game.setPublicGame(false);
                                AppData.gameChanged = true;
                                finish();
                            }
                        });
                        gameGoneAlert.show();
                    } else {
                        AppData.game = AppData.myGames.get(position);
                        AppData.gameChanged = true;
                        finish();
                    }
                }
                else{
                    AppData.game = AppData.myGames.get(position);
                    AppData.gameChanged = true;
                    finish();
                }

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Long press on myGames");
                AlertDialog.Builder firstServeAlert = new AlertDialog.Builder(MyGames.this);
                firstServeAlert.setTitle("Delete this game from MyGames?");
                firstServeAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //If I remove the game I currently have in the scoreboard
                        if(AppData.myGames.get(position) == AppData.game){
                            AppData.game = null;
                        }

                        if(AppData.myGames.get(position).isPublicGame()){
                            // remove from AppData.publicGames on this device
                            for(int i = 0; i<AppData.publicGames.size(); i++){
                                if(AppData.publicGames.get(i).getUid().equals(AppData.myGames.get(position).getUid())){
                                    System.out.println("removed from public games");
                                    AppData.publicGames.remove(i);
                                    break;
                                }
                            }
                            // remove the game from firebase
                            AppData.myGames.get(position).deleteFromFirebase();
                        }
                        // remove the game from myGames on this device
                        AppData.myGames.remove(position);
                        adapter.notifyDataSetChanged();
                        writeJson2("myGames2");


                    }
                });
                firstServeAlert.setNegativeButton("Cancel", null);
                firstServeAlert.show();



                return true;
            }
        });
    }
    public  boolean writeJson2(String yourSettingName){
        SharedPreferences mSettings = getSharedPreferences("YourPreferenceName", Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSettings.edit();


        Wrapper wrapper = new Wrapper();
//        if (!AppData.savedOnce) {
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
}