import com.google.gson.*;

import java.util.ArrayList;
import Models.User.*;

public class Main {
    public static void main(String[] args) {
        // read data from database
        User user = new User("mahdi", "pass", "nick");
        System.out.println(new Gson().toJson(user));

        // write data to database
    }
}