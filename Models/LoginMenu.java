package Models;

public class LoginMenu extends Model {
    public String signup(String username, String nickname, String password) {
        if (existsUser(username)) {
            return "user with username " + username + "already exists";
        } else if (existsNickname(nickname)) {
            return "user with nickname " + nickname + "already exists";
        } else {
            addUser(username, nickname, password);
            currentUser = getUser(username, password);
            return "user created successfully!";
        }
    }

    public String login(String username, String password) {
        if (!(existsUser(username)) || !(matchPassword(username, password))) {
            return "Username and password didn’t match!";
        } else {
            currentUser = getUser(username, password);
            return "user logged in successfully!";
        }
    }
}
