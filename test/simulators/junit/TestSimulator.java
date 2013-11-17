package simulators.junit;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import diseases.Disease;
import simulators.Simulator;
import states.Contagious;
import states.Sick;
import subjects.Subject;
import subjects.Type;

public class TestSimulator {
	
	Simulator simulator;
	
	@Before
	public void setup () {
		this.simulator = new Simulator();
		
		/*
		 * Creating test grid
		 * 	+-----+-----+-----+
			| HuH | PiC |     | 
			+-----+-----+-----+
			|     | DuS |     |
			+-----+-----+-----+
		 * 
		 */
		
		Subject grid [][] = new Subject[2][3];
		
		grid[0][0] = new Subject(Type.HUMAN);
		grid[0][1] = new Subject(Type.PIG);
		grid[1][1] = new Subject(Type.DUCK);
		
		grid[0][1].setDisease(Disease.H1N1);
		grid[1][1].setDisease(Disease.H5N1);
		
		grid[0][1].setState(new Contagious());
		grid[1][1].setState(new Sick());
		
		simulator.setGrid(grid);
		
	} // setup()
	
	@Test
	public void testMovePossible () {
		Subject s1 = simulator.getSubject(0, 0);
		
		System.out.println(simulator);
		
		// This cell is empty so we should be able to move there
		simulator.move(new Point(0, 0), new Point(0, 1));
		
		System.out.println(simulator);
		
		// Did we move ?
		assertSame ("Position not modified while moving to an empty spot", 
				s1,   simulator.getSubject(0, 1)); 
		
		// The previous spot should be empty ?
		assertSame ("Previous position not updated while moving to an empty spot", 
				null, simulator.getSubject(0, 0)); 
		
	} // testMovePossible()
	
	@Test
	public void testMoveNotPossible () {
		Subject s1 = simulator.getSubject(0, 0);
		Subject s2 = simulator.getSubject(1, 0);
		
		// s1 try to move where s2 is, and should not be able to
		simulator.move(new Point(0, 0), new Point(1, 0));
		
		// s1 and s2 should not have moved
		assertSame("An impossible movement change the subject in the original position of the moving subject",
				s1, simulator.getSubject(0, 0));
		assertSame("An impossible movement change subject in the occupied cell",
				s2, simulator.getSubject(1, 0));
		
	} // testMoveNotPossible()
	
} // class TestSimulator
