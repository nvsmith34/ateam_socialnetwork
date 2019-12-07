package application;

import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also
	private List<String> args;

	private static final int WINDOW_WIDTH = 900;
	private static final int WINDOW_HEIGHT = 600;
	private static final String APP_TITLE = "Social Network GUI";
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// save args example
		args = this.getParameters().getRaw();
		
		BorderPane borderPane = new BorderPane();

		Label topLabel = new Label("Bacefook");
		topLabel.setFont(new Font("Arial", 24));
		borderPane.setAlignment(topLabel, Pos.CENTER);
		borderPane.setTop(topLabel);
		
		VBox vBoxLeft = new VBox();
		
		HBox clearNetwork = new HBox();
		HBox loadFile = new HBox();
		HBox exportFile = new HBox();
		HBox addUserOption = new HBox();
		HBox addFriendshipOption = new HBox();
		HBox numGroups = new HBox();
		HBox otherButtons = new HBox();
		
		Button clearNetworkButton = new Button("Clear Network");
		clearNetwork.getChildren().addAll(clearNetworkButton);
		
		Button loadFileButton = new Button("Load File");
		TextField loadFileField = new TextField();
		loadFile.getChildren().addAll(loadFileField, loadFileButton);
		
		Button exportFileButton = new Button("Export File");
		exportFile.getChildren().addAll(exportFileButton);
		
		Button addUserButton = new Button("Add User");
		TextField addUserField = new TextField();
		addUserOption.getChildren().addAll(addUserField, addUserButton);
		
		Button addFriendshipButton = new Button("Add Friendship");
		TextField addFriendshipField = new TextField();
		addFriendshipOption.getChildren().addAll(addFriendshipField, addFriendshipButton);
		
		Label numGroupsLabel = new Label("Number of Groups in Social Network: 1");
		numGroups.getChildren().addAll(numGroupsLabel);
		
		
		Button back = new Button("Back");
		Button undo = new Button("Undo");
		Button redo = new Button("Redo");
		otherButtons.getChildren().addAll(back, undo, redo);
		otherButtons.setSpacing(15);
		
		vBoxLeft.getChildren().addAll(clearNetwork, loadFile, exportFile, addUserOption,
				addFriendshipOption, numGroupsLabel, otherButtons);
		vBoxLeft.setSpacing(32);
		
		
		borderPane.setLeft(vBoxLeft);
		
		
		
		
	// Create right pane
			VBox rightBox = new VBox();
			rightBox.setSpacing(10);
			//Select network or friends window
			HBox H1 = new HBox();
			H1.getChildren().addAll(new Button("View Friends"), new Button("View Network"));
			H1.setSpacing(30);
			HBox H2 = new HBox();
			TextField textField = new TextField();
			Button searchBut = new Button("Search");
			H2.getChildren().addAll(textField, searchBut);
			H2.setSpacing(25);
			HBox H3 = new HBox();
		    // Add slider bar
//			ScrollBar scroller = new ScrollBar();
//			scroller.setOrientation(Orientation.VERTICAL);
			// Create list view of users
			ObservableList<String> userList = FXCollections.<String>observableArrayList("User1", "User2", "User3", "User4");
			// Create the ListView for the fruits
			ListView<String> users = new ListView<String>();
			users.getItems().addAll(userList);
			H3.getChildren().add(users);
			H3.setAlignment(Pos.CENTER_RIGHT);
			HBox H4 = new HBox();
			H4.getChildren().addAll(new Button("Remove Friend"), new Button("Add Friends"));
			H4.setSpacing(20);
			HBox H5 = new HBox();
			H5.getChildren().addAll(new Button("View Mutual Friends"), new Button("Back"));
			H5.setSpacing(20);
			rightBox.getChildren().addAll(H1,H2,H3,H4,H5);
			// Main layout is Border Pane example (top,left,center,right,bottom)
			borderPane.setRight(rightBox);
		
		
		
			VBox vboxCenter = new VBox();
			HBox hbox = new HBox();
			Label user = new Label("Deb Deppeler");
			user.setFont(new Font("Arial",30));
			user.setWrapText(true);
			Image userIm = new Image("user.jpg");
			ImageView iv2 = new ImageView();
			iv2.setImage(userIm);
			hbox.getChildren().add(iv2);
			hbox.getChildren().add(user);
			hbox.setSpacing(20);
			vboxCenter.getChildren().add(hbox);
			vboxCenter.setSpacing(20);
			
			Label gender = new Label("Gender: Female");
			gender.setFont(new Font("Arial",20));
			vboxCenter.getChildren().add(gender);
			
			Label school = new Label("School: UW-Madison");
			school.setFont(new Font("Arial",20));
			vboxCenter.getChildren().add(school);
			
			Label relationship = new Label("Relationship Status: Complicated");
			relationship.setFont(new Font("Arial",20));
			vboxCenter.getChildren().add(relationship);
			
			vboxCenter.setAlignment(Pos.TOP_CENTER);
			borderPane.setCenter(vboxCenter);
			
			
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
}