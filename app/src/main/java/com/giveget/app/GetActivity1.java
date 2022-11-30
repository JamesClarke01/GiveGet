package com.giveget.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

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

        Cursor foodlistingCursor = dbManager.getAllFoodlistings();

        ListView foodListView = findViewById(R.id.foodListView);  //list view

        String[] dbTableColumns = {"name", "expiry", "amount", "image", "description"};

        int[] rowLayoutIDs = new int[] {R.id.name, R.id.expiry, R.id.amount, R.id.image, R.id.description };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.rowlayout_foodlisting, foodlistingCursor, dbTableColumns, rowLayoutIDs);


        foodListView.setAdapter(adapter);


        dbManager.close();


    }

}