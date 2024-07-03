package com.example.myfragmenttester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MyGames extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_games);
        System.out.println(AppData.myGames.size());

        Intent intent = getIntent();
        //selectedEvent = (String)intent.getSerializableExtra("Selected");
        //meet = (Meet)intent.getSerializableExtra("meet");

        MyGamesAdapter adapter=new MyGamesAdapter(this, AppData.myGames);
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
    }
}