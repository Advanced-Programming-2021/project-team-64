package Models.Menus;

import Models.*;

import java.util.ArrayList;

public class Scoreboard extends Model {
    public ArrayList<User> showScoreboard() {
        int n = allUsers.size();

        ArrayList<User> ranking = new ArrayList<User>(n);
        ArrayList<Boolean> flag = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            flag.set(i, false);
        }

        for (int i = 0; i < n; i++) {
            int maxScore = 0;
            int maxIndex = 0;

            for (int j = 0; j < n; j++) {
                User user = allUsers[j];

                if (!flag.get(j) && maxScore < user.getScore()) {
                    maxScore = user.getScore();
                    maxIndex = j;
                }
            }
            flag.set(maxIndex, true);
            ranking.add(allUsers[maxIndex]);
        }
        return ranking;
    }
}
