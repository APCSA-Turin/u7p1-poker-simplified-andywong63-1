package com.example.project.webui;

import java.util.ArrayList;

import com.example.project.*;

public class FullGame {
    private int gameID;
    private Deck deck;
    private Player player;
    private Player bot;
    private ArrayList<Card> community;

    public FullGame(Deck deck, Player player, Player bot) {
        this.deck = deck;
        this.player = player;
        this.bot = bot;
        community = new ArrayList<>();
        gameID = deck.getDeckID();
    }
    public FullGame() {
        this.deck = new Deck();
        this.player = new Player();
        this.bot = new Player();
        community = new ArrayList<>();
        gameID = deck.getDeckID();
    }

    public int getGameID() {
        return gameID;
    }
    public Deck getDeck() {
        return deck;
    }
    public Player getPlayer() {
        return player;
    }
    public Player getBot() {
        return bot;
    }
    public ArrayList<Card> getCommunity() {
        return community;
    }

    public void dealCards() {
        player.addCard(deck.drawCard());
        player.addCard(deck.drawCard());
        bot.addCard(deck.drawCard());
        bot.addCard(deck.drawCard());
        community.add(deck.drawCard());
        community.add(deck.drawCard());
        community.add(deck.drawCard());
    }
}
