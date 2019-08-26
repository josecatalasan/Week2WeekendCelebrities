package com.example.week2weekendcelebrities;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.week2weekendcelebrities.model.celebrity.Celebrity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import static com.example.week2weekendcelebrities.model.datasource.local.contentprovider.CelebrityProviderContract.CelebrityEntry.CELEBRITY_CONTENT_URI;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_BORN;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_FAVORITE;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_HEIGHT;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_NAME;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_NATIONALITY;

public class ViewCelebrityActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private final String[] feet = {"4\'", "5\'", "6\'", "7\'", "8\'"};
    private final String[] inches = {"1\"","2\"","3\"","4\"","5\"","6\"","7\"","8\"","9\"","10\"","11\"","12\""};
    TextView tvName;
    EditText etNationality;
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

        //setup Spinners
        sFeet.setOnItemSelectedListener(this);
        sInches.setOnItemSelectedListener(this);
        ArrayAdapter feetAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, feet);
        feetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter inchesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, inches);
        inchesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sFeet.setAdapter(feetAdapter);
        sInches.setAdapter(inchesAdapter);

        fillFields();

        FloatingActionButton deleteFab = findViewById(R.id.fabDeleteCeleb);
        deleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DialogInterface.OnClickListener confirmation = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int choice) {
                        switch(choice){
                            case DialogInterface.BUTTON_POSITIVE:
                                //delete
                                getContentResolver().delete(CELEBRITY_CONTENT_URI, null, new String[]{tvName.getText().toString()});
                                Intent returnIntent = new Intent(view.getContext(), MainActivity.class);
                                startActivity(returnIntent);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }

                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Delete celebrity for database?").setPositiveButton("Yes", confirmation)
                        .setNegativeButton("No",confirmation).show();
            }
        });

        FloatingActionButton updateFab = findViewById(R.id.fabUpdateCeleb);
        updateFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv = new ContentValues();
                cv.put(COL_HEIGHT,sFeet.getSelectedItem().toString() + " " + sInches.getSelectedItem().toString());
                cv.put(COL_NATIONALITY, etNationality.getText().toString());
                cv.put(COL_BORN, String.format("%d/%d/%d", picker.getMonth()+1,picker.getDayOfMonth(),picker.getYear()));
                getContentResolver().update(CELEBRITY_CONTENT_URI, cv, null, new String[]{tvName.getText().toString()});
                Intent returnIntent = new Intent(view.getContext(), MainActivity.class);
                startActivity(returnIntent);
            }
        });

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                fillFields();
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

    private void fillFields(){
        Celebrity celeb = getIntent().getExtras().getParcelable("celeb");
        tvName.setText(celeb.getName());
        etNationality.setText(celeb.getNationality());
        //parse born
        String born = celeb.getBorn();
        String delims = "[/]";
        String[] tokens = born.split(delims);
        //datePicker.updateDate(2016, 5, 22);
        picker.updateDate(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[0])-1, Integer.parseInt(tokens[1]));
        //parse height

        //spinner.setSelection(39)
        String height = celeb.getHeight();
        delims = "[ ]";
        tokens = height.split(delims);
        for(int i=0; i<feet.length-1; i++){
            if(tokens[0].equals(feet[i]))
                sFeet.setSelection(i);
        }
        for(int i=0; i<inches.length-1;i++){
            if(tokens[1].equals(inches[i]))
                sInches.setSelection(i);
        }
    }

    private void bindViews(){
        tvName = findViewById(R.id.tvName);
        etNationality = findViewById(R.id.etNationality);
        sFeet = findViewById(R.id.spinnerFeet);
        sInches = findViewById(R.id.spinnerInches);
        picker = findViewById(R.id.pickBorn);
    }
}
