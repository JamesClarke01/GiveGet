package com.giveget.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giveget.R;

public class AccountSelection extends AppCompatActivity {

    DBManager dbManager;
    DBManager.DBHelper dbHelper;
    ListView userListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_selection);

        dbManager = new DBManager(this); //instantiate the dbManager

        //display back button in action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initUserList();


    }

    @Override
    //runs when activity is returned to
    protected void onRestart() {
        super.onRestart();

        initUserList();

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



    public void initUserList()
    {

        dbManager.open();

        Cursor userCursor = dbManager.getAllUsers();


        if(userCursor.getCount() > 0)  //if results are returned
        {
            userListView = findViewById(R.id.accountlistview);  //list view

            String[] dbTableColumns = {"name", "address"};

            int[] rowLayoutIDs = new int[] {R.id.name, R.id.address};

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.rowlayout_user, userCursor, dbTableColumns, rowLayoutIDs);

            userListView.setAdapter(adapter);

            userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Cursor selectedUserCursor = (Cursor) parent.getItemAtPosition(position);

                    @SuppressLint("Range") int currentUser = selectedUserCursor.getInt(selectedUserCursor.getColumnIndex(dbHelper.KEY_USER_ID));

                    setResult(Activity.RESULT_OK, new Intent().putExtra("currentUserID", currentUser));
                    finish();

                    //Toast.makeText(AccountSelection.this, "User " + currentUser + " Selected" , Toast.LENGTH_SHORT).show();

                    /*
                    Intent gotoHomeScreen = new Intent(AccountSelection.this, MainActivity.class);
                    gotoHomeScreen.putExtra("currentUserID", currentUser);  //adds the value of userID to the intent with the key "userID"

                    startActivity(gotoHomeScreen);

                     */

                }
            });
        }
        else
        {
            Log.i("user", "No Accounts");
            ((TextView)findViewById(R.id.infoText)).setText("No Accounts added, please add one!");
        }

        dbManager.close();
    }


    public void gotoCreateAccount(View view)
    {
        Intent gotoIntent = new Intent(AccountSelection.this, CreateAccountActivity.class);
        startActivity(gotoIntent);
    }


}