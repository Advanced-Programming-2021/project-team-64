package Models.Card;

import Database.Database;
import Models.Model;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CardSystem extends Model {
    static String filePath = "Database/card-system.txt";
    static String monstersCsvFilePath = "Database/Monster.csv";
    static String spellTrapCsvFilePath = "Database/SpellTrap.csv";
    static public ArrayList<Card> Cards = new ArrayList<>();

    static public void exportToFile () throws IOException {
        Database.writeToFile(filePath, new Gson().toJson(Cards));
    }

    static public void importFromFile () throws IOException {
        Cards = new Gson().fromJson(Database.fileToString(filePath), Cards.getClass());
    }

    static private void importFromMonsterCsvFile () throws IOException {
        ArrayList<ArrayList<String>> csv = Database.csvToArrayList(monstersCsvFilePath);

        for (int i = 1; i < csv.size(); i++) {
            ArrayList<String> row = csv.get(i);
            Cards.add(new Card(
                    row.get(0),
                    row.get(2),
                    "Monster",
                    row.get(3),
                    row.get(4),
                    row.get(7),
                    "",
                    Integer.parseInt(row.get(8)),
                    Integer.parseInt(row.get(1)),
                    Integer.parseInt(row.get(5)),
                    Integer.parseInt(row.get(6))
            ));
        }
    }

    static private void importFromSpellTrapCsvFile () throws IOException {
        ArrayList<ArrayList<String>> csv = Database.csvToArrayList(spellTrapCsvFilePath);

        for (int i = 1; i < csv.size(); i++) {
            ArrayList<String> row = csv.get(i);
            Cards.add(new Card(
                    row.get(0),
                    "",
                    row.get(1),
                    row.get(2),
                    row.get(2),
                    row.get(3),
                    row.get(4),
                    Integer.parseInt(row.get(5)),
                    1,
                    0,
                    0
            ));
        }
    }

    static public void importFromCsv () throws IOException {
        importFromMonsterCsvFile();
        importFromSpellTrapCsvFile();
    }

    static public Card getCardCopy (String cardName) {
        for (Card card: Cards)
            if (card.checkName(cardName))
                return card;
        return null;
    }


    static public Card getCard (String cardName) {
        for (int i = 0; i < Cards.size(); i++)
            if (Cards.get(i).checkName(cardName))
                return Cards.get(i);
        return null;
    }

    static public int getCardCost (String cardName) {
        return getCard(cardName).getCost();
    }

    static public boolean hasCard (String cardName) {
        return getCardCopy(cardName) != null;
    }

    static public ArrayList<Card> getCards () {
        ArrayList<Card> cards = (ArrayList<Card>) Cards.clone();
        return cards;
    }

    static public ArrayList<Card> getCardsSortedByName () {
        ArrayList<Card> cards = (ArrayList<Card>) Cards.clone();
        Collections.sort(cards, Comparator.comparing(Card::getName));
        return cards;
    }
}
