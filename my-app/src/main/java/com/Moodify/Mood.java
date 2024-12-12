package com.Moodify;

public class Mood {
    private double tempo;
    private double energy;
    private double danceability;

    // Constructor
    public Mood(double tempo, double energy, double danceability) {
        this.tempo = tempo;
        this.energy = energy;
        this.danceability = danceability;
    }

    // Getter metodları
    public double getTempo() { return tempo; }
    public double getEnergy() { return energy; }
    public double getDanceability() { return danceability; }
}
