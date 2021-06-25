package Models.User;

import Database.Database;
import Models.Card.Card;
import Models.Card.CardSystem;
import Models.Model;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class User extends Model {
    static String filePath = "Database/user.txt";
    private static ArrayList<User> Users = new ArrayList<>();
    private ArrayList<Deck> decks = new ArrayList<>();
    private ArrayList<Card> cards = new ArrayList<>();
    public Deck activeDeck = null;
    private String username;
    private String password;

    private String nickname;
    private Integer score, money;

    static public void exportToFile () throws IOException {
        Database.writeToFile(filePath, new Gson().toJson(Users));
    }

    static public void importFromFile () throws IOException {
        Users = new Gson().fromJson(Database.fileToString(filePath), Users.getClass());
    }

    public User () {}

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.score = 0;
        this.money = 0;
    }

    public static ArrayList<User> getUsers() {
        return (ArrayList<User>) Users.clone();
    }

    public static User createUser(String username, String password, String nickname) {
        User user = new User(username, password, nickname);
        Users.add(user);
        return user;
    }

    public static User getByUsername(String username) {
        for (int i = 0; i < Users.size(); i++) {
            User user = Users.get(i);
            if (user.username.equals(username))
                return user;
        }
        return null;
    }

    public static User getByNickname(String nickname) {
        for (int i = 0; i < Users.size(); i++) {
            User user = Users.get(i);
            if (user.nickname.equals(nickname))
                return user;
        }
        return null;
    }

    public String getNickname() {
        return this.nickname;
    }

    static public boolean hasUsername(String username) {
        return getByUsername(username) != null;
    }

    static public boolean hasNickname(String nickname) {
        return getByNickname(nickname) != null;
    }

    public static boolean checkPassword(String username, String password) {
        User user = getByUsername(username);
        return ((user != null) && user.checkPassword(password));
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public String getUsername () {
        return this.username;
    }

    public Integer getScore() {
        return this.score;
    }

    public Integer getMoney () {
        return this.money;
    }

    public void increaseMoney (int delta) {
        this.money += delta;
    }

    public void decreaseMoney (int delta) {
        this.money -= delta;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void createDeck(String deckName) {
        this.decks.add(new Deck(deckName));
    }

    public void deleteDeck (String deckName) {
        Deck deck = this.getDeck(deckName);
        for (Card card: deck.allCards())
            this.cards.add(card);
        this.decks.remove(deck);
    }

    public void setActiveDeck(String deckName) {
        this.activeDeck = this.getDeck(deckName);
    }

    public Deck getDeck(String deckName) {
        for (int i = 0; i < this.decks.size(); i++)
            if (this.decks.get(i).checkName(deckName))
                return this.decks.get(i);
        return null;
    }

    public boolean hasDeck(String deckName) {
        return this.getDeck(deckName) != null;
    }

    public void addCard (String cardName) {
        this.cards.add(CardSystem.getCardCopy(cardName));
    }

    public void addCardToMainDeck(String cardName, String deckName) {
        Card card = CardSystem.getCardCopy(cardName);
        this.cards.remove(card);
        this.getDeck(deckName).addCardToMainDeck(card);
    }

    public void addCardToSideDeck(String cardName, String deckName) {
        Card card = CardSystem.getCardCopy(cardName);
        this.cards.remove(card);
        this.getDeck(deckName).addCardToSideDeck(card);
    }

    public void removeCardFromMainDeck(String cardName, String deckName) {
        Card card = CardSystem.getCardCopy(cardName);
        this.getDeck(deckName).removeCardFromMainDeck(card);
        this.cards.add(card);
    }

    public void removeCardFromSideDeck(String cardName, String deckName) {
        Card card = CardSystem.getCardCopy(cardName);
        this.getDeck(deckName).removeCardFromSideDeck(card);
        this.cards.add(card);
    }

    public int countOfCard(String deckName, String cardName) {
        return this.getDeck(deckName).countOfCard(cardName);
    }

    public boolean hasCard (String name) {
        for (Card card: this.cards)
            if (card.checkName(name))
                return true;
        return false;
    }

    public boolean hasActiveDeck () {
        return (this.activeDeck != null);
    }

    public boolean activeDeckIsValid () {
        return (this.activeDeck != null && this.activeDeck.isValid());
    }

    public void showAllDecks () {
        System.out.print("Decks:\nActive deck:\n");
        if (hasActiveDeck())
            this.activeDeck.showShortData();
        System.out.println("Other decks:");
        for (Deck deck: decks)
            if (deck != activeDeck)
                deck.showShortData();
    }
}
