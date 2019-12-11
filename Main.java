package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * This class defines the GUI interaction and display of the SocialNetwork class.
 * It allows the operator interacting with the GUI to add users, set a central user
 * (view friend/user), upload information to the social network using a valid file, 
 * save a log file, see mutual friends between users, see the shortest path of friends
 * between two users, view the friends of the central user, view the whole network
 * of users, and displays the number of users, friends, and total friendships in the
 * social network.
 * 
 * @author Justin Paddock, Rishi Patel, and Mohamed Alremethi
 *
 */
public class Main extends Application {
	private List<String> args;	// command-line arguments

	private static final int WINDOW_WIDTH = 900;	
	private static final int WINDOW_HEIGHT = 600;
	private static final String APP_TITLE = "Social Network GUI";
	private Label displayHelpMessage = new Label("Welcome! Add a user or "
			+ "load a file to get started.");	// message displayed on bottom
	
	private VBox vBoxLeft = new VBox();
	private VBox vBoxRight = new VBox();
	private VBox centralUserDisplay = new VBox();	// boxes that will be displayed
																								//  on each portion of the BorderPane
	private String centralUser;		
	private SocialNetwork socialNetwork;	// holds all info of social network
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		args = this.getParameters().getRaw();	// command line arguments
		
		
		BorderPane borderPane = new BorderPane();	// visual structure of GUI
		try {
			socialNetwork = new SocialNetwork();	// initialize social network
			
		} catch (Exception e) {
			displayHelpMessage("Could not make log file to write to");
		}
		
		// header of GUI
		Label topLabel = new Label("Social Network");
		topLabel.setFont(new Font("Arial", 28));
		BorderPane.setAlignment(topLabel, Pos.CENTER);
		borderPane.setTop(topLabel);
		
		setCentralUser(centralUser);	// central user is initially set to null
		
		// center display
		borderPane.setCenter(centralUserDisplay);
		
		
		
		// right side of GUI
		vBoxRight.setSpacing(10);
		
		HBox listOfFriends = new HBox();
		
		// create list of users and friends of central user
		ObservableList<String> friendList = FXCollections.<String>observableArrayList();
		ObservableList<String> userList = FXCollections.<String>observableArrayList();
		
		Set<Person> friendSet = socialNetwork.getFriends(centralUser);
		if (friendSet != null) {
			for (Person p : friendSet) {
				friendList.add(p.getName());
			}
		}
		// display friends on ListView
		 
		
		// create the ListView for the friends (initially, also does it for users)
		ListView<String> friends = new ListView<String>();
		friends.getItems().addAll(friendList);
		listOfFriends.getChildren().addAll(friends);
		listOfFriends.setAlignment(Pos.CENTER_RIGHT);
		
		// view friend and remove friend buttons
		HBox friendAddRemove = new HBox();
		Button viewFriend = new Button("View Friend");
		Button removeFriend = new Button("Remove Friend");
		friendAddRemove.getChildren().addAll(viewFriend, removeFriend);
		friendAddRemove.setSpacing(20);
		
		// mutual friends and view network buttons
		HBox mutualViewNetwork = new HBox();
		Button mutualFriends = new Button("View Mutual Friends");
		Button viewNetwork = new Button("View Network");
		mutualViewNetwork.setSpacing(20);
		mutualViewNetwork.getChildren().addAll(mutualFriends, viewNetwork);
		
		// label above ListView
		HBox labelForFriends = new HBox();
		
		Label friendsDisplay = new Label("No existing users");
		friendsDisplay.setFont(new Font("Arial", 15));
		labelForFriends.getChildren().addAll(friendsDisplay);
		labelForFriends.setAlignment(Pos.CENTER);
		
		vBoxRight.getChildren().addAll(labelForFriends, listOfFriends, 
				friendAddRemove, mutualViewNetwork);
		
		// right side of GUI set
		borderPane.setRight(vBoxRight);
		
		
		
		// left side of GUI
		HBox loadFile = new HBox();
		HBox exportFile = new HBox();
		HBox addUserOption = new HBox();
		HBox addFriendshipOption = new HBox();
		HBox shortestPath = new HBox();
		
		// load file button
		Button loadFileButton = new Button("Load File");
		loadFile.getChildren().addAll(loadFileButton);
		loadFile.setSpacing(10);
		Button exportFileButton = new Button("Export Social Network Data");
		exportFile.getChildren().addAll(exportFileButton);
		
		// add user button
		Button addUserButton = new Button("Add User");
		TextField addUserField = new TextField();
		addUserOption.getChildren().addAll(addUserField, addUserButton);
		addUserOption.setSpacing(10);
		
		// add friendship button
		Button addFriendshipButton = new Button("Add Friend (to central user)");
		TextField addFriendshipField = new TextField();
		addFriendshipOption.getChildren().addAll(addFriendshipField, addFriendshipButton);
		addFriendshipOption.setSpacing(10);
		
		// find shortest path button
		Button shortestPathButton = new Button("Find Shortest Path");
		TextField shortestPathField = new TextField("(Enter Two Users)");
		shortestPath.getChildren().addAll(shortestPathField, shortestPathButton);
		shortestPath.setSpacing(10);
		
		// number of users in network label
		Label numUsersLabel = new Label("Number of Users in Social Network: " +
		socialNetwork.getNumUsers());
		
		// number of friendships in network label
		Label numFriendshipsLabel = new Label("Number of Friendships in Social Network: " +
		socialNetwork.getNumFriends());
		
		// number groups in network label
		Label numGroupsLabel = new Label("Number of Groups in Social Network: " 
				+ socialNetwork.getConnectedComponents().size());

		
		vBoxLeft.getChildren().addAll(loadFile, exportFile, addUserOption,
				addFriendshipOption, shortestPath, numUsersLabel, numFriendshipsLabel,
				numGroupsLabel);
		vBoxLeft.setSpacing(50);
		// everything put into a vbox
	
		// display left side of GUI
		borderPane.setLeft(vBoxLeft);

		BorderPane.setAlignment(displayHelpMessage, Pos.BOTTOM_CENTER);
		
		// status/help message displayed
		borderPane.setBottom(displayHelpMessage);
		
		// actions
		// try catch blocks needed to write to a file/load a file/save a file
		
		// add user button action
		addUserButton.setOnAction(e -> { 
			try {
				if (socialNetwork.addUser(addUserField.getText().trim().split(" ")[0])) {
					// social network adds first string of chars in text field
					displayHelpMessage("Successfully added user! Only the first name was recorded.");
					
					// updates labels
					numUsersLabel.setText("Number of Users in Social Network: " +
							socialNetwork.getNumUsers());
					numGroupsLabel.setText("Number of Groups in Social Network: " + 
							socialNetwork.getConnectedComponents().size());
					
					// updates central user if there are no users in the network
					if (centralUser == null) {
						setCentralUser(addUserField.getText().trim().split(" ")[0]);
						friendsDisplay.setText(centralUser + "'s Friends");
					}
					
					// updates "user list" if operator is viewing the full network by 
					//   updating the ListView
					if (viewNetwork.getText().equals("View Friends")) {
						friends.getItems().clear();
						userList.clear();
						for (Person p : socialNetwork.getAllUsers()) {
							userList.add(p.getName());
						}
						friends.getItems().addAll(userList);
					}
					
					// resets text field
					addUserField.setText("");
					
				}
				else {
					displayHelpMessage("Could not add user to network.");
					addUserField.setText("");
				}
			} catch (Exception exception) {
				displayHelpMessage("Log file could not be written to.");
			}
		}
		);
		
		// add friendship button action
		addFriendshipButton.setOnAction(e -> {
			
			// cannot add friend with nothing contained in text field
			if (addFriendshipField.getText().equals("")) {
				displayHelpMessage("Please type a person's name in the text field.");
				return;
			}
			
			// checks if central user exists, does nothing if it doesn't
			if (centralUser == null) {
				displayHelpMessage("Cannot add friend without any users added.");
				addFriendshipField.setText("");
				return;
			}
			
			try {
				// add friendship between central user and name in text field
				if (socialNetwork.addFriends(centralUser, 
						addFriendshipField.getText().trim().split(" ")[0])) {
					displayHelpMessage("Successfully added friendship with central user! "
							+ "Only first names were recorded.");
					
					// update labels
					numFriendshipsLabel.setText("Number of Friendships in Social Network: " +
							socialNetwork.getNumFriends());
					numGroupsLabel.setText("Number of Groups in Social Network: " + 
							socialNetwork.getConnectedComponents().size());
					numUsersLabel.setText("Number of Users in Social Network: " + 
							socialNetwork.getNumUsers());
					
					// update list of friends if central user's friends are being viewed
					if (viewNetwork.getText().equals("View Network")) {
						friends.getItems().clear();
						friendList.clear();
						if (socialNetwork.getFriends(centralUser) != null) {
							for (Person p : socialNetwork.getFriends(centralUser)) {
								friendList.add(p.getName());
							}
						}
						friends.getItems().addAll(friendList);
					
						// update list of users if whole network is being viewed
					} else {
						friends.getItems().clear();
						friendList.clear();
						if (socialNetwork.getAllUsers() != null) {
							for (Person p : socialNetwork.getAllUsers()) {
								friendList.add(p.getName());
							}
						}
						friends.getItems().addAll(friendList);
					}
					
					// reset text field
					addFriendshipField.setText("");
				
				
			} else {
				displayHelpMessage("Friendship cannot be added.");
				addFriendshipField.setText("");
			}
			} catch (Exception exception) {
				displayHelpMessage("Log file could not be written to");
			}
		}
		);
		
		// a FileChooser object is used to load files and save files
		FileChooser fil_chooser = new FileChooser();
		
		// load file button action
		loadFileButton.setOnAction(e -> {
			try {
				// file chooser allows user to go through files on their device
				File file = fil_chooser.showOpenDialog(primaryStage);
				
				Person loadedCentralUser = null;
				loadedCentralUser = socialNetwork.loadFromFile(file);
				// if central user is set in file, it is saved, otherwise first
				//   user in set of all users is placed as central user by default
				if (loadedCentralUser != null) {
					setCentralUser(loadedCentralUser.getName());
				} else {
					userList.clear();
					for (Person p: socialNetwork.getAllUsers()) {
						userList.add(p.getName());
					}
					setCentralUser(userList.get(0));
				}
				displayHelpMessage("File successfully loaded!");
				
				// displays friend list of central user in ListView
				friendList.clear();
				friends.getItems().clear();
				if (socialNetwork.getFriends(centralUser) != null) {
					for (Person p: socialNetwork.getFriends(centralUser)) {
						friendList.add(p.getName());
					}
				}
				friends.getItems().addAll(friendList);
				
				// labels updated
				numUsersLabel.setText("Number of Users in Social Network: " +
						socialNetwork.getNumUsers());
				numFriendshipsLabel.setText("Number of Friendships in SocialNetwork: " +
						socialNetwork.getNumFriends());
				numGroupsLabel.setText("Number of Groups in Social Network: " +
						socialNetwork.getConnectedComponents().size());
				
			} catch (Exception exception) {
				displayHelpMessage("File could not be read");
			}
		}
		);
		
		// Save file button action
		exportFileButton.setOnAction(e -> {
			try {
				// user will only be able to see txt files when saving a file
				FileChooser.ExtensionFilter extFilter = 
						new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fil_chooser.getExtensionFilters().add(extFilter);
        
        // shows directories on device of operator
        File file = fil_chooser.showSaveDialog(primaryStage);
        
   
        if (file != null) {
        	socialNetwork.saveToFile(file);
        	// writes a file to specified directory
  				displayHelpMessage("File successfully exported!");
  				return;
        }
        
        displayHelpMessage("File could not be exported. Please specify a "
        		+ "valid path or name.");
				
			} catch (Exception exception) {
				displayHelpMessage("File could not be saved");
			}
			
		}
		);
		
		// takes away text in text field (of shortest path text field) when clicked
		shortestPathField.setOnMouseClicked(e -> shortestPathField.setText(""));
		
		// shortest path button action
		shortestPathButton.setOnAction(e -> {
			if (shortestPathField.getText().equals("")) {
				
				// cannot have nothing in text field
				displayHelpMessage("Please type in two people's names, separated by whitespace.");
				return;
			}
			
			// the text field must have two strings separated by white space
			if (shortestPathField.getText().trim().split(" ").length == 2) {
				
				// checks if path exists
				if (socialNetwork.getShortestPath(
						shortestPathField.getText().trim().split(" ")[0].trim(),
						shortestPathField.getText().trim().split(" ")[1].trim()) != null) {
					
					// makes list of names using set
					List<String> names = new ArrayList<String>();
					for (Person p : socialNetwork.getShortestPath(
							shortestPathField.getText().trim().split(" ")[0].trim(),
							shortestPathField.getText().trim().split(" ")[1].trim())) {
						names.add(p.getName());
					}
					
					// displays shortest path on status message
					displayHelpMessage("Shortest path between " + 
						shortestPathField.getText().trim().split(" ")[0].trim() + " and " + 
						shortestPathField.getText().trim().split(" ")[1].trim() + ": " 
							+ names.toString());
						
				} else {
					displayHelpMessage("The path between these two users does not exist.");
				}
				
				// resets text field value
				shortestPathField.setText("");
				
			} else {
				displayHelpMessage("Please type in two people's names, separated by whitespace.");
				shortestPathField.setText("");
			}
			
		}
		);
		
		// view friend/user button action (set central user)
		viewFriend.setOnAction(e -> {
			// item has to be selected in ListView
			if (friends.getSelectionModel().getSelectedItem() != null) {
				
				// resets buttons to actions that can be performed on central user's friends
				viewFriend.setText("View Friend");
				viewNetwork.setText("View Network");
				removeFriend.setText("Remove Friend");
				
				// sets central user to selected item in ListView
				setCentralUser(friends.getSelectionModel().getSelectedItem());
				displayHelpMessage("Central user switched to " + centralUser);
				
				// clears friend list
				friendList.clear();
				friends.getItems().clear();
				
				// makes friend list of central user in ListView
				if (socialNetwork.getFriends(centralUser) != null) {
					for (Person p : socialNetwork.getFriends(centralUser)) {
						friendList.add(p.getName());
					}
				}
				friends.getItems().addAll(friendList);
				
				// updates label
				friendsDisplay.setText(centralUser + "'s Friends");
				
			} else {
				displayHelpMessage("No item selected on the ListView. There needs to"
						+ " be a person selected to perform this action.");
			}
		}
		);
		
		
		// remove friend/user button action
		removeFriend.setOnAction(e -> {
			try {
				// item must be selected in ListView
				if (friends.getSelectionModel().getSelectedItem() != null) {
					// checks if button text corresponds to removing a friend
					if (removeFriend.getText().equals("Remove Friend")) {
						if (socialNetwork.removeFriends(centralUser, 
								friends.getSelectionModel().getSelectedItem().trim())) {
							
							// updates friend list in ListView
							friendList.clear();
							friends.getItems().clear();
							
							if (socialNetwork.getFriends(centralUser) != null) {
								for (Person p : socialNetwork.getFriends(centralUser)) {
									friendList.add(p.getName());
								}
							} 
							friends.getItems().addAll(friendList);
							
							displayHelpMessage("Friend successfully removed!");
							
							// updates labels
							numFriendshipsLabel.setText("Number of Friendships in Social Network:" + 
									socialNetwork.getNumFriends());
							numGroupsLabel.setText("Number of Groups in Social Network:" + 
									socialNetwork.getConnectedComponents().size());
							
						} else {
							displayHelpMessage("Friendship does not exist.");
						}
						
						// button text corresponds to removing a user
					} else {
						
						// cannot remove central user
						if (friends.getSelectionModel().getSelectedItem().equals(centralUser)) {
							displayHelpMessage("Cannot remove central user from the network. Please"
									+ " select another central user if you wish to remove this user");
							return;
						}
						
						// removes user selected in ListView
						if (socialNetwork.removeUser(friends.getSelectionModel().getSelectedItem())) {
							
							displayHelpMessage(friends.getSelectionModel().getSelectedItem() + 
									" successfully removed from the Social Network.");
							
							// updates list view with new list of users in network
							friends.getItems().clear();
							userList.clear();
							for (Person p: socialNetwork.getAllUsers()) {
								userList.add(p.getName());
							}
							friends.getItems().addAll(userList);
							
							// updates labels
							numUsersLabel.setText("Number of Users in Social Network: " +
									socialNetwork.getNumUsers());
							numFriendshipsLabel.setText("Number of Friendships in Social Network: " +
									socialNetwork.getNumFriends());
							numGroupsLabel.setText("Number of Groups in Social Network: " + 
									socialNetwork.getConnectedComponents().size());
							
						}
						else {
							displayHelpMessage(friends.getSelectionModel().getSelectedItem() + 
									" could not be removed from the Social Network.");
						}
					}
					
				} else {
					displayHelpMessage("No item selected on the ListView. There needs to"
							+ " be a person selected to perform this action.");
				}
			} catch (Exception exception) {
				displayHelpMessage("Log file could not be written to.");
			}
		}
		);
		
		// mutual friends button action
		mutualFriends.setOnAction(e -> {
			// there has to be users in network to find mutual friends
			if (centralUser == null) {
				displayHelpMessage("There needs to be users in the Social Network to "
						+ "display mutual friends between the central user and another user.");
				return;
			}
			
			// needs to be user selected in ListView to find mutual friends
			if (friends.getSelectionModel().getSelectedItem() == null) {
				displayHelpMessage("No item selected on the ListView. There needs to"
						+ " be a person selected to perform this action.");
				return;
			}
			
			// no mutual friends
			if (socialNetwork.getMutualFriends(centralUser, 
					friends.getSelectionModel().getSelectedItem().trim()).size() == 0) {
				displayHelpMessage("No mutual friends between central user and user "
						+ "selected.");
			// makes list of mutual friends and prints them in status message
			} else {
				Set<Person> mutual = socialNetwork.getMutualFriends(centralUser, 
						friends.getSelectionModel().getSelectedItem().trim());
				List<String> mutualList = new ArrayList<String>();
				
				for (Person p : mutual) {
					mutualList.add(p.getName());
				}
				
				displayHelpMessage("Mutual friends between " + centralUser + 
						" and " + friends.getSelectionModel().getSelectedItem().trim() + 
						": " + mutualList);
			}
		}
		);
		
		// view network button action
		viewNetwork.setOnAction(e -> {
			// checks if button indicates to operator to view the social network
			if (viewNetwork.getText().contentEquals("View Network")) {
				
				
				// checks if there are users in social network
				if (socialNetwork.getAllUsers().size() == 0) {
					friendsDisplay.setText("No Users in current Social Network");
					displayHelpMessage("No users to display. Please add a user to the network.");
					return;
				}
				
				displayHelpMessage("Displaying all of the users in network.");
				
				// otherwise, all users in network displayed in ListView
				friendsDisplay.setText("All Users in current Social Network");
				userList.clear();
				friends.getItems().clear();
				for (Person p : socialNetwork.getAllUsers()) {
					userList.add(p.getName());
				}
				friends.getItems().addAll(userList);
				
				// updates text in buttons
				viewFriend.setText("View User");
				removeFriend.setText("Remove User");
				viewNetwork.setText("View Friends");
				
				// button indicates to operator to view the friends of the central user
			} else {
				
				// updates the ListView with the central user's friends
				displayHelpMessage("Displaying friends of " + centralUser + ".");
				friendsDisplay.setText(centralUser + "'s friends");
				friendList.clear();
				friends.getItems().clear();
				for (Person p: socialNetwork.getFriends(centralUser)) {
					friendList.add(p.getName());
				}
				friends.getItems().addAll(friendList);
				
				// resets buttons
				viewFriend.setText("View Friend");
				removeFriend.setText("Remove Friend");
				viewNetwork.setText("View Network");
			}
		}
		);
		
		// alert (with button types) to be showed upon exit
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Exit");
		alert.setHeaderText("Exit without saving an export file?");
		
		// button types
		ButtonType exitWithoutSaving = new ButtonType("Exit Without Saving");
		ButtonType saveAndExit = new ButtonType("Save and Exit");
		alert.getButtonTypes().setAll(exitWithoutSaving, saveAndExit);
		
		// alert to display if file was saved successfully
		Alert savedSuccessfully = new Alert(AlertType.INFORMATION);
		savedSuccessfully.setTitle("Saved Successfully");
		savedSuccessfully.setHeaderText(null);
		savedSuccessfully.setContentText("File was successfully saved!");
		
		// alert to display if file was not saved
		Alert didNotSave = new Alert(AlertType.ERROR);
		didNotSave.setTitle("Error");
		didNotSave.setHeaderText(null);
		didNotSave.setContentText("File did not save!");
		
		// action after GUI is closed
		primaryStage.setOnCloseRequest(e -> {
			Optional<ButtonType> result = alert.showAndWait();
			
			
			if (result.get() == saveAndExit) {
				try {
					// save file if right button 
					FileChooser.ExtensionFilter extFilter = 
							new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
	        fil_chooser.getExtensionFilters().add(extFilter);
	        File file = fil_chooser.showSaveDialog(primaryStage);
	        
	        // saved successfully
	        socialNetwork.saveToFile(file);
	        savedSuccessfully.showAndWait();
	        
	        // did not save successfully
				} catch (Exception exception) {
					didNotSave.showAndWait();
				}
			}
		}
		);
		
		// make scene, display everything
		Scene mainScene = new Scene(borderPane, WINDOW_WIDTH, WINDOW_HEIGHT);
  	primaryStage.setTitle(APP_TITLE);
  	primaryStage.setScene(mainScene);
  	primaryStage.show();
	}

	/**
	 * display scene
	 * @param args
	 */
	public static void main(String[] args) {
		   launch(args);
	}
	
	
	/**
	 * displays the central user
	 * @param name of the central user
	 */
	private void setCentralUser(String name) {
		
		if (name == null) {
			// default display if no central user is made
			Label user = new Label("No Users");
			user.setFont(new Font("Arial",30));
			user.setWrapText(true);
			centralUserDisplay.getChildren().add(user);
			centralUserDisplay.setSpacing(20);
			centralUserDisplay.setAlignment(Pos.TOP_CENTER);
			
		} else {
			// default display for central user, unique to the name given in the
			//   argument
			centralUserDisplay.getChildren().clear();
			centralUser = name;
			Label user = new Label(centralUser);
			user.setFont(new Font("Arial", 30));
			user.setWrapText(true);
			Image userIm = new Image("user.jpg");
			ImageView iv2 = new ImageView();
			iv2.setImage(userIm);
			HBox hbox = new HBox();
			hbox.getChildren().add(iv2);
			hbox.getChildren().add(user);
			hbox.setSpacing(20);
			centralUserDisplay.getChildren().add(hbox);
			centralUserDisplay.setSpacing(20);
			
			centralUserDisplay.setAlignment(Pos.TOP_CENTER);
		}
	}
	
	/**
	 * setter method for the help/status message displayed on bottom of GUI
	 * @param message to be displayed
	 */
	private void displayHelpMessage(String message) {
		displayHelpMessage.setText(message);
	}
	
}
