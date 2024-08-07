package com.evs.myfragmenttester;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class PointsHistoryAdapter extends ArrayAdapter<Point> {

    private ArrayList<Point> thePoints;


    public PointsHistoryAdapter(@NonNull Context context, ArrayList<Point> points) {
        super(context, R.layout.custom_point_view, points);
        thePoints = points;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater= LayoutInflater.from(getContext());
        View rowView=inflater.inflate(R.layout.custom_point_view, null,true);


        TextView redWhyText = (TextView) rowView.findViewById(R.id.redWhy);
        TextView blueWhyText = (TextView) rowView.findViewById(R.id.blueWhy);
        TextView scoreText = (TextView) rowView.findViewById(R.id.score);
        TextView redRotateText = (TextView) rowView.findViewById(R.id.redRotationNum);
        TextView blueRotateText = (TextView) rowView.findViewById(R.id.blueRotationNum);

        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        if(thePoints.get(position).getWho().equals("red")) {
            redWhyText.setText(thePoints.get(position).getWhy());
            redWhyText.setBackgroundColor(Color.GREEN);
            blueWhyText.setText("");
            if (thePoints.get(position).getWhy().contains("Err")){
                blueWhyText.setBackgroundColor(Color.RED);
            }
        }
        else{
            blueWhyText.setText(thePoints.get(position).getWhy());
            blueWhyText.setBackgroundColor(Color.GREEN);
            redWhyText.setText("");
            if (thePoints.get(position).getWhy().contains("Err")){
                redWhyText.setBackgroundColor(Color.RED);
            }
        }
        scoreText.setText(thePoints.get(position).getScore());

        if(thePoints.get(position).getServe().equals("red")){
            redRotateText.setText("*"+(thePoints.get(position).getRedRotation()+1));
            redRotateText.setBackgroundColor(Color.YELLOW);
        }
        else{
            redRotateText.setText(""+(thePoints.get(position).getRedRotation()+1));
        }

        if(thePoints.get(position).getServe().equals("blue")){
            blueRotateText.setText("*"+(thePoints.get(position).getBlueRotation()+1));
            blueRotateText.setBackgroundColor(Color.YELLOW);
        }
        else{
            blueRotateText.setText(""+(thePoints.get(position).getBlueRotation()+1));
        }
        //view.setMinimumHeight((int)(view.getParent().getHeight()*.10));

        //imageView.setImageResource(imgid[position]);


        return rowView;

    }
}
