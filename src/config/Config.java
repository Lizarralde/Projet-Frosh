package config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Config is a class who can store and load the configuration of the simulation
 * using java.util.Properties.
 * 
 * @author Dorian LIZARRALDE
 * 
 */
public abstract class Config {

    /**
     * @see Properties
     */
    private static Properties property;

    /**
     * Stores the configuration.
     * 
     */
    public static void store() {

        // New properties
        Properties p = new Properties();

        try {

            // Grid properties
            p.setProperty("Grid.Width", "6");
            p.setProperty("Grid.Length", "12");
            p.setProperty("Grid.Infected", "24");

            // Ratio of subject
            p.setProperty("Ratio.Human", "40");
            p.setProperty("Ratio.Chicken", "15");
            p.setProperty("Ratio.Duck", "10");
            p.setProperty("Ratio.Pig", "15");

            // Days of incubation in function of the type and the state of the
            // subject
            p.setProperty("Human.Incubation.Sick", "2");
            p.setProperty("Human.Incubation.Contagious", "3");
            p.setProperty("Human.Incubation.Recovering", "3");

            p.setProperty("Chicken.Incubation.Sick", "3");
            p.setProperty("Chicken.Incubation.Contagious", "2");
            p.setProperty("Chicken.Incubation.Recovering", "3");

            p.setProperty("Duck.Incubation.Sick", "3");
            p.setProperty("Duck.Incubation.Contagious", "3");
            p.setProperty("Duck.Incubation.Recovering", "2");

            p.setProperty("Pig.Incubation.Sick", "2");
            p.setProperty("Pig.Incubation.Contagious", "3");
            p.setProperty("Pig.Incubation.Recovering", "3");

            p.setProperty("H1N1.Infection.Human.Human", "33");
            p.setProperty("H1N1.Infection.Pig.Human", "100");
            p.setProperty("H1N1.Infection.Pig.Pig", "33");

            p.setProperty("H5N1.Infection.Human.Human", "33");
            p.setProperty("H5N1.Infection.Chicken.Human", "33");
            p.setProperty("H5N1.Infection.Chicken.Chicken", "33");
            p.setProperty("H5N1.Infection.Chicken.Duck", "33");
            p.setProperty("H5N1.Infection.Duck.Human", "33");
            p.setProperty("H5N1.Infection.Duck.Chicken", "33");
            p.setProperty("H5N1.Infection.Duck.Duck", "33");

            // Mortality rate of a subject in function of his type
            p.setProperty("H1N1.Mortality.Human", "100");

            p.setProperty("H5N1.Mortality.Human", "33");

            // Creation of the config file
            p.store(new FileOutputStream("config.properties"), null);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * Returns the value of property.
     * 
     * @param s
     * @return
     */
    public static String getProperty(String s) {

        // Load only one time the config file
        if (property == null) {

            property = new Properties();

            try {

                property.load(new FileInputStream("config.properties"));
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        return property.getProperty(s);
    }
    
    public static void setProperty(Properties p) {
    	
    	if (property == null) {

    		property = p;
    	}
    }
}