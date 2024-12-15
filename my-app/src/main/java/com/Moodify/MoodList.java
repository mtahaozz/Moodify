package com.Moodify;
import java.util.ArrayList;
import java.util.Random;

public class MoodList extends Playlist{

    protected float dancebility;
    protected float tempo;
    protected float energy;

    MoodList(float d , float t , float e, ArrayList <song> songData , int size){
        super();
        Random random = new Random();

        //borders
        float upperD = d + (float)0.05;
        if(upperD > 1){upperD = 1;}    
        float lowerD = d - (float)0.05;
        if(lowerD < 0){lowerD = 0;}

        float upperT = t + (float)10.000;
        if(upperD > 1){upperT = 1;}    
        float lowerT = t - (float)10.000;
        if(lowerD < 0){lowerT = 0;}

        float upperE = d + (float)0.05;
        if(upperD > 1){upperE = 1;}    
        float lowerE = d - (float)0.05;
        if(lowerD < 0){lowerE = 0;}

        //ArrayListContainingSuitableSongs
        ArrayList <song> suitable = new ArrayList<>();

            for(int a = 0  ; a < songData.size() ; a++){

                song current = songData.get(a);
                float dancebilityCurrent = current.getDancebility();
                float tempoCurrent = current.getTempo();
                float energyCurrent = current.getEnergy();
                if((dancebilityCurrent >= lowerD && dancebilityCurrent <= upperD) && (tempoCurrent >= lowerT && tempoCurrent <= upperT) && (energyCurrent >= lowerE && energyCurrent <= upperE) ){
                    suitable.add(current);
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
                    this.addSong(suitable.get(randomIndex));
                }
            
           }

    }
}

   


