package Models.Menus;

import Models.Model;
import Models.User.*;
import Models.Card.*;

import java.util.ArrayList;

public class ShopMenu extends Menu {
    ShopMenu() {
        this.name = "Shop Menu";
    }

    public String shopBuy(String cardName) {
        if (!(currentUser.hasCard(cardName))) {
            return "there is no card with this name";
        } else if (currentUser.getMoney() < CardSystem.getCardCost(cardName)) {
            return "not enough money";
        } else {
            currentUser.addCard(cardName);
            return "purchase successful!";
        }
    }

    public ArrayList<String> showAllCards() {
        ArrayList<Card> cards = CardSystem.getCardsSortedByName();
        ArrayList<String> shopList = new ArrayList<>();
        for (Card card : cards)
            shopList.add(card.getName() + ":" + card.getCost());
        return shopList;
    }
}
