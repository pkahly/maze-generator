package solvers;

import java.awt.Point;
import java.util.List;
import java.util.Random;

public class RandomMouseSolver extends Solver {
	Random random;
	
	public RandomMouseSolver() {
		random = new Random();
	}
	
	@Override
	public String toString() {
		return "Random Mouse";
	}

	@Override
	public void nextMove() {
		List<Point> moves = getValidMoves();
		if (!moves.isEmpty()) {
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
