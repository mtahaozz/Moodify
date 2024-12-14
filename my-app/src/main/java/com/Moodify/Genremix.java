package com.Moodify;

import java.util.ArrayList;
import java.util.Random;

public class Genremix extends Playlist{

    protected Boolean ifPublic;
    protected ArrayList<song> songsArr;
    protected ArrayList<song> selectedSongs;

    
    
    public Genremix(ArrayList<String> genreList, String playlistName, int playlistSize, Boolean ifPublic, ArrayList<song> songsArr) {
        
        super();
        this.playlistName = playlistName;
        this.ifPublic = ifPublic;
        this.songSize = playlistSize;
        this.songsArr = songsArr;
        selectedSongs = new ArrayList<song>();

        int counter = 0;

        for(int i = 0; i < genreList.size(); i++) {
            int eachSize = playlistSize / genreList.size();
            fillPlaylist(eachSize, genreList.get(i));
            counter++;
        }

        int songGap = playlistSize - counter;
        Random random = new Random();

        while(songGap != 0) {
            int randomGenreIndex = random.nextInt(genreList.size());
            fillPlaylist(1, genreList.get(randomGenreIndex));
            songGap--;
        }

        this.setSongSize(playlistSize);


    }

    private void fillPlaylist(int genreSize, String genre) {

        for(int i = 0; i < songsArr.size(); i++) {

            if(songsArr.get(i).getSongGenre().equals(genre)) {
                selectedSongs.add(songsArr.get(i));
            }
        }

        Random random = new Random();
        
        for(int i = 0; i < this.songSize; i++) {
            int randomIndex = random.nextInt(selectedSongs.size());
            song songWillAdded = selectedSongs.get(randomIndex);
           

                if(this.isThisSongExists(songWillAdded)) {
                    i--;
                }
                else {
                    this.addSong(songWillAdded); 
            }

        }

    }

    public boolean isThisSongExists(song song) {
        for(song currentSong : this.songs) {
            if(currentSong.equals(song)) {
                return false;
            }
            
        }
        return true;
    }
    
}
