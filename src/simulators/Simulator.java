/* Ne pas faire de tests ici */
package simulators;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import config.Config;
import diseases.Disease;

import subjects.Subject;
import subjects.Type;

/**
 * Simulator is a class who manages a grid of subject. They meet each others by
 * a cross pattern. Simulator displays the grid on the console.
 * 
 * @author Dorian LIZARRALDE
 * 
 */
public class Simulator {

    /**
     * Defines the grid of Subject.
     * 
     */
    private Subject[][] grid;

    /**
     * Defines when the simulation stops.
     * 
     */
    private boolean endOfSimulation;

    /**
     * Default constructor.
     * 
     * @param s
     */
    public Simulator() {

        // New random grid
        grid = randomGrid();

        // Infects a number of subject
        infect();
    }
    
    /**
     * Set the new Subject grid
     * @param grid : the new grid
     */
    public void setGrid (Subject grid[][]) {
    	this.grid = grid;
    	
    } // setGrid()
    
    /**
     * get the subject grid
     * @return the simulator's Subject grid
     * @author lecpie
     */
    public Subject[][] getGrid () {
    	return grid;
    	
    } // getGrid()
    
    /**
     * get the subject located in the grid at the given coordinates
     * @param x : the x part of the coordinates
     * @param y : the y part of the coordinates
     * @return the subject in the x and y coordinates in the grid
     */
    public Subject getSubject (int x, int y) {
    	return grid[y][x];
    	
    } // getSubject()

    /**
     * Generates a random grid.
     * 
     * @return
     */
    private Subject[][] randomGrid() {

        // Width of the grid
        int width = Integer.parseInt(Config.getProperty("Grid.Width"));

        // Length of the grid
        int length = Integer.parseInt(Config.getProperty("Grid.Length"));

        // New grid
        Subject[][] s = new Subject[width][length];

        // List of subject
        List<Subject> l = new ArrayList<Subject>();

        // Generates a number of subject in function of the ratio
        for (Type t : Type.values()) {

            // Ratio of the actual subject
            int ratio = Integer.parseInt(Config.getProperty("Ratio."
                    + t.toString()));

            // New subject add to the list
            for (int i = 0; i < width * length * ratio / 100.; i++) {

                l.add(new Subject(t));
            }
        }

        int size = l.size();

        // Fills the list with empty spaces
        for (int i = 0; i < width * length - size; i++) {

            l.add(null);
        }

        // Shuffles the list
        Collections.shuffle(l);

        // Fills the grid
        for (int i = 0; i < width; i++) {

            for (int j = 0; j < length; j++) {

                s[i][j] = l.get(i * 10 + j);
            }
        }

        return s;
    }

    /**
     * Infects a number of subject.
     * 
     */
    private void infect() {

        for (int i = 0; i < Integer.parseInt(Config
                .getProperty("Grid.Infected")); i++) {

            // Position x
            int x = (int) (grid.length * Math.random());

            // Position y
            int y = (int) (grid[0].length * Math.random());

            // Infects the subject
            if (grid[x][y] != null) {

                switch (grid[x][y].getType()) {

                // Human starts healthy
                case HUMAN:
                    break;

                case CHICKEN:
                    grid[x][y].setDisease(Disease.H5N1);
                    break;

                case DUCK:
                    grid[x][y].setDisease(Disease.H5N1);
                    break;

                case PIG:
                    grid[x][y].setDisease(Disease.H1N1);
                    break;

                // Empty space
                default:
                    i--;
                    break;
                }
            }
        }
    }

    /**
     * Starts the simulation.
     * 
     */
    public void run() {

        // Days counter
        int days = 1;

        // Infinite loop
        while (!endOfSimulation) {

            // First step of the day
            day();

            // Displays the grid
            System.out.println(toString());

            // Last step of the day
            night();

            // Displays current number of days
            System.out.println("Actual Day : " + days);

            // Wait for continue or displays end of simulation
            if (!endOfSimulation) {

                System.out.println("Press any key to continue");

                new Scanner(System.in).nextLine();
            } else {

                System.out.println("End of the Simulation");
            }

            // Increments number of days
            days++;
        }
    }
	
    /**
     * 
     * @param a : a number representing an axis like x or y
     * @param mod : the axis length
     * @return an integer representing the converted axis of the parameter a
     * 
     * @author lecpie
     */
	private int getAxis (int a, int mod) {
		a %= mod;
		if (a < 0)
			a += mod;
		
		return a;
		
	} // getAxis()
	
	/**
	 * 
	 * @param x : the x part of the coordinates
	 * @param y : the y part of the coordinates
	 * @return the converted coordinates with the getAxis() function
	 * 
	 * @author lecpie
	 */
	private Point getCoords (int x, int y) {
		x = getAxis(x, grid[0].length);
		y = getAxis(y, grid.length);
		
		return new Point (x, y);
		
	} // getCoords()

    /**
     * Get a list of all the coordinates considered in the neighborhood of a given coordinates.
     * A neighbor cell can be only one cell away on the same line, same column 	and on the same diagonals.
     * @param point : the coordinates we want to get the neighborhood
     * @return a List<Point> representing the neighborhood
     * 
     * @author lecpie
     */
    public List<Point> getNeighborhood(Point point) {

		List<Point> neighborhood = new ArrayList<Point>();

		neighborhood.add(getCoords(point.x - 1, point.y - 1));
		neighborhood.add(getCoords(point.x - 1, point.y    ));
		neighborhood.add(getCoords(point.x - 1, point.y + 1));
		
		neighborhood.add(getCoords(point.x + 1, point.y - 1));
		neighborhood.add(getCoords(point.x + 1, point.y    ));
		neighborhood.add(getCoords(point.x + 1, point.y + 1));
		
		neighborhood.add(getCoords(point.x, point.y + 1));
		neighborhood.add(getCoords(point.x, point.y - 1));

		return neighborhood;

	} // getNeighborhood()
    
    /**
     * get a all the living neighbors of the given coordinates
     * @param point : the coordinates we need to get the living neighbors.
     * @return a List of Points, each Point giving the coordinates of a living neighbor
     * 
     * @author lecpie
     */
    public List<Point> getNeighbors(Point point) {
		List<Point> neighbors = getNeighborhood(point);

		for (Iterator<Point> it = neighbors.iterator(); it.hasNext();) {
			Point neighbor = (Point) it.next();

			if (grid[neighbor.y][neighbor.x] == null)
				it.remove();
		}
		
		return neighbors;
		
    } // getNeighbors()
	
    /**
     * Attempt to move a subject to a specific location, fails if the cell
     * where we want the subject to move is occupied already.
     * 
     * @author lecpie
     * 
     * @param orig : the coordinates of the subject to move
     * @param dest : the coordinates where we want the subject to move
     * @return true if the move was successful, false otherwise
     */
    public boolean move(Point orig, Point dest) {

		// Avoid to move where someone already is

		if (grid[dest.y][dest.x] != null)
			return false;

		grid[dest.y][dest.x] = grid[orig.y][orig.x];
		grid[orig.y][orig.x] = null;
		
		return true;

	} // move()
	
    /**
     * Try to move a subject to a random location within it's neighborhood.
     * The subject will not move if he wants to go where someone already is.
     * 
     * @param orig : the coordinates of the subject we want to randomly move
     * @return true if the subject moved, false otherwise
     */
	public boolean randomMove(Point orig) {

		List<Point> neighborhood = getNeighborhood(orig);

		return move(orig, neighborhood.get((int) (Math.random() * neighborhood.size())));

	} // radomMove()


    /**
     * Changes the state of the subject in the grid. Contagious subject infect
     * their neighbor.
     * @author Dorian LIZARRALDE, lecpie
     */
    public void day() {

        List <Subject> movedAlready = new ArrayList<Subject>();
    	
    	for (int i = 0; i < grid.length; i++) {

            for (int j = 0; j < grid[i].length; j++) {

                if (grid[i][j] == null || movedAlready.contains(grid[i][j])) {

                    continue;
                }

                // Changes the state of the subject
                grid[i][j].changeState();

                // The subject is contagious
                if (grid[i][j].isContagious()) {
                	
            		List <Point> neighbors = getNeighbors(new Point (j, i));
                	for (Iterator<Point> it = neighbors.iterator(); it.hasNext(); ) {
            			Point neighbor = (Point) it.next();
            			
            			grid[i][j].contact(grid[neighbor.y][neighbor.x]);
                	}
                }
                randomMove (new Point (j, i));
                movedAlready.add(grid[i][j]);
                
            }
        }
            
    } // day()

    /**
     * Infected subject incubate. If nobody is infected then the simulation
     * ends.
     * 
     */
    public void night() {

        // End of simulation
        endOfSimulation = true;

        for (int i = 0; i < grid.length; i++) {

            for (int j = 0; j < grid[i].length; j++) {

                if (grid[i][j] == null) {

                    continue;
                }

                // Someone is still infected
                if (grid[i][j].isInfected()) {

                    endOfSimulation = false;
                }

                // Incubation
                grid[i][j].incubate();
            }
        }
    }

    /**
     * Returns a String composed of "-" and "+" to limit the border of the grid.
     * 
     * @return
     */
    public String lineSeparator() {

        // Default String
        String s = "";

        // Composition of "-" and "+" in function of the length of the grid
        for (int k = 0; k < grid[0].length; k++) {

            s = s.concat("+-----");
        }

        // Add the last "+" and a line separator
        s = s.concat("+".concat(System.getProperty("line.separator")));

        return s;
    }

    /**
     * @see Object#toString()
     * 
     */
    public String toString() {

        // Default String
        String s = "";

        // Add the top border of the grid
        s = s.concat(lineSeparator());

        for (int i = 0; i < grid.length; i++) {

            for (int j = 0; j < grid[i].length; j++) {

                if (grid[i][j] != null) {

                    // Add the type and the state of the subject
                    s = s.concat("| ".concat(grid[i][j].toString()).concat(" "));
                } else {

                    s = s.concat("|     ");
                }
            }

            // Add a bottom border to the grid
            s = s.concat("|".concat(System.getProperty("line.separator"))
                    .concat(lineSeparator()));
        }

        return s;
    }
}