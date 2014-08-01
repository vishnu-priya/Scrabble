import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


class Dictionary {
    public static  HashMap<String,Integer> dict= new HashMap<String,Integer>();

    public static int worth[] = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
    

    public static void buildDictionary(File file) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file)); //To change body of generated methods, choose Tools | Templates.
        String line;
        
        while ((line = reader.readLine()) != null) {
            dict.put(line,calculateScore(line));
	}
        
    }
    public int getWorth(char a)
    {
        return worth[ a - 'A' ];
    }
    private static Integer calculateScore(String w) {
    
    int score = 0;    
    for(int i = 0; i < w.length(); i++){
            score += worth[ w.charAt(i) - 'A' ];
        }
    return score;
 //To change body of generated methods, choose Tools | Templates.
    }
    
    public Boolean isPresent(String word){
        if(dict.containsKey(word))
            return true;
        return false;
    }    

}

class Rack
{
	static public ArrayList<String> wordCombinations = new ArrayList<String>();
	
	public  static void permutation(String str) 
	{ 
	    permutation("", str); 
	}

	 private static void permutation(String prefix, String str) 
	 {
	    int n = str.length();
	    if (n == 0)
	    {
	    	wordCombinations.add(prefix);
	    }
	    else {
	        for (int i = 0; i < n; i++)
	           permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
	    }
	}

 
public static ArrayList<String> generateAllCombinations(String allLetters)
    {
        ArrayList<String> allCombinations = new ArrayList<String>();
        int numLetters = allLetters.length();
        int numCombinations = powerOfTwo(numLetters);
        String word;
        for ( int i = 0; i < numCombinations; i++)
        {
            word = generateAWord(i, allLetters);
            if (word.length() > 1)
            {
                
                allCombinations.add(word);
            }
        }
        return allCombinations;
    }
    
    static int powerOfTwo(int exponent)
    {
        int returnVal = 1;
        while(exponent > 0)
        {
            returnVal *= 2;
            exponent--;
        }
        return returnVal;
    }
    
    static String generateAWord(int number, String letters)
    {
        int i = 0;
        StringBuilder word = new StringBuilder();
        while (number > 0)
        {
            if (number % 2 == 1)
            {
                word.append(letters.charAt(i));
            }
            i++;
            number /= 2;
        }
        return word.toString();
    }

}

public class ScrabbleReRe {
	public static String getHighestScoringWord(HashMap<String,Integer> dict, ArrayList<String> words,String input)
	{
		int maxScore = 0;
		String maxWord = "";
		int blankValue=0;
                
                
		for(int i = 0; i < words.size(); i++)
		{
			String str = words.get(i).toUpperCase();
			if(dict.containsKey(str))
			{
				if(maxScore < dict.get(str))
				{
					maxScore = dict.get(str);
					maxWord = str;
				}
			}
		}
		return  maxWord + "\nScore: " +(maxScore);
	}
        public static String sortString(String word)
        {
            char[] charArray = word.toCharArray();
            Arrays.sort(charArray);
            return new String(charArray);
                   
        }
	
        public static char getConstraintWord(String word){
        	
        	for(int i = 0; i < word.length(); i++){
        		if(word.charAt(i) != '_'){
        			return word.charAt(i);
        		}
        	}
        	return (Character) null;
        }
        
        public static int getConstraintIndex(String word){
        	for(int i = 0; i < word.length(); i++){
        		if(word.charAt(i) != '_'){
        			return i;
        		}
        	}
        	return -1;
        }
        
    public static ArrayList< String > permutationsFollowConstraint(ArrayList< String> words, char constraint, int index, int maxLength){
    	if(index == -1)
    		return words;
    	int i = 0;
    	while(i < words.size()){
    		if(!wordFollowConstraint(words.get(i),constraint,index,maxLength)){
    			words.remove(i);
    		}
    		else{
    			i++;
    		}
    	}
    	return words;
    }
    
    public static boolean wordFollowConstraint(String input, char alphabet, int index, int maxLength){
    	
    	for( int i = 0; i < input.length(); i++){
    		int leftPart = index;
    		int rightPart = maxLength - index - 1;
    		if(input.charAt(i) == alphabet && leftPart >= i && rightPart >= input.length() - i -1){
    			return true;
    		}
    	}
    	return false;
    }
        
	public static void main(String[] args) throws FileNotFoundException, IOException
    {
		// Consider a Rack consists of 7 inputs only
		String lettersOnRack = "ALTHE".toUpperCase();
		String constraint = "____B__";
		char constraintWord = getConstraintWord(constraint);
		
        String[] alphabet={"_","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		File file = new File("sowpods.txt");
    	Dictionary.buildDictionary(file);
            //System.out.println(lettersOnRack);
            ArrayList<String> combinations = Rack.generateAllCombinations(lettersOnRack+""+constraintWord);
            for(int j = 0; j < combinations.size(); j++)
            {
        		Rack.permutation(combinations.get(j));
        }

        Rack.wordCombinations = permutationsFollowConstraint(Rack.wordCombinations,constraintWord,getConstraintIndex(constraint),constraint.length());

    	String highestScoringWord = getHighestScoringWord( Dictionary.dict, Rack.wordCombinations,lettersOnRack);


    	System.out.println(highestScoringWord);
    }
}
