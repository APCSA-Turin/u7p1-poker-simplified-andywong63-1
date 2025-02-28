package com.example.project;
import java.util.ArrayList;


public class Game{
    public static String determineWinner(Player p1, Player p2, String p1Hand, String p2Hand, ArrayList<Card> communityCards) {
        int p1Rank = Utility.getHandRanking(p1Hand);
        int p2Rank = Utility.getHandRanking(p2Hand);
        if (p1Rank > p2Rank) {
            return "Player 1 wins!";
        } else if (p1Rank < p2Rank) {
            return "Player 2 wins!";
        }
        
        // Tiebreaker
        if (p1Rank <= 2) {
            // Highest Card
            return breakHighCardTie(p1, p2);
        }

        if (p1Rank == 3) {
            // One Pair
            int p1Pair = p1.findPairs().get(0);
            int p2Pair = p2.findPairs().get(0);
            if (p1Pair > p2Pair) {
                return "Player 1 wins!";
            } else if (p1Pair < p2Pair) {
                return "Player 2 wins!";
            } else {
                return breakHighCardTie(p1, p2);
            }
        }

        if (p1Rank == 4) {
            // Two Pair
            ArrayList<Integer> p1Pairs = p1.findPairs();
            int p1Larger;
            ArrayList<Integer> p2Pairs = p2.findPairs();
            int p2Larger;
            if (p1Pairs.get(0) > p1Pairs.get(1)) {
                p1Larger = p1Pairs.get(0);
            } else {
                p1Larger = p1Pairs.get(1);
            }
            if (p2Pairs.get(0) > p2Pairs.get(1)) {
                p2Larger = p2Pairs.get(0);
            } else {
                p2Larger = p1Pairs.get(1);
            }
        }

        return "Error";
    }

    public static void play(){ //simulate card playing
    
    }


    // Tiebreakers
    public static String breakHighCardTie(Player p1, Player p2) {
        int p1High = p1.findHighCard();
        int p2High = p2.findHighCard();
        if (p1High > p2High) {
            return "Player 1 wins!";
        } else if (p1High < p2High) {
            return "Player 2 wins!";
        } else {
            return "Tie!";
        }
    }

}