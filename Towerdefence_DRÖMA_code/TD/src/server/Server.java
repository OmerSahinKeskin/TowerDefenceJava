package server;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JOptionPane;

public class Server
{
	/** 
	 * The Server which handles the Highscore data remotely through the Client.  
	 * @author Allan
	 * @version 3
	 */
	
	/**
	 * Location, initialization of Socket, ServerSocket and Stream along with bool check if server is active. 
	 */
	private String location = System.getProperty("user.home") + "/Desktop";
	private Socket socket = null; 
    private ServerSocket server = null; 
    private DataInputStream input = null; 
    private boolean active = true;
   
    /**
     * Start server listening to specific port and wait for signal. 
     * @param port - Port to listen.
     * @throws IOException
     */
    public Server(int port) throws IOException
    {
    	server = new ServerSocket(port);
    	createFile();
    	
    	while (active)
    	{
    		try
    		{
    			socket = server.accept(); 
    			input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    			
		        while (!socket.isClosed()) 
		        { 
		            try
		            { 
		                String indata = input.readUTF();
		                String[] splitted = indata.split(":");
		                
		                // String data as follows: 
		                // [0] = Command (W, X, Y, Z)
		                // [1] = Username
		                // [2] = Score
		                
		                PrintWriter output = new PrintWriter(socket.getOutputStream());
		                
		                if (splitted[0].equals("X")) // GetScore()
		                {
		                	System.out.println("GetScore Activated. ");
		                	output.print("User: " + splitted[1] + " - Score: " + getScore(splitted[1]));
		                	output.close();
		                	
		                }
		                else if (splitted[0].equals("Y")) // AddScore()
		                {
		                	System.out.println("AddScore Activated. ");
		                	addScore(splitted[1], Integer.valueOf(splitted[2]));
		                	output.print("Score added to list. ");
		                	
		                	sortFile();
		                	output.close();
		                }
		                else if (splitted[0].equals("Z")) // Get10Highscore
		                {
		                	System.out.println("Get Top 10 highscore. ");
		                	output.print(get10Score());
		                	output.close();
		                }
		                else if (splitted[0].equals("W")) // Check online
		                {
		                	System.out.println("Check if Server Online. ");
		                	output.print(true);
		                	output.close();
		                }
		                else // Close output
		                {
		                	active = false;
		                	output.close();
		                }
		            } 
		            catch (IOException er) 
		            {
		            	System.out.println(er.toString());            
		            }
		        } 
		        
            } 
    		catch (SocketException er) 
    		{ 
    			// Print out possible errors.
    			System.out.println(er.toString());
    		}
    		socket.close();
    		input.close();
    	}     
    }

    /**
     * Retrieves the score from specific user in the highscore file. Return 0 if user doesn't exist. 
     * @param userID - Username of user.
     * @return score - Score of the user.
     * @throws NumberFormatException
     * @throws IOException
     */
	public int getScore(String userID) throws NumberFormatException, IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(location + "\\score.txt"));
		try 
		{
		    String line;
		    while ((line = br.readLine()) != null) 
		    {
		    	if (line.contains(userID))
		    	{
		    		return Integer.valueOf(line.replace(userID + ":", ""));
		    	}
		    }
		} 
		finally 
		{
		    br.close();
		}
		return 0;
	}
	
	/**
	 * Create new highscore file on local desktop. This happens as the server starts to proceed storing, getting and syncing data with client. 
	 * @return True if successfully created file in directory. False if not. 
	 */
	public boolean createFile() throws IOException
	{
		File textFile = new File(location + "\\score.txt");
	    return textFile.createNewFile();
	}
	

	
	/**
	 * Adds new score with username on a new line to the highscore file. Each new score added is then sorted.
	 * @param userID - Username of user
	 * @param score - Score of the user
	 * @throws IOException
	 */
	public void addScore(String userID, int score) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(location + "\\score.txt"));
		try 
		{
		    String line;
		    while ((line = br.readLine()) != null) 
		    {
		    	if (line.contains(userID))
		    	{
		    		return;
		    	}
		    	
		    }
		} 
		finally
		{
			br.close();
		}
		
		try
		{
			Files.write(Paths.get(location + "\\score.txt"), (userID + ":" + score + "\n").getBytes(), StandardOpenOption.APPEND);
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	/** 
	 * Sort the lines in the highscore file based on the int score after receiving new data to the file from client. 
	 * @throws IOException
	 */
	public void sortFile() throws IOException
	{
		BufferedReader readFile = new BufferedReader(new FileReader(location + "\\score.txt"));
		ArrayList<Sort> scoreDatabase = new ArrayList<Sort>();
		String currentLine = readFile.readLine();
         
        while (currentLine != null)
        {
            String[] studentDetail = currentLine.split(":");
             
            String username = studentDetail[0];
  
            int score = Integer.valueOf(studentDetail[1]);
            scoreDatabase.add(new Sort(username, score));
            
            currentLine = readFile.readLine();
        }
        
        Collections.sort(scoreDatabase, new scoreCompare());
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(location + "\\score.txt"));
        for (Sort obj : scoreDatabase) 
        {
            writer.write(obj.username);
            writer.write(":" + obj.score);  
            writer.newLine();
        }
        readFile.close();
        writer.close();
	}	
	
	/**
	 * Get the top (possible) 10 rows from the highscore file and return to client. 
	 * @return String list with each user from the highscore file (top 10 lines).
	 * @throws IOException
	 */
	public String get10Score() throws IOException
	{
		if (!createFile())
		{
			StringBuilder data = new StringBuilder();
			BufferedReader readFile = new BufferedReader(new FileReader(location + "\\score.txt"));
			String currentLine = readFile.readLine();
			
			int i = 0;
	        while (currentLine != null && i < 10)
	        {
	        	String[] user_score = currentLine.split(":");
	        	data.append("Player: " + user_score[0] + " - " + "Score: " + user_score[1] + ",");
	            currentLine = readFile.readLine();
	            i++;
	        }
	        
	        readFile.close();
	        return data.toString(); 
		}
		else
		{
			return "";
		}
	}
}

/**
 * Comparison methods which compares the user and the score of each person. Used for sorting the lines inside the highscore file. Works with the Sort.java file.
 * @author Allan
 *
 */
class compareUsername implements Comparator<Sort>
{
	public int compare(Sort o1, Sort o2) 
	{
		return o1.username.compareTo(o2.username);
	}
	
}	
class scoreCompare implements Comparator<Sort>
{
    @Override
    public int compare(Sort s1, Sort s2)
    {
        return s2.score - s1.score;
    }
}