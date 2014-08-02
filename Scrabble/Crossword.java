package OtherTeams.Scrabble.Scrabble;

import java.io.*;
import java.util.*;

public class Crossword {

    public static String lcs(String a, String b) {

        int[][] lengths = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i < a.length(); i++) {
            for (int j = 0; j < b.length(); j++) {
                if (a.charAt(i) == b.charAt(j)) {
                    lengths[i + 1][j + 1] = lengths[i][j] + 1;
                } else {
                    lengths[i + 1][j + 1] = Math.max(lengths[i + 1][j],
                            lengths[i][j + 1]);
                }
            }
        }

        StringBuffer sb = new StringBuffer();

        for (int x = a.length(), y = b.length();
                x != 0 && y != 0;) {

            if (lengths[x][y] == lengths[x - 1][y]) {
                x--;
            } else if (lengths[x][y] == lengths[x][y - 1]) {
                y--;
            } else {
                assert a.charAt(x - 1) == b.charAt(y - 1);

                sb.append(a.charAt(x - 1));

                x--;
                y--;
            }
        }

        return sb.reverse().toString();

    }

    static List<String> readDictionary(String fileName)
            throws FileNotFoundException {
        Scanner sc = new Scanner(new FileReader(fileName));
        List<String> dictionary = new ArrayList<String>();

        while (sc.hasNextLine()) {
            String word = sc.nextLine();
            dictionary.add(word);
        }
        return dictionary;
    }

    public static String checkPatternMatches(String word, ArrayList<String> patternList) {
        for (String s : patternList) {
            StringTokenizer st = new StringTokenizer(s, "-");
            String temp = st.nextToken();
            int pos = Integer.parseInt("" + temp.charAt(0));
            char alpha = temp.charAt(1);
            int patternLength = Integer.parseInt(st.nextToken());
            int trailingCharacters = word.length() - pos - 1;
            int trailingSpaces = patternLength - pos - 1;
            if (word.length() == patternLength) {
                if (word.charAt(pos) == alpha && trailingCharacters == trailingSpaces) {
                    return s;
                }
            }
        }
        return "";
    }

    static String searchInDictionary(String inputString, List<String> dictionary, ArrayList<String> patternList) {
        String maxWord = "";
        int score = 0;
        int noOfBlankTiles = 1;
        final int rackSize = 7;
        String dictionaryWord, prev = "";

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < dictionary.size(); i++) {
            dictionaryWord = dictionary.get(i);
            String matchedPattern = checkPatternMatches(dictionaryWord, patternList);
            if (matchedPattern.equals("")) {
                continue;
            }

            char letterArray[] = dictionaryWord.toCharArray();

            Arrays.sort(letterArray);
            String newDictionaryWord = new String(letterArray);

            String matchedString = lcs(inputString, newDictionaryWord);
            if (matchedString.length() == newDictionaryWord.length()) {
                score = calculateScore(matchedString);
            } else if (matchedString.length() == newDictionaryWord.length() - 1 &&
                       inputString.length() == rackSize - noOfBlankTiles ) {
                int position = Integer.parseInt(""+matchedPattern.charAt(0));
                char[] sortedInputString = inputString.toCharArray();
                Arrays.sort(sortedInputString);
                int j;
                for( j = 0; sortedInputString[j] == letterArray[j]; j++);
                     
                if(j == position ){
                    System.out.println("J: " + j + "Position: " + position + "");
                    score = calculateScore(matchedString);
                }
            }

            if (score > max) {
                maxWord = dictionaryWord;
                max = score;
                prev = dictionaryWord;
            }

        }

        return new String(prev + "," + max);
    }

    static int calculateScore(String word) {
        int sum = 0;
        int score[] = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1,
            1, 1, 1, 4, 4, 8, 4, 10};

        for (int i = 0; i < word.length(); i++) {
            sum += score[word.charAt(i) - 'A'];
        }
        return sum;
    }

    public static void printResult(String input, String result) {

        String[] splitString = result.split(",");
        System.out.println(input + " " + splitString[0] + " " + splitString[1]);

    }

    public static ArrayList<String> possiblePatterns(String pattern) {
        String[] components = pattern.split("[A-Z]");
        int leadingSpaces = components[0].length();
        int trailingSpaces = components[1].length();
        int totalLength = pattern.length();

        ArrayList<String> patternList = new ArrayList<String>();
        for (int sum = totalLength; sum > 1; sum--) {
            for (int i = 0; i <= leadingSpaces; i++) {
                for (int j = 0; j <= trailingSpaces; j++) {
                    if (i + j + 1 == sum) {
                        patternList.add("" + i + pattern.charAt(leadingSpaces) + "-" + sum);
                    }
                }
            }
        }
        return patternList;
    }

    public static void main(String[] args) throws FileNotFoundException {

        List<String> dictionary = new ArrayList<String>();
        String fileName = "sowpods.txt";
        dictionary = readDictionary(fileName);
        Scanner sc = new Scanner(new FileReader("testScramble.txt"));
        String pattern = "__P__";
        String rack = "QWERTYZ";
        System.out.println(possiblePatterns(pattern));

        char letterArray[] = rack.toUpperCase().toCharArray();
        Arrays.sort(letterArray);
        String inputString = new String(letterArray);
        String result = searchInDictionary(inputString, dictionary, possiblePatterns(pattern));
        printResult(rack, result);
        /* while (sc.hasNextLine()) {
         String scrambledWord = sc.nextLine();
         char letterArray[] = scrambledWord.toUpperCase().toCharArray();
         Arrays.sort(letterArray);
         String inputString = new String(letterArray);
         String result = searchInDictionary(inputString, dictionary);
         printResult(scrambledWord, result);
         }*/

    }
}
