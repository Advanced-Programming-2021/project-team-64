package Views;

import Controllers.MenuController;
import Models.Game.Board;
import Models.Game.Cell;
import Models.Game.GameBoard;

import java.util.Scanner;

public class View {
    public static MenuController controller;
    public static Scanner scanner;

    static {
        controller = new MenuController();
        scanner = new Scanner(System.in);
    }

    public void setInput(String input) {
        controller.setOutput("invalid command");
        controller.recognizeInput(input);
    }

    public static String getInput() {
        return scanner.nextLine();
    }

    public void getOutput() {
        System.out.println(controller.getOutput());
    }

    public static void main(String[] args) {
        View view = new View();
        String input = scanner.nextLine();
        while (!input.equals("end")) {
            view.setInput(input);
            view.getOutput();
            input = scanner.nextLine();
        }
    }

    public static void showGameBoard(GameBoard gameBoard) {
        Board board1 = gameBoard.boards[1];
        Board board2 = gameBoard.boards[0];

        System.out.println(board1.user.getNickname() + " : " + board1.lifePoint);

        int cardCount = 0;
        for (Cell cell : board1.hand) {
            cardCount++;
        }
        for (int i = 0; i < cardCount; i++) {
            System.out.print("   c");
        }
        System.out.println();

        System.out.println(board1.deck.mainDeckSize());

        printSpells(board1);

        printMonsters(board1);

        System.out.print(board1.graveYard.size());
        System.out.print("                    ");
        if (board1.fieldZone != null) {
            System.out.println("O");
        } else {
            System.out.println("E");
        }
        System.out.println();

        System.out.println("--------------------------");

        System.out.println();

        System.out.print(board2.graveYard.size());
        System.out.print("                    ");
        if (board2.fieldZone != null) {
            System.out.println("O");
        } else {
            System.out.println("E");
        }

        printMonsters(board2);

        printSpells(board2);

        System.out.println(board2.deck.mainDeckSize());

        cardCount = 0;
        for (Cell cell : board2.hand) {
            cardCount++;
        }
        for (int i = 0; i < cardCount; i++) {
            System.out.print("   c");
        }
        System.out.println();

        System.out.println(board2.user.getNickname() + " : " + board2.lifePoint);
    }

    private static void printMonsters(Board board2) {
        for (Cell monsterCell : board2.monsterCells) {
            if (monsterCell == null) {
                System.out.print("   E");
            } else if (monsterCell.getState() == 1) {
                System.out.print("  DO");
            } else if (monsterCell.getState() == 2) {
                System.out.print("  DH");
            } else {
                System.out.print("  OO");
            }
        }
        System.out.println();
    }

    private static void printSpells(Board board2) {
        for (Cell spellCell : board2.spellCells) {
            if (spellCell == null) {
                System.out.print("   E");
            } else if (spellCell.getState() == 1) {
                System.out.print("   O");
            } else {
                System.out.print("   H");
            }
        }
        System.out.println();
    }
    // for now it just send data to controllers
    // later it will handle graphic
}