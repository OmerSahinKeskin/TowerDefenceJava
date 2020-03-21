package handlers;

import java.awt.image.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class dedicated to loading and distributing textures Textures are loaded by
 * name from default location
 * 
 * TextureHandler is able to tell the program if the texture load was successful
 * or not
 * 
 * @author Ömer
 * @author Robin
 * @author Pelle?
 * @version 4
 */

public enum TextureHandler {
	WATER("Water1"), GRASS("Grass1"), GRASS2("Grass2"), SAND("Sand1"), GRAVEL("Gravel1"), BRICK("Brick1"),
	EXIT("BrickExit"), GREEN_MONSTER("GreenMonster"), RED_MONSTER("RedMonster"), YELLOW_DEVIL("YellowDevil"),
	INVIS_MONSTER("Monster7"), MIKEY("Mikey"), SPOOKEY("Spookey"), SNOW("Snow1"), WATER2("Water2"), WATER3("Water3"),
	RED_TOWER("RedTower1"), YELLOW_TOWER("YellowTower1"), BLUE_TOWER("BlueTower1"), EMPTY("Empty"), SELL("Sell"),
	RED_PROJECTILE("RedProjectile"), BLUE_PROJECTILE("BlueProjectile"), YELLOW_PROJECTILE("YellowProjectile"),
	GAME_OVER_SCREEN("GameOverScreen"), GAME_OVER_SCREEN2("GameOverScreen2"), GAME_OVER_SCREEN3("GameOverScreen3"),
	MAIN_MENU_BACKGROUND("backgroundtest");

	public final BufferedImage img;

	TextureHandler(String fileName) {
		this.img = getTexture(fileName);
	}

	public static boolean allOk() {
		for (TextureHandler e : TextureHandler.values()) {
			if (e.img == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Attempt to load a texture
	 * 
	 * @param texturename - filename without extension
	 * @return returns image if found
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static BufferedImage getTexture(String textureName) {
		BufferedImage temp = null;

		try {
			File image = new File("pic/" + textureName + ".png");
			temp = ImageIO.read(image);
			return temp;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to load texture " + textureName);
		}

		return temp;
	}

}
