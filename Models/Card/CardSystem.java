package Models.Card;

import Models.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CardSystem extends Model {
    static public ArrayList<Card> Cards = new ArrayList<>();

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
