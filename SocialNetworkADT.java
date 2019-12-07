import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface SocialNetworkADT {
	

	public boolean addFriends(String user,String friend);
	
	public boolean removeFriends(String user, String friend);
	
	/**
	 * adds user to the social network
	 * 
	 * if user is added returns true
	 * if user is already in the graph or null returns false
	 * 
	 * @param user - user to add
	 * @return true if user added, false if null or already exists
	 */
	public boolean addUser(String user);
	
	public boolean removeUser(String user);
	
	public Set<Person> getFriends(String user);
	
	/**
	 * finds mutual friends between user1 and user2
	 * 
	 * @param user1 - first user to compare to
	 * @param user2 - second user to compare to
	 * @return
	 */
	public Set<Person> getMutualFriends(String user1, String user2);
	
	public List<Person>	getShortestPath(String user1, String user2);
	
	public Set<Graph>	getConnectedComponents();
	
	/**
	 * loads input commands from a file and executes commands to
	 * create a graph
	 * 
	 * @param filename - file name to load
	 * @return - main user of type person if set, otherwise just returns null
	 * @throws FileNotFoundException
	 */
	public Person loadFromFile(File filename) throws FileNotFoundException;
	
	/**
	 * saves graph to file by copying commands from log file 
	 * 
	 * @param filename - file to save to
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void saveToFile(File filename) throws FileNotFoundException, IOException;
}
