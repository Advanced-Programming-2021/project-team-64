package Models.Card;

import Models.Model;
import com.google.gson.Gson;

public class Spell implements Card {
    @Override
    public String toString() {
        return (new Gson()).toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Model))
            return false;

        Model c = (Model) o;
        return this.hashCode() == c.hashCode();
    }

    @Override
    public int hashCode() {
        return (new Gson()).toJson(this).hashCode();
    }
}
