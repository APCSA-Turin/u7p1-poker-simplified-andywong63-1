package com.example.project;
import java.util.ArrayList;
import java.util.Arrays;

public class Card{
    private String rank;
    private String suit;

    private int rankIndex;
    private int suitIndex;

    public Card(String rank, String suit){
        this.rank = rank;
        this.suit = suit;

        this.rankIndex = Arrays.asList(Utility.getRanks()).indexOf(rank);
        this.suitIndex = Arrays.asList(Utility.getSuits()).indexOf(suit);
    }

    public String getRank(){return rank;}
    public String getSuit(){return suit;}
    
    @Override
    public String toString(){
        return rank + " of " + suit;
    }


    public int getRankIndex() {
        return rankIndex;
    }
    public int getSuitIndex() {
        return suitIndex;
    }
}