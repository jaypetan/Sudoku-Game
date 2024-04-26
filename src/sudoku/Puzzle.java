package sudoku;
/**
 * The Sudoku number puzzle to be solved
 */
public class Puzzle {
   // The numbers on the puzzle  
   int[][] numbers = new int[GameBoardPanel.GRID_SIZE][GameBoardPanel.GRID_SIZE];
   // The clues - isGiven (no need to guess) or need to guess
   boolean[][] isGiven = new boolean[GameBoardPanel.GRID_SIZE][GameBoardPanel.GRID_SIZE];
   
   int randomNumber, random1, random2;   // for randomizer
   int diff;                             // for difficulty of game
   
   // Constructor
   public Puzzle() {
      super();
   }
   
   public void SetDiff(int diff) {
	   this.diff = diff;
   }
   
   public void newPuzzle() {
      //hard coded numbers
	      int[][] hardcodedNumbers =
	         {{5, 3, 4, 6, 7, 8, 9, 1, 2},
	          {6, 7, 2, 1, 9, 5, 3, 4, 8},
	          {1, 9, 8, 3, 4, 2, 5, 6, 7},
	          {8, 5, 9, 7, 6, 1, 4, 2, 3},
	          {4, 2, 6, 8, 5, 3, 7, 9, 1},
	          {7, 1, 3, 9, 2, 4, 8, 5, 6},
	          {9, 6, 1, 5, 3, 7, 2, 8, 4},
	          {2, 8, 7, 4, 1, 9, 6, 3, 5},
	          {3, 4, 5, 2, 8, 6, 1, 7, 9}};

	      // Copy from hardcodedNumbers into the array "numbers"
	      for (int row = 0; row < GameBoardPanel.GRID_SIZE; ++row) {
	         for (int col = 0; col < GameBoardPanel.GRID_SIZE; ++col) {
	            numbers[row][col] = hardcodedNumbers[row][col];
	         }
	      }
	      
	      // Randomizer for numbers, to add a random value to all the numbers
	      randomNumber = (int)(Math.random()*9.0)+1;
	      
	      for (int row = 0; row < GameBoardPanel.GRID_SIZE; ++row) {
	    	 for (int col = 0; col < GameBoardPanel.GRID_SIZE; ++col) {
		        numbers[row][col] += randomNumber;
		        if(numbers[row][col] > 9) {
		        	numbers[row][col] -= 9;
		        }
		     }
	      }
	  
      // Need to use input parameter cellsToGuess!
      boolean[][] hardcodedIsGiven =
         {{true, true, true, true, true, true, true, true, true},
          {true, true, true, true, true, true, true, true, true},
          {true, true, true, true, true, true, true, true, true},
          {true, true, true, true, true, true, true, true, true},
          {true, true, true, true, true, true, true, true, true},
          {true, true, true, true, true, true, true, true, true},
          {true, true, true, true, true, true, true, true, true},
          {true, true, true, true, true, true, true, true, true},
          {true, true, true, true, true, true, true, true, true}};

      // Copy from hardcodedIsGiven into array "isGiven"
      for (int row = 0; row < GameBoardPanel.GRID_SIZE; ++row) {
         for (int col = 0; col < GameBoardPanel.GRID_SIZE; ++col) {
            isGiven[row][col] = hardcodedIsGiven[row][col];
         }
      }
      
      // Randomizer for guesses 
      for(int numBlanks = 0; numBlanks < diff; ++ numBlanks ) {
    	  // To ensure there are no repeats for false
    	  do {
    		  random1 = (int)(Math.random()*8.9);
    		  random2 = (int)(Math.random()*8.9);
    	  } while(isGiven[random1][random2] == false);
    	  
    	  // Set a random box to be blank
    	  isGiven[random1][random2] = false;
      }
   }
   //(For advanced students) use singleton design pattern for this class
}