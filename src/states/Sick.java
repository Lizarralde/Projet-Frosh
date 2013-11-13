package states;

import config.Config;
import subjects.Subject;

/**
 * Sick is the state of a subject who gets infected.
 * 
 * @author Dorian LIZARRALDE
 * 
 */
public class Sick extends State {

    /**
     * @see State#changeState(Subject)
     * 
     */
    @Override
    public void changeState(Subject s) {

        // The subject incubates enough days to become contagious
        if (s.getIncubationTime() >= Integer.parseInt(Config.getProperty(s
                .getType().toString().concat(".Incubation.Sick")))) {

            // The subject gets his incubation time reset
            s.setIncubationTime(0);

            // The subject becomes contagious
            s.setState(new Contagious());
        }
    }

    /**
     * @see Object#toString()
     * 
     */
    public String toString() {

        return "S";
    }
}