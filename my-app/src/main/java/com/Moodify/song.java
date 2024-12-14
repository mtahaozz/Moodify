package com.Moodify;

import javax.swing.ImageIcon;

public class song {

    public String informations;
    private String songName;
    private artist songArtist;
    private int songLength;
    private int songMood;
    private String songGenre;
    private int songTempo;
    private int songListened;
    private String album;
    private ImageIcon songImage;
    // private audio songAudio;
    public song(String songName, artist songArtist, int songLength, int songMood, String songGenre) {
        this.songName = songName;
        this.songArtist = songArtist;
        this.songLength = songLength;
        this.songMood = songMood;
        this.songGenre = songGenre;
        this.songTempo = songTempo;
        this.songListened = songListened;
        this.album = album;
        this.songImage = songImage;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }
    public void setSongArtist(artist songArtist) {
        this.songArtist = songArtist;
    }
    public void setSongLength(int songLength) {
        this.songLength = songLength;
    }
    public void setSongMood(int songMood) {
        this.songMood = songMood;
    }
    public void setSongGenre(String songGenre) {
        this.songGenre = songGenre;
    }
    public void setSongTempo(int songTempo) {
        this.songTempo = songTempo;
    }
    public void setSongListened(int songListened) {
        this.songListened = songListened;
    }
    public void setAlbum(String album) {
        this.album = album;
    }
    public void setSongImage(ImageIcon songImage) {
        this.songImage = songImage;
    }

    
    
    public song(String information){

        System.out.println(information);

    }




    public String getSongName() {
        return songName;
    }
    public artist getSongArtist() {
        return songArtist;
    }
    public int getSongLength() {
        return songLength;
    }
    public int getSongMood() {
        return songMood;
    }
    public String getSongGenre() {
        return songGenre;
    }
    public int getSongTempo() {
        return songTempo;
    }
    public int getSongListened() {
        return songListened;
    }
    public String getAlbum() {
        return album;
    }
    public ImageIcon getSongImage() {
        return songImage;
    }
    
}

