package com.Moodify;

import java.util.ArrayList;



public class artist {

    private ArrayList<String> popularSongsIDS;
    private String ARTISTNAME;
    private int monthlyListener;
    private String ARTISTID;


    public artist(String ARTISTNAME, String accessToken) throws Exception{
        this.ARTISTID = SpotifyAuthHandler.getArtistId(accessToken, ARTISTNAME);
        this.popularSongsIDS = SpotifyAuthHandler.getArtistTopTrackIds(accessToken, ARTISTID, "TR");
        this.ARTISTNAME = ARTISTNAME;
    }
    
    public ArrayList<String> getPopularSongIDS(){
        return popularSongsIDS;
    }

    public String getARTISTNAME() {
        return ARTISTNAME;
    }

    public int getMonthlyListener() {
        return monthlyListener;
    }
}
