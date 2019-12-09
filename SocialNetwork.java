import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class SocialNetwork implements SocialNetworkADT {
	private Graph graph;

	private File log;
	PrintWriter writer;
	
	public SocialNetwork() throws FileNotFoundException {
		graph = new Graph();
		log = new File("log.txt");
		writer = new PrintWriter(log);
	}
	@Override
	public boolean addFriends(String user, String friend) {
		
		  if (user == null || friend == null || user.length() == 0 || friend.length() == 0) {
		      return false;
		    }
		    Person per1 = graph.getNode(user);
		    Person per2 = graph.getNode(friend);
		    if (per1 == null) {
		      per1 = new Person(user);
		    }
		    if (per2 == null) {
		      per2 = new Person(friend);
		    }
		    if (graph.addEdge(per1, per2) == 1) {
		    	writer.println("a " + user +" " + friend);
		      return true;
		    }
		    return false;
	}

	@Override
	public boolean removeFriends(String user, String friend) {
		 if (user == null || friend == null || user.length() == 0 || friend.length() == 0) {
		      return false;
		    }
		    Person per1 = graph.getNode(user);
		    Person per2 = graph.getNode(friend);
		    if (per1 == null) {
		      return false;
		    }
		    if (per2 == null) {
		      return false;
		    }
		    if (graph.removeEdge(per1, per2) == 1) {
		    	writer.println("r " + user + " " + friend);
		      return true;
		    }
		    return false;
	}

	@Override
	public boolean addUser(String user) {
	    if (user == null || user.length() == 0) {
	        return false;
	      }
	      Person person = new Person(user);
	      if (graph.addNode(person) == 1) {
	    	  writer.println("a "+user);
	        return true;
	      }
	      return false;
	}

	@Override
	public boolean removeUser(String user) {
	    if (user == null || user.length() == 0) {
	        return false;
	      }
	      Person person = new Person(user);
	      if (graph.removeNode(person) == 1) {
	    	  writer.println("r " + user);
	        return true;
	      }
	      return false;
		
	}

	@Override
	public Set<Person> getFriends(String user) {
	    if (user == null || user.length() == 0) {
	        return null;
	      }
	      Person person = graph.getNode(user);
	      if (person == null) {
	        return null;
	      }
	      List<Person> friendList = graph.getNeighbors(person);
	      Set<Person> friends = new HashSet<Person>();
	      for (Person p : friendList) {
	        friends.add(p);
	      }
	      return friends;
	}
	
	@Override
	public Set<Person> getMutualFriends(String user1, String user2) {
		Set<Person> mutual = getFriends(user1);//get friends of user1 in a Set
		//compare two sets of friends and retain common friends
		mutual.retainAll(getFriends(user2));
		return mutual;
	}

	@Override
	public List<Person> getShortestPath(String user1, String user2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Graph> getConnectedComponents() {
	    Set<Person> allPeople = graph.getAllNodes();
	    Set<Graph> connectedGraphs = new HashSet<Graph>();
	    while (allPeople.size() != 0) {
	      Graph currGraph =  new Graph();
	      Queue<Person> q = new LinkedList<Person>();
	      q.add((Person)allPeople.toArray()[0]);
	      while (!q.isEmpty()) {
	        Person currNode = q.poll();
	        currGraph.addNode(currNode);
	        allPeople.remove(currNode);
	        for (Person p : graph.getNeighbors(currNode)) {
	          if (currGraph.getNode(p.getName()) == null) {
	            currGraph.addEdge(currNode, p);
	            q.add(p);
	            allPeople.remove(p);
	          }
	        }
	      }
	      connectedGraphs.add(currGraph);
	    }
	    return connectedGraphs;
	}

	@Override
	public Person loadFromFile(File filename) throws FileNotFoundException, IllegalArgumentException  {
		Scanner scnr = new Scanner(filename);
		Person mainUser = null;
		
		//loop through each line until run out of input
		while(scnr.hasNextLine()) {
			String input = scnr.nextLine();
			
			//split into seperate string and insert into array
			String[] command = input.split(" ");
			
			//interpret input
			switch(command[0]) {
			case "a" : if(command.length == 3)//add friends on three inputs
							addFriends(command[1],command[2]);
						else if(command.length == 2)//add user on two inputs
							addUser(command[1]);
						//throw illegalArgumentException on invalid input
						else throw new IllegalArgumentException();
						break;
			case "r": if(command.length==3)//remove friends on three inputs
							removeFriends(command[1],command[2]);
					  else if(command.length ==2)//remove user on two inputs
						  removeUser(command[1]);
					  //throw illegalArgumentException on invalid inupt
					  else throw new IllegalArgumentException();
						break;
			case "s": if(command.length==2)//set main user on two inputs
						mainUser = graph.getNode(command[1]);
						//throw illegalArgumentException on invalid input
					   else throw new IllegalArgumentException();
						break;
			//invalid first argument
			default: throw new IllegalArgumentException(); 
			}
		}
		scnr.close();
		return mainUser;
	}

	@Override
	public void saveToFile(File filename) throws IOException {
		
		FileInputStream log = new FileInputStream(this.log);
		FileOutputStream out = new FileOutputStream(filename);
		
		byte[]buffer = new byte[1024];
		int length;
		
		while((length = log.read(buffer))>0) {
			out.write(buffer,0,length);
		}
		
		out.close();
		log.close();
	}


}
