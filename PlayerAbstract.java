package com.example.cardgame;

// An interface only contains static constants and abstract methods. The interface can't have a method body.
// The difference between Interface and Abstract class is that in Interface you can only declare methods and
// not define methods whereas in Abstract class, you can define concrete normal methods and abstract methods as well.

// The 'implement' keyword is used to implement interfaces. The method bodies are included in the class which
// 'implements' the interfaces.

// If a class implements the 'Comparable' interface, then objects of the class can be sorted using Java's
// sorting algorithms.

import java.util.ArrayList;
import java.util.List;

// An abstract class is a restricted class that can't be used to create objects.

public abstract class PlayerAbstract implements Comparable<PlayerAbstract> {

    protected int balanceAmount;
    private String name;
    private List<Card> handCards; // Hand cards of a player
    private List<List<Card>> handsCards; // All the sets of hand cards of a player
    private boolean isDealerOrNot; // Whether the player is dealer or not.
    private static final int TWENTY_ONE = 21;
    private static final int ThIRTY_ONE = 31;
    private static final String ACE = "A";
    private int bet; // bet placed by the player.

    public PlayerAbstract(String name, int balanceAmount, boolean isDealerOrNot) {
        this.name = name;
        this.handCards = new ArrayList<>();
        this.handsCards = new ArrayList<>();
        this.balanceAmount = balanceAmount;
        this.isDealerOrNot = isDealerOrNot;
    }

    // get method to get the bet of player.
    public int getBet(){
        return this.bet;
    }

    // set method to set the bet of player.
    public void setBet(int bet){
        this.bet = bet;
    }

    // This method returns 'true' if the player is a dealer.
    public boolean getIsDealerOrNot(){
        return this.isDealerOrNot;
    }

    // Sets a particular player as a dealer.
    public void setIsDealer(boolean isDealerOrNot){
        this.isDealerOrNot = isDealerOrNot;
    }

    // get method to get a player's name.
    public String getName(){
        return this.name;
    }

    // set method to change the name of the player.
    public void setName(String name){
        this.name = name;
    }

    // get method to get the handcards of a player.
    public List<Card> getHand() {
        return this.handCards;
    }

    // set method to set the handcards of a player.
    public void setHand(List<Card> handCards) {
        this.handCards = handCards;
    }

    // get method to get all the handcards of a player.
    public List<List<Card>> getHands() {
        return this.handsCards;
    }

    // set method to set all the handcards of a player.
    public void setHands(List<List<Card>> handsCards) {
        this.handsCards = handsCards;
    }

    // Initialize the set of handcards of a player.
    public void initHands() {
        List<List<Card>> myHandCards = new ArrayList<>();
        myHandCards.add(this.handCards);
        this.handsCards = myHandCards;
    }

    // Add a card to the handcards of a player.
    public void addCard(Card card) {
        this.handCards.add(card);
    }

    // Clean all the handcards of a player.
    public void cleanHands() {
        this.handCards.clear();
        this.handsCards.clear();
    }

    // get method to get the balance amount of a player.
    public int getBalanceAmount() {
        return this.balanceAmount;
    }

    // set method to set the balance amount of a player.
    public void setBalance(int balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public int calculateRankValue(){

        int value = 0;
        int aceCount = 0;

        for (int i = 0; i < this.handCards.size(); i++){
            Card card = this.handCards.get(i);
            value = value + card.getRank();
            if (card.getFaceValue().equals("A")){
                aceCount += 1;
            }
        }

        if (aceCount == 0){
            return value;
        }

        else if (aceCount == 1){
            if ((value + 10) > 31){
                return value;
            }
            else{
                return value + 10;
            }
        }

        else{
            for (int i = 1; i < aceCount; i++){
                value += 10;
            }
            return value;
        }
    }

    public String printHand() {
        StringBuilder sb = new StringBuilder(); // The StringBuilder is an alternative to String class. String
        // class creates an immutable sequence of characters whereas StringBuilder creates a mutable sequence of characters.
        sb.append("[");
        for(Card card: this.handCards) {
            sb.append(card.getType()).append(" ").append(card.getFaceValue()).append(", ");
        }
        sb.trimToSize(); // Makes the current sequence of characters more space efficient.
        return sb.substring(0, sb.length() - 2) + "]";
    }

    // Print a specific hand among all the hands that the player has.
    public String printHand(int index) {
        StringBuilder sb = new StringBuilder(); // The StringBuilder is an alternative to String class. String
        // class creates an immutable sequence of characters whereas StringBuilder creates a mutable sequence of characters.
        List<Card> hand = this.handsCards.get(index);
        for (Card card: hand) {
            sb.append(card.getType()).append(" ").append(card.getFaceValue()).append(", ");
        }
        sb.trimToSize(); // Makes the current sequence of characters more space efficient.
        return sb.substring(0, sb.length() - 2) + "]";
    }

    /** Print All Hands that the Player might have, in the format of [[spade 4, heart 5], [spade Q, heart 2]] */
    public String printHands() {
        StringBuilder res = new StringBuilder();
        res.append("[");
        for (int i = 0; i < this.handsCards.size(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            List<Card> hand = handsCards.get(i);
            for (Card card: hand) {
                sb.append(card.getType().toString()).append(" ").append(card.getFaceValue()).append(", ");
            }
            sb.trimToSize();

            res.append(sb.substring(0, sb.length() - 2)).append("]").append("]");
        }
        return res.toString();
    }

    // Integer.compare returns 0 if x == y. If (x < y), it returns a value less than 0.
    // If (x > y), it returns a value greater than 0.
    @Override
    public int compareTo(PlayerAbstract player1) {
        return Integer.compare(player1.getBalanceAmount(), this.getBalanceAmount());
    }
}
