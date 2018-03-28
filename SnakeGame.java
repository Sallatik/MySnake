import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
	
	public static final String VERSION = "1.2.0";
	
	public static final int TICK = 100;
	
	private Matrix matrix;	 
	private Set<Entity> entities;
	private Snake snake;

	private JFrame frame;
				
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

		snake = new Snake(this, 5,5,1,Direction.NORTH);
		new Food(this);
		frame.setVisible(true);

	}
	
	public void gameCycle() {
		while (snake.getIsAlive()) {

			snake.update();
			frame.repaint();
			try { Thread.sleep(TICK); } catch (Exception ex) {}
		}

	}

	private SnakeGame() {	// a private constructor
		
		matrix = new Matrix(this, 20,10);	
		entities = new HashSet<Entity>();

		frame = new JFrame("Snake alpha " + VERSION);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(new Listener());
		
		frame.getContentPane().add( BorderLayout.CENTER, matrix );
		
		frame.setSize(600,300);
		frame.setLocation(600,300);
		
		
		
	}
	
	public static void main(String [] args) {	
		SnakeGame game = new SnakeGame();
		game.setup();
		game.gameCycle();	
	}
	
	private class Listener extends KeyAdapter {
		
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