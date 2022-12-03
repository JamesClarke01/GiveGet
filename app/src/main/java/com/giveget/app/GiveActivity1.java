package com.giveget.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.giveget.R;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

public class GiveActivity1 extends AppCompatActivity {

    DBManager dbManager;
    int currentUserID;
    SeekBar amountBar;

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give1);

        Log.i("img", "in give screen");

        //display back button in action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //instantiate dbmanager
        dbManager = new DBManager(this);

        //set the current user id
        Bundle extras = getIntent().getExtras();
        currentUserID = extras.getInt("currentUserID");

        amountBar = (SeekBar) findViewById(R.id.amountBar);
        TextView amountText = (TextView) findViewById(R.id.seekText);
        amountBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int pogress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                pogress = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                amountText.setText(String.format("Amount of items : %d", pogress));
            }
        });


    }


    //function for action bar back button implementation
    @Override
    public boolean onOptionsItemSelected(MenuItem item)  //called whenever an item in the action bar is selected
    {
        if (item.getItemId() == android.R.id.home) {
            this.finish();  //close the activity
            return true;
        } else {
            return false;
        }
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("log", "avtivity result error");
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            findViewById(R.id.userImage);
            imageView.setImageBitmap(photo);
        }
        else
        {
            Log.i("log", "avtivity result error");
        }
    }
    */

    public void takePhoto(View view)
    {
        final int REQUEST_IMAGE_CAPTURE = 1;
        Log.i("img", "taking photo");

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        startActivityForResult(takePictureIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //retrieves photo from the takePictureActivity
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        imageView = findViewById(R.id.userImage);
        imageView.setImageBitmap(photo);
    }




    public void createFoodListing(View view)
    {

            /*
                Check camera stuff first
             */
/*                if(hasCameraPermission())
            {
                enableCamera();
            }
            else
            {
                requestPermission();
            }
*/
        TextInputEditText typeView = (TextInputEditText) findViewById(R.id.inputType);
        TextInputEditText dateView = (TextInputEditText) findViewById(R.id.inputExpDate);
        //TextInputEditText imgView  = (TextInputEditText) findViewById(R.id.inputImage);
        TextInputEditText descView = (TextInputEditText) findViewById(R.id.inputDescription);

        String[] data = new String[]{
                typeView.getText().toString(),
                dateView.getText().toString(),
                "Camera here",
                //                    imgView.getText().toString(),
                descView.getText().toString(),
        };
        int x = amountBar.getProgress();
        TextView myTextView = (TextView) findViewById(R.id.giveTextView);
        myTextView.setText(data[0] + "\n" + data[1]+ "\n" +  x  + "\n" + data[2] + "\n" + data[3]);
        dbManager.open();
        dbManager.insertFoodlisting(data[0],data[1],x,data[2],data[3], currentUserID);
        dbManager.close();
    }


/*
    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, 10);
    }
    public void enableCamera()
    {
        Intent camIntent = new Intent(this, CameraActivity1.class);
        startActivity(camIntent);
    }*/
}