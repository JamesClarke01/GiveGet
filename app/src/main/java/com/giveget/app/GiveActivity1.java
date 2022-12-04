package com.giveget.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
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
import android.widget.Toast;

import com.example.giveget.R;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GiveActivity1 extends AppCompatActivity {

    DBManager dbManager;
    int currentUserID;
    SeekBar amountBar;

    //globals for camera functionality
    final int REQUEST_IMAGE_CAPTURE = 1;
    String currentPhotoPath;
    //File photoFile;


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


    public void displayCurrentImage() throws IOException {
        Bitmap imgBitmap = BitmapFactory.decodeFile(currentPhotoPath);

        if (imgBitmap != null)
        {
            imgBitmap = ImageHelper.rotateImageIfRequired(imgBitmap, currentPhotoPath);

            ImageView imageview = findViewById(R.id.userImage);

            imageview.setImageBitmap(imgBitmap);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //Displays photo

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                displayCurrentImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //Creates a unique file name
    //implemented using official android documentation at https://developer.android.com/training/camera-deprecated/photobasics
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }

    //implemented using official android documentation at https://developer.android.com/training/camera-deprecated/photobasics
    public void takePhoto(View view)
    {

        Log.i("img", "taking photo");

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {

            // Create the File where the photo should go
            File photoFile = null;
            try
            {
                photoFile = createImageFile();

            } catch (IOException ex)
            {
                Log.e("img", "Error occurred while creating the File");
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {

                currentPhotoPath = photoFile.getAbsolutePath();  //set the global photo path

                Uri photoURI = FileProvider.getUriForFile(this, "com.giveget.android.fileprovider", photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

            }
        }
    }

    private boolean valiDate(String date)
    {
        //Get today's date in the desired format
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatta = new SimpleDateFormat("dd/MM/yy");
        String todayFormatted = formatta.format(today);

        //Use regex to validate user inputted date
        Pattern dateEx = Pattern.compile("^[0-3]?[0-9]/[0-1]?[0-9]/(?:[0-9]{2})?[0-9]{2}$");
        Matcher dateMatch = dateEx.matcher(date);

        if(dateMatch.find())
        {
            Log.i("date", "valiDate: I gots da date rite babee!!!!!");
            return true;
        }
        else
        {
            return false;
        }
    }

    public void createFoodListing(View view) {

        TextInputEditText typeView = (TextInputEditText) findViewById(R.id.inputType);
        TextInputEditText dateView = (TextInputEditText) findViewById(R.id.inputExpDate);
        TextInputEditText descView = (TextInputEditText) findViewById(R.id.inputDescription);


        String name = typeView.getText().toString();
        String date = dateView.getText().toString();
        String desc = descView.getText().toString();
        if (valiDate(date)) {
            int amount = amountBar.getProgress();

            TextView myTextView = (TextView) findViewById(R.id.giveTextView);

            dbManager.open();
            dbManager.insertFoodlisting(name, date, amount, currentPhotoPath, desc, currentUserID);
            dbManager.close();

            Toast.makeText(this, "Listing Created!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error, please give a vaild date in the format dd/mm/yy", Toast.LENGTH_SHORT).show();
        }
    }
}