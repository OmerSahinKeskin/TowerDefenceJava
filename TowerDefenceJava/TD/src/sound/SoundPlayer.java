package sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 * 
 * @author Ömer
 * @version 1
 * version 2
 * plays sound that is used in the game
 */
public class SoundPlayer {

	public SoundPlayer(String filepath)
	{
		try {
			File pathToMusicFile = new File(filepath);
			if(pathToMusicFile.exists()) {
				AudioInputStream musicInput = AudioSystem.getAudioInputStream(pathToMusicFile);
				
				
				Clip AudioClip = AudioSystem.getClip();
				AudioClip.open(musicInput);
				
				AudioClip.start();
				
			}
			else {
				throw new IOException();
			}
	}catch(LineUnavailableException | IOException | UnsupportedAudioFileException e) {
		e.printStackTrace();
	}
    
	}
}