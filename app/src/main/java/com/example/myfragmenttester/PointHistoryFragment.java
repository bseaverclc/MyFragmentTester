package com.example.myfragmenttester;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PointHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PointHistoryFragment extends Fragment {

 private ListView listView;
 private PointsHistoryAdapter pointsHistoryAdapter;
 private View theView;

 private TextView redTextView, blueTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_point_history, container, false);
        theView = v;
        return v;


    }

    @Override
    public void onResume() {
        super.onResume();
        listView = theView.findViewById(R.id.pointlistview);
        redTextView = theView.findViewById(R.id.redTeam);
        redTextView.setText(AppData.game.getTeams().get(0));
        blueTextView = theView.findViewById(R.id.blueTeam);
        blueTextView.setText(AppData.game.getTeams().get(1));

        pointsHistoryAdapter = new PointsHistoryAdapter(getContext(), AppData.game.getSets().get(AppData.selectedSet).getPointHistory());

        listView.setAdapter(pointsHistoryAdapter);


    }
}