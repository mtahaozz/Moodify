package com.Moodify;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MusicRead {

    public static void main(String[] args) {
      ArrayList <song> allSongs = new ArrayList<>();
      try {
        fillMusicList(allSongs);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      for(int i = 0 ; i < allSongs.size() ; ){
        System.out.println(allSongs.get(i).getGenre());
        i += 500;
      }
    }

    public static void fillMusicList(ArrayList <song> musicList) throws Exception {
      
      String filePath ="/Users/apple/Desktop/Moodify/my-app/src/main/java/com/Moodify/music.csv";
      //String filePath ="/Users/baristerbillioglu/Desktop/Moodify/my-app/src/main/java/com/Moodify/music.csv";
        //String filePath = "C:\\Users\\gurka\\OneDrive\\Desktop\\Projeson\\Moodify\\my-app\\src\\main\\java\\com\\Moodify\\music.csv"; // Dosya yolu burada belirtiyoruz

        //String filePath ="C:\\Users\\BILAL\\Desktop\\yeniProje\\Moodify\\my-app\\src\\main\\java\\com\\Moodify";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
          
            String line;
            br.readLine();
       
            while ((line = br.readLine()) != null ) {
              
              //System.out.println(line);
              
              String[] informations = line.split(",");
               long number = Long.parseLong(informations[0]);
               String TrackId = informations[1];
               artist artistOfTheSong = new artist(informations[2]);
               String albumName = informations[3];
               String trackName = informations[4];
            
              int popularity;
               int duration;
               float dancebility;
               float energy;
               float tempo;
               String genre;

               if(informations.length > 21){
                 popularity = Integer.parseInt(informations[5 + informations.length - 21]);
                 duration = Integer.valueOf(informations[6 + (informations.length - 21)]);
                 dancebility = Float.valueOf(informations[8+ (informations.length - 21)]);
                 energy = Float.valueOf(informations[9+ (informations.length - 21)]);
                 tempo = Float.valueOf(informations[18 + (informations.length - 21)]);
                 genre = informations[20 + (informations.length - 21)];
               }
               else{
                popularity = Integer.parseInt(informations[5]);
                duration = Integer.valueOf(informations[6]);
                dancebility = Float.valueOf(informations[8]);
                energy = Float.valueOf(informations[9]);
                tempo = Float.valueOf(informations[18]);
                genre = informations[20];}

              song newSong = new song(number, TrackId, artistOfTheSong, albumName, trackName, duration, dancebility, energy, tempo, genre,popularity);
            
              musicList.add(newSong);

               
            }
        } catch (IOException e) {
            
            // Dosya okuma hatası durumunda mesaj verelim
            System.err.println("Dosya okunurken bir hata oluştu: " + e.getMessage());
        } catch (NumberFormatException e) {

            // Sayısal dönüşüm hatası durumunda mesaj verelim
            System.err.println("Sayısal dönüşüm hatası: " + e.getMessage());
        }
    }

}