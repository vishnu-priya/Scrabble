package OtherTeams.Scrabble.Scrabble;

import java.util.*;
import java.lang.*;
import java.io.*;

public class scrabble {
    
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(new FileReader("input.txt"));
        ArrayList<String> listOfWords = readFile();
        while (scan.hasNext()) {
            String rack = scan.next();
            int noOfBlankTiles = 7 - rack.length();
            String maxScoreWord = getWordWithMaxScore(listOfWords, rack.toUpperCase(), noOfBlankTiles);
            System.out.print("Rack: " + rack);
            System.out.println("\tChosen Word: " + maxScoreWord);
            
        }
    }

    public static ArrayList< String> readFile() throws IOException {
        ArrayList< String> listOfWords = new ArrayList< String>();
        BufferedReader br = new BufferedReader(new FileReader("sowpods.txt"));
        String token = "";
        while ((token = br.readLine()) != null) {
            listOfWords.add(token);
        }
        br.close();
        return listOfWords;
    }

    public static String getWordWithMaxScore(final ArrayList< String> listOfWords, String rack, int noOfBlankTiles) {
        String output = "";
        int maxScore = 0;

        for (int i = 0; i < listOfWords.size(); i++) {
            if (listOfWords.get(i).length() > 7) {
                continue;
            }
            int score = getScore(listOfWords.get(i), fillMap(rack), noOfBlankTiles);
            if (score > maxScore) {
                maxScore = score;
                output = listOfWords.get(i);
            }
        }

        return output + " - " + maxScore;
    }

    public static int getScore(String word, HashMap<String, Integer> map, int noOfBlankTiles) {
        int score = 0;
        int[] scores = new int[]{1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
        int mismatchCounter = 0;
        for (int i = 0; i < word.length(); i++) {
            if (map.containsKey("" + word.charAt(i))) {
                if (map.get("" + word.charAt(i)) == 0) {
                    if (mismatchCounter < noOfBlankTiles) {
                        mismatchCounter++;
                    } else {
                        return 0;
                    }
                } else {
                    int value = map.get("" + word.charAt(i));
                    value--;
                    map.put("" + word.charAt(i), value);
                    score += scores[word.charAt(i) - 'A'];
                }
            } else {
                if (mismatchCounter < noOfBlankTiles) {
                    mismatchCounter++;
                } else {
                    return 0;
                }
            }
        }
        return score;
    }

    public static HashMap<String, Integer> fillMap(String input) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        for (int i = 0; i < input.length(); i++) {
            if (map.containsKey("" + input.charAt(i))) {
                int value = map.get("" + input.charAt(i));
                value++;
                map.put("" + input.charAt(i), value);
            } else {
                map.put("" + input.charAt(i), 1);
            }
        }

        return map;
    }
}
