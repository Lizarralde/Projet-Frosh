package states;

import config.Config;
import subjects.Subject;

/**
 * Contagious is the state of a subject who incubates some days while sick.
 * 
 * @author Dorian LIZARRALDE
 * 
 */
public class Contagious extends State {

    /**
     * Default constructor.
     * 
     */
    public Contagious() {

        // Contagious is a contagious state
        setContagious(true);
    }

    /**
     * @see State#changeState(Subject)
     * 
     */
    @Override
    public void changeState(Subject s) {

        // The subject incubates enough days to become recovering or dead
        if (s.getIncubationTime() >= Integer.parseInt(Config.getProperty(s
                .toString().concat(".Incubation.Contagious")))) {

            // The subject gets his incubation time reset
            s.setIncubationTime(0);

            // The subject can die of the disease
            if (Config.getProperty(s.getDisease().toString()
                    .concat(".Mortality." + s.toString())) != null) {

                // The subject dies
                if ((int) (100.0 * Math.random()) + 1 <= Integer
                        .parseInt(Config.getProperty(s.getDisease().toString()
                                .concat(".Mortality." + s.toString())))) {

                    // The subject becomes dead
                    s.setState(new Dead());

                    return;
                }
            }

            // The subject becomes recovering
            s.setState(new Recovering());
        }
    }

    /**
     * @see Object#toString()
     * 
     */
    public String toString() {

        return "C";
    }
}