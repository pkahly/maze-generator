package generators;

import java.util.List;
import java.util.Stack;

import data.MazeCell;

/**
 * Generates a perfect maze using Randomized Kruskal's algorithm.
 */
public class RandomizedKruskals extends Generator {
	public RandomizedKruskals() {
	}
	
	@Override
	public String toString() {
		return "Randomized Kruskal's";
	}
	
	@Override
	public MazeCell[][] generateMaze(int height, int width) {
		// Setup
		MazeCell[][] maze = createBlankMaze(height, width);
		
		return maze;
	}
}
