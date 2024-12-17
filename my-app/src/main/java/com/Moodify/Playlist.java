package com.Moodify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

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
    protected String owner;
    protected ArrayList<song> songs;
    protected String type;
    protected boolean isPublic;

    
    public Playlist() {
        
        this.songSize = 0;
        this.type = "Default";
        songs = new ArrayList<>();
       
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public void sendToSpotify(){
        SpotifyAuthHandler.createPlaylistFromTrackId(Inventory.accessToken, SpotifyAuthHandler.getUserId(Inventory.accessToken), playlistName, getTrackIds(), isPublic);
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
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
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
    public String toString(){

        String name;
        String toLength = "" + songSize;
        String toOwner;
        String toType = type;
        String space1 = "";
        String space2 = "";
        String space3 = "";

        if (playlistName.length() > 25) {
            name = playlistName.substring(0, 22) + "...";
        }
        else{
            name = playlistName;
        }
        if (owner.length() > 15) {
            toOwner = owner.substring(0, 12) + "...";
        }
        else{
            toOwner = owner;
        }

        for (int i = 0; i < 3; i++) {

            JLabel org;
            JLabel custom;

            if (i == 0) {
                
                org = new JLabel("Playlist Name                    ");
                custom = new JLabel(name + space1);
                
                if (org.getPreferredSize().getWidth()> custom.getPreferredSize().getWidth()) {
                    boolean check = true;
                    while (check) {
                        space1 += " ";
                        custom = new JLabel(name + space1);
                        if (org.getPreferredSize().getWidth()<= custom.getPreferredSize().getWidth()) {
                            check = false;
                        }
                    }
                }
                else if (org.getPreferredSize().getWidth() < custom.getPreferredSize().getWidth() && !space1.equals("")) {
                    boolean check = true;
                    while (check) {
                        space1 = space1.substring(0,space1.length()-1);
                        custom = new JLabel(name + space1);
                        if (org.getPreferredSize().getWidth()>= custom.getPreferredSize().getWidth() && !space1.equals("")) {
                            check = false;
                        }
                    }
                }
            }
            else if (i == 1) {
                    
                org = new JLabel("-1                  ");
                custom = new JLabel(toLength + space2);
                
                if (org.getPreferredSize().getWidth()> custom.getPreferredSize().getWidth()) {
                    boolean check = true;
                    while (check) {
                        space2 += " ";
                        custom = new JLabel(toLength + space2);
                        if (org.getPreferredSize().getWidth()<= custom.getPreferredSize().getWidth()) {
                            check = false;
                        }
                    }
                }
                else if (org.getPreferredSize().getWidth() < custom.getPreferredSize().getWidth() && !space2.equals("")) {
                    boolean check = true;
                    while (check) {
                        space2 = space2.substring(0,space2.length()-1);
                        custom = new JLabel(toLength + space2);
                        if (org.getPreferredSize().getWidth()>= custom.getPreferredSize().getWidth() && !space2.equals("")) {
                            check = false;
                        }
                    }
                }
            }
            else if (i == 2) {
                    
                org = new JLabel("owner              ");
                custom = new JLabel( toOwner + space3);
                
                if (org.getPreferredSize().getWidth()> custom.getPreferredSize().getWidth()) {
                    boolean check = true;
                    while (check) {
                        space3 += " ";
                        custom = new JLabel(toOwner + space3);
                        if (org.getPreferredSize().getWidth()<= custom.getPreferredSize().getWidth()) {
                            check = false;
                        }
                    }
                }
                else if (org.getPreferredSize().getWidth() < custom.getPreferredSize().getWidth() && !space3.equals("")) {
                    boolean check = true;
                    while (check) {
                        space3 = space3.substring(0,space3.length()-1);
                        custom = new JLabel(toOwner + space3);
                        if (org.getPreferredSize().getWidth() >= custom.getPreferredSize().getWidth() && !space3.equals("")) {
                            check = false;
                        }
                    }
                }
            }

        }

        return name + space1 + toLength + space2 + toOwner + space3 + toType;

    }

}
