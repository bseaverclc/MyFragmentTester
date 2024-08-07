package com.evs.myfragmenttester;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.google.android.material.button.MaterialButton;
import yuku.ambilwarna.AmbilWarnaDialog;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    private MaterialButton redTeamColor, blueTeamColor;
    private RadioGroup publicPrivateGroup, statsModeGroup;
    private RadioButton publicButton, privateButton, simpleButton, advancedButton;

    private RadioButton set1,set2,set3,set4,set5;

    private RadioButton blueRot1, blueRot2, blueRot3,blueRot4,blueRot5, blueRot6;

    private RadioButton redRot1, redRot2, redRot3,redRot4,redRot5, redRot6;

    private TextView redTeamColorText, blueTeamColorText, redStartingRotationText, blueStartingRotationText;
    int chosenSet = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        publicPrivateGroup = findViewById(R.id.publicPrivateGroup);
        publicButton = findViewById(R.id.publicButton);
        privateButton = findViewById(R.id.privateButton);

        if(AppData.game.isPublicGame()) {
            publicButton.toggle();
        }
        else{
            privateButton.toggle();
        }

        if(!AppData.canEdit){
            publicButton.setEnabled(false);
            privateButton.setEnabled(false);
        }
        else{
            publicButton.setEnabled(true);
            privateButton.setEnabled(true);
        }



        publicPrivateGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.publicButton){
                    System.out.println("Public button pressed");
                    AppData.game.setPublicGame(true);
                    AppData.game.saveGameToFirebase();
                    //AppData.publicGames.add(AppData.game);

                }
                if(checkedId == R.id.privateButton){
                    System.out.println("Private button pressed");
                    AppData.game.setPublicGame(false);
                    for(Game g: AppData.publicGames){
                        if(g.getUid().equals(AppData.game.getUid())){
                            AppData.publicGames.remove(g);
                            break;
                        }
                    }
                    AppData.game.deleteFromFirebase();
                   // AppData.game.setUid("");

                }
            }
        });

        statsModeGroup = findViewById(R.id.statsModeGroup);
        simpleButton = findViewById(R.id.simpleButton);
        advancedButton = findViewById(R.id.advancedButton);

        if(AppData.game.getType() == 1){
            simpleButton.toggle();
        }
        else{
            advancedButton.toggle();
        }

        if(!AppData.canEdit){
            simpleButton.setEnabled(false);
            advancedButton.setEnabled(false);
        }
        else{
            simpleButton.setEnabled(true);
            advancedButton.setEnabled(true);
        }

        statsModeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.simpleButton){
                    AppData.game.setType(1);
                    System.out.println("changed to simple");
                }
                else{
                    AppData.game.setType(0);
                }
            }
        });

        redTeamColorText = findViewById(R.id.redTeamColorText);
        if(AppData.game.getTeams().get(0).length() == 0){
            redTeamColorText.setText("Team 1");
        }
        else{
            redTeamColorText.setText(AppData.game.getTeams().get(0));
        }

        blueTeamColorText = findViewById(R.id.blueTeamColorText);
        if(AppData.game.getTeams().get(1).length() == 0){
            blueTeamColorText.setText("Team 2");
        }
        else{
            blueTeamColorText.setText(AppData.game.getTeams().get(1));
        }

        redTeamColor = findViewById(R.id.redTeamColor);
        redTeamColor.setBackgroundColor(AppData.gameRedTeamColor);
        blueTeamColor = findViewById(R.id.blueTeamColor);
        blueTeamColor.setBackgroundColor(AppData.gameBlueTeamColor);

        redStartingRotationText = findViewById(R.id.redStartingRotationText);
        if(AppData.game.getTeams().get(0).length() == 0){
            redStartingRotationText.setText("Team 1\nStarting Rotation");
        }
        else{
            redStartingRotationText.setText(AppData.game.getTeams().get(0) + "\nStarting Rotation");
        }

        blueStartingRotationText = findViewById(R.id.blueStartingRotationText);
        if(AppData.game.getTeams().get(1).length() == 0){
            blueStartingRotationText.setText("Team 2\nStarting Rotation");
        }
        else{
            blueStartingRotationText.setText(AppData.game.getTeams().get(1) + "\nStarting Rotation");
        }

        getSupportActionBar().setTitle("Settings");



        redTeamColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPickerRed();

            }
        });

        blueTeamColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPickerBlue();

            }
        });

        chosenSet = AppData.selectedSet;

        set1 =  findViewById(R.id.set1);
        set1.setOnClickListener(this);
        set2 = findViewById(R.id.set2);
        set2.setOnClickListener(this);
        set3 = findViewById(R.id.set3);
        set3.setOnClickListener(this);
        set4 = findViewById(R.id.set4);
        set4.setOnClickListener(this);
        set5 = findViewById(R.id.set5);
        set5.setOnClickListener(this);

        redRot1 =  findViewById(R.id.redRot1);
        redRot1.setOnClickListener(this);
        redRot2 = findViewById(R.id.redRot2);
        redRot2.setOnClickListener(this);
        redRot3 = findViewById(R.id.redRot3);
        redRot3.setOnClickListener(this);
        redRot4 = findViewById(R.id.redRot4);
        redRot4.setOnClickListener(this);
        redRot5 = findViewById(R.id.redRot5);
        redRot5.setOnClickListener(this);
        redRot6 = findViewById(R.id.redRot6);
        redRot6.setOnClickListener(this);

        blueRot1 =  findViewById(R.id.blueRot1);
        blueRot1.setOnClickListener(this);
        blueRot2 = findViewById(R.id.blueRot2);
        blueRot2.setOnClickListener(this);
        blueRot3 = findViewById(R.id.blueRot3);
        blueRot3.setOnClickListener(this);
        blueRot4 = findViewById(R.id.blueRot4);
        blueRot4.setOnClickListener(this);
        blueRot5 = findViewById(R.id.blueRot5);
        blueRot5.setOnClickListener(this);
        blueRot6 = findViewById(R.id.blueRot6);
        blueRot6.setOnClickListener(this);

        choseSet();
        highlightRedRotation();
        highlightBlueRotation();



    }

    public void openColorPickerRed(){
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, AppData.gameRedTeamColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                redTeamColor.setBackgroundColor(color);
                AppData.gameRedTeamColor = color;
            }
        });
        ambilWarnaDialog.show();
    }

    public void openColorPickerBlue(){
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, AppData.gameBlueTeamColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                blueTeamColor.setBackgroundColor(color);
                AppData.gameBlueTeamColor = color;
            }
        });
        ambilWarnaDialog.show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();


        if(id == R.id.set1 || id == R.id.set2 || id == R.id.set3 || id == R.id.set4 || id == R.id.set5) {
            System.out.println("set pressed");

            if (id == R.id.set1){
                chosenSet = 0;
            }
            if (id == R.id.set2){
                chosenSet = 1;
            }
            if (id == R.id.set3){
                chosenSet = 2;
            }
            if (id == R.id.set4){
                chosenSet = 3;
            }
            if (id == R.id.set5){
                chosenSet = 4;
            }
            choseSet();
            highlightRedRotation();
            highlightBlueRotation();
        }

        // clicked on a red rotation
        if(AppData.canEdit) {
            if (id == R.id.redRot1 || id == R.id.redRot2 || id == R.id.redRot3 || id == R.id.redRot4 || id == R.id.redRot5 || id == R.id.redRot6) {
                redRot1.setBackground(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.radio_off));
                redRot2.setBackground(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.radio_off));
                redRot3.setBackground(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.radio_off));
                redRot4.setBackground(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.radio_off));
                redRot5.setBackground(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.radio_off));
                redRot6.setBackground(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.radio_off));
                if (id == R.id.redRot1) {
                    redRot1.setBackground(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.radio_on));
                    redRotationChange(0);
                }
                if (id == R.id.redRot2) {
                    redRot2.setBackground(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.radio_on));
                    redRotationChange(1);
                }
                if (id == R.id.redRot3) {
                    redRot3.setBackground(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.radio_on));
                    redRotationChange(2);
                }
                if (id == R.id.redRot4) {
                    redRot4.setBackground(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.radio_on));
                    redRotationChange(3);
                }
                if (id == R.id.redRot5) {
                    redRot5.setBackground(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.radio_on));
                    redRotationChange(4);
                }
                if (id == R.id.redRot6) {
                    redRot6.setBackground(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.radio_on));
                    redRotationChange(5);
                }

            }

            // clicked on a blue rotation
            if (id == R.id.blueRot1 || id == R.id.blueRot2 || id == R.id.blueRot3 || id == R.id.blueRot4 || id == R.id.blueRot5 || id == R.id.blueRot6) {
                blueRot1.setBackground(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.radio_off));
                blueRot2.setBackground(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.radio_off));
                blueRot3.setBackground(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.radio_off));
                blueRot4.setBackground(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.radio_off));
                blueRot5.setBackground(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.radio_off));
                blueRot6.setBackground(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.radio_off));
                if (id == R.id.blueRot1) {
                    blueRot1.setBackground(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.radio_on));
                    blueRotationChange(0);
                }
                if (id == R.id.blueRot2) {
                    blueRot2.setBackground(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.radio_on));
                    blueRotationChange(1);
                }
                if (id == R.id.blueRot3) {
                    blueRot3.setBackground(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.radio_on));
                    blueRotationChange(2);
                }
                if (id == R.id.blueRot4) {
                    blueRot4.setBackground(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.radio_on));
                    blueRotationChange(3);
                }
                if (id == R.id.blueRot5) {
                    blueRot5.setBackground(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.radio_on));
                    blueRotationChange(4);
                }
                if (id == R.id.blueRot6) {
                    blueRot6.setBackground(ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.radio_on));
                    blueRotationChange(5);
                }

            }
        }
    }

    private void redRotationChange(int start){
        ASet theSet = AppData.game.getSets().get(chosenSet);
        if (theSet.getPointHistory().size() == 0){
            theSet.setRedRotation(start);
        }
        else {
            for (int i = 0; i < theSet.getRedRotationPlusMinus().size(); i++) {
                theSet.getRedRotationPlusMinus().set(i, 0);
            }
            int diff = start - theSet.getPointHistory().get(0).getRedRotation();
            for (Point p : theSet.getPointHistory()) {
                int newRed = p.getRedRotation() + diff;
                if (newRed < 0) {
                    newRed = 6 + newRed;
                }
                p.setRedRotation(newRed % 6);
                int currentRed = theSet.getRedRotationPlusMinus().get(p.getRedRotation());
                if (p.getWho().equals("red")) {
                    theSet.getRedRotationPlusMinus().set(p.getRedRotation(), currentRed + 1);
                } else {
                    theSet.getRedRotationPlusMinus().set(p.getRedRotation(), currentRed - 1);
                }
            }
                int newSetRedRotation = theSet.getRedRotation() + diff;
                if (newSetRedRotation < 0) {
                    newSetRedRotation = newSetRedRotation + 6;
                }
                theSet.setRedRotation(newSetRedRotation % 6);
            }
        }

    private void blueRotationChange(int start){
        ASet theSet = AppData.game.getSets().get(chosenSet);
        if (theSet.getPointHistory().size() == 0){
            theSet.setBlueRotation(start);
        }
        else {
            for (int i = 0; i < theSet.getBlueRotationPlusMinus().size(); i++) {
                theSet.getBlueRotationPlusMinus().set(i, 0);
            }
            int diff = start - theSet.getPointHistory().get(0).getBlueRotation();
            for (Point p : theSet.getPointHistory()) {
                int newBlue = p.getBlueRotation() + diff;
                if (newBlue < 0) {
                    newBlue = 6 + newBlue;
                }
                p.setBlueRotation(newBlue % 6);
                int currentBlue = theSet.getBlueRotationPlusMinus().get(p.getBlueRotation());
                if (p.getWho().equals("blue")) {
                    theSet.getBlueRotationPlusMinus().set(p.getBlueRotation(), currentBlue + 1);
                } else {
                    theSet.getBlueRotationPlusMinus().set(p.getBlueRotation(), currentBlue - 1);
                }
            }
            int newSetBlueRotation = theSet.getBlueRotation() + diff;
            if (newSetBlueRotation < 0) {
                newSetBlueRotation = newSetBlueRotation + 6;
            }
            theSet.setBlueRotation(newSetBlueRotation % 6);
        }
    }

        public void highlightRedRotation(){
        ASet theSet = AppData.game.getSets().get(chosenSet);
        int redStart = 0;
        if(theSet.getPointHistory().size() == 0){
            redStart = theSet.getRedRotation();
        }
        else{
            redStart = theSet.getPointHistory().get(0).getRedRotation();
        }

            redRot1.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_off));
            redRot2.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_off));
            redRot3.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_off));
            redRot4.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_off));
            redRot5.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_off));
            redRot6.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_off));
            switch (redStart){
                case 0: redRot1.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_on));
                    break;
                case 1: redRot2.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_on));
                    break;
                case 2: redRot3.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_on));
                    break;
                case 3: redRot4.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_on));
                    break;
                case 4: redRot5.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_on));
                    break;
                case 5: redRot6.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_on));
            }
        }
    public void highlightBlueRotation(){
        ASet theSet = AppData.game.getSets().get(chosenSet);
        int blueStart = 0;
        if(theSet.getPointHistory().size() == 0){
            blueStart = theSet.getBlueRotation();
        }
        else{
            blueStart = theSet.getPointHistory().get(0).getBlueRotation();
        }

        blueRot1.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_off));
        blueRot2.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_off));
        blueRot3.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_off));
        blueRot4.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_off));
        blueRot5.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_off));
        blueRot6.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_off));
        switch (blueStart){
            case 0: blueRot1.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_on));
                break;
            case 1: blueRot2.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_on));
                break;
            case 2: blueRot3.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_on));
                break;
            case 3: blueRot4.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_on));
                break;
            case 4: blueRot5.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_on));
                break;
            case 5: blueRot6.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_on));
        }
    }

        private void choseSet(){
            set1.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_off));
            set2.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_off));
            set3.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_off));
            set4.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_off));
            set5.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_off));
            switch (chosenSet){
                case 0: set1.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_on));
                    break;
                case 1: set2.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_on));
                    break;
                case 2: set3.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_on));
                    break;
                case 3: set4.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_on));
                    break;
                case 4: set5.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.radio_on));
            }
        }
    }
//
