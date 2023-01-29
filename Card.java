package com.example.cardgame;

public class Card {
    private int rank;
    private CardTypes type;
    private String faceValue;
    private static String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    // The variables 'rank', 'type', 'faceValue' and 'ranks' are private so that we can't access/modify the values
    // outside the class. Additionally the 'ranks' array is made static so that the variable 'ranks' belongs to
    // the class rather than a specific instance of the class.

    // To create an object of the class "Card", we require 2 input parameters - the type of the card
    // and the value of card.

    // Ace card has a value of 0. Card with number 2 has a value of 1. Card with number 3 has a value of 2.
    // So on....Car with number 10 has a value of 9. Card with "J", "Q", "K" on it has a value of 10.

    public Card(CardTypes type, int value) {
        this.type = type;

        if (value > 9){
            this.rank = 10;
        }
        else {
            this.rank = value + 1;
        }

        this.faceValue = ranks[value];
    }

    // Since 'rank', 'type' and 'faceValue' are private variables, we would not be able to access it outside
    // the class "Card". So we create the public 'get' methods  to access the rank, type and faceValue variables
    // outside the class.

    public int getRank(){
        return this.rank;
    }

    public CardTypes getType(){
        return this.type;
    }

    public String getFaceValue(){
        return this.faceValue;
    }

    // We also create a method to obtain the length of 'Ranks' array. We make this method 'static' so that method
    // belongs to the class rather than a specific instance of the class.

    public static int getSizeOfRanks(){
        return ranks.length;
    }

    // We are overriding the toString method so that when we print the object of the class 'Card', the faceValue
    // of the card will be printed.

    @Override
    public String toString() {
        return faceValue;
    }

}
