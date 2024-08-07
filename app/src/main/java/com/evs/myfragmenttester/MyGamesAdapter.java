package com.evs.myfragmenttester;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MyGamesAdapter extends ArrayAdapter<Game> {

    private ArrayList<Game> theGames;


    public MyGamesAdapter(@NonNull Context context, ArrayList<Game> games) {
        super(context, R.layout.custom_my_games, games);
        theGames = games;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView = inflater.inflate(R.layout.custom_my_games, null, true);



        Game rowGame = theGames.get(position);

        TextView redTeamName = rowView.findViewById(R.id.publicGameRedTeam);
        redTeamName.setText(theGames.get(position).getTeams().get(0));
        TextView blueTeamName = rowView.findViewById(R.id.publicGameBlueTeam);
        blueTeamName.setText(theGames.get(position).getTeams().get(1));

        TextView redSet1 = rowView.findViewById(R.id.redSet1);
        redSet1.setText("" + theGames.get(position).getSets().get(0).getRedStats().get("redScore"));
        TextView redSet2 = rowView.findViewById(R.id.redSet2);
        redSet2.setText("" + theGames.get(position).getSets().get(1).getRedStats().get("redScore"));
        TextView redSet3 = rowView.findViewById(R.id.redSet3);
        redSet3.setText("" + theGames.get(position).getSets().get(2).getRedStats().get("redScore"));
        TextView redSet4 = rowView.findViewById(R.id.redSet4);
        redSet4.setText("" + theGames.get(position).getSets().get(3).getRedStats().get("redScore"));
        TextView redSet5 = rowView.findViewById(R.id.redSet5);
        redSet5.setText("" + theGames.get(position).getSets().get(4).getRedStats().get("redScore"));

        TextView blueSet1 = rowView.findViewById(R.id.blueSet1);
        blueSet1.setText("" + theGames.get(position).getSets().get(0).getBlueStats().get("blueScore"));
        TextView blueSet2 = rowView.findViewById(R.id.blueSet2);
        blueSet2.setText("" + theGames.get(position).getSets().get(1).getBlueStats().get("blueScore"));
        TextView blueSet3 = rowView.findViewById(R.id.blueSet3);
        blueSet3.setText("" + theGames.get(position).getSets().get(2).getBlueStats().get("blueScore"));
        TextView blueSet4 = rowView.findViewById(R.id.blueSet4);
        blueSet4.setText("" + theGames.get(position).getSets().get(3).getBlueStats().get("blueScore"));
        TextView blueSet5 = rowView.findViewById(R.id.blueSet5);
        blueSet5.setText("" + theGames.get(position).getSets().get(4).getBlueStats().get("blueScore"));



        TextView dateView = rowView.findViewById(R.id.publicDate);
        DateFormat dateFormatter = new SimpleDateFormat();
        // dateFormatter.setDateStyle(DateFormat.SHORT);
        //  dateFormatter.setTimeStyle(DateFormat.SHORT);

        String convertedDate = dateFormatter.format(rowGame.getDate());

        dateView.setText("" + convertedDate);
        return rowView;
    }
}
