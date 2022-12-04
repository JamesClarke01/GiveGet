//This class was referenced from example given at: https://stackoverflow.com/questions/6710565/images-in-simplecursoradapter

package com.giveget.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.giveget.R;

import java.io.IOException;

public class ImageCursorAdaptor extends SimpleCursorAdapter {

    private Cursor cursor;
    private Context context;
    DBManager dbManager;
    DBManager.DBHelper dbHelper;


    public ImageCursorAdaptor(Context context, int layout, Cursor cursor, String[] from, int[] to) {
        super(context, layout, cursor, from, to);
        this.cursor = cursor;
        this.context = context;
        dbManager = new DBManager(context); //instantiate the dbManager
    }


    public View getView(int pos, View inView, ViewGroup parent) {
        View v = inView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.rowlayout_foodlisting, null);
        }
        this.cursor.moveToPosition(pos);

        //retrieve the info from the foodlisting cursor
        @SuppressLint("Range") String name = this.cursor.getString(this.cursor.getColumnIndex(dbHelper.KEY_FOODLISTING_NAME));

        @SuppressLint("Range") String expiry = this.cursor.getString(this.cursor.getColumnIndex(dbHelper.KEY_FOODLISTING_EXPIRY));

        @SuppressLint("Range") int amount = this.cursor.getInt(this.cursor.getColumnIndex(dbHelper.KEY_FOODLISTING_AMOUNT));

        @SuppressLint("Range") String desc = this.cursor.getString(this.cursor.getColumnIndex(dbHelper.KEY_FOODLISTING_DESC));

        @SuppressLint("Range") String imagePath = this.cursor.getString(this.cursor.getColumnIndex(dbHelper.KEY_FOODLISTING_IMAGE));

        //UPDATE THE TEXT VIEWS IN THE ACTIVITY
        ((TextView)v.findViewById(R.id.name)).setText(name);

        ((TextView)v.findViewById(R.id.expiry)).setText(expiry);

        ((TextView)v.findViewById(R.id.amount)).setText(String.valueOf(amount));

        ((TextView)v.findViewById(R.id.description)).setText(desc);


        //UPDATE THE IMAGE VIEW
        Bitmap imgBitmap = BitmapFactory.decodeFile(imagePath);
        if (imgBitmap != null)
        {
            try {
                imgBitmap = ImageHelper.rotateImageIfRequired(imgBitmap, imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ((ImageView)v.findViewById(R.id.listingImage)).setImageBitmap(imgBitmap);
        }

        return(v);

    }


}
