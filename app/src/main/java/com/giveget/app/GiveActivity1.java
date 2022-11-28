package com.giveget.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.giveget.R;

public class GiveActivity1 extends AppCompatActivity {

    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give1);

        //instantiate dbmanager
        dbManager = new DBManager(this);
    }

    public void createFoodListing(View view)
    {
        dbManager.open(); //connect to database

        dbManager.insertFoodlisting("Apples", "30/11/2022", 3, "apples.png", "3 Fresh Apples");

        //dbManager.close();
    }


}