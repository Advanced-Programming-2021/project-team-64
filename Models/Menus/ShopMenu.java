package Models.Menus;

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
        }
        else if (currentUser.getMoney() < getCardCost(cardName)) {
            return "not enough money";
        }
        else {
            currentUser.addToFreeCards(cardName);
            return "purchase successful!";
        }
    }

    public ArrayList<String> showAllCards() {
        ArrayList<String> cards = getAllCards();
        ArrayList<String> shopList = new ArrayList<>();
        for (String card : cards) {
            int cost = getCardCost(card);
            String tmp = card + ":" + cost;
            shopList.add(tmp);
        }
        return shopList;
    }
}
