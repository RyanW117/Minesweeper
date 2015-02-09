package minesweeper;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * The class that defines the MinesweeperButton.
 * Minesweeper buttons can either be a bomb or a number.
 * The numbers can vary between 0 ("") and 8.
 * 
 * @author Ryan Wilson
 */
public class MinesweeperButton extends JButton
{
	private static final long serialVersionUID = 3259047934452202538L;
	private boolean isMine;
	private boolean hasBeenRevealed = false;
	private int number;
	private int rowNumber;
	private int columnNumber;
		
	public MinesweeperButton(int r, int c)
	{
		rowNumber = r;
		columnNumber = c;
		isMine = true;
		number = -1;

		addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setBackground(Color.RED);
				setEnabled(false);
			}
		});
	}
	
	/**
	 * This constructor is for buttons that are not mines.
	 * If the number is 0, it is a blank tile (there are no
	 * mines next to that tile).
	 * 
	 * @param The value of the button after it is pressed.
	 * @param r rows
	 * @param c columns
	 */
	public MinesweeperButton(int number, int r, int c)
	{
		rowNumber = r;
		columnNumber = c;
		this.number = number;
		isMine = false;
		setForeground(Color.WHITE);
		setOpaque(true);
		addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFont(new Font("Verdana", Font.BOLD, 18));
				setBackground(Color.WHITE);
				
				if (getNumber() == 0)
				{
					setEnabled(false);
					MinesweeperPanel.revealNonNumberedSquares(rowNumber, columnNumber);
				}
				else
				{
					setText(getNumber() + "");
				}
				
				setEnabled(false);
			}
		});
	}
	
	public int getNumber()
	{
		return number;
	}
	

	public void setNumber(int number) 
	{
		this.number = number;
	}

	public boolean getIsMine() 
	{
		return isMine;
	}

	public void setRowNumber(int rowNumber) 
	{
		this.rowNumber = rowNumber;
	}

	public void setColumnNumber(int columnNumber) 
	{
		this.columnNumber = columnNumber;
	}

	public boolean buttonHasBeenRevealed()
	{
		return hasBeenRevealed;
	}

	public void setButtonHasBeenRevealed(boolean hasBeenRevealed) 
	{
		this.hasBeenRevealed = hasBeenRevealed;
	}
	
	
}
