package com.example.myfragmenttester;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class GameStatsAdapter extends ArrayAdapter<ASet> {

    private ArrayList<ASet> theSets;
    private TextView redTeamName, setNumber, blueTeamName;
    private TextView redPoints, bluePoints;
    private TextView redKills, blueKills;
    private TextView redBlocks, blueBlocks;

    public GameStatsAdapter(@NonNull Context context, ArrayList<ASet> sets) {
        super(context, R.layout.custom_gamestats_view, sets);
        theSets = sets;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView = inflater.inflate(R.layout.custom_gamestats_view, null, true);


            redTeamName = rowView.findViewById(R.id.gameStatsRedTeamName);
            redTeamName.setText(AppData.game.getTeams().get(0));
            setNumber = rowView.findViewById(R.id.gameStatsSetNumber);
            setNumber.setText("Set #" + (position + 1));
            blueTeamName = rowView.findViewById(R.id.gameStatsBlueTeamName);
            blueTeamName.setText(AppData.game.getTeams().get(1));

            redPoints = rowView.findViewById(R.id.gameStatsRedPoints);
            redPoints.setText("" + theSets.get(position).getRedStats().get("redScore"));
            bluePoints = rowView.findViewById(R.id.gameStatsBluePoints);
            bluePoints.setText("" + theSets.get(position).getBlueStats().get("blueScore"));

            redKills = rowView.findViewById(R.id.gameStatsRedKills);
            redKills.setText("" + theSets.get(position).getRedStats().get("Kill"));
            blueKills = rowView.findViewById(R.id.gameStatsBlueKills);
            blueKills.setText("" + theSets.get(position).getBlueStats().get("Kill"));

            redBlocks = rowView.findViewById(R.id.gameStatsRedBlocks);
            redBlocks.setText("" + theSets.get(position).getRedStats().get("Block"));
            blueBlocks = rowView.findViewById(R.id.gameStatsBlueBlocks);
            blueBlocks.setText("" + theSets.get(position).getBlueStats().get("Block"));




        return rowView;
    }




}
