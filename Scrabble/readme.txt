We are looping through all the possible words in the sowpods.txt file and searching for a word that can be made using those 7 letters which would result in the maximum score.
for checking weather a given word can be made from those 7 letters or not we are using hashmap to keep track of all the available 7 letters and their occurence(in case of a letter repeating)

Changes made by Team 1:
1. Moved scores[] to from main() to getScore()
2. Added a noOfBlankTiles counter used for score computation
3. Modified getScore() and added a mismatch counter which along with noOfBlankTiles allows us to know which is a valid word and the corresponding score