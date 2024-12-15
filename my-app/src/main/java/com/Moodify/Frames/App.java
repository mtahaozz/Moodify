package com.Moodify.Frames;

import java.util.ArrayList;

import com.Moodify.Genremix;
import com.Moodify.song;

public class App {

    public static void main(String[] args) {

        ArrayList<song> songList = new ArrayList<>();
        songList.add(new song(0, "accessToken", null, "authLink", "a1", 0, 0.2f, 0.3f, 80, "a"));
        songList.add(new song(0, "accessToken", null, "authLink", "a1", 0, 0.2f, 0.3f, 80, "a"));

        songList.add(new song(0, "accessToken", null, "authLink", "a1", 0, 0.2f, 0.3f, 80, "a"));

        songList.add(new song(0, "accessToken", null, "authLink", "a1", 0, 0.2f, 0.3f, 80, "a"));

        songList.add(new song(0, "accessToken", null, "authLink", "a1", 0, 0.2f, 0.3f, 80, "a"));

        songList.add(new song(0, "2", null, "authLink", "a2", 0, 0.35f, 0.3f, 80, "a"));
        songList.add(new song(0, "3", null, "authLink", "a3", 0, 0.42f, 0.3f, 80, "a"));
        songList.add(new song(0, "4", null, "authLink", "a4", 0, 0.21f, 0.3f, 80, "a"));
        songList.add(new song(0, "5", null, "authLink", "a5", 0, 0.32f, 0.3f, 80, "a"));
   



        
        ArrayList<String> userGenres = new ArrayList<>();
        userGenres.add("a");
        userGenres.add("b");

        Genremix newGenreMix = new Genremix(userGenres, "31", 5, true, songList);

        for (song song : newGenreMix.getSongsList()) {
            System.out.println(song.getTrackName());
        }

        
    }
    
}
