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
    private TextView redAces, blueAces;
    private TextView redErrors, blueErrors;
    private TextView redServiceErrors, blueServiceErrors;
    private TextView redAttackErrors, blueAttackErrors, redAttacks, blueAttacks;
    private TextView redHitPct, blueHitPct, redPassAvg, bluePassAvg;
    private TextView redSideoutPct, blueSideoutPct;
    private TextView redDigs, blueDigs;
    private TextView redPctEarned, bluePctEarned;

    public GameStatsAdapter(@NonNull Context context, ArrayList<ASet> sets) {
        super(context, R.layout.custom_gamestats_view, sets);
        theSets = sets;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView = inflater.inflate(R.layout.custom_gamestats_view, null, true);

        if(theSets.get(position).getRedStats().get("redScore") ==0) {
            rowView.setVisibility(View.GONE);
        }


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

            redAces = rowView.findViewById(R.id.gameStatsRedAces);
            redAces.setText("" + theSets.get(position).getRedStats().get("Ace"));
            blueAces = rowView.findViewById(R.id.gameStatsBlueAces);
            blueAces.setText("" + theSets.get(position).getBlueStats().get("Ace"));

            redErrors = rowView.findViewById(R.id.gameStatsRedErrors);
            int redErrs = theSets.get(position).getBlueStats().get("Opponent Attack Err") - theSets.get(position).getBlueStats().get("Block");
            redErrs += theSets.get(position).getBlueStats().get("Opponent Serve Err") + theSets.get(position).getBlueStats().get("Opponent Err");
            redErrors.setText("" + redErrs);

            blueErrors = rowView.findViewById(R.id.gameStatsBlueErrors);
            int blueErrs = theSets.get(position).getRedStats().get("Opponent Attack Err") - theSets.get(position).getRedStats().get("Block");
            blueErrs += theSets.get(position).getRedStats().get("Opponent Serve Err") + theSets.get(position).getRedStats().get("Opponent Err");
            blueErrors.setText("" + blueErrs);

            redServiceErrors = rowView.findViewById(R.id.gameStatsRedServiceErrors);
            redServiceErrors.setText("" + theSets.get(position).getBlueStats().get("Opponent Serve Err"));
            blueServiceErrors = rowView.findViewById(R.id.gameStatsBlueServiceErrors);
            blueServiceErrors.setText("" + theSets.get(position).getRedStats().get("Opponent Serve Err"));

            redAttackErrors = rowView.findViewById(R.id.gameStatsRedAttackErrors);
            redAttackErrors.setText("" + theSets.get(position).getBlueStats().get("Opponent Attack Err"));
            blueAttackErrors = rowView.findViewById(R.id.gameStatsBlueAttackErrors);
            blueAttackErrors.setText("" + theSets.get(position).getRedStats().get("Opponent Attack Err"));

            redAttacks = rowView.findViewById(R.id.gameStatsRedAttacks);
            redAttacks.setText("" + theSets.get(position).getRedAttack());
            blueAttacks = rowView.findViewById(R.id.gameStatsBlueAttacks);
            blueAttacks.setText("" + theSets.get(position).getBlueAttack());

            int rAtk = theSets.get(position).getRedAttack();
            int bAtk = theSets.get(position).getBlueAttack();
            int rAtkErrors = theSets.get(position).getBlueStats().get("Opponent Attack Err");
            int bAtkErrors = theSets.get(position).getRedStats().get("Opponent Attack Err");
            int rKills = theSets.get(position).getRedStats().get("Kill");
            int bKills = theSets.get(position).getBlueStats().get("Kill");

            redHitPct = rowView.findViewById(R.id.gameStatsRedHit);

            if (rAtk != 0) {
                double redPercent = (double) (rKills - rAtkErrors) / rAtk;
                String redPercentString = String.format("%.3f", redPercent);
                redHitPct.setText(redPercentString);
            } else {
                redHitPct.setText("0.000");
            }

            blueHitPct = rowView.findViewById(R.id.gameStatsBlueHit);

            if (bAtk != 0) {
                double bluePercent = (double) (bKills - bAtkErrors) / bAtk;
                String bluePercentString = String.format("%.3f", bluePercent);
                blueHitPct.setText(bluePercentString);
            } else {
                blueHitPct.setText("0.000");
            }


            redPassAvg = rowView.findViewById(R.id.gameStatsRedPass);

            int redTotal = theSets.get(position).getRedOne() + 2 * theSets.get(position).getRedTwo() + 3 * theSets.get(position).getRedThree();
            int redNum = theSets.get(position).getRedOne() + theSets.get(position).getRedTwo() + theSets.get(position).getRedThree() + theSets.get(position).getBlueStats().get("Ace");
            if (redNum != 0) {
                double redPassAvgNum = redTotal / (double) redNum;
                redPassAvg.setText(String.format("%.2f", redPassAvgNum));
            } else {
                redPassAvg.setText("N/A");
            }

            bluePassAvg = rowView.findViewById(R.id.gameStatsBluePass);

            int blueTotal = theSets.get(position).getBlueOne() + 2 * theSets.get(position).getBlueTwo() + 3 * theSets.get(position).getBlueThree();
            int blueNum = theSets.get(position).getBlueOne() + theSets.get(position).getBlueTwo() + theSets.get(position).getBlueThree() + theSets.get(position).getRedStats().get("Ace");
            if (blueNum != 0) {
                double bluePassAvgNum = blueTotal / (double) blueNum;
                bluePassAvg.setText(String.format("%.2f", bluePassAvgNum));
            } else {
                bluePassAvg.setText("N/A");
            }

// sideout calculation
            double redTotal2 = 0.0;
            double blueTotal2 = 0.0;
            double redSO = 0.0;
            double blueSO = 0.0;


            for (Point point : theSets.get(position).getPointHistory()) {
                if (point.getServe().equals("red")) {
                    blueTotal2 += 1;
                    if (point.getWho().equals("blue")) {
                        blueSO += 1;
                    }
                } else {
                    redTotal2 += 1;
                    if (point.getWho().equals("red")) {
                        redSO += 1;
                    }
                }

            }

            double bluePct = 0.0;
            if (blueTotal2 != 0) {
                bluePct = blueSO / blueTotal2;
            }
            double redPct = 0.0;
            if (redTotal2 != 0) {
                redPct = redSO / redTotal2;
            }
            bluePct = (int) (Math.round(bluePct * 100.0));
            redPct = (int) (Math.round(redPct * 100.0));

            redSideoutPct = rowView.findViewById(R.id.gameStatsRedSideout);
            blueSideoutPct = rowView.findViewById(R.id.gameStatsBlueSideout);
            redSideoutPct.setText(redPct + "%");
            blueSideoutPct.setText((bluePct + "%"));


            redDigs = rowView.findViewById(R.id.gameStatsRedDigs);
            blueDigs = rowView.findViewById(R.id.gameStatsBlueDigs);
            redDigs.setText("" + theSets.get(position).getRedDigs());
            blueDigs.setText("" + theSets.get(position).getBlueDigs());


            //percent earned code
            redPctEarned = rowView.findViewById(R.id.gameStatsRedPctEarned);
            bluePctEarned = rowView.findViewById(R.id.gameStatsBluePctEarned);
            if (theSets.get(position).getRedStats().get("redScore") != 0) {
                int rGood = theSets.get(position).getRedStats().get("Ace") + rKills + theSets.get(position).getRedStats().get("Block");
                int rGoodPct = (int) Math.round((double) rGood / theSets.get(position).getRedStats().get("redScore") * 100);
                redPctEarned.setText(rGoodPct + "%");
            } else {
                redPctEarned.setText("N/A");
            }

            if (theSets.get(position).getBlueStats().get("blueScore") != 0) {
                int bGood = theSets.get(position).getBlueStats().get("Ace") + bKills + theSets.get(position).getBlueStats().get("Block");
                int bGoodPct = (int) Math.round((double) bGood / theSets.get(position).getBlueStats().get("blueScore") * 100);
                bluePctEarned.setText(bGoodPct + "%");
            } else {
                bluePctEarned.setText("N/A");
            }




        return rowView;
    }






}
