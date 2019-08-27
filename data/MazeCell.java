package data;

public class MazeCell {
	// Walls. True means the wall exists.
	public boolean northWall = true;
	public boolean southWall = true;
	public boolean eastWall = true;
	public boolean westWall = true;
	
	// Has this cell been visited by the Generator
	public boolean isVisited = false;
	
	// Location info
	public int row;
	public int column;
	
	/**
	 * 
	 */
	public MazeCell(int row, int column) {
		this.row = row;
		this.column = column;
	}
}
