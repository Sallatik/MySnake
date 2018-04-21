import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;
class Matrix extends JPanel{ 
		
	Room game;
	
	public static final Color BACKGROUND_DEFAULT = 	Color.LIGHT_GRAY;	
	public static final Color COLOR_DEFAULT = Color.yellow;

	private int numOfRows;
	private int numOfColumns;
	
	private int cellWidth;
	private int cellHeight;
	private double millisPerPixelHorizontal; // for animated cells
	private double millisPerPixelVertical; // for animated cells

	private Color background;
	private Color color;

	//DEBUG !!! To be deleted.
	//JLabel label;

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

	public Matrix ( Room game, int width, int height ) {	// constructor
		this.game = game;
		
		this.numOfColumns = width;
		this.numOfRows = height;
		
		cellWidth = 10;
		cellHeight = 10;

		background = BACKGROUND_DEFAULT;
		color = COLOR_DEFAULT;

		// DEBUG !!! To be deleted.
		/*this.setLayout(new BorderLayout());
		label = new JLabel("DEBUG: ");
		this.add(label,BorderLayout.NORTH); */

	}

	public void paintComponent ( Graphics g ) {
		
		int width = getWidth();
		int height = getHeight();
		
		cellWidth = width / this.numOfColumns;
		cellHeight = height / this.numOfRows;

		millisPerPixelHorizontal = (double)game.TICK / (double)cellWidth;
		millisPerPixelVertical = (double)game.TICK / (double)cellHeight;

		g.setColor(background);
		g.fillRect(0, 0, width, height);	// filling the background
		
		g.setColor(color);
		for (Entity entity : game.getEntities()) {	// printing all the entities
			entity.paint(g);
		}

		//DEBUG!!! To be deleted.
		//label.setText("Height:" + getHeight() + "	Width:" + getWidth() + "	CellHeight: " + cellHeight + "	CellWidth:" + cellWidth + "	millisPerPixelHorizontal:" + millisPerPixelHorizontal + "	millisPerPixelVertical: "+ millisPerPixelVertical);
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
			} else if (x < 0) {
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

		public Cell(int x, int y) {
			this.x = -1;
			this.y = -1;

			setX(x);
			setY(y);
		}

		public boolean equals(Object obj) {
			boolean result = false;
			if (obj instanceof Cell) {
				Cell section = (Cell) obj;
				result = (this.x == section.getX() && this.y == section.getY());
			}
			return result;
		}

		public Cell clone() { // use or delete
			return new Cell(this.x, this.y);
		}

		public void paint(Graphics g) {
			int upperLeftX = x * cellWidth;
			int upperLeftY = y * cellHeight;
			g.fillRect(upperLeftX, upperLeftY, cellWidth, cellHeight);
		}

		public Cell neighbour(Direction direction) {
			int x = this.x;
			int y = this.y;

			if (direction.isVertical())
				y += (direction.sign() ? 1 : -1);
			else
				x += (direction.sign() ? 1 : -1);

			return new Cell(x, y);
		}

	}

	public class AnimatedCell extends Cell{

			private Direction direction;
			private long startTime;
			private boolean isShrinking;

			public AnimatedCell(int x,int y,Direction direction){
				super(x,y);
				this.direction = direction;
				isShrinking = false;
				startTime = System.currentTimeMillis();
			}
			private AnimatedCell(Cell cell, Direction direction){
				this(cell.getX(),cell.getY(),direction);
			}
			@Override
			public Cell neighbour(Direction direction){
				return new AnimatedCell(super.neighbour(direction),direction);
			}

			@Override
			public void paint(Graphics g){
				int upperLeftX = getX() * cellWidth;
				int upperLeftY = getY() * cellHeight;

				int width = cellWidth;
				int height = cellHeight;

				int timePassed = (int) (System.currentTimeMillis() - startTime);
				timePassed = timePassed > game.TICK? game.TICK : timePassed;

				if(direction.isVertical())
					height = (int) (isShrinking? cellHeight - timePassed / millisPerPixelVertical : timePassed / millisPerPixelVertical);
				else
					width = (int) (isShrinking? cellWidth - timePassed / millisPerPixelHorizontal : timePassed / millisPerPixelHorizontal);
				if(isShrinking){
					if(direction == Direction.SOUTH)
						upperLeftY +=cellHeight - height;
					else if(direction == Direction.EAST)
						upperLeftX +=cellWidth - width;
				} else {
					if (direction == Direction.NORTH)
						upperLeftY += cellHeight - height;
					else if (direction == Direction.WEST)
						upperLeftX += cellWidth - width;
				}

				g.fillRect(upperLeftX,upperLeftY,width,height);
			}

			public void startShrinking(){
				isShrinking = true;
				startTime = System.currentTimeMillis();
			}
	}
}

	
	