package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;

import solvers.*;
import generators.*;
import util.Bitmap;
import util.DisplayUtil;
import data.MazeCell;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener {
	private JPanel optionPanel;
	private JPanel buttonPanel;
	private JComboBox<Generator> genAlgorithm;
	private JCheckBox loopBox;
	private JComboBox<Solver> solverAlgorithm;
	private JTextField widthBox;
	private JTextField heightBox;
	private JTextField timerDelayBox;
	private JCheckBox eraseVisitedBox;
	private JCheckBox erasePoppedBox;
	
	public MainFrame() {
		optionPanel = new JPanel();
		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
		buttonPanel = new JPanel(new BorderLayout());
		
		//TODO fix layout
		
		// Size
		widthBox = new JTextField("20");
		widthBox.setPreferredSize(new Dimension(50, 25));
		addControl("Width:", widthBox);
		heightBox = new JTextField("10");
		heightBox.setPreferredSize(new Dimension(50, 25));
		addControl("Height:", heightBox);
		
		// Generator Algorithm
		genAlgorithm = new JComboBox<Generator>();
		genAlgorithm.addItem(new Wilsons());
		//genAlgorithm.addItem(new RandomizedKruskals()); // TODO(#6) Implement more maze generation algorithms
		genAlgorithm.addItem(new RecursiveBacktracker());
		addControl("Maze Generation Algorithm:", genAlgorithm);
		
		// Loop Settings
		loopBox = new JCheckBox();
		addControl("Add Loops", loopBox);
		
		// Solver Algorithm
		solverAlgorithm = new JComboBox<Solver>();
		solverAlgorithm.addItem(new RandomMouseSolver());
		solverAlgorithm.addItem(new SmartMouseSolver());
		//solverAlgorithm.addItem(new WallFollowerSolver()); // TODO(#2) Implement Wall-Follower
		solverAlgorithm.addItem(new DepthFirstSolver());
		solverAlgorithm.addItem(new BreadthFirstSolver());
		addControl("Maze Solving Algorithm:", solverAlgorithm);
		
		// Solver delay
		timerDelayBox = new JTextField("100");
		timerDelayBox.setPreferredSize(new Dimension(50, 25));
		addControl("Solver Delay, (ms):", timerDelayBox);
		
		// Color Settings
		eraseVisitedBox = new JCheckBox();
		addControl("Erase visited cells", eraseVisitedBox);
		erasePoppedBox = new JCheckBox();
		addControl("Erase popped cells", erasePoppedBox);
		
		// Generate Button
		JButton generateButton = new JButton("Generate");
		generateButton.setActionCommand("GENERATE_MAZE");
		generateButton.addActionListener(this);
		buttonPanel.add(generateButton, BorderLayout.EAST);
		
		Container cp = getContentPane();
		cp.add(optionPanel);
		cp.add(buttonPanel, BorderLayout.SOUTH);
		setupMainFrame();
	}
	   
	private void addControl(String description, Component component) {
		JPanel panel = new JPanel();
		panel.add(new JLabel(description));
		panel.add(component);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		optionPanel.add(panel);
	}

	private void setupMainFrame() {
		Toolkit tk;
		Dimension d;
	      
		tk = Toolkit.getDefaultToolkit();
		d = tk.getScreenSize();
		      
		setSize(d.width/2, d.height/2);
		setLocation(d.width/4, d.height/4);
		  
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setTitle("Maze Generator");
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int height = 10;
		int width = 20;
		int timerDelay = 100;
		
		try {
			height = Integer.parseInt(heightBox.getText());
			width = Integer.parseInt(widthBox.getText());
			timerDelay = Integer.parseInt(timerDelayBox.getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Nice try. Enter actual integers.");
			return;
		}
		
		if (height < 1) {
			JOptionPane.showMessageDialog(this, "Height must be positive");
		} else if (width < 1) {
			JOptionPane.showMessageDialog(this, "Width must be positive");
		} else if (timerDelay < 1) {
			JOptionPane.showMessageDialog(this, "Timer delay must be positive");
		} else {
		   // Generate Maze
			Generator generator = (Generator)genAlgorithm.getSelectedItem();
			MazeCell[][] maze = generator.generateMaze(height, width);
			
			// Add loops
			if (loopBox.isSelected()) {
			   generator.addLoops(maze);
			}
			
			// Create maze image
			BufferedImage image = DisplayUtil.imageFromMaze(maze, 0);
			
			// Create solver
			int startRow = Bitmap.translateCoordinates(0);
			int startColumn = Bitmap.translateCoordinates(0);
			
			int finishRow = Bitmap.translateCoordinates(maze.length - 1);
			int finishColumn = Bitmap.translateCoordinates(maze[0].length - 1);
			
			Solver solver = (Solver)solverAlgorithm.getSelectedItem();
			solver.setImage(image);
			solver.setStartLocation(startRow, startColumn);
			solver.setFinishLocation(finishRow, finishColumn);
			solver.setEraseVisited(eraseVisitedBox.isSelected());
			solver.setErasePopped(erasePoppedBox.isSelected());
			
			// Display maze
			new MazeDialog(image, solver, timerDelay);
		}
	}
}
