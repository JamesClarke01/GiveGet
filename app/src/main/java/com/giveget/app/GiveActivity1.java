package com.giveget.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
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

import com.example.giveget.R;
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
    File photoFile;


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

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    private static Bitmap rotateImageIfRequired(Bitmap img, File imageFile) throws IOException {

        ExifInterface ei = new ExifInterface(imageFile.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    public void displayImage(File imgFile) throws IOException {
        if (imgFile.exists())
        {
            Bitmap imgBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            imgBitmap = rotateImageIfRequired(imgBitmap, imgFile);

            ImageView imageview = findViewById(R.id.userImage);

            imageview.setImageBitmap(imgBitmap);
        }
    }

    //implemented using official android documentation at https://developer.android.com/training/camera-deprecated/photobasics
    public void takePhoto(View view)
    {

        Log.i("img", "taking photo");

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            Log.i("img", "There is a camera activity");
            // Create the File where the photo should go
            photoFile = null;
            try
            {
                photoFile = createImageFile();
                Log.i("img", "Image file created");
            } catch (IOException ex)
            {
                Log.e("img", "Error occurred while creating the File");
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                Log.i("img", "Image file created 2");
                Uri photoURI = FileProvider.getUriForFile(this, "com.giveget.android.fileprovider", photoFile);
                Log.i("img", String.valueOf(photoURI));
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                Log.i("img", "Calling take picture intent created");
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //Displays photo

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                displayImage(photoFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void createFoodListing(View view)
    {

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
}