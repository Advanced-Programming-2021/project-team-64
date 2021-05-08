package Models.Menus;

import Models.User.*;

public class DeckMenu extends Menu {
    DeckMenu() {
        this.name = "Deck Menu";
    }
    public String createDeck(String name) {
        if (currentUser.hasDeck(name)) {
            return "deck with name " + name + " already exists";
        }
        else {
            currentUser.createDeck(name);
        }
    }

    public String deleteDeck(String name) {
        if (!(currentUser.hasDeck(name))) {
            return "deck with name " + name + " does not exist";
        }
        else {
            currentUser.deleteDeck(name);
            return "deck deleted successfully";
        }
    }

    public String activateDeck(String name) {
        if(!(currentUser.hasDeck(name))) {
            return "deck with name " + name + "does not exist";
        }
        else {
            currentUser.setActiveDeck(name);
            return "deck activated successfully";
        }
    }

    public String addCardToMainDeck(String cardName, String deckName) {
        if(!(currentUser.hasDeck(deckName))) {
            return "deck with name " + deckName + "does not exist";
        }
        else if(!(currentUser.hasCard(cardName))) {
            return "card with name " + cardName + " does not exist";
        }
        //update needed
        else if(currentUser.getDeck(deckName).mainDeckSize() == 60) {
            return "main deck is full";
        }
        else if(currentUser.countOfCard(deckName, cardName) == 3) {
            return "there are already three cards with name " + cardName + "in deck " + deckName;
        }
        else {
            currentUser.addCardToMainDeck(cardName, deckName);
            return "card added to deck successfully";
        }
    }

    public String addCardToSideDeck(String cardName, String deckName) {
        if(!(currentUser.hasDeck(deckName))) {
            return "deck with name " + deckName + "does not exist";
        }
        else if(!(currentUser.hasCard(cardName))) {
            return "card with name " + cardName + " does not exist";
        }
        //update needed
        else if(currentUser.getDeck(deckName).sideDeckSize() == 15) {
            return "side deck is full";
        }
        else if(currentUser.countOfCard(deckName, cardName) == 3) {
            return "there are already three cards with name " + cardName + "in deck " + deckName;
        }
        else {
            currentUser.addCardToSideDeck(cardName, deckName);
            return "card added to deck successfully";
        }
    }

    public String removeCardFromMainDeck(String cardName, String deckName) {
        if(!(currentUser.hasDeck(deckName))) {
            return "deck with name " + deckName + "does not exist";
        }
        else if(!(currentUser.getDeck(deckName).mainHasCard(cardName))) {
            return "card with name " + cardName + " does not exist in main deck";
        }
        else {
            currentUser.removeCardFromMainDeck(cardName, deckName);
            return "card removed form deck successfully";
        }
    }

    public String removeCardFromSideDeck(String cardName, String deckName) {
        if(!(currentUser.hasDeck(deckName))) {
            return "deck with name " + deckName + "does not exist";
        }
        else if(!(currentUser.getDeck(deckName).sideHasCard(cardName))) {
            return "card with name " + cardName + " does not exist in side deck";
        }
        else {
            currentUser.removeCardFromSideDeck(cardName, deckName);
            return "card removed form deck successfully";
        }
    }
}
