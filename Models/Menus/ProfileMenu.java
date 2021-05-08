package Models.Menus;

import Models.User.*;

public class ProfileMenu extends Menu {
    ProfileMenu() {
        this.name = "Profile Menu";
    }
    public String changeNickname(String nickname) {
        if (User.hasNickname(nickname)) {
            return "user with nickname " + nickname + " already exists";
        }
        else {
            currentUser.changeNickname(nickname);
            return "nickname changed successfully!";
        }
    }

    public String changePassword(String currentPassword, String newPassword) {
        if(!(currentUser.checkPassword(currentPassword))) {
            return "current password is invalid";
        }
        else if(currentUser.checkPassword(newPassword)){
            return "please enter a new password";
        }
        else {
            currentUser.changePassword(newPassword);
            return "password changed successfully!";
        }
    }
}
