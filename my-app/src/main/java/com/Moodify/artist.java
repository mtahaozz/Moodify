package com.Moodify;

import java.util.ArrayList;


public class artist {

    private ArrayList<song> popularSongs;
    private String ARTISTNAME;
    private int monthlyListener;
    private String ARTISTID;
    String accessToken = Inventory.accessToken;

    public artist(String ARTISTNAME){
        this.ARTISTNAME = ARTISTNAME;
    }
    
    public ArrayList<song> getPopularSongs(){
        return popularSongs;
    }

    public void fillArtistIdandSongs() throws Exception{
        ARTISTID = SpotifyAuthHandler.getArtistId(accessToken, ARTISTNAME);
    }

    public String getARTISTNAME() {
        return ARTISTNAME;
    }

    public int getMonthlyListener() {
        return monthlyListener;
    }

}
