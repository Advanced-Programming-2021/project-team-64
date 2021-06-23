package Models.Game;

import Models.Card.Card;

public class Cell {
    private Card card;
    private int state;

    public Cell(Card card, int state) {
        this.card = card;
        this.state = state;
    }
}
