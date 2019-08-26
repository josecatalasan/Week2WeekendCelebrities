package com.example.week2weekendcelebrities.model.datasource.local.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.week2weekendcelebrities.model.celebrity.Celebrity;
import com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract;
import com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseHelper;

import static com.example.week2weekendcelebrities.model.datasource.local.contentprovider.CelebrityProviderContract.CONTENT_AUTHORITY;
import static com.example.week2weekendcelebrities.model.datasource.local.contentprovider.CelebrityProviderContract.CelebrityEntry.CELEBRITY_CONTENT_URI;
import static com.example.week2weekendcelebrities.model.datasource.local.contentprovider.CelebrityProviderContract.PATH_CELEBRITY;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_NAME;

public class CelebrityContentProvider extends ContentProvider {
    public static final int CELEBRITY = 100;
    public static final int CELEBRITY_NAME = 101;
    private CelebDatabaseHelper dbHelper;
    private UriMatcher uriMatcher = buildUriMatcher();

    @Override
    public boolean onCreate() {
        dbHelper = new CelebDatabaseHelper(getContext());
        //init database
        initDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortby) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor returnCursor = null;
        switch(uriMatcher.match(uri)){
            case CELEBRITY:
                returnCursor = db.query(
                        CelebDatabaseContract.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortby);
                break;
            case CELEBRITY_NAME:
                returnCursor = db.query(
                        CelebDatabaseContract.TABLE_NAME,
                        projection,
                        CelebrityProviderContract.CelebrityEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortby);
                break;
        }
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Override
    public String getType(Uri uri) {
        switch(uriMatcher.match(uri)){
            case CELEBRITY:
                return CelebrityProviderContract.CelebrityEntry.CONTENT_TYPE;
            case CELEBRITY_NAME:
                return CelebrityProviderContract.CelebrityEntry.CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = db.insert(CelebDatabaseContract.TABLE_NAME, null, contentValues);
        Uri returnUri = CelebrityProviderContract.CelebrityEntry.buildCelebUri(id);
        getContext().getContentResolver().notifyChange(returnUri, null);

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int id = db.delete(CelebDatabaseContract.TABLE_NAME, COL_NAME + " = ?", strings);
        final Uri newObjectUri = ContentUris.withAppendedId(CELEBRITY_CONTENT_URI,id);
        getContext().getContentResolver().notifyChange(newObjectUri,null);
        return id;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int id = db.update(CelebDatabaseContract.TABLE_NAME,contentValues, COL_NAME + " = ?", strings);
        final Uri newObjectUri = ContentUris.withAppendedId(CELEBRITY_CONTENT_URI,id);
        getContext().getContentResolver().notifyChange(newObjectUri,null);
        return id;
    }

    public UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_CELEBRITY, CELEBRITY);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_CELEBRITY+"/#", CELEBRITY_NAME);
        return uriMatcher;
    }

    private void initDatabase(){
        Celebrity celeb = new Celebrity("Brad Pitt","5\' 11\"", "12/18/1963", "American");
        dbHelper.insert(celeb);
        celeb = new Celebrity("Edward Norton", "6\' 0\"", "8/18/1969","American");
        dbHelper.insert(celeb);
        celeb = new Celebrity("Hugh Laurie", "6\' 2\"", "6/11/1959","English");
        dbHelper.insert(celeb);
    }
}

