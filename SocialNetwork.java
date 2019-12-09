import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;

public class SocialNetwork {
  Graph network;

  public SocialNetwork() {
    network = new Graph();
  }

  public boolean addFriends(String person1, String person2) {
    if (person1 == null || person2 == null || person1.length() == 0 || person2.length() == 0) {
      return false;
    }
    Person per1 = network.getNode(person1);
    Person per2 = network.getNode(person2);
    if (per1 == null) {
      per1 = new Person(person1);
    }
    if (per2 == null) {
      per2 = new Person(person2);
    }
    if (network.addEdge(per1, per2) == 1) {
      return true;
    }
    return false;
  }
  
  public boolean removeFriends(String person1, String person2) {
    if (person1 == null || person2 == null || person1.length() == 0 || person2.length() == 0) {
      return false;
    }
    Person per1 = network.getNode(person1);
    Person per2 = network.getNode(person2);
    if (per1 == null) {
      return false;
    }
    if (per2 == null) {
      return false;
    }
    if (network.removeEdge(per1, per2) == 1) {
      return true;
    }
    return false;
  }
  
  public boolean addUser(String user) {
    if (user == null || user.length() == 0) {
      return false;
    }
    Person person = new Person(user);
    if (network.addNode(person) == 1) {
      return true;
    }
    return false;
  }
  
  public boolean removeUser(String user) {
    if (user == null || user.length() == 0) {
      return false;
    }
    Person person = new Person(user);
    if (network.removeNode(person) == 1) {
      return true;
    }
    return false;
  }
  
  public Set<String> getFriends(String user) {
    if (user == null || user.length() == 0) {
      return null;
    }
    Person person = network.getNode(user);
    if (person == null) {
      return null;
    }
    List<Person> friendList = network.getNeighbors(person);
    Set<String> friends = new HashSet<String>();
    for (Person p : friendList) {
      friends.add(p.getName());
    }
    return friends;
  }
  
  public Set<Graph> getConnectedComponents() {
    Set<Person> allPeople = network.getAllNodes();
    Set<Graph> connectedGraphs = new HashSet<Graph>();
    while (allPeople.size() != 0) {
      Graph currGraph =  new Graph();
      Queue<Person> q = new LinkedList<Person>();
      q.add((Person)allPeople.toArray()[0]);
      while (!q.isEmpty()) {
        Person currNode = q.poll();
        currGraph.addNode(currNode);
        allPeople.remove(currNode);
        for (Person p : network.getNeighbors(currNode)) {
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
  
  private class inPq implements Comparable<inPq> {
    private int dist;
    private Person thePerson;
    private boolean visited;

    public inPq(Person p, int dist) {
      thePerson = p;
      this.dist = dist;
    }
    public void visit() {
      visited = true;
    }
    public Person getPerson() {
      return thePerson;
    }
    public boolean getVisited() {
      return visited;
    }
    @Override
    public int compareTo(inPq comp) {
      if (dist == -1) {
        return 1;
      }
      if (comp.dist == -1) {
        return -1;
      }
      return dist - comp.dist;
    }
    
  }
  
  public List<Person> getShortestPath(String person1, String person2) {
    if (person1 == null || person2 == null || person1.length() == 0 || person2.length() == 0) {
      return null;
    }
    Person per1 = network.getNode(person1);
    Person per2 = network.getNode(person2);
    if (per1 == null || per2 == null) {
      return null;
    }
    List<inPq> bitch = new LinkedList<inPq>();
    for (Person p : network.getAllNodes()) {
      inPq qAddition = new inPq(p, -1);
      bitch.add(qAddition);
    }
    PriorityQueue<inPq> pq = new PriorityQueue<inPq>();
    return null;
  }
}
