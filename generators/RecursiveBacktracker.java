package generators;

import java.util.List;
import java.util.Stack;

import data.MazeCell;

/**
 * Generates a maze using a recursive backtracker
 */
public class RecursiveBacktracker extends Generator {
	
	@Override
	public String toString() {
		return "Recursive Backtracker";
	}
	
	@Override
	public MazeCell[][] generateMaze(int height, int width) {
		// Setup
		MazeCell[][] maze = createBlankMaze(height, width);
		Stack<MazeCell> stack = new Stack<MazeCell>();
		
		// Initial Cell
		MazeCell currentCell = randomCell(maze);
		currentCell.isVisited = true;
		stack.push(currentCell);
		
		// Depth-first generation
		while (!stack.isEmpty()) {
			List<MazeCell> unvisitedNeighbors = getUnvisitedNeighbors(maze, currentCell);
			
			if (unvisitedNeighbors.isEmpty()) {
				// We are at a dead end
				currentCell = stack.pop();
			} else {
				// Visit another cell
				MazeCell newCell = randomCell(unvisitedNeighbors);
				breakWall(currentCell, newCell);
				newCell.isVisited = true;
				stack.push(newCell);
				currentCell = newCell;
			}
		}
		
		return maze;
	}
}
