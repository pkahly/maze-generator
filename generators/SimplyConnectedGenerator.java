package generators;

import java.util.List;
import java.util.Stack;

import data.MazeCell;

/**
 * Generates a "Perfect" or "Simply-Connected" maze.
 * This means that there is exactly one path between each pair of cells. 
 */
public class SimplyConnectedGenerator extends Generator {
	public SimplyConnectedGenerator() {
	}
	
	@Override
	public String toString() {
		return "Depth First Search";
	}
	
	@Override
	public MazeCell[][] generateMaze(int height, int width) {
		// Setup
		MazeCell[][] maze = createBlankMaze(height, width);
		Stack<MazeCell> stack = new Stack<MazeCell>();
		MazeCell newCell;
		
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
				newCell = randomCell(unvisitedNeighbors);
				breakWall(currentCell, newCell);
				newCell.isVisited = true;
				stack.push(newCell);
				currentCell = newCell;
			}
		}
		
		return maze;
	}
}
