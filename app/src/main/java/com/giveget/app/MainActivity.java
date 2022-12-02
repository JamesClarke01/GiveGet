package com.giveget.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.giveget.R;

public class MainActivity extends AppCompatActivity {

    int currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentUserID = 1;
    }

    public void gotoGiveScreen(View view) {
        Intent gotoGiveScreenIntent = new Intent(MainActivity.this, GiveActivity1.class);
        gotoGiveScreenIntent.putExtra("currentUserID", currentUserID);
        startActivity(gotoGiveScreenIntent);
    }

    public void gotoGetScreen(View view) {
        Intent gotoGetScreenIntent = new Intent(MainActivity.this, GetActivity1.class);
        gotoGetScreenIntent.putExtra("currentUserID", currentUserID);
        startActivity(gotoGetScreenIntent);
    }

    public void gotoUserScreen(View view) {
        Intent gotoUserScreenIntent = new Intent(MainActivity.this, UserInfoActivity.class);
        gotoUserScreenIntent.putExtra("currentUserID", currentUserID);
        startActivity(gotoUserScreenIntent);
    }
}