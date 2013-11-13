import config.Config;

import simulators.Simulator;

/**
 * Program is the entry point of the simulation.
 * 
 * @author Dorian LIZARRALDE
 * 
 */
public abstract class Program {

    /**
     * The main.
     * 
     * @param args
     */
    public static void main(String[] args) {

        // Creation of the config file
        Config.store();

        // Simulator
        Simulator s = new Simulator();

        // Start of the simulation
        s.run();
    }
}