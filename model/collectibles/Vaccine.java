package model.collectibles;

import java.awt.Point;

import engine.Game;
import model.characters.Character;
import model.characters.Hero;
import model.world.Cell;
import model.world.CharacterCell;
import views.scenes.GameView;

public class Vaccine implements Collectible {
	private int x;
	private int y;
	
	@Override
	public void pickUp(Hero h) {
		h.getVaccineInventory().add(this);
		GameView.sounds("pickUp");

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public void use(Hero h) {
		h.getVaccineInventory().remove(this);
		Point p = h.getTarget().getLocation();
		Cell cell = Game.map[p.x][p.y];
		Game.zombies.remove(h.getTarget());
		Hero tba = Game.availableHeroes.get((int) (Math.random() * Game.availableHeroes.size()));
		Game.availableHeroes.remove(tba);
		Game.heroes.add(tba);
		((CharacterCell) cell).setCharacter(tba);
		tba.setLocation(p);
		Game.adjustVisibility(tba);
	}

}
