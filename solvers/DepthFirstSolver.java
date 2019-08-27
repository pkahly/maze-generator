package solvers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class DepthFirstSolver extends Solver {
	private Stack<Point> stack;
	private Random random;
	
	public DepthFirstSolver() {
		random = new Random();
		stack = new Stack<Point>();
	}
	
	@Override
	public String toString() {
		return "Depth First Search";
	}
	
	@Override
	public void setStartLocation(int startRow, int startColumn) {
		super.setStartLocation(startRow, startColumn);
		stack.push(new Point(startRow, startColumn));
	}

	@Override
	public void nextMove() {
		if (stack.isEmpty()) {
			return;
		}
		
		// Find new moves
		List<Point> newMoves = new ArrayList<Point>();
		for (Point move : getValidMoves()) {
			int color = getColor(move.x, move.y);
			if (color == UNVISITED || color == FINISH) {
				newMoves.add(move);
			}
		}
		
		if (!newMoves.isEmpty()) {
			// Keep going
			int choice = Math.abs(random.nextInt()) % newMoves.size();
			Point move = newMoves.get(choice);
			stack.push(new Point(row, column));
			moveTo(move.x, move.y);
		} else {
			// Backtrack
			Point move = stack.pop();
			moveTo(move.x, move.y, POPPED);
		}
	}

	@Override
	public boolean isDoneTraversing() {
		return stack.isEmpty();
	}
}