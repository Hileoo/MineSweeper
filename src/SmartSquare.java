import javax.swing.*;
import java.awt.*;
import java.util.*;
/**
 * This class defines whether the square contains a bomb and provides the logical operation for each square.
 * If a specific square is clicked (overriding the click() from GameSquare, it will show all the bombs which means game-over if this square contains a bomb;
 * It will recursive to reveal all the surrounding empty squares and display the number of bombs in surrounding squares if this square does not contain a bomb.
 * If a specific square is right-clicked, it will set/remove the flag identifier in order to help the player to mark for suspect bombs.
 * The congratulations message-dialog will be shown if all the not-bomb-square be revealed, which means the player complete the game successfully.
 * @author Li Xinlong
 */
public class SmartSquare extends GameSquare {
	/** Status for this square **/
	private boolean thisSquareHasBomb = false;
	public static final int MINE_PROBABILITY = 10;
	private boolean swept = false;
	private boolean flaged = false;
	
	/** Status and statistics for whole game **/
	private static boolean gameEnd = false;
	private static int totalBomb = 0;
	private static int totalSwept = 0;

	/**
	 * Constructor for creating a new square (random to contains a bomb) which comprised the game-square.
	 * @param x  the x coordinate of this square on the game board.
	 * @param y  the y coordinate of this square on the game board.
	 * @param board  the GameBoard upon which this square resides.
	 */
	public SmartSquare(int x, int y, GameBoard board) {
		// Call the constructor method in parent class
		super(x, y, "images/blank.png", board);
		
		// Random to decide whether this square contains a bomb
		Random r = new Random();
		thisSquareHasBomb = (r.nextInt(MINE_PROBABILITY) == 0);
		if(thisSquareHasBomb)
			totalBomb ++;			
	}

	/**
	 * A method that overriding the click method from GameSquare class.
	 */
	public void clicked() {
		// Count the total square which has been swept
		if(!swept)
			totalSwept++;
		
		// Decided operation base on square status
		swept = true;
		if(!gameEnd) {
			if(thisSquareHasBomb)
				showAllBombs();
			else
				revealSquares();
		}
	}
	
	/**
	 * A method that overriding the rightClick method from GameSquare class.
	 * Right-click to set/remove flags for marking the suspect bomb-square.
	 */
	public void rightClicked() {
		if(!gameEnd) {
			if(!swept) {
				if(!flaged) {
					this.setImage("images/flag.png");
					flaged = true;
				}
				else {
					this.setImage("images/blank.png");
					flaged = false;
				}
			}		
		}
	}
	
	/**
	 * A method that reveal all the surrounding empty squares and display number of surrounding bombs when clicked.
	 */
	public void revealSquares() {
		int bombsCount = 0;
		
		// Scan surrounding 9 squares iteratively
		for(int x = (this.xLocation - 1); x <= (this.xLocation + 1); x++) {
			for(int y = (this.yLocation - 1); y <= (this.yLocation + 1); y++) {
				SmartSquare square = (SmartSquare) board.getSquareAt(x,y);
				if(square != null && square.thisSquareHasBomb)
					bombsCount++;
			}
		}
		// Display the number of surrounding bombs
		this.setImage("images/" + bombsCount + ".png");
		
		// Show message if all the non-bomb squares were revealed
		if(totalSwept == board.getBoardSize() - totalBomb) {
			SmartSquare.gameEnd = true;
			JOptionPane.showMessageDialog(this, "You Win!");
		}
		
		// Reveal all the empty squares by calling clicked() method recursively
		if(bombsCount == 0) {
			for(int x = (this.xLocation - 1); x <= (this.xLocation + 1); x++) {
				for(int y = (this.yLocation - 1); y <= (this.yLocation + 1); y++) {
					SmartSquare zeroSquare = (SmartSquare)board.getSquareAt(x, y);
					if(zeroSquare != null && !zeroSquare.swept)
						zeroSquare.clicked();
				}
			}
		}	
	}
	
	/**
	 * A method that display all the bombs in game-board when the player clicks a square contains bomb, that means you failed the game.
	 */
	public void showAllBombs() {
		SmartSquare.gameEnd = true;
		
		// Set the coordinate to (0,0)
		this.xLocation = 0;
		this.yLocation = 0;
		
		// Scan all the squares in game-board
		while(true) {
			SmartSquare scanSquare = (SmartSquare)this.board.getSquareAt(this.xLocation, this.yLocation);
			
			// Scan vertically until the upper boundary
			if(scanSquare != null) {
				if(scanSquare.thisSquareHasBomb)
					scanSquare.setImage("images/bomb.png");
				this.yLocation++;
			}
			// Set to scan the right adjacent column from bottom
			else {
				this.yLocation = 0;
				this.xLocation++;
				// Break the iterate until has scanned all the squares
				if ((SmartSquare)board.getSquareAt((this.xLocation),(this.yLocation)) == null)
					break;
			}				
		}
	}	
}
