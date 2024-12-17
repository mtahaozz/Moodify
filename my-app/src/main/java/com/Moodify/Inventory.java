package com.Moodify;

import java.util.ArrayList;

public class Inventory {
    public static String accessToken;
    public static ArrayList<song> allSongs = new ArrayList<>();
    public static ArrayList<Playlist> allPlaylists = new ArrayList<>();
    public static ArrayList<String> genreMixNames = new ArrayList<>();
    public static ArrayList<String> MoodListNames = new ArrayList<>();

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

        for(int i = 0 ; i < 5 ; i ++){
            song popular = findMostPopular(allSongs, popularSongs);
            popularSongs.add(popular);
        }
        
        return popularSongs;
               

    }


    public static ArrayList <song> popularTurkishSongs(){

        ArrayList <song> allTurkish = new ArrayList<>();

        for(song s : allSongs){

            if(s.getGenre().equals("turkish")){
                allTurkish.add(s);
            }
        }

        ArrayList <song> fiveTurkishPopular = new ArrayList<>();
         
        for(int i = 0 ; i < 5 ; i++){
            song popularTurkish = findMostPopular(allTurkish, fiveTurkishPopular);
            fiveTurkishPopular.add(popularTurkish);
        }

        return fiveTurkishPopular;

    }

    public static song findMostPopular(ArrayList <song> allSong , ArrayList <song> allreadyFilledTrends ){
            
        song popular = allSong.get(0);
        for(int i = 0 ; i < allSong.size() ; i++){
            if(allSong.get(i).getPopularity() > popular.getPopularity() && !alreadyAdded(allreadyFilledTrends, allSong.get(i))){
                popular = allSong.get(i);
            }
        }    
        
        return popular;
        }


    public static boolean alreadyAdded (ArrayList<song> list , song s ){

        for(int a = 0 ; a < list.size() ; a++){

            if(list.get(a).getTrackName().equals(s.getTrackName())){
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
    
    public static void getUsersPlaylists(){
        allPlaylists = new ArrayList<>();
        ArrayList<String> playlistIDs= SpotifyAuthHandler.getUserPlaylistIds(accessToken);
        for (String idString : playlistIDs) {
            Playlist playlist = new Playlist();
            playlist.trackIDtoSong(SpotifyAuthHandler.getTracksFromPlaylist(accessToken, idString));
            
            ArrayList<String> details = SpotifyAuthHandler.getPlaylistDetails(accessToken, idString);

            if(genreMixNames.contains(details.get(0))){
                playlist.setType("Moodlist");
            }else if(MoodListNames.contains(details.get(0))){
                playlist.setType("Genremix");
            }else{
                playlist.setType("default");
            }

            playlist.setPlaylistName(details.get(0));
            playlist.setSongSize(Integer.parseInt(details.get(1)));
            playlist.setOwner(details.get(2));
            allPlaylists.add(playlist);
        }
    }

    public static String[] displayPlaylists(){

        String[] strings = new String[allPlaylists.size()];

        for (int i = 0; i < strings.length; i++) {
            strings[i] = allPlaylists.get(i).toString();
        }
        return strings;
    }
}

