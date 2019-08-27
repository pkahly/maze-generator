package solvers;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//TODO wall-walker subclass
//TODO (others)
public abstract class Solver {
	// Colors
	public static final int WALL = 0;
	public static final int UNVISITED = 255;
	public static final int CURRENT = 160;
	public static final int VISITED = 100;
	public static final int POPPED = 40;
	public static final int FINISH = 68;
	
	// Data
	private BufferedImage image;
	protected int row;
	protected int column;
	private int finishRow;
	private int finishColumn;
	private long moves = 0;
	
	// Settings
	protected boolean eraseVisited;
	protected boolean erasePopped;
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public void setStartLocation(int startRow, int startColumn) {
		row = startRow;
		column = startColumn;
		setColor(row, column, CURRENT);
	}
	
	public void setFinishLocation(int finishRow, int finishColumn) {
		this.finishRow = finishRow;
		this.finishColumn = finishColumn;
		setColor(finishRow, finishColumn, FINISH);
	}
	
	public void setEraseVisited(boolean eraseVisited) {
		this.eraseVisited = eraseVisited;
	}
	
	public void setErasePopped(boolean erasePopped) {
		this.erasePopped = erasePopped;
	}
	
	public abstract void nextMove();
	
	public int getColor(int row, int column) {
		Raster raster = image.getRaster();
		// Note that images have reversed indicies
	    return raster.getSample(column, row, 0);
	}
	
	public void setColor(int row, int column, int color) {
		WritableRaster raster = image.getRaster();
		// Note that images have reversed indicies
		raster.setSample(column, row, 0, color);
	}
	
	public boolean isFinished() {
		if (row == finishRow && column == finishColumn) {
			System.out.println("Moves: " + moves);
			return true;
		}
		return false;
	}
	
	protected List<Point> getValidMoves() {
		List<Point> moves = new ArrayList<Point>();
		
		addCellIfValid(moves, row-1, column); // North 
		addCellIfValid(moves, row+1, column); // South
		addCellIfValid(moves, row, column-1); // West
		addCellIfValid(moves, row, column+1); // East
		
		// Remove walls
		Iterator<Point> iter = moves.iterator();
		while (iter.hasNext()) {
			Point move = iter.next();
			if (getColor(move.x, move.y) == WALL) {
				iter.remove();
			}
		}
		
		return moves;
	}
	
	private void addCellIfValid(List<Point> cells, int row, int column) {
		if (row >= 0 && row < image.getHeight()) { // Row is valid
			if (column >= 0 && column < image.getWidth()) { // Column is valid
				cells.add(new Point(row, column));
			}
		}
	}
	

	protected void moveTo(int newRow, int newColumn) {
		moveTo(newRow, newColumn, VISITED);
	}
	
	protected void moveTo(int newRow, int newColumn, int oldColor) {
		if (eraseVisited && oldColor == VISITED) {
			oldColor = WALL;
		} else if (erasePopped && oldColor == POPPED) {
			oldColor = WALL;
		}
		
		moves++;
		setColor(row, column, oldColor);
		row = newRow;
		column = newColumn;
		setColor(row, column, CURRENT);
	}

	public abstract boolean isDoneTraversing();
}