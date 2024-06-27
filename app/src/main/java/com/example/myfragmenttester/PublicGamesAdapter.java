package com.example.myfragmenttester;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class PublicGamesAdapter extends ArrayAdapter<Game> {

    private ArrayList<Game> theGames;


    public PublicGamesAdapter(@NonNull Context context, ArrayList<Game> games) {
        super(context, R.layout.custom_publicgames, games);
        theGames = games;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView = inflater.inflate(R.layout.custom_publicgames, null, true);


        TextView redTeamName = rowView.findViewById(R.id.publicGameRedTeam);
        redTeamName.setText(theGames.get(position).getTeams().get(0));

        return rowView;
    }
}
