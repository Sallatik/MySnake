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

class Room {
	
	public static final String VERSION = "2.1";
	public static final int FPS = 100;
	public static final int TICK = 100;
	
	private Matrix matrix;	 
	private Set<Entity> entities;
	private Snake snake;
	
				
	public Matrix getMatrix() {
		return matrix;
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

		snake = new Snake(this, 5,5,2,Direction.NORTH);
		new Food(this);

	}
	
	public void gameCycle() {
		while (snake.getIsAlive()) {

			snake.update();
			try { Thread.sleep(TICK); } catch (Exception ex) {}
		}

	}

	private Room() {	// a private constructor
		
		matrix = new Matrix(this, 20,20);	
		
		Map<Integer,Direction> snakeBindings = new HashMap<Integer,Direction>();
		snakeBindings.put(KeyEvent.VK_UP,Direction.NORTH);
		snakeBindings.put(KeyEvent.VK_DOWN,Direction.SOUTH);
		snakeBindings.put(KeyEvent.VK_RIGHT,Direction.EAST);
		snakeBindings.put(KeyEvent.VK_LEFT,Direction.WEST);
		
		matrix.addKeyListener(new Listener(snakeBindings));
		entities = new HashSet<Entity>();
		
		GameWindow window = new GameWindow(600,600,"Snake alpha ".concat(VERSION));
		window.setUpdater(new Updater(FPS));
		window.setJPanel(matrix);
		
	}
	
	public static void main(String [] args) {	
		Room game = new Room();
		game.setup();
		game.gameCycle();	
	}
	
	private class Listener extends KeyAdapter { // !!! Matrix should not have control over the snake
		private Map<Integer,Direction> snakeBindings;
		
		public void keyPressed(KeyEvent k) {
			int key = k.getKeyCode();
			Direction direction;
			if ((direction = snakeBindings.get(key)) != null)
				snake.setDirection(direction);
		}
		
		public Listener(Map<Integer,Direction> snakeBindings) {
			this.snakeBindings = snakeBindings;
		}
		
		
	}
}