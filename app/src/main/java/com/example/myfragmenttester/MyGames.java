package com.example.myfragmenttester;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

    public void attachListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("clicked on my game");
                AppData.game = AppData.myGames.get(position);
                AppData.gameChanged = true;
                finish();

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
}