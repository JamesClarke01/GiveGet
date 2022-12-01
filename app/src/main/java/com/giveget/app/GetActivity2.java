package com.giveget.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.giveget.R;

public class GetActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get2);

        //display back button in action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        Bundle extras = getIntent().getExtras();

        Integer listingID = extras.getInt("listingID");

        TextView text = findViewById(R.id.user);

        text.setText(listingID.toString());


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



}