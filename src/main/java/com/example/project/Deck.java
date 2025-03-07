package com.example.project;
import java.util.ArrayList;
import java.util.Collections;

public class Deck{
    private ArrayList<Card> cards;
    private int deckID;
    private static int nextDeckID = 0;

    public Deck(int deckID) {
        this.deckID = deckID;
        nextDeckID = deckID + 1;
        cards = new ArrayList<>();
        initializeDeck();
        shuffleDeck();
    }

    public Deck() {
        this(nextDeckID);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void initializeDeck() { //hint.. use the utility class
        cards.clear();
        for (String suit : Utility.getSuits()) {
            for (String rank : Utility.getRanks()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public void shuffleDeck() { //You can use the Collections library or another method. You do not have to create your own shuffle algorithm
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (!isEmpty()) {
            return cards.remove(0);
        } else {
            return null;
        }
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

   
    public int getDeckID() {
        return deckID;
    }

    public void setDeckID(int deckID) {
        this.deckID = deckID;
    }

}