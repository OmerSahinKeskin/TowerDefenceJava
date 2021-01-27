package mapbuilder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import game.*;
import towers.TowerTracker;
import windows.SwitchableWindow;
import windows.Window;

/**
 * Content panel for the map maker
 * Holds the map maker UI and handles user input for it
 * @author Robin
 * @version 2
 *
 */
public class MapMakerWindow implements Window, SwitchableWindow {

	public static MapPanelIfc mapPanel;

	private Container currentMap;

	private JLabel mainMenuBackground;
	private JButton resetMapButton;
	private SwitchableWindow mainMenu;

	/**
	 * Create window Create MainMenu container for its items Create GameWindow
	 * container for its items (play area and so on)
	 * 
	 * @param original - The main menu window so it doesn't get lost
	 * @throws IOException - file load failed
	 *
	 */

	public MapMakerWindow(SwitchableWindow original) throws IOException {
		mainMenu = original;

		currentMap = new Container();
		mapPanel = new MapPanel();
		initialize();
	}

	/**
	 * GameWindow initialization
	 * 
	 * @throws IOException
	 * 
	 */
	private void initialize() throws IOException {

		currentMap.setLayout(new BorderLayout(0, 0));

		// Creating left menu panel
		// For choosing tower to buy
		// or entering "SELL MODE"
		JPanel materialMenuPanel = new JPanel();
		materialMenuPanel.setBackground(Color.DARK_GRAY);
		currentMap.add(materialMenuPanel, BorderLayout.WEST);
		materialMenuPanel.setLayout(new GridLayout(0, 2, 2, 40));

		JButton water1Button = new JButton("Water1");
		water1Button.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		water1Button.setToolTipText("");
		materialMenuPanel.add(water1Button);
		water1Button.addActionListener(e -> water1Mode());

		JButton water2Button = new JButton("Water2");
		water2Button.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		water2Button.setToolTipText("");
		materialMenuPanel.add(water2Button);
		water2Button.addActionListener(e -> water2Mode());

		JButton water3Button = new JButton("Water3");
		water3Button.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		water3Button.setToolTipText("");
		materialMenuPanel.add(water3Button);
		water3Button.addActionListener(e -> water3Mode());

		JButton snowButton = new JButton("Snow");
		snowButton.setToolTipText("");
		snowButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		materialMenuPanel.add(snowButton);
		snowButton.addActionListener(e -> snowMode());

		JButton grass1Button = new JButton("Grass1");
		grass1Button.setToolTipText("");
		grass1Button.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		materialMenuPanel.add(grass1Button);
		grass1Button.addActionListener(e -> grass1Mode());

		JButton grass2Button = new JButton("Grass2");
		grass2Button.setToolTipText("");
		grass2Button.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		materialMenuPanel.add(grass2Button);
		grass2Button.addActionListener(e -> grass2Mode());

		JButton sandButton = new JButton("Sand");
		sandButton.setToolTipText("");
		sandButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		materialMenuPanel.add(sandButton);
		sandButton.addActionListener(e -> sandMode());

		JButton gravelButton = new JButton("Gravel");
		gravelButton.setToolTipText("");
		gravelButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		materialMenuPanel.add(gravelButton);
		gravelButton.addActionListener(e -> gravelMode());

		JButton brickButton = new JButton("Brick");
		brickButton.setToolTipText("");
		brickButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		materialMenuPanel.add(brickButton);
		brickButton.addActionListener(e -> brickMode());

		JButton exitButton = new JButton("Exit");
		exitButton.setToolTipText("");
		exitButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		materialMenuPanel.add(exitButton);
		exitButton.addActionListener(e -> exitMode());

		JButton empty1Button = new JButton("Empty");
		empty1Button.setToolTipText("");
		empty1Button.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		materialMenuPanel.add(empty1Button);
		empty1Button.addActionListener(e -> emptyMode());

		JButton empty2Button = new JButton("Empty");
		empty2Button.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		empty2Button.setToolTipText("");
		materialMenuPanel.add(empty2Button);
		empty2Button.addActionListener(e -> emptyMode());

		// Set up for bottom menu panel

		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(Color.DARK_GRAY);
		currentMap.add(infoPanel, BorderLayout.SOUTH);

		JButton mainMenuButton = new JButton("Main Menu");
		mainMenuButton.addActionListener(e -> mainMenu());
		mainMenuButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		mainMenuButton.setToolTipText("Return to main menu");

		resetMapButton = new JButton("Generate");
		resetMapButton.setToolTipText("Generates new map");
		resetMapButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		resetMapButton.addActionListener(e -> newMap());

		JButton saveButton = new JButton("Save");
		saveButton.setToolTipText("Save current map");
		saveButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		saveButton.addActionListener(e -> saveMap());

		GroupLayout bottomPanelLayout = new GroupLayout(infoPanel);
		bottomPanelLayout.setHorizontalGroup(bottomPanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(bottomPanelLayout.createSequentialGroup()

						.addPreferredGap(ComponentPlacement.RELATED, 805, Short.MAX_VALUE)
						.addComponent(saveButton, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(resetMapButton)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(mainMenuButton).addGap(61)));
		bottomPanelLayout.setVerticalGroup(bottomPanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(bottomPanelLayout.createParallelGroup(Alignment.BASELINE).addComponent(mainMenuButton)

						.addComponent(resetMapButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(saveButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)));
		bottomPanelLayout.setAutoCreateContainerGaps(true);
		// Add menu layout to bottom infoPanel
		infoPanel.setLayout(bottomPanelLayout);

		currentMap.add((JPanel) mapPanel, BorderLayout.CENTER);

	}

	/**
	 * Switch back to main menu view when "Main Menu" is pressed
	 */
	public void switchWindow(SwitchableWindow newcontent) {
		mainMenu.switchWindow(newcontent);

	}

	private void mainMenu() {

		mapPanel.threadStop();
		switchWindow(mainMenu);

	}

	private void resetMap() {
		mapPanel.clearMap();
	}
	
	private void newMap() {
		mapPanel.randomMap();
	}

	private void saveMap() {
		String instruction = "Enter name of new map";
		String input = null;
		boolean valid = false;

		while (!valid) {
			input = (String) JOptionPane.showInputDialog(instruction);
			if (input == null)
				return;

			valid = validFileName(input);
			if (!valid)
				instruction = "Name contains illegal characters or the name is taken, try again";
		}

		mapPanel.saveMapAs(input);
		JOptionPane.showMessageDialog(null, "Map saved as " + input + ".txt");
	}

	/**
	 * Tests filename if valid and not already used Hopefully it covers everything
	 * (probably not)
	 * 
	 * @param fileName
	 * @return valid or not
	 */
	private boolean validFileName(String fileName) {
		String[] invalid = { "/", "\\", "`", "?", "*", "<", ">", "|", ":", ";", "\"" };

		// False if name contains illegal characters
		for (String c : invalid) {
			if (fileName.contains(c))
				return false;
		}

		File testFile = new File("map/" + fileName + ".txt");
		boolean isValid = true;

		//If file exists ask about overwriting
		if (testFile.exists()) {
			int n = JOptionPane.showConfirmDialog(null, "File already exists, overwrite?", "An Inane Question",
					JOptionPane.YES_NO_OPTION);
			System.out.println(n);
			if (n == 1)
				return false;
		}
		
		//Make sure that it's possible to create the file
		try {
			if (testFile.createNewFile()) {
				testFile.delete();

			}
		} catch (IOException e) {
			isValid = false;
		}

		return isValid;
	}

	@Override
	public Container getContent() {
		return currentMap;
	}

	/**
	 * Simple methods to tell the window which texture to place
	 */
	private void grass1Mode() {
		mapPanel.setBuildMode(1);

	}

	private void grass2Mode() {
		mapPanel.setBuildMode(3);

	}

	private void brickMode() {
		mapPanel.setBuildMode(0);

	}

	private void exitMode() {
		mapPanel.setBuildMode(6);

	}

	private void water1Mode() {
		mapPanel.setBuildMode(2);

	}

	private void water2Mode() {
		mapPanel.setBuildMode(8);

	}

	private void water3Mode() {
		mapPanel.setBuildMode(9);

	}

	private void snowMode() {
		mapPanel.setBuildMode(7);

	}

	private void gravelMode() {
		mapPanel.setBuildMode(5);

	}

	private void emptyMode() {
		mapPanel.setBuildMode(10);

	}

	private void sandMode() {
		mapPanel.setBuildMode(4);

	}

}
