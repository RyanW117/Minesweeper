package minesweeper;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

public class MinesweeperPanel extends JPanel 
{
	private static final long serialVersionUID = 8695450935260311620L;
	private MinesweeperButton[][] buttonArray;
	private List<MinesweeperButton> buttons;
	
	public MinesweeperPanel(int rows)
	{
		setLayout(new GridLayout(rows, 0, 3, 3));
		setOpaque(true);
		setBackground(Color.WHITE);
		
		createMinesweeperButtons(rows);
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < rows; j++)
				add(buttonArray[i][j]);
	}

	private void createMinesweeperButtons(int rows) 
	{
		int mines = assignNumberOfMines(rows);
		
		buttons = new ArrayList<>();
		
		for (int i = 0; i < mines; i++)
			buttons.add(new MinesweeperButton());
		for (int i = 0; i < ((rows * rows) - mines); i++)
			buttons.add(new MinesweeperButton(0));
		
		Collections.shuffle(buttons);
		
		buttonArray = new MinesweeperButton[rows][rows];
		int assignmentVariable = 0;
		
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < rows; j++)
				buttonArray[i][j] = buttons.get(assignmentVariable++);
		
		minesweeperLogic(rows);
	}
	
	private void minesweeperLogic(int rows)
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

	private int assignNumberOfMines(int rows) 
	{
		int mines;
		if (rows == 8)
			mines = (int)((rows * rows * 0.175));
		else if (rows == 10)
			mines = (int)((rows * rows * 0.2));
		else if (rows == 13)
			mines = (int)((rows * rows * 0.225));
		else
			mines = (int)((rows * rows * 0.25));
		return mines;
	}
}
