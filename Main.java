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

public class Main extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also
	private List<String> args;

	private static final int WINDOW_WIDTH = 900;
	private static final int WINDOW_HEIGHT = 600;
	private static final String APP_TITLE = "Social Network GUI";
	private Label displayHelpMessage = new Label("Welcome! Add a user or "
			+ "load a file to get started.");
	private VBox vBoxLeft = new VBox();
	private VBox vBoxRight = new VBox();
	private VBox centralUserDisplay = new VBox();
	private String centralUser;
	private SocialNetwork socialNetwork;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// save args example
		args = this.getParameters().getRaw();
		
		
		BorderPane borderPane = new BorderPane();
		try {
			socialNetwork = new SocialNetwork();
			
		} catch (Exception e) {
			displayHelpMessage("Could not make log file to write to");
		}

		Label topLabel = new Label("Social Network");
		topLabel.setFont(new Font("Arial", 24));
		BorderPane.setAlignment(topLabel, Pos.CENTER);
		borderPane.setTop(topLabel);
		
		setCentralUser(centralUser);
		
		// Center Implementation

		
		borderPane.setCenter(centralUserDisplay);
		
		
		
		// Right side implementation
		vBoxRight.setSpacing(10);

		
		HBox listOfFriends = new HBox();
		
		// Create list view of users
		
		ObservableList<String> friendList = FXCollections.<String>observableArrayList();
		ObservableList<String> userList = FXCollections.<String>observableArrayList();
		Set<Person> friendSet = socialNetwork.getFriends(centralUser);
		if (friendSet != null) {
			for (Person p : friendSet) {
				friendList.add(p.getName());
			}
		}
		 
		
		// Create the ListView for the friends
		ListView<String> friends = new ListView<String>();
		friends.getItems().addAll(friendList);
		listOfFriends.getChildren().addAll(friends);
		listOfFriends.setAlignment(Pos.CENTER_RIGHT);
		
		HBox friendAddRemove = new HBox();
		Button viewFriend = new Button("View Friend");
		Button removeFriend = new Button("Remove Friend");
		friendAddRemove.getChildren().addAll(viewFriend, removeFriend);
		friendAddRemove.setSpacing(20);
		
		
		HBox mutualViewNetwork = new HBox();
		Button mutualFriends = new Button("View Mutual Friends");
		Button viewNetwork = new Button("View Network");
		mutualViewNetwork.setSpacing(20);
		mutualViewNetwork.getChildren().addAll(mutualFriends, viewNetwork);
		
		HBox labelForFriends = new HBox();
		
		Label friendsDisplay = new Label("No existing users");
		friendsDisplay.setFont(new Font("Arial", 15));
		labelForFriends.getChildren().addAll(friendsDisplay);
		labelForFriends.setAlignment(Pos.CENTER);
		
		vBoxRight.getChildren().addAll(labelForFriends, listOfFriends, 
				friendAddRemove, mutualViewNetwork);
		
		borderPane.setRight(vBoxRight);
		
		
		
		// Left side implementation
		HBox loadFile = new HBox();
		HBox exportFile = new HBox();
		HBox addUserOption = new HBox();
		HBox addFriendshipOption = new HBox();
		HBox shortestPath = new HBox();
		
		Button loadFileButton = new Button("Load File");
		loadFile.getChildren().addAll(loadFileButton);
		loadFile.setSpacing(10);
		
		Button exportFileButton = new Button("Export Social Network Data to File");
		exportFile.getChildren().addAll(exportFileButton);
		
		Button addUserButton = new Button("Add User");
		TextField addUserField = new TextField();
		addUserOption.getChildren().addAll(addUserField, addUserButton);
		addUserOption.setSpacing(10);
		
		Button addFriendshipButton = new Button("Add Friend (to central user)");
		TextField addFriendshipField = new TextField();
		addFriendshipOption.getChildren().addAll(addFriendshipField, addFriendshipButton);
		addFriendshipOption.setSpacing(10);
		
		Button shortestPathButton = new Button("Find Shortest Path");
		TextField shortestPathField = new TextField("(Enter Two Users)");
		shortestPath.getChildren().addAll(shortestPathField, shortestPathButton);
		shortestPath.setSpacing(10);
		
		Label numUsersLabel = new Label("Number of Users in Social Network: " +
		socialNetwork.getNumUsers());
		
		Label numFriendshipsLabel = new Label("Number of Friendships in Social Network: " +
		socialNetwork.getNumFriends());
		
		Label numGroupsLabel = new Label("Number of Groups in Social Network: " 
				+ socialNetwork.getConnectedComponents().size());

		
		vBoxLeft.getChildren().addAll(loadFile, exportFile, addUserOption,
				addFriendshipOption, shortestPath, numUsersLabel, numFriendshipsLabel,
				numGroupsLabel);
		vBoxLeft.setSpacing(50);
	
		borderPane.setLeft(vBoxLeft);

		BorderPane.setAlignment(displayHelpMessage, Pos.BOTTOM_CENTER);
		borderPane.setBottom(displayHelpMessage);
		
		// Actions
		
		// Add user button
		addUserButton.setOnAction(e -> { 
			try {
				if (socialNetwork.addUser(addUserField.getText().trim().split(" ")[0])) {
					
					displayHelpMessage("Successfully added user!");
					numUsersLabel.setText("Number of Users in Social Network: " +
							socialNetwork.getNumUsers());
					numGroupsLabel.setText("Number of Groups in Social Network: " + 
							socialNetwork.getConnectedComponents().size());
					if (centralUser == null) {
						setCentralUser(addUserField.getText().trim().split(" ")[0]);
						friendsDisplay.setText(centralUser + "'s Friends");
					}
					addUserField.setText("");
					
					if (viewNetwork.getText().equals("View Friends")) {
						friends.getItems().clear();
						userList.clear();
						for (Person p : socialNetwork.getAllUsers()) {
							userList.add(p.getName());
						}
						friends.getItems().addAll(userList);
					}
					
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
		
		// Add friendship button
		addFriendshipButton.setOnAction(e -> {
			if (addFriendshipField.getText().equals("")) {
				displayHelpMessage("Please type a person's name in the text field.");
				return;
			}
			
			if (centralUser == null) {
				displayHelpMessage("Cannot add friend without any users added.");
				addFriendshipField.setText("");
				return;
			}
			try {
				if (socialNetwork.addFriends(centralUser, 
						addFriendshipField.getText().trim().split(" ")[0])) {
					displayHelpMessage("Successfully added friendship with central user!");
					numFriendshipsLabel.setText("Number of Friendships in Social Network: " +
							socialNetwork.getNumFriends());
					numGroupsLabel.setText("Number of Groups in Social Network: " + 
							socialNetwork.getConnectedComponents().size());
					numUsersLabel.setText("Number of Users in Social Network: " + 
							socialNetwork.getNumUsers());
					
					if (viewNetwork.getText().equals("View Network")) {
						friends.getItems().clear();
						friendList.clear();
						if (socialNetwork.getFriends(centralUser) != null) {
							for (Person p : socialNetwork.getFriends(centralUser)) {
								friendList.add(p.getName());
							}
						}
						friends.getItems().addAll(friendList);
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
		
		
		FileChooser fil_chooser = new FileChooser();
		loadFileButton.setOnAction(e -> {
			try {
				File file = fil_chooser.showOpenDialog(primaryStage);
				Person loadedCentralUser = null;
				loadedCentralUser = socialNetwork.loadFromFile(file);
				if (loadedCentralUser != null) {
					setCentralUser(loadedCentralUser.getName());
				} else {
					setCentralUser(friendList.get(0));
				}
				displayHelpMessage("File successfully loaded");
				if (socialNetwork.getFriends(centralUser) != null) {
					for (Person p: socialNetwork.getFriends(centralUser)) {
						friendList.add(p.getName());
					}
				}
				friends.getItems().addAll(friendList);
				numUsersLabel.setText("Number of Users in Social Network: " +
						socialNetwork.numUsers());
				numFriendshipsLabel.setText("Number of Friendships in SocialNetwork: " +
						socialNetwork.getNumFriends());
				numGroupsLabel.setText("Number of Groups in Social Network: " +
						socialNetwork.getConnectedComponents().size());
				
			} catch (Exception exception) {
				displayHelpMessage("File could not be read");
			}
		}
		);
		
		// Save file button
		exportFileButton.setOnAction(e -> {
			try {
				socialNetwork.saveToFile(new File("Saved_SocialNetowrk"));
				displayHelpMessage("File saved to same directory as program location.");
			} catch (Exception exception) {
				displayHelpMessage("File could not be saved");
			}
			
		}
		);
		
		// Takes away text in text field (of shortest path text field)
		shortestPathField.setOnMouseClicked(e -> shortestPathField.setText(""));
		
		// Shortest path button
		shortestPathButton.setOnAction(e -> {
			if (shortestPathField.getText().equals("")) {
				displayHelpMessage("Please type in two people's names, separated by whitespace.");
				return;
			}
			
			if (shortestPathField.getText().trim().split(" ").length == 2) {
				if (socialNetwork.getShortestPath(
						shortestPathField.getText().trim().split(" ")[0].trim(),
						shortestPathField.getText().trim().split(" ")[1].trim()) != null) {
					
					List<String> names = new ArrayList<String>();
					for (Person p : socialNetwork.getShortestPath(
							shortestPathField.getText().trim().split(" ")[0].trim(),
							shortestPathField.getText().trim().split(" ")[1].trim())) {
						names.add(p.getName());
		}
					
					displayHelpMessage("Shortest path between " + 
						shortestPathField.getText().trim().split(" ")[0].trim() + " and " + 
						shortestPathField.getText().trim().split(" ")[1].trim() + ": " 
							+ names.toString());
						
				} else {
					displayHelpMessage("The path between these two users does not exist.");
				}
				
				shortestPathField.setText("");
				
			} else {
				displayHelpMessage("Please type in two people's names, separated by whitespace.");
				shortestPathField.setText("");
			}
			
		}
		);
		
		// View friend button
		viewFriend.setOnAction(e -> {
			if (friends.getSelectionModel().getSelectedItem() != null) {
				
				viewFriend.setText("View Friend");
				viewNetwork.setText("View Network");
				removeFriend.setText("Remove Friend");
				
				setCentralUser(friends.getSelectionModel().getSelectedItem());
				displayHelpMessage("Central user switched to " + centralUser);
				
				friendList.clear();
				friends.getItems().clear();
				
				if (socialNetwork.getFriends(centralUser) != null) {
					for (Person p : socialNetwork.getFriends(centralUser)) {
						friendList.add(p.getName());
					}
				}
				
				viewNetwork.setText("View Network");
				friends.getItems().addAll(friendList);
				friendsDisplay.setText(centralUser + "'s Friends");
				
			} else {
				displayHelpMessage("No item selected on the ListView. There needs to"
						+ " be a person selected to perform this action.");
			}
		}
		);
		
		
		// Remove friend button
		removeFriend.setOnAction(e -> {
			try {
				if (friends.getSelectionModel().getSelectedItem() != null) {
					if (removeFriend.getText().equals("Remove Friend")) {
						if (socialNetwork.removeFriends(centralUser, 
								friends.getSelectionModel().getSelectedItem().trim())) {
							socialNetwork.removeFriends(centralUser, 
									friends.getSelectionModel().getSelectedItem().trim());
							
							friendList.clear();
							friends.getItems().clear();
							
							if (socialNetwork.getFriends(centralUser) != null) {
								for (Person p : socialNetwork.getFriends(centralUser)) {
									friendList.add(p.getName());
								}
							} 
							
							friends.getItems().addAll(friendList);
							
							displayHelpMessage("Friend successfully removed!");
							
							numFriendshipsLabel.setText("Number of Friendships in Social Network:" + 
									socialNetwork.getNumFriends());
							
							numGroupsLabel.setText("Number of Groups in Social Network:" + 
									socialNetwork.getConnectedComponents().size());
							
						} else {
							displayHelpMessage("Friendship does not exist.");
						}
					}
					else {
						if (friends.getSelectionModel().getSelectedItem().equals(centralUser)) {
							displayHelpMessage("Cannot remove central user from the network. Please"
									+ " select another central user if you wish to remove this user");
							return;
						}
							
						if (socialNetwork.removeUser(friends.getSelectionModel().getSelectedItem())) {
							displayHelpMessage(friends.getSelectionModel().getSelectedItem() + 
									" successfully removed from the Social Network.");
							
							friends.getItems().clear();
							userList.clear();
							for (Person p: socialNetwork.getAllUsers()) {
								userList.add(p.getName());
							}
							friends.getItems().addAll(userList);
							
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
		
		// Mutual friends button
		mutualFriends.setOnAction(e -> {
			if (friends.getSelectionModel().getSelectedItem() == null) {
				displayHelpMessage("No item selected on the ListView. There needs to"
						+ " be a person selected to perform this action.");
			}
			
			if (socialNetwork.getMutualFriends(centralUser, 
					friends.getSelectionModel().getSelectedItem().trim()).size() == 0) {
				displayHelpMessage("No mutual friends between central user and user "
						+ "selected.");
			} else {
				displayHelpMessage("Mutual friends between " + centralUser + 
						" and " + friends.getSelectionModel().getSelectedItem().trim() + 
						": " + socialNetwork.getMutualFriends(centralUser, 
					friends.getSelectionModel().getSelectedItem().trim()).toString());
			}
		}
		);
		
		// View network button
		viewNetwork.setOnAction(e -> {
			if (viewNetwork.getText().contentEquals("View Network")) {
				displayHelpMessage("Displaying all of the users.");
				friendsDisplay.setText("All Users in current Social Network");
				userList.clear();
				friends.getItems().clear();
				for (Person p : socialNetwork.getAllUsers()) {
					userList.add(p.getName());
				}
				friends.getItems().addAll(userList);
				viewFriend.setText("View User");
				removeFriend.setText("Remove User");
				viewNetwork.setText("View Friends");
				}
			else {
				displayHelpMessage("Displaying friends of " + centralUser + ".");
				friendsDisplay.setText(centralUser + "'s friends");
				friendList.clear();
				friends.getItems().clear();
				for (Person p: socialNetwork.getFriends(centralUser)) {
					friendList.add(p.getName());
				}
				friends.getItems().addAll(friendList);
				viewFriend.setText("View Friend");
				removeFriend.setText("Remove Friend");
				viewNetwork.setText("View Network");
			}
		}
		);
		
		// Alert (with buttons) to be showed upon exit
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Exit");
		alert.setHeaderText("Exit without saving an export file?");
		
		ButtonType exitWithoutSaving = new ButtonType("Exit Without Saving");
		ButtonType saveAndExit = new ButtonType("Save and Exit");
		
		alert.getButtonTypes().setAll(exitWithoutSaving, saveAndExit);
		
		
		primaryStage.setOnCloseRequest(e -> {
			Optional<ButtonType> result = alert.showAndWait();
			
			if (result.get() == saveAndExit) {
				try {
					socialNetwork.saveToFile(new File("Saved_SocialNetwork"));
				} catch (Exception exception) {
					
				}
			}
		}
		);
			
		Scene mainScene = new Scene(borderPane, WINDOW_WIDTH, WINDOW_HEIGHT);
		
  	primaryStage.setTitle(APP_TITLE);
  	primaryStage.setScene(mainScene);
  	primaryStage.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		   launch(args);
	}
	
	
	private void setCentralUser(String name) {
		
		if (name == null) {
			Label user = new Label("No Users");
			user.setFont(new Font("Arial",30));
			user.setWrapText(true);
			centralUserDisplay.getChildren().add(user);
			centralUserDisplay.setSpacing(20);
			centralUserDisplay.setAlignment(Pos.TOP_CENTER);
			
		} else {
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
	
	
	private void displayHelpMessage(String message) {
		displayHelpMessage.setText(message);
	}
	
}
