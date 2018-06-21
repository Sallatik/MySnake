package sallat;

import java.util.*;
import java.awt.*;
public abstract class Entity {
	
	private Color color;
	
	public void setColor(Color color){
		this.color = color;
	}
	
	private Collection<Matrix.Cell> cells;
		
	private Room game;
	
	public Room getGame(){
		return game;
	}
	
	public Collection<Matrix.Cell> getCells() {
		return cells;
	}

	public boolean contains(Matrix.Cell cell) {
		return cells.contains(cell);
	}
	
	public void paint(Graphics g){
		g.setColor(color);
		for (Matrix.Cell cell : cells)
			cell.paint(g);
	}
	
	public Entity(Room game, Collection<Matrix.Cell> cells) {
		this.cells = cells;
		this.game = game;
		game.getEntities().add(this);
	}
}