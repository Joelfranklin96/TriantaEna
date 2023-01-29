package com.example.cardgame;

import java.util.ArrayList;
import java.util.List;

public class Player extends PlayerAbstract{

    private List<Integer> multiplyWin;

    // multiplyWin is a list which has the bet details of each round. The element is 1 if the game is a tie.
    // The element is 2 if the player wins. The element is 4 for double up.

    // A constructor is to construct an object. A constructor is a method that is called when an object
    // is created.
    // We have 2 constructors below. One constructor takes 2 parameters

    public Player(String name, int balanceAmount){
        super(name,balanceAmount,false);
        this.multiplyWin = new ArrayList<>();
    }

    public Player(String name, int balanceAmount, boolean isDealerOrNot) {
        super(name, balanceAmount, isDealerOrNot);
    }

    // Method to add balance
    public void addBalance(int balanceAmount) {
        this.balanceAmount += balanceAmount;
    }

    // Method is subtract balance
    public void subtractBalance(int balanceAmount) {
        this.balanceAmount -= balanceAmount;
    }

    // Method to set multiplyWin
    public void setMultiplyWin(List<Integer> list){
        this.multiplyWin = list;
    }

    // Method to set multiplyWin of a specific round
    public void setMultiplyWinOf(int index, int multi){
        this.multiplyWin.set(index, multi);
    }

    // Method to get multiplyWin of a specific round
    public int getmultiplyWinOf(int index){
        return this.multiplyWin.get(index);
    }

}
