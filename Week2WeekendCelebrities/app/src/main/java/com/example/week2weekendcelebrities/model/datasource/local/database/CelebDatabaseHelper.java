package com.example.week2weekendcelebrities.model.datasource.local.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.week2weekendcelebrities.model.celebrity.Celebrity;

import java.util.ArrayList;

public class CelebDatabaseHelper extends SQLiteOpenHelper{

    public CelebDatabaseHelper(Context context) {
        super(context, CelebDatabaseContract.DATABASE_NAME, null, CelebDatabaseContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CelebDatabaseContract.QUERY_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(CelebDatabaseContract.DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    //Get all Celebrities
    public ArrayList<Celebrity> getAllCelebrities(){
        ArrayList<Celebrity> celebList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(CelebDatabaseContract.QUERY_SELECT_ALL,null);

        if(cursor.moveToFirst()){
            do{
                Celebrity celeb = new Celebrity(
                        cursor.getString(cursor.getColumnIndex(CelebDatabaseContract.COL_NAME)),
                        cursor.getString(cursor.getColumnIndex(CelebDatabaseContract.COL_HEIGHT)),
                        cursor.getString(cursor.getColumnIndex(CelebDatabaseContract.COL_BORN)),
                        cursor.getString(cursor.getColumnIndex(CelebDatabaseContract.COL_NATIONALITY)));
                celeb.setFavorite(cursor.getInt(cursor.getColumnIndex(CelebDatabaseContract.COL_FAVORITE)));
                celebList.add(celeb);
            } while(cursor.moveToNext());
        }
        return celebList;
    }

    //get all Favorites
    public ArrayList<Celebrity> getAllFavorites(){
        ArrayList<Celebrity> celebList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(CelebDatabaseContract.QUERY_SELECT_FAVORITES,null);

        if(cursor.moveToFirst()){
            do{
                Celebrity celeb = new Celebrity(
                        cursor.getString(cursor.getColumnIndex(CelebDatabaseContract.COL_NAME)),
                        cursor.getString(cursor.getColumnIndex(CelebDatabaseContract.COL_HEIGHT)),
                        cursor.getString(cursor.getColumnIndex(CelebDatabaseContract.COL_BORN)),
                        cursor.getString(cursor.getColumnIndex(CelebDatabaseContract.COL_NATIONALITY)));
                celeb.setFavorite(cursor.getInt(cursor.getColumnIndex(CelebDatabaseContract.COL_FAVORITE)));
                celebList.add(celeb);
            } while(cursor.moveToNext());
        }
        return celebList;
    }

    public Celebrity getCelebByName(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(CelebDatabaseContract.QUERY_SELECT_BY_NAME(name),null);

        if(cursor.moveToFirst()){
            Celebrity celeb = new Celebrity(cursor.getString(cursor.getColumnIndex(CelebDatabaseContract.COL_NAME)),
                    cursor.getString(cursor.getColumnIndex(CelebDatabaseContract.COL_HEIGHT)),
                    cursor.getString(cursor.getColumnIndex(CelebDatabaseContract.COL_BORN)),
                    cursor.getString(cursor.getColumnIndex(CelebDatabaseContract.COL_NATIONALITY)));
            celeb.setFavorite(cursor.getInt(cursor.getColumnIndex(CelebDatabaseContract.COL_FAVORITE)));

            return celeb;
        }
        return null;
    }

    public void insert(Celebrity celeb){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //put values in contentValues
        cv.put(CelebDatabaseContract.COL_NAME, celeb.getName());
        cv.put(CelebDatabaseContract.COL_HEIGHT, celeb.getHeight());
        cv.put(CelebDatabaseContract.COL_BORN, celeb.getBorn());
        cv.put(CelebDatabaseContract.COL_NATIONALITY, celeb.getNationality());
        cv.put(CelebDatabaseContract.COL_FAVORITE, celeb.getFavorite());

        db.insert(CelebDatabaseContract.TABLE_NAME, null, cv);
    }

    public void update(Celebrity celeb){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //put values in contentValues
        cv.put(CelebDatabaseContract.COL_HEIGHT, celeb.getHeight());
        cv.put(CelebDatabaseContract.COL_BORN, celeb.getBorn());
        cv.put(CelebDatabaseContract.COL_NATIONALITY, celeb.getNationality());
        cv.put(CelebDatabaseContract.COL_FAVORITE, celeb.getFavorite());

        db.update(CelebDatabaseContract.TABLE_NAME, cv, "name = ?", new String[]{celeb.getName()});
    }

    public void delete(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CelebDatabaseContract.TABLE_NAME, "name = ?", new String[]{name});
    }
}
