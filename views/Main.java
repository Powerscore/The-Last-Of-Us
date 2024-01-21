package views;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import engine.Game;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import views.scenes.Home;


public class Main  extends Application {
	
	
//	public static String fontString = "src/Resources/PressStart2P-Regular.ttf";
//	public static Font font = Font.loadFont(fontString, 45);

	
	public static Stage window;
	private Scene IntroWindow;
	private Scene home;
	
	public static void main(String[] args) {

		launch(args);
	
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		String fontPath = "src/Resources/bloody.otf";
		Font customFont = Font.loadFont(fontPath, 16);
		customFont =Font.loadFont(Main.class.getResourceAsStream("/Resources/bloody.otf"), 20);
		InputStream fontStream = Main.class.getResourceAsStream("/Resources/Creepster-Regular.ttf");
		customFont = Font.loadFont(fontStream, 20);
		
		    
		    
		window = primaryStage;
		
		Image image = new Image(new File("src/Resources/lastofuscover.jpg").toURI().toString());
		window.getIcons().add(image);
		
		//window.setTitle("Marvels Ultimate War");
		
		window.setTitle("THE LAST OF US");
		
		//window.setScene(new HomeScreenWindow().getHomeScreenScene());
		window.setScene(new views.scenes.Intro().getIntroWindow());
		window.setFullScreen(true);

		home = new Scene(new Home().createContent(), 500, 500);
		//home.getStylesheets().add(getClass().getResource("/button-49.css").toExternalForm());
		//Main.window.setScene(home);
		//Main.window.setFullScreen(true);
		
		window.show();
		window.setOnCloseRequest(event -> {
            if (primaryStage.isMaximized()) {
        		Main.window.setFullScreen(true);
        		event.consume();
            }
		});
		
		
		
	}
	
	
	public Scene getIntroWindow() {
		return IntroWindow;
	}
	
}
