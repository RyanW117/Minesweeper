package minesweeper;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import java.awt.event.ItemEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * This class contains most of the GUI aspect of the program and it launches the program.
 * If you need to add another panel to the program, add it to the card layout.
 * 
 * @author Ryan Wilson
 */
public class InitialFrame extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int screenWidth = screenSize.width;
	private int screenHeight = screenSize.height;
	
	//These panels represent the "Cards" for the card layout
	private Panel_Home panel_Home;
	private Panel_HighScores panel_HighScores;
	
	protected static final String HOME = "The Home Panel";
	protected static final String MINESWEEPER = "The Panel for playing Minesweeper";
	protected static final String HIGHSCORES = "Shows you the highscores";
	protected static final String WINLOSE = "Win or Lose";
	protected static JPanel cardPanel;
	private JPanel panel_WinLose;
	
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
		setBounds(300, 100, screenWidth - 600, screenHeight - 200);	
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(5, 5));
		setContentPane(contentPane);
		
		cardPanel = createCardPanelAndCards();
		contentPane.add(cardPanel, BorderLayout.CENTER);
	}
	
	/**
	 * This panel creates the cards that will be displayed as the user
	 * clicks buttons.  The minesweeper panel is not included because
	 * it depends on the rows and columns that the player specifies.
	 * 
	 * @return The card Panel
	 */
	private JPanel createCardPanelAndCards()
	{		
		JPanel cardPanel = new JPanel();
		cardPanel.setLayout(new CardLayout());
		
		panel_Home = new Panel_Home();
		panel_WinLose = new WinLoseScreen();
		panel_HighScores = new Panel_HighScores();
		
		cardPanel.add(panel_Home, HOME);
		cardPanel.add(panel_HighScores, HIGHSCORES);
		cardPanel.add(panel_WinLose, WINLOSE);
		
		return cardPanel;
	}

	public static void requestPanelChange(ItemEvent evt)
	{
		CardLayout cl = (CardLayout)(cardPanel.getLayout());
        cl.show(cardPanel, (String)evt.getItem());
	}
}
