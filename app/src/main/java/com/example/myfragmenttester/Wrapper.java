package com.example.myfragmenttester;

import java.util.ArrayList;

public class Wrapper {

    ArrayList<Game> dataList;
    //constrctor
    public Wrapper() {
        //empty constructor for Gson
    }
    //setter for dataList
    public void setDataList(ArrayList<Game> dataList){
        this.dataList = dataList;
    }
}
