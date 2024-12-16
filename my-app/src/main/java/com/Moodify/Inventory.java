package com.Moodify;

import java.util.ArrayList;

public class Inventory {
    public static String accessToken;
    public static ArrayList<song> allSongs;

    public static void fillAllSongs(){
        try {
            allSongs = new ArrayList<>();
            MusicRead.fillMusicList(allSongs);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static ArrayList<song> findArtistTrendSongs(String ArtistID){
        ArrayList<song> artisttrends = new ArrayList();
        for (song song : allSongs) {
            if(ArtistID.equals(song.getSongArtist().get)){
                artisttrends.add(song);
                break;
            }
        }
        return artisttrends;
    }
}
