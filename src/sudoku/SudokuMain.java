package sudoku;
import java.awt.*;

import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;

/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {
   private static final long serialVersionUID = 1L;  // to prevent serial warning
   
   // private variables
   GameBoardPanel board = new GameBoardPanel();
   Timer timer;
   Sound sound = new Sound();
   ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("Celebrate_Pepe.jpg"));
   
   JLabel CellsRemainingLabel = new JLabel(" Start the game.", SwingConstants.CENTER);
   
   JMenu menu, diff, hs_menu, hint_menu;
   JMenuItem NewGame, Reset, ResetTime, Baby, Easy, Med, Hard, hs_Item, clear_leaderboard, Clear;
   
   private int time = 0;
   private int timescore = 0;
   private String WinnerName;
   
   private String[] namelist = {"unknown","unknown","unknown","unknown","unknown"};
   private int[] highscore = {10000,10000,10000,10000,10000};

   // Constructor
   public SudokuMain() {
	   board.initialgame();
	   // To construct the menu buttons.
	   JFrame frame = new JFrame("Sudoku");
	   JMenuBar menuBar = new JMenuBar();
	   
	   // JMenu
	   menu = new JMenu("Menu");
	   diff = new JMenu("Difficulty");
	   hs_menu = new JMenu("Highscore");
	   hint_menu = new JMenu("Hint");
	   
	   // JMenuItems
	   NewGame = new JMenuItem("Start New Game");
	   Reset = new JMenuItem("Reset Game");
	   ResetTime = new JMenuItem("Reset Time");
	   
	   Baby = new JMenuItem("Baby");       // Test mode
	   Easy = new JMenuItem("Easy");
	   Med = new JMenuItem("Intermediate");
	   Hard = new JMenuItem("Hard");
	   
	   hs_Item = new JMenuItem("Highscore");
	   clear_leaderboard = new JMenuItem("Clear");
	   Clear = new JMenuItem("Clear");
	   
	   // Add hints from 1-9
	   JMenuItem[] hint_item = new JMenuItem[9];
	   for(int hint_number = 0; hint_number < 9; hint_number++) {
		   hint_item[hint_number] = new JMenuItem((hint_number + 1 )+ ""); 
		   hint_menu.add(hint_item[hint_number]);
		   
		   int number = hint_number;
		   hint_item[hint_number].addActionListener(new ActionListener() {
			   @Override
	           	public void actionPerformed(ActionEvent evt) {
				   board.getHint(number + 1);
	           	}
		   });
	   }
	   Clear.addActionListener(new ActionListener() {
		   @Override
           	public void actionPerformed(ActionEvent evt) {
			   board.clearHint();
           	}
	   });
	   hint_menu.add(Clear);
	   
	   // Adding items to menu
	   menu.add(NewGame);
	   menu.add(Reset);
	   menu.add(ResetTime);
	   
	   diff.add(Baby);
	   diff.add(Easy);
	   diff.add(Med);
	   diff.add(Hard);
	   
	   hs_menu.add(hs_Item);
	   hs_menu.add(clear_leaderboard);
	   
	   menuBar.add(menu);
	   menuBar.add(diff);
	   menuBar.add(hs_menu);
	   menuBar.add(hint_menu);
	   
	   frame.setJMenuBar(menuBar);
	   frame.setSize(700,700);
	   frame.setLayout(new BorderLayout());
	   frame.setVisible(true);
	   
	   frame.setLayout(new BorderLayout());
	   board.setBackground(new Color(29, 46, 104));
	   frame.add(board, BorderLayout.CENTER);
	   
	   
	   
	   // HIGHSCORE BUTTON
	   hs_Item.addActionListener(new ActionListener() {
		   @Override
           	public void actionPerformed(ActionEvent evt) {
			   String hs_message = "   Fastest Times: \n";
			   for(int placement = 0; placement < 5; placement++ ) {
				   hs_message += (placement + 1) + ") " + namelist[placement] + " [ " + highscore[placement] + "s ]\n";
			   }
			   JOptionPane.showMessageDialog(null, hs_message, "======== HIGH SCORE ========", JOptionPane.INFORMATION_MESSAGE, image);   
           	}
	   });
	   
	   clear_leaderboard.addActionListener(new ActionListener() {
		   @Override
          	public void actionPerformed(ActionEvent evt) {
			   for(int placement = 0; placement < 5; placement++ ) {
				   namelist[placement] = "unknown";
				   highscore[placement] = 10000;
			   }
		   }
	   });
	   
	   // TIMER LABEL
	   JLabel TimeLabel = new JLabel("TIME: " + time, SwingConstants.CENTER);
	   ActionListener UpdateSystem = new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent evt) {
			   CellsRemainingLabel.setText("REMAINING: " + board.getCounter() + "");        //To update cells remaining
			   time++;
			   TimeLabel.setText("TIME: " + time);                                          //To update time 
			   
			   //for solved puzzle
			   if(board.isSolved() == true) {
				   timer.stop();
				   
				   sound.setFile(0);                                                       //play victory song
				   sound.play();
				   
				   WinnerName = JOptionPane.showInputDialog(null, "Congratulation! Your time is " + time + "s!\n Enter your name: ");
				   timescore = time;
				   
				   
				   
				   for(int placement = 0; placement < 5; placement++ ) {                   //To arrange high score
					   if(timescore < highscore[placement]) {
						   for(int move_placement = 4; move_placement > placement; --move_placement ) {
							   highscore[move_placement] = highscore[move_placement - 1];
							   namelist[move_placement] = namelist[move_placement - 1];
						   }
						   highscore[placement] = timescore;
						   namelist[placement] = WinnerName;
						   break;
					   }
				   }
				   
				   
			   }
		   }
	   };
	   timer = new Timer(1000, UpdateSystem);
	   
	   
	   // Button for new game
	   NewGame.addActionListener(new ActionListener() {
		   @Override
           	public void actionPerformed(ActionEvent evt) {
			   board.newGame();
			   time = 0;
			   timer.start();
		   }
	   });
	   
	   // Button for reset game
	   Reset.addActionListener(new ActionListener() {
		   @Override
           	public void actionPerformed(ActionEvent evt) {
			   board.resetGame();
           	}
	   });
	   
	   // Button to reset time
	   ResetTime.addActionListener(new ActionListener() {
		   @Override
           	public void actionPerformed(ActionEvent evt) {
			   time = 0;
			   timer.start();
           	}
	   });
      
	   JPanel statusBar = new JPanel(new GridLayout(1, 3, 2, 2));
	   frame.add(statusBar, BorderLayout.NORTH);
	   statusBar.setBackground(new Color(29, 46, 104));
      
	   // Difficulty setting
	   JLabel DifficultyLabel = new JLabel(" Choose a Difficulty.", SwingConstants.CENTER);
	   DifficultyLabel.setFont(new Font("Serif", Font.BOLD, 21));
	   DifficultyLabel.setOpaque(true);
	   DifficultyLabel.setBackground(new Color(158, 186, 245));
	   statusBar.add(DifficultyLabel);

	   // Cells remaining
	   CellsRemainingLabel.setFont(new Font("Serif", Font.BOLD, 21));
	   CellsRemainingLabel.setOpaque(true);
	   CellsRemainingLabel.setBackground(new Color(109, 140, 212));
	   statusBar.add(CellsRemainingLabel);
	   
	   // Timer 
	   TimeLabel.setFont(new Font("Serif", Font.BOLD, 21));
	   TimeLabel.setOpaque(true);
	   TimeLabel.setBackground(new Color(158, 186, 245));
	   statusBar.add(TimeLabel);
	   
      
	   // Button for difficulty level
	   Baby.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent e) {
			   board.setBaby();
			   DifficultyLabel.setText(" MODE: Baby");
		   }
	   });
	   Easy.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent e) {
			   board.setEasy();
			   DifficultyLabel.setText(" MODE: Easy");
		   }
	   });
	   Med.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent e) {
			   board.setMed();
			   DifficultyLabel.setText(" MODE: Intermediete");
		   }
	   });
	   Hard.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent e) {
			   board.setHard();
			   DifficultyLabel.setText(" MODE: Hard");
		   }
	   });   
	   
	   //For LEFT design	   
	   JPanel LeftOfScreen = new JPanel(new GridLayout(3, 1, 2, 2));
	   LeftOfScreen.setBackground(new Color(29, 46, 104));
	   frame.add(LeftOfScreen, BorderLayout.WEST);
	   JLabel Lcolour1 = new JLabel("    ");
	   Lcolour1.setOpaque(true);
	   Lcolour1.setBackground(new Color(158, 186, 245));
	   JLabel Lcolour2 = new JLabel("    ");
	   Lcolour2.setOpaque(true);
	   Lcolour2.setBackground(new Color(109, 140, 212));
	   JLabel Lcolour3 = new JLabel("    ");
	   Lcolour3.setOpaque(true);
	   Lcolour3.setBackground(new Color(158, 186, 245));
	   LeftOfScreen.add(Lcolour1);
	   LeftOfScreen.add(Lcolour2);
	   LeftOfScreen.add(Lcolour3);
	   
	   //For RIGHT design
	   JPanel RightOfScreen = new JPanel(new GridLayout(3, 1, 2, 2));
	   RightOfScreen.setBackground(new Color(29, 46, 104));
	   frame.add(RightOfScreen, BorderLayout.EAST);
	   JLabel Rcolour1 = new JLabel("    ");
	   Rcolour1.setOpaque(true);
	   Rcolour1.setBackground(new Color(158, 186, 245));
	   JLabel Rcolour2 = new JLabel("    ");
	   Rcolour2.setOpaque(true);
	   Rcolour2.setBackground(new Color(109, 140, 212));
	   JLabel Rcolour3 = new JLabel("    ");
	   Rcolour3.setOpaque(true);
	   Rcolour3.setBackground(new Color(158, 186, 245));
	   RightOfScreen.add(Rcolour1);
	   RightOfScreen.add(Rcolour2);
	   RightOfScreen.add(Rcolour3);
	   
	   //For BOTT design
	   JPanel BottomOfScreen = new JPanel(new GridLayout(1, 1, 2, 2));
	   BottomOfScreen.setBackground(new Color(29, 46, 104));
	   frame.add(BottomOfScreen, BorderLayout.SOUTH);
	   JLabel bottom1 = new JLabel(" ");
	   bottom1.setOpaque(true);
	   bottom1.setBackground(new Color(158, 186, 245));
	   BottomOfScreen.add(bottom1);
	   JLabel bottom2 = new JLabel(" ");
	   bottom2.setOpaque(true);
	   bottom2.setBackground(new Color(109, 140, 212));
	   BottomOfScreen.add(bottom2);
	   JLabel bottom3 = new JLabel(" ");
	   bottom3.setOpaque(true);
	   bottom3.setBackground(new Color(158, 186, 245));
	   BottomOfScreen.add(bottom3);
	   
	   
   }

   /** The entry main() entry method */
   public static void main(String[] args) {
      // [TODO 1] Check "Swing program template" on how to run
      //  the constructor of "SudokuMain"
	   SwingUtilities.invokeLater(new Runnable() {
           @Override
           public void run() {
              new SudokuMain(); // Let the constructor does the job             
           }
      });
   }
   
}
