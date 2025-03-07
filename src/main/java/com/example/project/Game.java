package com.example.project;
import java.util.ArrayList;


public class Game {
    public static String determineWinner(Player p1, Player p2, String p1Hand, String p2Hand, ArrayList<Card> communityCards) {
        int p1Rank = Utility.getHandRanking(p1Hand);
        int p2Rank = Utility.getHandRanking(p2Hand);
        if (p1Rank > p2Rank) {
            return "Player 1 wins!";
        } else if (p1Rank < p2Rank) {
            return "Player 2 wins!";
        }
        
        // Tiebreakers (p1Rank == p2Rank)
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
            ArrayList<Integer> p2Pairs = p2.findPairs();
            int p1Larger = p1Pairs.get(p1Pairs.size() - 1); // findPairs returns in order from smallest to largest, so last one is always largest
            int p2Larger = p2Pairs.get(p2Pairs.size() - 1);
            if (p1Larger > p2Larger) {
                return "Player 1 wins!";
            } else if (p1Larger < p2Larger) {
                return "Player 2 wins!";
            } else {
                return "Tie!";
            }
        }

        if (p1Rank == 5) {
            // Three of a Kind
            ArrayList<Integer> p1Triples = p1.findTriples();
            ArrayList<Integer> p2Triples = p2.findTriples();
            int p1Triple = p1Triples.get(p1Triples.size() - 1);
            int p2Triple = p2Triples.get(p2Triples.size() - 1);
            if (p1Triple > p2Triple) {
                return "Player 1 wins!";
            } else if (p1Triple < p2Triple) {
                return "Player 2 wins!";
            } else {
                return breakHighCardTie(p1, p2);
            }
        }

        if (p1Rank == 6 || p1Rank == 10) {
            // Straight or Straight Flush
            int p1Straight = p1.findStraight();
            int p2Straight = p2.findStraight();
            if (p1Straight > p2Straight) {
                return "Player 1 wins!";
            } else if (p1Straight < p2Straight) {
                return "Player 2 wins!";
            } else {
                return breakHighCardTie(p1, p2);
            }
        }

        if (p1Rank == 7) {
            // Flush
            int p1Flush = p1.findFlush();
            int p2Flush = p2.findFlush();
            if (p1Flush > p2Flush) {
                return "Player 1 wins!";
            } else if (p1Flush < p2Flush) {
                return "Player 2 wins!";
            } else {
                return breakHighCardTie(p1, p2);
            }
        }

        if (p1Rank == 8) {
            // Full House
            ArrayList<Integer> p1Triples = p1.findTriples();
            ArrayList<Integer> p2Triples = p2.findTriples();
            int p1Triple = p1Triples.get(p1Triples.size() - 1);
            int p2Triple = p2Triples.get(p2Triples.size() - 1);
            if (p1Triple > p2Triple) {
                return "Player 1 wins!";
            } else if (p1Triple < p2Triple) {
                return "Player 2 wins!";
            } else {
                return breakHighCardTie(p1, p2);
            }
        }

        if (p1Rank == 9) {
            // Four of a Kind
            ArrayList<Integer> p1Quads = p1.findQuads();
            ArrayList<Integer> p2Quads = p2.findQuads();
            int p1Quad = p1Quads.get(p1Quads.size() - 1);
            int p2Quad = p2Quads.get(p2Quads.size() - 1);
            if (p1Quad > p2Quad) {
                return "Player 1 wins!";
            } else if (p1Quad < p2Quad) {
                return "Player 2 wins!";
            } else {
                return breakHighCardTie(p1, p2);
            }
        }

        // Rank 10 (straight flush) already implemented with rank 6 (straight)

        if (p1Rank == 11) {
            // Royal Flush
            return "Tie!";
        }

        return "Error: Unimplemented tiebreaker";
    }

    public static void play(){ //simulate card playing
        // Done in Main.java in webui folder, unimplemented here
    }


    // Tiebreaker
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