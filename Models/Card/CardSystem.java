package Models.Card;

import Models.Model;

import java.util.ArrayList;

public class CardSystem extends Model {
    static public ArrayList<Card> Cards = new ArrayList<Card>();

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

    static public boolean hasCard (String cardName) {
        return getCardCopy(cardName) != null;
    }
}
