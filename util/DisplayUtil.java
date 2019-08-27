package util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import data.MazeCell;

public class DisplayUtil {
	/**
	 * Get bitmap of the given maze
	 */
	public static Bitmap bitmapFromMaze(MazeCell[][] maze) {
		Bitmap bitmap = createBitmap(maze.length, maze[0].length);

		int mazeRow = 0;
		int mazeColumn;
		while (mazeRow < maze.length) {
			mazeColumn = 0;
			while (mazeColumn < maze[mazeRow].length) {
				MazeCell cell = maze[mazeRow][mazeColumn];
				bitmap.writeCell(cell);
				mazeColumn++;
			}
			mazeRow++;
		}
		
		return bitmap;
	}

	private static Bitmap createBitmap(int mazeHeight, int mazeWidth) {
		int bitmapHeight = Bitmap.translateCoordinates(mazeHeight);
		int bitmapWidth = Bitmap.translateCoordinates(mazeWidth);
		return new Bitmap(bitmapHeight, bitmapWidth);
	}
	
	/**
	 * Get image for given maze 
	 */
	public static BufferedImage imageFromMaze(MazeCell[][] maze, int scaleFactor) {
		Bitmap bitmap = DisplayUtil.bitmapFromMaze(maze);
		
	    BufferedImage image = new BufferedImage(bitmap.width(), bitmap.height(), BufferedImage.TYPE_BYTE_INDEXED);
	    WritableRaster raster = image.getRaster();
	    
	    for(int column=0; column<bitmap.width(); column++) {
	        for(int row=0; row<bitmap.height(); row++) {
	        	// Image coords are reverse of maze coords
	            raster.setSample(column, row, 0, getColorValue(bitmap, row, column));
	        }
	    }
	    
	    return resizeImage(image, scaleFactor);
	}
	
	/**
	 * Save image to file
	 */
	public static void saveImage(BufferedImage image, File file, int scaleFactor) {
		image = resizeImage(image, scaleFactor);
	    
	    try {
			ImageIO.write(image, "jpg", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int getColorValue(Bitmap bitmap, int row, int column) {
		if (bitmap.get(row, column)) {
			return 0;
		}
		return 255;
	}
	
	/**
	 * Resize image 
	 */
	public static BufferedImage resizeImage(BufferedImage originalImage, int scaleFactor) {
		if (scaleFactor <= 1) {
			return originalImage;
		}
		int newWidth = originalImage.getWidth() * scaleFactor;
		int newHeight = originalImage.getHeight() * scaleFactor;
		
		return resizeImage(originalImage, newWidth, newHeight);
	}
	
	public static BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
	    BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, Image.SCALE_REPLICATE);
	    Graphics2D g = resizedImage.createGraphics();
	    g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
	    g.dispose();

	    return resizedImage;
	}
}