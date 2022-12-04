package com.giveget.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giveget.R;

public class MainActivity extends AppCompatActivity {

    int currentUserID;
    final int REQUEST_ACCOUNT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentUserID = 1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ACCOUNT && resultCode == Activity.RESULT_OK) {
            currentUserID = data.getIntExtra("currentUserID", 1);
            Log.i("user", String.valueOf(currentUserID));

            Toast.makeText(this, "User " + String.valueOf(currentUserID) + " Selected", Toast.LENGTH_SHORT).show();
        }
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
        Intent gotoUserScreenIntent = new Intent(MainActivity.this, AccountSelection.class);
        gotoUserScreenIntent.putExtra("currentUserID", currentUserID);
        startActivityForResult(gotoUserScreenIntent, REQUEST_ACCOUNT);
    }


}