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
    private ArrayList<Card> graveYard = new ArrayList<Card>();

    public Board(User user, Deck deck) {
        this.user = user;
        this.deck = deck;
    }


}
