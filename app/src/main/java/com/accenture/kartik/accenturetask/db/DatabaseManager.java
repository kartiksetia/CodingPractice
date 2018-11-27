package com.accenture.kartik.accenturetask.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.accenture.kartik.accenturetask.domain.Album;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static DatabaseManager sInstance;
    private static final String ASC_ORDER = "ASC";

    /**
     * The context.
     */
    private Context mContext;

    /**
     * The sql database.
     */
    private SQLiteDatabase database;

    private DBHelper mDbHelper;

    public static synchronized DatabaseManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseManager(context.getApplicationContext());
        }

        return sInstance;
    }

    /**
     * Constructor.
     *
     * @param context The context,
     */
    public DatabaseManager(Context context) {
        mContext = context;
        mDbHelper = new DBHelper(context);
    }

    /**
     * Return a {@link Cursor} that contains every Album in the database.
     *
     * @return {@link Cursor} containing all Album results.
     */
    public List<Album> queryAllAlbums() {
        //TODO: Implement the query
        String selectQuery = "SELECT  * FROM " + AlbumTable.TABLE_NAME + " ORDER BY " + AlbumTable.TITLE + " " + ASC_ORDER;

        List<Album> albums = new ArrayList<>();
        Cursor cursor = database.rawQuery(selectQuery, null);

        // populate the storyboards list.
        try {
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex(AlbumTable.TITLE));
                Album album = new Album();
                album.setTitle(cursor.getString(cursor.getColumnIndex(AlbumTable.TITLE)));
                albums.add(album);
            }
        } finally {
            cursor.close();
        }

            return albums;

    }


        public void storeAlbum(Album album) {

            ContentValues values = new ContentValues();
            values.put(AlbumTable.TITLE,album.getTitle());

            database.insert(
                    AlbumTable.TABLE_NAME,
                    null,
                    values);
        }

        /**
         * Opens the database connection.
         *
         * @throws SQLException
         */
        public void open () throws SQLException {

            database = mDbHelper.getWritableDatabase();
        }

        /**
         * Closes the database connection.
         */
        public void close () {

            mDbHelper.close();
        }

        /**
         * delete the database.
         */
        public void deleteDataBase () {
            database.delete(AlbumTable.TABLE_NAME, null, null);
        }


        public boolean isExist () {
            String count = "SELECT count(*) FROM " + AlbumTable.TABLE_NAME;

            Cursor cursor = database.rawQuery(count, null);
            cursor.moveToFirst();
            int icount = cursor.getInt(0);
            if (icount > 0) {
                return true;
            } else {
                return false;
            }
        }
    }


