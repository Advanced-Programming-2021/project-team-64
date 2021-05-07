package Models.Card;

import Models.Model;

import java.util.ArrayList;

public interface Card {
    static public ArrayList<Card> Cards = new ArrayList<Card>();
    String name = "";
    String attribute = "";
    String type = "";
    String description = "";
    Integer level = 0;
    Integer cardNumber = 0;
    Integer attack = 0;
    Integer defence = 0;
}
