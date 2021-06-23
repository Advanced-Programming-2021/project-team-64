package Models.Game;

import Models.User.Deck;
import Models.User.User;
import Models.Card.Card;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private User user;
    private int lifePoint = 8000;
    private Deck deck = null;
    private Card fieldZone = null;
    private Cell[] spellCells = new Cell[5];
    private Cell[] monsterCells = new Cell[5];
    private Cell[] allCells = new Cell[10];
    private ArrayList<Card> graveYard = new ArrayList<Card>();

    public Board(User user, Deck deck) {
        this.user = user;
        this.deck = deck;
        for (int i = 0; i < 5; i++) {
            allCells[i] = spellCells[i];
            allCells[5 + i] = monsterCells[i];
        }
    }

    public int sumOfCardsLevels() {
        int s = 0;
        for (int i = 0; i < 10; i++)
            if (allCells[i].hasCard())
                s += allCells[i].cardLevel();
        return s;
    }

    public ArrayList<Card> getCardsOfTypes(String[] types) {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (allCells[i].hasCard())
                for (String type: types)
                    if (allCells[i].hasCard() && allCells[i].getCard().checkType(type))
                        cards.add(allCells[i].getCard());
        }
        return cards;
    }
}
