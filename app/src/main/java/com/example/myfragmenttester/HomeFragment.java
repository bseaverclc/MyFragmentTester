package com.example.myfragmenttester;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    Button createGame, viewPublic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ((MainActivity)getActivity()).hideTabBar();
        createGame = v.findViewById(R.id.createGameButton);
        viewPublic = v.findViewById(R.id.viewPublicButton);

        createGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).tabLayout.getTabAt(1).select();
//                Fragment x = getSupportFragmentManager().findFragmentByTag("f1");
//                ScoreboardFragment y = (ScoreboardFragment) x;
//                AppData.savedOnce = false;
//                // if not first time to scoreboard screen
//                if (y!=null) {
//                    y.createGame();
//                }
            }
        });

        viewPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppData.publicGames.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
                Collections.reverse(AppData.publicGames);
                Intent intent = new Intent(getActivity(), PublicGames.class);
                //intent.putExtra("meet", meet);
                startActivity(intent);
            }
        });

        return v;


    }


}