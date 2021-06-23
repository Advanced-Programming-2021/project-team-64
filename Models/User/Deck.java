package Models.User;

import Models.Card.Card;
import Models.Card.CardSystem;
import Models.Model;

import java.util.ArrayList;

public class Deck extends Model {
    private String name;
    public ArrayList<Card> mainDeck = new ArrayList<>();
    public ArrayList<Card> sideDeck = new ArrayList<>();

    Deck (String name) {
        this.name = name;
    }

    public boolean checkName (String name) {
        return this.name.equals(name);
    }

    public void addCardToMainDeck (Card card) {
        this.mainDeck.add(card);
    }

    public void addCardToSideDeck (Card card) {
        this.sideDeck.add(card);
    }

    public void removeCardFromMainDeck (Card card) {
        this.mainDeck.remove(card);
    }

    public void removeCardFromSideDeck (Card card) {
        this.sideDeck.remove(card);
    }

    public int mainDeckSize () {
        return this.mainDeck.size();
    }

    public int sideDeckSize () {
        return this.sideDeck.size();
    }

    public ArrayList<Card> allCards () {
        ArrayList<Card> res = new ArrayList<>(this.mainDeck);
        res.addAll(sideDeck);
        return res;
    }

    public int countOfCard (String name) {
        int cnt = 0;
        for (Card card: this.allCards())
            if (card.checkName(name))
                cnt++;
        return cnt;
    }

    public boolean mainHasCard (String name) {
        for (Card card: this.mainDeck)
            if (card.checkName(name))
                return true;
        return false;
    }

    public boolean sideHasCard (String name) {
        for (Card card: this.sideDeck)
            if (card.checkName(name))
                return true;
        return false;
    }

    public boolean isValid () {
        return this.mainDeck.size() >= 40;
    }
}
