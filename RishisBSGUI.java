package application;

import java.io.FileInputStream;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Main extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also
	private List<String> args;

	private static final int WINDOW_WIDTH = 900;
	private static final int WINDOW_HEIGHT = 800;
	private static final String APP_TITLE = "CS400 MyFirstJavaFX";
	private final Image IMAGE_RUBY  = new Image("https://upload.wikimedia.org/wikipedia/commons/f/f1/Ruby_logo_64x64.png");
    private final Image IMAGE_APPLE  = new Image("http://findicons.com/files/icons/832/social_and_web/64/apple.png");
    private final Image IMAGE_VISTA  = new Image("http://antaki.ca/bloom/img/windows_64x64.png");
    private final Image IMAGE_TWITTER = new Image("http://files.softicons.com/download/social-media-icons/fresh-social-media-icons-by-creative-nerds/png/64x64/twitter-bird.png");

    private Image[] listOfImages = {IMAGE_RUBY, IMAGE_APPLE, IMAGE_VISTA, IMAGE_TWITTER};

	@Override
	public void start(Stage primaryStage) throws Exception {
		// save args example
		args = this.getParameters().getRaw();

		// Create a vertical box with Hello labels for each args
		VBox vbox = new VBox();
		HBox hbox = new HBox();
		// Creates a canvas that can draw shapes and text
		Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
		vbox.getChildren().add(canvas);
		hbox.getChildren().add(new Label("CS400 MyFirstJavaFX"));
		
		//Create a combobox
		String comboBox[] = {"a" , "b", "c"};
		ComboBox combo_box = new ComboBox(FXCollections.observableArrayList(comboBox));
		
        //Create image
		FileInputStream input = new FileInputStream("Rishi2.png");
		Image image = new Image(input);
		ImageView imageView = new ImageView (image);
		HBox imageHBox = new HBox(imageView);	
		
		//Create button
		Button b = new Button("Done");
		HBox buttonBox = new HBox(b);
		
		//Create Slider
		Slider slider = new Slider();
		HBox sliderBox = new HBox(slider);

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
//		ScrollBar scroller = new ScrollBar();
//		scroller.setOrientation(Orientation.VERTICAL);
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
		BorderPane root = new BorderPane();
		root.setRight(rightBox);
		// Add the vertical box to the center of the root pane
		root.setTop(hbox);
		root.setLeft(combo_box);
		root.setCenter(imageHBox);
		root.setBottom(buttonBox);
		
		Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		

		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	protected void setGraphic(Object object) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}