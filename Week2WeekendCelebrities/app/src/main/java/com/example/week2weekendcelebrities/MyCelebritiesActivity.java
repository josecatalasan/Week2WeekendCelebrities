package com.example.week2weekendcelebrities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import com.example.week2weekendcelebrities.model.celebrity.Celebrity;
import com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract;

import java.util.ArrayList;

import static com.example.week2weekendcelebrities.model.datasource.local.contentprovider.CelebrityProviderContract.CelebrityEntry.CELEBRITY_CONTENT_URI;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_BORN;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_FAVORITE;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_HEIGHT;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_NAME;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_NATIONALITY;

public class MyCelebritiesActivity extends AppCompatActivity {

    RecyclerView rvFavorites;
    CelebrityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_celebrities);

        dbInit();
    }


    private ArrayList<Celebrity> buildList(Cursor cursor){
        ArrayList<Celebrity> list = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                Celebrity celeb = new Celebrity(
                        cursor.getString(cursor.getColumnIndex(CelebDatabaseContract.COL_NAME)),
                        cursor.getString(cursor.getColumnIndex(CelebDatabaseContract.COL_HEIGHT)),
                        cursor.getString(cursor.getColumnIndex(CelebDatabaseContract.COL_BORN)),
                        cursor.getString(cursor.getColumnIndex(CelebDatabaseContract.COL_NATIONALITY)));
                celeb.setFavorite(cursor.getInt(cursor.getColumnIndex(CelebDatabaseContract.COL_FAVORITE)));
                list.add(celeb);
            } while(cursor.moveToNext());
        }
        return list;
    }

    private void dbInit(){
        //Query construction
        String[] projection = {COL_NAME, COL_HEIGHT, COL_BORN, COL_NATIONALITY, COL_FAVORITE};
        Cursor cursor = getContentResolver().query(CELEBRITY_CONTENT_URI, projection, COL_FAVORITE + " = ?", new String[]{String.valueOf(1)}, COL_NAME);
        ArrayList<Celebrity> celebList = buildList(cursor);
        //RecyclerView init
        rvFavorites = findViewById(R.id.rvFavorites);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rvFavorites.setLayoutManager(layoutManager);
        adapter = new CelebrityAdapter(celebList);
        adapter.notifyDataSetChanged();
        rvFavorites.setAdapter(adapter);
    }
}
