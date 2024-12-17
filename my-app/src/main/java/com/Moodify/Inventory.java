package com.Moodify;

import java.util.ArrayList;

public class Inventory {
    public static String accessToken;
    public static ArrayList<song> allSongs;

    public static void fillAllSongs(){
        try {
            allSongs = new ArrayList<>();
            MusicRead.fillMusicList(allSongs);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //top 10 trend song
    public static ArrayList<song> findTrendSongs() {

        Inventory.fillAllSongs();

        ArrayList <song> popularSongs = new ArrayList<>();

        for(int i = 0 ; i < 10 ; i ++){
            song popular = findMostPopular(allSongs, popularSongs);
            popularSongs.add(popular);
        }
        
        return popularSongs;
               

    }

    public static song findMostPopular(ArrayList <song> allSong , ArrayList <song> allreadyFilledTrends ){
            
        song popular = allSong.get(0);
        for(int i = 0 ; i < allSong.size() ; i++){
            if(allSong.get(i).getPopularity() > popular.getPopularity() && !alreadAdded(allreadyFilledTrends, allSong.get(i))){
                popular = allSong.get(i);
            }
        }    
        
        return popular;
        }

<<<<<<< Updated upstream

    public static boolean alreadAdded (ArrayList<song> list , song s ){

        for(song a : list){
            if(a.getTrackName().equals(s.getTrackName())){
=======
    public static boolean alreadAdded (ArrayList<song> list , song s ){

        for(song a : list){
            if(a.equals(s)){
>>>>>>> Stashed changes
                return true;
            }
        }
        return false;
    }
}

