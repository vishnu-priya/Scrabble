import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class scrabble_Improved{
	
     public static void main(String[] args) throws IOException{
    	 int[] scores = new int[]{1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
    	 int[] primes = new int[]{2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101};
    	 HashMap< Long, ArrayList< String > > mapOfWords = readFile(primes);
    	 ArrayList< String> input = readInputFile("input.txt");
    	 for(int i = 0; i < input.size(); i++){
    		 long index= 0L;
    		 if(input.get(i).length() == 7){
    			 index = getMaxScore(mapOfWords,input.get(i),primes,' ');
    		 }
    		 else{
    			 char letter = 'A';
    			 long tempIndex = 0L;
    			 int maxRank = 0;
    			 for(int j = 0; j < 26; j++){
    				 tempIndex = getMaxScore(mapOfWords,input.get(i),primes,letter);
    				 if(mapOfWords.containsKey(tempIndex) && getRank(mapOfWords.get(tempIndex).get(0)) > maxRank){
    					 maxRank = getRank(mapOfWords.get(tempIndex).get(0));
    					 index = tempIndex;
    				 }
    				 letter++;
    			 }
    		 }
        	 printMaxWords(mapOfWords,index,input.get(i));
    	 }
    	 
     }
     
     static ArrayList<String> readInputFile(String fileName) throws FileNotFoundException 
 	{
 		Scanner sc = new Scanner(new FileReader(fileName));
 		char letter = 'A';
 		String orignialWord;
 		ArrayList <String> inputWordList = new ArrayList<String>();
 		
 		while(sc.hasNextLine()) 
 		{
 			String word=sc.nextLine();
 			orignialWord = word;
 				inputWordList.add(word.toUpperCase());
 		}
 		
 		return inputWordList;
 	}
     
     public static long getMaxScore(HashMap< Long, ArrayList< String >> mapOfWords, String input, int[] primes, char ch){
    	 int maxScore = 0;
    	 long index = 0L;
    	 for(int i = 0; i < 128; i++){
    		 String subString= "";
    		 String binary = Integer.toBinaryString(i);
    		 if(ch != ' ')
    			 subString= getString(binary,input+""+ch);
    		 else
    			 subString= getString(binary,input);
    		 
    		 long score = getScore(subString,primes);
    		 int rank = 0;
    		 if(ch == ' '){
    			 rank = getRank(subString);
    		 }
    		 else if(ch != ' ' && i %2 == 0){
    			 rank = getRank(subString) - getRank("" + ch);
    		 }
    		 if(mapOfWords.containsKey(score) && rank > maxScore){
    			 maxScore = getRank(subString);
    			 index = score;
    		 }
    	 }
    	 return index;
     }
     
     public static int getRank(String input){
    	 int[] scores = new int[]{1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
    	 
    	 int score = 0;
    	 
    	 for(int i = 0; i < input.length(); i++){
    		 score += scores[input.charAt(i) - 'A'];
    	 }
    	 
    	 return score;
     }
     
     public static String getString(String binary, String input){
    	 String output = "";
    	 
    	 for(int i = 0; i < binary.length(); i++){
    		 if(binary.charAt(i) == '1'){
    			 output += input.charAt(i);
    		 }
    	 }
    	 
    	 return output;
     }
     
     public static void printMaxWords(HashMap< Long, ArrayList< String>> mapOfWords, long maxScore, String input){
    	 
    	 ArrayList< String> listOfMaxWords = mapOfWords.get(maxScore);
    	 System.out.println("Max Score for "+input+" is: "+getRank(listOfMaxWords.get(0)));
    	 for(int i = 0; i < listOfMaxWords.size(); i++){
    		 System.out.print(listOfMaxWords.get(i)+"\t");
    	 }
    	 System.out.println("\n");
     }
     
     public static HashMap< Long, ArrayList< String > > readFile(int[] primes) throws IOException{
 		HashMap< Long, ArrayList< String >> mapOfWords = new HashMap< Long, ArrayList< String > >();
        BufferedReader br = new BufferedReader(new FileReader("sowpods.txt"));
        String token = "";   
        
        while((token = br.readLine()) != null){
        	long score = getScore(token,primes);
 			if(mapOfWords.containsKey(score)){
 				ArrayList< String > newList = mapOfWords.get(score);
 				newList.add(token);
 				mapOfWords.put(score, newList);
 			}
 			else{
 				ArrayList< String > newList = new ArrayList<String>();
 				newList.add(token);
 				mapOfWords.put(score, newList);
 			}
 			
 		}
 		return mapOfWords;
 	}
     

     public static Long getScore(String input, int[] prime){
    	 long score = 1;
    	 
    	 for( int i = 0; i < input.length(); i++){
    		 score *= prime[input.charAt(i) - 'A'];
    	 }
    	 
    	 return score;
     }
}