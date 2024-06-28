package com.example.myfragmenttester;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.google.android.material.button.MaterialButton;
import yuku.ambilwarna.AmbilWarnaDialog;

public class Settings extends AppCompatActivity {

    private MaterialButton redTeamColor, blueTeamColor;
    private RadioGroup publicPrivateGroup, statsModeGroup;
    private RadioButton publicButton, privateButton, simpleButton, advancedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        publicPrivateGroup = findViewById(R.id.publicPrivateGroup);
        publicButton = findViewById(R.id.publicButton);
        privateButton = findViewById(R.id.privateButton);

        if(AppData.game.getUid().equals("")) {
            privateButton.toggle();
        }
        else{
            publicButton.toggle();
        }



        publicPrivateGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.publicButton){
                    System.out.println("clicked public button");
                    System.out.println("Going to save to firebase eventually");

                }
                if(checkedId == R.id.privateButton){
                    System.out.println("clicked private button");
                }
            }
        });

        statsModeGroup = findViewById(R.id.statsModeGroup);
        simpleButton = findViewById(R.id.simpleButton);
        advancedButton = findViewById(R.id.advancedButton);

        if(AppData.game.getType() == 0){
            simpleButton.toggle();
        }
        else{
            advancedButton.toggle();
        }

        statsModeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.privateButton){
                    AppData.game.setType(0);
                }
                else{
                    AppData.game.setType(1);
                }
            }
        });


        redTeamColor = findViewById(R.id.redTeamColor);
        redTeamColor.setBackgroundColor(AppData.gameRedTeamColor);
        blueTeamColor = findViewById(R.id.blueTeamColor);
        blueTeamColor.setBackgroundColor(AppData.gameBlueTeamColor);

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
}