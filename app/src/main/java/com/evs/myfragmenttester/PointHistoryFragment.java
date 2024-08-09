package com.evs.myfragmenttester;

import android.graphics.Color;
import android.os.Bundle;

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

 private TextView redTextView, blueTextView, setTextView;
 private TextView redRotation1, redRotation2, redRotation3, redRotation4, redRotation5,redRotation6;
    private TextView blueRotation1, blueRotation2, blueRotation3, blueRotation4, blueRotation5,blueRotation6;
    private TextView redTeamRotationName, blueTeamRotationName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_point_history, container, false);
        theView = v;

        listView = theView.findViewById(R.id.pointlistview);
        redTextView = theView.findViewById(R.id.redTeam);
        blueTextView = theView.findViewById(R.id.blueTeam);
        setTextView = theView.findViewById(R.id.setTitle);


        redRotation1 = theView.findViewById(R.id.redRotation1);
        redRotation2 = theView.findViewById(R.id.redRotation2);
        redRotation3 = theView.findViewById(R.id.redRotation3);
        redRotation4 = theView.findViewById(R.id.redRotation4);
        redRotation5 = theView.findViewById(R.id.redRotation5);
        redRotation6 = theView.findViewById(R.id.redRotation6);

        blueRotation1 = theView.findViewById(R.id.blueRotation1);
        blueRotation2 = theView.findViewById(R.id.blueRotation2);
        blueRotation3 = theView.findViewById(R.id.blueRotation3);
        blueRotation4 = theView.findViewById(R.id.blueRotation4);
        blueRotation5 = theView.findViewById(R.id.blueRotation5);
        blueRotation6 = theView.findViewById(R.id.blueRotation6);

        redTeamRotationName = theView.findViewById(R.id.redTeamRotationName);
        blueTeamRotationName = theView.findViewById(R.id.blueTeamRotationName);
        return v;


    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).tabLayout.getTabAt(3).select();



        if(AppData.game.getTeams().get(0).length() == 0){
            redTeamRotationName.setText("Team 1");
            redTextView.setText("Team 1");
        }
        else{
            redTeamRotationName.setText(AppData.game.getTeams().get(0));
            redTextView.setText(AppData.game.getTeams().get(0));
        }

        if(AppData.game.getTeams().get(1).length() == 0){
            blueTeamRotationName.setText("Team 2");
            blueTextView.setText("Team 2");
        }
        else{
            blueTeamRotationName.setText(AppData.game.getTeams().get(1));
            blueTextView.setText(AppData.game.getTeams().get(1));
        }




        setTextView.setText("Set " + (AppData.selectedSet + 1));

        pointsHistoryAdapter = new PointsHistoryAdapter(getContext(), AppData.game.getSets().get(AppData.selectedSet).getPointHistory());
        listView.setAdapter(pointsHistoryAdapter);


        updateRotations();
    }


    public void updateRotations(){
        int red1 = AppData.game.getSets().get(AppData.selectedSet).getRedRotationPlusMinus().get(0);
        redRotation1.setText(""+red1);
        if (red1 < 0){redRotation1.setBackgroundColor(Color.RED);}
        else if (red1 > 0){redRotation1.setBackgroundColor(Color.GREEN);}
        else {redRotation1.setBackgroundColor(Color.WHITE);}

        int red2 = AppData.game.getSets().get(AppData.selectedSet).getRedRotationPlusMinus().get(1);
        redRotation2.setText(""+red2);
        if (red2 < 0){redRotation2.setBackgroundColor(Color.RED);}
        else if (red2 > 0){redRotation2.setBackgroundColor(Color.GREEN);}
        else {redRotation2.setBackgroundColor(Color.WHITE);}

        int red3 = AppData.game.getSets().get(AppData.selectedSet).getRedRotationPlusMinus().get(2);
        redRotation3.setText(""+red3);
        if (red3 < 0){redRotation3.setBackgroundColor(Color.RED);}
        else if (red3 > 0){redRotation3.setBackgroundColor(Color.GREEN);}
        else {redRotation3.setBackgroundColor(Color.WHITE);}

        int red4 = AppData.game.getSets().get(AppData.selectedSet).getRedRotationPlusMinus().get(3);
        redRotation4.setText(""+red4);
        if (red4 < 0){redRotation4.setBackgroundColor(Color.RED);}
        else if (red4 > 0){redRotation4.setBackgroundColor(Color.GREEN);}
        else {redRotation4.setBackgroundColor(Color.WHITE);}

        int red5 = AppData.game.getSets().get(AppData.selectedSet).getRedRotationPlusMinus().get(4);
        redRotation5.setText(""+red5);
        if (red5 < 0){redRotation5.setBackgroundColor(Color.RED);}
        else if (red5 > 0){redRotation5.setBackgroundColor(Color.GREEN);}
        else {redRotation5.setBackgroundColor(Color.WHITE);}

        int red6 = AppData.game.getSets().get(AppData.selectedSet).getRedRotationPlusMinus().get(5);
        redRotation6.setText(""+red6);
        if (red6 < 0){redRotation6.setBackgroundColor(Color.RED);}
        else if (red6 > 0){redRotation6.setBackgroundColor(Color.GREEN);}
        else {redRotation6.setBackgroundColor(Color.WHITE);}


        int blue1 = AppData.game.getSets().get(AppData.selectedSet).getBlueRotationPlusMinus().get(0);
        blueRotation1.setText(""+blue1);
        if (blue1 < 0){blueRotation1.setBackgroundColor(Color.RED);}
        else if (blue1 > 0){blueRotation1.setBackgroundColor(Color.GREEN);}
        else {blueRotation1.setBackgroundColor(Color.WHITE);}

        int blue2 = AppData.game.getSets().get(AppData.selectedSet).getBlueRotationPlusMinus().get(1);
        blueRotation2.setText(""+blue2);
        if (blue2 < 0){blueRotation2.setBackgroundColor(Color.RED);}
        else if (blue2 > 0){blueRotation2.setBackgroundColor(Color.GREEN);}
        else {blueRotation2.setBackgroundColor(Color.WHITE);}

        int blue3 = AppData.game.getSets().get(AppData.selectedSet).getBlueRotationPlusMinus().get(2);
        blueRotation3.setText(""+blue3);
        if (blue3 < 0){blueRotation3.setBackgroundColor(Color.RED);}
        else if (blue3 > 0){blueRotation3.setBackgroundColor(Color.GREEN);}
        else {blueRotation3.setBackgroundColor(Color.WHITE);}

        int blue4 = AppData.game.getSets().get(AppData.selectedSet).getBlueRotationPlusMinus().get(3);
        blueRotation4.setText(""+blue4);
        if (blue4 < 0){blueRotation4.setBackgroundColor(Color.RED);}
        else if (blue4 > 0){blueRotation4.setBackgroundColor(Color.GREEN);}
        else {blueRotation4.setBackgroundColor(Color.WHITE);}

        int blue5 = AppData.game.getSets().get(AppData.selectedSet).getBlueRotationPlusMinus().get(4);
        blueRotation5.setText(""+blue5);
        if (blue5 < 0){blueRotation5.setBackgroundColor(Color.RED);}
        else if (blue5 > 0){blueRotation5.setBackgroundColor(Color.GREEN);}
        else {blueRotation5.setBackgroundColor(Color.WHITE);}

        int blue6 = AppData.game.getSets().get(AppData.selectedSet).getBlueRotationPlusMinus().get(5);
        blueRotation6.setText(""+blue6);
        if (blue6 < 0){blueRotation6.setBackgroundColor(Color.RED);}
        else if (blue6 > 0){blueRotation6.setBackgroundColor(Color.GREEN);}
        else {blueRotation6.setBackgroundColor(Color.WHITE);}


    }
}