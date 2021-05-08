package Models.User;

import Models.Card.Card;
import Models.Card.CardSystem;
import Models.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User extends Model {
    private static ArrayList<User> Users = new ArrayList<>();
    private ArrayList<Deck> decks = new ArrayList<>();
    private ArrayList<Card> cards = new ArrayList<>();
    private Deck activeDeck = null;
    private String username, password, nickname;
    private Integer score;

    public User (String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.score = 0;
    }

    public static ArrayList<User> getUsers () {
        return (ArrayList<User>)Users.clone();
    }

    public static User createUser (String username, String password, String nickname) {
        User user = new User(username, password, nickname);
        Users.add(user);
        return user;
    }

    public static User getByUsername (String username) {
        for (int i = 0; i < Users.size(); i++) {
            User user = Users.get(i);
            if (user.username.equals(username))
                return user;
        }
        return null;
    }

    public static User getByNickname (String nickname) {
        for (int i = 0; i < Users.size(); i++) {
            User user = Users.get(i);
            if (user.nickname.equals(nickname))
                return user;
        }
        return null;
    }

    static public boolean hasUsername (String username) {
        return getByUsername(username) != null;
    }

    static public boolean hasNickname (String nickname) {
        return getByNickname(nickname) != null;
    }

    public static boolean checkPassword (String username, String password) {
        User user = getByUsername(username);
        return ((user != null) && user.checkPassword(password));
    }

    public boolean checkPassword (String password) {
        return this.password.equals(password);
    }

    public Integer getScore () {
        return this.score;
    }

    public void changePassword (String password) {
        this.password = password;
    }

    public void changeNickname (String nickname) {
        this.nickname = nickname;
    }

    public void createDeck (String deckName) {
        this.decks.add(new Deck(deckName));
    }

    public void deleteDeck (String deckName) {
        Deck deck = this.getDeck(deckName);
        for (Card card: deck.allCards())
            this.cards.add(card);
        this.decks.remove(deck);
    }

    public void setActiveDeck (String deckName) {
        this.activeDeck = this.getDeck(deckName);
    }

    public Deck getDeck (String deckName) {
        for (int i = 0; i < this.decks.size(); i++)
            if (this.decks.get(i).checkName(deckName))
                return this.decks.get(i);
        return null;
    }

    public boolean hasDeck (String deckName) {
        return this.getDeck(deckName) != null;
    }

    public void addCardToMainDeck (String cardName, String deckName) {
        Card card = CardSystem.getCardCopy(cardName);
        this.cards.remove(card);
        this.getDeck(deckName).addCardToMainDeck(card);
    }

    public void addCardToSideDeck (String cardName, String deckName) {
        Card card = CardSystem.getCardCopy(cardName);
        this.cards.remove(card);
        this.getDeck(deckName).addCardToSideDeck(card);
    }

    public void removeCardFromMainDeck (String cardName, String deckName) {
        Card card = CardSystem.getCardCopy(cardName);
        this.getDeck(deckName).removeCardFromMainDeck(card);
        this.cards.add(card);
    }

    public void removeCardFromSideDeck (String cardName, String deckName) {
        Card card = CardSystem.getCardCopy(cardName);
        this.getDeck(deckName).removeCardFromSideDeck(card);
        this.cards.add(card);
    }

    public int countOfCard (String deckName, String cardName) {
        return this.getDeck(deckName).countOfCard(cardName);
    }
}
