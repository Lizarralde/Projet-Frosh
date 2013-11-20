/** @author Julien Le Van Suu
 * Cette classe vérifie que les états se comportent bien
 */

package states.junit;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import states.*;
import subjects.*;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import config.Config;
import diseases.H1N1;

public class TestState {

    @BeforeClass
    /*
     * On crée un fichier de config a des fins de test c'est a dire avec des
     * valeurs extrêmes: 100, 0
     */
    public static void loadTestProperties() {

        Properties property = new Properties();

        try {

            property.load(new FileInputStream("config.properties.test"));
        } catch (IOException e) {

            e.printStackTrace();
        }

        Config.setProperty(property);
    }

    @Test
    public void changeState() {
        // On teste les changements d'état d'un pig (ne meurt jamais)
        Subject s = new Pig();
        State h = new Healthy();
        State si = new Sick();
        State c = new Contagious();
        State r = new Recovering();

        assertEquals(s.getState().toString(), "H");
        s.setDisease(new H1N1());
        h.changeState(s);
        assertEquals(s.getState().toString(), "S");
        si.changeState(s);
        // Si il n'a pas incubé il devrait rester sick quoi qu'il arrive
        assertEquals(s.getState().toString(), "S");

        for (int i = 0; i < Integer.parseInt(Config
                .getProperty("Pig.Incubation.Sick")); i++) {
            s.incubate();
        }
        si.changeState(s);

        assertEquals(s.getState().toString(), "C");

        for (int i = 0; i < Integer.parseInt(Config
                .getProperty("Pig.Incubation.Contagious")); i++) {
            s.incubate();
        }
        c.changeState(s);

        assertEquals(s.getState().toString(), "R");

        for (int i = 0; i < Integer.parseInt(Config
                .getProperty("Pig.Incubation.Recovering")); i++) {
            s.incubate();
        }
        r.changeState(s);

        assertEquals(s.getState().toString(), "H");

    }

    @Test
    public void testToString() {
        State si = new Sick();
        assertEquals(si.toString(), "S");
        State h = new Healthy();
        assertEquals(h.toString(), "H");
        State c = new Contagious();
        assertEquals(c.toString(), "C");
        State r = new Recovering();
        assertEquals(r.toString(), "R");
    }

}
