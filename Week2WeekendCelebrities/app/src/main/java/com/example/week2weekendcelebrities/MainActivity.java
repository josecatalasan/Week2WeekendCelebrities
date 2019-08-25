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

import com.example.week2weekendcelebrities.model.datasource.local.contentprovider.CelebrityContentProvider;
import com.example.week2weekendcelebrities.model.datasource.local.contentprovider.CelebrityProviderContract;
import com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract;

import static com.example.week2weekendcelebrities.model.datasource.local.contentprovider.CelebrityProviderContract.CONTENT_URI;
import static com.example.week2weekendcelebrities.model.datasource.local.contentprovider.CelebrityProviderContract.CelebrityEntry.CELEBRITY_CONTENT_URI;
import static com.example.week2weekendcelebrities.model.datasource.local.contentprovider.CelebrityProviderContract.CelebrityEntry.buildCelebUri;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_FAVORITE;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_NAME;


public class MainActivity extends AppCompatActivity {
    //datePicker.updateDate(2016, 5, 22);
    //String text = mySpinner.getSelectedItem().toString();
    //spinner.setSelection(39)
    RecyclerView rvCelebrities;
    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test = findViewById(R.id.testView);

        //Query construction
        String[] projection = {COL_NAME};
        Cursor cursor = getContentResolver().query(CELEBRITY_CONTENT_URI, projection, null, null, COL_NAME);

        int index = cursor.getColumnIndex(COL_NAME);
        if(cursor != null){
            cursor.moveToFirst();
            String word = cursor.getString(cursor.getColumnIndex(CelebDatabaseContract.COL_NAME));
            test.setText(word);
            cursor.moveToNext();
            word = cursor.getString(cursor.getColumnIndex(CelebDatabaseContract.COL_NAME));
            test.setText(word);
        }



        //RecyclerView init
//        rvCelebrities = findViewById(R.id.rvCelebrities);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
//        rvCelebrities.setLayoutManager(layoutManager);


//        provider = getContentResolver();
//        Uri uri = CelebrityProviderContract.CONTENT_URI;

    }
}
