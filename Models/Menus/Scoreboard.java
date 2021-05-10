package Models.Menus;

import Models.Model;
import Models.User.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

public class Scoreboard extends Menu {
    Scoreboard() {
        this.name = "Scoreboard";
    }

    public ArrayList<User> showScoreboard() {
        ArrayList<User> ranking = User.getUsers();
        Collections.sort(ranking, Comparator.comparing(User::getScore));
        return ranking;
    }
}
