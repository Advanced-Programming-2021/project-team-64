package Models.Game;

import Models.User.Deck;
import Models.User.User;
import Models.Card.Card;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    public User user;
    public int lifePoint = 8000;
    public Deck deck = null;
    public Card fieldZone = null;
    public Cell[] spellCells = new Cell[5];
    public Cell[] monsterCells = new Cell[5];
    public ArrayList<Card> graveYard = new ArrayList<Card>();
    public Cell[] hand = new Cell[6];

    public Board(User user, Deck deck) {
        this.user = user;
        this.deck = deck;
    }


}
