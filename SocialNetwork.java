import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.PriorityQueue;
import java.util.Arrays;

public class SocialNetwork implements SocialNetworkADT {
	private Graph graph;

	private File log;
	PrintStream writer;
	
	public SocialNetwork() throws FileNotFoundException {
		graph = new Graph();
		log = new File("log.txt");
		writer = new PrintStream(log);

	}
	@Override
	public boolean addFriends(String user, String friend) throws FileNotFoundException {
		
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
		    	//writer = new PrintWriter(log);
		    	writer.println("a " + user +" " + friend);
		    	//writer.close();
		      return true;
		    }
		    return false;
	}

	@Override
	public boolean removeFriends(String user, String friend) throws FileNotFoundException {
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
		    	//writer = new PrintWriter(log);
		    	writer.println("r " + user + " " + friend);
		    	//writer.close();
		      return true;
		    }
		    return false;
	}

	@Override
	public boolean addUser(String user) throws FileNotFoundException {
	    if (user == null || user.length() == 0) {
	        return false;
	      }
	      Person person = new Person(user);
	      if (graph.addNode(person) == 1) {
	    		//writer = new PrintWriter(log);
	    	  writer.println("a "+user);
	    	//  writer.close();
	        return true;
	      }
	      return false;
	}

	@Override
	public boolean removeUser(String user) throws FileNotFoundException {
	    if (user == null || user.length() == 0) {
	        return false;
	      }
	      Person person = new Person(user);
	      if (graph.removeNode(person) == 1) {
	    	//	writer = new PrintWriter(log);
	    	  writer.println("r " + user);
	    	//  writer.close();
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
	
	public Set<Person> getAllUsers(){
		return graph.getAllNodes();
	}
	@Override
	public Set<Person> getMutualFriends(String user1, String user2) {
		Set<Person> mutual = getFriends(user1);//get friends of user1 in a Set
		//compare two sets of friends and retain common friends
		mutual.retainAll(getFriends(user2));
		return mutual;
	}

	private class inPq implements Comparable<inPq> {
      		private int dist;
      		private Person thePerson;

      		public inPq(Person p, int dist) {
        		thePerson = p;
        		this.dist = dist;
      		}
      		@Override
      		public int compareTo(inPq comp) {
      		  	return dist - comp.dist;
      		}
    	}

    	@Override
    	public List<Person> getShortestPath(String person1, String person2) {
      		if (person1 == null || person2 == null || person1.length() == 0 || person2.length() == 0) {
        		return null;
      		}
      		Person per1 = graph.getNode(person1);
      		Person per2 = graph.getNode(person2);
      		if (per1 == null || per2 == null) {
        		return null;
      		}
      
     		List<Person> people = Arrays.asList((((Person[])graph.getAllNodes().toArray())));
      		int[] distance = new int[people.size()];
      		boolean[] visited = new boolean[people.size()];
      		Person[] pred = new Person[people.size()];
      
      		List<inPq> nodes = new LinkedList<inPq>();
      		for (Person p : people) {
        		inPq qAddition = new inPq(p, -1);
        		nodes.add(qAddition);
      		}
      		PriorityQueue<inPq> pq = new PriorityQueue<inPq>();
      		for (inPq in : nodes) {
        		if (in.thePerson.getName().equals(person1)) {
          			pq.add(in);
          			in.dist = 0;
          			distance[people.indexOf(in.thePerson)] = 0;
          			visited[people.indexOf(in.thePerson)] = true;
          			break;
        		}
      		}
      		while (!pq.isEmpty()) {
        		inPq currNode = pq.poll();
        		visited[people.indexOf(currNode.thePerson)] = true;
        		for (Person p : getFriends(currNode.thePerson.getName())) {
          			int index = people.indexOf(p);
          			if (!visited[index]) {
            				if (distance[index] == -1 || distance[index] > (currNode.dist + 1)) {
              					nodes.get(index).dist = currNode.dist + 1;
              					distance[index] = currNode.dist + 1;
              					pred[index] = currNode.thePerson;
              					pq.add(nodes.get(index));
            				}
          			}
        		}
      		}
      		int start = people.indexOf(per2);
      		int end = people.indexOf(per1);
      		int currIndex = end;
      		LinkedList<Person> theOrder = new LinkedList<Person>();
      		theOrder.add(per2); // not sure if final node should be included
      		while (currIndex != start) {
        		theOrder.addFirst(pred[currIndex]);
        		currIndex = people.indexOf(pred[currIndex]);
      		}
      		return theOrder;
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
	
	public int getNumUsers() {
		return graph.order();
	}
	
	public int getNumFriends() {
		return graph.size();
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
