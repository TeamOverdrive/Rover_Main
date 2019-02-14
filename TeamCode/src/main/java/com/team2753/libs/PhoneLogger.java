package com.team2753.libs;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by joshua9889 on 5/25/2018.
 */

public class PhoneLogger {
    private FileOutputStream out = null;
    private Charset charset = null;

    public PhoneLogger(String fname){
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_data");
        myDir.mkdirs();
        File file = new File (myDir, fname);
        if (file.exists ())
            file.delete ();
        charset = Charset.forName("US-ASCII");

        try {
            out = new FileOutputStream(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void write(Object object){
        String toBeWritten = String.valueOf(object) + "\n";
        try {
            out.write(toBeWritten.getBytes(charset));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
