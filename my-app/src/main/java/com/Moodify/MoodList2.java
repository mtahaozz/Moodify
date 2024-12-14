package com.Moodify;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MoodList2 extends Playlist{

    public List<song> allSongs;
    
    Random random = new Random();

    public MoodList2(List<song> allSongs){

        this.allSongs = allSongs;

    }

    public Playlist createMoodPlaylist(String playlistName, double tempoValue, double energyValue, double danceabilityValue, int maxSize ) {

        List<song> filteredSongs = new ArrayList<>();

        // Filter songs based on the mood parameters
        for (song s : allSongs) {
            
            if(!(s.getTempo() <= 50) && !(s.getEnergy() <= 0) && !(s.getDancebility() <= 0))
                if(s.getTempo() >= tempoValue - 20 && s.getTempo() <= tempoValue + 20 &&
                    s.getEnergy() >= energyValue - 0.1 && s.getEnergy() <= energyValue + 0.1 &&
                    s.getDancebility() >= danceabilityValue - 0.1 && s.getDancebility() <= danceabilityValue + 0.1){

                    filteredSongs.add(s);
                }
        }
        Collections.shuffle(filteredSongs);

        // Limit the playlist size
        if (filteredSongs.size() > maxSize) {
            filteredSongs = filteredSongs.subList(0, maxSize);
        }

        // Create and return a new Playlist with the filtered songs

        Playlist moodPlaylist = new Playlist();
        moodPlaylist.setPlaylistName(playlistName);



        for (song s : filteredSongs) {

            moodPlaylist.addSong(s);

        }

        return moodPlaylist;
    }

    // Getter for all songs
    public List<song> getAllSongs() {
        return allSongs;
    }

    // Setter for all songs
    public void setAllSongs(List<song> allSongs) {
        this.allSongs = allSongs;
    }
    
}

