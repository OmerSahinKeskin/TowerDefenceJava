package windows;

import game.ConstantIntEnum;
import handlers.TextureHandler;

/**
 * 
 * Class used for the launch sequence
 * @author Robin
 * @version 3
 *
 */
public class Main {

	public static void main(String[] args) {
		ConstantIntEnum.printTest();
		/**
		 * Only launch game if handlers were successfully loaded
		 */
		if (TextureHandler.allOk()) {

			new MainMenu();
		} else {
			new PopupInfo("Textures failed to load\nLaunch aborted");
			
		}

	}

}
