/**
 * 
 */
package diseases.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import diseases.Disease;
import diseases.H1N1;

/**
 * @author Julien Le Van Suu
 * 
 */
public class TestDisease {
    @Test
    public void testToString() {
        Disease d = new H1N1();
        assertEquals("H1N1", d.toString());
    }
}
