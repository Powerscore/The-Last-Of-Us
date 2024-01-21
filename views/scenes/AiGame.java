package views.scenes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.media.*;
import model.characters.Direction;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Vaccine;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;
import views.Main;
import java.util.*;

public class AiGame {
	
	private static Scene game;
	public static BorderPane root;
	public static Image images[]=new Image[10];

	public Scene getGame() {
		return game;
	}

	public AiGame() {
		// TODO Auto-generated method stub
		
				//test hero
				
				try {
					Game.loadHeroes("Heroes.csv");
				} catch (IOException e) {
					System.out.println("File not found");
				}
				Game.currentHero=Game.availableHeroes.get(3);
				Hero h = Game.currentHero;//will choose character later instead of 0 
				Game.startGame(h);
				//h.setActionsAvailable(100);

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
				Scene gameView = new Scene(root,1000,800);
				gameView.getStylesheets().add("/Resources/styles.css");
				
			
				
				
				GridPane grid = makeGrid();
				StackPane gridPane = new StackPane(grid);


				root.setCenter(gridPane);
				
				VBox heroInfo = makeHeroInfo(Game.currentHero);
				VBox icons = new VBox();
				
				String[] paths = new String[3];
				paths[0]="/Resources/chip.png";
				paths[1]="/Resources/Ai.png";
				paths[2]="/Resources/ai1.png";
		        root.setRight(heroInfo);

		        for(int i=0 ; i<3 ; i++)
				{	
				 Image image = new Image(paths[i]);
				 Button button = new Button();
		         ImageView imageView = new ImageView(image);
		         imageView.setFitWidth(90); // set the width of the image
		         imageView.setFitHeight(90); // set the height of the image
		         button.setGraphic(imageView);
		         button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

		         button.setOnMouseClicked(ex->{
		        	 play();
		        	 updateMap();
		         });
		         icons.setSpacing(10);
		         icons.setPadding(new Insets(0,15,0,15));
		         icons.getChildren().addAll(button);
		         icons.setAlignment(Pos.CENTER);

				}
				root.setLeft(icons);

				
				
				
				BackgroundImage backgroundImg = new BackgroundImage(new Image("/Resources/black.jpg", 0, 0, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
				BackgroundImage botBackgroundImg = new BackgroundImage(new Image("/Resources/black.jpg", 0, 0, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
				gridPane.setBackground(new Background(backgroundImg));
			    icons.setBackground(new Background(botBackgroundImg));
			    heroInfo.setBackground(new Background(botBackgroundImg));
			    game=gameView;
			    keyActions(game);
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
	
	public static void updateMap()
	{
		
	
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
		
		
		GridPane grid = makeGrid();
		StackPane gridPane = new StackPane(grid);


		root.setCenter(gridPane);
		
		VBox heroInfo = makeHeroInfo(Game.currentHero);
		VBox icons = new VBox();
		
		String[] paths = new String[3];
		paths[0]="/Resources/chip.png";
		paths[1]="/Resources/Ai.png";
		paths[2]="/Resources/ai1.png";
        root.setRight(heroInfo);

        for(int i=0 ; i<3 ; i++)
		{	
		 Image image = new Image(paths[i]);
		 Button button = new Button();
         ImageView imageView = new ImageView(image);
         imageView.setFitWidth(90); // set the width of the image
         imageView.setFitHeight(90); // set the height of the image
         button.setGraphic(imageView);
         button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

         button.setOnMouseClicked(ex->{
        	 play();
        	 updateMap();
         });
         icons.setSpacing(10);
         icons.setPadding(new Insets(0,15,0,15));
         icons.getChildren().addAll(button);
         icons.setAlignment(Pos.CENTER);

		}
		root.setLeft(icons);

		
		
		
		BackgroundImage backgroundImg = new BackgroundImage(new Image("/Resources/black.jpg", 0, 0, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
		BackgroundImage botBackgroundImg = new BackgroundImage(new Image("/Resources/black.jpg", 0, 0, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
		gridPane.setBackground(new Background(backgroundImg));
	    icons.setBackground(new Background(botBackgroundImg));
	    heroInfo.setBackground(new Background(botBackgroundImg));
	    
	    keyActions(game);
	    
	}
	
	
	public static void clickActions() {
		
	}
	
	public static void switchHero()
	{
		
		try {
			if(Game.currentHero.getTarget()==null||Game.currentHero.getTarget() instanceof Zombie)
				throw new InvalidTargetException();
			Game.currentHero = (Hero) Game.currentHero.getTarget();
			updateMap();
		} catch (InvalidTargetException e) {
			sounds("target");
		}
	}
	
	public static void keyActions(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			
			
			public void handle(KeyEvent event)
			{
				
				switch(event.getCode())
				{
				case P: play(); break;
				
				
				default:
					break;
				

				
				
				} 
				
				updateMap();
			/*	makeGrid();
				makeHeroInfo(Game.currentHero);
				
				BorderPane root = new BorderPane();
				Scene gameView = new Scene(root,1000,800);
				gameView.getStylesheets().add("/Resources/styles.css");
				
				GridPane grid = makeGrid();
				StackPane gridPane = new StackPane(grid);
				root.setCenter(gridPane);
				
				VBox heroInfo = makeHeroInfo(Game.currentHero);
				root.setBottom(heroInfo);
				
				
				
							
			
				
				Main.window.setTitle("THE LAST OF US");
				Main.window.setFullScreen(true);
				Main.window.setScene(gameView);
				Main.window.show();*/
				
			}
		});
	}
	public static void clickActions(Button button, int i , int j)
	{
		 
			
	
	
	}
	
	public static GridPane makeGrid() {
		GridPane grid = new GridPane();
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
				button.setPrefHeight(size+10);
				button.setPadding(new Insets(1, 5, 0, 5));
				grid.add(button, j, Game.map.length - 1 - i);

				// click test
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

		return grid;
	}
	
	public static void play() {
		if(Game.currentHero.getActionsAvailable()==0) {
			try {
				Game.endTurn();
				GameView.sounds("endTurn");
			} catch (NotEnoughActionsException | InvalidTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		boolean moved=false;
		Vaccine v=nearestvaccine();
		if(v!=null) {
			int []start= {Game.currentHero.getLocation().x,Game.currentHero.getLocation().y};
			int []end={v.getX(),v.getY()};
			List<int[]> shortestPath = shortestPath(start,end , Game.map);
			int[]d1=shortestPath.get(0);
			int[]d2=shortestPath.get(1);
			int[]d= {d2[0]-d1[0],d2[1]-d1[1]};
			try {
				if(d[0]!=0) {
					if(d[0]==1)
						Game.currentHero.move(Direction.UP);
					else {
						Game.currentHero.move(Direction.DOWN);
					}
				}else {
					if(d[1]==1)
						Game.currentHero.move(Direction.RIGHT);
					else {
						Game.currentHero.move(Direction.LEFT);
					}
				}
				moved=true;
			} catch (MovementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotEnoughActionsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
		}else {
			Zombie z=nearestzombie();
			if(z!=null) {
				if(Math.abs(Game.currentHero.getLocation().x-z.getX())<=1&&Math.abs(Game.currentHero.getLocation().y-z.getY())<=1) {
					Game.currentHero.setTarget(z);
					try {
						Game.currentHero.cure();
						GameView.sounds("cure");
					} catch (NoAvailableResourcesException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvalidTargetException e) {
						// TODO Auto-generated catch block
						
						e.printStackTrace();
					} catch (NotEnoughActionsException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
				int j=0;
				for(int i=Game.zombies.indexOf(z);j<Game.zombies.size();i=(i+1)%Game.zombies.size(),j++) {
				int []start= {Game.currentHero.getLocation().x,Game.currentHero.getLocation().y};
				int []end={Game.zombies.get(i).getX(),Game.zombies.get(i).getY()};
				List<int[]> shortestPath = shortestPath1(start,end , Game.map);
				int[]d1=shortestPath.get(0);
				int[]d2=shortestPath.get(1);
				int[]d= {d2[0]-d1[0],d2[1]-d1[1]};
				try {
					if(d[0]!=0) {
						if(d[0]==1) {
							Game.currentHero.move(Direction.UP);
						}else {
							Game.currentHero.move(Direction.DOWN);
						}
					}else {
						if(d[1]==1)
							Game.currentHero.move(Direction.RIGHT);
						else {
							Game.currentHero.move(Direction.LEFT);
						}
					}
					moved=true;
					break;
				} catch (MovementException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotEnoughActionsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				}
			}
		}
	}
	public static Vaccine nearestvaccine() {
		Vaccine v=null;
		int x=100,y=100;
		for(int i=0;i<Game.vaccines.size();i++) {
			if(Math.abs(Game.currentHero.getLocation().x-Game.vaccines.get(i).getX())+Math.abs(Game.currentHero.getLocation().y-Game.vaccines.get(i).getY())<Math.abs(Game.currentHero.getLocation().x-x)+Math.abs(Game.currentHero.getLocation().y-y)) {
				v=Game.vaccines.get(i);
				x=Game.vaccines.get(i).getX();
				y=Game.vaccines.get(i).getY();
			}
		}
		
		return v;
	}
	
	public static Zombie nearestzombie() {
		Zombie z=null;
		int x=100,y=100;
		for(int i=0;i<Game.zombies.size();i++) {
			if(Math.abs(Game.currentHero.getLocation().x-Game.zombies.get(i).getX())+Math.abs(Game.currentHero.getLocation().y-Game.zombies.get(i).getY())<Math.abs(Game.currentHero.getLocation().x-x)+Math.abs(Game.currentHero.getLocation().y-y)) {
				z=Game.zombies.get(i);
				x=Game.zombies.get(i).getX();
				y=Game.zombies.get(i).getY();
			}
		}
		
		return z;
	}
	
	public static VBox makeHeroInfo(Hero h)
	{
		VBox heroInfo = new VBox();
		Font font = new Font("Arial",16);
		Label name = new Label("Name: " + h.getName());
		Label type = new Label("Type: " + getType(h));
		Label currentHp = new Label("Current HP: " + h.getCurrentHp());
		Label attackDmg = new Label("Attack Damage: " + h.getAttackDmg());
		Label supplies = new Label("Supplies: " + h.getSupplyInventory().size());
		Label vaccines = new Label("Vaccines: " + h.getVaccineInventory().size());
		Label actions = new Label("Actions: " + h.getActionsAvailable());
		String targetName;
		if(h.getTarget()!=null)
			 targetName = h.getTarget().getName();
		else
			targetName = "None";
		
		Label target = new Label("Target: " + targetName);

		
		//set fonts
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
		
		
		String path = Game.getImagePath();
        Image image = new Image(new File(path).toURI().toString());
        ImageView imageView = new ImageView(image);
        int size = 80;
        imageView.setFitWidth(300); // set the width of the image
        imageView.setFitHeight(400); // set the height of the image

		
		heroInfo.getChildren().addAll(imageView,name,type,currentHp,attackDmg,supplies,vaccines,actions,target);
		
		heroInfo.setPadding(new Insets(0, 40, 0, 0));
		heroInfo.setAlignment(Pos.CENTER);
		
		return heroInfo;
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
	
	public static List<int[]> shortestPath1(int[] start, int[] end, Cell[][] grid) {
        Queue<int[]> queue = new LinkedList<>();
        Map<String, String> parentMap = new HashMap<>();
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        
        queue.offer(start);
        parentMap.put(start[0] + "," + start[1], null);
        
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            
            if (Arrays.equals(current, end)) {
                return reconstructPath(parentMap, start, end);
            }
            
            for (int[] direction : directions) {
                int dx = direction[0];
                int dy = direction[1];
                int newX = current[0] + dx;
                int newY = current[1] + dy;
                
                if (newX >= 0 && newX < grid.length && newY >= 0 && newY < grid[0].length
                        && possible(newX,newY,end[0],end[1]) && !parentMap.containsKey(newX + "," + newY)) {
                    queue.offer(new int[]{newX, newY});
                    parentMap.put(newX + "," + newY, current[0] + "," + current[1]);
                }
            }
        }
        
        return new ArrayList<>(); // No path found
    }
	
	public static boolean possible(int x, int y,int i,int j) {
		if(x==i&&y==j)
			return true;
		if((Game.map[x][y]instanceof CharacterCell&&((CharacterCell)Game.map[x][y]).getCharacter()!=null)||Game.map[x][y]instanceof TrapCell)
			return false;
		return true;
	}
	public static List<int[]> shortestPath(int[] start, int[] end, Cell[][] grid) {
        Queue<int[]> queue = new LinkedList<>();
        Map<String, String> parentMap = new HashMap<>();
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        
        queue.offer(start);
        parentMap.put(start[0] + "," + start[1], null);
        
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            
            if (Arrays.equals(current, end)) {
                return reconstructPath(parentMap, start, end);
            }
            
            for (int[] direction : directions) {
                int dx = direction[0];
                int dy = direction[1];
                int newX = current[0] + dx;
                int newY = current[1] + dy;
                
                if (newX >= 0 && newX < grid.length && newY >= 0 && newY < grid[0].length
                        && possible(newX,newY) && !parentMap.containsKey(newX + "," + newY)) {
                    queue.offer(new int[]{newX, newY});
                    parentMap.put(newX + "," + newY, current[0] + "," + current[1]);
                }
            }
        }
        
        return new ArrayList<>(); // No path found
    }
	
	public static boolean possible(int x, int y) {
		if((Game.map[x][y]instanceof CharacterCell&&((CharacterCell)Game.map[x][y]).getCharacter()!=null)||Game.map[x][y]instanceof TrapCell)
			return false;
		return true;
	}
    
    private static List<int[]> reconstructPath(Map<String, String> parentMap, int[] start, int[] end) {
        List<int[]> path = new ArrayList<>();
        int[] current = end;
        
        while (current != null) {
            path.add(current);
            String parentCoordinates = parentMap.get(current[0] + "," + current[1]);
            
            if (parentCoordinates != null) {
                String[] coordinates = parentCoordinates.split(",");
                int parentX = Integer.parseInt(coordinates[0]);
                int parentY = Integer.parseInt(coordinates[1]);
                current = new int[]{parentX, parentY};
            } else {
                current = null;
            }
        }
        
        Collections.reverse(path);
        return path;
    }
    
}
	   