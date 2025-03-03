package com.example.project;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Player player1 = new Player();
        Player player2 = new Player();

        player1.addCard(new Card("8", "♠"));
        player1.addCard(new Card("8", "♠"));
        
        player2.addCard(new Card("9", "♠"));
        player2.addCard(new Card("9", "♣"));
        
        ArrayList<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card("8", "♦"));
        communityCards.add(new Card("8", "♠"));
        communityCards.add(new Card("9", "♣"));
        
        String p1Result = player1.playHand(communityCards);
        String p2Result = player2.playHand(communityCards);
        
        String winner = Game.determineWinner(player1, player2, p1Result, p2Result, communityCards);
        System.out.println(winner);
    }
}
