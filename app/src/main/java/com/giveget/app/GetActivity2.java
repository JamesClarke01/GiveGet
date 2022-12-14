package com.giveget.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giveget.R;

import java.io.IOException;

public class GetActivity2 extends AppCompatActivity {

    DBManager dbManager;
    DBManager.DBHelper dbHelper;
    ImageHelper imageHelper;
    Integer listingID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get2);

        dbManager = new DBManager(this); //instantiate the dbManager
        imageHelper = new ImageHelper();

        //display back button in action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        try {
            displayListing();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public void displayListing() throws IOException {
        //retrieve listing id
        Bundle extras = getIntent().getExtras();
        listingID = extras.getInt("listingID");

        Log.i("ID", String.valueOf(listingID));

        dbManager.open();

        //GET FOODLISTING DATA
        Cursor listing = dbManager.getListingByID(listingID);

        if (listing.getCount() <= 0) {return;}// if the query failed, escape

        listing.moveToFirst(); //go to first result (the only result!)

        //retrieve the info from the foodlisting cursor
        @SuppressLint("Range") String name = listing.getString(listing.getColumnIndex(dbHelper.KEY_FOODLISTING_NAME));

        @SuppressLint("Range") String expiry = listing.getString(listing.getColumnIndex(dbHelper.KEY_FOODLISTING_EXPIRY));

        @SuppressLint("Range") int amount = listing.getInt(listing.getColumnIndex(dbHelper.KEY_FOODLISTING_AMOUNT));

        @SuppressLint("Range") String desc = listing.getString(listing.getColumnIndex(dbHelper.KEY_FOODLISTING_DESC));

        @SuppressLint("Range") String imagePath = listing.getString(listing.getColumnIndex(dbHelper.KEY_FOODLISTING_IMAGE));

        @SuppressLint("Range") int giverID = listing.getInt(listing.getColumnIndex(dbHelper.KEY_FOODLISTING_GIVER));

        //GET GIVER DATA
        Cursor giverInfo = dbManager.getUserByID(giverID);

        if(giverInfo.getCount() <= 0){return;}  //if the query failed return

        giverInfo.moveToFirst(); //go to first result (the only result!)

        @SuppressLint("Range") String giverName = giverInfo.getString(giverInfo.getColumnIndex(dbHelper.KEY_USER_NAME));

        @SuppressLint("Range") String giverAddress = giverInfo.getString(giverInfo.getColumnIndex(dbHelper.KEY_USER_ADDRESS));

        //UPDATE THE TEXT VIEWS IN THE ACTIVITY
        ((TextView)findViewById(R.id.name)).setText(name);

        ((TextView)findViewById(R.id.expiry)).setText(expiry);

        ((TextView)findViewById(R.id.amount)).setText(String.valueOf(amount));

        ((TextView)findViewById(R.id.description)).setText(desc);

        ((TextView)findViewById(R.id.giver)).setText(giverName);

        ((TextView)findViewById(R.id.address)).setText(giverAddress);

        //UPDATE THE IMAGE VIEW
        Bitmap imgBitmap = BitmapFactory.decodeFile(imagePath);
        if (imgBitmap != null)
        {
            imgBitmap = ImageHelper.rotateImageIfRequired(imgBitmap, imagePath);

            ((ImageView)findViewById(R.id.listingImage)).setImageBitmap(imgBitmap);
        }

        dbManager.close();
    }

    public void removeListing(View view)
    {
        dbManager.open();
        dbManager.deleteListingByID(listingID);
        dbManager.close();
        Toast.makeText(this, "Listing Requested!", Toast.LENGTH_SHORT).show();
        finish();
    }
    //Inspired by StackOverflow article : https://stackoverflow.com/questions/2201917/how-can-i-open-a-url-in-androids-web-browser-from-my-application
    public void searchNutrition(View view)
    {
        TextView t = (TextView)findViewById(R.id.name);
        String name = t.getText().toString();
        Intent loadBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nutritionvalue.org/search.php?food_query=" + name));
        startActivity(loadBrowser);
    }
    //end reference

}