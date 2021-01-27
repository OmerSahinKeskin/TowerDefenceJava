package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import enemytrackers.EnemyTracker;
import player.Player;
/**
 * 
 * Saves game data into textfile. Loads game data into the game.
 * @author Mojtaba
 * @version 2
 * 
 *
 */
public class SaveData {
	private static Player player = Player.getInstance();;
	private static String map;
	private static int[][] towers;
	private static int[] enemiesArray = new int[6];

	public SaveData() {

		towers = null;
	}
	/**
	 * saves game states 
	 */
	public static void save() {
		GameLogic.paused = true;
		String fileName = null;
		boolean valid = false;
		String instruction = "Save file as";		
		while(!valid) {
			fileName = (String)JOptionPane.showInputDialog(instruction);
			if(fileName == null)
				return;
			valid = validFileName(fileName);
			if(!valid)
				instruction = "Name contains illegal characters or the name is taken, try again";
		}
		
	    try {
	    	map = player.GetMapName();
	    	int health = player.getRoundHP();
	    	int score = player.getRoundScore();
	    	int gold = player.getRoundGold();
	    	int round = player.getRound();
	    	towers = GameLogic.towerGrid.saveTowers();
	    	PrintWriter pw = new PrintWriter("saves/" + fileName + ".txt");	
	    	pw.print(map);
	    	pw.print("\n");
	    	pw.print("" + health);
	    	pw.print("\n");
	    	pw.print("" + score);;
	    	pw.print("\n");
	    	pw.print("" + gold);
	    	pw.print("\n");
	    	pw.print("" + round);
	    	pw.print("\n");
	    	pw.print(ConstantIntEnum.GRID_HEIGHT.val);
	    	pw.print("\n");
	    	pw.print(ConstantIntEnum.GRID_WIDTH.val);
	    	pw.print("\n");
	    	for(int i = 0; i < ConstantIntEnum.GRID_HEIGHT.val; i++) {
	    		for(int j = 0; j < ConstantIntEnum.GRID_WIDTH.val; j++ ) {
	    			pw.print(towers[i][j]+ " ");
	    		}
	    		pw.println();
	    	}
	    	pw.print(""+EnemyTracker.getEnemies());
	    	pw.close();
	    } catch (IOException e){
	    	e.printStackTrace();
			
	    }
	    JDialog.setDefaultLookAndFeelDecorated(true);
	    int response = JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Confirm",
	        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	    if (response == JOptionPane.YES_OPTION) 
			GameLogic.paused = false;		     
	    else if (response == JOptionPane.NO_OPTION || response == JOptionPane.CLOSED_OPTION)
	    	System.exit(0);
		
	}
	/**
	 *  Loads players states and towers from saved file
	 *  and stores them
	 */
	public static void load() {
		int height;
		int width;

		String fileName = null;
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Select file");
		jfc.setCurrentDirectory(new File("saves"));
		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File fileToLoad = jfc.getSelectedFile();
			fileName = fileToLoad.getName();
		}
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("saves/" + fileName));
			String tempStorage1;
			tempStorage1 = br.readLine();
			map = tempStorage1;
			player.setMapName(tempStorage1);
			player.setHealth(Integer.parseInt(br.readLine()));
			player.setScore(Integer.parseInt(br.readLine()));
			player.setGold(Integer.parseInt(br.readLine()));
			player.changeRound(Integer.parseInt(br.readLine()));
			height = Integer.parseInt(br.readLine());
			width = Integer.parseInt(br.readLine());
			towers = new int[height][width];

			for (int i = 0; i < height; i++) {
				String read = br.readLine();
				String[] tempStorage = read.split(" ");
				for (int j = 0; j < width; j++) {
					towers[i][j] = Integer.parseInt(tempStorage[j]);
				}
			}
			int temp1 = Integer.parseInt(br.readLine());
			enemiesArray[0] = temp1;
			temp1 = Integer.parseInt(br.readLine());
			enemiesArray[1] = temp1;
			temp1 = Integer.parseInt(br.readLine());
			enemiesArray[2] = temp1;
			temp1 = Integer.parseInt(br.readLine());
			enemiesArray[3] = temp1;
			temp1 = Integer.parseInt(br.readLine());
			enemiesArray[4] = temp1;
			temp1 = Integer.parseInt(br.readLine());
			enemiesArray[5] = temp1;
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static boolean validFileName(String fileName) {
		String[] invalid = { "/", "\\", "`", "?", "*", "<", ">", "|", ":", ";", "\"" };

		// False if name contains illegal characters
		for (String c : invalid) {
			if (fileName.contains(c))
				return false;
		}

		File testFile = new File("map/" + fileName + ".txt");
		boolean isValid = true;

		// If file exists ask about overwriting
		if (testFile.exists()) {
			int n = JOptionPane.showConfirmDialog(null, "File already exists, overwrite?", "An Inane Question",
					JOptionPane.YES_NO_OPTION);
			System.out.println(n);
			if (n == 1)
				return false;
		}

		// Make sure that it's possible to create the file
		try {
			if (testFile.createNewFile()) {
				testFile.delete();

			}
		} catch (IOException e) {
			isValid = false;
		}

		return isValid;
	}

	public int[][] getTowers() {
		return towers;
	}
	/**
	 * 
	 * @return - returns name of map used
	 */
	public String getMapName() {
		return map;
	}
	/**
	 * 
	 * @return enemiesArray -saved enemies
	 */
	public int[] getMobs() {
		return enemiesArray;
	}
	
}
