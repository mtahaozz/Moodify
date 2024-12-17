package com.Moodify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

public class Playlist {

    //variables
    protected int totalTime;
    protected boolean isGenre;
    protected String playlistName;
    protected ImageIcon pictureOfPlaylist;
    protected LocalDateTime date;
    protected int like;
    protected int songSize;
    protected int mainMood;
    protected String mainGenre;
    protected profile owner;
    protected ArrayList<song> songs;

    public Playlist() {
           
        this.songSize = 0;
        songs = new ArrayList<>();

    }

    public song getSongByIndex(int index){
        return songs.get(index);
    }

    public ArrayList<String> getTrackIds(){

        ArrayList <String> trackids = new ArrayList<>();

        for(int i = 0 ; i < songs.size() ; i++){

            trackids.add(songs.get(i).getTrackId());
            
        }

        return trackids;

    }


    // Finds the most common mood between all songs in the playlist.
    public String findMainMood(){

        Random random = new Random();
        int max = 0;
        ArrayList<String> mostFrequentMoods = new ArrayList<>();

        for (int i = 0; i < songs.size(); i++) {

            String currentMood = songs.get(i).getMood();
            int count = 0;
            for(int j = 0; j < songs.size(); j++) {
                
                if (currentMood == (songs.get(j).getMood())) {
                    count++;
                }
            }
            if (count > max) {
                max = count;
                mostFrequentMoods.clear();
                mostFrequentMoods.add(currentMood);
            }
            else if (count == max && !mostFrequentMoods.contains(currentMood)) {
                mostFrequentMoods.add(currentMood);
            }
        }

        if (mostFrequentMoods.size() > 1) {
            int select = random.nextInt(mostFrequentMoods.size());
            return mostFrequentMoods.get(select);
        }
        else{
            return mostFrequentMoods.get(0);
        }
    }

    // Finds the most common genre between all songs in the playlist.
    public String findMainGenre(){

        Random random = new Random();
        int max = 0;
        ArrayList<String> mostFrequentGenres = new ArrayList<>();

        for (int i = 0; i < songs.size(); i++) {

            String currentGenre = songs.get(i).getGenre();
            int count = 0;
            for(int j = 0; j < songs.size(); j++) {
                
                if (currentGenre.equals(songs.get(j).getGenre())) {
                    count++;
                }
            }
            if (count > max) {
                max = count;
                mostFrequentGenres.clear();
                mostFrequentGenres.add(currentGenre);
            }
            else if (count == max && !mostFrequentGenres.contains(currentGenre)) {
                mostFrequentGenres.add(currentGenre);
            }
        }

        if (mostFrequentGenres.size() > 1) {
            int select = random.nextInt(mostFrequentGenres.size());
            return mostFrequentGenres.get(select);
        }
        else{
            return mostFrequentGenres.get(0);
        }
    }

    public void addSongByTrackID(String trackID){
        ArrayList<song> allSongs = Inventory.allSongs;
        for (song song : allSongs) {
            if(trackID.equals(song.getTrackId())){
                this.songs.add(song);
                break;
            }
        }
        this.songSize++;
    }

    public void removeSong(int index){
        songs.remove(index);
        this.songSize--;
    }

    //getters and setters
    public int getTotalTime() {
        return totalTime;
    }
    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
    public boolean isGenre() {
        return isGenre;
    }
    public void setGenre(boolean isGenre) {
        this.isGenre = isGenre;
    }
    public String getPlaylistName() {
        return playlistName;
    }
    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }
    public ImageIcon getPictureOfPlaylist() {
        return pictureOfPlaylist;
    }
    public void setPictureOfPlaylist(ImageIcon pictureOfPlaylist) {
        this.pictureOfPlaylist = pictureOfPlaylist;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public int getLike() {
        return like;
    }
    public void setLike(int like) {
        this.like = like;
    }
    public int getSongSize() {
        return songSize;
    }
    public void setSongSize(int songSize) {
        this.songSize = songSize;
    }
    public int getMainMood() {
        return mainMood;
    }
    public void setMainMood(int mainMood) {
        this.mainMood = mainMood;
    }
    public String getMainGenre() {
        return mainGenre;
    }
    public void setMainGenre(String mainGenre) {
        this.mainGenre = mainGenre;
    }
    public profile getOwner() {
        return owner;
    }
    public void setOwner(profile owner) {
        this.owner = owner;
    }

    public ArrayList<song> getSongsList() {
        return songs;
    }

    public boolean isThisSongExists(song song) {
        
        for(song currentSong : this.songs) {
            if(currentSong.getTrackId().equals(song.getTrackId())) {
                return true;
            }
            
        }
        return false; 
    }
    public String[] displayPlaylist(){

        String[] songsArray = new String[songs.size()];
        for (int i = 0; i < songsArray.length; i++) {
            
            String space;
            if (i + 1 < 10) {
                space = (i+1) + "         ";
            }
            else{
                space = (i+1) + "        ";
            }
            songsArray[i] = space + songs.get(i).toString();
        }
        return songsArray;
    }

}
