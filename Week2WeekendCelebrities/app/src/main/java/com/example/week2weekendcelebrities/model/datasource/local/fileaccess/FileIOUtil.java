package com.example.week2weekendcelebrities.model.datasource.local.fileaccess;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;

public class FileIOUtil {

    // write text to file
    public void WriteBtn(Context context) {
        // add-write text into file
        try {
            FileOutputStream fileout = context.openFileOutput("mytextfile.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write("Some info");
            outputWriter.close();

            //display file saved message
            Log.d("TAG", "File saved successfully!");
            //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Read text from file
    public void readBtn(Context context) {
        //reading text from file
        try {
            FileInputStream fileIn= context.openFileInput("mytextfile.txt");
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

            Log.d("TAG", s);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
