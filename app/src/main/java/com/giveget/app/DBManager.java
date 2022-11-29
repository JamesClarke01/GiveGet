package com.giveget.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBManager {
    //this class is used for CRUD operations

    Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public DBManager(Context context) {this.context = context;}

    public DBManager open() throws SQLException
    {
        dbHelper = new DBHelper(context);  //instantiate the db helper class
        database = dbHelper.getWritableDatabase();  //gets database, and if doesn't already exist, creates it
        return this;
    }

    public void close() {dbHelper.close();}

    public long insertFoodlisting(String name, String expiryDate, int amount, String image, String description)
    {
        ContentValues insertingValues = new ContentValues();
        insertingValues.put(dbHelper.KEY_FOODLISTING_NAME, name);
        insertingValues.put(dbHelper.KEY_FOODLISTING_EXPIRY, expiryDate);
        insertingValues.put(dbHelper.KEY_FOODLISTING_AMOUNT, amount);
        insertingValues.put(dbHelper.KEY_FOODLISTING_IMAGE, image);
        insertingValues.put(dbHelper.KEY_FOODLISTING_DESC, description);

        return database.insert(dbHelper.TABLE_NAME_FOODLISTING, null, insertingValues);
    }

    public Cursor getAllFoodlistings() throws SQLException{
        return database.rawQuery("SELECT * FROM" + dbHelper.TABLE_NAME_FOODLISTING, null);
    }

}


class DBHelper extends SQLiteOpenHelper {
    //This class is used to create the database
    //Code structure for this class loosely inspired by example found at: https://guides.codepath.com/android/Local-Databases-with-SQLiteOpenHelper


    //database information
    public static final String DATABASE_NAME = "GiveGetDB";
    public static final int DATABASE_VERSION = 1;

    //table names
    public static final String TABLE_NAME_FOODLISTING = "FoodListing";
    public static final String TABLE_NAME_USER = "User";

    //foodlisting table columns
    private static final String KEY_FOODLISTING_ID = "id";
    public static final String KEY_FOODLISTING_NAME = "name";
    public static final String KEY_FOODLISTING_EXPIRY = "expiry";
    public static final String KEY_FOODLISTING_AMOUNT = "amount";
    public static final String KEY_FOODLISTING_IMAGE = "image";
    public static final String KEY_FOODLISTING_DESC = "description";
    //need to add foreign keys here



    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //create table foodlisting
        final String CREATE_TABLE_FOODLISTING =
                "CREATE TABLE " + TABLE_NAME_FOODLISTING +
                        "(" +
                            KEY_FOODLISTING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                            KEY_FOODLISTING_NAME + " TEXT," +
                            KEY_FOODLISTING_EXPIRY + " TEXT," +
                            KEY_FOODLISTING_AMOUNT + " INTEGER," +
                            KEY_FOODLISTING_IMAGE + " TEXT," +
                            KEY_FOODLISTING_DESC + " TEXT" +
                        ")";

        db.execSQL(CREATE_TABLE_FOODLISTING);

    }

    //if version number is greater than old version number when this is called, recreate the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_FOODLISTING);

            onCreate(db);
        }

    }
}
