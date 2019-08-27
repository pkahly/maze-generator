package solvers;

import java.awt.Point;

public class WallFollowerSolver extends Solver {
	public enum Direction {
		NORTH,
		SOUTH,
		EAST,
		WEST;
	}
	//TODO use direction vectors and rotation matricies

	private Direction direction;
	
	public WallFollowerSolver() {
		direction = Direction.SOUTH;
	}
	
	@Override
	public String toString() {
		return "Wall Follower";
	}
	
	@Override
	public void nextMove() {
		Point right = getRightCell();
		Point forward = getForwardCell();
		Point left = getLeftCell();
		Point back = getBackCell();
	}

	private Point getRightCell() {
		return null;
	}

	private Point getForwardCell() {
		return null;
	}

	private Point getLeftCell() {
		return null;
	}

	private Point getBackCell() {
		return null;
	}

	@Override
	public boolean isDoneTraversing() {
		return false;
	}
}
