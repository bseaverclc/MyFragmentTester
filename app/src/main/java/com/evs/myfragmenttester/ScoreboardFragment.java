package com.evs.myfragmenttester;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScoreboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoreboardFragment extends Fragment implements View.OnClickListener {

    public static ScoreboardFragment scoreboardFragment = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_scoreboard, container, false);
        scoreboardFragment = this;
        ((MainActivity)getActivity()).showTabBar();
        ((MainActivity)getActivity()).menu.findItem(R.id.settingsButton).setVisible(true);
        createButtons(v);
        createEditTexts(v);
        if(AppData.game == null) {
            createGame();
        }



        vibe = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE) ;
        anim.setDuration(500);
        return v;
    }

    @Override
    public void onResume() {
        System.out.println("On Resume from Scoreboard Fragment");
        super.onResume();
        if(AppData.game == null) {
            createGame();
        }
        simpleAdvancedView();
        if(AppData.gameChanged) {
            AppData.gameChanged = false;
            set = AppData.game.getSets().get(0);
            set1.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.radio_off));
            set2.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.radio_off));
            set3.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.radio_off));
            set4.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.radio_off));
            set5.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.radio_off));
            set1.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.radio_on));
            AppData.selectedSet = 0;

            AppData.canEdit = false;
            if(!AppData.game.isPublicGame()) {
                AppData.canEdit = true;
            }
            else{
                for(Game g: AppData.myGames){
                    if(g.getUid() == AppData.game.getUid()){
                        AppData.canEdit = true;
                        break;
                    }
                }
            }
        }

        if(!AppData.canEdit){
            redTeamEditText.setEnabled(false);
            blueTeamEditText.setEnabled(false);
        }
        else{
            redTeamEditText.setEnabled(true);
            blueTeamEditText.setEnabled(true);
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


    Animation anim = new AlphaAnimation(0.5f, 1.0f);

    Vibrator vibe;
    private int layout = 0;
    private LinearLayoutCompat statsHorizontalLayout, redStatsLayout, blueStatsLayout, redScorePanel, blueScorePanel, redPositiveAndScoreLayout, redPositiveLayout, bluePositiveAndScoreLayout, bluePositiveLayout;
    private EditText redTeamEditText, blueTeamEditText;
    private RadioButton set1,set2,set3,set4,set5;

    private Button undoButton, switchSides;
    private TextView visibilityTextView;
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
    private TextView redRotationView, blueRotationView;
    private TextView redWhyView, blueWhyView;

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

    private TextView redServeReceiveText, blueServeReceiveText;
    private LinearLayoutCompat redServeReceiveRow, blueServeReceiveRow;

    private int rkill = 0;
   // private Game game;
    private ArrayList<String> teams = new ArrayList<String>();

    private ASet set;
    private int setNum = 0;




    private void createEditTexts(View view) {
        redTeamEditText = view.findViewById(R.id.redTeamName);
        blueTeamEditText = view.findViewById(R.id.blueTeamName);


//        redTeamEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                System.out.println("text changed");
//                AppData.game.getTeams().set(0, s.toString());
//
//
//            }
//        });





        redTeamEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //Clear focus here from edittext


                    redTeamEditText.clearFocus();
                   // redTeamEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    AppData.game.getTeams().set(0, redTeamEditText.getText().toString());
                    AppData.game.getTeams().set(1, blueTeamEditText.getText().toString());
                    updateScreen();
                    return false;
                }
                return false;

            }


        });

        blueTeamEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //Clear focus here from edittext


                    blueTeamEditText.clearFocus();
                    //blueTeamEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    AppData.game.getTeams().set(1, blueTeamEditText.getText().toString());
                    AppData.game.getTeams().set(0, redTeamEditText.getText().toString());
                    return false;
                }
                return false;

            }


        });

//        blueTeamEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                System.out.println("blue text changed");
//                AppData.game.getTeams().set(1,s.toString());
//
//            }
//        });


    }

    private void createButtons(View view){
        redServeReceiveRow = view.findViewById(R.id.redServeReceiveRow);
        redServeReceiveText = view.findViewById(R.id.redServeReceiveText);
        blueServeReceiveRow = view.findViewById(R.id.blueServeReceiveRow);
        blueServeReceiveText = view.findViewById(R.id.blueServeReceiveText);


        redScorePanel= view.findViewById(R.id.redScorePanel);
        blueScorePanel = view.findViewById(R.id.blueScorePanel);
        statsHorizontalLayout = view.findViewById(R.id.statsHorizontalLayout);
       redStatsLayout = view.findViewById(R.id.redStatsLayout);
       blueStatsLayout = view.findViewById(R.id.blueStatsLayout);
       redPositiveAndScoreLayout = view.findViewById(R.id.redPositiveAndScoreLayout);
       redPositiveLayout = view.findViewById(R.id.redPositiveLayout);
       bluePositiveAndScoreLayout = view.findViewById(R.id.bluePositiveAndScoreLayout);
       bluePositiveLayout = view.findViewById(R.id.bluePositiveLayout);



        undoButton = view.findViewById(R.id.undoPoint);
        undoButton.setOnClickListener(this);

        switchSides = view.findViewById(R.id.switchSides);
        switchSides.setOnClickListener(this);

        visibilityTextView = view.findViewById(R.id.visibilityTextView);




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
        redRotationView = view.findViewById(R.id.redRotationText);
        redWhyView = view.findViewById(R.id.redWhyText);

        blueHitPctView = view.findViewById(R.id.blueHitPct);
        bluePassAvgView = view.findViewById(R.id.bluePassAvg);
        blueEarnedPctView = view.findViewById(R.id.blueEarnedPct);
        blueRotationView = view.findViewById(R.id.blueRotationText);
        blueWhyView = view.findViewById(R.id.blueWhyText);


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

        redAtkButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                System.out.println("Long Click");
                if(set.getRedAttack() > 0) {
                    set.setRedAttack(set.getRedAttack() - 1);
                    updateScreen();
                }
                vibe.vibrate(100);
                return true;
            }
        });
        redDigButton = (Button)(view.findViewById(R.id.redDig));
        redDigButton.setOnClickListener(this);
        redDigButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                System.out.println("Long Click");
                if(set.getRedDigs() > 0) {
                    set.setRedDigs(set.getRedDigs() - 1);
                    updateScreen();
                }
                vibe.vibrate(100);
                return true;
            }
        });
        redOppAtkErrButton = (Button)(view.findViewById(R.id.redOppAttackErr));
        redOppAtkErrButton.setOnClickListener(this);
        redOppServeErrButton = (Button)(view.findViewById(R.id.redOppServeErr));
        redOppServeErrButton.setOnClickListener(this);
        redOppOtherErrButton = (Button)(view.findViewById(R.id.redOppOtherErr));
        redOppOtherErrButton.setOnClickListener(this);
        redOneButton = (Button)(view.findViewById(R.id.redOne));
        redOneButton.setOnClickListener(this);
        redOneButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                System.out.println("Long Click");
                if(set.getRedOne() > 0) {
                    set.setRedOne(set.getRedOne() - 1);
                    updateScreen();
                }
                vibe.vibrate(100);
                return true;
            }
        });
        redTwoButton = (Button)(view.findViewById(R.id.redTwo));
        redTwoButton.setOnClickListener(this);
        redTwoButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                System.out.println("Long Click");
                if(set.getRedTwo() > 0) {
                    set.setRedTwo(set.getRedTwo() - 1);
                    updateScreen();
                }
                vibe.vibrate(100);
                return true;
            }
        });
        redThreeButton = (Button)(view.findViewById(R.id.redThree));
        redThreeButton.setOnClickListener(this);
        redThreeButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                System.out.println("Long Click");
                if(set.getRedThree() > 0) {
                    set.setRedThree(set.getRedThree() - 1);
                    updateScreen();
                }
                vibe.vibrate(100);
                return true;
            }
        });

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
        blueAtkButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                System.out.println("Long Click");
                if(set.getBlueAttack() > 0) {
                    set.setBlueAttack(set.getBlueAttack() - 1);
                    updateScreen();
                }
                vibe.vibrate(100);
                return true;
            }
        });
        blueDigButton = (Button)(view.findViewById(R.id.blueDig));
        blueDigButton.setOnClickListener(this);
        blueDigButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                System.out.println("Long Click");
                if(set.getBlueDigs() > 0) {
                    set.setBlueDigs(set.getBlueDigs() - 1);
                    updateScreen();
                }
                vibe.vibrate(100);
                return true;
            }
        });
        blueOppAtkErrButton = (Button)(view.findViewById(R.id.blueOppAttackErr));
        blueOppAtkErrButton.setOnClickListener(this);
        blueOppServeErrButton = (Button)(view.findViewById(R.id.blueOppServeErr));
        blueOppServeErrButton.setOnClickListener(this);
        blueOppOtherErrButton = (Button)(view.findViewById(R.id.blueOppOtherErr));
        blueOppOtherErrButton.setOnClickListener(this);
        blueOneButton = (Button)(view.findViewById(R.id.blueOne));
        blueOneButton.setOnClickListener(this);
        blueOneButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                System.out.println("Long Click");
                if(set.getBlueOne() > 0) {
                    set.setBlueOne(set.getBlueOne() - 1);
                    updateScreen();
                }
                vibe.vibrate(100);
                return true;
            }
        });
        blueTwoButton = (Button)(view.findViewById(R.id.blueTwo));
        blueTwoButton.setOnClickListener(this);
        blueTwoButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                System.out.println("Long Click");
                if(set.getBlueTwo() > 0) {
                    set.setBlueTwo(set.getBlueTwo() - 1);
                    updateScreen();
                }
                vibe.vibrate(100);
                return true;
            }
        });
        blueThreeButton = (Button)(view.findViewById(R.id.blueThree));
        blueThreeButton.setOnClickListener(this);
        blueThreeButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                System.out.println("Long Click");
                if(set.getBlueThree() > 0) {
                    set.setBlueThree(set.getBlueThree() - 1);
                    updateScreen();
                }
                vibe.vibrate(100);
                return true;
            }
        });



    }

    public void createGame(){
        System.out.println("new Game being created");
        teams = new ArrayList<String>();
        teams.add("");
        teams.add("");
        AppData.game = new Game(teams,new Date(), false);
        AppData.game.getSets().add(new ASet());
        AppData.game.getSets().add(new ASet());
        AppData.game.getSets().add(new ASet());
        AppData.game.getSets().add(new ASet());
        AppData.game.getSets().add(new ASet());
        set = AppData.game.getSets().get(0);
        set1.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.radio_on));
        AppData.selectedSet = 0;
        AppData.game.setPublicGame(false);

        setNum = 1;
        AppData.canEdit = true;
        redTeamEditText.setEnabled(true);
        redTeamEditText.setEnabled(true);

        AppData.myGames.add(AppData.game);

        updateScreen();

//        self.setSegmentedControlOutlet.selectedSegmentIndex = 0
//        AppData.canEdit = true
//        AppData.selectedGame = game
//        redTextFieldOutlet.isEnabled = true
//        blueTextFieldOutlet.isEnabled = true
//        self.updateScreen()
    }

    public void updateScreen(){
        System.out.println("updateScreen");
        if(AppData.canEdit && AppData.game.isPublicGame()){
                AppData.game.updateFirebase();
        }
        if(AppData.canEdit){
            ((MainActivity)getActivity()).writeJson2("myGames2");
        }

       redTeamEditText.setText(AppData.game.getTeams().get(0));
       blueTeamEditText.setText(AppData.game.getTeams().get(1));
        if(set.getServe().equals("red") && set.getPointHistory().size()!=0) {
            redScoreButton.setText("*" + set.getRedStats().get("redScore"));
        }
        else{
            redScoreButton.setText("" + set.getRedStats().get("redScore"));
        }
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

        if(set.getServe().equals("blue") && set.getPointHistory().size() != 0) {
            blueScoreButton.setText("*" + set.getBlueStats().get("blueScore"));
        }
        else{
            blueScoreButton.setText("" + set.getBlueStats().get("blueScore"));
        }

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

        if(AppData.game.isPublicGame()) {
            visibilityTextView.setText("Public\nGame");
            visibilityTextView.setBackgroundColor(Color.GREEN);
        }
        else{
            visibilityTextView.setText("Private\nGame");
            visibilityTextView.setBackgroundColor(Color.YELLOW);
        }

        updatePercents();
       // AppData.game.updateFirebase();
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

        redRotationView.setText("Roation " + (set.getRedRotation() + 1));
        if(set.getPointHistory().size() > 0) {
            if(set.getPointHistory().get(set.getPointHistory().size() - 1).getWho().equals("red")) {
                redWhyView.setText("" + set.getPointHistory().get(set.getPointHistory().size() - 1).getWhy());
            }
            else{
                redWhyView.setText("");
            }
        }
        else{
            redWhyView.setText("");
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
        int blueNum = set.getBlueOne() + set.getBlueTwo() + set.getBlueThree() + set.getRedStats().get("Ace");
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

        blueRotationView.setText("Roation " + (set.getBlueRotation() + 1));

        if(set.getPointHistory().size() > 0) {
            if(set.getPointHistory().get(set.getPointHistory().size() - 1).getWho().equals("blue")) {
                blueWhyView.setText("" + set.getPointHistory().get(set.getPointHistory().size() - 1).getWhy());
            }
            else{
                blueWhyView.setText("");
            }
        }
        else{
            blueWhyView.setText("");
        }




    }

    public void redPointUpdate(String why){
        if(why.length() !=0) {
            set.getRedStats().put(why, set.getRedStats().get(why) + 1);
        }
        set.getRedStats().put("redScore", set.getRedStats().get("redScore") + 1);
        Point point = new Point(set.getServe(), set.getRedRotation(), set.getBlueRotation(), "red", why, set.getRedStats().get("redScore") + "-" + set.getBlueStats().get("blueScore"));
        set.addPoint(point, AppData.game.getUid());


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
        updateScreen();
    }

    public void bluePointUpdate(String why){
        if(why.length() !=0) {
            set.getBlueStats().put(why, set.getBlueStats().get(why) + 1);
        }
        set.getBlueStats().put("blueScore", set.getBlueStats().get("blueScore") + 1);
        Point point = new Point(set.getServe(), set.getRedRotation(), set.getBlueRotation(), "blue", why, set.getRedStats().get("redScore") + "-" + set.getBlueStats().get("blueScore"));
        set.addPoint(point, AppData.game.getUid());

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
        updateScreen();
    }

    public void setFirstServe(String who, String why){
        AlertDialog.Builder firstServeAlert = new AlertDialog.Builder(getContext());
        firstServeAlert.setTitle("Who served first?");
        //firstServeAlert.setMessage("Who served first?");
        String redTeam = "Red Team";
        String blueTeam = "Blue Team";
        if(AppData.game.getTeams().get(0).length() != 0){
            redTeam = AppData.game.getTeams().get(0);
        }
        if(AppData.game.getTeams().get(1).length() != 0){
            blueTeam = AppData.game.getTeams().get(1);
        }
        firstServeAlert.setItems(new CharSequence[]
                        {redTeam, blueTeam},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0:
                                set.setServe("red");
                                if(who.equals("red")) {
                                    redPointUpdate(why);
                                }
                                else{
                                    bluePointUpdate(why);
                                }
                                break;
                            case 1:
                                set.setServe("blue");
                                if(who.equals("red")) {
                                    redPointUpdate(why);
                                }
                                else{
                                    bluePointUpdate(why);
                                }
                                break;

                        }
                    }
                });
        firstServeAlert.create().show();
       //firstServeAlert.show();
    }

    public void redScoreAction(View view){
        if(AppData.canEdit) {
            if (set.getPointHistory().size() == 0) {
                setFirstServe("red", "");
            } else {
                redPointUpdate("");
            }
        }

    }
    public void redAceAction(View view){
        if(AppData.canEdit) {
            if (set.getPointHistory().size() == 0) {
                setFirstServe("red", "Ace");
            } else {
                redPointUpdate("Ace");
            }
        }
    }

    public void redBlockAction(View view){
        if(AppData.canEdit) {
            if (set.getPointHistory().size() == 0) {
                setFirstServe("red", "Block");
            } else {
                redPointUpdate("Block");
            }
            set.setBlueAttack(set.getBlueAttack() + 1);
            set.getRedStats().put("Opponent Attack Err", set.getRedStats().get("Opponent Attack Err") + 1);
        }
    }
    public void redKillAction(View view){
        if(AppData.canEdit) {
            if (set.getPointHistory().size() == 0) {
                setFirstServe("red", "Kill");
            } else {
                redPointUpdate("Kill");
            }
            set.setRedAttack(set.getRedAttack() + 1);
        }

    }

    public void redOppAttackErrAction(View view){
        if(AppData.canEdit) {
            if (set.getPointHistory().size() == 0) {
                setFirstServe("red", "Opponent Attack Err");
            } else {
                redPointUpdate("Opponent Attack Err");
            }
            set.setBlueAttack(set.getBlueAttack() + 1);
        }
    }

    public void redOppServeErrAction(View view){
        if(AppData.canEdit) {
            if (set.getPointHistory().size() == 0) {
                setFirstServe("red", "Opponent Serve Err");
            } else {
                redPointUpdate("Opponent Serve Err");
            }
        }

    }
    public void redOppOtherErrAction(View view){
        if(AppData.canEdit) {
            if (set.getPointHistory().size() == 0) {
                setFirstServe("red", "Opponent Err");
            } else {
                redPointUpdate("Opponent Err");
            }
        }
    }
    public void redAtkAction(View view){
        if(AppData.canEdit) {
            set.setRedAttack(set.getRedAttack() + 1);
        }
    }
    public void redDigAction(View view){
        if(AppData.canEdit) {
            set.setRedDigs(set.getRedDigs() + 1);
        }
    }

    public void redOneAction(View view){
        if(AppData.canEdit) {
            set.setRedOne(set.getRedOne() + 1);
        }
    }
    public void redTwoAction(View view){
        if(AppData.canEdit) {
            set.setRedTwo(set.getRedTwo() + 1);
        }
    }
    public void redThreeAction(View view){
        if(AppData.canEdit) {
            set.setRedThree(set.getRedThree() + 1);
        }
    }

    // Blue Actions
    public void blueScoreAction(View view){
        if(AppData.canEdit) {
            if (set.getPointHistory().size() == 0) {
                setFirstServe("blue", "");
            } else {
                bluePointUpdate("");
            }
        }



    }
    public void blueAceAction(View view){
        if(AppData.canEdit) {
            if (set.getPointHistory().size() == 0) {
                setFirstServe("blue", "Ace");
            } else {
                bluePointUpdate("Ace");
            }
        }

    }

    public void blueBlockAction(View view){
        if(AppData.canEdit) {
            if (set.getPointHistory().size() == 0) {
                setFirstServe("blue", "Block");
            } else {
                bluePointUpdate("Block");
            }

            set.setRedAttack(set.getRedAttack() + 1);
            set.getBlueStats().put("Opponent Attack Err", set.getBlueStats().get("Opponent Attack Err") + 1);
        }

    }
    public void blueKillAction(View view){
        if(AppData.canEdit) {
            if (set.getPointHistory().size() == 0) {
                setFirstServe("blue", "Kill");
            } else {
                bluePointUpdate("Kill");
            }

            set.setBlueAttack(set.getBlueAttack() + 1);
        }


    }

    public void blueOppAttackErrAction(View view){
        if(AppData.canEdit) {
            if (set.getPointHistory().size() == 0) {
                setFirstServe("blue", "Opponent Attack Err");
            } else {
                bluePointUpdate("Opponent Attack Err");
            }

            set.setRedAttack(set.getRedAttack() + 1);
        }

    }

    public void blueOppServeErrAction(View view){
        if(AppData.canEdit) {
            if (set.getPointHistory().size() == 0) {
                setFirstServe("blue", "Opponent Serve Err");
            } else {
                bluePointUpdate("Opponent Serve Err");
            }
        }

    }
    public void blueOppOtherErrAction(View view){
        if(AppData.canEdit) {
            if (set.getPointHistory().size() == 0) {
                setFirstServe("blue", "Opponent Err");
            } else {
                bluePointUpdate("Opponent Err");
            }
        }

    }
    public void blueAtkAction(View view){
        if(AppData.canEdit) {
            set.setBlueAttack(set.getBlueAttack() + 1);
        }
    }
    public void blueDigAction(View view){
        if(AppData.canEdit) {
            set.setBlueDigs(set.getBlueDigs() + 1);
        }
    }

    public void blueOneAction(View view){
        if(AppData.canEdit) {
            set.setBlueOne(set.getBlueOne() + 1);
        }
    }
    public void blueTwoAction(View view){
        if(AppData.canEdit) {
            set.setBlueTwo(set.getBlueTwo() + 1);
        }
    }
    public void blueThreeAction(View view){
        if(AppData.canEdit) {
            set.setBlueThree(set.getBlueThree() + 1);
        }
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
            redScoreButton.startAnimation(anim);
            redScoreAction(v);
        }
        else if(id == R.id.redAce){

             //You can manage the blinking time with this parameter
            redAceButton.startAnimation(anim);
            redAceAction(v);
        }
        else if(id == R.id.redBlock){
            redBlockButton.startAnimation(anim);
            redBlockAction(v);
        }
        else if(id == R.id.redKill){
            redKillButton.startAnimation(anim);
            redKillAction(v);
        }
        else if(id == R.id.redOppAttackErr){
            redOppAtkErrButton.startAnimation(anim);
            redOppAttackErrAction(v);
        }
        else if(id == R.id.redOppServeErr){
            redOppServeErrButton.startAnimation(anim);
            redOppServeErrAction(v);
        }
        else if(id == R.id.redOppOtherErr){
            redOppOtherErrButton.startAnimation(anim);
            redOppOtherErrAction(v);
        }
        else if(id == R.id.redAttack){
            redAtkButton.startAnimation(anim);
            redAtkAction(v);
        }
        else if(id == R.id.redDig){
            redDigButton.startAnimation(anim);
            redDigAction(v);
        }

        else if(id == R.id.redOne){
            redOneButton.startAnimation(anim);
            redOneAction(v);
        }
        else if(id == R.id.redTwo){
            redTwoButton.startAnimation(anim);
            redTwoAction(v);
        }
        else if(id == R.id.redThree){
            redThreeButton.startAnimation(anim);
            redThreeAction(v);
        }
        // blue actions

        if(id == R.id.blueScore){
            blueScoreButton.startAnimation(anim);
            blueScoreAction(v);
        }
        else if(id == R.id.blueAce){
            blueAceButton.startAnimation(anim);
            blueAceAction(v);
        }
        else if(id == R.id.blueBlock){
            blueBlockButton.startAnimation(anim);
            blueBlockAction(v);
        }
        else if(id == R.id.blueKill){
            blueKillButton.startAnimation(anim);
            blueKillAction(v);
        }
        else if(id == R.id.blueOppAttackErr){
            blueOppAtkErrButton.startAnimation(anim);
            blueOppAttackErrAction(v);
        }
        else if(id == R.id.blueOppServeErr){
            blueOppServeErrButton.startAnimation(anim);
            blueOppServeErrAction(v);
        }
        else if(id == R.id.blueOppOtherErr){
            blueOppOtherErrButton.startAnimation(anim);
            blueOppOtherErrAction(v);
        }
        else if(id == R.id.blueAttack){
            blueAtkButton.startAnimation(anim);
            blueAtkAction(v);
        }
        else if(id == R.id.blueDig){
            blueDigButton.startAnimation(anim);
            blueDigAction(v);
        }

        else if(id == R.id.blueOne){
            blueOneButton.startAnimation(anim);
            blueOneAction(v);
        }
        else if(id == R.id.blueTwo){
            blueTwoButton.startAnimation(anim);
            blueTwoAction(v);
        }
        else if(id == R.id.blueThree){
            blueThreeButton.startAnimation(anim);
            blueThreeAction(v);
        }
        if (id == R.id.undoPoint) {
            undoButton.startAnimation(anim);
            System.out.println("undo button pushed");
            undoAction();
        }

        if(id == R.id.switchSides){

            switchSides.startAnimation(anim);
            if (layout == 0) {
                statsHorizontalLayout.removeAllViews();
                bluePositiveAndScoreLayout.removeAllViews();
                bluePositiveAndScoreLayout.addView(bluePositiveLayout);
                bluePositiveAndScoreLayout.addView(blueScorePanel);
                statsHorizontalLayout.addView(blueStatsLayout);

                redPositiveAndScoreLayout.removeAllViews();
                redPositiveAndScoreLayout.addView(redScorePanel);
                redPositiveAndScoreLayout.addView(redPositiveLayout);
                statsHorizontalLayout.addView(redStatsLayout);
                layout = 1;
            }
            else{
                statsHorizontalLayout.removeAllViews();
                redPositiveAndScoreLayout.removeAllViews();
                redPositiveAndScoreLayout.addView(redPositiveLayout);
                redPositiveAndScoreLayout.addView(redScorePanel);
                statsHorizontalLayout.addView(redStatsLayout);

                bluePositiveAndScoreLayout.removeAllViews();
                bluePositiveAndScoreLayout.addView(blueScorePanel);
                bluePositiveAndScoreLayout.addView(bluePositiveLayout);
                statsHorizontalLayout.addView(blueStatsLayout);
                layout = 0;
            }



        }

//        if(id == R.id.publicGames){
//            System.out.println("public games pushed");
//            AppData.publicGames.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
//            Collections.reverse(AppData.publicGames);
//            Intent intent = new Intent(getContext(), PublicGames.class);
//            //intent.putExtra("meet", meet);
//            startActivity(intent);
//        }




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

public void gameGoneAlert(){
    AlertDialog.Builder gameGonealert = new AlertDialog.Builder(getContext());
    gameGonealert.setTitle("This game is no longer public");
    gameGonealert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            AppData.myGames.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
            Collections.reverse(AppData.myGames);
            Intent intent = new Intent(getActivity(), MyGames.class);
            //intent.putExtra("meet", meet);
            startActivity(intent);
        }
    });
    gameGonealert.show();

}

public void simpleAdvancedView(){
    System.out.println("simpleAdvancedView being called");
        if(AppData.game.getType() == 1){
            System.out.println("simple game");
            blueHitPctView.setVisibility(View.INVISIBLE);
            redHitPctView.setVisibility((View.INVISIBLE));
            redPassAvgView.setVisibility((View.INVISIBLE));
            bluePassAvgView.setVisibility((View.INVISIBLE));

            blueAtkButton.setVisibility(View.INVISIBLE);
            redAtkButton.setVisibility(View.INVISIBLE);
            redDigButton.setVisibility(View.INVISIBLE);
            blueDigButton.setVisibility(View.INVISIBLE);

            redServeReceiveText.setVisibility(View.INVISIBLE);
            blueServeReceiveText.setVisibility(View.INVISIBLE);
            redServeReceiveRow.setVisibility(View.INVISIBLE);
            blueServeReceiveRow.setVisibility(View.INVISIBLE);
        }
        else{
            blueHitPctView.setVisibility(View.VISIBLE);
            redHitPctView.setVisibility((View.VISIBLE));
            redPassAvgView.setVisibility((View.VISIBLE));
            bluePassAvgView.setVisibility((View.VISIBLE));

            blueAtkButton.setVisibility(View.VISIBLE);
            redAtkButton.setVisibility(View.VISIBLE);
            redDigButton.setVisibility(View.VISIBLE);
            blueDigButton.setVisibility(View.VISIBLE);

            redServeReceiveText.setVisibility(View.VISIBLE);
            blueServeReceiveText.setVisibility(View.VISIBLE);
            redServeReceiveRow.setVisibility(View.VISIBLE);
            blueServeReceiveRow.setVisibility(View.VISIBLE);
        }
}

}