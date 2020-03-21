package mapbuilder;

import java.util.Random;

import drawers.TileMap;
import game.ConstantIntEnum;

/**
 * Class used for generating maps for the game of size GRID_HEIGHxGRID_WIDTH
 * It randomizes the biome, number of lakes and monster path
 * 
 * @author Robin
 * @version 1
 *
 */
public class MapGenerator {
	private Random rand = new Random();
	private int[][] grid = new int[ConstantIntEnum.GRID_HEIGHT.val][ConstantIntEnum.GRID_WIDTH.val];
	private int start;
	private TileMap map;

	public MapGenerator(TileMap map) {
		this.map = map;
		generate();
	}

	/**
	 * Generates a largely randomized map
	 */
	private void generate() {

		int[] biome = { 1, 3, 4, 5, 7 };
		int[] water = { 2, 9, 9, 2, 8 };
		int road = 0;
		int selection = rand.nextInt(5);
		int currentBiome = biome[selection];
		int lakes = rand.nextInt(7);
		int currentWater = water[selection];

		// Base map
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 30; j++) {
				map.setSingleTile(i, j, currentBiome);
			}
		}
		// Lakes
		for (int i = 0; i < lakes; i++) {
			int lakex = rand.nextInt(29);
			int lakey = rand.nextInt(15);

			for (int a = rand.nextInt(5)+1; a < 7; a++) {
				int lakeCurrentX = lakex;
				for (int b = rand.nextInt(5)+1; b < 7; b++) {
					if (lakeCurrentX > 29)
						break;
					map.setSingleTile(lakey, lakeCurrentX, currentWater);
					lakeCurrentX++;
				}
				lakey++;
				if (lakey > 15)
					break;
			}
		}

		start = rand.nextInt(14)+1;

		// Road
		int[] moveChance = { 1, 2, 2, 3, 3 };
		int x = 0;
		int y = start;
		int lastChoice = 0;
		for (int i = 0; i<3; i++) {
			map.setSingleTile(y, x, 0);
			x++;
		}
		
		//step size 2
		//Clean later
		while (x < 28) {
			int choice = moveChance[rand.nextInt(5)];
			switch (choice) {
			case 1: {

				map.setSingleTile(y, x, 0);
				x++;
				if (x > 30)
					x = 0;
				map.setSingleTile(y, x, 0);
				x++;
				if (x > 30)
					x = 0;
				lastChoice = 1;
				if (x>26) map.setSingleTile(y,26,6);
				break;

			}
			case 2: {
				if (lastChoice != 3 && x < 27) {
					map.setSingleTile(y, x, 0);
					y--;
					if (y < 1)
						y = 1;
					map.setSingleTile(y, x, 0);
					y--;
					if (y < 1)
						y = 1;
					lastChoice = 2;
				}
				break;
			}
			case 3: {
				if (lastChoice != 2 && x < 27) {
					map.setSingleTile(y, x, 0);
					y++;
					if (y > 14)
						y = 14;
					map.setSingleTile(y, x, 0);
					y++;
					if (y > 14)
						y = 14;
					lastChoice = 3;
				}
				break;
			}

			}
		}
	}

}
