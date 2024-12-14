package com.Moodify;

import javax.swing.ImageIcon;

public class song {

    //advanced variables => from DATA
    protected long number;
    protected String trackId;
    protected artist songArtist;
    protected String albumName;
    protected String trackName;
    protected int popularityScore;
    protected int duration;
    protected boolean explicit;
    protected float dancebility;
    protected float energy;

    //


    public song(long number,String trackId,artist songArtist ,String albumName,String trackName,int popularityScore,int duration,boolean explicit,float dancebility,float energy){
       
        this.number = number;
        this.trackId = trackId;
        this.songArtist = songArtist;
        this.albumName = albumName;
        this.trackName = trackName;
        this.popularityScore = popularityScore;
        this.duration = duration;
        this.explicit = explicit;
        this.dancebility = dancebility;
        this.energy = energy;
    }


    public long getNumber() {
        return number;
    }
    public void setNumber(long number) {
        this.number = number;
    }
    public String getTrackId() {
        return trackId;
    }
    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }
    public artist getSongArtist() {
        return songArtist;
    }
    public void setSongArtist(artist songArtist) {
        this.songArtist = songArtist;
    }
    public String getAlbumName() {
        return albumName;
    }
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
    public String getTrackName() {
        return trackName;
    }
    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }
    public int getPopularityScore() {
        return popularityScore;
    }
    public void setPopularityScore(int popularityScore) {
        this.popularityScore = popularityScore;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public boolean isExplicit() {
        return explicit;
    }
    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
    }
    public float getDancebility() {
        return dancebility;
    }
    public void setDancebility(float dancebility) {
        this.dancebility = dancebility;
    }
    public float getEnergy() {
        return energy;
    }
    public void setEnergy(float energy) {
        this.energy = energy;
    }

    
}

