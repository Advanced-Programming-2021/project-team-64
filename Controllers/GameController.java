package Controllers;

import Models.Card.Card;
import Models.Card.CardSystem;
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
    private static Cell selectedCell = null;
    private static int selectedIndex = 0;
    private static int playerNum = 0;
    private static boolean isSelectedInHand = false;
    private static boolean isEnemyCard = false;
    private static int number = 0;
    private static int summonedCards = 0;

    private static final String[] phaseNames = {"", "draw phase", "standby phase", "main phase 1", "battle phase", "main phase 2", "end phase"};

    private static final int[] order = {2, 3, 1, 4, 0};

    public static boolean endCondition() {
        if (gameBoard.boards[0].lifePoint <= 0 || gameBoard.boards[1].lifePoint <= 0) {
            return true;
        }
        if (gameBoard.boards[0].deck.mainDeckSize() == 0 || gameBoard.boards[1].deck.mainDeckSize() == 0) {
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

    private static int firstEmptyMonsterCell() {
        for (int i : order) {
            int j = i - 1;
            if(gameBoard.boards[playerNum].monsterCells[j] == null) {
                return j;
            }
        }
        return 0;
    }

    private static int firstEmptySpellCell() {
        for (int i : order) {
            int j = i - 1;
            if(gameBoard.boards[playerNum].spellCells[j] == null) {
                return j;
            }
        }
        return 0;
    }

    private static void deselect() {
        selectedCard = null;
        selectedCell = null;
        isSelectedInHand = false;
        isEnemyCard = false;
    }

    public static void recognizeGameInputsP2P(User[] players) {
        choosePlayer(players);
        initialGame();
        while (!endCondition()) {
            choosePlayer(players);
            for (int phase = 1; phase < 7; phase++) {
                System.out.println("phase: " + phaseNames[phase]);

                showGame();
                if (phase == 1) {
                    gameBoard.boards[playerNum].hand.add(new Cell(gameBoard.boards[playerNum].deck.mainDeck.get(0), 0));
                    String name = gameBoard.boards[playerNum].deck.mainDeck.get(0).getName();
                    System.out.println("new card added to the hand : " + name);
                    gameBoard.boards[playerNum].deck.mainDeck.remove(0);
                    showGame();
                }
                getInput();
                if (checkMenuExit(input)) {
                    return;
                }
                while(phase != 3 && phase != 5 && mainPhaseAction()) {
                    System.out.println("action not allowed in this phase");
                    getInput();
                    if (checkMenuExit(input)) {
                        return;
                    }
                }
                while(phase != 4 && battlePhaseAction()) {
                    System.out.println("action not allowed in this phase");
                    getInput();
                }

                if(recognizeCheat() == 1) {
                    return;
                }

                recognizeSelect(playerNum);
                recognizeCardShow();
                recognizeShowGraveYard();


                if (input.equals("next phase")) {
                    continue;
                }
                else if (phase == 2) {
                    //TODO : standby phase
                    continue;
                }

                else if (phase == 3 || phase == 5) {
                    summonedCards = 0;
                    while (!input.equals("next phase")) {
                        if (selectedCard == null) {
                            output = "no card is selected yet";
                        }
                        if (checkWord("summon", input)) {
                            if (!selectedCard.isMonster() || !isSelectedInHand) {
                                output = "you can’t summon this card";
                            }
                            else if(gameBoard.boards[playerNum].countOfCardsInMonsters() == 5){
                                output = "monster card zone is full";
                            }
                            else if(summonedCards != 0) {
                                output = "you already summoned/set on this turn";
                            }
                            else {
                                if(selectedCard.getLevel() <= 4) {
                                    gameBoard.boards[playerNum].monsterCells[firstEmptyMonsterCell()] = new Cell(selectedCard, 3);
                                    gameBoard.boards[playerNum].hand.remove(number);
                                    output = "summoned successfully";
                                    summonedCards++;
                                }
                                else if(selectedCard.getLevel() == 5 || selectedCard.getLevel() == 6) {
                                    if(gameBoard.boards[playerNum].countOfCardsInMonsters() == 0) {
                                        output = "there are not enough cards for tribute";
                                    }
                                    else {
                                        while(true) {
                                            System.out.println("select a card's address to tribute or cancel");
                                            getInput();
                                            if(input.equals("cancel")) {
                                                break;
                                            }
                                            int num = Integer.parseInt(input);
                                            num--;
                                            if(gameBoard.boards[playerNum].monsterCells[num] == null) {
                                                output = "there no monsters one this address";
                                            }
                                            else {
                                                gameBoard.boards[playerNum].graveYard.add(gameBoard.boards[playerNum].monsterCells[num].getCard());
                                                gameBoard.boards[playerNum].monsterCells[num] = null;
                                                gameBoard.boards[playerNum].monsterCells[firstEmptyMonsterCell()] = new Cell(selectedCard, 3);
                                                gameBoard.boards[playerNum].hand.remove(number);
                                                output = "summoned successfully";
                                                break;
                                            }
                                        }
                                    }
                                }
                                else  {
                                    if(gameBoard.boards[playerNum].countOfCardsInMonsters() < 2) {
                                        output = "there are not enough cards for tribute";
                                    }
                                    else {
                                        while(true) {
                                            System.out.println("select 2 cards address's to tribute or cancel");
                                            getInput();
                                            if(input.equals("cancel")) {
                                                break;
                                            }
                                            String[] tmp = input.split(" ");
                                            int num1 = Integer.parseInt(tmp[0]);
                                            int num2 = Integer.parseInt(tmp[1]);
                                            num1--;
                                            num2--;

                                            if(gameBoard.boards[playerNum].monsterCells[num1] == null || gameBoard.boards[playerNum].monsterCells[num2] == null) {
                                                output = "there is no monster on one of these addresses";
                                            }

                                            else {
                                                gameBoard.boards[playerNum].graveYard.add(gameBoard.boards[playerNum].monsterCells[num1].getCard());
                                                gameBoard.boards[playerNum].monsterCells[num1] = null;
                                                gameBoard.boards[playerNum].graveYard.add(gameBoard.boards[playerNum].monsterCells[num2].getCard());
                                                gameBoard.boards[playerNum].monsterCells[num2] = null;
                                                gameBoard.boards[playerNum].monsterCells[firstEmptyMonsterCell()] = new Cell(selectedCard, 3);
                                                gameBoard.boards[playerNum].hand.remove(number);
                                                output = "summoned successfully";
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        else if (checkWord("set", input)) {
                            if(input.equals("set")) {
                                standardSet();
                            }
                            String tmp = getNames("set", input);
                            if(stringExists(tmp, "--position")) {
                                if(selectedCard == null) {
                                    output = "no card is selected yet";
                                }
                                else if(!(selectedCard.isMonster() && !isSelectedInHand)) {
                                    output = "you can’t change this card position";
                                }
                                if(stringExists(tmp, "attack")) {
                                    if(selectedCell.getState() != 1) {
                                        output = "this card is already in the wanted position";
                                    }
                                    else {
                                        selectedCell.setState(3);
                                        output = "monster card position changed successfully";
                                    }
                                }
                                else if(stringExists(tmp, "defense")) {
                                    if(selectedCell.getState() != 3) {
                                        output = "this card is already in the wanted position";
                                    }
                                    else {
                                        selectedCell.setState(1);
                                        output = "monster card position changed successfully";
                                    }
                                }
                            }

                        }

                        else if (checkWord("flip-summon", input)) {
                            if(selectedCard == null) {
                                output = "no card is selected yet";
                            }
                            else if(selectedCell.getState() != 2) {
                                output = "you can’t flip summon this card";
                            }
                            else {
                                output = "flip summoned successfully";
                            }
                        }

                        else if (checkWord("activate effect", input)) {
                            if(selectedCard == null) {
                                output = "no card is selected yet";
                            }
                            else if(!selectedCard.isSpell()) {
                                output = "activate effect is only for spell cards";
                            }
                            // TODO :
//                            else if(selectedCard.isActivated()) {
//                                output = "you have already activated this card";
//                            }
                            else if(isSelectedInHand) {
                                if(gameBoard.boards[playerNum].countOfCardsInSpells() == 5) {
                                    output = "spell card zone is full";
                                }
                                // TODO :
//                                if(selectedCard.isFieldSpell()) {
//                                    if(gameBoard.boards[playerNum].fieldZone.hasCard()) {
//                                        gameBoard.boards[playerNum].graveYard.add(gameBoard.boards[playerNum].fieldZone.getCard());
//                                    }
//                                    gameBoard.boards[playerNum].fieldZone = new Cell(selectedCard, 1);
//                                }
                                gameBoard.boards[playerNum].spellCells[firstEmptySpellCell()] = new Cell(selectedCard, 1);
                                gameBoard.boards[playerNum].hand.remove(number);
                                output = "set successful";
                            }
                            else {
                                gameBoard.boards[playerNum].spellCells[selectedIndex] = new Cell(selectedCard, 1);
                                output = "set successful";
                            }
                        }

                        System.out.println(output);
                        getInput();
                        if (checkMenuExit(input)) {
                            return;
                        }
                        while(battlePhaseAction()) {
                            System.out.println("action not allowed in this phase");
                            getInput();
                        }

                        if(recognizeCheat() == 1) {
                            return;
                        }

                        recognizeSelect(playerNum);
                        recognizeCardShow();
                        recognizeShowGraveYard();
                        showGame();
                    }
                }

                else if (phase == 4) {
                    while (!input.equals("next phase")) {
                        if (selectedCard == null) {
                            output = "no card is selected yet";
                        }
                        if(checkWord("attack", input)) {
                            String tmp = getNames("attack", input);
                            if(stringExists(tmp, "direct")) {
                                if(selectedCard == null) {
                                    output = "no card is selected yet";
                                }
                                else if(!(isSelectedInHand && selectedCard.isMonster())) {
                                    output = "you can’t attack with this card";
                                }
                                else if(gameBoard.boards[1 - playerNum].countOfCardsInMonsters() != 0) {
                                    output = "you can’t attack the opponent directly";
                                }
                                else {
                                    gameBoard.boards[1 - playerNum].lifePoint -= selectedCard.getAttack1();
                                    output = "you opponent receives " + selectedCard.getAttack1() + " battle damage";
                                }
                            }
                            else {
                                String[] s = tmp.split(" ");
                                int opponentNum = Integer.parseInt(s[s.length - 1]);
                                if(selectedCard == null) {
                                    output = "no card is selected yet";
                                }
                                else if(!(selectedCard.isMonster() && !isSelectedInHand)) {
                                    output = "you can’t attack with this card";
                                }
                                else if(gameBoard.boards[1 - playerNum].monsterCells[opponentNum] == null) {
                                    output = "there is no card to attack here";
                                }
                                else {
                                    int lp1 = selectedCard.getAttack1();
                                    int lp2 = 0;
                                    switch (gameBoard.boards[1 - playerNum].monsterCells[opponentNum].getState()) {
                                        case 3 -> {
                                            lp2 = gameBoard.boards[1 - playerNum].monsterCells[opponentNum].getCard().getAttack1();
                                            if (lp1 > lp2) {
                                                gameBoard.boards[1 - playerNum].graveYard.add(gameBoard.boards[1 - playerNum].monsterCells[opponentNum].getCard());
                                                gameBoard.boards[1 - playerNum].monsterCells[opponentNum] = null;
                                                gameBoard.boards[1 - playerNum].lifePoint -= lp1 - lp2;
                                                output = "your opponent’s monster is destroyed and your opponent receives " + (lp1 - lp2) + " battle damage";
                                            } else if (lp1 == lp2) {
                                                gameBoard.boards[1 - playerNum].monsterCells[opponentNum] = null;
                                                gameBoard.boards[playerNum].monsterCells[number] = null;
                                                output = "both you and your opponent monster cards are destroyed and no one receives damage";
                                            } else {
                                                gameBoard.boards[playerNum].graveYard.add(gameBoard.boards[playerNum].monsterCells[number].getCard());
                                                gameBoard.boards[playerNum].monsterCells[number] = null;
                                                gameBoard.boards[playerNum].lifePoint -= lp1 - lp2;
                                                output = "Your monster card is destroyed and you received " + (lp2 - lp1) + " battle damage";
                                            }
                                        }
                                        case 1 -> {
                                            lp2 = gameBoard.boards[1 - playerNum].monsterCells[opponentNum].getCard().getDefence();
                                            if (lp1 > lp2) {
                                                gameBoard.boards[1 - playerNum].graveYard.add(gameBoard.boards[1 - playerNum].monsterCells[opponentNum].getCard());
                                                gameBoard.boards[1 - playerNum].monsterCells[opponentNum] = null;
                                                output = "the defense position monster is destroyed";
                                            } else if (lp1 == lp2) {
                                                output = "no card is destroyed";
                                            } else {
                                                gameBoard.boards[playerNum].lifePoint -= lp2 - lp1;
                                                output = "no card is destroyed and you received " + (lp2 - lp1) + " battle damage";
                                            }
                                        }
                                        case 2 -> {
                                            lp2 = gameBoard.boards[1 - playerNum].monsterCells[opponentNum].getCard().getDefence();
                                            if (lp1 > lp2) {
                                                gameBoard.boards[1 - playerNum].graveYard.add(gameBoard.boards[1 - playerNum].monsterCells[opponentNum].getCard());
                                                gameBoard.boards[1 - playerNum].monsterCells[opponentNum] = null;
                                                output = "opponent’s monster card was " + (gameBoard.boards[1 - playerNum].monsterCells[opponentNum].getCard().getName()) + " the defense position monster is destroyed";
                                            } else if (lp1 == lp2) {
                                                output = "opponent’s monster card was " + (gameBoard.boards[1 - playerNum].monsterCells[opponentNum].getCard().getName()) + "no card is destroyed";
                                            } else {
                                                gameBoard.boards[playerNum].lifePoint -= lp2 - lp1;
                                                output = "opponent’s monster card was " + (gameBoard.boards[1 - playerNum].monsterCells[opponentNum].getCard().getName()) + "no card is destroyed and you received " + (lp2 - lp1) + " battle damage";
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        System.out.println(output);
                        getInput();
                        if (checkMenuExit(input)) {
                            return;
                        }
                        while(mainPhaseAction()) {
                            System.out.println("action not allowed in this phase");
                            getInput();
                        }

                        recognizeSelect(playerNum);
                        recognizeCardShow();
                        recognizeShowGraveYard();
                        showGame();
                    }
                }

                else if(phase == 6) {
                    System.out.println("its " + gameBoard.boards[1 - playerNum].user.getNickname() + " turn");
                    gameBoard.round++;
                }
            }
        }
    }


    private static void choosePlayer(User[] players) {
        playerNum = 0;
        if (gameBoard.round % 2 == 0) {
            currentUser = players[0];
            player2 = players[1];
        } else {
            playerNum = 1;
            currentUser = players[1];
            player2 = players[0];
        }
    }

    private static void standardSet() {
        if(selectedCard == null) {
            output = "no card is selected yet";
        }
        else if(!isSelectedInHand) {
            output = "you cant set this card";
        }
        else if(summonedCards != 0) {
            output = "you already summoned/set on this turn";
        }
        else if(selectedCard.isMonster()) {
            if(gameBoard.boards[playerNum].countOfCardsInMonsters() == 5) {
                output = "monster card zone is full";
            }
        }
        else if(!selectedCard.isMonster()) {
            if(gameBoard.boards[playerNum].countOfCardsInSpells() == 5) {
                output = "spell/trap card zone is full";
            }
        }
        else {
            gameBoard.boards[playerNum].hand.remove(number);
            if(selectedCard.isMonster())
                gameBoard.boards[playerNum].monsterCells[firstEmptyMonsterCell()] = new Cell(selectedCard, 2);
            else {
                gameBoard.boards[playerNum].monsterCells[firstEmptySpellCell()] = new Cell(selectedCard, 2);
            }
            output = "set successfully";
        }
    }

    private static void recognizeCardShow() {
        if (checkWord("card show --selected", input)) {
            if(selectedCard == null) {
                output = "no card is selected yet";
            }
            else if(isEnemyCard && selectedCell.getState() == 2) {
                output = "card is not visible";
            }
            else {
                output = selectedCard.getName() + " : " + "att : " + selectedCard.getAttack1() + "def : " + selectedCard.getDefence();
            }
            System.out.println(output);
        }
    }

    private static void recognizeShowGraveYard() {
        while(!input.equals("back")) {
            if (checkWord("show graveyard", input)) {
                String tmp = getNames("show graveyard", input);
                int player = playerNum;
                if (stringExists(tmp, "--opponent")) {
                    player = 1 - player;
                }
                if (gameBoard.boards[player].graveYard.size() == 0) {
                    System.out.println("graveyard empty");
                } else {
                    int i = 1;
                    for (Card card : gameBoard.boards[player].graveYard) {
                        System.out.println("" + i + ". " + card.getName() + " : " + card.getDescription());
                        i++;
                    }
                }
            }
            getInput();
        }
    }

    private static void recognizeSelect(int playerNum) {
        if (checkWord("select", input)) {
            output = "invalid selection";

            String tmp = getNames("select", input);
            String[] words = tmp.split(" ");

            number = 0;
            Board board = gameBoard.boards[playerNum];
            boolean enemy = false;
            if (stringExists(tmp, "--opponent")) {
                enemy = true;
                board = gameBoard.boards[1 - playerNum];
            }

            if (!stringExists(tmp, "--field")) {
                number = Integer.parseInt(words[words.length - 1]);
            } else if (stringExists(tmp, "--monster")) {
                number = order[number - 1] - 1;

                if (number > 4) {
                    output = "invalid selection";
                } else if (board.monsterCells[number] == null) {
                    output = "no card found in the given position";
                } else {
                    selectedCard = board.monsterCells[number].getCard();
                    selectedCell = board.monsterCells[number];
                    isEnemyCard = enemy;
                    output = "card selected";
                }
            } else if (stringExists(tmp, "--spell")) {
                number = order[number - 1] - 1;

                if (number > 4) {
                    output = "invalid selection";
                } else if (board.spellCells[number] == null) {
                    output = "no card found in the given position";
                } else {
                    selectedCard = board.spellCells[number].getCard();
                    selectedCell = board.monsterCells[number];
                    isEnemyCard = enemy;
                    output = "card selected";
                }
            } else if (stringExists(tmp, "--field")) {
                if (board.fieldZone.equals(null)) {
                    output = "no card found in the given position";
                } else {
                    selectedCard = board.fieldZone.getCard();
                    selectedCell = board.fieldZone;
                    isEnemyCard = enemy;
                    output = "card selected";
                }
            } else if (stringExists(tmp, "--hand")) {
                if (number > board.hand.size()) {
                    output = "invalid selection";
                } else {
                    selectedCard = board.hand.get(number - 1).getCard();
                    selectedCell = board.monsterCells[number - 1];
                    isSelectedInHand = true;
                    isEnemyCard = enemy;
                    output = "card selected";
                }
            } else if (stringExists(tmp, "-d")) {
                if (selectedCard == null) {
                    output = "no card is selected yet";
                } else {
                    isSelectedInHand = false;
                    output = "card deselected";

                    deselect();
                }
            }
            System.out.println(output);
        }
    }

    private static boolean mainPhaseAction() {
        return checkWord("set", input) || checkWord("summon", input) || checkWord("flip-summon", input) || checkWord("activate", input);
    }

    private static boolean battlePhaseAction() {
        return checkWord("attack", input);
    }

    private static int recognizeCheat() {
        String tmp = "";
        if(checkWord("increase --money", input)) {
            tmp = getNames("increase --money", input);
            int x = Integer.parseInt(tmp);
            gameBoard.boards[playerNum].user.increaseMoney(x);
        }
        else if(checkWord("select --hand ", input)) {
            tmp = getNames("select --hand", input);
            StringBuilder card = new StringBuilder();
            String[] s = tmp.split(" ");
            for (String s1 : s) {
                if(!s1.equals("--force")) {
                    card.append(s1);
                }
            }
            if(stringExists(tmp, "--force")) {
                selectedCard = CardSystem.getCard(card.toString());
            }
        }
        else if(checkWord("increase --LP", input)) {
            tmp = getNames("increase --LP", input);
            int x = Integer.parseInt(tmp);
            gameBoard.boards[playerNum].lifePoint += x;
        }
        else if(checkWord("duel set winner", input)) {
            tmp = getNames("duel set winner", input);
            String nickname = tmp;
            System.out.println(User.getByNickname(nickname).getUsername() + "won the whole match with score: " + gameBoard.boards[playerNum].lifePoint + " - " + gameBoard.boards[1 - playerNum].lifePoint);
            return 1;
        }
        return 0;
    }

    public static void recognizeGameInputsP2A(User player) {
        currentUser = player;
        initialGame();
    }
}