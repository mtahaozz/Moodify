package com.Moodify;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MusicRead {

    public static void fillMusicList(ArrayList <song> musicList) {
      
        String filePath = "C:\\Users\\gurka\\OneDrive\\Desktop\\Projeson\\Moodify\\my-app\\src\\main\\java\\com\\Moodify\\music.csv"; // Dosya yolu burada belirtiyoruz

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
          
            String line;
            br.readLine();
       
            while ((line = br.readLine()) != null ) {
              
              String[] informations = line.split(",");
              
              long number = Long.parseLong(informations[0]);
              String TrackId = informations[1];
              artist artistOfTheSong = new artist(informations[2]);
              String albumName = informations[3];
              String trackName = informations[4];
              int duration = Integer.valueOf(informations[6]);
              float dancebility = Float.valueOf(informations[8]);
              float energy = Float.valueOf(informations[9]);
              float tempo = Float.valueOf(informations[18]);
              String genre = informations[20];

              song newSong = new song(number, TrackId, artistOfTheSong, albumName, trackName, duration, dancebility, energy, tempo, genre);
            
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