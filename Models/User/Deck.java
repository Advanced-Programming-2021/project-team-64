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

    public void showShortData () {
        System.out.printf("%s: main %d, side %d, %s\n",
                this.name, this.mainDeckSize(), this.sideDeckSize(), this.isValid()? "valid": "invalid");
    }

    public void showADeck (String type, ArrayList<Card> deck) {
        System.out.printf("Deck: %s\n%s deck:\nMonsters:\n", name, type);
        for (Card card: deck)
            if (card.isMonster())
                card.showString();
        System.out.println("Spell and Traps:");
        for (Card card: deck)
            if (!card.isMonster())
                card.showString();
    }

    public void showMainDeck () {
        showADeck("Main", mainDeck);
    }

    public void showSideDeck () {
        showADeck("Side", sideDeck);
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
