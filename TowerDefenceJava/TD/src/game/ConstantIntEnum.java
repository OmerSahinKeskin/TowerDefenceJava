package game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Enum dedicated to loading constants (int) from a configuration file.
 * 
 * @author Robin R
 * @version 3
 *
 */

public enum ConstantIntEnum {
	WIDTH("WIDTH"), HEIGHT("HEIGHT"), GRID_SIZE("GRID_SIZE"), SCREEN_HEIGHT("SCREEN_HEIGHT"),
	SCREEM_WIDTH("SCREEM_WIDTH"), ENEMY_ROAD("ENEMY_ROAD"), GREEN_HEALTH("GREEN_HEALTH"), GREEN_GOLD("GREEN_GOLD"),
	GREEN_DAMAGE_TO_PLAYER("GREEN_DAMAGE_TO_PLAYER"), GREEN_SCORE("GREEN_SCORE"),
	GREEN_WHEN_TO_WALK("GREEN_WHEN_TO_WALK"), RED_HEALTH("RED_HEALTH"), RED_GOLD("RED_GOLD"),
	RED_DAMAGE_TO_PLAYER("RED_DAMAGE_TO_PLAYER"), RED_SCORE("RED_SCORE"), RED_WHEN_TO_WALK("RED_WHEN_TO_WALK"),
	INVIS_HEALTH("INVIS_HEALTH"), INVIS_GOLD("INVIS_GOLD"), INVIS_DAMAGE_TO_PLAYER("INVIS_DAMAGE_TO_PLAYER"),
	INVIS_SCORE("INVIS_SCORE"), INVIS_WHEN_TO_WALK("INVIS_WHEN_TO_WALK"), MIKEY_HEALTH("MIKEY_HEALTH"),
	MIKEY_GOLD("MIKEY_GOLD"), MIKEY_DAMAGE_TO_PLAYER("MIKEY_DAMAGE_TO_PLAYER"), MIKEY_SCORE("MIKEY_SCORE"),
	MIKEY_WHEN_TO_WALK("MIKEY_WHEN_TO_WALK"), SPOOKEY_HEALTH("SPOOKEY_HEALTH"), SPOOKEY_GOLD("SPOOKEY_GOLD"),
	SPOOKEY_DAMAGE_TO_PLAYER("SPOOKEY_DAMAGE_TO_PLAYER"), SPOOKEY_SCORE("SPOOKEY_SCORE"),
	SPOOKEY_WHEN_TO_WALK("SPOOKEY_WHEN_TO_WALK"), YELLOW_HEALTH("YELLOW_HEALTH"), YELLOW_GOLD("YELLOW_GOLD"),
	YELLOW_DAMAGE_TO_PLAYER("YELLOW_DAMAGE_TO_PLAYER"), YELLOW_SCORE("YELLOW_SCORE"),
	YELLOW_WHEN_TO_WALK("YELLOW_WHEN_TO_WALK"), REDT_SHOOTINGSPEED("REDT_SHOOTINGSPEED"), REDT_DAMAGE("REDT_DAMAGE"),
	REDT_VALUE("REDT_VALUE"), REDT_RANGE("REDT_RANGE"), YELLOWT_SHOOTINGSPEED("YELLOWT_SHOOTINGSPEED"),
	YELLOWT_DAMAGE("YELLOWT_DAMAGE"), YELLOWT_VALUE("YELLOWT_VALUE"), YELLOWT_RANGE("YELLOWT_RANGE"),
	BLUET_DAMAGE("BLUET_DAMAGE"), BLUET_VALUE("BLUET_VALUE"), BLUET_RANGE("BLUET_RANGE"), GRID_HEIGHT("GRID_HEIGHT"),
	GRID_WIDTH("GRID_WIDTH"), BLUET_SAVE_VAL("BLUET_SAVE_VAL"), YELLOWT_SAVE_VAL("YELLOWT_SAVE_VAL"), REDT_SAVE_VAL("REDT_SAVE_VAL"), EXIT_ROAD("EXIT_ROAD"),
	GRASS1("GRASS1"), WATER1("WATER1"), GRASS2("GRASS2"), SAND("SAND"), GRAVEL("GRAVEL"), SNOW("SNOW"), WATER2("WATER2"),
	WATER3("WATER3"), EMPTY("EMPTY");

	public final int val;

	ConstantIntEnum(String fileName) {
		this.val = getConst(fileName);
	}

	/**
	 * Get relevant data for enum key
	 * 
	 * @param propName - key for which to find value
	 * @return found value
	 */
	private int getConst(String propName) {
		Properties prop = new Properties();
		FileInputStream in;
		String temp = "";
		try {
			in = new FileInputStream("src/config.properties");
			prop.load(in);
			temp = prop.getProperty(propName);
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Integer.parseInt(temp);
	}

	public static void printTest() {
		for (ConstantIntEnum a : ConstantIntEnum.values()) {
			System.out.println(a.name() + " = " + a.val);

		}
	}
}