package Models.Menus;

import Models.Model;
import Models.User.User;

public class Menu extends Model {
    static private User currentUser = null;

    public void login (User user) {
        currentUser = user;
    }

    public void logout () {
        currentUser = null;
    }
}
