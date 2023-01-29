package com.example.cardgame;

public abstract class Game {

    private static int CONDITION_OF_WIN = 0;

    public Game(int win){
        CONDITION_OF_WIN = win;
    }
}
