/**
 * 
 */
package diseases.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import diseases.Disease;

/**
 * @author Julien Le Van Suu
 *
 */
public class TestDisease {
	@Test
	public void testToString()
	{
		Disease d = Disease.H1N1;
		assertEquals("H1N1",d.toString());
	}
}
