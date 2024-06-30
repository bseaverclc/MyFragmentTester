package com.example.myfragmenttester;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScoreboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoreboardFragment extends Fragment implements View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_scoreboard, container, false);
        createButtons(v);
        createEditTexts(v);
        createGame();
        updateScreen();
        setNum = 1;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        System.out.println("database reference : " + mDatabase);
        readGamesFromFirebase();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(AppData.gameChanged) {
            set = AppData.game.getSets().get(0);
            set1.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.radio_off));
            set2.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.radio_off));
            set3.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.radio_off));
            set4.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.radio_off));
            set5.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.radio_off));
            set1.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.radio_on));
            AppData.selectedSet = 0;
            AppData.gameChanged = false;
        }
        updateScreen();
        updateBackgroundColors();
    }

    public void updateBackgroundColors(){
        redAceButton.setBackgroundColor(AppData.gameRedTeamColor);
        redKillButton.setBackgroundColor(AppData.gameRedTeamColor);
        redBlockButton.setBackgroundColor((AppData.gameRedTeamColor));
        redOppAtkErrButton.setBackgroundColor(AppData.gameRedTeamColor);
        redOppOtherErrButton.setBackgroundColor(AppData.gameRedTeamColor);
        redOppServeErrButton.setBackgroundColor(AppData.gameRedTeamColor);
        redScoreButton.setBackgroundColor(AppData.gameRedTeamColor);
        redScorePanel.setBackgroundColor(AppData.gameRedTeamColor);

         blueAceButton.setBackgroundColor(AppData.gameBlueTeamColor);
         blueKillButton.setBackgroundColor(AppData.gameBlueTeamColor);
         blueBlockButton.setBackgroundColor((AppData.gameBlueTeamColor));
         blueOppAtkErrButton.setBackgroundColor(AppData.gameBlueTeamColor);
         blueOppOtherErrButton.setBackgroundColor(AppData.gameBlueTeamColor);
         blueOppServeErrButton.setBackgroundColor(AppData.gameBlueTeamColor);
         blueScoreButton.setBackgroundColor(AppData.gameBlueTeamColor);
         blueScorePanel.setBackgroundColor(AppData.gameBlueTeamColor);
    }

    //
    private DatabaseReference mDatabase;
    private int layout = 0;
    private LinearLayoutCompat statsHorizontalLayout, redStatsLayout, blueStatsLayout, redScorePanel, blueScorePanel;
    private EditText redTeamEditText, blueTeamEditText;
    private RadioButton set1,set2,set3,set4,set5;

    private Button undoButton, switchSides;
    private Button publicGames;

    private Button redScoreButton;
    private Button redAceButton;
    private Button redBlockButton;
    private Button redKillButton;
    private Button redAtkButton;
    private Button redDigButton;
    private Button redOppAtkErrButton;
    private Button redOppServeErrButton;
    private Button redOppOtherErrButton;
    private Button redOneButton;
    private Button redTwoButton;
    private Button redThreeButton;
    private TextView redHitPctView;
    private TextView redPassAvgView;
    private TextView redEarnedPctView;

    private Button blueScoreButton;
    private Button blueAceButton;
    private Button blueBlockButton;
    private Button blueKillButton;
    private Button blueAtkButton;
    private Button blueDigButton;
    private Button blueOppAtkErrButton;
    private Button blueOppServeErrButton;
    private Button blueOppOtherErrButton;
    private Button blueOneButton;
    private Button blueTwoButton;
    private Button blueThreeButton;
    private TextView blueHitPctView;
    private TextView bluePassAvgView;
    private TextView blueEarnedPctView;

    private int rkill = 0;
   // private Game game;
    private ArrayList<String> teams = new ArrayList<String>();

    private ASet set;
    private int setNum = 0;

    private void readGamesFromFirebase(){
        System.out.println("In Read Games from firebase");
        mDatabase.child("games").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key = snapshot.getKey();
                System.out.println(key);
                Game aGame = new Game(key, (Map<String, Object>) snapshot.getValue());
                AppData.publicGames.add(aGame);
                mDatabase.child("games").child(key).child("sets").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot2, @Nullable String previousChildName) {
                        ASet aSet = new ASet(snapshot2.getKey(), (Map<String, Object>)snapshot2.getValue());
                        aGame.addSet(aSet);

                        mDatabase.child("games").child(key).child("sets").child(snapshot2.getKey()).child("pointHistory").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot3, @Nullable String previousChildName) {
                                System.out.println("heard Point added on firebase");
                                if(aSet.getPointHistory() != null) {
                                    for (Point p : aSet.getPointHistory()) {
                                        if (p.getUid() == snapshot3.getKey()) {
                                            return;
                                        }
                                    }
                                }
                                aSet.addPoint(snapshot3.getKey(), (Map <String,Object>) snapshot3.getValue());
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void createEditTexts(View view){
        redTeamEditText = view.findViewById(R.id.redTeamName);
        blueTeamEditText = view.findViewById(R.id.blueTeamName);
        redTeamEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("text changed");
                AppData.game.getTeams().set(0,s.toString());

            }
        });

        blueTeamEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("blue text changed");
                AppData.game.getTeams().set(1,s.toString());

            }
        });
    }

    private void createButtons(View view){
        redScorePanel= view.findViewById(R.id.redScorePanel);
        blueScorePanel = view.findViewById(R.id.blueScorePanel);
        statsHorizontalLayout = view.findViewById(R.id.statsHorizontalLayout);
       redStatsLayout = view.findViewById(R.id.redStatsLayout);
       blueStatsLayout = view.findViewById(R.id.blueStatsLayout);

        undoButton = view.findViewById(R.id.undoPoint);
        undoButton.setOnClickListener(this);

        switchSides = view.findViewById(R.id.switchSides);
        switchSides.setOnClickListener(this);






        set1 =  view.findViewById(R.id.set1);
        set1.setOnClickListener(this);
        set2 = view.findViewById(R.id.set2);
        set2.setOnClickListener(this);
        set3 = view.findViewById(R.id.set3);
        set3.setOnClickListener(this);
        set4 = view.findViewById(R.id.set4);
        set4.setOnClickListener(this);
        set5 = view.findViewById(R.id.set5);
        set5.setOnClickListener(this);


        redHitPctView = view.findViewById(R.id.redHitPct);
        redPassAvgView = view.findViewById(R.id.redPassAvg);
        redEarnedPctView = view.findViewById(R.id.redEarnedPct);
        blueHitPctView = view.findViewById(R.id.blueHitPct);
        bluePassAvgView = view.findViewById(R.id.bluePassAvg);
        blueEarnedPctView = view.findViewById(R.id.blueEarnedPct);


        redScoreButton = (Button)(view.findViewById(R.id.redScore));
        redScoreButton.setOnClickListener(this);
        redAceButton = (Button)(view.findViewById(R.id.redAce));
        redAceButton.setOnClickListener(this);
        redBlockButton = (Button)(view.findViewById(R.id.redBlock));
        redBlockButton.setOnClickListener(this);
        redKillButton = (Button)(view.findViewById(R.id.redKill));
        redKillButton.setOnClickListener(this);
        redAtkButton = (Button)(view.findViewById(R.id.redAttack));
        redAtkButton.setOnClickListener(this);
        redDigButton = (Button)(view.findViewById(R.id.redDig));
        redDigButton.setOnClickListener(this);
        redOppAtkErrButton = (Button)(view.findViewById(R.id.redOppAttackErr));
        redOppAtkErrButton.setOnClickListener(this);
        redOppServeErrButton = (Button)(view.findViewById(R.id.redOppServeErr));
        redOppServeErrButton.setOnClickListener(this);
        redOppOtherErrButton = (Button)(view.findViewById(R.id.redOppOtherErr));
        redOppOtherErrButton.setOnClickListener(this);
        redOneButton = (Button)(view.findViewById(R.id.redOne));
        redOneButton.setOnClickListener(this);
        redTwoButton = (Button)(view.findViewById(R.id.redTwo));
        redTwoButton.setOnClickListener(this);
        redThreeButton = (Button)(view.findViewById(R.id.redThree));
        redThreeButton.setOnClickListener(this);

        blueScoreButton = (Button)(view.findViewById(R.id.blueScore));
        blueScoreButton.setOnClickListener(this);
        blueAceButton = (Button)(view.findViewById(R.id.blueAce));
        blueAceButton.setOnClickListener(this);
        blueBlockButton = (Button)(view.findViewById(R.id.blueBlock));
        blueBlockButton.setOnClickListener(this);
        blueKillButton = (Button)(view.findViewById(R.id.blueKill));
        blueKillButton.setOnClickListener(this);
        blueAtkButton = (Button)(view.findViewById(R.id.blueAttack));
        blueAtkButton.setOnClickListener(this);
        blueDigButton = (Button)(view.findViewById(R.id.blueDig));
        blueDigButton.setOnClickListener(this);
        blueOppAtkErrButton = (Button)(view.findViewById(R.id.blueOppAttackErr));
        blueOppAtkErrButton.setOnClickListener(this);
        blueOppServeErrButton = (Button)(view.findViewById(R.id.blueOppServeErr));
        blueOppServeErrButton.setOnClickListener(this);
        blueOppOtherErrButton = (Button)(view.findViewById(R.id.blueOppOtherErr));
        blueOppOtherErrButton.setOnClickListener(this);
        blueOneButton = (Button)(view.findViewById(R.id.blueOne));
        blueOneButton.setOnClickListener(this);
        blueTwoButton = (Button)(view.findViewById(R.id.blueTwo));
        blueTwoButton.setOnClickListener(this);
        blueThreeButton = (Button)(view.findViewById(R.id.blueThree));
        blueThreeButton.setOnClickListener(this);

    }

    public void createGame(){
        System.out.println("new Game being created");
        teams.add("Red Team");
        teams.add("Blue Team");
        AppData.game = new Game(teams,new Date(), false);
        AppData.game.getSets().add(new ASet());
        AppData.game.getSets().add(new ASet());
        AppData.game.getSets().add(new ASet());
        AppData.game.getSets().add(new ASet());
        AppData.game.getSets().add(new ASet());
        set = AppData.game.getSets().get(0);
        set1.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.radio_on));
        AppData.selectedSet = 0;

//        self.setSegmentedControlOutlet.selectedSegmentIndex = 0
//        AppData.canEdit = true
//        AppData.selectedGame = game
//        redTextFieldOutlet.isEnabled = true
//        blueTextFieldOutlet.isEnabled = true
//        self.updateScreen()
    }

    public void updateScreen(){
       redTeamEditText.setText(AppData.game.getTeams().get(0));
       blueTeamEditText.setText(AppData.game.getTeams().get(1));

        redScoreButton.setText("" + set.getRedStats().get("redScore"));
        redAceButton.setText("Ace\n"+set.getRedStats().get("Ace"));
        redBlockButton.setText("Block\n"+set.getRedStats().get("Block"));
        redKillButton.setText("Kill\n"+set.getRedStats().get("Kill"));
        redAtkButton.setText("Atk\n" + set.getRedAttack());
        redDigButton.setText("Dig\n" + set.getRedDigs());
        redOppAtkErrButton.setText("Opp\nAttack\nErr\n"+set.getRedStats().get("Opponent Attack Err"));
        redOppServeErrButton.setText("Opp\nServe\nErr\n"+set.getRedStats().get("Opponent Serve Err"));
        redOppOtherErrButton.setText("Opp\nOther\nErr\n"+set.getRedStats().get("Opponent Err"));
        redOneButton.setText("1SR\n" + set.getRedOne());
        redTwoButton.setText("2SR\n" + set.getRedTwo());
        redThreeButton.setText("3SR\n" + set.getRedThree());

        blueScoreButton.setText("" + set.getBlueStats().get("blueScore"));
        blueAceButton.setText("Ace\n"+set.getBlueStats().get("Ace"));
        blueBlockButton.setText("Block\n"+set.getBlueStats().get("Block"));
        blueKillButton.setText("Kill\n"+set.getBlueStats().get("Kill"));
        blueAtkButton.setText("Atk\n" + set.getBlueAttack());
        blueDigButton.setText("Dig\n" + set.getBlueDigs());
        blueOppAtkErrButton.setText("Opp\nAttack\nErr\n"+set.getBlueStats().get("Opponent Attack Err"));
        blueOppServeErrButton.setText("Opp\nServe\nErr\n"+set.getBlueStats().get("Opponent Serve Err"));
        blueOppOtherErrButton.setText("Opp\nOther\nErr\n"+set.getBlueStats().get("Opponent Err"));
        blueOneButton.setText("1SR\n" + set.getBlueOne());
        blueTwoButton.setText("2SR\n" + set.getBlueTwo());
        blueThreeButton.setText("3SR\n" + set.getBlueThree());

        updatePercents();
    }

    public void updatePercents(){
// red hit % calculation
        if (set.getRedAttack() != 0) {

            double redPct = (set.getRedStats().get("Kill") - set.getBlueStats().get("Opponent Attack Err")) / (double)set.getRedAttack();

            redHitPctView.setText("Hit % " + String.format("%.3f", redPct));
        }
        else{
            redHitPctView.setText("Hit % 0.000");
        }
// red pass average calculation
        int redTotal = set.getRedOne() + 2*set.getRedTwo() + 3*set.getRedThree();
        int redNum = set.getRedOne() + set.getRedTwo() + set.getRedThree() + set.getBlueStats().get("Ace");
        if (redNum !=0){
            double redPassAvg = redTotal/(double)redNum;
            redPassAvgView.setText("Pass Avg " + String.format("%.2f", redPassAvg));
        }
        else{
            redPassAvgView.setText("Pass Avg: N/A");
        }
// red earned % calculation
        int redGood = set.getRedStats().get("Ace")  + set.getRedStats().get("Block")  + set.getRedStats().get("Kill");
        if (set.getRedStats().get("redScore") != 0){
            int redEarned = (int)(Math.round(redGood/(double)set.getRedStats().get("redScore")*100));
            redEarnedPctView.setText("Earned " + redEarned + "%");
        }

// blue calculations
// blue hit % calculation
        if (set.getBlueAttack() != 0) {
            double bluePct = (set.getBlueStats().get("Kill") - set.getRedStats().get("Opponent Attack Err")) / (double) set.getBlueAttack();

            blueHitPctView.setText("Hit % " + String.format("%.3f", bluePct));
        }
        else{
            blueHitPctView.setText("Hit % 0.000");
        }
        //blue pass average calculation
        int blueTotal = set.getBlueOne() + 2*set.getBlueTwo() + 3*set.getBlueThree();
        int blueNum = set.getBlueOne() + set.getBlueTwo() + set.getBlueThree() + set.getBlueStats().get("Ace");
        if (blueNum !=0){
            double bluePassAvg = blueTotal/(double)blueNum;
            bluePassAvgView.setText("Pass Avg " + String.format("%.2f", bluePassAvg));
        }
        else{
            bluePassAvgView.setText("Pass Avg: N/A");
        }
// blue earned % calculation
        int blueGood = set.getBlueStats().get("Ace")  + set.getBlueStats().get("Block")  + set.getBlueStats().get("Kill");
        if (set.getBlueStats().get("blueScore") != 0){
            int blueEarned = (int)(Math.round(blueGood/(double)set.getBlueStats().get("blueScore")*100));
            blueEarnedPctView.setText("Earned " + blueEarned + "%");
        }



    }

    public void redPointUpdate(){

        int currentRedPlusRotation = set.getRedRotationPlusMinus().get(set.getRedRotation());
        set.getRedRotationPlusMinus().set(set.getRedRotation(), currentRedPlusRotation + 1);
        int currentBluePlusRotation = set.getBlueRotationPlusMinus().get(set.getBlueRotation());
        set.getBlueRotationPlusMinus().set(set.getBlueRotation(), currentBluePlusRotation - 1);

        if (!set.getServe().equals("red"))
        {
            set.setServe("red");
            set.setRedRotation(set.getRedRotation()+1);
            if (set.getRedRotation() == 6){
            set.setRedRotation(0);
            }
        }
    }

    public void bluePointUpdate(){

        int currentBlueRotation = set.getBlueRotationPlusMinus().get(set.getBlueRotation());
        set.getBlueRotationPlusMinus().set(set.getBlueRotation(), currentBlueRotation + 1);
        int currentRedRotation = set.getRedRotationPlusMinus().get(set.getRedRotation());
        set.getRedRotationPlusMinus().set(set.getRedRotation(), currentRedRotation - 1);

        if (!set.getServe().equals("blue"))
        {
            set.setServe("blue");
            set.setBlueRotation(set.getBlueRotation()+1);
            if (set.getBlueRotation() == 6){
                set.setBlueRotation(0);
            }
        }
    }

    public void redScoreAction(View view){
        set.getRedStats().put("redScore", set.getRedStats().get("redScore") + 1);
        Point point = new Point(set.getServe(), set.getRedRotation(), set.getBlueRotation(), "red", "", set.getRedStats().get("redScore") + "-" + set.getBlueStats().get("blueScore"));
        set.addPoint(point, AppData.game.getUid());
        redPointUpdate();
    }
    public void redAceAction(View view){
        set.getRedStats().put("Ace", set.getRedStats().get("Ace") + 1);
        set.getRedStats().put("redScore", set.getRedStats().get("redScore") + 1);
        Point point = new Point(set.getServe(), set.getRedRotation(), set.getBlueRotation(), "red", "Ace", set.getRedStats().get("redScore") + "-" + set.getBlueStats().get("blueScore"));
        set.addPoint(point, AppData.game.getUid());
        redPointUpdate();
    }

    public void redBlockAction(View view){
        // System.out.println("redkill");
        set.getRedStats().put("Block", set.getRedStats().get("Block") + 1);
        set.setBlueAttack(set.getBlueAttack() + 1);
        set.getRedStats().put("Opponent Attack Err", set.getRedStats().get("Opponent Attack Err") + 1);
        set.getRedStats().put("redScore", set.getRedStats().get("redScore") + 1);
        Point point = new Point(set.getServe(), set.getRedRotation(), set.getBlueRotation(), "red", "Block", set.getRedStats().get("redScore") + "-" + set.getBlueStats().get("blueScore"));
        set.addPoint(point, AppData.game.getUid());
        redPointUpdate();
    }
    public void redKillAction(View view){
        // System.out.println("redkill");
        set.getRedStats().put("Kill", set.getRedStats().get("Kill") + 1);
        set.setRedAttack(set.getRedAttack() + 1);
        set.getRedStats().put("redScore", set.getRedStats().get("redScore") + 1);
        Point point = new Point(set.getServe(), set.getRedRotation(), set.getBlueRotation(), "red", "Kill", set.getRedStats().get("redScore") + "-" + set.getBlueStats().get("blueScore"));
        set.addPoint(point, AppData.game.getUid());
        redPointUpdate();

    }

    public void redOppAttackErrAction(View view){
        // System.out.println("redkill");
        set.getRedStats().put("Opponent Attack Err", set.getRedStats().get("Opponent Attack Err") + 1);
        set.setBlueAttack(set.getBlueAttack() + 1);
        set.getRedStats().put("redScore", set.getRedStats().get("redScore") + 1);
        Point point = new Point(set.getServe(), set.getRedRotation(), set.getBlueRotation(), "red", "Opponent Attack Err", set.getRedStats().get("redScore") + "-" + set.getBlueStats().get("blueScore"));
        set.addPoint(point, AppData.game.getUid());
        redPointUpdate();
    }

    public void redOppServeErrAction(View view){
        // System.out.println("redkill");
        set.getRedStats().put("Opponent Serve Err", set.getRedStats().get("Opponent Serve Err") + 1);
        set.getRedStats().put("redScore", set.getRedStats().get("redScore") + 1);
        Point point = new Point(set.getServe(), set.getRedRotation(), set.getBlueRotation(), "red", "Opponent Serve Err", set.getRedStats().get("redScore") + "-" + set.getBlueStats().get("blueScore"));
        set.addPoint(point, AppData.game.getUid());
        redPointUpdate();
    }
    public void redOppOtherErrAction(View view){
        // System.out.println("redkill");
        set.getRedStats().put("Opponent Err", set.getRedStats().get("Opponent Err") + 1);
        set.getRedStats().put("redScore", set.getRedStats().get("redScore") + 1);
        Point point = new Point(set.getServe(), set.getRedRotation(), set.getBlueRotation(), "red", "Opponent Err", set.getRedStats().get("redScore") + "-" + set.getBlueStats().get("blueScore"));
        set.addPoint(point, AppData.game.getUid());
        redPointUpdate();
    }
    public void redAtkAction(View view){
        set.setRedAttack(set.getRedAttack() + 1);
    }
    public void redDigAction(View view){
        set.setRedDigs(set.getRedDigs() + 1);
    }

    public void redOneAction(View view){
        set.setRedOne(set.getRedOne() + 1);
    }
    public void redTwoAction(View view){
        set.setRedTwo(set.getRedTwo() + 1);
    }
    public void redThreeAction(View view){
        set.setRedThree(set.getRedThree() + 1);
    }

    // Blue Actions
    public void blueScoreAction(View view){
        set.getBlueStats().put("blueScore", set.getBlueStats().get("blueScore") + 1);
        Point point = new Point(set.getServe(), set.getRedRotation(), set.getBlueRotation(), "blue", "", set.getRedStats().get("redScore") + "-" + set.getBlueStats().get("blueScore"));
        set.addPoint(point, AppData.game.getUid());
        bluePointUpdate();
    }
    public void blueAceAction(View view){
        set.getBlueStats().put("Ace", set.getBlueStats().get("Ace") + 1);
        set.getBlueStats().put("blueScore", set.getBlueStats().get("blueScore") + 1);
        Point point = new Point(set.getServe(), set.getRedRotation(), set.getBlueRotation(), "blue", "Ace", set.getRedStats().get("redScore") + "-" + set.getBlueStats().get("blueScore"));
        set.addPoint(point, AppData.game.getUid());
        bluePointUpdate();
    }

    public void blueBlockAction(View view){
        // System.out.println("bluekill");
        set.getBlueStats().put("Block", set.getBlueStats().get("Block") + 1);
        set.setRedAttack(set.getRedAttack() + 1);
        set.getBlueStats().put("Opponent Attack Err", set.getBlueStats().get("Opponent Attack Err") + 1);
        set.getBlueStats().put("blueScore", set.getBlueStats().get("blueScore") + 1);
        Point point = new Point(set.getServe(), set.getRedRotation(), set.getBlueRotation(), "blue", "Block", set.getRedStats().get("redScore") + "-" + set.getBlueStats().get("blueScore"));
        set.addPoint(point, AppData.game.getUid());
        bluePointUpdate();
    }
    public void blueKillAction(View view){
        // System.out.println("bluekill");
        set.getBlueStats().put("Kill", set.getBlueStats().get("Kill") + 1);
        set.setBlueAttack(set.getBlueAttack() + 1);
        set.getBlueStats().put("blueScore", set.getBlueStats().get("blueScore") + 1);
        Point point = new Point(set.getServe(), set.getRedRotation(), set.getBlueRotation(), "blue", "Kill", set.getRedStats().get("redScore") + "-" + set.getBlueStats().get("blueScore"));
        set.addPoint(point, AppData.game.getUid());
        bluePointUpdate();

    }

    public void blueOppAttackErrAction(View view){
        // System.out.println("bluekill");
        set.getBlueStats().put("Opponent Attack Err", set.getBlueStats().get("Opponent Attack Err") + 1);
        set.setRedAttack(set.getRedAttack() + 1);
        set.getBlueStats().put("blueScore", set.getBlueStats().get("blueScore") + 1);
        Point point = new Point(set.getServe(), set.getRedRotation(), set.getBlueRotation(), "blue", "Opponent Attack Err", set.getRedStats().get("redScore") + "-" + set.getBlueStats().get("blueScore"));
        set.addPoint(point, AppData.game.getUid());
        bluePointUpdate();
    }

    public void blueOppServeErrAction(View view){
        // System.out.println("bluekill");
        set.getBlueStats().put("Opponent Serve Err", set.getBlueStats().get("Opponent Serve Err") + 1);
        set.getBlueStats().put("blueScore", set.getBlueStats().get("blueScore") + 1);
        Point point = new Point(set.getServe(), set.getRedRotation(), set.getBlueRotation(), "blue", "Opponent Serve Err", set.getRedStats().get("redScore") + "-" + set.getBlueStats().get("blueScore"));
        set.addPoint(point, AppData.game.getUid());
        bluePointUpdate();
    }
    public void blueOppOtherErrAction(View view){
        // System.out.println("bluekill");
        set.getBlueStats().put("Opponent Err", set.getBlueStats().get("Opponent Err") + 1);
        set.getBlueStats().put("blueScore", set.getBlueStats().get("blueScore") + 1);
        Point point = new Point(set.getServe(), set.getRedRotation(), set.getBlueRotation(), "blue", "Opponent Err", set.getRedStats().get("redScore") + "-" + set.getBlueStats().get("blueScore"));
        set.addPoint(point, AppData.game.getUid());
        bluePointUpdate();
    }
    public void blueAtkAction(View view){
        set.setBlueAttack(set.getBlueAttack() + 1);
    }
    public void blueDigAction(View view){
        set.setBlueDigs(set.getBlueDigs() + 1);
    }

    public void blueOneAction(View view){
        set.setBlueOne(set.getBlueOne() + 1);
    }
    public void blueTwoAction(View view){
        set.setBlueTwo(set.getBlueTwo() + 1);
    }
    public void blueThreeAction(View view){
        set.setBlueThree(set.getBlueThree() + 1);
    }




    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.set1 || id == R.id.set2 || id == R.id.set3 || id == R.id.set4 || id == R.id.set5) {
            System.out.println("set pressed");
            set1.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.radio_off));
            set2.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.radio_off));
            set3.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.radio_off));
            set4.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.radio_off));
            set5.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.radio_off));
            if (id == R.id.set1){
                set1.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.radio_on));
                set = AppData.game.getSets().get(0);
                AppData.selectedSet = 0;
            }
            if (id == R.id.set2){
                set2.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.radio_on));
                set = AppData.game.getSets().get(1);
                AppData.selectedSet = 1;
            }
            if (id == R.id.set3){
                set3.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.radio_on));
                set = AppData.game.getSets().get(2);
                AppData.selectedSet = 2;
            }
            if (id == R.id.set4){
                set4.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.radio_on));
                set = AppData.game.getSets().get(3);
                AppData.selectedSet = 3;

            }
            if (id == R.id.set5){
                set5.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.radio_on));
                set = AppData.game.getSets().get(4);
                AppData.selectedSet = 4;

            }
        }



        if(id == R.id.redScore){
            redScoreAction(v);
        }
        else if(id == R.id.redAce){
            redAceAction(v);
        }
        else if(id == R.id.redBlock){
            redBlockAction(v);
        }
        else if(id == R.id.redKill){
            redKillAction(v);
        }
        else if(id == R.id.redOppAttackErr){
            redOppAttackErrAction(v);
        }
        else if(id == R.id.redOppServeErr){
            redOppServeErrAction(v);
        }
        else if(id == R.id.redOppOtherErr){
            redOppOtherErrAction(v);
        }
        else if(id == R.id.redAttack){
            redAtkAction(v);
        }
        else if(id == R.id.redDig){
            redDigAction(v);
        }

        else if(id == R.id.redOne){
            redOneAction(v);
        }
        else if(id == R.id.redTwo){
            redTwoAction(v);
        }
        else if(id == R.id.redThree){
            redThreeAction(v);
        }
        // blue actions

        if(id == R.id.blueScore){
            blueScoreAction(v);
        }
        else if(id == R.id.blueAce){
            blueAceAction(v);
        }
        else if(id == R.id.blueBlock){
            blueBlockAction(v);
        }
        else if(id == R.id.blueKill){
            blueKillAction(v);
        }
        else if(id == R.id.blueOppAttackErr){
            blueOppAttackErrAction(v);
        }
        else if(id == R.id.blueOppServeErr){
            blueOppServeErrAction(v);
        }
        else if(id == R.id.blueOppOtherErr){
            blueOppOtherErrAction(v);
        }
        else if(id == R.id.blueAttack){
            blueAtkAction(v);
        }
        else if(id == R.id.blueDig){
            blueDigAction(v);
        }

        else if(id == R.id.blueOne){
            blueOneAction(v);
        }
        else if(id == R.id.blueTwo){
            blueTwoAction(v);
        }
        else if(id == R.id.blueThree){
            blueThreeAction(v);
        }
        if (id == R.id.undoPoint) {
            System.out.println("undo button pushed");
            undoAction();
        }

        if(id == R.id.switchSides){

            if (layout == 0) {
                statsHorizontalLayout.removeAllViews();
                statsHorizontalLayout.addView(blueStatsLayout);
                statsHorizontalLayout.addView(redStatsLayout);
                layout = 1;
            }
            else{
                statsHorizontalLayout.removeAllViews();
                statsHorizontalLayout.addView(redStatsLayout);
                statsHorizontalLayout.addView(blueStatsLayout);
                layout = 0;
            }

        }

        if(id == R.id.publicGames){
            System.out.println("public games pushed");
            AppData.publicGames.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
            Collections.reverse(AppData.publicGames);
            Intent intent = new Intent(getContext(), PublicGames.class);
            //intent.putExtra("meet", meet);
            startActivity(intent);
        }




        updateScreen();
    }


    public void undoAction() {
        System.out.println("calling undo action");
        if (AppData.canEdit) {
            if (set.getPointHistory().size() != 0) {
                Point point = set.getPointHistory().get(set.getPointHistory().size() - 1);
                set.setServe(point.getServe());
                set.setBlueRotation(point.getBlueRotation());
                set.setRedRotation(point.getRedRotation());
                System.out.println(point.getWho());
                if (point.getWho().equals("red")) {
                    System.out.println("why: " + point.getWhy());

                    for (Map.Entry<String, Integer> entry : set.getRedStats().entrySet()) {
                        String key = entry.getKey();
                        System.out.println("key: " + key);
                        Integer value = entry.getValue();
                        if (key.equals(point.getWhy())) {
                            entry.setValue(entry.getValue() - 1);

                            if (key.equals("Opponent Attack Err")) {
                                set.setBlueAttack(set.getBlueAttack() - 1);
                            }
                            if (key.equals("Kill")) {
                                set.setRedAttack(set.getRedAttack() - 1);
                            }
                            if (key.equals("Block")) {
                                set.setBlueAttack(set.getBlueAttack() - 1);
                                int rae = set.getRedStats().get("Opponent Attack Err");
                                set.getRedStats().put("Opponent Attack Err", (rae - 1));
                            }
                        }
                    }

                    if (set.getRedStats().get("redScore") > 0) {
                        set.getRedStats().put("redScore", set.getRedStats().get("redScore") - 1);
                    }
                    set.getRedRotationPlusMinus().set(set.getRedRotation(), set.getRedRotationPlusMinus().get(set.getRedRotation()) - 1);
                    set.getBlueRotationPlusMinus().set(set.getBlueRotation(), set.getBlueRotationPlusMinus().get(set.getBlueRotation()) + 1);

                }

                if(point.getWho().equals("blue")){
                    System.out.println("why: " + point.getWhy());

                    for (Map.Entry<String, Integer> entry : set.getBlueStats().entrySet()) {
                        String key = entry.getKey();
                        System.out.println("key: " + key);
                        Integer value = entry.getValue();
                        if (key.equals(point.getWhy())) {
                            entry.setValue(entry.getValue() - 1);

                            if (key.equals("Opponent Attack Err")) {
                                set.setRedAttack(set.getRedAttack() - 1);
                            }
                            if (key.equals("Kill")) {
                                set.setBlueAttack(set.getBlueAttack() - 1);
                            }
                            if (key.equals("Block")) {
                                set.setRedAttack(set.getRedAttack() - 1);
                                int bae = set.getBlueStats().get("Opponent Attack Err");
                                set.getBlueStats().put("Opponent Attack Err", (bae - 1));
                            }
                        }
                    }

                    if (set.getBlueStats().get("blueScore") > 0) {
                        set.getBlueStats().put("blueScore", set.getBlueStats().get("blueScore") - 1);
                    }
                    set.getBlueRotationPlusMinus().set(set.getBlueRotation(), set.getBlueRotationPlusMinus().get(set.getBlueRotation()) - 1);
                    set.getRedRotationPlusMinus().set(set.getRedRotation(), set.getRedRotationPlusMinus().get(set.getRedRotation()) + 1);

                }
                // remove point
                set.getPointHistory().remove(set.getPointHistory().size() - 1);
//                    for (key,value) in set.redStats{
//                        if key == point.why{
//                            set.redStats[key]! -= 1
//                            for button in redStatsOutlet{
//                                var title = button.title(for: .normal)!
//                                if title.contains(key){
//                                    highlightRedButton(button: button)
//                                }
//                            }
//                            if key == "Opponent Attack Err"{
//                                set.blueAttack = set.blueAttack - 1
//                            }
//                            if key == "Kill"{
//                                set.redAttack = set.redAttack - 1
//                            }
//                            if key == "Block"{
//                                set.blueAttack -= 1
//                                if let oae = set.redStats["Opponent Attack Err"]{
//                                    set.redStats["Opponent Attack Err"]! -= 1
//                                }
//                            }
//                        }
//                    }
//
//                    //decrease red score
//                    if set.redStats["redScore"]! > 0{
//                        set.redStats["redScore"]! -= 1
//                        redOutlet.setTitle("\(set.redStats["redScore"]!)", for: .normal)
//                    }
//
//                    set.redRotationPlusMinus[set.redRotation] -= 1
//                    set.blueRotationPlusMinus[set.blueRotation] += 1
//
//                }
//                if point.who == "blue"{
//                    print("blue who")
//                    for (key,value) in set.blueStats{
//                        if key == point.why{
//                            set.blueStats[key]! -= 1
//                            for button in blueStatsOutlet{
//                                var title = button.title(for: .normal)!
//                                if title.contains(key){
//                                    highlightBlueButton(button: button)
//                                }
//                            }
//                            if key == "Opponent Attack Err"{
//                                set.redAttack = set.redAttack - 1
//                            }
//                            if key == "Kill"{
//                                set.blueAttack = set.blueAttack - 1
//                            }
//                            if key == "Block"{
//                                set.redAttack -= 1
//                                if let oae = set.blueStats["Opponent Attack Err"]{
//                                    set.blueStats["Opponent Attack Err"]! -= 1
//                                }
//                            }
//                        }
//                    }
//
//                    if set.blueStats["blueScore"]! > 0{
//                        set.blueStats["blueScore"]! -= 1
//                        blueOutlet.setTitle("\(set.blueStats["blueScore"]!)", for: .normal)
//                    }
//                    set.redRotationPlusMinus[set.redRotation] += 1
//                    set.blueRotationPlusMinus[set.blueRotation] -= 1
//
//                }
//
//                updatePercents()
//                updateScreen()
//
//                if let guid = game.uid{
//                    //set.pointHistory.removeLast()
//
//                    set.deletePointFromFirebase(gameUid: guid, euid: point.uid)
//                    set.setUpdateSetInfoFirebase(gameUid: guid)
//                    //game.updateFirebase()
//
//
//                }
//            else{
//                    set.pointHistory.removeLast()
//                }
//
//                updateScreen()
//
//
//
//
//

            }
        }
    }


}