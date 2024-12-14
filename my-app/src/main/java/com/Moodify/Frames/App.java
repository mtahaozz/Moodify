package com.Moodify.Frames;

import java.util.ArrayList;

import com.Moodify.Genremix;
import com.Moodify.song;

public class App {

    public static void main(String[] args) {

        ArrayList<song> songList = new ArrayList<>();
        songList.add(new song(0, "accessToken", null, "authLink", "a1", 0, 0.2f, 0.3f, 80, "a"));
        songList.add(new song(0, "accessToken", null, "authLink", "a2", 0, 0.35f, 0.3f, 80, "a"));
        songList.add(new song(0, "accessToken", null, "authLink", "a3", 0, 0.42f, 0.3f, 80, "a"));
        songList.add(new song(0, "accessToken", null, "authLink", "a4", 0, 0.21f, 0.3f, 80, "a"));
        songList.add(new song(0, "accessToken", null, "authLink", "a5", 0, 0.32f, 0.3f, 80, "a"));
        songList.add(new song(0, "accessToken", null, "authLink", "b1", 0, 0.67f, 0.3f, 80, "b"));
        songList.add(new song(0, "accessToken", null, "authLink", "b2", 0, 0.89f, 0.3f, 80, "b"));
        songList.add(new song(0, "accessToken", null, "authLink", "b3", 0, 0.75f, 0.3f, 80, "b"));
        songList.add(new song(0, "accessToken", null, "authLink", "b4", 0, 0.45f, 0.3f, 80, "b"));
        songList.add(new song(0, "accessToken", null, "authLink", "b5", 0, 0.34f, 0.3f, 80, "b"));
        songList.add(new song(0, "accessToken", null, "authLink", "c1", 0, 0.42f, 0.3f, 80, "c"));
        songList.add(new song(0, "accessToken", null, "authLink", "c2", 0, 0.45f, 0.3f, 80, "c"));
        songList.add(new song(0, "accessToken", null, "authLink", "c3", 0, 0.43f, 0.3f, 80, "c"));
        songList.add(new song(0, "accessToken", null, "authLink", "c4", 0, 0.90f, 0.3f, 80, "c"));
        songList.add(new song(0, "accessToken", null, "authLink", "c5", 0, 0.95f, 0.3f, 80, "c"));




        
        ArrayList<String> userGenres = new ArrayList<>();
        userGenres.add("a");
        userGenres.add("b");

        Genremix newGenreMix = new Genremix(userGenres, "31", 5, true, songList);

        for (song song : newGenreMix.getSongsList()) {
            System.out.println(song.getTrackName());
        }

        
    }
    
}
