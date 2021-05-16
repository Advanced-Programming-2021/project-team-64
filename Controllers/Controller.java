package Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Models.Menus.*;
import Models.User.User;

public class Controller {
    private String input;
    private String output;
    public Place place = Place.LOGINMENU;
    private static HashMap<String, String> map = new HashMap<String, String>();

    private static Menu Menu;
    private static DeckMenu DeckMenu;
    private static DuelMenu DuelMenu;
    private static LoginMenu LoginMenu;
    private static MainMenu MainMenu;
    private static ProfileMenu ProfileMenu;
    private static Scoreboard Scoreboard;
    private static ShopMenu ShopMenu;

    static {
        map.put("--username", null);
        map.put("--nickname", null);
        map.put("--password", null);
        map.put("--current", null);
        map.put("--new", null);
        map.put("--card", null);
        map.put("--deck", null);
        map.put("--deck-name", null);
        map.put("--monster", null);
        map.put("--rounds", null);
        map.put("--second-player", null);

        Menu = new Menu();
        DeckMenu = new DeckMenu();
        DuelMenu = new DuelMenu();
        LoginMenu = new LoginMenu();
        MainMenu = new MainMenu();
        ProfileMenu = new ProfileMenu();
        Scoreboard = new Scoreboard();
        ShopMenu = new ShopMenu();
    }


    public void setInput(String input) {
        this.output = "Invalid Command!";
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void updateMap(String input) {
        String[] inputParts = input.split(" ");
        ArrayList<Integer> indexList = new ArrayList<>();
        ArrayList<StringBuilder> inputList = new ArrayList<>();
        for (int i = 0; i < inputParts.length; i++) {
            if (checkWord("--.+", inputParts[i])) {
                indexList.add(i);
            }
        }
        indexList.add(inputParts.length);
        for (int i = 0; i < indexList.size() - 1; i++) {
            StringBuilder tmpString = new StringBuilder();
            for (int j = indexList.get(i) + 1; j < indexList.get(i + 1); j++) {
                tmpString.append(inputParts[j]);
            }
            inputList.add(tmpString);
            map.put(inputParts[i], tmpString.toString());
        }
    }

    public String changePlace(String destination) {
        if (this.place != Place.MAINMENU) return "menu navigation is not possible";
        switch (destination) {
            case "Deck Menu":
                this.place = Place.DECKMENU;
                return "Entered Deck Menu";
            case "Duel Menu":
                this.place = Place.DUELMENU;
                return "Entered Duel Menu";
            case "Profile Menu":
                this.place = Place.PROFILEMENU;
                return "Entered Profile Menu";
            case "Scoreboard":
                this.place = Place.SCOREBOARD;
                return "Entered Scoreboard";
            case "Shop Menu":
                this.place = Place.SHOPMENU;
                return "Entered Shop Menu";
            default:
                return "Invalid Menu Name";
        }
    }

    public String inputCatcher(String input) {
        Pattern pattern1 = Pattern.compile("(--.+)");
        Matcher matcher1 = pattern1.matcher(input);
        return matcher1.group(0);
    }

    public boolean checkWord(String check, String input) {
        return Pattern.matches(check, input);
    }

    public void recognizeInput() {
        String input = this.input;

        // Show Current Menu Method
        if (checkWord("menu show-current", input)) {
            output = Menu.showCurrent();
        }

        // Changing Menu
        Pattern pattern = Pattern.compile("menu enter (.+)");
        Matcher matcher = pattern.matcher(input);
        boolean isChangingMenu = matcher.find();
        if (isChangingMenu && this.place == Place.MAINMENU) {
            String destination = matcher.group(0);
            output = changePlace(destination);
        }
        if (checkWord("menu exit", input)) {
            if (this.place == Place.MAINMENU) {
                this.place = Place.LOGINMENU;
                Menu.logout();
                output = "User Logged Out SuccessFully\nEntered Login Menu";
            } else if (this.place == Place.LOGINMENU) {
                System.exit(0);
            } else {
                this.place = Place.MAINMENU;
                output = "Entered Main Menu";
            }
        }

        if (this.place == Place.LOGINMENU) {
            recognizeLoginMenu(input);
        } else if (this.place == Place.MAINMENU) {
            recognizeMainMenu(input);
        } else if (this.place == Place.SCOREBOARD) {
            recognizeScoreboard(input);
        } else if (this.place == Place.PROFILEMENU) {
            recognizeProfileMenu(input);
        } else if (this.place == Place.DECKMENU) {
            recognizeDeckMenu(input);
        }
    }

    public void recognizeLoginMenu(String input) {
        output = "please login first";
        if (checkWord("user create", input)) {
            updateMap(inputCatcher(input));
            output = LoginMenu.signup(map.get("--username"), map.get("--nickname"), map.get("--password"));
        } else if (checkWord("user login", input)) {
            updateMap(inputCatcher(input));
            output = LoginMenu.login(map.get("--username"), map.get("--password"));
        }
    }

    public void recognizeMainMenu(String input) {
        if (checkWord("user logout", input)) {
            output = Menu.logout();
        }
    }

    public void recognizeScoreboard(String input) {
        if (checkWord("scoreboard show", input)) {
            ArrayList<User> ranking = Scoreboard.showScoreboard();
            for (int i = 0; i < ranking.size(); i++) {
                StringBuilder player = new StringBuilder();
                player.append(i);
                player.append("- ");
                player.append(ranking.get(i).getNickname());
                player.append(": ");
                player.append(ranking.get(i).getScore());
                System.out.println(player);
            }
            output = "";
        }
    }

    public void recognizeProfileMenu(String input) {
        if (checkWord("profile change --nickname", input)) {
            updateMap(inputCatcher(input));
            output = ProfileMenu.changeNickname(map.get("--nickname"));
        } else if (checkWord("profile change", input)) {
            updateMap(inputCatcher(input));
            output = ProfileMenu.changePassword(map.get("--current"), map.get("--new"));
        }
    }

    public void recognizeDeckMenu(String input) {
        if (checkWord("deck create", input)) {
            if (Pattern.compile("deck create (.+)").matcher(input).find()) {
                String deckName = Pattern.compile("deck create (.+)").matcher(input).group(0);
                output = DeckMenu.createDeck(deckName);
            } else {
                output = "please enter a valid deck name";
            }
        } else if (checkWord("deck delete", input)) {
            if (Pattern.compile("deck delete (.+)").matcher(input).find()) {
                String deckName = Pattern.compile("deck delete (.+)").matcher(input).group(0);
                output = DeckMenu.deleteDeck(deckName);
            } else {
                output = "please enter a valid deck name";
            }
        }
        else if (checkWord("deck set-activate", input)) {
            if (Pattern.compile("deck set-activate (.+)").matcher(input).find()) {
                String deckName = Pattern.compile("deck set-activate (.+)").matcher(input).group(0);
                output = DeckMenu.activateDeck(deckName);
            } else {
                output = "please enter a valid deck name";
            }
        }
    }

    /*
     here we will have all common functions for controllers
     like the functions for handling regex's that arrangement of inputs is not important for them
     or have optimal inputs
     we will handle them all in controller and use them in other controllers
    */
}