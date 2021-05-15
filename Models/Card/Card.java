package Models.Card;

public interface Card extends Comparable<Card> {
    String name = "";
    String attribute = "";
    String type = "";
    String description = "";
    Integer cost = 0;
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

    default public String getName () {
        return this.name;
    }

    default public Integer getCost () {
        return this.cost;
    }

    @Override
    public String toString ();

    @Override
    public boolean equals (Object o);

    @Override
    public int hashCode ();
}
