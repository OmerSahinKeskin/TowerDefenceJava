package windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import game.*;
import handlers.TextureHandler;
import inpurdevices.*;
import network.Client;
import observerfiles.ExampleModel;
import observerfiles.Observer;
import observerfiles.ObserverMessage;
import player.Player;
import towers.TowerTracker;

/**
 * Builds the game window to be displayed to the user during gameplay. Creates
 * the necessary buttons for the game and the game area.
 * 
 * @author Robin
 * @author Ömer
 * @author Mojtaba
 * @author Allan
 * @author Dalibor
 * @version 9
 *
 */
public class GameWindow implements Window, SwitchableWindow, UserInterface, Observer<ExampleModel, ObserverMessage> {

	public static Panel GamePanel;
	private Player player;

	private Container GameContent;
	private JLabel goldDisplayLabel;
	private JLabel scoreDisplayLabel;
	private JLabel hpDisplayLabel;
	private JLabel progressDisplayLabel;
	private MouseControls mouseHandler;

	private JLabel mainMenuBackground;
	private JButton quitButton;
	private JButton loadGameButton;
	private JButton highScoreButton;
	private JButton newGameButton;
	private JButton pauseButton;
	private SwitchableWindow MainMenu;
	private Client client;

	/**
	 * Sets up the game ui and all necessary components Also handles some user input
	 * Used for loading a saved game
	 * 
	 * @param original - original display container
	 * @param mapName  - name of the map to load
	 * @param towerArr - saved towers
	 * @param enemies  - saved enemies
	 * @throws IOException - if map is not found
	 */

	public GameWindow(SwitchableWindow original, String mapName, int[][] towerArr, int[] enemies, Client client)
			throws IOException {
		player = Player.getInstance();
		player.addObserver(this);
		MainMenu = original;
		GameContent = new Container();
		GamePanel = new Panel(mapName, enemies);
		mouseHandler = MouseControls.getInstance();
		GamePanel.loadTowers(towerArr);
		this.client = client;

		initialize();
	}

	/**
	 * Sets up the game ui and all necessary components Also handles some user input
	 * Used for setting up a new game
	 * 
	 * @param original - original display container
	 * @param mapName  - map to load
	 * @throws IOException - if map not found
	 */
	public GameWindow(SwitchableWindow original, String mapName, Client client) throws IOException {
		MainMenu = original;
		GameContent = new Container();
		player = Player.getInstance();
		player.addObserver(this);
		GamePanel = new Panel(mapName);
		player.setMapName(mapName);
		mouseHandler = MouseControls.getInstance();
		this.client = client;
		initialize();
	}

	/**
	 * GameWindow initialization
	 * 
	 * @throws IOException
	 * 
	 */
	private void initialize() throws IOException {

		GameContent.setLayout(new BorderLayout(0, 0));

		// Creating left menu panel
		// For choosing tower to buy
		// or entering "SELL MODE"
		JPanel towerMenuPanel = new JPanel();
		towerMenuPanel.setBackground(Color.DARK_GRAY);
		GameContent.add(towerMenuPanel, BorderLayout.WEST);
		GridBagLayout gbl_towerMenuPanel = new GridBagLayout();
		gbl_towerMenuPanel.columnWidths = new int[] { 95, 0 };
		gbl_towerMenuPanel.rowHeights = new int[] { 130, 42, 42, 42, 42, 130, 0 };
		gbl_towerMenuPanel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_towerMenuPanel.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		towerMenuPanel.setLayout(gbl_towerMenuPanel);

		JButton redTowerButton = new JButton("Red Tower");
		redTowerButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		redTowerButton.setToolTipText("Cost: X, Range: X, Attack Speed: osv");
		GridBagConstraints gbc_redTowerButton = new GridBagConstraints();
		gbc_redTowerButton.insets = new Insets(0, 0, 5, 0);
		gbc_redTowerButton.fill = GridBagConstraints.BOTH;
		gbc_redTowerButton.gridx = 0;
		gbc_redTowerButton.gridy = 1;
		towerMenuPanel.add(redTowerButton, gbc_redTowerButton);
		redTowerButton.addActionListener(e -> buyRedTower());

		JButton yellowTowerButton = new JButton("Yellow Tower");
		yellowTowerButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		yellowTowerButton.setToolTipText("Cost: X, Range: X, Attack Speed: osv");
		GridBagConstraints gbc_yellowTowerButton = new GridBagConstraints();
		gbc_yellowTowerButton.fill = GridBagConstraints.BOTH;
		gbc_yellowTowerButton.insets = new Insets(0, 0, 5, 0);
		gbc_yellowTowerButton.gridx = 0;
		gbc_yellowTowerButton.gridy = 2;
		towerMenuPanel.add(yellowTowerButton, gbc_yellowTowerButton);
		yellowTowerButton.addActionListener(e -> buyYellowTower());

		JButton blueTowerButton = new JButton("Blue Tower");
		blueTowerButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		blueTowerButton.setToolTipText("Cost: X, Range: X, Attack Speed: osv");
		GridBagConstraints gbc_blueTowerButton = new GridBagConstraints();
		gbc_blueTowerButton.insets = new Insets(0, 0, 5, 0);
		gbc_blueTowerButton.fill = GridBagConstraints.BOTH;
		gbc_blueTowerButton.gridx = 0;
		gbc_blueTowerButton.gridy = 3;
		towerMenuPanel.add(blueTowerButton, gbc_blueTowerButton);
		blueTowerButton.addActionListener(e -> buyBlueTower());

		JButton sellButton = new JButton("Sell");
		sellButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		sellButton.setToolTipText("Enter sell mode");
		GridBagConstraints gbc_sellButton = new GridBagConstraints();
		gbc_sellButton.fill = GridBagConstraints.BOTH;
		gbc_sellButton.insets = new Insets(0, 0, 5, 0);
		gbc_sellButton.gridx = 0;
		gbc_sellButton.gridy = 4;
		towerMenuPanel.add(sellButton, gbc_sellButton);
		sellButton.addActionListener(e -> sellTower());

		// Set up for bottom menu panel

		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(Color.DARK_GRAY);
		GameContent.add(infoPanel, BorderLayout.SOUTH);

		hpDisplayLabel = new JLabel("HP: " + player.getHealth());
		hpDisplayLabel.setToolTipText("Health Points - if it reaches 0 you lose");
		hpDisplayLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 20));
		hpDisplayLabel.setForeground(Color.RED);

		goldDisplayLabel = new JLabel("GOLD: " + player.getGold());
		goldDisplayLabel.setToolTipText("Gold: currency use to buy towers");
		goldDisplayLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 20));
		goldDisplayLabel.setForeground(Color.YELLOW);

		scoreDisplayLabel = new JLabel("SCORE: " + player.getScore());
		scoreDisplayLabel.setToolTipText("Score: earned by killing monsters (and completing rounds?)");
		scoreDisplayLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 20));
		scoreDisplayLabel.setForeground(Color.ORANGE);

		progressDisplayLabel = new JLabel("PROGRESS:");
		progressDisplayLabel.setToolTipText("Round progress");
		progressDisplayLabel.setForeground(Color.GREEN);
		progressDisplayLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 20));

		JButton mainMenuButton = new JButton("Main Menu");
		mainMenuButton.addActionListener(e -> mainMenu());
		mainMenuButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		mainMenuButton.setToolTipText("Return to main menu");

		pauseButton = new JButton("Pause");
		pauseButton.setToolTipText("Pauses the game");
		pauseButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		pauseButton.addActionListener(e -> pauseGame());

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(e -> SaveData.save());
		saveButton.setToolTipText("Save your progress");
		saveButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));

		GroupLayout bottomPanelLayout = new GroupLayout(infoPanel);
		bottomPanelLayout.setHorizontalGroup(bottomPanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(bottomPanelLayout.createSequentialGroup().addGap(37)
						.addComponent(hpDisplayLabel, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(goldDisplayLabel, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
						.addGap(18).addComponent(scoreDisplayLabel).addGap(126)
						.addComponent(progressDisplayLabel, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 805, Short.MAX_VALUE)
						.addComponent(saveButton, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(pauseButton)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(mainMenuButton).addGap(61)));
		bottomPanelLayout.setVerticalGroup(bottomPanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(bottomPanelLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(hpDisplayLabel, GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
						.addComponent(mainMenuButton).addComponent(scoreDisplayLabel).addComponent(goldDisplayLabel)
						.addComponent(pauseButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(saveButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(progressDisplayLabel, GroupLayout.PREFERRED_SIZE, 28,
								GroupLayout.PREFERRED_SIZE)));
		bottomPanelLayout.setAutoCreateContainerGaps(true);
		// Add menu layout to bottom infoPanel
		infoPanel.setLayout(bottomPanelLayout);

		GameContent.add(GamePanel, BorderLayout.CENTER);
		GamePanel.addMouseListener(mouseHandler);
		GamePanel.addMouseMotionListener(mouseHandler);
		updateInterface();

	}

	/**
	 * Switch back to main menu view when "Main Menu" is pressed
	 */
	public void switchWindow(SwitchableWindow newcontent) {
		MainMenu.switchWindow(newcontent);

	}

	private void mainMenu() {

		switchWindow(MainMenu);
		Panel.setOpenGame(false);
		System.out.println("MAIN MENU PRESSED");

	}

	private void pauseGame() {
		if (GameLogic.paused) {
			GameLogic.paused = false;
			pauseButton.setText("Pause");

		} else {
			GameLogic.paused = true;
			pauseButton.setText("Resume");
		}
	}

	// Public methods for displaying information to player
	// replace with listeners later?

	@Override
	public void updateGoldDisplay() {
		goldDisplayLabel.setText("GOLD: " + player.getGold());
		goldDisplayLabel.setForeground(Color.YELLOW);

	}

	@Override
	public void updateHealthDisplay() {
		hpDisplayLabel.setText("HP: " + player.getHealth());
		hpDisplayLabel.setForeground(Color.RED);

		if (player.getHealth() <= 0) {
			if (client.serverReachable()) {
				player.setName((String) JOptionPane.showInputDialog("Enter your name\nWill be shown in the highscore"));

				try {
					client.addData(player.getPlayerName(), player.getScore());
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void updateScoreDisplay() {
		scoreDisplayLabel.setText("SCORE: " + player.getScore());
		scoreDisplayLabel.setForeground(Color.ORANGE);

	}

	@Override
	public void updateProgressDisplay() {
		progressDisplayLabel.setText("PROGRESS: " + player.getRound());
		progressDisplayLabel.setForeground(Color.GREEN);
	}

	@Override
	public Container getContent() {
		return GameContent;
	}

	/**
	 * Simple methods to tell the game which tower needs to be built
	 */
	private void buyRedTower() {
		TowerTracker.buildMode = TextureHandler.RED_TOWER;

	}

	private void buyBlueTower() {
		TowerTracker.buildMode = TextureHandler.BLUE_TOWER;

	}

	private void buyYellowTower() {
		TowerTracker.buildMode = TextureHandler.YELLOW_TOWER;

	}

	private void sellTower() {
		TowerTracker.buildMode = TextureHandler.SELL;

	}

	@Override
	public void updateInterface() {
		updateGoldDisplay();
		updateHealthDisplay();
		updateScoreDisplay();
		updateProgressDisplay();

	}

	@Override
	public void update(ExampleModel observable, ObserverMessage message) {
		updateInterface();
	}

}
