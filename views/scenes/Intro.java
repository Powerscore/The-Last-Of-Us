package views.scenes;

import java.io.File;
import model.characters.Character;
import java.net.MalformedURLException;
import java.nio.file.Paths;

import engine.Game;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;

import views.Main;

public class Intro {
    private static String MEDIA_FILE ;
    private final Scene introWindow;
    private Scene homeScreenScene;
    private MediaPlayer mediaPlayer;
    public static int count=0;
    public Intro() throws Exception {
    	System.out.println(count);
    	if((Game.checkGameOver()&&count >0)||Character.lost )
		{
    		
			if(Game.checkWin()&& !(Character.lost))
	
		    		MEDIA_FILE = "src/Resources/Win.mp4";

					
				
			else
	    		MEDIA_FILE = "src/Resources/Lose.mp4";
		
    }
    	else
    		MEDIA_FILE = "src/Resources/Intovod.mp4";
    	System.out.println(MEDIA_FILE);
        String mediaPath = new File(MEDIA_FILE).toURI().toString();
        Media media = new Media(mediaPath);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        MediaView mediaView = new MediaView (mediaPlayer);
        mediaView.fitWidthProperty().bind(Main.window.widthProperty()); 
        mediaView.fitHeightProperty().bind(Main.window.heightProperty());
        mediaView.setPreserveRatio(false);
        VBox root = new VBox();
        

        Button skip = new Button("Skip");
        skip.setOnMouseClicked(e->{
        	mediaPlayer.stop();
        	nextScene();
        });
        skip.setStyle("-fx-pref-width: 250px; " +
                "-fx-pref-height: 80px; " +
                "-fx-background-color: transparent; " +
                "-fx-background-radius: 50%; " +
                "-fx-box-shadow: #B00B1E 0 10px 20px -10px; " +
                "-fx-box-sizing: border-box; " +
                "-fx-color: #8B0000; " +
                "-fx-cursor: pointer; " +
                "-fx-font-family: 'Creepster', sans-serif; "+
                "-fx-font-size: 32px; " +
                "-fx-font-weight: 700; " +
                "-fx-line-height: 24px; " +
                "-fx-opacity: 1; " + "-fx-text-fill: #FFFFFF;"+
                "-fx-padding: 8px 18px; " +
                "-fx-user-select: none; " +
                "-fx-webkit-user-select: none; " +
                "-fx-touch-action: manipulation; " +
                "-fx-word-break: break-word; " +
                "-fx-border-width: 0;");
     
        
        
        skip.setTranslateX(770);
        skip.setTranslateY(700);
        skip.setPadding(new Insets(10,10,10,10));
        
        Pane rootOverriden = new Pane();
        root.getChildren().addAll(mediaView);
        root.setAlignment(Pos.CENTER);
        rootOverriden.getChildren().addAll(root,skip);
        
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
               
               nextScene();
            }
        });
        introWindow = new Scene(rootOverriden,600,400, Color.BLACK);
		introWindow.getStylesheets().add("/Resources/styles1.css");


    }
    public void nextScene() {
    	 mediaPlayer.dispose();
    	 if(!Game.checkGameOver()||count==0)
         { 
    		 homeScreenScene = new Scene(new Home().createContent(), 500, 500);
         Main.window.setScene(homeScreenScene);}
         else
         {
    		 count--;
         	 homeScreenScene = new endgame().getEnd();
         	 Character.lost=false;
         	Home.BACKGROUND_MUSIC.play();
              Main.window.setScene(homeScreenScene);
              Main.window.show();
         }
    	 
         Main.window.setFullScreen(true);
    }
    public Scene getIntroWindow() {
        return introWindow;
    }

    public Scene getHomeScreenScene() {
        return homeScreenScene;
    }
}