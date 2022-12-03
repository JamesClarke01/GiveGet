package com.giveget.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.giveget.R;

public class MainActivity extends AppCompatActivity {

    int currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentUserID = -1;
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

    public void gotoLoginScreen(View view)
    {
        //checks if a user is logged in or not
        if(currentUserID != -1) {
            Context c = getApplicationContext();
            Toast toast = Toast.makeText(c, "Error: you're already logged in.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        Intent gotoLoginScreenIntent = new Intent(MainActivity.this, LoginActivity.class);
        gotoLoginScreenIntent.putExtra("currentUserID",currentUserID);
        startActivity(gotoLoginScreenIntent);
    }
}