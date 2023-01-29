package com.example.cardgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TriantaEna extends Game {

    private static final int CONDITION_OF_WIN = 31;

    private DeckOfCards deck = new DeckOfCards(); // A new deck is prepared
    private Scanner scan = new Scanner(System.in);
    private int balanceInitial = 100; // Initial balance is 100


    public TriantaEna(){
        super(CONDITION_OF_WIN);
    }

    public void playTriantaMode() {
        boolean play = true;
        boolean firstRound = true;
        Card newCard;
        List<Player> newPlayers; // Creating a list to store the player details

        while(play) {
            // Initializing the number of players
            System.out.println("Please enter the number of players. The number of players must be atleast 2");
            int numOfPlayers = scan.nextInt(); // Getting the input from user regarding the number of players.
            while (numOfPlayers < 2) {
                System.out.println("Please enter a number greater than 2. The game requires atleast 2 players");
                numOfPlayers = scan.nextInt();
            }
            List<Player> players = new ArrayList<>();

            // Creating all players and adding them in a list
            for (int i = 0; i < numOfPlayers; i++) {
                String name = "Player " + (i + 1);
                Player newPlayer = new Player(name, balanceInitial);
                newPlayer.setIsDealer(false);
                players.add(newPlayer);
            }

            // Preparing 2 decks of cards
            deck.deckPrepare(2);

            boolean ongoing = true;
            while(ongoing) {

                // The dealer is chosen
                chooseBanker(players); // We select the banker from the set of players.

                // We initialize the banker
                Player banker = getBanker(players); // We get the player who is the banker.
                banker.cleanHands();
                if (firstRound){
                    banker.setBalance(3*balanceInitial);
                    firstRound = false;
                }

                // The card is drawn by the banker
                newCard = deck.cardDraw(); // We draw a card from the deck.
                banker.addCard(newCard); // We add the drawn card to the banker's hand.
                System.out.println("The card that the banker " + banker.getName() + " has drawn is " + newCard.toString());

                // Each player draws a card and choose to fold or keep playing
                newPlayers = new ArrayList<>(); // We create an empty array list of 'newPlayers' to store only those
                // players who opt to play further.
                for (int i = 0; i < numOfPlayers; i++) {
                    Player player = players.get(i); // Get the ith player.
                    if (!player.getIsDealerOrNot()) {
                        player.cleanHands();
                        newCard = deck.cardDraw(); // A new card is drawn.
                        player.cleanHands();
                        player.addCard(newCard);
                        System.out.println(player.getName() + " draws " + newCard.toString());
                        System.out.println("Please press 0 to fold or press 1 to keep playing");
                        int response = scan.nextInt();
                        if (response == 1) {
                            newPlayers.add(player);
                        }
                    }
                }

                // Player chooses to play and bets
                for (int i = 0; i < newPlayers.size(); i++) {
                    Player player = newPlayers.get(i); // Gets the ith player
                    List<List<Card>> newHands = new ArrayList<>(); // newHands is a list of cards with the player after every round.
                    // The first element of newHands is the card list with the player after round 1.
                    // The second element of newHands is the card list with the player after round 2.
                    newHands.add(player.getHand()); // We store the hand of player in newHands.
                    player.setHands(newHands);
                    System.out.println(player.getName() + ", Please select a bet amount");
                    int bet = scan.nextInt();

                    // The bet must not exceed half of banker's current balance.
                    while (bet > banker.getBalanceAmount() / 2) {
                        System.out.println("No sufficient balance with the banker, please enter a smaller amount");
                        bet = scan.nextInt();
                    }
                    player.subtractBalance(bet); // We subtract the balance amount from the player.
                    player.setBet(bet); // We set the bet of the player.

                    while (player.calculateRankValue() < CONDITION_OF_WIN) {
                        System.out.println("The cards with " + player.getName() + " is " + player.getHand());
                        System.out.println("Please press 0 (hit) or 1 (stand)");
                        int choice = scan.nextInt();
                        if (choice == 0) {
                            newCard = deck.cardDraw();
                            System.out.println(player.getName() + " draws a new card " + newCard);
                            player.addCard(newCard);
                        } else {
                            break;
                        }
                    }
                }

                // Turn of the banker
                List<List<Card>> newHands = new ArrayList<>(); // We define newHands to a be empty list of Card class objects.
                newHands.add(banker.getHand());
                banker.setHands(newHands);
                if (newPlayers.size() > 0) { // If there is atleast 1 player who continues playing.
                    while (banker.calculateRankValue() < CONDITION_OF_WIN) {
                        System.out.println("The banker " + banker.getName() + " now has " + banker.getHand());
                        System.out.println("Please press 0 to hit or 1 to stand");
                        int choice = scan.nextInt();
                        if (choice == 0) {
                            newCard = deck.cardDraw();
                            System.out.println("Banker draws a new card " + newCard);
                            banker.addCard(newCard);
                        } else {
                            break;
                        }
                    }
                    // 5. check win
                    for (int i = 0; i < newPlayers.size(); i++) {
                        Player player = newPlayers.get(i);
                        Compare(player, banker);
                    }
                } else {
                    System.out.println("All players have chosen to fold this round.");
                }

                // 6. Game summary
                Summary(players);

                System.out.println("Do you want to start a new round? Please press 0 for Yes or 1 for No");
                int choice = scan.nextInt();
                if (choice != 0) {
                    ongoing = false;
                }
            }

            System.out.println("Do you want to start a new game? Please press 0 for yes or 1 for No");
            int choice = scan.nextInt();
            if (choice != 0) {
                play = false;
                System.out.println("Bye Bye!");
            }
        }
    }

    public void chooseBanker(List<Player> players) {
        Collections.sort(players); // The 'PlayerAbstract' class implements Comparable interface so that
        // objects of 'PlayerAbstract' class can be sorted using Java's sorting algorithms.
        boolean isDealer = false;
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i); // Get the ith player.
            System.out.println("Hi " + player.getName() + ", Do you want to be the banker?  Please press 0 if Yes or 1 if No");
            int response = scan.nextInt();
            if (response == 0) {
                player.setIsDealer(true); // The player becomes the dealer.
                isDealer = true;
                break;
            } else {
                player.setIsDealer(false);
            }
        }
        // If isDealer is false, we randomly select a player and make him the dealer.
        if (!isDealer) {
            double d1 = Math.random() * players.size();
            int d2 = (int) Math.floor(d1);
            players.get(d2).setIsDealer(true);
            System.out.println(players.get(d2).getName() + " is randomly chosen to become the banker.");
        }
    }

    public Player getBanker(List<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getIsDealerOrNot()) {
                return players.get(i);
            }
        }
        return null;
    }

    private void Compare(Player player, Player dealer) {

        int playerRankValue = player.calculateRankValue();
        int dealerRankValue = dealer.calculateRankValue();

        System.out.println("The cards of " + player.getName() + " are " + player.printHands() + ", Total points is " + playerRankValue);
        System.out.println("The cards of " + dealer.getName() + " are " + dealer.printHands() + ", Total points is " + dealerRankValue);

        // Find the winner!
        System.out.println(player.getName() + "'s bet is " + player.getBet());
        if (playerRankValue <= CONDITION_OF_WIN && dealerRankValue <= CONDITION_OF_WIN) { // If both player and bankers are not bust.
            if (playerRankValue < dealerRankValue) {
                dealer.addBalance(player.getBet()); //Banker wins the bet.
                System.out.println("Banker wins");
            } else if (playerRankValue > dealerRankValue) {
                dealer.subtractBalance(player.getBet());
                // gain own and one more bet amount back
                player.addBalance(player.getBet() * 2);
                System.out.println("Player wins!");
            } else {
                // Checking Natural Trianta Ena
                if (playerRankValue == CONDITION_OF_WIN) {
                    if (player.getHand().size() == 3 && dealer.getHand().size() > 3) {
                        dealer.subtractBalance(player.getBet());
                        player.addBalance(player.getBet() * 2);
                        System.out.println("Player wins");
                    } else {
                        dealer.addBalance(player.getBet());
                        System.out.println("Banker wins");
                    }
                } else {
                    // if draw, the dealer wins
                    dealer.addBalance(player.getBet());
                    System.out.println("Banker wins");
                }
            }
        } else if (playerRankValue <= CONDITION_OF_WIN) {
            dealer.subtractBalance(player.getBet());
            player.addBalance(player.getBet() * 2);
            System.out.println("Player wins");
        } else {
            dealer.addBalance(player.getBet());
            System.out.println("Banker wins");
        }
    }

    private void Summary(List<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            System.out.println("The current balance of " + player.getName() + " is " + player.getBalanceAmount());
        }
    }

}
