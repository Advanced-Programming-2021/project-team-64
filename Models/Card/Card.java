package Models.Card;

import Models.Model;
import com.google.gson.Gson;

import java.util.ArrayList;

public interface Card extends Comparable<Card> {
    String name = "";
    String attribute = "";
    String type = "";
    String description = "";
    Integer level = 0;
    Integer cardNumber = 0;
    Integer attack = 0;
    Integer defence = 0;

    default public boolean checkName (String cardName) {
        return this.name.equals(cardName);
    }

    default public int compareTo (Card o) {
        return this.toString().compareTo(o.toString());
    }

    @Override
    public String toString ();

    @Override
    public boolean equals (Object o);

    @Override
    public int hashCode ();
}
