package com.example.cardgame;

import java.util.ArrayList;
import java.util.List;

public class DeckOfCards {

    private List<Card> cardDeck;

    // We are declaring the 'cardDeck' to be an empty list.

    public DeckOfCards(){
        this.cardDeck = new ArrayList<>();
    }

    public void deckPrepare(int n) {

        // The clear() method of List Interface removes all elements from List.
        this.cardDeck.clear();

        // Now we get the values of 'totalCardTypes' and 'rankSize' by calling the values() and getSizeOfRanks()
        // methods of CardTypes and Card classes.
        int totalCardTypes = CardTypes.values().length;
        int rankSize = Card.getSizeOfRanks();

        for(int i = 0; i < n; i++){ // Where n is the number of decks required
            for (int suit = 0; suit < totalCardTypes; suit++) { //totalCardTypes is normally 4.
                for (int rank = 0; rank < rankSize; rank++) { //rankSize is normally 13.
                    this.cardDeck.add(new Card(CardTypes.values()[suit], rank)); // The values() method of enum
                    // class is to return all values present inside the enum.
                }
            }
        }
    }

    public Card cardDraw() {

        double random1 = Math.random() * this.cardDeck.size(); //Math.random() returns a number between 0 and 1.
        int random2 = (int) Math.floor(random1); // Math.floor rounds off the number to the closest integer
        // (return type double) which is lesser than the number. We are explicitly typecasting the result to an integer.

        Card card = this.cardDeck.get(random2); // The get() method of List interface is to get the element in the
        // list at the specific index passed in as parameter.
        this.cardDeck.remove(random2); // The remove() method of List interface is to remove the element in the
        // list at the specific index passed in as parameter.
        return card;
    }

    // Since 'cardDeck' is a private variable, we create the get methods to access the private variables outside
    // of a class.

    public List<Card> getCardDeck(){
        return this.cardDeck;
    }

    // The below method returns the size of the 'cardDeck'.

    public int deckSize(){
        return this.cardDeck.size();
    }

}


