package com.Moodify;

import javax.swing.ImageIcon;

public class song {

    //advanced variables => from DATA
    protected long number;
    protected String trackId;
    protected artist songArtist;
    protected String albumName;
    protected String trackName;
    protected int duration;
    protected float dancebility;
    protected float energy;
    protected float tempo;
    protected String genre;

    public song(long number, String trackId, artist songArtist, String albumName, String trackName, int duration,
            float dancebility, float energy, float tempo, String genre) {
        this.number = number;
        this.trackId = trackId;
        this.songArtist = songArtist;
        this.albumName = albumName;
        this.trackName = trackName;
        this.duration = duration;
        this.dancebility = dancebility;
        this.energy = energy;
        this.tempo = tempo;
        this.genre = genre;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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

    public float getTempo() {
        return tempo;
    }

    public void setTempo(float tempo) {
        this.tempo = tempo;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

  
    
}

