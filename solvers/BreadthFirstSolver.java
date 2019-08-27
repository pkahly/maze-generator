package solvers;

import java.util.ArrayList;
import java.awt.Point;
import java.util.List;

public class BreadthFirstSolver extends Solver {
	List<Point> queue;
	
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
	}
	
	@Override
	public void nextMove() {
		List<Point> newQueue = new ArrayList<Point>();
		
		for (Point move : queue) {
			moveTo(move.x, move.y);
			setColor(move.x, move.y, VISITED);
			for (Point neighbor : getValidMoves()) {
				int color = getColor(neighbor.x, neighbor.y);
				if (color == UNVISITED | color == FINISH) { 
					newQueue.add(neighbor);
				}
			}
		}
		
		queue = newQueue;
	}

	@Override
	public boolean isDoneTraversing() {
		return queue.isEmpty();
	}
}