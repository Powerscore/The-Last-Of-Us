package views.scenes;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

import engine.Game;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import views.Main;

public class Home {
	
    private static final String MUSIC_FILE = "src/Resources/gameTheme.mp3";
    private static final Media MUSIC = new Media(Paths.get(MUSIC_FILE).toUri().toString());
    public static final MediaPlayer BACKGROUND_MUSIC = new MediaPlayer(MUSIC);
    public static boolean playOrNot = true;
    static String s1 = "src/Resources/roar.mp3";
	public static Media h1 = new Media(Paths.get(s1).toUri().toString());
    public static GraphicsDevice gd=GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    public static double width = gd.getDisplayMode().getWidth();

    public static double height = gd.getDisplayMode().getHeight();
    public static boolean profver=false;
    private Scene prevScene;
    public Parent createContent() {
    	
    	Pane root = new Pane();
        root.setPrefSize(860, 600);
		System.out.println(width+" "+height);

        BackgroundImage backgroundImage = new BackgroundImage(
            new Image(new File("src/Resources/louwallpaper.jpg").toURI().toString()),
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(300, 300, true, true, true, true)
        );
        root.setBackground(new Background(backgroundImage));

        Title title = new Title("THE LAST OF US");
        title.setTranslateX(-(height/700)*100);
       
        root.getStylesheets().add("/Resources/hand.css");
        Button playButton = createButton("Play");
        playButton.setOnAction(e -> {
            Main.window.setScene((new playername()).getPlayerScene());
            Main.window.setFullScreen(true);
        });

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        Button button0 = createButton("AI Player");
        button0.setOnAction(e -> {
        	
        	Game.name="AI";
            Main.window.setScene((new AiGame()).getGame());
            Main.window.setFullScreen(true);
        });
        Button button2 = createButton("Rules");
        Button button3 = createButton("BALABIZO");
        Button button4 = createButton("RERUN Intro");
        Button button5 = createButton("EXIT");
        button2.setOnMouseClicked(e -> {
        	new help();
        });
        button5.setOnMouseClicked(e -> {
        	Platform.exit();
        });
        button3.setOnMouseClicked(e -> {
            try {
            	profver=true;
            	 Main.window.setScene((new playername()).getPlayerScene());
                 Main.window.setFullScreen(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        button4.setOnMouseClicked(e -> {
            try {
            	Home.BACKGROUND_MUSIC.stop();
                Main.window.setScene(new Intro().getIntroWindow());
                Main.window.setFullScreen(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        vbox.getChildren().addAll(title,playButton, button0, button2, button3, button4,button5);
        vbox.setTranslateX((width/400)*100);
        vbox.setTranslateY((height/700)*100);

        root.getChildren().addAll(vbox);

        music();

        return root;
    }

    
//"-fx-font-family: Inter, Helvetica, \"Apple Color Emoji\", \"Segoe UI Emoji\", NotoColorEmoji, \"Noto Color Emoji\", \"Segoe UI Symbol\", \"Android Emoji\", EmojiSymbols, -apple-system, system-ui, \"Segoe UI\", Roboto, \"Helvetica Neue\", \"Noto Sans\", sans-serif; " 
	public static Button createButton(String text) {
        Button button = new Button(text);
    
        button.setStyle("-fx-pref-width: 250px; " +
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
                "-fx-opacity: 1; " + "-fx-text-fill: #8B0000;"+
                "-fx-padding: 8px 18px; " +
                "-fx-user-select: none; " +
                "-fx-webkit-user-select: none; " +
                "-fx-touch-action: manipulation; " +
                "-fx-word-break: break-word; " +
                "-fx-border-width: 0;");
        button.setOnMouseEntered(e -> {button.setStyle("-fx-background-color: transparent; \r\n"
        		+ "-fx-border-color: transparent;");
		MediaPlayer soundEffect = new MediaPlayer(h1);
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
	
	
	
	public static class Title extends StackPane {
		
		public Title(String name) {
			Rectangle bg = new Rectangle(655, 90);
			bg.setStroke(Color.WHITE);
			bg.setStrokeWidth(2);
			bg.setFill(null);
			
			Text text = new Text(name);
			text.setFill(Color.LIGHTGRAY);
			text.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 70));
			text.setStyle("-fx-font-family: 'Creepster'");
			setAlignment(Pos.CENTER);
			getChildren().addAll(bg, text);
		}
	}
	
	public static void music() {
		
		BACKGROUND_MUSIC.setCycleCount(AudioClip.INDEFINITE);
		BACKGROUND_MUSIC.setVolume(0.05);
		
		if (playOrNot) {
			BACKGROUND_MUSIC.play();
			
		}
		else {
			BACKGROUND_MUSIC.stop();
		}
	}
}
