package Controllers;

import Models.Card.Card;
import Models.Game.Cell;
import Models.Game.GameBoard;
import Models.User.User;
import Views.View;

import java.util.HashMap;
import java.util.regex.Pattern;

public class GameController extends MenuController {
    private static HashMap<String, String> map = new HashMap<>();

    private static User currentUser = null;
    private static User player2 = null;
    private static GameBoard gameBoard = new GameBoard();
    private static String input = "";

    private static final String[] phaseNames = {"", "draw phase", "standby phase", "main phase 1", "battle phase", "main phase 2", "end phase"};

    private static final int[] order = {2,3,1,4,0};

    public static boolean endCondition() {
        if(gameBoard.boards[0].lifePoint <= 0 || gameBoard.boards[1].lifePoint <= 0) {
            return true;
        }
        if(gameBoard.boards[0].deck.mainDeckSize() == 0 || gameBoard.boards[1].deck.mainDeckSize() == 0) {
            return true;
        }
        return input.equals("surrender");
    }

    public static void initialGame() {
        gameBoard.boards[0].user = currentUser;
        //TODO : COPY DECK
        gameBoard.boards[0].deck = currentUser.activeDeck;
        gameBoard.boards[1].user = player2;
        //TODO : COPY DECK
        gameBoard.boards[1].deck = player2.activeDeck;
    }

    private static void getInput() {
        input = View.getInput();
    }

    private static boolean checkWord(String check, String input) {
        check = check + ".*";
        return Pattern.matches(check, input);
    }

    private static boolean checkMenuExit(String input) {
        return checkWord("menu exit", input);
    }

    private static int findFirstEmptyHand(int player) {
        for(int i = 0; i < 6; i++) {
            if(gameBoard.boards[player].hand[i].equals(null)) {
                return i;
            }
        }
        return 6;
    }

    public static void recognizeGameInputsP2P(User[] players) {
        int playerNum = 0;
        if(gameBoard.round % 2 == 0) {
            currentUser = players[0];
            player2 = players[1];
        }
        else {
            playerNum = 1;
            currentUser = players[1];
            player2 = players[0];
        }
        initialGame();
        while(!endCondition()) {
            for(int phase = 1; phase < 7; phase++) {
                getInput();
                if(checkMenuExit(input)) {
                    return;
                }

                System.out.println("phase: " + phaseNames[phase]);
                if(phase == 1) {
                    gameBoard.boards[playerNum].hand[findFirstEmptyHand(playerNum)] = new Cell(gameBoard.boards[playerNum].deck.mainDeck.get(0), 0);
                    gameBoard.boards[playerNum].deck.mainDeck.remove(0);
                }
            }
        }
    }

    public static void recognizeGameInputsP2A(User player) {
        currentUser = player;
        initialGame();
    }
}