package util;

import data.MazeCell;

public class Bitmap {
	private boolean[][] bitmapArray;
	
	public Bitmap(int height, int width) {
		bitmapArray = new boolean[height][];
		
		int row = 0;
		while (row < bitmapArray.length) {
			bitmapArray[row] = new boolean[width];
			int column = 0;
			while (column < bitmapArray[row].length) {
				bitmapArray[row][column] = true;
				column++;
			}
			row++;
		}
	}
	
	public int width() {
		return bitmapArray[0].length;
	}

	public int height() {
		return bitmapArray.length;
	}
	
	public void print() {
		int row = 0;
		while (row < bitmapArray.length) {
			int column = 0;
			while (column < bitmapArray[row].length) {
				if (bitmapArray[row][column]) {
					System.out.print("X");
				} else {
					System.out.print(" ");
				}
				column++;
			}
			System.out.println("");
			row++;
		}
		System.out.println("");
	}
	
	public void writeCell(MazeCell cell) {
		int cellRow = translateCoordinates(cell.row);
		int cellColumn = translateCoordinates(cell.column);
		
		// Write cell
		bitmapArray[cellRow][cellColumn] = false;
		
		// Write north wall
		bitmapArray[cellRow-1][cellColumn] = cell.northWall;
		
		// Write south wall
		bitmapArray[cellRow+1][cellColumn] = cell.southWall;
		
		// Write west wall
		bitmapArray[cellRow][cellColumn-1] = cell.westWall;
		
		// Write east wall
		bitmapArray[cellRow][cellColumn+1] = cell.eastWall;
	}
	
	/**
	 * Convertes Maze coordinates or dimensions
	 * into Bitmap coordinates or dimensions.  
	 */
	public static int translateCoordinates(int mazeValue) {
		return (mazeValue * 2) + 1;
	}

	public boolean get(int row, int column) {
		return bitmapArray[row][column];
	}
}