package simulators;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import config.Config;
import diseases.H1N1;
import diseases.H5N1;
import foxAndRabbit.Field;
import foxAndRabbit.GraphView;
import foxAndRabbit.GridView;
import foxAndRabbit.SimulatorView;
import states.Dead;
import subjects.Chicken;
import subjects.Duck;
import subjects.Human;
import subjects.Pig;
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
    private Field grid;

    /**
     * Defines when the simulation stops.
     * 
     */
    private boolean endOfSimulation;

    private List<SimulatorView> views;

    /**
     * Default constructor.
     * 
     * @param s
     */
    public Simulator() {

        // New random grid
        grid = randomGrid();

        initViews();

        // Infects a number of subject
        infect();
    }

    /**
     * @author Dorian LIZARRALDE
     */
    private void initViews() {

        views = new ArrayList<SimulatorView>();

        SimulatorView view = new GridView(grid.getDepth(), grid.getWidth());

        view.setColor(Human.class, Color.BLUE);

        view.setColor(Chicken.class, Color.GREEN);

        view.setColor(Duck.class, Color.MAGENTA);

        view.setColor(Pig.class, Color.ORANGE);

        views.add(view);

        SimulatorView graphe = new GraphView(500, 150, 500);

        graphe.setColor(Human.class, Color.BLUE);

        graphe.setColor(Chicken.class, Color.GREEN);

        views.add(graphe);
    }

    /**
     * Set the new Subject grid
     * 
     * @param grid
     *            : the new grid
     */
    public void setGrid(Field grid) {
        this.grid = grid;

    } // setGrid()

    /**
     * get the subject grid
     * 
     * @return the simulator's Subject grid
     * @author lecpie
     */
    public Field getGrid() {
        return grid;

    } // getGrid()

    /**
     * get the subject located in the grid at the given coordinates
     * 
     * @param x
     *            : the x part of the coordinates
     * @param y
     *            : the y part of the coordinates
     * @return the subject in the x and y coordinates in the grid
     */
    public Subject getSubject(int x, int y) {
        return (Subject) grid.getObjectAt(y, x);

    } // getSubject()

    /**
     * Generates a random grid.
     * 
     * @return
     */
    private Field randomGrid() {

        // Length of the grid
        int length = Integer.parseInt(Config.getProperty("Grid.Length"));

        // Width of the grid
        int width = Integer.parseInt(Config.getProperty("Grid.Width"));

        // New grid
        Field s = new Field(length, width);

        // List of subject
        List<Subject> l = new ArrayList<Subject>();

        String str;

        // Generates a number of subject in function of the ratio
        for (Type t : Type.values()) {

            str = t.toString();

            // Ratio of the actual subject
            int ratio = Integer.parseInt(Config.getProperty("Ratio"
                    + str.substring(str.lastIndexOf("."))));

            // New subject add to the list
            for (int i = 0; i < length * width * ratio / 100.; i++) {

                try {

                    l.add((Subject) Class.forName(t.toString()).newInstance());
                } catch (InstantiationException | IllegalAccessException
                        | ClassNotFoundException e) {

                    e.printStackTrace();
                }
            }
        }

        int size = l.size();

        // Fills the list with empty spaces
        for (int i = 0; i < length * width - size; i++) {

            l.add(null);
        }

        // Shuffles the list
        Collections.shuffle(l);

        // Fills the grid
        for (int i = 0; i < width; i++) {

            for (int j = 0; j < length; j++) {

                s.place(l.get(j * width + i), j, i);
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
            int x = (int) (grid.getWidth() * Math.random());

            // Position y
            int y = (int) (grid.getDepth() * Math.random());

            // Infects the subject
            if (getSubject(x, y) != null) {

                switch (getSubject(x, y).toString()) {

                // Human starts healthy
                case "Human":
                    break;

                case "Chicken":
                    getSubject(x, y).setDisease(new H5N1());
                    break;

                case "Duck":
                    getSubject(x, y).setDisease(new H5N1());
                    break;

                case "Pig":
                    getSubject(x, y).setDisease(new H1N1());
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

            display(days);

            // Displays the grid
            // System.out.println(toString());

            // Last step of the day
            night();

            // Displays current number of days
            // System.out.println("Actual Day : " + days);

            // Wait for continue or displays end of simulation
            /*
             * if (!endOfSimulation) {
             * 
             * System.out.println("Press any key to continue");
             * 
             * new Scanner(System.in).nextLine(); } else {
             * 
             * System.out.println("End of the Simulation"); }
             */

            // Increments number of days
            days++;
        }
    }

    /**
     * @author Dorian LIZARRALDE
     */
    private void display(int days) {

        for (SimulatorView view : views) {

            view.showStatus(days, grid);
        }
    }

    /**
     * 
     * @param a
     *            : a number representing an axis like x or y
     * @param mod
     *            : the axis length
     * @return an integer representing the converted axis of the parameter a
     * 
     * @author lecpie
     */
    private int getAxis(int a, int mod) {
        a %= mod;
        if (a < 0)
            a += mod;

        return a;

    } // getAxis()

    /**
     * 
     * @param x
     *            : the x part of the coordinates
     * @param y
     *            : the y part of the coordinates
     * @return the converted coordinates with the getAxis() function
     * 
     * @author lecpie
     */
    private Point getCoords(int x, int y) {
        x = getAxis(x, grid.getWidth());
        y = getAxis(y, grid.getDepth());

        return new Point(x, y);

    } // getCoords()

    /**
     * Get a list of all the coordinates considered in the neighborhood of a
     * given coordinates. A neighbor cell can be only one cell away on the same
     * line, same column and on the same diagonals.
     * 
     * @param point
     *            : the coordinates we want to get the neighborhood
     * @return a List<Point> representing the neighborhood
     * 
     * @author lecpie
     */
    public List<Point> getNeighborhood(Point point) {

        List<Point> neighborhood = new ArrayList<Point>();

        neighborhood.add(getCoords(point.x - 1, point.y - 1));
        neighborhood.add(getCoords(point.x - 1, point.y));
        neighborhood.add(getCoords(point.x - 1, point.y + 1));

        neighborhood.add(getCoords(point.x + 1, point.y - 1));
        neighborhood.add(getCoords(point.x + 1, point.y));
        neighborhood.add(getCoords(point.x + 1, point.y + 1));

        neighborhood.add(getCoords(point.x, point.y + 1));
        neighborhood.add(getCoords(point.x, point.y - 1));

        return neighborhood;

    } // getNeighborhood()

    /**
     * get a all the living neighbors of the given coordinates
     * 
     * @param point
     *            : the coordinates we need to get the living neighbors.
     * @return a List of Points, each Point giving the coordinates of a living
     *         neighbor
     * 
     * @author lecpie
     */
    public List<Point> getNeighbors(Point point) {
        List<Point> neighbors = getNeighborhood(point);

        for (Iterator<Point> it = neighbors.iterator(); it.hasNext();) {
            Point neighbor = (Point) it.next();

            if (getSubject(neighbor.x, neighbor.y) == null)
                it.remove();
        }

        return neighbors;

    } // getNeighbors()

    /**
     * Attempt to move a subject to a specific location, fails if the cell where
     * we want the subject to move is occupied already.
     * 
     * @author lecpie
     * 
     * @param orig
     *            : the coordinates of the subject to move
     * @param dest
     *            : the coordinates where we want the subject to move
     * @return true if the move was successful, false otherwise
     */
    public boolean move(Point orig, Point dest) {

        // Avoid to move where someone already is

        if (getSubject(dest.x, dest.y) != null)
            return false;

        grid.place(getSubject(orig.x, orig.y), dest.y, dest.x);
        grid.place(null, orig.y, orig.x);

        return true;

    } // move()

    /**
     * Try to move a subject to a random location within it's neighborhood. The
     * subject will not move if he wants to go where someone already is.
     * 
     * @param orig
     *            : the coordinates of the subject we want to randomly move
     * @return true if the subject moved, false otherwise
     */
    public boolean randomMove(Point orig) {

        List<Point> neighborhood = getNeighborhood(orig);

        return move(orig,
                neighborhood.get((int) (Math.random() * neighborhood.size())));

    } // radomMove()

    /**
     * Changes the state of the subject in the grid. Contagious subject infect
     * their neighbor.
     * 
     * @author Dorian LIZARRALDE, lecpie
     */
    public void day() {

        List<Subject> movedAlready = new ArrayList<Subject>();

        for (int i = 0; i < grid.getWidth(); i++) {

            for (int j = 0; j < grid.getDepth(); j++) {

                if (getSubject(i, j) == null
                        || movedAlready.contains(getSubject(i, j))) {

                    continue;
                }

                // Changes the state of the subject
                getSubject(i, j).changeState();

                // The subject is contagious
                if (getSubject(i, j).isContagious()) {

                    List<Point> neighbors = getNeighbors(new Point(i, j));
                    for (Iterator<Point> it = neighbors.iterator(); it
                            .hasNext();) {
                        Point neighbor = (Point) it.next();

                        getSubject(i, j).contact(
                                getSubject(neighbor.x, neighbor.y));
                    }
                }
                randomMove(new Point(i, j));
                movedAlready.add(getSubject(i, j));

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

        for (int i = 0; i < grid.getWidth(); i++) {

            for (int j = 0; j < grid.getDepth(); j++) {

                if (getSubject(i, j) == null) {

                    continue;
                }

                if (getSubject(i, j).getState() instanceof Dead) {

                    grid.place(null, j, i);

                    continue;
                }

                // Someone is still infected
                if (getSubject(i, j).isInfected()) {

                    endOfSimulation = false;
                }

                // Incubation
                getSubject(i, j).incubate();
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
        for (int k = 0; k < grid.getWidth(); k++) {

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

        Subject sub;

        for (int j = 0; j < grid.getDepth(); j++) {

            for (int i = 0; i < grid.getWidth(); i++) {

                sub = getSubject(i, j);

                if (sub != null) {

                    // Add the type and the state of the subject
                    s = s.concat("| ".concat(sub.toString().substring(0, 2))
                            .concat(sub.getState().toString()).concat(" "));
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