package Database;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Database {
    static public String fileToString (String filePath) throws IOException {
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        String s = "";
        while (fileReader.ready())
            s += (char)fileReader.read();
        return s;
    }

    static public ArrayList<String> splitString (String s, char splitChar) {
        ArrayList<String> arr = new ArrayList<>();

        String cur = "";
        boolean ok = true;
        s += ',';

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\"') {
                ok = ok ^ true;
                if (splitChar == '\n')
                    cur += c;
            }
            else if (c == splitChar && ok) {
                arr.add(cur);
                cur = "";
            }
            else
                cur += c;
        }
        return arr;
    }

    static public ArrayList<ArrayList<String>> csvToArrayList (String filePath) throws IOException {
        String s = fileToString(filePath);
        ArrayList<String> rows = splitString(s, '\n');
        ArrayList<ArrayList<String>> res = new ArrayList<>();
        for (String row: rows)
            res.add(splitString(row, ','));
        return res;
    }

    static public void writeToFile (String filePath, String data) throws IOException {
        File file = new File(filePath);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(data);
        fileWriter.close();
    }
}
