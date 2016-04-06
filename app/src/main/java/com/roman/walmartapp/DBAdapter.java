package com.roman.walmartapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    static final String KEY_ROWID = "_id";
    static final String KEY_JSON = "jsonFile";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "favItems";
    static final int DATABASE_VERSION = 2;

    static final String DATABASE_CREATE =
            "create table "+DATABASE_TABLE+" (_id integer primary key autoincrement, "
                    + "jsonFile text not null);";

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS favItems");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close() {
        DBHelper.close();
    }

    public void resetDB(){
        db.execSQL("DROP TABLE IF EXISTS favItems");
        try {
            db.execSQL(DATABASE_CREATE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //---insert a contact into the database---
    public void insertItem(String mJson) {
        ContentValues insertValues = new ContentValues();
        insertValues.put(KEY_JSON, mJson);
        db.insertOrThrow(DATABASE_TABLE,null,insertValues);
    }

    //---deletes a particular contact---
    public boolean deleteItem(String mJson) {
        return db.delete(DATABASE_TABLE, KEY_JSON + "='" + mJson +"'", null) > 0;
    }

    //---retrieves all the contacts---
    public Cursor getAllItems() {
        return db.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_JSON}, null, null, null, null, null);
    }

    //---retrieves a particular contact---
    public Cursor getItem(String mJson) throws SQLException {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[]{KEY_ROWID,
                                KEY_JSON}, KEY_JSON + "='" + mJson +"'", null,
                        null, null, null, null);
        return mCursor;
    }

    //---updates a contact---
    public boolean updateItem(long rowId, String mJson) {
        ContentValues args = new ContentValues();
        args.put(KEY_JSON, mJson);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
