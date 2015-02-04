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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class InitialFrame extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JPanel mainPanel;
	private JPanel gameStartPanel;
	
	private MinesweeperPanel minesweeperPanel;
	
	String textInput;
	private JTextField textField;

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
		
		JLabel enterTextHereLabel = new JLabel("Enter the number of rows :  ");
		enterTextHereLabel.setForeground(Color.WHITE);
		enterTextHereLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		inputPanel.add(enterTextHereLabel);
		
		JTextField textField = createTextField_GameStartPanel();
		inputPanel.add(textField);
		
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
				if (isTextInputNumber())
				{
					mainPanel.remove(gameStartPanel);
					
					minesweeperPanel = new MinesweeperPanel(Integer.parseInt(textInput));
					mainPanel.add(minesweeperPanel, BorderLayout.CENTER);
					
					revalidate();
					repaint();
				}
				else
					JOptionPane.showMessageDialog(null, "asdfasfd");
			}
		});
		return playGameButton;
	}

	private JTextField createTextField_GameStartPanel()
	{
		textField = new JTextField();
		textField.setColumns(10);
		textField.setFont(new Font("Verdana", Font.BOLD, 20));
		
		return textField;
	}
	
	private boolean isTextInputNumber()
	{
		if (textField.getText() == null)
		{
			textInput = "8";
			return true;
		}
		
		textInput = textField.getText();
		textInput = textInput.replaceAll("[^0-9]", "").trim();
		return true;
	}
}
