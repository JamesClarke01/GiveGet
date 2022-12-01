package com.giveget.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.giveget.R;

public class UserInfoActivity extends AppCompatActivity {

    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        dbManager = new DBManager(this); //instantiate the dbManager

        //display back button in action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    //function for action bar back button implementation
    @Override
    public boolean onOptionsItemSelected(MenuItem item)  //called whenever an item in the action bar is selected
    {
        if (item.getItemId() == android.R.id.home)
        {
            this.finish();  //close the activity
            return true;
        }
        else
        {
            return false;
        }
    }

    public void insertUser(View view)
    {
        dbManager.open();

<<<<<<< HEAD
=======
        dbManager.insertUser("James", "23 Yellow Road, Blanchardstown, Dublin 15");
>>>>>>> 44233a6de0998c142881a0cc8b4aca19848172c3

        //dbManager.close();
    }
}