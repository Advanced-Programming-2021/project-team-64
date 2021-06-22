package Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Models.Menus.*;
import Models.User.User;

public class Controller {
    private String output;
    public Place place = Place.LOGINMENU;
    private static HashMap<String, String> map = new HashMap<>();

    private static final Menu Menu;
    private static final DeckMenu DeckMenu;
    private static final DuelMenu DuelMenu;
    private static final LoginMenu LoginMenu;
    private static final MainMenu MainMenu;
    private static final ProfileMenu ProfileMenu;
    private static final Scoreboard Scoreboard;
    private static final ShopMenu ShopMenu;

    static {
        map.put("--username", "");
        map.put("--nickname", "");
        map.put("--password", "");
        map.put("--current", "");
        map.put("--new", "");
        map.put("--card", "");
        map.put("--deck", "");
        map.put("--deck-name", "");
        map.put("--monster", "");
        map.put("--rounds", "");
        map.put("--second-player", "");

        Menu = new Menu();
        DeckMenu = new DeckMenu();
        DuelMenu = new DuelMenu();
        LoginMenu = new LoginMenu();
        MainMenu = new MainMenu();
        ProfileMenu = new ProfileMenu();
        Scoreboard = new Scoreboard();
        ShopMenu = new ShopMenu();
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }

    public void updateMap(String input) {
        String[] inputParts = input.split(" ");
        ArrayList<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < inputParts.length; i++) {
            if (checkWord("--", inputParts[i])) {
                indexList.add(i);
            }
        }
        indexList.add(inputParts.length);
        for (int i = 0; i < indexList.size() - 1; i++) {
            StringBuilder tmpString = new StringBuilder();
            for (int j = indexList.get(i) + 1; j < indexList.get(i + 1); j++) {
                tmpString.append(inputParts[j]);
                if (!(j == indexList.get(i + 1) - 1)) {
                    tmpString.append(" ");
                }
            }
            map.put(inputParts[indexList.get(i)], tmpString.toString());
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

    public String inputCatcher(String input, String startsWith) {
        String reg = startsWith + "(--.+)";
        Pattern pattern1 = Pattern.compile(reg);
        Matcher matcher1 = pattern1.matcher(input);
        matcher1.find();
        return matcher1.group(1);
    }

    public boolean checkWord(String check, String input) {
        check = check + ".*";
        return Pattern.matches(check, input);
    }

    public boolean stringExists(String input, String check) {
        String[] tmp = input.split(" ");
        for (String s : tmp) {
            if (s.equals(check)) {
                return true;
            }
        }
        return false;
    }

    public void recognizeInput(String input) {

        // Show Current Menu Method
        if (checkWord("menu show-current", input)) {
            switch (this.place) {
                case MAINMENU -> output = "Main Menu";
                case DUELMENU -> output = "Duel Menu";
                case DECKMENU -> output = "Deck Menu";
                case LOGINMENU -> output = "Login Menu";
                case PROFILEMENU -> output = "Profile Menu";
                case SCOREBOARD -> output = "Scoreboard";
                case SHOPMENU -> output = "Shop Menu";
            }
        }

        // Changing Menu

        else {
            switch (this.place) {
                case LOGINMENU -> recognizeLoginMenu(input);
                case MAINMENU -> recognizeMainMenu(input);
                case SCOREBOARD -> recognizeScoreboard(input);
                case PROFILEMENU -> recognizeProfileMenu(input);
                case DECKMENU -> recognizeDeckMenu(input);
                case SHOPMENU -> recognizeShopMenu(input);
                case DUELMENU -> recognizeDuelMenu(input);
            }
        }
    }

    public void checkMenuExit(String input) {
        if (checkWord("menu exit", input)) {
            if (this.place != Place.MAINMENU) {
                if (this.place == Place.LOGINMENU) {
                    System.exit(0);
                } else {
                    this.place = Place.MAINMENU;
                    output = "Entered Main Menu";
                }
            } else {
                this.place = Place.LOGINMENU;
                output = Menu.logout();
                output = "User Logged Out SuccessFully\nEntered Login Menu";
            }
        }
    }

    // user create --nickname sepehrv --username sepehr --password 12345
    public void recognizeLoginMenu(String input) {
        checkMenuExit(input);
        output = "please login first";
        if (checkWord("user create", input)) {
            updateMap(inputCatcher(input, "user create "));
            output = LoginMenu.signup(map.get("--username"), map.get("--nickname"), map.get("--password"));
            if (output.equals("user created successfully!")) {
                this.place = Place.MAINMENU;
            }
        } else if (checkWord("user login", input)) {
            updateMap(inputCatcher(input, "user login "));
            output = LoginMenu.login(map.get("--username"), map.get("--password"));
            if (output.equals("user logged in successfully!")) {
                this.place = Place.MAINMENU;
            }
        }
    }

    public void recognizeMainMenu(String input) {
        checkMenuExit(input);
        if (checkWord("user logout", input)) {
            this.place = Place.LOGINMENU;
            output = Menu.logout();
        }
        Pattern pattern = Pattern.compile("menu enter (.+)");
        Matcher matcher = pattern.matcher(input);
        boolean isChangingMenu = matcher.find();
        if (isChangingMenu) {
            String destination = matcher.group(1);
            output = changePlace(destination);
        }

    }

    public void recognizeScoreboard(String input) {
        checkMenuExit(input);
        if (checkWord("scoreboard show", input)) {
            ArrayList<User> ranking = Scoreboard.showScoreboard();
            for (int i = 0; i < ranking.size(); i++) {
                StringBuilder player = new StringBuilder();
                player.append(i + 1);
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
        checkMenuExit(input);
        if (checkWord("profile change --nickname", input)) {
            updateMap(inputCatcher(input, "profile change "));
            output = ProfileMenu.changeNickname(map.get("--nickname"));
        } else if (checkWord("profile change", input)) {
            updateMap(inputCatcher(input, "profile change "));
            output = ProfileMenu.changePassword(map.get("--current"), map.get("--new"));
        }
    }

    private boolean validInput(String check, String input) {
        check = check + " (.+)";
        Pattern pattern = Pattern.compile(check);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    private String getNames(String check, String input) {
        check = check + " (.+)";
        Pattern pattern = Pattern.compile(check);
        Matcher matcher = pattern.matcher(input);
        matcher.find();
        return matcher.group(1);
    }

    public void recognizeDeckMenu(String input) {
        checkMenuExit(input);
        if (checkWord("deck create", input)) {
            Pattern pattern = Pattern.compile("deck create (.+)");
            Matcher matcher = pattern.matcher(input);
            if (validInput("deck create", input)) {
                String deckName = getNames("deck create", input);
                output = DeckMenu.createDeck(deckName);
            } else {
                output = "please enter a valid deck name";
            }
        } else if (checkWord("deck delete", input)) {
            if (validInput("deck delete", input)) {
                String deckName = getNames("deck delete", input);
                output = DeckMenu.deleteDeck(deckName);
            } else {
                output = "please enter a valid deck name";
            }
        } else if (checkWord("deck set-activate", input)) {
            if (validInput("deck set-activate", input)) {
                String deckName = getNames("deck set-activate", input);
                output = DeckMenu.activateDeck(deckName);
            } else {
                output = "please enter a valid deck name";
            }
        } else if (checkWord("deck add-card", input)) {
            if (validInput("deck add-card", input)) {
                String tmp = getNames("deck add-card", input);
                updateMap(inputCatcher(tmp, ""));
                if (!stringExists(tmp, "--side")) {
                    output = DeckMenu.addCardToMainDeck(map.get("--card"), map.get("--deck"));
                } else {
                    output = DeckMenu.addCardToSideDeck(map.get("--card"), map.get("--deck"));
                }
            }
        } else if (checkWord("deck rm-card", input)) {
            if (validInput("deck rm-card", input)) {
                String tmp = getNames("deck rm-card", input);
                updateMap(inputCatcher(tmp, ""));
                if (!stringExists(tmp, "--side")) {
                    output = DeckMenu.removeCardFromMainDeck(map.get("--card"), map.get("--deck"));
                } else {
                    output = DeckMenu.removeCardFromSideDeck(map.get("--card"), map.get("--deck"));
                }
            }
        } else if (checkWord("deck show --all", input)) {
            // TODO :
            // SHOW ALL CARDS
        } else if (checkWord("deck show --cards", input)) {
            // TODO :
            // SHOW FREE CARDS
        } else if (checkWord("deck show", input)) {
            if (validInput("deck show", input)) {
                String tmp = getNames("deck show", input);
                updateMap(inputCatcher(tmp, ""));
                if (!stringExists(tmp, "--side")) {
                    // TODO :
                    // SHOW MAIN DECK
                } else {
                    // TODO :
                    // SHOW SIDE DECK
                }
            }
        }
    }

    public void recognizeShopMenu(String input) {
        checkMenuExit(input);
        if (checkWord("shop buy", input)) {
            if (validInput("shop buy", input)) {
                String cardName = getNames("shop buy", input);
                output = ShopMenu.shopBuy(cardName);
            } else if (checkWord("shop show --all", input)) {
                ArrayList<String> cards = ShopMenu.showAllCards();
                for (String card : cards) {
                    System.out.println(card);
                }
            }
        }
    }

    public void recognizeDuelMenu(String input) {
        checkMenuExit(input);
        if (stringExists(input, "duel")) {
            if (stringExists(input, "--ai")) {
                if (validInput("duel", input)) {
                    String tmp = getNames("duel", input);
                    updateMap(inputCatcher(tmp, ""));
                    System.out.println("P2A");
                    output = DuelMenu.newP2AGame(Integer.parseInt(map.get("--rounds")));
                }
            } else {
                if (validInput("duel", input)) {
                    String tmp = getNames("duel", input);
                    updateMap(inputCatcher(tmp, ""));
                    System.out.println("P2P");
                    output = DuelMenu.newP2PGame(map.get("--second-player"), Integer.parseInt(map.get("--rounds")));
                }
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