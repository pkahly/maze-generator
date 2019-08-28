package generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import data.MazeCell;

public abstract class Generator {
   public static final int LOOP_MULTIPLIER = 3;
   
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
	 * Retrieve a list of all cells in the maze
	 */
   public List<MazeCell> getAllCells(MazeCell[][] maze) {
      List<MazeCell> cells = new ArrayList<MazeCell>();
      
      for (MazeCell[] array : maze) {
        cells.addAll(Arrays.asList(array));
      }
      
      return cells;
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
	 * Add loops to the maze by removing a few random walls
	 */
	public void addLoops(MazeCell[][] maze) {
	   // Number of Loops is proportional to maze height
	   int numLoops = maze.length * LOOP_MULTIPLIER;
	   
	   for (int i = 0; i < numLoops; i++) {
	      // Pick a random cell
	      MazeCell cell = randomCell(maze);
	      
	      // Get a list of it's neighbors
	      List<MazeCell> neighbors = getNeighbors(maze, cell);
	      
	      // Pick a random neighbor
	      MazeCell neighbor = randomCell(neighbors);
	      
	      // Connect the cells
	      breakWall(cell, neighbor);
	   }
	}
	
	/**
	 * Select a random cell from the list.
	 */
	protected MazeCell randomCell(List<MazeCell> cells) {
		int index = Math.abs(random.nextInt()) % cells.size();
		return cells.get(index);
	}
	
	/**
	 * Get the unvisited cells bordering the current cell.
	 * Returns a list containing between 0 and 4 cells. 
	 */
	protected List<MazeCell> getUnvisitedNeighbors(MazeCell[][] maze, MazeCell currentCell) {
	   // Get neighboring cells
		List<MazeCell> cells = getNeighbors(maze, currentCell);
		
		// Remove cells that have been visited
		Iterator<MazeCell> iter = cells.iterator();
		while (iter.hasNext()) {
			MazeCell cell = iter.next();
			if (cell.isVisited) {
				iter.remove();
			}
		}
		
		return cells;
	}
	
	/**
	 * Get all cells bordering the current cell.
	 * Returns a list containing between 2 and 4 cells. 
	 */
	protected List<MazeCell> getNeighbors(MazeCell[][] maze, MazeCell currentCell) {
		List<MazeCell> cells = new ArrayList<MazeCell>();
		int row = currentCell.row;
		int column = currentCell.column;
		
		addCellIfValid(maze, cells, row-1, column); // North 
		addCellIfValid(maze, cells, row+1, column); // South
		addCellIfValid(maze, cells, row, column-1); // West
		addCellIfValid(maze, cells, row, column+1); // East
		
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
