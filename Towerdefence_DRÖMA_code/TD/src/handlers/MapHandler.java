package handlers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import drawers.Tile;
import game.ConstantIntEnum;

/**
 * Loads/saves maps using int[][] to/from a textfile
 * @author Ömer
 * @version 2
 *
 */
public class MapHandler {

	private int gridWidth;
	private int gridHeight;
	private String filename;

	private int gridSize;
	private int[][] grid;

	/**
	 * name of the file specified
	 * 
	 * @param fileNames - filename to set
	 */
	public MapHandler(String fileNames) {
		this.filename = fileNames;
	}

	/**
	 * opens the file and checks how big the map should be in width and height and
	 * makes an array that is as big as the map reads every number in the file and
	 * adds it to the grid that is later used by TileMap to create the map.
	 */
	public void LoadMapFromText() {
		this.setGridSize(ConstantIntEnum.GRID_SIZE.val);

		BufferedReader readfile;
		try {
			readfile = new BufferedReader(new FileReader(filename));

			gridWidth = Integer.parseInt(readfile.readLine());
			gridHeight = Integer.parseInt(readfile.readLine());

			grid = new int[gridHeight][gridWidth];

			String delimiters = " ";

			for (int row = 0; row < gridHeight; row++) {
				String readline = readfile.readLine();

				String[] tempStorage = readline.split(delimiters);

				for (int col = 0; col < gridWidth; col++) {
					grid[row][col] = Integer.parseInt(tempStorage[col]);
				}
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param mapName name of map
	 * @param tile    model for the map
	 * 
	 *                takes the map and translates it to the corresponding number
	 *                for the tile i.e. grass = 1 and bricks = 0 by calling
	 *                getType() in tile to get what type it is.
	 */
	public void saveMapToText(String mapName, Tile[][] tile) {
		try {
			PrintWriter writer = new PrintWriter("map/" + mapName + ".txt");
			writer.print(ConstantIntEnum.GRID_WIDTH.val);
			writer.print("\n");
			writer.print(ConstantIntEnum.GRID_HEIGHT.val);
			writer.print("\n");
			for (int row = 0; row < gridHeight; row++) {
				for (int col = 0; col < gridWidth; col++) {
					writer.print(tile[row][col].getType() + " ");

				}
				writer.println();
			}
			writer.close();
		} catch (IOException e) {

		}

	}

	/**
	 * 
	 * @return gridWidth
	 */
	public int getgridWidth() {
		return gridWidth;
	}

	/**
	 * 
	 * @return gridHeight
	 */
	public int getgridHeight() {
		return gridHeight;

	}

	/**
	 * 
	 * @return grid
	 */
	public int[][] getGrid() {
		return grid;
	}

	/**
	 * 
	 * @return gridSize
	 */
	public int getGridSize() {
		return gridSize;
	}

	/**
	 * 
	 * @param gridSize grid size
	 */
	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}

}
