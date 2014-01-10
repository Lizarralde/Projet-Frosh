package simulators.junit;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import diseases.H1N1;
import diseases.H5N1;
import foxAndRabbit.Field;
import simulators.Simulator;
import states.Contagious;
import states.Sick;
import subjects.Duck;
import subjects.Human;
import subjects.Pig;
import subjects.Subject;

/**
 * 
 * @author lecpie, le van suu
 * 
 */
public class TestSimulator {

    Simulator simulator;

    @Before
    public void setup() {
        this.simulator = new Simulator();

        /*
         * Creating test grid 
         * +-----+-----+-----+ 
         * | HuH | PiC |     |
         * +-----+-----+-----+ 
         * |     | DuS |     | 
         * +-----+-----+-----+
         */

        Field grid = new Field(2, 3);

        grid.place(new Human(), 0, 0);
        grid.place(new Pig(), 0, 1);;
        grid.place(new Duck(), 1, 1);;

        ((Subject) grid.getObjectAt(0, 1)).setDisease(new H1N1());
        ((Subject) grid.getObjectAt(1, 1)).setDisease(new H5N1());

        ((Subject) grid.getObjectAt(0, 1)).setState(new Contagious());
        ((Subject) grid.getObjectAt(1, 1)).setState(new Sick());

        simulator.setGrid(grid);

    } // setup()
	
	/*
	@author Julien Le Van Suu
	@Test
	public void TestRun()
	{
		Simulator s = new Simulator();
		assertTrue(s.endOfSimulation);
	}
	
	@author Julien Le Van Suu
	@Test
	public void getAxis()
	{
		Simulator s = new Simulator();
		
		assertEquals(s.getAxis(8, 5),3);
	}
	
	@author Julien Le Van Suu
	@Test
	public void TestGetCoords()
	{
		Simulator s = new Simulator();
		assertEquals(s.getCoords(3, 4), new Point(3,4)); 
	}
	*/
	
	/* @author Julien Le Van Suu */
	@Test
	public void testGetNeighborhood()
	{
		Simulator s = new Simulator();
		java.util.List<Point> liste=s.getNeighborhood(new Point(3,4));
		assertTrue(liste.contains(new Point(2,3)));
		assertTrue(liste.contains(new Point(2,4)));
		assertTrue(liste.contains(new Point(2,5)));
		
		assertTrue(liste.contains(new Point(3,5)));
		assertTrue(liste.contains(new Point(3,3)));
		assertTrue(liste.contains(new Point(4,3)));
		assertTrue(liste.contains(new Point(4,4)));
		assertTrue(liste.contains(new Point(4,5)));
	}
	
	/*
	@author Julien Le Van Suu 
	@Test
	public void testMove()
	{
		
		Simulator s = new Simulator();
		Subject a = s.grid[2][3];
		s.move(new Point(2,3),new Point(3,3));
		assertGrid(s.grid[3][3] == a);
	}
	*/
	
    @Test
    public void testMovePossible() {
        Subject s1 = simulator.getSubject(0, 0);

        System.out.println(simulator);

        // This cell is empty so we should be able to move there
        simulator.move(new Point(0, 0), new Point(0, 1));

        System.out.println(simulator);

        // Did we move ?
        assertSame("Position not modified while moving to an empty spot", s1,
                simulator.getSubject(0, 1));

        // The previous spot should be empty ?
        assertSame(
                "Previous position not updated while moving to an empty spot",
                null, simulator.getSubject(0, 0));

    } // testMovePossible()

    @Test
    public void testMoveNotPossible() {
        Subject s1 = simulator.getSubject(0, 0);
        Subject s2 = simulator.getSubject(1, 0);

        // s1 try to move where s2 is, and should not be able to
        simulator.move(new Point(0, 0), new Point(1, 0));

        // s1 and s2 should not have moved
        assertSame(
                "An impossible movement change the subject in the original position of the moving subject",
                s1, simulator.getSubject(0, 0));
        assertSame(
                "An impossible movement change subject in the occupied cell",
                s2, simulator.getSubject(1, 0));

    } // testMoveNotPossible()



} // class TestSimulator
