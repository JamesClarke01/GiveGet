package com.giveget.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.giveget.R;

public class GetActivity1 extends AppCompatActivity {

    DBManager dbManager;
    ListView foodListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get1);

        dbManager = new DBManager(this); //instantiate the dbManager

        //display back button in action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



        initialiseFoodList();


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

    public void initialiseFoodList()
    {
        dbManager.open();

        Cursor foodlistingCursor = dbManager.getAllFoodlistings();

        foodListView = findViewById(R.id.foodListView);  //list view

        String[] dbTableColumns = {"name", "expiry", "amount", "image", "description"};

        int[] rowLayoutIDs = new int[] {R.id.name, R.id.expiry, R.id.amount, R.id.image, R.id.description };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.rowlayout_foodlisting, foodlistingCursor, dbTableColumns, rowLayoutIDs);

        foodListView.setAdapter(adapter);
        dbManager.close();

        foodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor selectedListingCursor = (Cursor) parent.getItemAtPosition(position);


                int userID = selectedListingCursor.getInt(0);  //gets the id of the user from the column with index 0 (_id)

                Intent gotoGetScreen2 = new Intent(GetActivity1.this, GetActivity2.class);
                gotoGetScreen2.putExtra("userID", userID);  //adds the value of userID to the intent with the key "userID"
                startActivity(gotoGetScreen2);





                //@SuppressLint("Range") String itemName = selectedListingCursor.getString(selectedListingCursor.getColumnIndex("name"));

                //Toast.makeText(getApplicationContext(), itemName, Toast.LENGTH_LONG).show();
                //String selectedItem = (String) parent.getItemAtPosition(position);
                //Toast.makeText(getApplicationContext(), "Selected item: " + selectedItem, Toast.LENGTH_LONG).show();
            }
        });




    }

}