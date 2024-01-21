package views.scenes;

import java.io.File;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import views.Main;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class help {
	public static Scene help;
	
	public help() {

		Image EndBack =new Image (new File("src/Resources/ControlerBackground.jpg").toURI().toString());
		ImageView imageView = new ImageView(EndBack);
        imageView.fitWidthProperty().bind(Main.window.widthProperty()); 
        imageView.fitHeightProperty().bind(Main.window.heightProperty());
        Group root = new Group (imageView);
		Scene FinalScene = new Scene(root);
		
		Image image = new Image(new File("src/Resources/kEYBORD.jpg").toURI().toString());
		ImageView imageView2 = new ImageView(image);
		// Set the position and size of the image view
		imageView2.setX(100); // 100 pixels from the left
		imageView2.setY(50); // 50 pixels from the top
		imageView2.setFitWidth(200); // 200 pixels wide
		imageView2.setFitHeight(150); // 150 pixels high
		root.getChildren().add(imageView2);
		
		
		Text Q = new Text("press Q to End the Turn");
		Text E = new Text("press E to use selected her special action");
		Text F = new Text("press F to attack adjacent zombie");
		Text X = new Text("press X to change the hero ");
		Text C = new Text("press C to cure an adjacent Zombie ");
		Text m = new Text("Move with WASD");

		
		m.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		m.setFill(Color.YELLOW);
		m.setX(20);m.setY(250);
		m.setStyle("-fx-fill: linear-gradient(to bottom, limegreen, darkgreen); -fx-font-family: 'Comic Sans MS'; -fx-font-size: 36px; -fx-stroke: black; -fx-stroke-width: 1px;");
		
		Q.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		Q.setFill(Color.YELLOW);
		Q.setX(20);Q.setY(300);
		Q.setStyle("-fx-fill: linear-gradient(to bottom, limegreen, darkgreen); -fx-font-family: 'Comic Sans MS'; -fx-font-size: 36px; -fx-stroke: black; -fx-stroke-width: 1px;");
		
		E.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		E.setFill(Color.RED);
		E.setX(20);E.setY(350);
		E.setStyle("-fx-fill: linear-gradient(to bottom, limegreen, darkgreen); -fx-font-family: 'Comic Sans MS'; -fx-font-size: 36px; -fx-stroke: black; -fx-stroke-width: 1px;");
		
		F.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		F.setFill(Color.WHITE);
		F.setX(20);F.setY(400);
		F.setStyle("-fx-fill: linear-gradient(to bottom, limegreen, darkgreen); -fx-font-family: 'Comic Sans MS'; -fx-font-size: 36px; -fx-stroke: black; -fx-stroke-width: 1px;");
		
		X.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		X.setFill(Color.BLACK);
		X.setX(20);X.setY(450);
		X.setStyle("-fx-fill: linear-gradient(to bottom, limegreen, darkgreen); -fx-font-family: 'Comic Sans MS'; -fx-font-size: 36px; -fx-stroke: black; -fx-stroke-width: 1px;");
		
		C.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		C.setFill(Color.DARKBLUE);
		C.setX(20);C.setY(500);
		C.setStyle("-fx-fill: linear-gradient(to bottom, limegreen, darkgreen); -fx-font-family: 'Comic Sans MS'; -fx-font-size: 36px; -fx-stroke: black; -fx-stroke-width: 1px;");
			
		root.getChildren().addAll(Q,E,F,X,C,m);
		
		//buttons

		Button Win = new Button("When to win ? and When to Lose");
		Win.setLayoutX(100);
		Win.setLayoutY(550);
		Win.setScaleX(1.75);
		Win.setScaleY(1.75);
        // set the action for the button
		Win.setOnAction(event -> {
            // create an alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            // set the title and header text
            alert.setTitle("End Game");
            alert.setHeaderText(null);
            alert.initOwner(Main.window);
            // set the content text
            alert.setContentText("The game ends when the player\r\n"
            		+ "has collected and used all vaccines or when all heroes have been overwhelmed and defeated by\r\n"
            		+ "the zombies.\r\n"
            		+ "The player only wins if he has successfully collected and used all vaccines and has 5 or more\r\n"
            		+ "heroes alive");
            // show the dialog and wait for the user's response
            alert.showAndWait();
        });
		
		Button Description = new Button("Game Description");
		Description.setLayoutX(100);
		Description.setLayoutY(650);
		Description.setScaleX(1.75);
		Description.setScaleY(1.75);
        // set the action for the button
		Description.setOnAction(event -> {
            // create an alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            // set the title and header text
            alert.setTitle("Description");
            alert.setHeaderText(null);
            alert.initOwner(Main.window);
            // set the content text
            alert.setContentText("The player starts off in a 15x15 grid map with just one hero and 10 zombies. The player can\r\n"
            		+ "only see the directly adjacent cells next to their pool of heroes. The player then keeps taking\r\n"
            		+ "his turn trying to collect vaccines, and cure or kill zombies.");
            // show the dialog and wait for the user's response
            alert.showAndWait();
        });
		
		FinalScene.getStylesheets().add("/Resources/styles1.css");
        Button returnButton = new Button("Back");
        returnButton.setPrefWidth(150);
        returnButton.setPrefHeight(90);
        
        returnButton.setOnMouseClicked(event -> {
        	
        	Scene homeScreenScene = new Scene(new Home().createContent(), 500, 500);
            Main.window.setScene(homeScreenScene);
            Main.window.setFullScreen(true);
        });
        //creste a boox for buttons and add them 
        HBox HBox = new HBox(50, returnButton);
        HBox.setTranslateX(1300); // set X coordinate
        HBox.setTranslateY(700);
        HBox.setAlignment(Pos.CENTER);
        HBox.setPadding(new Insets(50));
        root.getChildren().addAll(HBox,Win,Description);
        

		//stage ending 
		
		Main.window.setTitle("Last of US");
		Main.window.setScene(FinalScene);
		Main.window.show();
		Main.window.setFullScreen(true);
	}
	
}
