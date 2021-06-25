package Controllers;

import Models.Card.Card;
import Models.Game.Board;
import Models.Game.Cell;
import Models.Game.GameBoard;
import Models.User.User;
import Views.View;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameController extends MenuController {
    private static HashMap<String, String> map = new HashMap<>();

    private static User currentUser = null;
    private static User player2 = null;
    private static GameBoard gameBoard = new GameBoard();
    private static String input = "";
    private static String output = "invalid command";
    private static Card selectedCard = null;

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

    private static void showGame() {
        View.showGameBoard(gameBoard);
    }

    private static String getNames(String check, String input) {
        check = check + " (.+)";
        Pattern pattern = Pattern.compile(check);
        Matcher matcher = pattern.matcher(input);
        matcher.find();
        return matcher.group(1);
    }

    private static boolean stringExists(String input, String check) {
        String[] tmp = input.split(" ");
        for (String s : tmp) {
            if (s.equals(check)) {
                return true;
            }
        }
        return false;
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
                System.out.println("phase: " + phaseNames[phase]);

                showGame();
                if(phase == 1) {
                    gameBoard.boards[playerNum].hand[findFirstEmptyHand(playerNum)] = new Cell(gameBoard.boards[playerNum].deck.mainDeck.get(0), 0);
                    String name = gameBoard.boards[playerNum].deck.mainDeck.get(0).getName();
                    System.out.println("new card added to the hand : " + name);
                    gameBoard.boards[playerNum].deck.mainDeck.remove(0);
                    showGame();
                }
                getInput();
                if(checkMenuExit(input)) {
                    return;
                }

                recognizeSelect(playerNum);

                if(input.equals("next phase")) {
                    continue;
                }
                else if(phase == 2) {
                    //TODO : standby phase
                    continue;
                }
                else if(phase == 3) {
                    while(!input.equals("next phase")) {
                        if(checkWord("summon", input)) {

                        } else if(checkWord("set", input)) {

                        } else if(checkWord("flip-summon", input)) {

                        } else if(checkWord("activate effect", input)) {

                        }

                        getInput();
                        if(checkMenuExit(input)) {
                            return;
                        }

                        recognizeSelect(playerNum);
                        recognizeCardShow();
                        recognizeShowGraveYard();
                    }
                }
            }
        }
    }

    private static void recognizeCardShow() {
        if(checkWord("card show --selected")) {

        }
    }

    private static void recognizeShowGraveYard() {
        if(checkWord("show graveyard")) {
            
        }
    }

    private static void recognizeSelect(int playerNum) {
        if(checkWord("select", input)) {
            output = "invalid selection";

            String tmp = getNames("select", input);
            String[] words = tmp.split(" ");

            int number = 0;

            Board board = gameBoard.boards[playerNum];
            if(stringExists(tmp, "--opponent")) {
                board = gameBoard.boards[1 - playerNum];
            }

            if(!stringExists(tmp, "--field")) {
                number = Integer.parseInt(words[words.length - 1]);
            }

            else if(stringExists(tmp, "--monster")) {
                number = order[number - 1] - 1;

                if(number > 4) {
                    output = "invalid selection";
                }
                else if(board.monsterCells[number] == null) {
                    output = "no card found in the given position";
                }
                else {
                    selectedCard = board.monsterCells[number].getCard();
                    output = "card selected";
                }
            }
            else if(stringExists(tmp, "--spell")) {
                number = order[number - 1] - 1;

                if(number > 4) {
                    output = "invalid selection";
                }
                else if(board.spellCells[number] == null) {
                    output = "no card found in the given position";
                }
                else {
                    selectedCard = board.spellCells[number].getCard();
                    output = "card selected";
                }
            }
            else if(stringExists(tmp, "--field")) {
                if(board.fieldZone.equals(null)) {
                    output = "no card found in the given position";
                }
                else {
                    selectedCard = board.fieldZone;
                    output = "card selected";
                }
            }
            else if(stringExists(tmp, "--hand")) {
                if(number > board.hand.length) {
                    output = "invalid selection";
                }
                else {
                    selectedCard = board.hand[number - 1].getCard();
                    output = "card selected";
                }
            }
            else if(stringExists(tmp, "-d")) {
                if(selectedCard == null) {
                    output = "no card is selected yet";
                }
                else {
                    output = "card deselected";
                    selectedCard = null;
                }
            }
            System.out.println(output);
        }
    }


    public static void recognizeGameInputsP2A(User player) {
        currentUser = player;
        initialGame();
    }
}