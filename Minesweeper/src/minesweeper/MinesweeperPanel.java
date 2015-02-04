package minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MinesweeperPanel extends JPanel 
{
	private static final long serialVersionUID = 8695450935260311620L;
	protected static MinesweeperButton[][] buttonArray;
	private static List<MinesweeperButton> buttons;
	protected static int numberOfRows;
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
	
	public MinesweeperPanel(int rows)
	{
		numberOfRows = rows;
		setLayout(new BorderLayout(5,5));
		setBackground(Color.WHITE);
		
		JPanel headerPanel = new JPanel();
		headerPanel.add(new JLabel("Minesweeper"));
		add(headerPanel, BorderLayout.NORTH);
		
		buttonArrayPanel = new JPanel();
		buttonArrayPanel.setLayout(new GridLayout(rows, 0, 3, 3));
		buttonArrayPanel.setBackground(Color.WHITE);
		add(buttonArrayPanel, BorderLayout.CENTER);
		
		createMinesweeperButtons(rows);
	}

	public static void createMinesweeperButtons(int rows) 
	{
		int mines = assignNumberOfMines(rows);
		
		buttons = new ArrayList<>();
		
		for (int i = 0; i < mines; i++)
			buttons.add(new MinesweeperButton(0,0));
		for (int i = 0; i < ((rows * rows) - mines); i++)
			buttons.add(new MinesweeperButton(0,0,0));
		
		Collections.shuffle(buttons);
		
		buttonArray = new MinesweeperButton[rows][rows];
		int assignmentVariable = 0;
		
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < rows; j++)
			{
				buttons.get(assignmentVariable).setRowNumber(i);
				buttons.get(assignmentVariable).setColumnNumber(j);				
				buttonArray[i][j] = buttons.get(assignmentVariable);
				assignmentVariable++;
			}
		
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < rows; j++)
				buttonArrayPanel.add(buttonArray[i][j]);
		
		minesweeperLogic(rows);
	}
	
	private static void minesweeperLogic(int rows)
	{
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < rows; j++)
			{
				if (buttonArray[i][j].getIsMine())
				{
					//Bottom
					if (i + 1 >= 0 && i + 1 < (rows))
						if (!buttonArray[i + 1][j].getIsMine())
							buttonArray[i + 1][j].setNumber(buttonArray[i + 1][j].getNumber() + 1);
					
					//Bottom Right
					if ((i + 1 >= 0 && i + 1 < (rows)) && (j + 1 >= 0 && j + 1 < (rows)))
						if (!buttonArray[i + 1][j + 1].getIsMine())
							buttonArray[i + 1][j + 1].setNumber(buttonArray[i + 1][j + 1].getNumber() + 1);
					
					//Right
					if (j + 1 >= 0 && j + 1 < (rows))
						if (!buttonArray[i][j + 1].getIsMine())
							buttonArray[i][j + 1].setNumber(buttonArray[i][j + 1].getNumber() + 1);
					
					//Top Right
					if ((i - 1 >= 0 && i - 1 < (rows)) && (j + 1 >= 0 && j + 1 < (rows)))
						if (!buttonArray[i - 1][j + 1].getIsMine())
							buttonArray[i - 1][j + 1].setNumber(buttonArray[i - 1][j + 1].getNumber() + 1);
					
					//Top
					if (i - 1 >= 0 && i - 1 < (rows))
						if (!buttonArray[i - 1][j].getIsMine())
							buttonArray[i - 1][j].setNumber(buttonArray[i - 1][j].getNumber() + 1);
					
					//Top Left
					if ((i - 1 >= 0 && i - 1 < (rows)) && (j - 1 >= 0 && j - 1 < (rows)))
						if (!buttonArray[i - 1][j - 1].getIsMine())
							buttonArray[i - 1][j - 1].setNumber(buttonArray[i - 1][j - 1].getNumber() + 1);
					
					//Left
					if (j - 1 >= 0 && j - 1 < (rows))
						if (!buttonArray[i][j - 1].getIsMine())
							buttonArray[i][j - 1].setNumber(buttonArray[i][j - 1].getNumber() + 1);
					
					//Bottom Left
					if ((i + 1 >= 0 && i + 1 < (rows)) && (j - 1 >= 0 && j - 1 < (rows)))
						if (!buttonArray[i + 1][j - 1].getIsMine())
							buttonArray[i + 1][j - 1].setNumber(buttonArray[i + 1][j - 1].getNumber() + 1);
				}
			}
		}
	}

	private static int assignNumberOfMines(int rows) 
	{
		int mines = (int) (rows * rows * rows * 0.02);
		if (mines >= ((int)(rows * rows) / 3))
			mines = ((int)(rows * rows) / 3);
		
		return mines;
	}
	
	public static int getNumberOfRows()
	{
		return numberOfRows;
	}
	
	public static void removeAllButtons()
	{
		for (int i = 0; i < numberOfRows; i++)
			for (int j = 0; j < numberOfRows; j++)
				buttonArrayPanel.remove(buttonArray[i][j]);
	}
	
	protected static void revealNonNumberedSquares(int rowNumber, int columnNumber)
	{
		MinesweeperButton activeButton = null;
		cursorMovements = new int[] {1,1,2,2,2,0};
		
		int layers = 1;
		while (layers < 10)
		{
			revealNonNumberedSquares_resetVariables(rowNumber, columnNumber);
			
			System.out.println("\nTiles UP: " + tilesAvailable_UP +
					"\nTiles RIGHT: " + tilesAvailable_RIGHT +
					"\nTiles DOWN: " + tilesAvailable_DOWN +
					"\nTiles LEFT: " + tilesAvailable_LEFT +
					"\nRow: " + rowNumber + "   " + "Column: " + columnNumber +
					"\nCursor Movements: " + Arrays.toString(cursorMovements) +
					"\nLayers: " + layers);
					
			try
			{
				//Initially Down and Initially RIGHT and Finally Right
				if (!lowerBoundaryHit)
				{
					for (int i = 1; i <= cursorMovements[0]; i++)
					{
							activeButton = buttonArray[rowNumber + i][columnNumber];
							if (!activeButton.getIsMine())
								showButtonNumber(activeButton);
					}
				
					for (int i = 1; i <= cursorMovements[1]; i++)
					{
							if (columnNumber + i < numberOfRows)
								activeButton = buttonArray[rowNumber + cursorMovements[0]][columnNumber + i];
							if (!activeButton.getIsMine())
								showButtonNumber(activeButton);
					}
					
					for (int i = 1; i <= cursorMovements[5]; i++)
					{
						if (columnNumber - 1 - (i - 1) >= 0 && columnNumber - 1 - (i - 1) < numberOfRows)
							activeButton = buttonArray[rowNumber + cursorMovements[0]][columnNumber - 1 - (i - 1)];
						if (!activeButton.getIsMine())
							showButtonNumber(activeButton);
					}
				}
				
				//UP
				if (!rightBoundaryHit)
				{
					for (int i = 1; i <= cursorMovements[2]; i++)
					{
						if (rowNumber + 1 - i + (layers - 1) >= 0 && rowNumber + 1 - i + (layers - 1) < numberOfRows)
							activeButton = buttonArray[rowNumber + 1 - i + (layers - 1)][columnNumber + cursorMovements[0]];
						if (!activeButton.getIsMine())
							showButtonNumber(activeButton);
					}
				}
				
				//Left
				if (!upperBoundaryHit)
				{
					for (int i = 1; i <= cursorMovements[3]; i++)
					{
						if (columnNumber - i + 1 + (layers - 1) < numberOfRows && columnNumber - i + 1 + (layers - 1) >= 0)
							activeButton = buttonArray[rowNumber - cursorMovements[0]][columnNumber - i + 1 + (layers - 1)];
						if (!activeButton.getIsMine())
							showButtonNumber(activeButton);
					}
				}
		
				//Finally Down
				if (!left_BoundaryHit)
				{
					for (int i = 1; i <= cursorMovements[4]; i++)
					{
						if (rowNumber + i - 1 - (layers - 1) < numberOfRows && rowNumber + i - 1 - (layers - 1) >= 0)
							activeButton = buttonArray[rowNumber + i - 1 - (layers - 1)][columnNumber - cursorMovements[0]];
						if (!activeButton.getIsMine())
							showButtonNumber(activeButton);
					}
				}
				layers++;
				revealNonNumberedSquares_recalculateCursorMovements();
				System.out.println("\nUpper " + upperBoundaryHit + "\nRight " + rightBoundaryHit + "\nLower " + lowerBoundaryHit + "\nLeft " + left_BoundaryHit);
			} catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("\nUpper " + upperBoundaryHit + "\nRight " + rightBoundaryHit + "\nLower " + lowerBoundaryHit + "\nLeft " + left_BoundaryHit);
			}
		}
	}
	
	private static void revealNonNumberedSquares_resetVariables(int rowNumber, int columnNumber)
	{
		rightBoundaryHit = true;
		upperBoundaryHit = true;
		lowerBoundaryHit = true;
		left_BoundaryHit = true;
		tilesAvailable_UP = rowNumber;
		tilesAvailable_RIGHT = numberOfRows - columnNumber - 1;	//Should be columnNumber - numberOfColumns
		tilesAvailable_LEFT = columnNumber;
		tilesAvailable_DOWN = numberOfRows - rowNumber - 1;
		
		if (tilesAvailable_DOWN >= cursorMovements[0])
			lowerBoundaryHit = false;
		if (tilesAvailable_RIGHT >= cursorMovements[0])
			rightBoundaryHit = false;
		if (tilesAvailable_LEFT >= cursorMovements[0])
			left_BoundaryHit = false;
		if (tilesAvailable_UP >= cursorMovements[0])
			upperBoundaryHit = false;
	}
	
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
	
	private static void showButtonNumber(MinesweeperButton activeButton)
	{
		activeButton.setEnabled(false);
		if (activeButton.getNumber() != 0)
			activeButton.setText(activeButton.getNumber() + "");
	}	
}
