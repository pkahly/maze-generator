## Generates and Solves Mazes

#### After cloning, compile and run with:
```
# Shortcut for Unix systems
./run.sh

# OR

# Java commands
javac main/Main.java
java main.Main
```

#### You should then see a UI which allows you to set parameters for the maze generator and solver
![GUI](/images/Gui.png)

#### Solvers use colors to remember a cell's status
* white = unvisited
* light blue = visited - may be used for backtracking
* dark blue = popped - will not be used again

![Maze 1](/images/maze1.png)
![Maze 2](/images/maze2.png)
