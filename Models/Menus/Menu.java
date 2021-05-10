package Models.Menus;

import Models.Model;
import Models.User.*;

public class Menu extends Model {
    static public User currentUser = null;
    static protected String name = "";

    public void login(User user) {
        currentUser = user;
    }

    public void logout() {
        currentUser = null;
    }

    public String showCurrent() {
        return this.name;
    }

}
