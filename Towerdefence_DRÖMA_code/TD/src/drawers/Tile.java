package drawers;

import java.awt.*;

import game.ConstantIntEnum;
import handlers.TextureHandler;

/**
 *  responsible for translating the different numbers sent in
 *         by TileMap to corresponding image
 * @author Ömer
 * @author Robin
 * @version 2
 */
public class Tile extends Rectangle {

	private int Type;

	/**
	 * 
	 * @param x,      x position
	 * @param y,      y position
	 * @param width,  width of the tile
	 * @param height, height of the tile
	 * @param type,   what type should the Tile be
	 */
	public Tile(int x, int y, int width, int height, int type) {
		setBounds(x, y, width, height);
		this.setType(type);
	}

	public void draw(Graphics g) {

		switch (getType()) {
		case 0:
			g.drawImage(TextureHandler.BRICK.img, x, y, ConstantIntEnum.WIDTH.val, ConstantIntEnum.HEIGHT.val, null);
			break;
		case 1:
			g.drawImage(TextureHandler.GRASS.img, x, y, ConstantIntEnum.WIDTH.val, ConstantIntEnum.HEIGHT.val, null);
			break;
		case 2:
			g.drawImage(TextureHandler.WATER.img, x, y, ConstantIntEnum.WIDTH.val, ConstantIntEnum.HEIGHT.val, null);
			break;
		case 3:
			g.drawImage(TextureHandler.GRASS2.img, x, y, ConstantIntEnum.WIDTH.val, ConstantIntEnum.HEIGHT.val, null);
			break;
		case 4:
			g.drawImage(TextureHandler.SAND.img, x, y, ConstantIntEnum.WIDTH.val, ConstantIntEnum.HEIGHT.val, null);
			break;
		case 5:
			g.drawImage(TextureHandler.GRAVEL.img, x, y, ConstantIntEnum.WIDTH.val, ConstantIntEnum.HEIGHT.val, null);
			break;
		case 6:
			g.drawImage(TextureHandler.EXIT.img, x, y, ConstantIntEnum.WIDTH.val, ConstantIntEnum.HEIGHT.val, null);
			break;
		case 7:
			g.drawImage(TextureHandler.SNOW.img, x, y, ConstantIntEnum.WIDTH.val, ConstantIntEnum.HEIGHT.val, null);
			break;
		case 8:
			g.drawImage(TextureHandler.WATER2.img, x, y, ConstantIntEnum.WIDTH.val, ConstantIntEnum.HEIGHT.val, null);
			break;
		case 9:
			g.drawImage(TextureHandler.WATER3.img, x, y, ConstantIntEnum.WIDTH.val, ConstantIntEnum.HEIGHT.val, null);
			break;
		case 10:
			g.drawImage(TextureHandler.EMPTY.img, x, y, ConstantIntEnum.WIDTH.val, ConstantIntEnum.HEIGHT.val, null);
			break;
		case 11:
			g.drawImage(TextureHandler.EXIT.img, x, y, ConstantIntEnum.WIDTH.val, ConstantIntEnum.HEIGHT.val, null);
			break;
		case 12:
			g.drawImage(TextureHandler.EXIT.img, x, y, ConstantIntEnum.WIDTH.val, ConstantIntEnum.HEIGHT.val, null);
			break;
		default:
			g.setColor(Color.BLACK);
			g.drawRect(x, y, width, height);
		}

	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

}
