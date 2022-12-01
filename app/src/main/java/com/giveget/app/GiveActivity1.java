package com.giveget.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.giveget.R;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

public class GiveActivity1 extends AppCompatActivity {

    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give1);
        Button giveButton = (Button)findViewById(R.id.givebtn);

        //display back button in action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //instantiate dbmanager
        dbManager = new DBManager(this);
        SeekBar amountBar = (SeekBar) findViewById(R.id.amountBar);
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
        giveButton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                TextInputEditText typeView = (TextInputEditText) findViewById(R.id.inputType);
                TextInputEditText dateView = (TextInputEditText) findViewById(R.id.inputExpDate);
                TextInputEditText imgView  = (TextInputEditText) findViewById(R.id.inputImage);
                TextInputEditText descView = (TextInputEditText) findViewById(R.id.inputDescription);
                String[] data = new String[]{
                                    typeView.getText().toString(),
                                    dateView.getText().toString(),
                                    imgView.getText().toString(),
                                    descView.getText().toString(),
                                            };
                int x = amountBar.getProgress();
                TextView myTextView = (TextView) findViewById(R.id.giveTextView);
                myTextView.setText(data[0] + data[1]+ x + data[2] + data[3]);
                //DB crashes app, many such cases!!
                //dbManager.insertFoodlisting(data[0],data[1] ,1,data[3],data[2]);
                };
            }
        );
    }

<<<<<<< HEAD

=======
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
>>>>>>> 9d7e17d105976af099df76f9de6324b9da142f39

    public void createFoodListing(View view)
    {
        dbManager.open(); //connect to database

<<<<<<< HEAD
=======
        dbManager.insertFoodlisting("Apples", "30/11/2022", 3, "apples.png", "Very Fresh");

>>>>>>> 9d7e17d105976af099df76f9de6324b9da142f39
        //dbManager.close();
    }


}