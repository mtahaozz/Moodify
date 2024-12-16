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

    //top 10 trend song
    public static ArrayList<song> findArtistTrendSongs() {

                ArrayList<song> tenTrendSongs = new ArrayList<song>();
                int size = allSongs.size();
                while(size != 0) {
                    size--;
                    for(int i = 0; i < allSongs.size(); i++) {
        
                        if(allSongs.get(i).getPopularity() > allSongs.get(i + 1).getPopularity()) {
                            song clone = allSongs.get(i + 1);
                            allSongs.set(i + 1, allSongs.get(i));
                            allSongs.set(i, clone);    
                        }
                    }
                }
                //size-10
                for(int i = allSongs.size(); i > allSongs.size() - 11; i--) {
                    tenTrendSongs.add(allSongs.get(i));
                }
        
                return tenTrendSongs;
}
