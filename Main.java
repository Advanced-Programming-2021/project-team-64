import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import com.google.gson.Gson;

import Models.Card.Card;
import Models.Card.CardSystem;
import Models.Model;
import Views.View;
import Models.User.User;

public class Main {
    static ArrayList<Model> modules = new ArrayList<>();
    private View view = new View();

    static public void importFromDatabase () throws IOException {
        CardSystem.importFromFile();
    }

    static public void exportToDatabase () throws IOException {
        CardSystem.exportToFile();
    }

    public static void main(String[] args) throws IOException {
        CardSystem.importFromCsv();

        exportToDatabase();
    }
}