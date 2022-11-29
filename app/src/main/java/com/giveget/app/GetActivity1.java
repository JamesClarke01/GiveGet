package com.giveget.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.giveget.R;

public class GetActivity1 extends AppCompatActivity {

    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get1);

        dbManager = new DBManager(this); //instantiate the dbManager

        //display back button in action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



        updateFoodList();


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

    public void updateFoodList()
    {
        dbManager.open();

        //Cursor foodlistingCursor = dbManager.getAllFoodlistings();

        String names[] = {"apples", "onions", "oranges"};  //array

        ListView foodListView = findViewById(R.id.foodListView);  //list view

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, names );

        foodListView.setAdapter(arrayAdapter);


        dbManager.close();


    }

}