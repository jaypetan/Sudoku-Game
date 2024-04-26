package sudoku;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoardPanel extends JPanel {
	
   private static final long serialVersionUID = 1L;  // to prevent serial warning

   // Define named constants for the game board properties
   public static final int GRID_SIZE = 9;    // Size of the board
   public static final int SUBGRID_SIZE = 9; // Size of the sub-grid
   // Define named constants for UI sizes
   public static final int CELL_SIZE = 60;   // Cell width/height in pixels
   public static final int BOARD_WIDTH  = CELL_SIZE * GRID_SIZE;
   public static final int BOARD_HEIGHT = CELL_SIZE * GRID_SIZE;
                                             // Board width/height in pixels
   // Define properties
   /** The game board composes of 9x9 Cells (customized JTextFields) */
   private Cell[][] cells = new Cell[GRID_SIZE][GRID_SIZE];
   /** It also contains a Puzzle with array numbers and isGiven */
   private Puzzle puzzle = new Puzzle();
   
   // For counter
   public int counter;
   // For Sound
   private Sound gb_sound = new Sound();

   /** Constructor */
   public GameBoardPanel() {
      super.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE, 2, 2));  // JPanel

      // Allocate the 2D array of Cell, and added into JPanel.
      for (int row = 0; row < GRID_SIZE; ++row) {
         for (int col = 0; col < GRID_SIZE; ++col) {
            cells[row][col] = new Cell(row, col);
            super.add(cells[row][col]);   // JPanel
         }
      }
      // [TODO 3]
      CellInputListener listener = new CellInputListener();
      
      // [TODO 4]
      for (int row = 0; row < GRID_SIZE; ++row) {
          for (int col = 0 ; col < GRID_SIZE; ++col) {
             if (cells[row][col].isEditable()) {
                cells[row][col].addActionListener(listener);   // For all editable rows and cols
             }
          }
       }


      super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
   }

   /**
    * Generate a new puzzle; and reset the gameboard of cells based on the puzzle.
    * You can call this method to start a new game.
    */
   public void newGame() {
      // Generate a new puzzle
      puzzle.newPuzzle();

      // Initialize all the 9x9 cells, based on the puzzle.
      for (int row = 0; row < GRID_SIZE; ++row) {
         for (int col = 0; col < GRID_SIZE; ++col) {
            cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
         }
      }
      
      // To start/reset counter
      counter = 0;
      for (int row = 0; row < GRID_SIZE; ++row) {
   	   for (int col = 0; col < GRID_SIZE; ++col) {
   		   if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
   			   counter++ ;
   		   }
   	   }
      }
   }
   
   // To get value for counter
   public int getCounter() {
	   return counter;
   }

   /**
    * Return true if the puzzle is solved
    * i.e., none of the cell have status of TO_GUESS or WRONG_GUESS
    */
   public boolean isSolved() {
      for (int row = 0; row < GRID_SIZE; ++row) {
         for (int col = 0; col < GRID_SIZE; ++col) {
            if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
               return false;
            }
         }
      }
      return true;
   }
   
   private class CellInputListener implements ActionListener {
	   @Override
	   public void actionPerformed(ActionEvent e) {
		   // Get a reference of the JTextField that triggers this action event
		   Cell sourceCell = (Cell)e.getSource();
		 
		   // Retrieve the int entered
		   int numberIn = Integer.parseInt(sourceCell.getText());
		   // For debugging
		   System.out.println("You entered " + numberIn);

		   // To check if correct or wrong guess
		   if (numberIn == sourceCell.number) {
			   sourceCell.status = CellStatus.CORRECT_GUESS;
			   gb_sound.setFile(2);                         // To play success sound
			   gb_sound.play();
			   counter--;
			   
		   }else {
			   sourceCell.status = CellStatus.WRONG_GUESS;
			   gb_sound.setFile(1);                         // To play error sound
			   gb_sound.play();
		   }
		   sourceCell.paint();                              // re-paint this cell based on its status			   
		   
	   }
   }
   
   // To set difficulty of the puzzles
   public void setBaby() {
	   puzzle.SetDiff(1);
   }
   public void setEasy() {
	   puzzle.SetDiff(20);
   }
   public void setMed() {
	   puzzle.SetDiff(40);
   }
   public void setHard() {
	   puzzle.SetDiff(60);
   }
   
   public void resetGame() {
	   // To paint the guessed rows back to grey
	   for (int row = 0; row < GRID_SIZE; ++row) {
		   for (int col = 0; col < GRID_SIZE; ++col) {
			   if (cells[row][col].status != CellStatus.GIVEN && cells[row][col].status != CellStatus.HINT) {
				   cells[row][col].status = CellStatus.TO_GUESS;
				   cells[row][col].paint();
			   }
		   }
	   }
	   // To start/reset counter
	   counter = 0;
	   for (int row = 0; row < GRID_SIZE; ++row) {
		   for (int col = 0; col < GRID_SIZE; ++col) {
			   if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
				   counter++ ;
			   }
		   }
	   }
  }
  
  // To clear all the given hints
  public void clearHint(){
	   for (int row = 0; row < GameBoardPanel.GRID_SIZE; ++row) {
		   for (int col = 0; col < GameBoardPanel.GRID_SIZE; ++col) {
			   if(cells[row][col].status == CellStatus.HINT) {
		        	cells[row][col].status = CellStatus.GIVEN;
		        	cells[row][col].paint();
			   }
		   }
	   }
  }
  // To give the hint for the specific number
  public void getHint(int hint_number){
	   clearHint();
	   for (int row = 0; row < GameBoardPanel.GRID_SIZE; ++row) {
		   for (int col = 0; col < GameBoardPanel.GRID_SIZE; ++col) {
			   if(puzzle.isGiven[row][col] == true && puzzle.numbers[row][col] == hint_number) {
		        	cells[row][col].status = CellStatus.HINT;
		        	cells[row][col].paint();
			   }
		   }
	   }
  }
  
  public void initialgame(){
	  cells[1][3].status = CellStatus.START;
	  cells[1][4].status = CellStatus.START;
	  cells[1][5].status = CellStatus.START;
	  cells[2][2].status = CellStatus.START;
	  cells[2][6].status = CellStatus.START;
	  cells[3][2].status = CellStatus.START;
	  cells[4][3].status = CellStatus.START;
	  cells[4][4].status = CellStatus.START;
	  cells[4][5].status = CellStatus.START;
	  cells[5][6].status = CellStatus.START;
	  cells[6][2].status = CellStatus.START;
	  cells[6][6].status = CellStatus.START;
	  cells[7][3].status = CellStatus.START;
	  cells[7][4].status = CellStatus.START;
	  cells[7][5].status = CellStatus.START;
	  for (int row = 0; row < GameBoardPanel.GRID_SIZE; ++row) {
		   for (int col = 0; col < GameBoardPanel.GRID_SIZE; ++col) {
		        	cells[row][col].paint();
		   }
	  }
   
  }
}

