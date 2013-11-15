/* Ne pas faire de tests ici */
package simulators;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
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
     * Tells if the given coordinates are a valid coordinates within the grid.
     * @param point : the coordinates to test
     * @return true if the given coordinates are valid, false otherwise
     */
	private boolean isValid(Point point) {
		// Check if a given point has valid coordinates (inside the grid)

		if (point.x < 0 || point.y < 0 || point.y >= grid.length || point.x >= grid[0].length)
			return false;

		return true;

	} // isValid()

    /**
     * get a list of all the coordinates considered in the neighborhood of a given coordinates
     * @param point : the coordinates we want to get the neighborhood
     * @return a List<Point> representing the neighborhood
     */
    public List<Point> getNeighborhood(Point point) {
		// get the neighborhood of a given point in the grid

		List<Point> neighborhood = new ArrayList<Point>();

		// checking cells left from point

		for (int offY = -1; offY <= 1; ++offY) {
			Point neighbor = new Point(point.x - 1, point.y + offY);
			if (isValid(neighbor))
				neighborhood.add(neighbor);
		}

		// checking cells right from point

		for (int offY = -1; offY <= 1; ++offY) {
			Point neighbor = new Point(point.x + 1, point.y + offY);
			if (isValid(neighbor))
				neighborhood.add(neighbor);
		}

		// checking cells up and down

		Point neighborUp = new Point(point.x, point.y + 1);
		Point neighborDown = new Point(point.x, point.y - 1);

		if (isValid(neighborUp))
			neighborhood.add(neighborUp);
		if (isValid(neighborDown))
			neighborhood.add(neighborDown);

		return neighborhood;

	} // getNeighborhood()
    
	
    /**
     * Attempt to move a subject to a specific location, fails if the cell
     * where we want the subject to move is occupied already.
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
     * 
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

                    // Cross pattern
                    if (j != 0) {

                        grid[i][j].contact(grid[i][j - 1]);
                    }

                    if (i != grid.length - 1) {

                        grid[i][j].contact(grid[i + 1][j]);
                    }

                    if (j != grid[i].length - 1) {

                        grid[i][j].contact(grid[i][j + 1]);
                    }

                    if (i != 0) {

                        grid[i][j].contact(grid[i - 1][j]);
                    }
                }
                
                randomMove (new Point (j, i));
            }
        }
    }

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