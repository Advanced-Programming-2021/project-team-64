package Models.Game;

import Models.Card.Card;

public class Cell {
    private Card card = null;
    private int state;
    private int abilityUsageCount = 0;

    public Cell () {}

    public Cell(Card card, int state) {
        this.card = card;
        this.state = state;
    }

    public boolean hasCard() {
        return this.card != null;
    }

    public int cardLevel() {
        return this.card.getLevel();
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

    public void deleteCard () {
        this.card = null;
    }
}
