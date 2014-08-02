
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class scrabble3{
	
	static int maxLeadingBlanks = 0;
    static int maxTrailingBlanks = 0;
    static String existingLetter = "";
    static String rack = "";
	
     public static void main(String[] args) throws IOException
     { 
    	 ArrayList< String> input = readInputFile("input.txt");
    	 for(int i = 0; i < input.size(); i++)
    	 {
    		 		 processInput(input.get(i));  
    		 		 /*if(rack.length() == 6){
    		 		  * for(int i = 0 ; i < maxLeadingBlanks ;i++)
    		 		  * {
    		 		  * regex = [rack]{0,i}[A-Z][rack]{0,maxLeadingBlanks-i}existingLetter[rack]{0,maxTrailingBlanks}
    		 		  * call all functions and populate map
    		 		  * }
    		 		  * 
    		 		  * for(int i = 0 ; i < maxTrailingBlanks ;i++)
    		 		  * {
    		 		  * regex = [rack]{0,maxLeadingBlanks}existingLetter[rack]{0,i}[A-Z][rack]{0,maxTrailingBlanks-i}
    		 		  * call all functions and populate map
    		 		  * }
    		 		  * 
    		 			String regex ----> [A - Z][rack]{0 , maxLeadingBlanks - 1}existingLetter[rack]{0,maxTrailingBlanks}
    		 		 }*/
    		 		 String regex = "[" + rack +"]" + "{" + 0 + "," + maxLeadingBlanks + "}" + existingLetter + "[" + rack +"]" + "{" + 0 + "," + maxTrailingBlanks + "}";
    	          	 System.out.println("\n\n" +rack);
    	             ArrayList <String> matchingWords = findMatchingWords(regex);
    	          	 HashMap<Long, ArrayList< String >> mapOfWords = computeScrabbleScores(matchingWords);
    	          	 mapOfWords = refineMap(mapOfWords);
    	          	 printMaxWord(mapOfWords);
    	 }
     }
     static void processInput (String input)
     {
    	 String tmpCond;
	     char[] cond  ;
	     int k;
	    rack = input.split(" ")[1];
		rack.toUpperCase();
       tmpCond = input.split(" ")[0];
       cond = tmpCond.toCharArray();
       for( k=0 ; k<tmpCond.length();k++)
       {
              if (cond[k] != '_'){
            	  existingLetter = Character.toString(cond[k]);
                    break;
              }
                    
       }
       maxLeadingBlanks = k - 1;
       maxTrailingBlanks = tmpCond.length() - k - 1;

     }
     static String sort (String s)
     {
    	 char ch[] = s.toCharArray();
    	 Arrays.sort(ch);
    	 return new String(ch);
     }
     static boolean checkSequence (String rack , String s) //this will work for blank tile also
     {
    	 Boolean blankTile = false;
    	 String originalRack = rack;
    	 for( int i = 0; i < s.length(); i++)
    	 {
    		 String regex = "" + s.charAt(i);
    		 String tempRack = rack.replaceFirst(regex , "");
    		 if(tempRack.length() == rack.length())
    		 {
    			
    			 if(originalRack.length() == 6 && blankTile == false)
    			 {
    				 blankTile = true;
    			 }
    			 else return false;
    		 }
    		 rack = tempRack;
    		
    	 }
    	 return true;
     }
     static HashMap<Long, ArrayList< String >> refineMap (HashMap< Long, ArrayList< String > > mapOfWords)
     {
    	 long key;
    	 ArrayList< String > value;
    	 rack = sort(rack);
    	 
    	 for (Entry<Long, ArrayList<String>> entry : mapOfWords.entrySet())
    	 {
    		 key = entry.getKey();
    		 value = entry.getValue();
    		 for(int i = 0; i < value.size(); i++)
 	        {
 	        	String sortedValue = sort(value.get(i)); 	        	
 	        	if(!checkSequence(rack, sortedValue))
 	        	{
 	        		value.remove(i);
 	        	} 	        	
 	        } 
 	        if(value.size() > 0)
 	        {
 	        	mapOfWords.put(key, value);
 	        }
 	        else
 	        {
 	        	mapOfWords.remove(key);
 	        }    		
    	 }
    	 return mapOfWords;
     }
     
     static void printMaxWord(HashMap< Long, ArrayList< String > > mapOfWords)
     {
    	 long score = -1L;
    	 ArrayList<String> value = null;
    	 for (Entry<Long, ArrayList<String>> entry : mapOfWords.entrySet())
    	 {
    		 Long key = entry.getKey();
    		 value = entry.getValue();
    		 if(key > score)
    			 {
    			 	score = key;
    			 }
    		
    	 }
    	 for( int i = 0; i < value.size(); i++)
    	 {
    		 System.out.println( value.get(i) + " score = " +score);
    	 }
     }
     
     static ArrayList<String> findMatchingWords (String regex) throws IOException
     {
    	 ArrayList<String> matchingWords = new ArrayList<String> ();
    	 
    	 BufferedReader br = new BufferedReader(new FileReader("sowpods.txt"));
         String token = "";   
         
         while((token = br.readLine()) != null)
         {
         	if(token.matches(regex)){
         		matchingWords.add(token);
         	}
  			
  		 }
    	 br.close();
    	 return matchingWords;    	 
     }
     
     public static HashMap< Long, ArrayList< String >> computeScrabbleScores(ArrayList< String > words) throws IOException{
  		HashMap< Long, ArrayList< String >> mapOfWords = new HashMap< Long, ArrayList< String > >();
  		for(int i = 0; i < words.size(); i++)
  		{
  			long score = getRank(words.get(i));
  			if(mapOfWords.containsKey(score)){
 				ArrayList< String > newList = mapOfWords.get(score);
 				newList.add(words.get(i));
 				mapOfWords.put(score, newList);
 			}
 			else{
 				ArrayList< String > newList = new ArrayList<String>();
 				newList.add(words.get(i));
 				mapOfWords.put(score, newList);
 			}
  		}
  		
        return mapOfWords;
  	}
     
     
     static ArrayList<String> readInputFile(String fileName) throws FileNotFoundException 
 	{
 		Scanner sc = new Scanner(new FileReader(fileName));
 		ArrayList <String> inputWordList = new ArrayList<String>();
 		while(sc.hasNextLine()) 
 		{
 			String word=sc.nextLine();
 			//String orignialWord = word;
 				inputWordList.add(word.toUpperCase());
 		}
 		sc.close();
 		return inputWordList;
 	}
     public static int getRank(String input){
    	 int[] scores = new int[]{1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
    	 
    	 int score = 0;
    	 
    	 for(int i = 0; i < input.length(); i++){
    		 score += scores[input.charAt(i) - 'A'];
    	 }
    	 
    	 return score;
     }
}
