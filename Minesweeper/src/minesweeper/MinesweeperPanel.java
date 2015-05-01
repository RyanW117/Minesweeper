package minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
	
	static Iterator<MinesweeperButton> it;
	
	/*
	 * Edit theses variables to change the game.
	 */
	private static double percentageOfMines = 0.10;
	private static Set<MinesweeperButton> adjacentTilesSet;
	
	/**
	 * Initially create the panels.
	 * 
	 * @param rows
	 * @param columns
	 */
	public MinesweeperPanel(int rows, int columns)
	{
		adjacentTilesSet = new HashSet<>();
		numberOfRows = rows;
		numberOfColumns = columns;
		setLayout(new BorderLayout(5,5));
		setBackground(Color.WHITE);
		
		JPanel headerPanel = new JPanel();
		headerPanel.add(new JLabel("Minesweeper"));
		add(headerPanel, BorderLayout.NORTH);
		
		buttonArrayPanel = new JPanel();
		buttonArrayPanel.setLayout(new GridLayout(rows, 0, 3, 3));
		buttonArrayPanel.setBackground(Color.BLACK);
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
		buttonArray = new MinesweeperButton[rows][columns];
		buttons = new ArrayList<>();
		int mines = assignNumberOfMines(rows, columns);
		
		//Create the tiles that ARE mines
		for (int i = 0; i < mines; i++)
			buttons.add(new MinesweeperButton(0,0));
		//Create the tiles that are NOT mines
		for (int i = 0; i < ((rows * columns) - mines); i++)
			buttons.add(new MinesweeperButton(0,0,0));
		
		Collections.shuffle(buttons);
		
		//After Shuffled, correct all of the row and column numbers for each button.
		int assignmentVariable = 0;
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < columns; j++)
			{
				buttons.get(assignmentVariable).setRowNumber(i);
				buttons.get(assignmentVariable).setColumnNumber(j);				
				buttonArray[i][j] = buttons.get(assignmentVariable);
				assignmentVariable++;
			}
		}
		
		//Add the buttons to the panel.
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				buttonArrayPanel.add(buttonArray[i][j]);
		
		minesweeperLogic(rows, columns);
	}
	
	/**
	 * This method finds all of the mines on the board and increments
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
					if (i + 1 < (rows))
						if (!buttonArray[i + 1][j].getIsMine())
							buttonArray[i + 1][j].setNumber(buttonArray[i + 1][j].getNumber() + 1);
					
					//Bottom Right
					if (i + 1 < (rows) && j + 1 < (columns))
						if (!buttonArray[i + 1][j + 1].getIsMine())
							buttonArray[i + 1][j + 1].setNumber(buttonArray[i + 1][j + 1].getNumber() + 1);
					
					//Right
					if (j + 1 < (columns))
						if (!buttonArray[i][j + 1].getIsMine())
							buttonArray[i][j + 1].setNumber(buttonArray[i][j + 1].getNumber() + 1);
					
					//Top Right
					if ((i - 1 >= 0 && i - 1 < (rows)) && (j + 1 < (columns)))
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
					if ((i + 1 < (rows)) && (j - 1 >= 0 && j - 1 < (columns)))
						if (!buttonArray[i + 1][j - 1].getIsMine())
							buttonArray[i + 1][j - 1].setNumber(buttonArray[i + 1][j - 1].getNumber() + 1);
				}
			}
		}
		boolean tile0Found = true; 
		while (tile0Found)
		{
			double randRow = Math.random() * numberOfRows;
			double randCol = Math.random() * numberOfColumns;
			
			if (buttonArray[(int) randRow][(int) randCol].getNumber() == 0)
			{
				buttonArray[(int) randRow][(int) randCol].setColor(Color.GREEN);
				tile0Found = false;
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
	private static int assignNumberOfMines(int rows, int columns)
	{
		return (int) ((rows * columns) * percentageOfMines);
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
	
	public static void addAdjacentBlankTilesToSet(int rows, int columns)
	{
		MinesweeperButton activeButton = buttonArray[rows][columns];
		int ring = 1;
		adjacentTilesSet.add(activeButton);
		
		if (columns - 1 >= 0 && columns + 1 < numberOfColumns && rows - 1 >= 0 && rows + 1 < numberOfRows)
		{
			if (buttonArray[rows - 1][columns].getNumber() == 0)
			{
				adjacentTilesSet.add(buttonArray[rows - 1][columns]);
			}
			
			
			if (buttonArray[rows - 1][columns + 1].getNumber() == 0)
			{
				adjacentTilesSet.add(buttonArray[rows - 1][columns + 1]);
			}
			
			
			if (buttonArray[rows - 1][columns - 1].getNumber() == 0)
			{
				adjacentTilesSet.add(buttonArray[rows - 1][columns - 1]);
			}
			
			
			if (buttonArray[rows][columns + ring].getNumber() == 0)
			{
				adjacentTilesSet.add(buttonArray[rows][columns + 1]);
			}
			
		
			if (buttonArray[rows][columns - ring].getNumber() == 0)
			{
				adjacentTilesSet.add(buttonArray[rows][columns - ring]);
			}
			
			
			if (buttonArray[rows + ring][columns].getNumber() == 0)
			{
				adjacentTilesSet.add(buttonArray[rows + ring][columns]);
			}
			
			
			if (buttonArray[rows + ring][columns + ring].getNumber() == 0)
			{
				adjacentTilesSet.add(buttonArray[rows + ring][columns + ring]);
			}
			
			
			if (buttonArray[rows + ring][columns - ring].getNumber() == 0)
			{
				adjacentTilesSet.add(buttonArray[rows + ring][columns - ring]);
			}
			
			buttonArray[rows - 1][columns].revealButton();
			buttonArray[rows - 1][columns + 1].revealButton();
			buttonArray[rows - 1][columns - 1].revealButton();
			buttonArray[rows][columns + 1].revealButton();
//			buttonArray[rows][columns + 1].justShowTheButton();
//			buttonArray[rows + 1][columns].justShowTheButton();
//			buttonArray[rows + 1][columns + 1].justShowTheButton();
//			buttonArray[rows + 1][columns - 1].justShowTheButton();
		}
			
	}
	
	public static int getNumberOfRows()
	{
		return numberOfRows;
	}
	
}

