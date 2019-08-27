package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import util.DisplayUtil;

@SuppressWarnings("serial")
public class MazeImagePanel extends JPanel {
	private BufferedImage bitmapImage;
	
	public MazeImagePanel(BufferedImage bitmapImage) {
		this.bitmapImage = bitmapImage;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if (bitmapImage != null) {
			BufferedImage scaledImage = DisplayUtil.resizeImage(bitmapImage, getWidth(), getHeight());
			g.drawImage(scaledImage, 0, 0, null);
		}
	}
}