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

    public void setInput(String input) {
        controller.setOutput("invalid command");
        controller.recognizeInput(input);
    }

    public void getOutput() {
        System.out.println(controller.getOutput());
    }

    public static void main(String[] args) {
        View view = new View();
        String input = scanner.nextLine();
        while(!input.equals("end")) {
            view.setInput(input);
            view.getOutput();
            input = scanner.nextLine();
        }
    }
    // for now it just send data to controllers
    // later it will handle graphic
}