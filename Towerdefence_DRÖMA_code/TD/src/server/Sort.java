package server;

import java.io.IOException;

/**
 * Sort class used with the compare methods in the server java class. 
 * @author Allan
 *
 */
public class Sort 
{
	String location = System.getProperty("user.home") + "/Desktop";
	String username;
	int score;
	
	/**
	 * Setting user and score for sorting.
	 * @param username
	 * @param score
	 * @throws IOException
	 */
	public Sort(String username, int score) throws IOException
	{
		this.username = username;
		this.score = score;
	}
}
