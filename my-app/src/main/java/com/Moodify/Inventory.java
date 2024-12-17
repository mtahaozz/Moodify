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


    public static boolean alreadAdded (ArrayList<song> list , song s ){

        for(song a : list){
            if(a.getTrackName().equals(s.getTrackName())){
                return true;
            }
        }
        return false;
    }

    public static ArrayList<song> fillPopularSongs(artist artist){
        ArrayList<song> allSongs = Inventory.allSongs;
        ArrayList<song> tenTrendSongs = new ArrayList<>();
        ArrayList<song> artistSongs = new ArrayList<>();
        
        // Sanatçı şarkılarını filtreleme
        for (song song : allSongs) {
            if (song.getSongArtist().getARTISTNAME().equals(artist.getARTISTNAME())) {
                artistSongs.add(song);
            }
        }
        
        // Sanatçı şarkılarını popülerliğe göre sıralama
        artistSongs.sort((s1, s2) -> Integer.compare(s2.getPopularity(), s1.getPopularity()));
        
        // En popüler 10 şarkıyı seçme
        for (int i = 0; i < Math.min(5, artistSongs.size()); i++) {
            tenTrendSongs.add(artistSongs.get(i));
        }
        
        // Popüler şarkılar listesini güncelleme
        return tenTrendSongs;
        
    }
}

