package com.example.week2weekendcelebrities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.week2weekendcelebrities.model.celebrity.Celebrity;
import com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.example.week2weekendcelebrities.model.datasource.local.contentprovider.CelebrityProviderContract.CelebrityEntry.CELEBRITY_CONTENT_URI;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_BORN;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_FAVORITE;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_HEIGHT;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_NAME;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_NATIONALITY;


public class MainActivity extends AppCompatActivity {
    RecyclerView rvCelebrities;
    CelebrityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        dbInit();

        FloatingActionButton fabAdd = findViewById(R.id.fabAddCeleb);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(view.getContext() , AddCelebrityActivity.class);
                startActivity(addIntent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        dbInit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.myFavorites:
                //start my favorites
                Intent favIntent = new Intent(this, MyCelebritiesActivity.class);
                startActivity(favIntent);
        }
        return super.onOptionsItemSelected(item);
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
        Cursor cursor = getContentResolver().query(CELEBRITY_CONTENT_URI, projection, null, null, COL_NAME);
        ArrayList<Celebrity> celebList = buildList(cursor);
        //RecyclerView init
        rvCelebrities = findViewById(R.id.rvCelebrities);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rvCelebrities.setLayoutManager(layoutManager);
        adapter = new CelebrityAdapter(celebList);
        adapter.notifyDataSetChanged();
        rvCelebrities.setAdapter(adapter);
    }
}
