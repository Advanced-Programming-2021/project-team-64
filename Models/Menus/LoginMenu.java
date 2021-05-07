package Models.Menus;

import Models.Model;
import Models.User.*;

public class LoginMenu extends Menu {
    public String signup(String username, String nickname, String password) {
        if (User.hasUsername(username)) {
            return "user with username " + username + "already exists";
        } else if (User.hasNickname(nickname)) {
            return "user with nickname " + nickname + "already exists";
        } else {
            login(User.createUser(username, password, nickname));
            return "user created successfully!";
        }
    }

    public String login(String username, String password) {
        if (!(User.hasUsername(username)) || !(User.checkPassword(username, password))) {
            return "Username and password didn’t match!";
        } else {
            login(User.getByUsername(username));
            return "user logged in successfully!";
        }
    }
}
