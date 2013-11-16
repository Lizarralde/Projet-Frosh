/** @author Julien le Van Suu */

package subjects.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import subjects.Type;

public class TestType {
	@Test
	public void testToString()
	{
		Type t = Type.CHICKEN;
		assertEquals(t.toString(), "Chicken");
	}
}
