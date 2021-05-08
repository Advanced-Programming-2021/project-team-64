package Models;

import com.google.gson.*;

public class Model implements Comparable<Model> {
    // here we will have all common functions for models
    // like importing and exporting datas and etc.
    // static functions for database actions


    @Override
    public int compareTo (Model o) {
        return this.toString().compareTo(o.toString());
    }

    @Override
    public String toString () {
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
    public int hashCode () {
        return (new Gson()).toJson(this).hashCode();
    }
}