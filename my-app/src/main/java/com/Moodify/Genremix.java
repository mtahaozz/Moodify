package com.Moodify;

import java.util.ArrayList;
import java.util.Random;

public class Genremix extends Playlist{

    protected Boolean ifPublic;
    protected ArrayList<song> songsArr;
    protected ArrayList<song> selectedSongs;
    protected int inputSongSize;

    public Genremix(ArrayList<String> genreList, String playlistName, int playlistSize, Boolean ifPublic, ArrayList<song> songsArr) {
        
        super();
        this.playlistName = playlistName;
        this.ifPublic = ifPublic;
        inputSongSize = playlistSize;
        this.songsArr = songsArr;
        selectedSongs = new ArrayList<song>();

        for(int i = 0; i < genreList.size(); i++) {
            int eachSize = playlistSize / genreList.size();
            fillPlaylist(eachSize, genreList.get(i));
        }

        int songGap = playlistSize - this.songSize;
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

            if(songsArr.get(i).getGenre().equals(genre)) {
                selectedSongs.add(songsArr.get(i));
            }
        }

        Random random = new Random();
        
        for(int i = 0; i < genreSize; i++) {
            int randomIndex = random.nextInt(selectedSongs.size());
            song songWillAdded = selectedSongs.get(randomIndex);
           
                if(isThisSongExists(songWillAdded)) {
                    i--;
                }
                else {

                    this.addSong(songWillAdded); 
                }    
        }
    }

    public String[] displayGenremix(){

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
    

