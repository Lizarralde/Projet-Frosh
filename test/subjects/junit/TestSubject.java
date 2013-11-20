/**
 * @author Julien Le Van Suu
 * Cette classe vérifie que le sujet se comporte bien
 */
package subjects.junit;

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
import diseases.H5N1;

public class TestSubject {

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

    /* On teste le changement d'état d'un humain qui meurt a coup sûr */
    @Test
    public void testChangeStateH() {
        Subject s = new Human();
        s.setState(new Healthy());

        // Normalement ici il n'a pas changé pas d'état car il n'a pas de
        // maladie
        s.changeState();
        assertEquals(s.getState().toString(), "H");

        // Ici il devrait changer d'état
        s.setDisease(new H1N1());
        s.changeState();
        assertEquals(s.getState().toString(), "S");

        // Il ne devrait pas changer d'état car on ne lui a pas fait incuber
        s.changeState();
        assertEquals(s.getState().toString(), "S");

        // Simulation d'une incubation
        for (int i = 0; i < Integer.parseInt(Config
                .getProperty("Human.Incubation.Sick")); i++) {
            s.incubate();
        }

        // On lui a fait incuber donc là il devrait changer d'état sans souci
        s.changeState();
        assertEquals(s.getState().toString(), "C");

        // Si on lui fait changer d'état ici il ne devrait pas changer d'état
        s.changeState();
        assertEquals(s.getState().toString(), "C");

        // On lui fait incuber
        for (int i = 0; i < Integer.parseInt(Config
                .getProperty("Human.Incubation.Contagious")); i++) {
            s.incubate();
        }

        // La il devrait changer d'état
        // Vu qu'on a mis la mortalité à 100 il meurt a coup sûr
        s.changeState();
        if (Config.getProperty("H1N1.Mortality.Human").equals("100")) {
            assertEquals(s.getState().toString(), "D");
        }
    }

    @Test
    public void testChangeState() {
        Subject s = new Human();
        s.setState(new Healthy());

        // Normalement ici il n'a pas changé pas d'état car il n'a pas de
        // maladie
        s.changeState();
        assertEquals(s.getState().toString(), "H");

        // Ici il devrait changer d'état
        s.setDisease(new H1N1());
        s.changeState();
        assertEquals(s.getState().toString(), "S");

        // Il ne devrait pas changer d'état car on ne lui a pas fait incuber
        s.changeState();
        assertEquals(s.getState().toString(), "S");

        // Simulation d'une incubation
        for (int i = 0; i < Integer.parseInt(Config
                .getProperty("Human.Incubation.Sick")); i++) {
            s.incubate();
        }

        // On lui a fait incuber donc là il devrait changer d'état sans souci
        s.changeState();
        assertEquals(s.getState().toString(), "C");

        // Si on lui fait changer d'état ici il ne devrait pas changer d'état
        s.changeState();
        assertEquals(s.getState().toString(), "C");

        // On lui fait incuber
        for (int i = 0; i < Integer.parseInt(Config
                .getProperty("Human.Incubation.Contagious")); i++) {
            s.incubate();
        }

        // La il devrait changer d'état
        // Vu qu'on a mis la mortalité à 100 il meurt a coup sûr
        s.changeState();
        if (Config.getProperty("H1N1.Mortality.Human").equals("100")) {
            assertEquals(s.getState().toString(), "D");
        }
    }

    /*
     * On teste le contact entre deux individus Dans le fichier properties on a
     * mis le ratio de contagion a 100 et 0 respectivement
     */
    @Test
    public void testContact() {

        Subject s1 = new Human();
        Subject s2 = new Pig();

        // On simule le passe a l'état contagieux de s2
        s2.setDisease(new H1N1());
        s2.changeState();
        for (int i = 0; i < Integer.parseInt(Config
                .getProperty("Human.Incubation.Sick")); i++) {
            s2.incubate();
        }
        s2.changeState();

        // S2 entre en contact avec s1
        s2.contact(s1);

        // Normalement S1 est contaminé
        assertEquals(s2.getState().toString(), "C");
        assertEquals(s1.getState().toString(), "S");

        /** ------------------------------------ */

        // Même chose
        Subject s3 = new Duck();
        Subject s4 = new Chicken();
        s4.setDisease(new H5N1());

        s4.changeState();
        for (int i = 0; i < Integer.parseInt(Config
                .getProperty("Chicken.Incubation.Sick")); i++) {
            s4.incubate();
        }
        s4.changeState();
        s4.contact(s3);
        // S1 dans ce cas n'est pas contaminé a coup sûr
        assertEquals(s3.getState().toString(), "H");
        assertEquals(s4.getState().toString(), "C");
    }
}
