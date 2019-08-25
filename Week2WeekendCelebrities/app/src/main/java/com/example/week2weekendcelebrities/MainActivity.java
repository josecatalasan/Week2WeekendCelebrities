package com.example.week2weekendcelebrities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.week2weekendcelebrities.model.celebrity.Celebrity;
import com.example.week2weekendcelebrities.model.datasource.local.contentprovider.CelebrityContentProvider;
import com.example.week2weekendcelebrities.model.datasource.local.contentprovider.CelebrityProviderContract;
import com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract;

import java.util.ArrayList;

import static com.example.week2weekendcelebrities.model.datasource.local.contentprovider.CelebrityProviderContract.CONTENT_URI;
import static com.example.week2weekendcelebrities.model.datasource.local.contentprovider.CelebrityProviderContract.CelebrityEntry.CELEBRITY_CONTENT_URI;
import static com.example.week2weekendcelebrities.model.datasource.local.contentprovider.CelebrityProviderContract.CelebrityEntry.buildCelebUri;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_BORN;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_FAVORITE;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_HEIGHT;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_NAME;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_NATIONALITY;


public class MainActivity extends AppCompatActivity {
    RecyclerView rvCelebrities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Query construction
        String[] projection = {COL_NAME, COL_HEIGHT, COL_BORN, COL_NATIONALITY, COL_FAVORITE};
        Cursor cursor = getContentResolver().query(CELEBRITY_CONTENT_URI, projection, null, null, COL_NAME);

        ArrayList<Celebrity> celebList = buildList(cursor);

        //RecyclerView init
        rvCelebrities = findViewById(R.id.rvCelebrities);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rvCelebrities.setLayoutManager(layoutManager);
        CelebrityAdapter adapter = new CelebrityAdapter(celebList);
        rvCelebrities.setAdapter(adapter);

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
}
