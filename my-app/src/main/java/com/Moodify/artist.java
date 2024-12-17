package com.Moodify;

import java.util.ArrayList;


public class artist {

    private ArrayList<song> popularSongs;
    private String ARTISTNAME;
    private int monthlyListener;
    private String ARTISTID;
    String accessToken = Inventory.accessToken;

    public artist(String ARTISTNAME) throws Exception{
        this.ARTISTNAME = ARTISTNAME;
    }
    
    public ArrayList<song> getPopularSongs(){
        return popularSongs;
    }

    public void fillArtistIdandSongs() throws Exception{
        ARTISTID = SpotifyAuthHandler.getArtistId(accessToken, ARTISTNAME);
    }

    public void fillPopularSongs(){
        ArrayList<song> allSongs = Inventory.allSongs;
        ArrayList<song> tenTrendSongs = new ArrayList<song>();
        ArrayList<song> artistSongs = new ArrayList<>();

        for (song song : allSongs) {
            if(song.getSongArtist().getARTISTNAME().equals(this.ARTISTNAME)){
                artistSongs.add(song);
            }
        }

        int size = artistSongs.size();
        
        while(size != 0) {
            size--;
            for(int i = 0; i < artistSongs.size(); i++) {

                if(artistSongs.get(i).getPopularity() > artistSongs.get(i + 1).getPopularity()) {
                    song clone = artistSongs.get(i + 1);
                    artistSongs.set(i + 1, artistSongs.get(i));
                    artistSongs.set(i, clone);    
                }
            }
        }
        //size-10
        for(int i = artistSongs.size(); i > artistSongs.size() - 11; i--) {
            tenTrendSongs.add(artistSongs.get(i));
        }

        popularSongs = tenTrendSongs;
    }

    public String getARTISTNAME() {
        return ARTISTNAME;
    }

    public int getMonthlyListener() {
        return monthlyListener;
    }
}
