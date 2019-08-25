package com.example.week2weekendcelebrities;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class ViewCelebrityActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private final String[] feet = {"4\'", "5\'", "6\'", "7\'", "8\'"};
    private final String[] inches = {"1\"","2\"","3\"","4\"","5\"","6\"","7\"","8\"","9\"","10\"","11\"","12\""};
    EditText etName, etNationality;
    Spinner sFeet, sInches;
    DatePicker picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_celebrity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        bindViews();
        sFeet.setOnItemSelectedListener(this);
        sInches.setOnItemSelectedListener(this);
        ArrayAdapter feetAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,feet);
        feetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter inchesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, inches);
        inchesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sFeet.setAdapter(feetAdapter);
        sInches.setAdapter(inchesAdapter);

        FloatingActionButton fab = findViewById(R.id.fabAddCeleb);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_celebrity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.resetFields:
                resetFields();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //spinner
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //spinner

    }

    private void resetFields(){

    }

    private void bindViews(){
        etName = findViewById(R.id.etName);
        etNationality = findViewById(R.id.etNationality);
        sFeet = findViewById(R.id.spinnerFeet);
        sInches = findViewById(R.id.spinnerInches);
        picker = findViewById(R.id.pickBorn);
    }
}
