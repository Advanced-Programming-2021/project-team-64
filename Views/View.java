package Views;

import Controllers.Controller;

import java.util.Scanner;

public class View {
    public static Controller controller;
    public static Scanner scanner;

    static {
        controller = new Controller();
        scanner = new Scanner(System.in);
    }


    public void setInput() {
        String input = scanner.nextLine();
        controller.setInput(input);
    }

    public String getOutput() {
        return controller.getOutput();
    }
    // for now it just send data to controllers
    // later it will handle graphic
}