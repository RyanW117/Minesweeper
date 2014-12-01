package minesweeper;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
	
	private JPanel switchingPanel;
	private CardLayout layout;
	
	private static final String STARTPANEL = "Time to start!";
	private static final String MINESWEEPER = "minesweeper";
	
	String textInput;

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
		layout = new CardLayout(20, 20);
		
		//The "Cards", panel1
		gameStartPanel = createGameStartPanel(STARTPANEL);
				
		//mainPanel contains the cards.
		mainPanel = createCardPanel();
		mainPanel.add(gameStartPanel, STARTPANEL);
		layout.show(mainPanel, STARTPANEL);
		
		contentPane.add(mainPanel, BorderLayout.CENTER);
	}

	private JPanel createGameStartPanel(String identifier) 
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

	private JPanel createInputPanel_GameStartPanel() 
	{
		JPanel inputPanel = new JPanel();
		inputPanel.setBackground(Color.DARK_GRAY);
		
		JLabel enterTextHereLabel = new JLabel("Enter the Amount of rows :  ");
		enterTextHereLabel.setForeground(Color.WHITE);
		enterTextHereLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		inputPanel.add(enterTextHereLabel);
		
		JTextField textField = createTextField_GameStartPanel();
		inputPanel.add(textField);
		
		JButton playGameButton = createPlayGameButton_GameStartPanel();
		inputPanel.add(playGameButton);
		
		return inputPanel;
	}

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
					minesweeperPanel = new MinesweeperPanel(Integer.parseInt(textInput));
					mainPanel.add(minesweeperPanel);
					layout.show(mainPanel, MINESWEEPER);
				}
				else
					JOptionPane.showMessageDialog(null, "asdfasfd");
			}
		});
		return playGameButton;
	}

	private JTextField createTextField_GameStartPanel()
	{
		JTextField textField = new JTextField();
		textField.setColumns(10);
		textField.setFont(new Font("Verdana", Font.BOLD, 20));
		textField.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				textInput = e.getActionCommand();
			}
		});
		return textField;
	}
	
	private boolean isTextInputNumber()
	{
		if (textInput == null)
		{
			textInput = "8";
			return true;
		}
		textInput = textInput.replaceAll("[^0-9]", "").trim();
		return true;
	}

	private JPanel createCardPanel()
	{				
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(layout);
		mainPanel.setBackground(Color.RED);
		
		return mainPanel;
	}
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	private JButton createButton(String name, final String id) 
//	{
//		JButton button = new JButton(name);
//		button.setFont(new Font(button.getFont().getFamily(), Font.PLAIN, 18));
//		button.addActionListener(new ActionListener() 
//		{
//			@Override
//			public void actionPerformed(ActionEvent e) 
//			{
//				layout.show(mainPanel, id);
//			}
//		});
//		return button;
//	}
}
