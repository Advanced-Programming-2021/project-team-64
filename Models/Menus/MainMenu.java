package Models.Menus;

import Models.*;

public class MainMenu extends Model {
    public void logout() {
        currentUser = null;
        return;
    }
}
