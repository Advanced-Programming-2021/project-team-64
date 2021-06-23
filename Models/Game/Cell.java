package Models.Game;

import Models.Card.Card;

public class Cell {
    private Card card;
    private int state;

    public Cell(Card card, int state) {
        this.card = card;
        this.state = state;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
