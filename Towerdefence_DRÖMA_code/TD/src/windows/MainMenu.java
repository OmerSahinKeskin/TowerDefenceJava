package windows;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import game.*;
import handlers.TextureHandler;
import mapbuilder.MapMakerWindow;
import network.*;
import player.Player;
/**
 * Creates the main frame for the game and the main menu switchable container.
 * High Score button will only be active when the high score server is reachable.
 * 
 * @author Robin
 * @author Ömer
 * @author Mojtaba
 * @author Allan
 * @author Dalibor
 * @version 8
 *
 */
public class MainMenu implements Window, SwitchableWindow {
	private JFrame frame;
	private Container MainMenu;
	private Client client;
	JLabel mainMenuBackground;
	JButton quitButton;
	JButton loadGameButton;
	JButton highScoreButton;
	JButton newGameButton;
	boolean server_reached = false;

	public MainMenu() {

		// Online IP:
		// 83.249.189.91
		client = new Client("localhost", 1604);
		initialize();

	}

	/**
	 * MainMenu initialization
	 */
	public void initialize() {

		/**
		 * Create the main frame and assign its content pane to MainMenu
		 * 
		 */
		frame = new JFrame("Tower Defence");
		MainMenu = frame.getContentPane();
		frame.setBounds(100, 100, ConstantIntEnum.SCREEM_WIDTH.val, ConstantIntEnum.SCREEN_HEIGHT.val);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		// setup for main menu buttons
		MainMenu.setLayout(null);

		// Load button
		loadGameButton = new JButton("LOAD GAME");
		loadGameButton.addActionListener(e -> loadGame());
		loadGameButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 29));
		loadGameButton.setBounds(378, 396, 520, 87);
		MainMenu.add(loadGameButton);
		

		// Highscore button
		highScoreButton = new JButton("HIGHSCORE");
		highScoreButton.setForeground(new Color(50, 205, 50));
		highScoreButton.addActionListener(e -> HighScore());
		highScoreButton.setFont(new Font("Microsoft YaHei", Font.BOLD | Font.ITALIC, 29));
		highScoreButton.setBounds(378, 493, 520, 74);
		MainMenu.add(highScoreButton);
		// Enable if server is reachable
		boolean connectionStatus = client.serverReachable();
		highScoreButton.setEnabled(connectionStatus);

		// Map Maker button
		JButton mmButton = new JButton("MAP MAKER");
		mmButton.setForeground(Color.BLUE);
		mmButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 29));
		mmButton.setBounds(378, 574, 520, 47);
		mmButton.addActionListener(e->mapEditor());
		MainMenu.add(mmButton);
		
		// Quit button
		quitButton = new JButton("QUIT");
		quitButton.addActionListener(e -> Quit());
		quitButton.setForeground(Color.RED);
		quitButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 29));
		quitButton.setBounds(378, 574, 520, 47);
		MainMenu.add(quitButton);

		JButton newGameButton = new JButton("NEW GAME");
		newGameButton.addActionListener(e -> newGame());
		newGameButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 29));
		newGameButton.setBounds(378, 298, 520, 87);
		frame.getContentPane().add(newGameButton);

		// JLabel added for optional main menu background image. Does nothing else
		mainMenuBackground = new JLabel(new ImageIcon(TextureHandler.MAIN_MENU_BACKGROUND.img));
		mainMenuBackground.setBounds(0, 0, 1264, 665);
		frame.getContentPane().add(mainMenuBackground);

		// Create the top menu
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.addActionListener(e -> Quit());
		mnFile.add(mntmQuit);

		// Insert dev operations into this JMenu
		// = insert JMenuItems into it and add actions to new items
		JMenu mnDev = new JMenu("Dev");
		menuBar.add(mnDev);

		JMenuItem sendDataItem = new JMenuItem("Test connection T/F");
		sendDataItem.addActionListener(e -> sendTestData());
		mnDev.add(sendDataItem);

		// Move buttons when window is resized
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent ev) {
				Rectangle r = frame.getBounds();
				int h = r.height;
				int w = r.width;
				// Adjust background to window size (up to image resolution) - use 4k image?
				mainMenuBackground.setBounds(0, 0, w, h);
				// Buttons
				newGameButton.setBounds((w / 2 - 270), (h / 2) - 200, 520, 87);
				loadGameButton.setBounds((w / 2) - 270, (h / 2) - 100, 520, 87);
				highScoreButton.setBounds((w / 2) - 270, (h / 2), 520, 74);
				mmButton.setBounds((w / 2) - 270, (h / 2) + 86, 520, 47);
				quitButton.setBounds((w / 2) - 270, (h / 2) + 145, 520, 47);
			}
		});
		frame.setVisible(true);
		if (!connectionStatus) {
			new PopupInfo("Server not found\nHighscore disabled");
		}
		
	}

	/**
	 * Method for quitButton Shuts down the program
	 */
	private void Quit() {
		System.exit(0);
	}

	/**
	 * Change view to received window/content
	 */

	public void switchWindow(SwitchableWindow newcontent) {
		frame.getContentPane().setVisible(false);

		if (newcontent == null) {

			frame.setContentPane(MainMenu);
			MainMenu.setVisible(true);
		}

		else {
			frame.setContentPane(newcontent.getContent());
			newcontent.getContent().setVisible(true);
		}

	}

	/**
	 * Method for newGame Changes content pane to game view Hides mainMenu
	 * 
	 * @throws IOException
	 */
	private void newGame() {
		String fileName = "map1.txt";
		Player.getInstance().resetPlayer();
		final JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("map/blank.txt"));
		int result = fc.showOpenDialog(null);
		
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fc.getSelectedFile();
		    fileName = selectedFile.getName();
		}
		try {
			switchWindow(new GameWindow(this,fileName, client));
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * Method for loadGameButton Loads saved game state Changes content pane to game
	 * view Hides mainMenu
	 */
	private void loadGame() {
		SaveData load = new SaveData();
		SaveData.load();
		try {
			switchWindow(new GameWindow(this,load.getMapName(),load.getTowers(), load.getMobs(), client));
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}

	/**
	 * Opens map editor in the same window
	 */
	private void mapEditor() {
		try {
			switchWindow(new MapMakerWindow(this));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Method for highScoreButton Opens high score window
	 */
	private void HighScore() {
		new HighScoreWindow(client);
	}

	@Override
	public Container getContent() {
		return MainMenu;
	}

	/**
	 * Send test data to server It is possible to send
	 */

	private void sendTestData() {

		Player.getInstance().changeRound(5);
	}

}
