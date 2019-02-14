package com.team2753.libs;

import android.os.Environment;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Vector;

/**
 * Created by joshua9889 on 5/25/2018.
 */

public class PhoneFileReader {
    private FileInputStream in = null;
    private Charset charset = null;

    public PhoneFileReader(String fname){
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_data");
        myDir.mkdirs();
        File file = new File (myDir, fname);
        charset = Charset.forName("US-ASCII");


        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String read(){
        String text = "";
        int content;
        try {
            while ((content = in.read()) != -1) {
                // convert to char and display it
                text += (char)content;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public void close(){
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final Vector constants = new Vector();

    /**
     * Reads the constants file and overrides the values in this class for any constants it contains.
     */
    public static void readConstantsFromFile() {
        DataInputStream constantsStream;
        PhoneFileReader constantsFile;
        byte[] buffer = new byte[255];
        String content = "";

        // Read everything from the file into one string.
        constantsFile = new PhoneFileReader("constants.txt");
        content = constantsFile.read();
        constantsFile.close();

        try {
            // Extract each line separately.
            String[] lines = split(content, "\n");
            for (int i = 0; i < lines.length; i++) {
                // Extract the key and value.
                String[] line = split(lines[i], "=");
                if (line.length != 2) {
                    System.out.println("Error: invalid constants file line: " +
                            (lines[i].length() == 0 ? "(empty line)" : lines[i]));
                    continue;
                }

                boolean found = false;
                // Search through the constants until we find one with the same name.
                for (int j = 0; j < constants.size(); j++) {
                    Constant constant = (Constant)constants.elementAt(j);
                    if (constant.getName().compareTo(line[0]) == 0) {
                        System.out.println("Setting " + constant.getName() + " to " + Double.parseDouble(line[1]));
                        constant.setVal(Double.parseDouble(line[1]));
                        System.out.println("Value: " + constant.getDouble());
                        found = true;
                        break;
                    }
                }

                if (!found)
                    System.out.println("Error: the constant doesn't exist: " + lines[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("======================++==================");
        for (int i = 0; i < constants.size(); i++) {
            Constant con = (Constant)constants.elementAt(i);
            System.out.println(con.getName() + ": " + con.getDouble());
        }
    }

    public static String[] split(String input, String delimiter) {
        Vector node = new Vector();
        int index = input.indexOf(delimiter);
        while (index >= 0) {
            node.addElement(input.substring(0, index));
            input = input.substring(index + delimiter.length());
            index = input.indexOf(delimiter);
        }
        node.addElement(input);

        String[] retString = new String[node.size()];
        for (int i = 0; i < node.size(); ++i) {
            retString[i] = (String) node.elementAt(i);
        }

        return retString;
    }

    public static class Constant {
        private String name;
        private double value;

        public Constant(String name, double value) {
            this.name = name;
            this.value = value;
            constants.addElement(this);
        }

        public String getName(){
            return name;
        }

        public double getDouble() {
            return value;
        }

        public int getInt() {
            return (int)value;
        }

        public void setVal(double value){
            this.value = value;
        }

        public String toString(){
            return name + ": " + value;
        }
    }
}
