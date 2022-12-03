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

    String currentPhotoPath;
    final int REQUEST_IMAGE_CAPTURE = 1;

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

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
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
            File photoFile = null;
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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                Log.i("img", "Calling take picture intent created");
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                galleryAddPic();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //retrieves photo from the takePictureActivity
        Log.i("img", String.valueOf(requestCode));
        Log.i("img", String.valueOf(resultCode));

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Bitmap photo = (Bitmap) data.getExtras().get("data");
            //imageView = findViewById(R.id.userImage);
            //imageView.setImageBitmap(photo);
        }

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