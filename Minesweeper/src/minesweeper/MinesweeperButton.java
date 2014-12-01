package minesweeper;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MinesweeperButton extends JButton
{
	private static final long serialVersionUID = 3259047934452202538L;
	private boolean isMine;
	private int number;
	private static boolean gameOver = false;
	
	public MinesweeperButton()
	{
		isMine = true;
		number = -1;
		addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setBackground(Color.RED);
				setEnabled(false);
				gameOver = true;
			}
		});
	}
	
	public MinesweeperButton(int number)
	{
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
					setText("");
				else
					setText(getNumber() + "");
				
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

	public static boolean isGameOver() 
	{
		return gameOver;
	}
	
	
}
