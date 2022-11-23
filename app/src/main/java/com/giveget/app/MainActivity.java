package com.giveget.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.giveget.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gotoGiveScreen(View view) {
        Intent gotoGiveScreenIntent = new Intent(MainActivity.this, GiveActivity1.class);
        startActivity(gotoGiveScreenIntent);
    }

    public void gotoGetScreen(View view) {
        Intent gotoGetScreenIntent = new Intent(MainActivity.this, GetActivity1.class);
        startActivity(gotoGetScreenIntent);
    }
}