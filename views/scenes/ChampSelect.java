package views.scenes;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;

import engine.Game;

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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import views.Main;

public class ChampSelect {
	public static BorderPane root;
	private Scene champSelect;
	private Scene game;
	private Scene prevScene;
	public int counter;
	public ChampSelect(){
		
		BackgroundImage backgroundImage = new BackgroundImage(
	            new Image(new File("src/Resources/back.jpg").toURI().toString()),
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundPosition.CENTER,
	            new BackgroundSize(300, 300, true, true, true, true)
	        );
	        
		root = new BorderPane();
		try {
			Game.loadHeroes("Heroes.csv");
		} catch (IOException e) {
			System.out.println("File not found");
		}
		Button Start= createButton("Start");
		Start.setTranslateX(Home.width*0.29);
		Start.setTranslateY(-80);
        Start.setOnAction(e -> {
        	Game.currentHero=Game.availableHeroes.get(counter);
        	if(Home.profver) {
        		 Main.window.setScene((new ProfVersion()).getGame());
                 Main.window.setFullScreen(true);
        	}else {
            Main.window.setScene((new GameView()).getGame());
            Main.window.setFullScreen(true);
            }
        });
		
		HBox middle=new HBox();
		VBox layout1 = new VBox();
		
		//Name label
		Label namesLabel = new Label("Select Your Champion");
		namesLabel.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(10, 10, 10, 10, false), new Insets(10,10,10,10))));
		namesLabel.setPrefSize(Home.width*(0.273),Home.height*(100.0/1440));
		namesLabel.setTextAlignment(TextAlignment.CENTER);
		
		namesLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-alignment:CENTER;");
		namesLabel.setFont(new Font(50));
		
		
		layout1.getChildren().addAll(namesLabel);
		layout1.setPadding(new Insets(20,20,20,500));
		layout1.setSpacing(20);
		
		counter=0;
		Hero h = Game.availableHeroes.get(0);//will choose character later instead of 0 
		Game.currentHero = h;
		sounds(h.getVoicepath());
		Button right = rightbutton();
		
        
        Button left = leftbutton();
        
		
        
        ImageView imageView = heroimage();
        middle.getChildren().addAll(left,imageView,right);
        middle.setPadding(new Insets(0,0,0,290));
		VBox heroInfo = makeHeroInfo(Game.currentHero);
		heroInfo.setAlignment(Pos.CENTER);
		Button menu= createButton("Menu");
		menu.setOnMouseClicked(e->{
			Scene homeScreenScene = new Scene(new Home().createContent(), 500, 500);
	         Main.window.setScene(homeScreenScene);
	         Main.window.setFullScreen(true);
		});
		menu.setTranslateY(225);
		heroInfo.getChildren().add(menu);
		GridPane heropics=new GridPane();
		heropics.setHgap(50);
		heropics.setVgap(30);

		for(int j=0;j<2;j++)
		for(int i=0;i<4;i++) {
			Image image3 = new Image(new File("src/Resources/"+Game.availableHeroes.get(i+j*4).getPath()).toURI().toString());
	        ImageView imageView1 = new ImageView(image3);
	        //imageView1.setPreserveRatio(true);
	        imageView1.setFitWidth(Home.width*(100.0/2560)); // Set the desired width
	        imageView1.setFitHeight(Home.height*(150.0/1440));
	        heropics.add(imageView1,j,i);
	        final int x=i+j*4;
	        imageView1.setOnMouseClicked(event -> {
	        	
	        	setCounter(x);
	        	sounds(Game.availableHeroes.get(counter).getVoicepath());
	        	Button Start1= createButton("Start");
	        	Start1.setTranslateX(Home.width*(740.0/2560));
	    		Start1.setTranslateY(-Home.height*(80.0/1440));
	            Start1.setOnAction(e1 -> {
	            	Game.currentHero=Game.availableHeroes.get(counter);
	            	if(Home.profver) {
	           		 Main.window.setScene((new ProfVersion()).getGame());
	                    Main.window.setFullScreen(true);
	           	}else {
	               Main.window.setScene((new GameView()).getGame());
	               Main.window.setFullScreen(true);
	               }
	            });
	    		VBox heroInfo1 = makeHeroInfo(Game.currentHero);
	    		root.setRight(heroInfo1);
	    		ImageView imageView2 = heroimage();
	    		HBox middle1= new HBox();
	    		Button left1=leftbutton();
	    		Button right1 =rightbutton();
	            middle1.getChildren().addAll(left1,imageView2,right1);
	            middle1.setPadding(new Insets(0,0,0,290));
	            root.setCenter(middle1);
	    		root.setBottom(Start1);

	        });
		}
		heropics.setPadding(new Insets(0,0,0,30));
        
        
        
		
		
        
		root.setCenter(middle);
		root.setRight(heroInfo);
		root.setLeft(heropics);
		root.setTop(layout1);
		root.setBottom(Start);
		root.setBackground(new Background(backgroundImage));
		this.champSelect = new Scene(root,1000,800);
		champSelect.getStylesheets().add("/Resources/styles.css");
	}
	
	
	
	public int getCounter() {
		return counter;
	}
	
	public Button rightbutton() {
		
		Button right = new Button();
        right.setShape(new Circle(50)); // Set the shape to a circle with a radius of 50 pixels
        right.setMinSize(100, 100); // Set the minimum size of the button
        right.setMaxSize(100, 100); // Set the maximum size of the button
        Image image = new Image(new File("src/Resources/right.png").toURI().toString());
        right.setBackground(new Background(new BackgroundFill(new ImagePattern(image), CornerRadii.EMPTY, Insets.EMPTY)));
        right.setAlignment(Pos.CENTER);
        right.setTranslateY(200);
        right.setOnMouseClicked(e->{
        	setCounter(counter+1);
        	sounds(Game.availableHeroes.get(counter).getVoicepath());
        	Button Start= createButton("Start");
    		Start.setTranslateX(Home.width*(740.0/2560));
    		Start.setTranslateY(-Home.height*(80.0/1440));
            Start.setOnAction(e1 -> {
            	Game.currentHero=Game.availableHeroes.get(counter);
            	if(Home.profver) {
           		 Main.window.setScene((new ProfVersion()).getGame());
                    Main.window.setFullScreen(true);
           	}else {
               Main.window.setScene((new GameView()).getGame());
               Main.window.setFullScreen(true);
               }
            });
    		VBox heroInfo1 = makeHeroInfo(Game.currentHero);
    		root.setRight(heroInfo1);
    		ImageView imageView1 = heroimage();
    		HBox middle1= new HBox();
    		Button left=leftbutton();
    		Button right1 =rightbutton();
            middle1.getChildren().addAll(left,imageView1,right1);
            middle1.setPadding(new Insets(0,0,0,290));
            root.setCenter(middle1);
    		root.setBottom(Start);

        });
        
        return right;
	}
	
	public Button leftbutton() {
		Button left = new Button();
		left.setShape(new Circle(50)); // Set the shape to a circle with a radius of 50 pixels
        left.setMinSize(100, 100); // Set the minimum size of the button
        left.setMaxSize(100, 100); // Set the maximum size of the button
        Image image1 = new Image(new File("src/Resources/left.png").toURI().toString());
        left.setBackground(new Background(new BackgroundFill(new ImagePattern(image1), CornerRadii.EMPTY, Insets.EMPTY)));
        left.setTranslateY(200);
        left.setOnMouseClicked(e->{

        	setCounter(counter-1);
        	sounds(Game.availableHeroes.get(counter).getVoicepath());
        	Button Start= createButton("Start");
        	Start.setTranslateX(Home.width*(740.0/2560));
    		Start.setTranslateY(-Home.height*(80.0/1440));
            Start.setOnAction(e1 -> {
            	Game.currentHero=Game.availableHeroes.get(counter);
            	if(Home.profver) {
           		 Main.window.setScene((new ProfVersion()).getGame());
                    Main.window.setFullScreen(true);
           	}else {
               Main.window.setScene((new GameView()).getGame());
               Main.window.setFullScreen(true);
               }
            });
    		VBox heroInfo1 = makeHeroInfo(Game.currentHero);
    		root.setRight(heroInfo1);
    		ImageView imageView1 = heroimage();
    		HBox middle1= new HBox();
    		Button left1=leftbutton();
    		Button right1 =rightbutton();
            middle1.getChildren().addAll(left1,imageView1,right1);
            middle1.setPadding(new Insets(0,0,0,290));
            root.setCenter(middle1);
    		root.setBottom(Start);

        });
        
        return left;
        
	}
	
	public static Button createButton(String text) {
        Button button = new Button(text);
    
        button.setStyle("-fx-pref-width: 250px; " +
                "-fx-pref-height: 80px; " +
                "-fx-background-color: transparent; " +
                "-fx-background-radius: 50%; " +
                "-fx-box-shadow: #B00B1E 0 10px 20px -10px; " +
                "-fx-box-sizing: border-box; " +
                "-fx-color: #FFFFFF; " +
                "-fx-cursor: pointer; " +
                "-fx-font-family: 'Creepster', sans-serif; "+
                "-fx-font-size: 60px; " +
                "-fx-font-weight: 700; " +
                "-fx-line-height: 24px; " +
                "-fx-opacity: 1; " + "-fx-text-fill: white;"+
                "-fx-padding: 8px 18px; " +
                "-fx-user-select: none; " +
                "-fx-webkit-user-select: none; " +
                "-fx-touch-action: manipulation; " +
                "-fx-word-break: break-word; " +
                "-fx-border-width: 0;");
        
        return button;
    }

	public void setCounter(int counter) {
		if(counter>7)
			this.counter=0;
		else if(counter<0)
			this.counter=7;
		else
			this.counter=counter;
	}

	public Scene getChampSelect() {
		return champSelect;
	}

	public static String getType(Hero h)
	{
		if(h instanceof Fighter)
		{
			return "Fighter";
		}
		if(h instanceof Medic)
			return "Medic";
		else
			return "Explorer"; 
	}

	public static void sounds(String s) {
		String s1 = "src/Resources/"+s+".mp3";
    	Media h1 = new Media(Paths.get(s1).toUri().toString());
    	MediaPlayer soundEffect = new MediaPlayer(h1);
 		soundEffect.play();
 		soundEffect.setOnEndOfMedia(new Runnable() {
 			
 			public void run() {
 				
 				soundEffect.dispose();
 			}
 		});
	}
	
	public ImageView heroimage() {
		Image image2 = new Image(new File("src/Resources/"+Game.availableHeroes.get(getCounter()).getPath()).toURI().toString());
        ImageView imageView = new ImageView(image2);
        //imageView.setPreserveRatio(true);
        imageView.setFitWidth(350); // Set the desired width
        imageView.setFitHeight(600);
        return imageView;
	}
	
	public VBox makeHeroInfo(Hero h)
	{
		VBox heroInfo = new VBox();
		Font font = new Font("Arial",16);
		Label name = new Label("Name: " + Game.heroinfo[getCounter()][0]);
		Label type = new Label("Type: " + Game.heroinfo[getCounter()][1]);
		Label currentHp = new Label("HP: " + Game.heroinfo[getCounter()][2]);
		Label attackDmg = new Label("Attack Damage: " + Game.heroinfo[getCounter()][4]);
		Label actions = new Label("Actions: " + Game.heroinfo[getCounter()][3]);
		//set fonts
		name.setFont(font);
		type.setFont(font);
		name.setFont(font);
		type.setFont(font);
		currentHp.setFont(font);
		attackDmg.setFont(font);
		actions.setFont(font);
		
        int size = 80;
		
		heroInfo.getChildren().addAll(name,type,currentHp,attackDmg,actions);
		
		heroInfo.setPadding(new Insets(0, 40, 0, 0));
		heroInfo.setAlignment(Pos.CENTER);
		
		return heroInfo;
	}
	
}
