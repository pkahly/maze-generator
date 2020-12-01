package solvers;

import java.util.ArrayList;
import java.awt.Point;
import java.util.List;

public class BreadthFirstSolver extends Solver {
	List<Point> queue;
	int moveCount;
	
	public BreadthFirstSolver() {
		queue = new ArrayList<Point>();
	}
	
	@Override
	public String toString() {
		return "Breadth First Search";
	}
	
	@Override
	public void setStartLocation(int startRow, int startColumn) {
		super.setStartLocation(startRow, startColumn);
		queue.add(new Point(startRow, startColumn));
		moveCount = 0;
	}
	
	@Override
	public void nextMove() {
		List<Point> newQueue = new ArrayList<Point>();
		
		// Generate heatmap based on how long it took to reach a spot
		int visitedColor = VISITED + (moveCount / 200);

		for (Point move : queue) {
			moveTo(move.x, move.y, visitedColor);
			setColor(move.x, move.y, visitedColor);
			for (Point neighbor : getValidMoves()) {
				int color = getColor(neighbor.x, neighbor.y);
				if (color == UNVISITED | color == FINISH) { 
					newQueue.add(neighbor);
				}
			}
		}
		
		queue = newQueue;
		moveCount++;
	}

	@Override
	public boolean isDoneTraversing() {
		return queue.isEmpty();
	}

	/**
	* Only stop once the entire maze is explored
	*/
	@Override
	public boolean isFinished() {
		return isDoneTraversing();
	}
}
