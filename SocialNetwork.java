import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class SocialNetwork implements SocialNetworkADT {
	private Graph graph;
	private Person mainUser;
	
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
		return graph.removeNode(graph.getNode(user))>0;
		
	}

	@Override
	public Set<Person> getFriends(String user) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Set<Person> getMutualFriends(String user1, String user2) {
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
	public void loadFromFile(File filename) throws FileNotFoundException {
		Scanner scnr = new Scanner(filename);
		
		while(scnr.hasNextLine()) {
			String input = scnr.nextLine();
			String[] command = input.split(" ");
			switch(command[0]) {
			case "a" : if(command.length == 3)
							addFriends(command[1],command[2]);
						else if(command.length == 2)
							addUser(command[1]);
						
						break;
			case "r": if(command.length==3)
							removeFriends(command[1],command[2]);
					  else if(command.length ==2)
						  removeUser(command[1]);
			
			case "s": if(command.length==2)
						setMainUser(command[1]);
						
			}
		}
		
	}
	
	public void setMainUser(String user) {
		mainUser = graph.getNode(user);
	}
	
	public Person getMainUser(String user) {
		return mainUser;
	}

	@Override
	public void saveToFile(File filename) throws FileNotFoundException {

	}


}
