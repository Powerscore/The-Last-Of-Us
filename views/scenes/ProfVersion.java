package views.scenes;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.media.*;
import model.characters.Direction;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import views.Main;

public class ProfVersion {
	private static int prof;
	private static Scene game;
	public static BorderPane root;
	public static Image images[]=new Image[10];
	private static VBox heroInfo;

	private static BackgroundImage backgroundImg = new BackgroundImage(new Image("/Resources/black.jpg", 0, 0, false, true),
			BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			new BackgroundSize(1.0, 1.0, true, true, false, false));
	private static BackgroundImage botBackgroundImg = new BackgroundImage(new Image("/Resources/black.jpg", 0, 0, false, true),
			BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			new BackgroundSize(1.0, 1.0, true, true, false, false));
	public static boolean zoombool=false;
	public static int zoomval=0;
	
	//------
	 private static final double MIN_SCALE = 1;
	 private static final double MAX_SCALE = 10.0;
     private static final double SCALE_DELTA = 1.1;
	    
	    
	public Scene getGame() {
		return game;
	}

	public ProfVersion() {
		Home.profver=false;
		// TODO Auto-generated method stub
		zoombool=false;
		// test hero

		Hero h = Game.currentHero;// will choose character later instead of 0
		Game.startGame(h);
		h.setActionsAvailable(100);

		String[] paths1 = new String[10];
		paths1[5] = "/Resources/clouds.png";
		paths1[6] = "/Resources/Zombie.png";
		paths1[7] = "/Resources/Supply.png";
		paths1[8] = "/Resources/vaccine.png";
		//paths1[9] = "/Resources/empty.png";
		int size=(int) (Home.height * (58.0 / 1440));
		for(int i=5;i<9;i++) {
			Image image = new Image(paths1[i]);
			 // set the height of the image
			images[i]=image;
		}
		root = new BorderPane();
		root.setBackground(new Background(backgroundImg));
		Scene gameView = new Scene(root, 1000, 800);
		gameView.getStylesheets().add("/Resources/styles.css");

		GridPane grid = makeGrid();
	//--------------------------------------------------------------------
		 ScrollPane scrollPane = new ScrollPane(grid);
			scrollPane.setBackground(new Background(backgroundImg));

	        scrollPane.setFitToHeight(true);
	        scrollPane.setFitToWidth(true);

	        // Create a Scale object for the transformation
	        Scale scale = new Scale(1, 1);
	        grid.getTransforms().add(scale);

	        // Add a scroll event handler for zooming
	        scrollPane.setOnScroll(new EventHandler<ScrollEvent>() {
	            @Override
	            public void handle(ScrollEvent event) {
	                event.consume();

	                double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA : 1 / SCALE_DELTA;

	                // Apply scaling limits
	                double currentScale = scale.getX();
	                if (currentScale * scaleFactor < MIN_SCALE || currentScale * scaleFactor > MAX_SCALE) {
	                    return;
	                }

	                // Zoom in/out by scaling the GridPane
	                scale.setPivotX(event.getX());
	                scale.setPivotY(event.getY());
	                scale.setX(scale.getX() * scaleFactor);
	                scale.setY(scale.getY() * scaleFactor);
	            }
	        });
	        scrollPane.setPannable(true);

	  
	    	//--------------------------------------------------------------------

		StackPane gridPane = new StackPane(scrollPane);

		root.setCenter(gridPane);

		heroInfo = makeHeroInfo(Game.currentHero);
		VBox icons = new VBox();

		String[] paths = new String[5];
		paths[0] = "/Resources/AttackIcon.png";
		paths[1] = "/Resources/Heal.png";
		paths[2] = "/Resources/SpecialAction.png";
		paths[3] = "/Resources/change.png";
		paths[4] = "/Resources/ends.png";
		String[] labels = new String[5];
		labels[0] = "F";
		labels[1] = "C";
		labels[2] = "E";
		labels[3] = "X";
		labels[4] = "Q";

		root.setRight(heroInfo);

		for (int i = 0; i < 5; i++) {
			Label letter = new Label(labels[i]);
			Image image = new Image(paths[i]);
			Button button = new Button();
			images[i]=image;
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(90); // set the width of the image
			imageView.setFitHeight(90); // set the height of the image
			button.setGraphic(imageView);
			button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

			button.setOnMouseClicked(ex -> {

				try {
					switch (letter.getText()) {
					case "F":
						Game.currentHero.attack();
						sounds("balabizo");
						break;
					case "C":
						Game.currentHero.cure();
						ask();
						sounds("balabizo");
						break;
					case "E":
						Game.currentHero.useSpecial();
						sounds("balabizo");
						break;
					case "X":
						switchHero();
						sounds("balabizo");
						break;
					case "Q":
						Game.endTurn();
						sounds("balabizo");
						break;

					default:
						break;
					}

				} catch (NotEnoughActionsException e) {
					sounds("balabizo");
				} catch (InvalidTargetException e) {
					sounds("balabizo");
				} catch (NoAvailableResourcesException e) {
					sounds("balabizo");
				}

				updateMap();
			});
			icons.setSpacing(10);
			icons.setPadding(new Insets(0, 15, 0, 15));
			icons.getChildren().addAll(letter, button);
			icons.setAlignment(Pos.CENTER);

		}
		root.setLeft(icons);

		grid.setBackground(new Background(backgroundImg));
	
		icons.setBackground(new Background(botBackgroundImg));
		heroInfo.setBackground(new Background(botBackgroundImg));
		game = gameView;
		keyActions(game);
	}

	public static void sounds(String s) {
		String s1 = "src/Resources/" + s + ".mp3";
		Media h1 = new Media(Paths.get(s1).toUri().toString());
		MediaPlayer soundEffect = new MediaPlayer(h1);
		soundEffect.play();
		soundEffect.setOnEndOfMedia(new Runnable() {

			public void run() {

				soundEffect.dispose();
			}
		});
	}

	public static void updateMap() {
		
		if(Game.checkGameOver())
		{
			Intro.count++;
			Home.BACKGROUND_MUSIC.stop();
			if(Game.checkWin())
				try {
					Main.window.setScene(new Intro().getIntroWindow());
					Main.window.setFullScreen(true);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else
				try {
					Main.window.setScene(new Intro().getIntroWindow());
					Main.window.setFullScreen(true);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
		}
		
	//	root.getChildren().clear();

		GridPane grid = makeGrid();
		//--------------------------------------------------------------------
		ScrollPane scrollPane = new ScrollPane(grid);
		scrollPane.setBackground(new Background(backgroundImg));

	        scrollPane.setFitToHeight(true);
	        scrollPane.setFitToWidth(true);

	        // Create a Scale object for the transformation
	        Scale scale = new Scale(1, 1);
	        grid.getTransforms().add(scale);

	        // Add a scroll event handler for zooming
	        scrollPane.setOnScroll(new EventHandler<ScrollEvent>() {
	            @Override
	            public void handle(ScrollEvent event) {
	                event.consume();

	                double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA : 1 / SCALE_DELTA;

	                // Apply scaling limits
	                double currentScale = scale.getX();
	                if (currentScale * scaleFactor < MIN_SCALE || currentScale * scaleFactor > MAX_SCALE) {
	                    return;
	                }

	                // Zoom in/out by scaling the GridPane
	                scale.setPivotX(event.getX());
	                scale.setPivotY(event.getY());
	                scale.setX(scale.getX() * scaleFactor);
	                scale.setY(scale.getY() * scaleFactor);
	            }
	        });
	        scrollPane.setPannable(true);

	   
	
	    	//--------------------------------------------------------------------

		StackPane gridPane = new StackPane(scrollPane);
		root.setCenter(gridPane);

		heroInfo = makeHeroInfo(Game.currentHero);
		VBox icons = new VBox();

		String[] paths = new String[5];
		paths[0] = "/Resources/AttackIcon.png";
		paths[1] = "/Resources/Heal.png";
		paths[2] = "/Resources/SpecialAction.png";
		paths[3] = "/Resources/change.png";
		paths[4] = "/Resources/ends.png";
		String[] labels = new String[5];
		labels[0] = "F";
		labels[1] = "C";
		labels[2] = "E";
		labels[3] = "X";
		labels[4] = "Q";

		root.setRight(heroInfo);

		for (int i = 0; i < 5; i++) {
			Label letter = new Label(labels[i]);
			Button button = new Button();
			ImageView imageView = new ImageView(images[i]);
			imageView.setFitWidth(90); // set the width of the image
			imageView.setFitHeight(90);
			// set the height of the image
			button.setGraphic(imageView);
			button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

			button.setOnMouseClicked(ex -> {

				try {
					switch (letter.getText()) {
					case "F":
						Game.currentHero.attack();
						sounds("balabizo");
						break;
					case "C":
						Game.currentHero.cure();
						ask();
						sounds("balabizo");
						break;
					case "E":
						Game.currentHero.useSpecial();
						sounds("balabizo");
						break;
					case "X":
						switchHero();
						sounds("balabizo");
						break;
					case "Q":
						Game.endTurn();
						sounds("balabizo");
						break;

					default:
						break;
					}

				} catch (NotEnoughActionsException e) {
					sounds("balabizo");
				} catch (InvalidTargetException e) {
					sounds("balabizo");
				} catch (NoAvailableResourcesException e) {
					sounds("balabizo");

				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				updateMap();
			});

			icons.setSpacing(10);
			icons.setPadding(new Insets(0, 15, 0, 15));
			icons.getChildren().addAll(letter, button);
			icons.setAlignment(Pos.CENTER);

		}
		root.setLeft(icons);

		grid.setBackground(new Background(backgroundImg));

		icons.setBackground(new Background(botBackgroundImg));
		heroInfo.setBackground(new Background(botBackgroundImg));

		keyActions(game);

	}



	public static void switchHero() {

		try {
			if (Game.currentHero.getTarget() == null || Game.currentHero.getTarget() instanceof Zombie)
				throw new InvalidTargetException();
		
			Game.currentHero = (Hero) Game.currentHero.getTarget();
			updateMap();
		} catch (InvalidTargetException e) {
			sounds("balabizo");
		}
	}

	public static void keyActions(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent event) {

				try {
					switch (event.getCode()) {
					case UP:
						Game.currentHero.move(Direction.UP);
						System.out.println("up");
						break;
					case W:
						Game.currentHero.move(Direction.UP);
						break;
					case DOWN:
						Game.currentHero.move(Direction.DOWN);
						break;
					case S:
						Game.currentHero.move(Direction.DOWN);
						break;
					case LEFT:
						Game.currentHero.move(Direction.LEFT);
						break;
					case A:
						Game.currentHero.move(Direction.LEFT);
						break;
					case RIGHT:
						Game.currentHero.move(Direction.RIGHT);
						break;
					case D:
						Game.currentHero.move(Direction.RIGHT);
						break;

					case F:
						Game.currentHero.attack();
						sounds("balabizo");
						break;
					case C:
						Game.currentHero.cure();
						ask();
						sounds("balabizo");
						break;
					case E:
						Game.currentHero.useSpecial();
						sounds("balabizo");
						break;
					case X:
						switchHero();
						sounds("balabizo");
						break;
					case Q:
						Game.endTurn();
						sounds("balabizo");
						break;

					default:
						break;

					}
				} catch (NotEnoughActionsException e) {
					sounds("balabizo");
				} catch (MovementException e) {
					if (Hero.out)
						sounds("balabizo");
					else
						sounds("balabizo");

				} catch (InvalidTargetException e) {
					sounds("balabizo");

				} catch (NoAvailableResourcesException e) {
					sounds("balabizo");

				}

				updateMap();
				/*
				 * makeGrid(); makeHeroInfo(Game.currentHero);
				 * 
				 * BorderPane root = new BorderPane(); Scene gameView = new
				 * Scene(root,1000,800); gameView.getStylesheets().add("/Resources/styles.css");
				 * 
				 * GridPane grid = makeGrid(); StackPane gridPane = new StackPane(grid);
				 * root.setCenter(gridPane);
				 * 
				 * VBox heroInfo = makeHeroInfo(Game.currentHero); root.setBottom(heroInfo);
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * Main.window.setTitle("THE LAST OF US"); Main.window.setFullScreen(true);
				 * Main.window.setScene(gameView); Main.window.show();
				 */

			}
		});
	}
	public static VBox makeZombieInfo(Zombie h) {
		VBox zombieInfo = new VBox();
		Font font = new Font("Arial", 16);
		Label name = new Label("Name: " + h.getName());
		Label currentHp = new Label("Current HP: " + h.getCurrentHp());
		Label attackDmg = new Label("Attack Damage: " + h.getAttackDmg());
		String targetName;
		if (h.getTarget() != null)
			targetName = h.getTarget().getName();
		else
			targetName = "None";

		Label target = new Label("Target: " + targetName);

		// set fonts
		name.setFont(font);
		name.setFont(font);
		currentHp.setFont(font);
		attackDmg.setFont(font);
		target.setFont(font);

		String path = Game.getImagePath();
		Image image = new Image(new File(path).toURI().toString());
		ImageView imageView = new ImageView(image);
		int size = 80;
		imageView.setFitWidth(300); // set the width of the image
		imageView.setFitHeight(400); // set the height of the image

		zombieInfo.getChildren().addAll(imageView, name, currentHp, attackDmg, target);

		zombieInfo.setPadding(new Insets(0, 40, 0, 0));
		zombieInfo.setAlignment(Pos.CENTER);

		return zombieInfo;
	}


	public static void clickActions(Button button, int i, int j) {
		//hero info on hover partial code, needs debugging-----------------------------------------
	
		button.setOnMouseEntered(ex->{
			// if(ex.getButton().equals(MouseButton.PRIMARY) && ex.getClickCount() == 2) {
			if (Game.map[i][j] instanceof CharacterCell && Game.map[i][j].isVisible()) {
				if(((CharacterCell)Game.map[i][j]).getCharacter() instanceof Hero)
				{		
					heroInfo =makeHeroInfo((Hero) ((CharacterCell)Game.map[i][j]).getCharacter());
			}

				else if(((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie)
				{
					heroInfo = makeZombieInfo((Zombie) ((CharacterCell)Game.map[i][j]).getCharacter());

				}
				
				
			//}
			 }
			 
		});
		button.setOnMouseExited(ey->{
			heroInfo = makeHeroInfo(Game.currentHero);

			
			
		});
		button.setOnMouseClicked(e_ -> {
			System.out.println("Hello world!");

			
			if (Game.map[i][j] instanceof CharacterCell && Game.map[i][j].isVisible()) {
				// if(((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie)
				// {
				Game.currentHero.setTarget(((CharacterCell) Game.map[i][j]).getCharacter());
				// }
			}

			updateMap();
		});
		
	}
	
	public static void ask()
	{
		String [] questions = {"What is Object oriented programming?","What do you do if you don't want to create an instance of an object?","If an instance variable is declared as protected, who has access to it?","What do you do if you want many objects to exhibit the same behaviour?","Can you make two methods that are exactly the same excpet for the number of its formal parameters?"};
		String [] answer1 = {"Abstraction, Polymorphism, Inheritence, Encapsulation","Make it abstract","The class's children and the ones in the same package","Implement an interface","Yes"};
		String [] answer2 = {"Classes, Objects, Contstructors, Instance Variables","Implement an Interface","Only the classes in it's own package","Re-write the same methods in each class","No"};
		
		ButtonType yesButton = new ButtonType(answer1[prof], ButtonData.OTHER);
		
        ButtonType noButton = new ButtonType(answer2[prof], ButtonData.OTHER);
        int x = (int) Game.r.nextInt(2);
        if(x%2==1)
        {
        	 yesButton = new ButtonType(answer2[prof], ButtonData.YES);
    		
             noButton = new ButtonType(answer1[prof], ButtonData.NO);	
        }
        	Alert alert = new Alert(AlertType.CONFIRMATION, questions[prof], yesButton, noButton);//valueOf("ANSWER CORRECTLY TO KEEP PLAYING")
        

         
        
         alert.initOwner(views.Main.window);
         alert.setTitle("POP QUIZ!");
         
         alert.setHeaderText("Answer Correctly to keep playing!");
         alert.showAndWait();
         
        	  
        	
         prof=(prof+1)%5;
     	if (alert.getResult() == noButton) {
     	    // Lose
     		if(x%2==0)
     		{sounds("balabizo");
     	    Game.heroes.clear();
     	    Game.checkGameOver();}
     	} else {
     	    // Keep playing
     		if(x%2==1)
     		{
     			sounds("balabizo");
         	    Game.heroes.clear();
         	    Game.checkGameOver();
     		}
     	}
	}

	public static GridPane makeGrid() {
		GridPane grid = new GridPane();
		if(!zoombool) {
		for (int i = 0; i < Game.map.length; i++) {

			for (int j = 0; j < Game.map[i].length; j++) {
				boolean empty = false;
				Button button = new Button();
				Image image1=images[5];
				String path = "/Resources/clouds.png";
				if (Game.map[i][j].isVisible())// Game.map[i][j].isVisible()
				{
					if (Game.map[i][j] instanceof CharacterCell) {
						if (((CharacterCell) Game.map[i][j]).getCharacter() == null) {
							empty = true;// path = "/Resources/empty.png";
						} else {
							// button.setText(((CharacterCell)Game.map[i][j]).getCharacter().getName());
							if (((CharacterCell) Game.map[i][j]).getCharacter() instanceof Hero) {
								path = "/Resources/"
										+ ((Hero) ((CharacterCell) Game.map[i][j]).getCharacter()).getPath();
								image1 = new Image(path);
								// set the height of the image
							}else
								image1=images[6];

						}
					} else {
						if (Game.map[i][j] instanceof CollectibleCell) {
							if (((CollectibleCell) Game.map[i][j]).getCollectible() instanceof Vaccine) {
								// button.setText("Vaccine");
								image1=images[8];

							} else {
								// button.setText("Supply");
								image1=images[7];

							}

						} else
							empty = true;// path = "/Resources/empty.png";

					}
				}else {
					image1=images[5];
				}

				// Button Styling
				button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
				// button.setPrefWidth(Double.MAX_VALUE);
				int size = (int) (Home.height * (58.0 / 1440));
				if (!empty) {
					ImageView image2 =new ImageView(image1);
					image2.setFitWidth((int) (Home.height * (58.0 / 1440)) + 10); // set the width of the image
					image2.setFitHeight((int) (Home.height * (58.0 / 1440))); 
					// set the height of the image
					button.setGraphic(image2);
				}

				button.setPrefWidth(size + 30);
				button.setPrefHeight(size);
				button.setPadding(new Insets(1, 5, 0, 5));
				grid.add(button, j, Game.map.length - 1 - i);

				// click test
				if( Game.map[i][j].isVisible())
					clickActions(button, i, j);
			}
			
			grid.setAlignment(Pos.CENTER);

		}

		// Set the column constraints to make the buttons occupy the whole window
		// horizontally
		for (int i = 0; i < Game.map.length; i++) {
			ColumnConstraints col = new ColumnConstraints();
			// col.setPercentWidth(100.0/Game.map.length);
			grid.getColumnConstraints().add(col);
		}

		grid.setHgap(0); // Remove any horizontal gap between buttons
		grid.setVgap(0); // Remove any vertical gap between buttons
		}else {
			int range=zoomval;
			Point p=Game.currentHero.getLocation();
			int x1=max(p.x-range,0),x2=min(p.x+range,14);
			int y1=max(p.y-range,0),y2=min(p.y+range,14);
			int x12=x1+range*2,x21=x2-range*2;
			int y12=y1+range*2,y21=y2-range*2;
			int X1=0,X2=0;
			int Y1=0,Y2=0;
			if(x1==x21) {
				X1=x1;
				X2=x2;
			}else {
				if(!(x21<0)) {
					X1=x21;
					X2=x2;
				}else {
					X1=x1;
					X2=x12;
				}
			}
			if(y1==y21) {
				Y1=y1;
				Y2=y2;
			}else {
				if(!(y21<0)) {
					Y1=y21;
					Y2=y2;
				}else {
					Y1=y1;
					Y2=y12;
					
				}
			}
			for (int i = X1; i <=X2; i++) {

				for (int j = Y1; j <= Y2; j++) {
					boolean empty = false;
					Button button = new Button();
					Image image1=images[5];
					String path = "/Resources/clouds.png";
					if (Game.map[i][j].isVisible())// Game.map[i][j].isVisible()
					{
						if (Game.map[i][j] instanceof CharacterCell) {
							if (((CharacterCell) Game.map[i][j]).getCharacter() == null) {
								empty = true;// path = "/Resources/empty.png";
							} else {
								// button.setText(((CharacterCell)Game.map[i][j]).getCharacter().getName());
								if (((CharacterCell) Game.map[i][j]).getCharacter() instanceof Hero) {
									path = "/Resources/"
											+ ((Hero) ((CharacterCell) Game.map[i][j]).getCharacter()).getPath();
									image1 = new Image(path);
									// set the height of the image
								}else
									image1=images[6];

							}
						} else {
							if (Game.map[i][j] instanceof CollectibleCell) {
								if (((CollectibleCell) Game.map[i][j]).getCollectible() instanceof Vaccine) {
									// button.setText("Vaccine");
									image1=images[8];

								} else {
									// button.setText("Supply");
									image1=images[7];

								}

							} else
								empty = true;// path = "/Resources/empty.png";

						}
					}else {
						image1=images[5];
					}

					// Button Styling
					button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
					// button.setPrefWidth(Double.MAX_VALUE);
					int size = (int) (Home.height * (58.0*(15.0/(range*2+1)) / 1440));
					if (!empty) {
						ImageView image2 =new ImageView(image1);
						image2.setFitWidth((int) (Home.height * (58.0*(15.0/(range*2+1))/ 1440)) + 10); // set the width of the image
						image2.setFitHeight((int) (Home.height * (58.0*(15.0/(range*2+1)) / 1440))); 
						// set the height of the image
						button.setGraphic(image2);
					}

					button.setPrefWidth(size );
					button.setPrefHeight(size);
					button.setPadding(new Insets(1, 5, 0, 5));
					grid.add(button, j, 14- i);

					// click test
					if( Game.map[i][j].isVisible())
						clickActions(button, i, j);
				}
				grid.setAlignment(Pos.CENTER);

			}

			// Set the column constraints to make the buttons occupy the whole window
			// horizontally
			for (int i = 0; i <=range*2; i++) {
				ColumnConstraints col = new ColumnConstraints();
				// col.setPercentWidth(100.0/Game.map.length);
				grid.getColumnConstraints().add(col);
			}

			grid.setHgap(0); // Remove any horizontal gap between buttons
			grid.setVgap(0); // Remove any vertical gap between buttons
		}
		return grid;
	}
	
	public static int max(int x,int y) {
		if(x>y)
			return x;
		else
			return y;
	}
	public static int min(int x,int y) {
		if(x>y)
			return y;
		else
			return x;
	}

	public static VBox makeHeroInfo(Hero h) {
		heroInfo = new VBox();
		Font font = new Font("Arial", 16);
		Label name = new Label("Name: " + h.getName());
		Label type = new Label("Type: " + getType(h));
		Label currentHp = new Label("Current HP: " + h.getCurrentHp());
		Label attackDmg = new Label("Attack Damage: " + h.getAttackDmg());
		Label supplies = new Label("Supplies: " + h.getSupplyInventory().size());
		Label vaccines = new Label("Vaccines: " + h.getVaccineInventory().size());
		Label actions = new Label("Actions: " + h.getActionsAvailable());
		Label maxactions = new Label("Max Actions: " + h.getMaxActions());
		Button menu= createButton("Menu");
		menu.setOnMouseClicked(e->{
			Scene homeScreenScene = new Scene(new Home().createContent(), 500, 500);
	         Main.window.setScene(homeScreenScene);
	         Main.window.setFullScreen(true);
		});
		Button zoom= createButton("Zoom5x");
		zoom.setOnMouseClicked(e->{
			if(zoomval==1||zoomval==0) {
				zoombool=true;
				zoomval=2;
			}else {
				zoombool=false;
				zoomval=0;
			}
			
			
			updateMap();
		});
		Button zoom1= createButton("Zoom3x");
		zoom1.setOnMouseClicked(e->{
			if(zoomval==2||zoomval==0) {
				zoombool=true;
				zoomval=1;
			}else {
				zoombool=false;
				zoomval=0;
			}
			updateMap();
		});
		String targetName;
		if (h.getTarget() != null)
			targetName = h.getTarget().getName();
		else
			targetName = "None";

		Label target = new Label("Target: " + targetName);

		// set fonts
		name.setFont(font);
		type.setFont(font);
		name.setFont(font);
		type.setFont(font);
		currentHp.setFont(font);
		attackDmg.setFont(font);
		supplies.setFont(font);
		vaccines.setFont(font);
		actions.setFont(font);
		target.setFont(font);
		maxactions.setFont(font);

		String path = Game.getImagePath();
		Image image = new Image(new File(path).toURI().toString());
		ImageView imageView = new ImageView(image);
		int size = 80;
		imageView.setFitWidth(300); // set the width of the image
		imageView.setFitHeight(400); // set the height of the image

		heroInfo.getChildren().addAll(imageView, name, type, currentHp, attackDmg, supplies, vaccines,maxactions, actions, target,menu,zoom,zoom1);

		heroInfo.setPadding(new Insets(0, 40, 0, 0));
		heroInfo.setAlignment(Pos.CENTER);

		return heroInfo;
	}

	public static Button createButton(String text) {
        Button button = new Button(text);
    
        button.setStyle("-fx-pref-width: 180px; " +
                "-fx-pref-height: 60px; " +
                "-fx-background-color: transparent; " +
                "-fx-background-radius: 50%; " +
                "-fx-box-shadow: #B00B1E 0 10px 20px -10px; " +
                "-fx-box-sizing: border-box; " +
                "-fx-color: #FFFFFF; " +
                "-fx-cursor: pointer; " +
                "-fx-font-family: 'Creepster', sans-serif; "+
                "-fx-font-size: 40px; " +
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
	
	public static String getType(Hero h) {
		if (h instanceof Fighter) {
			return "Fighter";
		}
		if (h instanceof Medic)
			return "Medic";
		else
			return "Explorer";
	}

}