import java.io.File;
import java.util.List;
import java.util.Set;

public class SocialNetwork implements SocialNetworkADT {
	private Graph graph;
	
	public SocialNetwork() {
		graph = new Graph();
	}
	@Override
	public boolean addFriends(String user, String friend) {
		
		return false;
	}

	@Override
	public boolean removeFriends(String user, String friend) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean addUser(String user) {
		Person person = new Person(user);
		
		return graph.addNode(person) >0;
	
		
	}

	@Override
	public boolean removeUser(String user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Person> getFriends(String user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Person> getShortestPath(String user1, String user2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Graph> getConnectedComponents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadFromFile(File filename) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveToFile(File filename) {
		// TODO Auto-generated method stub
		
	}

}
