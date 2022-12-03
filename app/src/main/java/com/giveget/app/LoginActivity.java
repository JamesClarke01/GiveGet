package com.giveget.app;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giveget.R;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity{

    DBManager dbManager;
    DBManager.DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        dbManager = new DBManager(this);
        dbHelper = new DBManager.DBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public int verifyUser(View view)
    {
        Log.i("user", "verifying");
        TextInputEditText UnameView = (TextInputEditText) findViewById(R.id.LoginName);
        TextInputEditText AddressView = (TextInputEditText) findViewById(R.id.LoginAddress);
        dbManager.open();
        String usaName = UnameView.getText().toString();
        String addy = AddressView.getText().toString();
        Cursor user = dbManager.getUserbyName(usaName);

        Log.i("user", String.valueOf(user.getCount()));

        if(user.getCount() <= 0)
        {
            Log.i("user", "no user found, exiting");
            dbManager.close();
            return -1;
        }

        user.moveToFirst();

        @SuppressLint("Range") String name = user.getString(user.getColumnIndex(dbHelper.KEY_USER_NAME));
        @SuppressLint("Range") String address = user.getString(user.getColumnIndex(dbHelper.KEY_USER_ADDRESS));
        if(name.equals(usaName)  && addy.equals(address))
        {
            @SuppressLint("Range") int usaId = user.getInt(user.getColumnIndex(dbHelper.KEY_USER_ID));
            Log.i("user", String.valueOf(usaId));
            return usaId;
        }
        Log.i("user", "login failed");
        //If all else fails put the app back in a noUser state
        dbManager.close();
        return -1;
    }



}

