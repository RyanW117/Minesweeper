package minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class does most of the logic calculations.  It makes
 * sure that the all of the MinesweeperButtons have the correct
 * values according to the number of mines that are adjacent to them.
 * It also has a method that allows the user to click a tile with a
 * value of 0 which will automatically any other tile with 0 nearby (still
 * working on that).
 * 
 * @author Ryan Wilson
 */
public class MinesweeperPanel extends JPanel 
{
	private static final long serialVersionUID = 8695450935260311620L;
	protected static MinesweeperButton[][] buttonArray;
	private static List<MinesweeperButton> buttons;
	protected static int numberOfRows;
	protected static int numberOfColumns;
	private static JPanel buttonArrayPanel;
	private static boolean rightBoundaryHit;
	private static boolean upperBoundaryHit;
	private static boolean lowerBoundaryHit;
	private static boolean left_BoundaryHit;
	private static int tilesAvailable_UP;
	private static int tilesAvailable_RIGHT;
	private static int tilesAvailable_LEFT;
	private static int tilesAvailable_DOWN;
	private static int[] cursorMovements;
	
	/*
	 * Edit theses variables to change the game.
	 */
	private static int mines = 5;

	
	/**
	 * Initially create the panels.
	 * 
	 * @param rows
	 * @param columns
	 */
	public MinesweeperPanel(int rows, int columns)
	{
		numberOfRows = rows;
		numberOfColumns = columns;
		setLayout(new BorderLayout(5,5));
		setBackground(Color.WHITE);
		
		JPanel headerPanel = new JPanel();
		headerPanel.add(new JLabel("Minesweeper"));
		add(headerPanel, BorderLayout.NORTH);
		
		buttonArrayPanel = new JPanel();
		buttonArrayPanel.setLayout(new GridLayout(rows, 0, 3, 3));
		buttonArrayPanel.setBackground(Color.WHITE);
		add(buttonArrayPanel, BorderLayout.CENTER);
		
		createMinesweeperButtons(rows, columns);
	}

	/**
	 * This method creates and orders the MinesweeperButtons so that they
	 * appear in the correct logical way when playing the game.
	 * 
	 * @param rows
	 * @param columns
	 */
	public static void createMinesweeperButtons(int rows, int columns) 
	{		
		buttons = new ArrayList<>();
		
		for (int i = 0; i < mines; i++)
			buttons.add(new MinesweeperButton(0,0));
		for (int i = 0; i < ((rows * columns) - mines); i++)
			buttons.add(new MinesweeperButton(0,0,0));
		
		Collections.shuffle(buttons);
		
		buttonArray = new MinesweeperButton[rows][columns];
		int assignmentVariable = 0;
		
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
			{
				buttons.get(assignmentVariable).setRowNumber(i);
				buttons.get(assignmentVariable).setColumnNumber(j);				
				buttonArray[i][j] = buttons.get(assignmentVariable);
				assignmentVariable++;
			}
		
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				buttonArrayPanel.add(buttonArray[i][j]);
		
		minesweeperLogic(rows, columns);
	}
	
	/**
	 * This method finds all of the minds on the board and increments
	 * the value of all of the adjacent tiles by one.
	 * 
	 * @param rows
	 * @param columns
	 */
	private static void minesweeperLogic(int rows, int columns)
	{
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < columns; j++)
			{
				if (buttonArray[i][j].getIsMine())
				{
					//Bottom
					if (i + 1 >= 0 && i + 1 < (rows))
						if (!buttonArray[i + 1][j].getIsMine())
							buttonArray[i + 1][j].setNumber(buttonArray[i + 1][j].getNumber() + 1);
					
					//Bottom Right
					if ((i + 1 >= 0 && i + 1 < (rows)) && (j + 1 >= 0 && j + 1 < (columns)))
						if (!buttonArray[i + 1][j + 1].getIsMine())
							buttonArray[i + 1][j + 1].setNumber(buttonArray[i + 1][j + 1].getNumber() + 1);
					
					//Right
					if (j + 1 >= 0 && j + 1 < (columns))
						if (!buttonArray[i][j + 1].getIsMine())
							buttonArray[i][j + 1].setNumber(buttonArray[i][j + 1].getNumber() + 1);
					
					//Top Right
					if ((i - 1 >= 0 && i - 1 < (rows)) && (j + 1 >= 0 && j + 1 < (columns)))
						if (!buttonArray[i - 1][j + 1].getIsMine())
							buttonArray[i - 1][j + 1].setNumber(buttonArray[i - 1][j + 1].getNumber() + 1);
					
					//Top
					if (i - 1 >= 0 && i - 1 < (rows))
						if (!buttonArray[i - 1][j].getIsMine())
							buttonArray[i - 1][j].setNumber(buttonArray[i - 1][j].getNumber() + 1);
					
					//Top Left
					if ((i - 1 >= 0 && i - 1 < (rows)) && (j - 1 >= 0 && j - 1 < (columns)))
						if (!buttonArray[i - 1][j - 1].getIsMine())
							buttonArray[i - 1][j - 1].setNumber(buttonArray[i - 1][j - 1].getNumber() + 1);
					
					//Left
					if (j - 1 >= 0 && j - 1 < (columns))
						if (!buttonArray[i][j - 1].getIsMine())
							buttonArray[i][j - 1].setNumber(buttonArray[i][j - 1].getNumber() + 1);
					
					//Bottom Left
					if ((i + 1 >= 0 && i + 1 < (rows)) && (j - 1 >= 0 && j - 1 < (columns)))
						if (!buttonArray[i + 1][j - 1].getIsMine())
							buttonArray[i + 1][j - 1].setNumber(buttonArray[i + 1][j - 1].getNumber() + 1);
				}
			}
		}
	}

	/**
	 * A simple method that calculates how many mines there should be in a match.
	 * (Later I hope to make this customizable by the player).
	 * 
	 * @param rows
	 * @return the number of mines in the board.
	 */
	private static int assignNumberOfMines(int rows) 
	{
		int mines = (int) (rows * rows * rows * 0.02);
		if (mines >= ((int)(rows * rows) / 3))
			mines = ((int)(rows * rows) / 3);
		
		return mines;
	}
	
	/**
	 * A method that removes all buttons from the buttonArrayPanel.
	 */
	public static void removeAllButtons()
	{
		for (int i = 0; i < numberOfRows; i++)
			for (int j = 0; j < numberOfRows; j++)
				buttonArrayPanel.remove(buttonArray[i][j]);
	}
	
	/**
	 * This method reveals the tiles adjacent to the clicked button with a value of 0.
	 * Imagine a pointer that first moves down from the clicked button, then right,
	 * up, left, down again, and right again.  This is essentially the way this method
	 * checks for adjacent tiles with a value of 0.
	 * 
	 * @param rowNumber - r position of the clicked button.
	 * @param columnNumber - c position of the clicked button.
	 */
	protected static void revealNonNumberedSquares(int rowNumber, int columnNumber)
	{
		MinesweeperButton activeButton = null;
		tilesAvailable_UP = rowNumber;
		tilesAvailable_RIGHT = numberOfColumns - columnNumber - 1;
		tilesAvailable_LEFT = columnNumber;
		tilesAvailable_DOWN = numberOfRows - rowNumber - 1;
		
		//This stands for: move down 1, right 1, up 2, left 2, down 2, and finally right 0.
		//After the while loop: {2, 2, 4, 4, 4, 1}.  After the next: {3, 3, 6, 6, 6, 2}.
		cursorMovements = new int[] {1,1,2,2,2,0};
		
		int layer = 1;
		while (layer < 3)	//layer < 10 is not necessary.  It could be layer < 5 if you want.
		{
			revealNonNumberedSquares_resetVariables(rowNumber, columnNumber);
			printVariableInfo(rowNumber, columnNumber, layer);
			
			//This try block is just for testing.
			try
			{
				if (!lowerBoundaryHit)
				{
					//Initially Down
					for (int i = 1; i <= cursorMovements[0]; i++)
					{
							activeButton = buttonArray[rowNumber + i][columnNumber];
							if (!activeButton.getIsMine())
								showButtonNumber(activeButton);
					}
				
					//Initially Right
					for (int i = 1; i <= cursorMovements[1]; i++)
					{
							if (columnNumber + i < numberOfRows)
								activeButton = buttonArray[rowNumber + cursorMovements[0]][columnNumber + i];
							if (!activeButton.getIsMine())
								showButtonNumber(activeButton);
					}
					
					//Finally Right 
					for (int i = 1; i <= cursorMovements[5]; i++)
					{
						if (columnNumber - 1 - (i - 1) >= 0 && columnNumber - 1 - (i - 1) < numberOfRows)
							activeButton = buttonArray[rowNumber + cursorMovements[0]][columnNumber - 1 - (i - 1)];
						if (!activeButton.getIsMine())
							showButtonNumber(activeButton);
					}
				}
				
				if (!rightBoundaryHit)
				{
					//UP
					for (int i = 1; i <= cursorMovements[2]; i++)
					{
						if (rowNumber + 1 - i + (layer - 1) >= 0 && rowNumber + 1 - i + (layer - 1) < numberOfRows)
							activeButton = buttonArray[rowNumber + 1 - i + (layer - 1)][columnNumber + cursorMovements[0]];
						if (!activeButton.getIsMine())
							showButtonNumber(activeButton);
					}
				}
				
				if (!upperBoundaryHit)
				{
					//Left
					for (int i = 1; i <= cursorMovements[3]; i++)
					{
						if (columnNumber - i + 1 + (layer - 1) < numberOfRows && columnNumber - i + 1 + (layer - 1) >= 0) {
							activeButton = buttonArray[rowNumber - cursorMovements[0]][columnNumber - i + 1 + (layer - 1)];
							if (!activeButton.getIsMine())
								showButtonNumber(activeButton);
						}
					}
				}
				
				if (!left_BoundaryHit)
				{
					//Finally Down
					for (int i = 1; i <= cursorMovements[4]; i++)
					{
						if (rowNumber + i - 1 - (layer - 1) < numberOfRows && rowNumber + i - 1 - (layer - 1) >= 0)
							activeButton = buttonArray[rowNumber + i - 1 - (layer - 1)][columnNumber - cursorMovements[0]];
						if (!activeButton.getIsMine())
							showButtonNumber(activeButton);
					}
				}
				
				layer++;
				revealNonNumberedSquares_recalculateCursorMovements();
			} 
			catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("\nUpper " + upperBoundaryHit + "\nRight " + rightBoundaryHit + "\nLower " + lowerBoundaryHit + "\nLeft " + left_BoundaryHit);
			}
		}
	}
	
	/**
	 * Resets all of the necessary variables needed in the revealNonNumberedSquares method.
	 * 
	 * @param rowNumber
	 * @param columnNumber
	 */
	private static void revealNonNumberedSquares_resetVariables(int rowNumber, int columnNumber)
	{
		rightBoundaryHit = true;
		upperBoundaryHit = true;
		lowerBoundaryHit = true;
		left_BoundaryHit = true;
		
		if (tilesAvailable_DOWN >= cursorMovements[0])
			lowerBoundaryHit = false;
		if (tilesAvailable_RIGHT >= cursorMovements[0])
			rightBoundaryHit = false;
		if (tilesAvailable_LEFT >= cursorMovements[0])
			left_BoundaryHit = false;
		if (tilesAvailable_UP >= cursorMovements[0])
			upperBoundaryHit = false;
	}
	
	/**
	 * increments the cursorMovements array in a specific way.
	 */
	private static void revealNonNumberedSquares_recalculateCursorMovements()
	{
		for (int i = 0; i < cursorMovements.length; i++)
		{
			if (i <= 1 || i == 5)
				cursorMovements[i]++;
			else
				cursorMovements[i]+=2;
		}
	}
	
	/**
	 * Shows the button's number and sets the button to disabled.
	 * 
	 * @param activeButton
	 */
	private static void showButtonNumber(MinesweeperButton activeButton)
	{
		activeButton.setEnabled(false);
		if (activeButton.getNumber() != 0)
			activeButton.setText(activeButton.getNumber() + "");
	}
	
	/**
	 * Primarily used for testing.  Shows all of the values of the variables
	 * that could go wrong.
	 * 
	 * @param rowNumber
	 * @param columnNumber
	 * @param layers
	 */
	private static void printVariableInfo(int rowNumber, int columnNumber, int layers)
	{
		System.out.println("\nTiles UP: " + tilesAvailable_UP +
				"\nTiles RIGHT: " + tilesAvailable_RIGHT +
				"\nTiles DOWN: " + tilesAvailable_DOWN +
				"\nTiles LEFT: " + tilesAvailable_LEFT +
				"\nRow: " + rowNumber + "   " + "Column: " + columnNumber +
				"\nCursor Movements: " + Arrays.toString(cursorMovements) +
				"\nLayers: " + layers);
	}
	
	public static int getNumberOfRows()
	{
		return numberOfRows;
	}
	
}
