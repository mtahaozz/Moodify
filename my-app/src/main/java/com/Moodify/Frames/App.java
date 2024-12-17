package com.Moodify.Frames;

import java.util.ArrayList;

import com.Moodify.Genremix;
import com.Moodify.Inventory;
import com.Moodify.MusicRead;
import com.Moodify.artist;
import com.Moodify.song;

public class App {

    public static void main(String[] args) {
        Inventory.fillAllSongs();
        try {
            artist newArtist = new artist("Chord Overstreet");
            ArrayList<song> artistpopularsongs = Inventory.fillPopularSongs(newArtist);
            for (song song : artistpopularsongs) {
                System.out.println(song.getTrackName());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /*String query = "micheal";

        ArrayList<song> songList = new ArrayList<>();
        try {
            MusicRead.fillMusicList(songList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ArrayList<song> bulunanSongs = new ArrayList<>();

        for (song s : songList) {
            
            String artistName = s.getSongArtist().getARTISTNAME().toLowerCase(); 
            String songName = s.getTrackName().toLowerCase(); 

            if (artistName.contains(query) || songName.contains(query)) {
                bulunanSongs.add(s);
            }
        }

        System.out.println(bulunanSongs);

        /*ArrayList<song> songList = new ArrayList<>();
        songList.add(new song(0, "accessToken", null, "authLink", "a1", 0, 0.2f, 0.3f, 80, "a"));
        songList.add(new song(0, "accessToken", null, "authLink", "a1", 0, 0.2f, 0.3f, 80, "a"));

        songList.add(new song(0, "accessToken", null, "authLink", "a1", 0, 0.2f, 0.3f, 80, "a"));

        songList.add(new song(0, "accessToken", null, "authLink", "a1", 0, 0.2f, 0.3f, 80, "a"));

        songList.add(new song(0, "accessToken", null, "authLink", "a1", 0, 0.2f, 0.3f, 80, "a"));

        songList.add(new song(0, "2", null, "authLink", "a2", 0, 0.35f, 0.3f, 80, "a"));
        songList.add(new song(0, "3", null, "authLink", "a3", 0, 0.42f, 0.3f, 80, "a"));
        songList.add(new song(0, "4", null, "authLink", "a4", 0, 0.21f, 0.3f, 80, "a"));
        songList.add(new song(0, "5", null, "authLink", "a5", 0, 0.32f, 0.3f, 80, "a"));
        songList.add(new song(0, "6", null, "authLink", "b1", 0, 0.67f, 0.3f, 80, "b"));
        songList.add(new song(0, "7", null, "authLink", "b2", 0, 0.89f, 0.3f, 80, "b"));
        songList.add(new song(0, "8", null, "authLink", "b3", 0, 0.75f, 0.3f, 80, "b"));
        songList.add(new song(0, "9", null, "authLink", "b4", 0, 0.45f, 0.3f, 80, "b"));
        songList.add(new song(0, "10", null, "authLink", "b5", 0, 0.34f, 0.3f, 80, "b"));
        songList.add(new song(0, "11", null, "authLink", "c1", 0, 0.42f, 0.3f, 80, "c"));
        songList.add(new song(0, "12", null, "authLink", "c2", 0, 0.45f, 0.3f, 80, "c"));
        songList.add(new song(0, "13", null, "authLink", "c3", 0, 0.43f, 0.3f, 80, "c"));
        songList.add(new song(0, "14", null, "authLink", "c4", 0, 0.90f, 0.3f, 80, "c"));
        songList.add(new song(0, "15", null, "authLink", "c5", 0, 0.95f, 0.3f, 80, "c"));




        
        /*ArrayList<String> userGenres = new ArrayList<>();
        userGenres.add("a");
        userGenres.add("b");

        Genremix newGenreMix = new Genremix(userGenres, "31", 5, true, songList);

        for (song song : newGenreMix.getSongsList()) {
            System.out.println(song.getTrackName());
        }/* */
        
        
    }
    
}
