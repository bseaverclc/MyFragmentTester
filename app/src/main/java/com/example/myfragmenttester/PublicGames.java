package com.example.myfragmenttester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class PublicGames extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_games);

        Intent intent = getIntent();
        //selectedEvent = (String)intent.getSerializableExtra("Selected");
        //meet = (Meet)intent.getSerializableExtra("meet");

        PublicGamesAdapter adapter=new PublicGamesAdapter(this, AppData.publicGames);
        listView=(ListView)findViewById(R.id.publicGamesListView);
        listView.setAdapter(adapter);

        setTitle("Public Games");
//        attachListener();
//        eventPosition = 0;
    }
}