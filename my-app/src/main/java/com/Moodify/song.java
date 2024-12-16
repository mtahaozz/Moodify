package com.Moodify;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

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

    public String toString(){

        String name;
        String toGenre;
        String toAlbum;
        String toArtist;

        String space1 = "";
        String space2 = "";
        String space3 = "";

        
        if (trackName.length()>22) {
            name = trackName.substring(0, 19) + "...";
        }
        else{
            name = trackName;
        }
        if (genre.length() > 12) {
            toGenre = genre.substring(0, 9) + "...";
        }
        else{
            toGenre = genre;
        }
        if (albumName.length() > 17) {
            toAlbum = albumName.substring(0, 14) + "...";
        }
        else{
            toAlbum = albumName;
        }
        if (songArtist.getARTISTNAME().length()> 15) {
            toArtist = songArtist.getARTISTNAME().substring(0,12) + "...";
        }
        else{
            toArtist = songArtist.getARTISTNAME();
        }
        
        for (int i = 0; i < 3; i++) {

            JLabel org;
            JLabel custom;

            if (i == 0) {
                
                org = new JLabel("songName                          ");
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
                    
                org = new JLabel("mood         ");
                custom = new JLabel(toGenre + space2);
                
                if (org.getPreferredSize().getWidth()> custom.getPreferredSize().getWidth()) {
                    boolean check = true;
                    while (check) {
                        space2 += " ";
                        custom = new JLabel(toGenre + space2);
                        if (org.getPreferredSize().getWidth()<= custom.getPreferredSize().getWidth()) {
                            check = false;
                        }
                    }
                }
                else if (org.getPreferredSize().getWidth() < custom.getPreferredSize().getWidth() && !space2.equals("")) {
                    boolean check = true;
                    while (check) {
                        space2 = space2.substring(0,space2.length()-1);
                        custom = new JLabel(toGenre + space2);
                        if (org.getPreferredSize().getWidth()>= custom.getPreferredSize().getWidth() && !space2.equals("")) {
                            check = false;
                        }
                    }
                }
            }
            else if (i == 2) {
                    
                org = new JLabel("xListened             ");
                custom = new JLabel( toArtist + space3);
                
                if (org.getPreferredSize().getWidth()> custom.getPreferredSize().getWidth()) {
                    boolean check = true;
                    while (check) {
                        space3 += " ";
                        custom = new JLabel(toArtist + space3);
                        if (org.getPreferredSize().getWidth()<= custom.getPreferredSize().getWidth()) {
                            check = false;
                        }
                    }
                }
                else if (org.getPreferredSize().getWidth() < custom.getPreferredSize().getWidth() && !space3.equals("")) {
                    boolean check = true;
                    while (check) {
                        space3 = space3.substring(0,space3.length()-1);
                        custom = new JLabel(toArtist + space3);
                        if (org.getPreferredSize().getWidth() >= custom.getPreferredSize().getWidth() && !space3.equals("")) {
                            check = false;
                        }
                    }
                }
            }

        }
    
        return name + space1 + toArtist + space3 + toGenre + space2 + toAlbum;

    }
    
}

