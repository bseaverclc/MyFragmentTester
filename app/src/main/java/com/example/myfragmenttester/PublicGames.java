package com.example.myfragmenttester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class PublicGames extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_games);
        System.out.println(AppData.publicGames.size());

        Intent intent = getIntent();
        //selectedEvent = (String)intent.getSerializableExtra("Selected");
        //meet = (Meet)intent.getSerializableExtra("meet");

        PublicGamesAdapter adapter=new PublicGamesAdapter(this, AppData.publicGames);
        listView=(ListView)findViewById(R.id.publicGamesListView);
        listView.setAdapter(adapter);

        setTitle("Public Games");
       attachListener();
//        eventPosition = 0;
    }

    public void attachListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("clicked on a public game");
                AppData.game = AppData.publicGames.get(position);
                AppData.gameChanged = true;
                finish();

            }
        });
    }
}