import java.awt.*;
import javax.swing.*;
class Matrix extends JPanel{ 
		
	SnakeGame game;
	
	public static final Color BACKGROUND_DEFAULT = 	Color.LIGHT_GRAY;	
	public static final Color COLOR_DEFAULT = Color.yellow;

	private int numOfRows;
	private int numOfColumns;
	
	private int cellWidth;
	private int cellHeight;

	private Color background;
	private Color color;
	
	public int getNumOfRows() { // rename or delete !!!
		return numOfRows;
	}

	public int getNumOfColumns() { // rename or delete !!!
		return numOfColumns;
	}

	public void setBackground(Color background) {
		this.background = background;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
					// setters (no getters yet)

	public Matrix ( SnakeGame game, int width, int height ) {	// constructor
		this.game = game;
		
		this.numOfColumns = width;
		this.numOfRows = height;
		
		cellWidth = 10;
		cellHeight = 10;

		background = BACKGROUND_DEFAULT;
		color = COLOR_DEFAULT;
	}

	public void paintComponent ( Graphics g ) {
		
		int width = getWidth();
		int height = getHeight();
		
		cellWidth = width / this.numOfColumns;
		cellHeight = height / this.numOfRows;

		g.setColor(background);
		g.fillRect(0, 0, width, height);	// filling the background
		
		g.setColor(color);
		for (Entity entity : game.getEntities()) {	// printing all the entities
			entity.paint(g);
		}
	}
	
	public class Cell {
		
		private int x;
		private int y;

		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
		public void setX(int x) {
			if (x >= numOfColumns) {
				this.x = x % numOfColumns; 
			} else if (x < 0){ 
				this.x = x + numOfColumns;
			} else {
				this.x = x;
			}
		}
		
		public void setY(int y) {
			if (y >= numOfRows) {
				this.y = y % numOfRows;	
			} else if (y < 0) {
				this.y = y + numOfRows;
			} else { 
				this.y = y;
			}
		}

		public Cell (int x, int y) { 
			this.x = -1;
			this.y = -1;

			setX(x);
			setY(y);
		}
		
		public boolean equals(Object obj) {
			boolean result = false;
			if (obj instanceof Cell) {
				Cell section = (Cell) obj;
				result = ( this.x == section.getX() && this.y == section.getY() );
			}
			return result;
		}
		
		public Cell clone() { // use or delete
			return new Cell( this.x, this.y );
		}
		
		public void paint(Graphics g) {
			int upperLeftX = x * cellWidth;
			int upperLeftY = y * cellHeight;
			g.fillRect(upperLeftX, upperLeftY, cellWidth, cellHeight);
		}
		
		public Cell neighbour(Direction direction){
			int x = this.x;
			int y = this.y;
			
			if (direction.isVertical())
				y += (direction.sign()? 1 : -1);
			else 
				x += (direction.sign()? 1 : -1);
			
			return new Cell(x,y);
		}
	}
}
	
	