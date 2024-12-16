package com.Moodify;

import java.util.ArrayList;



public class artist {

    private ArrayList<String> popularSongsIDS;
    private String ARTISTNAME;
    private int monthlyListener;
    private String ARTISTID;
    String accessToken = Inventory.accessToken;

    public artist(String ARTISTNAME) throws Exception{
        this.ARTISTNAME = ARTISTNAME;
    }
    
    public ArrayList<String> getPopularSongIDS(){
        return popularSongsIDS;
    }

    public void fillArtistIdandSongs() throws Exception{
        ARTISTID = SpotifyAuthHandler.getArtistId(accessToken, ARTISTNAME);
        popularSongsIDS = SpotifyAuthHandler.getArtistTopTrackIds(accessToken, ARTISTID, "TR");
    }

    public String getARTISTNAME() {
        return ARTISTNAME;
    }

    public int getMonthlyListener() {
        return monthlyListener;
    }
}
