package edu.nnu.mp.simpledatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

/**
 * Created by User on 6/8/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Books.db";

    Context context ;

    public DatabaseHelper(Context context, String name, int version) {
        super(context, name, null, version);
        this.context = context ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BooksSQL.SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(BooksSQL.SQL_DELETE_ENTRIES);
        onCreate(db);
    }


    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    void deleteDatabase() {
        
        context.deleteDatabase(DATABASE_NAME);
    }



}
