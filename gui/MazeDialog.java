package gui;

import java.awt.image.BufferedImage;
import solvers.Solver;

import java.awt.event.ActionEvent;
import java.io.File;
import util.DisplayUtil;

import java.awt.event.ActionListener;

import javax.swing.Timer;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JDialog;

@SuppressWarnings("serial")
public class MazeDialog extends JDialog implements ActionListener {
	private Timer timer;
	private Solver solver;
	private BufferedImage image;
	
	public MazeDialog(BufferedImage image, Solver solver, int timerDelay) {
		this.solver = solver;
		this.image = image;
		
		// Start solver timer
		timer = new Timer(timerDelay, this);
		timer.setActionCommand("TIMER");
		timer.setCoalesce(true);
		timer.start();
		
		// Create image panel
		MazeImagePanel panel = new MazeImagePanel(image); 
		
		getContentPane().add(panel);
		setupMainFrame();
	}
	
	private void setupMainFrame() {
		Toolkit tk;
		Dimension d;
	      
		tk = Toolkit.getDefaultToolkit();
		d = tk.getScreenSize();
		      
		setSize(d.width, d.height);
		setLocation(0, 0);
		  
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Maze Generator");
		setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		solver.nextMove();
		if (solver.isFinished() || solver.isDoneTraversing()) {
			timer.stop();
			//DisplayUtil.saveImage(image, new File("maze.jpg"), 10); //TODO ask where to save it
		}
		repaint();
	}
	
	@Override
	public void dispose() {
		if (timer != null) {
			timer.stop();
		}
		super.dispose();
	}
}
