package com.example.project;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Player {
    private ArrayList<Card> hand;
    private ArrayList<Card> allCards; //the current community cards + hand
    String[] suits = Utility.getSuits();
    String[] ranks = Utility.getRanks();
    private ArrayList<Integer> rankFrequency;
    private ArrayList<Integer> suitFrequency;
    
    public Player() {
        hand = new ArrayList<>();
    }

    public ArrayList<Card> getHand(){return hand;}
    public ArrayList<Card> getAllCards(){return allCards;}

    public void addCard(Card c) {
        hand.add(c);
    }

    public String playHand(ArrayList<Card> communityCards) {
        if (hand.size() == 0) return "Nothing";
        allCards = new ArrayList<>();
        allCards.addAll(hand);
        allCards.addAll(communityCards);
        sortAllCards();
        rankFrequency = findRankingFrequency();
        suitFrequency = findSuitFrequency();

        int straight = findStraight();
        int flush = findFlush();
        ArrayList<Integer> triples = findTriples();
        ArrayList<Integer> pairs = findPairs();

        if (straight != -1 && flush != -1) {
            if (straight == 8) {
                return "Royal Flush";
            }
            return "Straight Flush";
        }
        if (findQuads().size() >= 1) {
            return "Four of a Kind";
        }
        if (triples.size() >= 1 && pairs.size() >= 1) {
            return "Full House";
        }
        if (flush != -1) {
            return "Flush";
        }
        if (straight != -1) {
            return "Straight";
        }
        if (triples.size() >= 1) {
            return "Three of a Kind";
        }
        if (pairs.size() == 2) {
            return "Two Pair";
        }
        if (pairs.size() == 1) {
            return "A Pair";
        }
        if (findHighCard() == allCards.get(allCards.size() - 1).getRankIndex()) {
            return "High Card";
        }

        return "Nothing";
    }

    public void sortAllCards() {
        sort(allCards);
        sort(hand);
    }

    private void sort(ArrayList<Card> array) {
        for (int i = 1; i < array.size(); i++) {
            Card currentCard = array.get(i);
            int cardRankIndex = currentCard.getRankIndex();
            int currentIndex = i;
            while (currentIndex > 0 && cardRankIndex < array.get(currentIndex - 1).getRankIndex()) {
                array.set(currentIndex, array.get(currentIndex - 1));
                currentIndex--;
            }
            array.set(currentIndex, currentCard);
        }
    }

    public ArrayList<Integer> findRankingFrequency() {
        // https://stackoverflow.com/a/3676539
        ArrayList<Integer> frequency = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        for (Card card : allCards) {
            int index = card.getRankIndex();
            frequency.set(index, frequency.get(index) + 1);
        }

        return frequency;
    }

    public ArrayList<Integer> findSuitFrequency() {
        ArrayList<Integer> frequency = new ArrayList<>(List.of(0, 0, 0, 0));
        for (Card card : allCards) {
            int index = card.getSuitIndex();
            frequency.set(index, frequency.get(index) + 1);
        }

        return frequency;
    }

   
    @Override
    public String toString() {
        return hand.toString();
    }



    // Find rankings
    public int findHighCard() {
        if (hand.size() == 0) return -1;
        return hand.get(hand.size() - 1).getRankIndex();
    }

    public ArrayList<Integer> findPairs() {
        ArrayList<Integer> pairs = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            if (rankFrequency.get(i) == 2) {
                pairs.add(i);
            }
        }
        return pairs;
    }

    public ArrayList<Integer> findTriples() {
        ArrayList<Integer> triples = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            if (rankFrequency.get(i) == 3) {
                triples.add(i);
            }
        }
        return triples;
    }

    /** @return First card in straight */
    public int findStraight() {
        int straightCount = 0;
        int lastRank = -1;
        for (Card card : allCards) {
            int rankIndex = card.getRankIndex();
            if (rankIndex == lastRank + 1) {
                straightCount++;
                if (straightCount == 5) {
                    return lastRank - 3;
                }
            } else {
                straightCount = 1;
            }
            lastRank = rankIndex;
        }
        return -1;
    }

    /** @return Highest card in flush */
    public int findFlush() {
        int suit = -1;
        for (int i = 0; i < 4; i++) {
            if (suitFrequency.get(i) >= 5) {
                suit = i;
                break;
            }
        }

        if (suit == -1) {
            return -1;
        }

        // Find highest card with suit
        for (int i = allCards.size() - 1; i >= 0; i--) {
            if (allCards.get(i).getSuitIndex() == suit) {
                return allCards.get(i).getRankIndex();
            }
        }
        return -1;
    }

    public ArrayList<Integer> findQuads() {
        ArrayList<Integer> quads = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            if (rankFrequency.get(i) == 4) {
                quads.add(i);
            }
        }
        return quads;
    }
}
