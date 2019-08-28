package generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import data.MazeCell;

/**
 * Generates a maze using Wilson's algorithm.
 */
public class Wilsons extends Generator {
	
	@Override
	public String toString() {
		return "Wilson's";
	}
	
	@Override
	public MazeCell[][] generateMaze(int height, int width) {
		// Setup
		MazeCell[][] maze = createBlankMaze(height, width);
		
		// Get a list of all cells
		List<MazeCell> unvisitedCells = getAllCells(maze);
		
		// Mark a random cell as part of the maze
	   MazeCell initialCell = randomCell(unvisitedCells);
	   unvisitedCells.remove(initialCell);
	   initialCell.isVisited = true;
		
		// Until the maze is connected
		while (!unvisitedCells.isEmpty()) {
		   // Select the first cell
		   MazeCell start = unvisitedCells.remove(0);
		   
		   // Perform loop-erased random walk
		   List<MazeCell> path = loopErasedRandomWalk(maze, start);
		   
		   // Add those cells to the maze
		   path.remove(start);
		   start.isVisited = true;
		   
		   MazeCell prev = start;
		   for (MazeCell cell : path) {
		      // Connect the cells
   	      breakWall(prev, cell);
   	      
   	      // Mark visited
		      cell.isVisited = true;
		      
		      // Remove from our selection list
		      unvisitedCells.remove(cell);
		      
		      prev = cell;
		   }
		}
		
		return maze;
	}
	
	private List<MazeCell> loopErasedRandomWalk(MazeCell[][] maze, MazeCell start) {
	   List<MazeCell> path = new ArrayList<>();
	   path.add(start);
	   
	   MazeCell cell = start;
	   while (true) {
	      // Get neighboring cells
	      List<MazeCell> neighbors = getNeighbors(maze, cell);
	      
	      // Pick one at random
	      MazeCell next = randomCell(neighbors);
	      
	      // Check for loops
	      if (path.contains(next)) {
	         MazeCell loopCell = null;
	         while (loopCell != next) {
	            loopCell = path.remove(path.size() - 1);
	         }
	      }
	      
	      // Add to path
	      path.add(next);
	      
	      // If we've reached the maze, stop
	      if (next.isVisited) {
	         break;
	      }
	      
	      cell = next;
	   }
	   
	   return path;
   }
}
