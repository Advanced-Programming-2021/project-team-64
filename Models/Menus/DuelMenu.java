package Models.Menus;

import Models.User.*;

public class DuelMenu extends Menu {
    DuelMenu() { this.name = "Duel Menu"; }

    public String newP2PGame(String player2Username, int rounds) {
        // TODO :
        // update
        User player2 = new User();
        if(!(User.hasUsername(player2Username))) {
            return "there is no player with this username";
        }
        else {
            player2 = User.getByUsername(player2Username);
        }

        if(!(currentUser.hasActiveDeck())) {
            return currentUser.getUsername() + " has no active deck";
        }
        else if(!(player2.hasActiveDeck())) {
            return player2.getUsername() + " has no active deck";
        }

        if(!(currentUser.activeDeckIsValid())) {
            return currentUser.getUsername() + "'s deck is invalid";
        }
        else if(!(player2.activeDeckIsValid())) {
            return player2.getUsername() + "'s deck is invalid";
        }

        if(!(rounds == 1 || rounds == 3)) {
            return "number of rounds is not supported";
        }

        // TODO :
        // START P2P GAME WITH PLAYER 2
        return "";
    }

    public String newP2AGame(int rounds) {
        if(!(currentUser.hasActiveDeck())) {
            return currentUser.getUsername() + " has no active deck";
        }

        if(!(currentUser.activeDeckIsValid())) {
            return currentUser.getUsername() + "'s deck is invalid";
        }

        if(!(rounds == 1 || rounds == 3)) {
            return "number of rounds is not supported";
        }

        // TODO :
        // START P2A GAME
        return "";
    }
}
