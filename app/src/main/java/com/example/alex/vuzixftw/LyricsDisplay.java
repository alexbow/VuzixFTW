package com.example.alex.vuzixftw;

import android.content.Context;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by grantg on 10/9/16.
 */

public class LyricsDisplay {
    private int endLyricTime;
    private String currentLyrics;
    private boolean firstRun = true;
    private int currentTime = 0;
    private int endTime = 17;

//    public String lyricsController(String song) {
//        currentTime += 500; //TODO: Replace with current time.
//        parseLyric(song, currentTime);
//        System.out.println("Current time: " + currentTime); //Debug
//        System.out.println("En time: " + endLyricTime); //Debug
//        if(firstRun == true){
//            firstRun = false;
//            return currentLyrics;
//        }
//        if (currentLyrics == "END_LYRICS") {
//            System.out.println("returning en");
//            return "END_LYRICS";
//        }
//
//        if (currentTime < endLyricTime) {
//            System.out.println("returning not new");
//            return "NOT_NEW";
//        }
//
//        System.out.println(currentLyrics);
//        return currentLyrics;
//    }

    public int getEndTime() {
        return endLyricTime;
    }

    public String resetView() {
        return "";
    }

    public String getLyricsTest() {
        return "Fuck this shit";
    }

    public String parseLyric(String song, int current) {
        String lyrics = "";
        song += ".txt";
        System.out.println(song);

        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File lyricsFile = new File(sdCard, song);

            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(lyricsFile);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while ((lyrics = bufferedReader.readLine()) != null) {
                String[] l = lyrics.split("-");
                String[] starttime = l[0].split(":");
                //System.out.println(starttime[0] + ":" + starttime[1]);
                String[] endtime = l[1].split(":");
                //System.out.println(endtime[0] + ":" + endtime[1]);
                int start = ((Integer.parseInt(starttime[0]) * 60) + (Integer.parseInt(starttime[1])) * 1000);
                int end = ((Integer.parseInt(endtime[0]) * 60) + (Integer.parseInt(endtime[1])) * 1000);
                //System.out.println(start);
                //System.out.println(end);
                endLyricTime = end;


                int totmin = Integer.parseInt(endtime[0]) - Integer.parseInt(starttime[0]);
                int totsecs = (totmin * 60) + (Integer.parseInt(endtime[1]) - Integer.parseInt(starttime[1]));

                final String x = l[2];

                if (current >= start && current < end) {
                    /*
                    System.out.println("Current: " + current); //Debug
                    System.out.println("Start: " + start); //Debug
                    System.out.println("End: " + end); //Debug
                    */
                    currentLyrics = x;
                    return x;
                }

                try {

                    Thread.sleep(totsecs * 1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
            //TODO: Figure out how to return the end of the file.
            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            song + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + song + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }

        return lyrics;
    }
}