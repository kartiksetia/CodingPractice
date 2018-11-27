package com.accenture.kartik.accenturetask.db;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.accenture.kartik.accenturetask.R;
import com.accenture.kartik.accenturetask.domain.Album;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = DBHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "insects.db";
    private static final int DATABASE_VERSION = 1;

    //Used to read data from res/ and assets/
    private Resources mResources;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mResources = context.getResources();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO: Create and fill the database
        createAlbumTable(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: Handle database version upgrades
    }


    /**
     * Creates the Insect table.
     *
     * @param db The database.
     */
    private void createAlbumTable(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE " + AlbumTable.TABLE_NAME + " (" +
                        AlbumTable.TITLE + " TEXT PRIMARY KEY NOT NULL);"

        );
    }

}

