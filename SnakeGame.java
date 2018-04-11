import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import gui.*;

enum Direction {    
	
	
	NORTH(true,false),
	SOUTH(true,true),
	
	WEST(false,false),
	EAST(false,true);
	
	
	private boolean sign;
	public boolean sign(){
		return sign;
	}
	
	private boolean isVertical;
	public boolean isVertical(){
		return isVertical; 
	}
	private Direction(boolean isVertical, boolean sign) {
		this.isVertical = isVertical;
		this.sign = sign;
	}
}

class SnakeGame {
	
	public static final String VERSION = "2.0";
	public static final int FPS = 25;
	public static final int TICK = 100;
	
	private Matrix matrix;	 
	private Set<Entity> entities;
	private Snake snake;
	
				
	public Matrix getMatrix() {
		return matrix;
	}
	public Snake getSnake() { // DELETE!!! 
		return snake;
	}
	public Set<Entity> getEntities() {
		return entities;
	}
	public Entity checkCollision(Matrix.Cell cell) {
		
		for (Entity entity : entities) {
			if ( entity.contains(cell) ) {
				return entity;
			}
		}
		 
		return null;
	}

	public void setup() {

		snake = new Snake(this, 5,5,1,Direction.NORTH);
		new Food(this);

	}
	
	public void gameCycle() {
		while (snake.getIsAlive()) {

			snake.update();
			try { Thread.sleep(TICK); } catch (Exception ex) {}
		}

	}

	private SnakeGame() {	// a private constructor
		
		matrix = new Matrix(this, 20,20);	
		matrix.addKeyListener(new Listener());
		entities = new HashSet<Entity>();
		
		GameWindow window = new GameWindow(600,600,"Snake alpha ".concat(VERSION));
		window.setUpdater(new Updater(FPS));
		window.setJPanel(matrix);
		
	}
	
	public static void main(String [] args) {	
		SnakeGame game = new SnakeGame();
		game.setup();
		game.gameCycle();	
	}
	private class Listener extends KeyAdapter { // !!! Matrix should not have control over the snake
		
		public void keyPressed(KeyEvent k) {
			int key = k.getKeyCode();
			Direction direction;
			if ( key == KeyEvent.VK_UP) {
				direction = Direction.NORTH;
			} else if (key == KeyEvent.VK_DOWN) {
				direction = Direction.SOUTH;
			} else if (key == KeyEvent.VK_RIGHT) {
				direction = Direction.EAST;
			} else if (key == KeyEvent.VK_LEFT) {
				direction = Direction.WEST;
			} else return;
			
			snake.setDirection(direction);
		}
		
	}
}