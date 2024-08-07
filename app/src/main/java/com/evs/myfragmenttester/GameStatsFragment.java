package com.evs.myfragmenttester;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameStatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameStatsFragment extends Fragment {

    private ListView listView;
    private GameStatsAdapter gameStatsAdapter;
    private View theView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_game_stats, container, false);
        theView = v;

        listView = theView.findViewById(R.id.gamestatslistview);

        return v;


    }

    @Override
    public void onResume() {
        super.onResume();


        gameStatsAdapter = new GameStatsAdapter(getContext(), AppData.game.getSets());
        listView.setAdapter(gameStatsAdapter);



    }



}