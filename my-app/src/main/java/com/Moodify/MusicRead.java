package com.Moodify;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MusicRead {

    public static void fillMusicList(ArrayList <song> musicList) {
        // Dosyanın tam yolu
        String filePath = "C:\\Users\\gurka\\OneDrive\\Desktop\\Projeson\\Moodify\\my-app\\src\\main\\java\\com\\Moodify\\music.csv"; // Dosya yolu burada belirtiyoruz

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
          
            String line;
            br.readLine();
            int count = 0;
       
            while ((line = br.readLine()) != null && count < 1 ) {
            
              song createSong = new song();  
              String[] informations = line.split(",");
              
              count++;

               
            }
        } catch (IOException e) {
            // Dosya okuma hatası durumunda mesaj verelim
            System.err.println("Dosya okunurken bir hata oluştu: " + e.getMessage());
        } catch (NumberFormatException e) {
            // Sayısal dönüşüm hatası durumunda mesaj verelim
            System.err.println("Sayısal dönüşüm hatası: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ArrayList <song> musicList = new ArrayList<>();
        fillMusicList(musicList);
    }
}