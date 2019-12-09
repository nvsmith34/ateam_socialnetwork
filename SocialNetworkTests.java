

import org.junit.jupiter.api.BeforeEach;
import org.junit.After;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SocialNetworkTests {
	static SocialNetwork network;
	
	@BeforeEach
	public void setup() throws Exception{
		network = new SocialNetwork();
	}
	
	
	@Test
	public void TestaddFriend() throws IOException {
		network.loadFromFile(new File("friends001.txt"));
		
		network.addUser("Abe");
		network.addFriends("Justin","Jake");
		network.saveToFile(new File("test2"));
		/**
		System.out.print(network.addFriends("Justin", "Jake"));
		network.addFriends("Justin", "Nate");
		System.out.print(network.getFriends("Jake"));
		System.out.print(network.getFriends("Justin"));
		network.removeFriends("Justin", "Jake");
		System.out.print(network.getFriends("Justin"));
		*/


	}
}
