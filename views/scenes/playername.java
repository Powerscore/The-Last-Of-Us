package views.scenes;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.lang.Thread;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
//import views.AlertBox;
import views.Main;
public class playername {
	private Scene playerScene;
	private Scene prevScene;
	private Scene champSelect;
	static String s1 = "src/Resources/hi.wav";
	static String s2 = "src/Resources/balabizo.mp3";
	public static Media h1 = new Media(Paths.get(s1).toUri().toString());
	public static Media h2 = new Media(Paths.get(s2).toUri().toString());
	public playername() {
		VBox mainLayout = new VBox();
		VBox layout1 = new VBox();
		
		//Name label
		Label namesLabel = new Label("Please Enter Your Name");
		namesLabel.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(10, 10, 10, 10, false), new Insets(10,10,10,10))));
		namesLabel.setPrefSize(700,100);
		namesLabel.setTextAlignment(TextAlignment.CENTER);
		
		namesLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-alignment:CENTER;");
		namesLabel.setFont(new Font(50));
		
		//background
		BackgroundImage backgroundImage = new BackgroundImage(
	            new Image(new File("src/Resources/nameback.jpg").toURI().toString()),
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundPosition.CENTER,
	            new BackgroundSize(300, 300, true, true, true, true)
	        );
	        
		
		
		// name textfield
		TextField name1Input = new TextField();
		
		name1Input.setPromptText("Player 1");
		name1Input.setPrefSize(15, 150);
		name1Input.setMaxWidth(500);
		name1Input.setFont(new Font(60));
		name1Input.setStyle("-fx-text-fill: lightgrey; -fx-alignment:CENTER;-fx-background-color: transparent;-fx-font-family: \"Creepster\";-fx-font-size: 60; \r\n"
				+ "");
		name1Input.setOnMouseClicked(e -> {
			
			MediaPlayer soundEffect = new MediaPlayer(h2);
			soundEffect.play();
			soundEffect.setOnEndOfMedia(new Runnable() {
				
				public void run() {
					
					soundEffect.dispose();
				}
			});
		});

		
		
		layout1.getChildren().addAll(namesLabel, name1Input);
		layout1.setPadding(new Insets(20,20,20,20));
		layout1.setSpacing(20);
		
		VBox layout2 = new VBox();
		layout2.setAlignment(Pos.CENTER);
		layout1.setAlignment(Pos.CENTER);
		//Back to home screen scene Button
		Button backToHomeScreenButton = createButton("Back");
		backToHomeScreenButton.setOnMouseClicked(new EventHandler<Event>(){
			
			@Override
			public void handle(Event event) {
				prevScene = new Scene(new Home().createContent(), 500, 500);
				Main.window.setScene(prevScene);
				Main.window.setFullScreen(true);
			}
			});
		// button to go to champion selection scene
		Button chooseChampionsButton = createButton("Champion Select");
		chooseChampionsButton.setOnMouseClicked(e -> {
			checkselect(chooseChampionsButton,name1Input);
		});
		backToHomeScreenButton.setOnMouseClicked(e -> {
			MediaPlayer soundEffect = new MediaPlayer(h1);
			soundEffect.play();
			
					soundEffect.dispose();
					prevScene = new Scene(new Home().createContent(), 500, 500);
					Main.window.setScene(prevScene);
					Main.window.setFullScreen(true);
					soundEffect.setOnEndOfMedia(new Runnable() {
						
						public void run() {
							
							soundEffect.dispose();
						}
					});
		});
		
		
		layout2.getChildren().addAll( chooseChampionsButton,backToHomeScreenButton);
		layout2.setPadding(new Insets(20,20,20,20));
		layout2.setSpacing(20);
		
		
		
		BackgroundFill background = new BackgroundFill(Color.valueOf("#00FFD0"), new CornerRadii(0), new Insets(0));
		Background bg = new Background(background);
		mainLayout.setBackground(bg);
		mainLayout.getChildren().addAll(layout1,layout2);
		mainLayout.setPadding(new Insets(50, 50, 50, 50));
		mainLayout.setSpacing(30);
		mainLayout.setAlignment(Pos.TOP_CENTER);
		mainLayout.setBackground(new Background(backgroundImage));
		this.playerScene=new Scene(mainLayout, 1000, 1000);;
		
	}
	
	private Button createButton(String text) {
        Button button = new Button(text);
        
 
        button.setStyle("-fx-pref-width: 250px; " +
                "-fx-pref-height: 80px; " +
                "-fx-background-color: transparent; " +
                "-fx-background-radius: 50%; " +
                "-fx-box-shadow: #B00B1E 0 10px 20px -10px; " +
                "-fx-box-sizing: border-box; " +
                "-fx-text-fill: #8B0000; " +
                "-fx-cursor: pointer; " +
                "-fx-font-family: 'Creepster', sans-serif; "+
                "-fx-font-size: 32px; " +
                "-fx-font-weight: 700; " +
                "-fx-line-height: 24px; " +
                "-fx-opacity: 1; " + "-fx-text-fill: #8B0000;"+
                "-fx-padding: 8px 18px; " +
                "-fx-user-select: none; " +
                "-fx-webkit-user-select: none; " +
                "-fx-touch-action: manipulation; " +
                "-fx-word-break: break-word; " +
                "-fx-border-width: 0;");
        button.setOnMouseEntered(e -> {button.setStyle("-fx-background-color: transparent; \r\n"
        		+ "-fx-border-color: transparent;");
		MediaPlayer soundEffect = new MediaPlayer(h2);
		soundEffect.play();
		soundEffect.setOnEndOfMedia(new Runnable() {
			
			public void run() {
				
				soundEffect.dispose();
			}
		});

        	
        });
        button.setOnMouseExited(e -> {
            button.setStyle("-fx-pref-width: 250px; " +
                            "-fx-pref-height: 80px; " +
                            "-fx-background-color: transparent; " +
                            "-fx-background-radius: 50%; " +
                            "-fx-box-shadow: #B00B1E 0 10px 20px -10px; " +
                            "-fx-box-sizing: border-box; " +
                            "-fx-text-fill: #8B0000; " +
                            "-fx-cursor: pointer; " +
                            "-fx-font-family: 'Creepster'; " +
                            "-fx-font-size: 32px; "+ "-fx-font-weight: 700; " +
                            "-fx-line-height: 24px; " +
                            "-fx-opacity: 1; " + "-fx-text-fill: #8B0000;"+
                            "-fx-padding: 8px 18px; " +
                            "-fx-user-select: none; " +
                            "-fx-webkit-user-select: none; " +
                            "-fx-touch-action: manipulation; " +
                            "-fx-word-break: break-word; " +
                            "-fx-border-width: 0;");
        });
        
        return button;
    }
	
public void checkselect(Button check, TextField name) {
		
			if(name.getText().length()==0) {
				name.setPromptText("BALABIZOO");
			}else{
				
				Main.window.setScene(new ChampSelect().getChampSelect());
				Main.window.setFullScreen(true);
			}
	}
	
public Scene getPlayerScene() {
		this.playerScene.getStylesheets().add("/Resources/styles.css");
		this.playerScene.getStylesheets().add("/Resources/hand.css");

		return this.playerScene;
	}
}
