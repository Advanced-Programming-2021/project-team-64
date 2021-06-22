import Controllers.Controller;
import Models.Card.Card;
import Models.Card.Monster;
import Models.Model;

import Views.View;
import java.util.ArrayList;
import java.util.Scanner;

import Models.Model;
import Models.User.*;
import Models.User.User;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Main {
    static ArrayList<Model> modules = new ArrayList<>();
    private View view = new View();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // read data from database
        Gson g = new Gson();
        User a = new User("mahdi", "pass", "nick");
        User b = new User("mahdi", "pass", "nick");
        User c = new User("mahdi", "pass", "nick");
        System.out.printf("%d %d %d\n", a.compareTo(b), a.compareTo(c), b.compareTo(c));
        System.out.printf("%b %b %b\n", a.equals(b), a.equals(c), b.equals(c));

        Card A = new Monster();
        Card B = new Monster();
        Card C = new Monster();
        System.out.printf("%d %d %d\n", A.hashCode(), B.hashCode(), C.hashCode());
        System.out.printf("%d %d %d\n", A.compareTo(B), A.compareTo(C), B.compareTo(C));
        System.out.printf("%b %b %b\n", A.equals(B), A.equals(C), B.equals(C));
        // write data to database
    }
}