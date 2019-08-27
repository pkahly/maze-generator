package solvers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SmartMouseSolver extends Solver {
	Random random;
	Point previousMove;
	
	public SmartMouseSolver() {
		random = new Random();
		previousMove = new Point(row, column);
	}
	
	@Override
	public String toString() {
		return "Smart Random Mouse";
	}

	@Override
	public void nextMove() {
		List<Point> moves = getValidMoves();
		
		// Find unvisited moves
		List<Point> newMoves = new ArrayList<Point>();
		for (Point move : moves) {
			int color = getColor(move.x, move.y);
			if (color == UNVISITED || color == FINISH) {
				newMoves.add(move);
			}
		}
		
		// If no unvisited moves, revert
		if (!newMoves.isEmpty()) {
			moves = newMoves;
		}
		
		// Find moves other than previous position
		newMoves = new ArrayList<Point>();
		for (Point move : moves) {
			if (move.x != previousMove.x || move.y != previousMove.y) {
				newMoves.add(move);
			}
		}
		
		// If backtracking is inevitable, revert
		if (!newMoves.isEmpty()) {
			moves = newMoves;
		}
		
		// Move
		if (!moves.isEmpty()) {
			previousMove = new Point(row, column);
			int choice = Math.abs(random.nextInt()) % moves.size();
			Point move = moves.get(choice);
			moveTo(move.x, move.y);
		}
	}

	@Override
	public boolean isDoneTraversing() {
		return false;
	}
}