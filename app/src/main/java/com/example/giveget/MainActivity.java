package com.example.giveget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gotoGiveScreen(View view) {
        Intent gotoGiveScreenIntent = new Intent(MainActivity.this, Give1.class);
        startActivity(gotoGiveScreenIntent);
    }
}