package minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * This class contains most of the GUI aspect of the program and it launches the program.
 * 
 * @author Ryan Wilson
 */
public class InitialFrame extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JPanel mainPanel;
	private JPanel gameStartPanel;
	
	private MinesweeperPanel minesweeperPanel;
	
	private JTextField textField_Rows;
	private JTextField textField_Columns;

	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					InitialFrame frame = new InitialFrame();
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructor
	 * 
	 * Contains two panels.
	 */
	public InitialFrame() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(5, 5));
		setContentPane(contentPane);
		
		//The "Cards", panel1
		gameStartPanel = createGameStartPanel();
				
		//mainPanel contains the cards.
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(5,5));
		mainPanel.setBackground(Color.RED);
		mainPanel.add(gameStartPanel, BorderLayout.CENTER);
		
		contentPane.add(mainPanel, BorderLayout.CENTER);
	}

	/**
	 * This panel allows the player to pick how many rows they want.
	 * This is the first panel the user sees when the game is started.
	 * 
	 * @return A formatted Panel
	 */
	private JPanel createGameStartPanel() 
	{
		JPanel gameStartPanel = new JPanel();
		gameStartPanel.setLayout(new BorderLayout(15, 15));
		gameStartPanel.setBackground(Color.BLACK);
		
		JLabel label = new JLabel("Welcome to Minesweeper!!");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Verdana", Font.PLAIN, 30));
		label.setOpaque(false);
		gameStartPanel.add(label, BorderLayout.CENTER);
		
		JPanel inputPanel = createInputPanel_GameStartPanel();
		gameStartPanel.add(inputPanel, BorderLayout.SOUTH);
		
		return gameStartPanel;
	}

	/**
	 * This panel is located to the SOUTH of the gameStartPanel and
	 * has the textfield where the user can enter the number of rows
	 * he or she wants.
	 * 
	 * @return A panel inside the gameStartPanel
	 */
	private JPanel createInputPanel_GameStartPanel() 
	{
		JPanel inputPanel = new JPanel();
		inputPanel.setBackground(Color.DARK_GRAY);
		
		JLabel enterRowsLabel = new JLabel("Enter the number of rows :  ");
		enterRowsLabel.setForeground(Color.WHITE);
		enterRowsLabel.setFont(new Font("Verdana", Font.PLAIN, 16));
		inputPanel.add(enterRowsLabel);
		
		textField_Rows = createTextField_GameStartPanel();
		inputPanel.add(textField_Rows);
		
		JLabel enterColumnsLabel = new JLabel("Enter the number of columns :  ");
		enterColumnsLabel.setForeground(Color.WHITE);
		enterColumnsLabel.setFont(new Font("Verdana", Font.PLAIN, 16));
		inputPanel.add(enterColumnsLabel);
		
		textField_Columns = createTextField_GameStartPanel();
		inputPanel.add(textField_Columns);
		
		JButton playGameButton = createPlayGameButton_GameStartPanel();
		inputPanel.add(playGameButton);
		
		return inputPanel;
	}

	/**
	 * A button that has an ActionListener that changes panels
	 * to the Minesweeper Panel.
	 * 
	 * @return A button that is used to start Minesweeper
	 */
	private JButton createPlayGameButton_GameStartPanel() 
	{
		JButton playGameButton = new JButton("Play Game!");
		playGameButton.setFont(new Font("Verdana", Font.PLAIN, 20));
		playGameButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				mainPanel.remove(gameStartPanel);
				
				minesweeperPanel = new MinesweeperPanel(
						Integer.parseInt(validateAndReturnInput(textField_Rows.getText())),
						Integer.parseInt(validateAndReturnInput(textField_Columns.getText())));
				mainPanel.add(minesweeperPanel, BorderLayout.CENTER);
				
				revalidate();
				repaint();
			}
		});
		return playGameButton;
	}

	/**
	 * Returns a JTextField with some properties.
	 * 
	 * @return a modified JTextField
	 */
	private JTextField createTextField_GameStartPanel()
	{
		JTextField textField = new JTextField();
		textField.setColumns(5);
		textField.setFont(new Font("Verdana", Font.BOLD, 16));
		
		return textField;
	}
	
	/**
	 * A method that tests the player's input for letters or spaces and removes them.
	 * Also, if the number is too big, it returns 20.
	 * 
	 * @param textFieldInput
	 * @return
	 */
	private String validateAndReturnInput(String textFieldInput)
	{
		if (textFieldInput == null || textFieldInput.equalsIgnoreCase(""))
		{
			textFieldInput = "8";
			return textFieldInput;
		}
		
		textFieldInput = textFieldInput.replaceAll("[^0-9]", "").trim();
		if (Integer.parseInt(textFieldInput) > 20)
			return "20";
		
		return textFieldInput;
	}
}
