package com.Moodify;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MusicRead {
    public static void main(String[] args) {
        // Dosyanın tam yolu
        String filePath = "C:\\Users\\gurka\\OneDrive\\Desktop\\Projeson\\Moodify\\my-app\\src\\main\\java\\com\\Moodify\\music.csv"; // Dosya yolu burada belirtiyoruz

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Başlık satırını atla
            br.readLine();
            int count = 0;
            // Satırları oku ve her satırdaki verileri işleyelim
            while ((line = br.readLine()) != null && count < 5 ) {
                // Her satırı virgülle ayıralım
               System.out.println(line);
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
}