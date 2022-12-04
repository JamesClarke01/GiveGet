package com.giveget.app;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.giveget.R;

public class CreateAccountActivity extends AppCompatActivity{

    DBManager dbManager;
    DBManager.DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        dbManager = new DBManager(this);
        dbHelper = new DBManager.DBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createuser);

        //display back button in action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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

    public void createUser(View view)
    {
        String inputUserName = ((EditText)findViewById(R.id.inputUserName)).getText().toString();
        String inputUserAddress = ((EditText)findViewById(R.id.inputUserAddress)).getText().toString();

        dbManager.open();

        dbManager.insertUser(inputUserName, inputUserAddress);

        dbManager.close();

        Toast.makeText(this, "Account Created!", Toast.LENGTH_SHORT).show();
        finish();
    }

}

