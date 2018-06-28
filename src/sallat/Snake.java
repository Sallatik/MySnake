package sallat;

import java.awt.*;
import java.util.concurrent.*;
class Snake extends Entity {
	
	public static final Color DEFAULT_COLOR = Color.red;
	
	private boolean isAlive;
	private Direction direction;
	private Direction directionBuff;
	private ConcurrentLinkedDeque<Matrix.Cell> cellsQueue;
	
	public boolean getIsAlive() {
		return isAlive;
	}
	
	public void setDirection(Direction direction) { 
		if (this.direction == null || (direction.isVertical() != this.direction.isVertical())) {
			this.directionBuff = direction;
		}
			
	}
	
	public void update() {
		direction = directionBuff;
		move();
	}
	
	private void move () {
		Matrix.Cell newHead = cellsQueue.peekLast().neighbour(direction);
		Entity entityToCollide = getGame().checkCollision(newHead);
		
		boolean isHungry = true;
		if (entityToCollide instanceof Food) {
			eat( (Food) entityToCollide);
			isHungry = false;
		} else if(entityToCollide instanceof Entity)
			die();
		
		if (isAlive) {
			cellsQueue.addLast(newHead);
			if (isHungry) {
				cellsQueue.removeFirst();
				((Matrix.AnimatedCell) cellsQueue.peekFirst()).startShrinking();
			}
		}
	}
	
	private void eat(Food food) { // REFACTOR!!!!!!!!!!
		food.die();
	}
	
	private void die() {
		isAlive = false;
	}
		
	public Snake( Room game, int x, int y, int length, Direction direction) { // refactor !!! 
		super(game, new ConcurrentLinkedDeque<>());
		cellsQueue = (ConcurrentLinkedDeque<Matrix.Cell>) getCells();
		setColor(DEFAULT_COLOR);
		
		setDirection(direction);
		isAlive = true;
		
		cellsQueue.add( getGame().getMatrix().new AnimatedCell(x,y,direction));
		for (int i = 1; i < length; i++)
			cellsQueue.addLast(cellsQueue.peekLast().neighbour(direction));
		
	}
}
		
		
		