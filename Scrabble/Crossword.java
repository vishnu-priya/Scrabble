import java.io.*;
import java.util.*;

public class Crossword {

    static String rack = "AEIIBII";
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
//        System.out.println(sb);

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
        sc.close();
        return dictionary;
    }

    public static String checkPatternMatches(String word, ArrayList<String> patternList)
    {
        for (String s : patternList)
        {
        	
        	char char1;
        	char char2 = 0;
        	int leadingSpaces = 0;
        	int middleSpaces = 0;
        	int trailingSpaces = 0;
        	int x = 0;
        	//int patternLength = 0;
        	
            StringTokenizer st = new StringTokenizer(s, "-");
            String temp = st.nextToken();
            leadingSpaces = Integer.parseInt(""+temp.charAt(0));
            if(temp.length()!=3)
            {
            	
            	middleSpaces = Integer.parseInt(""+temp.charAt(2));
            	trailingSpaces = Integer.parseInt(""+temp.charAt(4));
            	char1 = temp.charAt(1);
            	char2 = temp.charAt(3);
            	x = 2;
            	
            }
            else
            {
            	trailingSpaces = Integer.parseInt(""+temp.charAt(2));
            	char1 = temp.charAt(1);
            	x = 1;
            }
            int pos = Integer.parseInt("" + temp.charAt(0));
            
            int patternLength = Integer.parseInt(st.nextToken());
            int trailingCharacters = word.length() - pos - middleSpaces - x;
            if (word.length() == patternLength)
            {
    			//System.out.println(word.charAt(pos+middleSpaces+1)+ "    " +word + "         mid"+ middleSpaces + " char2 : "+char2);
                if ((middleSpaces > 0 && 
                	word.charAt(pos) == char1 && 
                	word.charAt(pos + middleSpaces + 1) == char2 
                	&& trailingCharacters == trailingSpaces) ||
                	(middleSpaces == 0 &&
                	word.charAt(pos) == char1 &&
                	trailingCharacters == trailingSpaces))
		                {
                			//System.out.println(word.charAt(pos+middleSpaces+1)+ "    " +word);
		                    return s;
		                }
		                	
            }
        }
        return "";
    }

    static String searchInDictionary(String inputString1, List<String> dictionary, ArrayList<String> patternList) {
    	char letterArra[] = rack.toUpperCase().toCharArray();
        Arrays.sort(letterArra);
        String inputString = new String(letterArra);
        String maxWord = "";
        int score = 0;
        int noOfBlankTiles = 1;
        final int rackSize = 7;
        System.out.println(rackSize);
        String dictionaryWord, prev = "";

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < dictionary.size(); i++) {
            dictionaryWord = dictionary.get(i);
            //System.out.println(dictionaryWord);
            String matchedPattern = checkPatternMatches(dictionaryWord, patternList);
            //System.out.println(matchedPattern);
            if (matchedPattern.equals("")) {
                continue;
            }

            char letterArray[] = dictionaryWord.toCharArray();

            Arrays.sort(letterArray);
            String newDictionaryWord = new String(letterArray);

            String matchedString = lcs(inputString, newDictionaryWord);
//            System.out.println(inputString1.length());
            if (matchedString.length() == newDictionaryWord.length()) {
                score = calculateScore(matchedString);
            } 
            else if (matchedString.length() == newDictionaryWord.length() - 1 &&
                       inputString1.length() == rackSize - noOfBlankTiles ) {
                int position = Integer.parseInt(""+matchedPattern.charAt(0));
                System.out.println(matchedPattern.charAt(0)+"-"+inputString+"-"+newDictionaryWord);
                char[] sortedInputString = inputString.toCharArray();
                Arrays.sort(sortedInputString);
                int j;
                for( j = 0; sortedInputString[j] == letterArray[j]; j++);
                  if(j == position+1 ){
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
        //System.out.println(components[0] + "......" + components[2]);
        int leadingSpaces = 0;
        int trailingSpaces = 0;
        int middleSpaces = 0;
        int x = 0;
        
        leadingSpaces = components[0].length();
        int totalLength = pattern.length();
        
        if(components.length == 3)
        {
        	 middleSpaces = components[1].length();
             trailingSpaces = components[2].length();
             x = 2;
             rack += pattern.charAt(leadingSpaces + middleSpaces + 1 );
        }
        else if (components.length == 2)
        {
        	 trailingSpaces = components[1].length();
        	 x = 1;
        }
        
        
        //System.out.println(totalLength);
        rack += pattern.charAt(leadingSpaces);
        ArrayList<String> patternList = new ArrayList<String>();
        
        for (int sum = totalLength; sum > 1; sum--) {
            for (int i = 0; i <= leadingSpaces; i++) {
                for (int j = 0; j <= trailingSpaces; j++) {
                    if (i + j + x + middleSpaces == sum) 
                    {
                    	if(middleSpaces != 0)
                    	{
                    		
                            patternList.add("" + i + pattern.charAt(leadingSpaces) + middleSpaces + pattern.charAt(leadingSpaces + middleSpaces + 1) + j + "-" + sum);

                    	}
                    	else
                            patternList.add("" + i + pattern.charAt(leadingSpaces) + j + "-" + sum);

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
        //Scanner sc = new Scanner(new FileReader("testScramble.txt"));
        String pattern = "_P_T_";
        String tempRack = rack;
        //System.out.println(possiblePatterns(pattern));

        
        String result = searchInDictionary(tempRack, dictionary, possiblePatterns(pattern));
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
