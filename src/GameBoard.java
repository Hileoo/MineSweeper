import javax.swing.*;

import java.awt.*;

import javax.swing.event.*;

import java.awt.event.*;
/**
 * This class provides a graphical model of a board game.
 * The class creates a rectangular panel of clickable squares,
 * of type SmartSquare. If a square is clicked by the user, a
 * callback method is invoked upon the corresponding SmartSquare instance.
 * The class is intended to be used as a basis for tile based games.
 * @author joe finney
 */
public class GameBoard extends JFrame implements MouseListener
{
	private JPanel boardPanel = new JPanel();

	private int boardHeight;
	private int boardWidth;
	private GameSquare[][] board; 

	/**
	 * Create a new game board of the given size.
	 * As soon as an instance of this class is created, it is visualized
	 * on the screen.
	 * 
	 * @param title the name printed in the window bar.
	 * @param width the width of the game area, in squares.
	 * @param height the height of the game area, in squares.
	 */
	public GameBoard(String title, int width, int height)
	{
		super();

		this.boardWidth = width;
		this.boardHeight = height;

		// Create game state
		this.board = new GameSquare[width][height];

		// Set up window
		setTitle(title);
		setSize(20+width*20,20+height*20);
		setContentPane(boardPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		boardPanel.setLayout(new GridLayout(height,width));
		
		for (int y = 0; y<height; y++)
		{
			for (int x = 0; x<width; x++)
			{
				board[x][y] = new SmartSquare(x, y, this);
				board[x][y].addMouseListener(this);

				boardPanel.add(board[x][y]);
			}
		}

		// make our window visible
		setVisible(true);
				
	}

	/**
	 * Returns the GameSquare instance at a given location. 
	 * @param x the x co-ordinate of the square requested.
	 * @param y the y co-ordinate of the square requested.
	 * @return the GameSquare instance at a given location 
	 * if the x and y co-ordinates are valid - null otherwise. 
	 */
	public GameSquare getSquareAt(int x, int y)
	{
		if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight)
			return null;

		return board[x][y];
	}

	public void mouseClicked(MouseEvent e)
	{
		GameSquare b = (GameSquare)e.getSource();
		// Left Click
		if(e.getButton() == MouseEvent.BUTTON1)
			b.clicked();
		
		// Right Click
		if(e.getButton() == MouseEvent.BUTTON3)
			b.rightClicked();
	}
	
	/**
	 * Get the value of board-size, it means the product of boardWidth and boardHeight
	 * @return boardWidth * boardHeight
	 */
	public int getBoardSize()
	{
		return boardWidth * boardHeight;
	}

public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
}
