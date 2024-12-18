package com.Moodify;
import java.util.ArrayList;
import java.util.Random;

public class MoodList extends Playlist{

    protected float dancebility;
    protected float tempo;
    protected float energy;
    protected  ArrayList<song> songData;

    public MoodList(float d , float t , float e, int size, boolean isPublic,String name){
        super();
        this.playlistName = name;
        this.type = "Moodlist";
        this.isPublic = isPublic;
        Random random = new Random();
        songData = Inventory.allSongs;

        //borders
        float upperD = d + (float)0.05;
        //if(upperD > 1){upperD = 1;}    
        float lowerD = d - (float)0.05;
        //if(lowerD < 0){lowerD = 0;}

        float upperT = t + (float)10;
        //if(upperT > 200){upperT = 200;}    
        float lowerT = t - (float)10;
        //if(lowerT < 60){lowerT = 60;}

        float upperE = e + (float)0.05;
        //if(upperE > 1){upperE = 1;}    
        float lowerE = e - (float)0.05;
        //if(lowerE < 0){lowerE = 0;}

        //ArrayListContainingSuitableSongs
        ArrayList <song> suitable = new ArrayList<>();
        boolean isFilled = true;
        while(isFilled) {

            for(int a = 0  ; a < songData.size() ; a++){

                song current = songData.get(a);
                float dancebilityCurrent = current.getDancebility();
                float tempoCurrent = current.getTempo();
                float energyCurrent = current.getEnergy();
                if((dancebilityCurrent >= lowerD && dancebilityCurrent <= upperD) && (tempoCurrent >= lowerT && tempoCurrent <= upperT) && (energyCurrent >= lowerE && energyCurrent <= upperE) ){
                    suitable.add(current);
                }
            }
            //tempo51 energy130/100f dancibility44/100f
            if(suitable.size() < 50) {
                
                    upperD -= 0.1;
                    lowerD -= 0.1;
                    upperE -= 0.1;
                    upperT -= 0.1;
                    lowerE -= 0.1;
                    lowerT -= 0.1;
            }
            else{
                isFilled = false;
            }
        }

           int suitableSize = suitable.size();
           
           for(int i = 0 ; i < size ; i ++ ){

                int randomIndex = random.nextInt(suitableSize);
                song willAdded = suitable.get(randomIndex);
                
                if(isThisSongExists(willAdded)) {
                    i--;

                }
                else {
                    this.addSongByTrackID(suitable.get(randomIndex).getTrackId());
                }
            
           }

        Inventory.MoodListNames.add(playlistName);
        sendToSpotify();
    }
}

   


