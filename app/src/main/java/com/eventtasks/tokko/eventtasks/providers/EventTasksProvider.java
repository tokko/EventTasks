package com.eventtasks.tokko.eventtasks.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class EventTasksProvider extends ContentProvider {
    public static final String AUTHORITY = "com.eventtasks.tokko.eventtasks.providers";
    //columns
    public static final String ID = "_id";
    public static final String TITLE = "title";
    private static final String DATABASE_NAME = "eventtasks";
    //table names
    private static final String TABLE_EVENT_NAME = "events";

    //table keys
    private static final int KEY_EVENT = 0;
    private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY + "/");
    //uris
    private static UriMatcher um = new UriMatcher(UriMatcher.NO_MATCH);
    public static final Uri URI_EVENTS = makeUri(TABLE_EVENT_NAME, KEY_EVENT);
    private Helper db;

    public EventTasksProvider() {
    }

    private static Uri makeUri(String table, int key) {
        um.addURI(AUTHORITY, table, key);
        return BASE_URI.buildUpon().appendPath(table).build();
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        db = new Helper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private class Helper extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 1;

        private static final String CREATE_EVENTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EVENT_NAME + "(" +
                ID + " INTEGER PRIMARY KEY, " +
                TITLE + " TEXT UNIQUE NOT NULL);";

        public Helper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_EVENTS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
