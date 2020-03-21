package drawers;

import java.awt.*;

import game.ConstantIntEnum;
import handlers.MapHandler;

/**
 * Turns int array into the map
 * @author Ömer 
 * @version 3
 *
 */
public class TileMap {

	private Tile[][] tile;
	private MapHandler readfile;

	public TileMap(String mapLocation) {
		readfile = new MapHandler(mapLocation);
		readfile.LoadMapFromText();
		readGrid();
	}

	public void readGrid() {

		setTile(new Tile[readfile.getgridHeight()][readfile.getgridWidth()]);

		for (int row = 0; row < getTile().length; row++) {
			for (int col = 0; col < getTile()[0].length; col++) {

				getTile()[row][col] = new Tile(col * ConstantIntEnum.GRID_SIZE.val, row * ConstantIntEnum.GRID_SIZE.val,
						ConstantIntEnum.WIDTH.val, ConstantIntEnum.WIDTH.val, readfile.getGrid()[row][col]);
			}
		}
	}

	public void draw(Graphics g) {
		for (int row = 0; row < getTile().length; row++) {
			for (int col = 0; col < getTile()[0].length; col++) {
				getTile()[row][col].draw(g);
			}
		}
	}

	public Tile[][] getTile() {
		return tile;
	}

	public int getType(int i, int k) {
		return tile[i][k].getType();
	}

	public void saveMap(String mapName, Tile[][] map) {
		readfile.saveMapToText(mapName, map);
	}

	public void setTile(Tile[][] tile) {
		this.tile = tile;
	}

	public void setSingleTile(int x, int y, int type) {
		tile[x][y].setType(type);
	}

}
