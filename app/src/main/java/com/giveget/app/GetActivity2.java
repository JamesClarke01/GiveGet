package com.giveget.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.giveget.R;

public class GetActivity2 extends AppCompatActivity {

    DBManager dbManager;
    DBManager.DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get2);

        dbManager = new DBManager(this); //instantiate the dbManager

        //display back button in action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Log.i("A", "CREATING");

        displayListing();


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

    public void displayListing()
    {
        //retrieve listing id
        Bundle extras = getIntent().getExtras();
        Integer listingID = extras.getInt("listingID");

        Log.i("ID", String.valueOf(listingID));

        dbManager.open();

        Cursor listing = dbManager.getListingByID(listingID);



        if (listing != null)
        {
            //get indexes of columns
            int nameIndex = listing.getColumnIndex(dbHelper.KEY_FOODLISTING_NAME);
            int expiryIndex = listing.getColumnIndex(dbHelper.KEY_FOODLISTING_EXPIRY);
            int amountIndex = listing.getColumnIndex(dbHelper.KEY_FOODLISTING_AMOUNT);
            int descIndex = listing.getColumnIndex(dbHelper.KEY_FOODLISTING_DESC);
            int imageIndex = listing.getColumnIndex(dbHelper.KEY_FOODLISTING_IMAGE);

            listing.moveToFirst(); //go to first result (the only result!)

            //retrieve the data from the columns
            String name = listing.getString(nameIndex);
            String expiry = listing.getString(expiryIndex);
            int amount = listing.getInt(amountIndex);
            String desc = listing.getString(descIndex);
            String image = listing.getString(imageIndex);

            //update the views on the activity
            TextView nameText = findViewById(R.id.name);
            nameText.setText(name);

            TextView imageText = findViewById(R.id.image);
            imageText.setText(image);

            TextView expiryText = findViewById(R.id.expiry);
            expiryText.setText(expiry);

            TextView amountText = findViewById(R.id.amount);
            amountText.setText(String.valueOf(amount));

            TextView descText  = findViewById(R.id.description);
            descText.setText(desc);
        }
        else
        {
            Log.e("Query Failed", null);
        }




        dbManager.close();


    }



}