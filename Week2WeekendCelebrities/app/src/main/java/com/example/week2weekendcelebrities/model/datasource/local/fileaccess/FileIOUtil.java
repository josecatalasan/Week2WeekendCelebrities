package com.example.week2weekendcelebrities.model.datasource.local.fileaccess;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.example.week2weekendcelebrities.model.celebrity.Celebrity;
import com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.week2weekendcelebrities.model.datasource.local.contentprovider.CelebrityProviderContract.CelebrityEntry.CELEBRITY_CONTENT_URI;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_BORN;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_FAVORITE;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_HEIGHT;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_NAME;
import static com.example.week2weekendcelebrities.model.datasource.local.database.CelebDatabaseContract.COL_NATIONALITY;

public class FileIOUtil {

    public static void writeToFile(Context context) {
        try {
            FileOutputStream fileout = context.openFileOutput("favorite_list.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            //get info here
            String[] projection = {COL_NAME, COL_HEIGHT, COL_BORN, COL_NATIONALITY, COL_FAVORITE};
            Cursor cursor = context.getContentResolver().query(CELEBRITY_CONTENT_URI, projection, COL_FAVORITE + " = ?", new String[]{String.valueOf(1)}, COL_NAME);
            ArrayList<Celebrity> celebList = buildList(cursor);

            outputWriter.write("These are your favorite celebrities.\n");
            for(Celebrity c : celebList) {
                outputWriter.write(String.format("%s %s %s %s\n", c.getName(), c.getHeight(), c.getBorn(), c.getNationality()));
            }

            outputWriter.close();

            //display file saved message
            Log.d("TAG", "File write succesful.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readFromFile(Context context) {
        try {
            FileInputStream fileIn= context.openFileInput("favorite_list.txt");
            InputStreamReader inputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[100];
            String s="";
            int charRead;

            while ((charRead=inputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            inputRead.close();
            //do something with s
            Toast.makeText(context, s, Toast.LENGTH_LONG).show();

            Log.d("TAG", s);

            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    private static ArrayList<Celebrity> buildList(Cursor cursor){
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
