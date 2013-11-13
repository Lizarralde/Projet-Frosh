/**
 * 
 * Test if the config file is well initialized
 * 
 * @author Julien Le Van Suu
 */

package config.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import config.Config;

public class TestConfig {

	@Test
	public void testGetProperty() {
		Config.store();
		int width = Integer.parseInt(Config.getProperty("Grid.Width"));
		int ratio = Integer.parseInt(Config.getProperty("Ratio.Human"));
		int inc = Integer.parseInt(Config.getProperty("Human.Incubation.Sick"));
		assertEquals(width,6);
		assertEquals(ratio,40);
		assertEquals(inc,2);
		
	}

}
