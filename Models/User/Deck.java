package Models.User;

import Models.Card.Card;
import Models.Model;

import java.util.ArrayList;

public class Deck extends Model {
    private String name;
    private ArrayList<Card> mainDeck = new ArrayList<>();
    private ArrayList<Card> sideDeck = new ArrayList<>();

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
}
