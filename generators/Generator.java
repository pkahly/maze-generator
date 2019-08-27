package generators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import data.MazeCell;

public abstract class Generator {
	Random random;
	
	public Generator() {
		random = new Random();
	}
	
	/**
	 * Produce a square maze with a solution
	 */
	public MazeCell[][] generateMaze(int size) {
		return generateMaze(size, size);
	}
	
	/**
	 * Produce a new maze with a solution. 
	 */
	public abstract MazeCell[][] generateMaze(int height, int width);
	
	/**
	 * Create a new maze of unvisited cells with all their walls intact.
	 */
	public MazeCell[][] createBlankMaze(int height, int width) {
		MazeCell[][] maze = new MazeCell[height][];
		int row = 0;
		while (row < height) {
			maze[row] = new MazeCell[width];
			int column = 0;
			while (column < width) {
				maze[row][column] = new MazeCell(row, column);
				column++;
			}
			row++;
		}
		return maze;
	}
	
	/**
	 * Connect the two cells together.
	 */
	protected void breakWall(MazeCell currentCell, MazeCell newCell) {
		if (currentCell.row == newCell.row) {
			// Cells are in same row
			if ((currentCell.column + 1) == newCell.column) {
				// newCell is east of currentCell
				currentCell.eastWall = false;
				newCell.westWall = false;
			} else if (currentCell.column == (newCell.column + 1)) {
				// newCell is west of currentCell
				currentCell.westWall = false;
				newCell.eastWall = false;
			} else {
				throw new IllegalArgumentException("Cells are not touching");
			}
		} else if (currentCell.column == newCell.column) {
			// Cells are in same column
			if ((currentCell.row + 1) == newCell.row) {
				// newCell is south of currentCell
				currentCell.southWall = false;
				newCell.northWall = false;
			} else if (currentCell.row == (newCell.row + 1)) {
				// newCell is north of currentCell
				currentCell.northWall = false;
				newCell.southWall = false;
			} else {
				throw new IllegalArgumentException("Cells are not touching");
			}
		} else {
			throw new IllegalArgumentException("Cells are not in same row or column");
		}
	}
	
	/**
	 * Select a random cell from the maze.
	 */
	public MazeCell randomCell(MazeCell[][] maze) {
		int row = Math.abs(random.nextInt()) % maze.length;
		int column = Math.abs(random.nextInt()) % maze[row].length;
		return maze[row][column];
	}
	
	/**
	 * Select a random cell from the list.
	 */
	protected MazeCell randomCell(List<MazeCell> unvisitedNeighbors) {
		int index = Math.abs(random.nextInt()) % unvisitedNeighbors.size();
		return unvisitedNeighbors.get(index);
	}
	
	/**
	 * Get the unvisited cells bordering the current cell.
	 * Returns a list containing between 0 and 4 cells. 
	 */
	protected List<MazeCell> getUnvisitedNeighbors(MazeCell[][] maze, MazeCell currentCell) {
		List<MazeCell> cells = new ArrayList<MazeCell>();
		int row = currentCell.row;
		int column = currentCell.column;
		
		addCellIfValid(maze, cells, row-1, column); // North 
		addCellIfValid(maze, cells, row+1, column); // South
		addCellIfValid(maze, cells, row, column-1); // West
		addCellIfValid(maze, cells, row, column+1); // East
		
		Iterator<MazeCell> iter = cells.iterator();
		while (iter.hasNext()) {
			MazeCell cell = iter.next();
			if (cell.isVisited) {
				iter.remove();
			}
		}
		
		return cells;
	}
	
	private void addCellIfValid(MazeCell[][] maze, List<MazeCell> cells, int row, int column) {
		if (row >= 0 && row < maze.length) { // Row is valid
			if (column >= 0 && column < maze[row].length) { // Column is valid
				cells.add(maze[row][column]);
			}
		}
	}
}