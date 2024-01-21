package views.scenes;

import java.io.File;

import engine.Game;
import javafx.animation.FillTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import views.Main;

public class endgame {
	private Scene end;
	public endgame() {
		Text winMessage=new Text("");
		Group root= new Group ();
		//backgrouind image			
		Image EndBack =new Image (new File("src/Resources/image.png").toURI().toString());
		if (!Game.checkWin()) 
		EndBack =new Image (new File("src/Resources/Lose (2).png").toURI().toString());
		ImageView imageView = new ImageView(EndBack);
        imageView.fitWidthProperty().bind(views.Main.window.widthProperty()); 
        imageView.fitHeightProperty().bind(Main.window.heightProperty());
		root = new Group (imageView);
		Scene FinalScene = new Scene(root);
		
		//add icon
		if (Game.checkWin()) {
		winMessage = new Text("You're a lean, mean, winning machine!");
		FillTransition fillTransition = new FillTransition(Duration.seconds(1), winMessage, Color.GREEN, Color.LIGHTGREEN);
		fillTransition.setAutoReverse(true);
		fillTransition.setCycleCount(FillTransition.INDEFINITE);
		fillTransition.play();}
		else{
		winMessage = new Text("You're such a loser ");
		FillTransition fillTransition = new FillTransition(Duration.seconds(1), winMessage, Color.RED, Color.GRAY);
		fillTransition.setAutoReverse(true);
		fillTransition.setCycleCount(FillTransition.INDEFINITE);
		fillTransition.play();
		}
		winMessage.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		winMessage.setFill(Color.GREEN);
		winMessage.setX(870);
		winMessage.setY(500);
		winMessage.setStyle("-fx-fill: linear-gradient(to bottom, limegreen, darkgreen); -fx-font-family: 'Comic Sans MS'; -fx-font-size: 36px; -fx-stroke: black; -fx-stroke-width: 1px;");
		DropShadow dropShadow = new DropShadow();
		dropShadow.setOffsetX(3);
		dropShadow.setOffsetY(3);
		dropShadow.setColor(Color.color(0, 0, 0, 0.5));
		winMessage.setEffect(dropShadow);
		
		ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), winMessage);
		scaleTransition.setByX(0.5);
		scaleTransition.setByY(0.5);
		scaleTransition.setAutoReverse(true);
		scaleTransition.setCycleCount(ScaleTransition.INDEFINITE);
		scaleTransition.play();
		root.getChildren().add(winMessage);
		//buttons 
		FinalScene.getStylesheets().add("/Resources/styles1.css");

		Button replayButton = new Button("Replay");
		replayButton.setPrefWidth(150);
		replayButton.setPrefHeight(90);
	//	replayButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 20px; -fx-border-color: black; -fx-border-width: 3px; -fx-border-radius: 10px;");
        Button menuButton = new Button("Menu");
        menuButton.setPrefWidth(150);
        menuButton.setPrefHeight(90);
      //  menuButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 20px; -fx-border-color: black; -fx-border-width: 3px; -fx-border-radius: 10px;");
        Button quitButton = new Button("Quit");
        quitButton.setPrefWidth(150);
        quitButton.setPrefHeight(90);
        
        // Terminate the application
        quitButton.setOnAction(event -> {
            ButtonType yesButton = new ButtonType("Yes", ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonData.NO);
        	Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to exit?", yesButton, noButton);
            alert.initOwner(views.Main.window);
            alert.setTitle("Leave Us ?");
            alert.showAndWait();
        	if (alert.getResult() == yesButton) {
        	    // Terminate the program
        	    Platform.exit();
        	} else {
        	    // Do nothing
        	}
        });
        
        menuButton.setOnAction(event -> {
            ButtonType yesButton = new ButtonType("Yes", ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonData.NO);
        	Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to go to the main menu?", yesButton, noButton);
            alert.initOwner(views.Main.window);
            alert.setTitle("Main Menu ?");
            alert.showAndWait();
        	if (alert.getResult() == yesButton) {
        	    // Terminate the program
        		Scene home;
        		home = new Scene(new Home().createContent(), 500, 500);
        		//home.getStylesheets().add(getClass().getResource("/button-49.css").toExternalForm());
        		Main.window.setScene(home);
        		Main.window.setFullScreen(true);
        	} else {
        	    // Do nothing
        	}
        });
        
        replayButton.setOnAction(event -> {
            ButtonType yesButton = new ButtonType("Yes", ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonData.NO);
        	Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to replay?", yesButton, noButton);
            alert.initOwner(views.Main.window);
            alert.setTitle("REPLAY ?");
            alert.showAndWait();
        	if (alert.getResult() == yesButton) {
        	    // Terminate the program
        		Main.window.setScene(new ChampSelect().getChampSelect());
				Main.window.setFullScreen(true);
        	} else {
        	    // Do nothing
        	}
        });
        //creste a boox for buttons and add them 
        HBox HBox = new HBox(50, replayButton, menuButton, quitButton);
        HBox.setTranslateX(1000); // set X coordinate
        HBox.setTranslateY(600);
        HBox.setAlignment(Pos.CENTER);
        HBox.setPadding(new Insets(50));
        root.getChildren().add(HBox);
        
        
		//stage ending 
		this.end=FinalScene;
		
		
	}
	public Scene getEnd() {
		return end;
	}
	
}
