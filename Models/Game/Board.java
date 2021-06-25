package Models.Game;

import Models.User.Deck;
import Models.User.User;
import Models.Card.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Board {
    public User user;
    public int lifePoint = 8000;
    public Deck deck = null;
    public Card fieldZone = null;
    public Cell[] spellCells = new Cell[5];
    public Cell[] monsterCells = new Cell[5];
    public ArrayList<Card> graveYard = new ArrayList<Card>();
    private Cell[] allCells = new Cell[10];
    public Cell[] hand = new Cell[6];

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

    public boolean hasCardInHand (String name) {
        for (Cell cell: this.hand)
            if (cell.hasCard() && cell.getCard().checkName(name))
                return true;
        return false;
    }

    public void deleteCardFromHandByName (String name) {
        for (Cell cell: this.hand)
            if (cell.hasCard() && cell.getCard().checkName(name))
                cell.deleteCard();
    }

    public void deleteRandomCardFromHand () {
        int cnt = this.countOfCardsInHand();
        if (cnt == 0)
            return;
        int index = (new Random()).nextInt(cnt);
        for (Cell cell: this.hand)
            if (cell.hasCard()) {
                if (index == 0) {
                    cell.deleteCard();
                    return;
                }
                index--;
            }
    }

    public int countOfCardsInHand () {
        int cnt = 0;
        for (Cell cell: this.hand)
            if (cell.hasCard())
                cnt++;
        return cnt;
    }
}
