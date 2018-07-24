package sallat;

import java.awt.Color;
import java.util.*;
public class Food extends Entity {
	
	public static final Color DEFAULT_COLOR = Color.blue;
	
	private Matrix.Cell randomPlace() { 

		int maxX = getGame().getMatrix().getNumOfColumns();
		int maxY = getGame().getMatrix().getNumOfRows();
		
		Matrix.Cell newPlace = null;
		Random rand = new Random(); 			//! Game will STUCK if no free place
		do {
			int x = rand.nextInt(maxX);
			int y = rand.nextInt(maxY);

			newPlace = getGame().getMatrix().new Cell(x,y);

		} while (getGame().checkCollision(newPlace) != null);
		
		return newPlace;
	}
	private void respawn() {
		
		Matrix.Cell newPlace = randomPlace();
		
		getCells().add(newPlace);
		List listCells = (ArrayList<Matrix.Cell>) getCells();
		listCells.remove(0);
	}
	
	public void die() {
		respawn(); 
	}
	
	public Food(Room game) {
		super(game,new ArrayList<>());
		setColor(DEFAULT_COLOR);
		Matrix.Cell newPlace = randomPlace();
		getCells().add(newPlace);
	}
}	