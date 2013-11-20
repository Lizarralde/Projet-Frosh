package states;

import config.Config;
import subjects.Subject;

/**
 * Recovering is the state of a subject who incubates some days while contagious
 * and gets over the disease.
 * 
 * @author Dorian LIZARRALDE
 * 
 */
public class Recovering extends State {

    /**
     * @see State#changeState(Subject)
     * 
     */
    @Override
    public void changeState(Subject s) {

        // The subject incubates enough days to become Healthy
        if (s.getIncubationTime() >= Integer.parseInt(Config.getProperty(s
                .toString().concat(".Incubation.Recovering")))) {

            // The subject gets his incubation time reset
            s.setIncubationTime(0);

            // The subject loses his disease
            s.setDisease(null);

            // The subject becomes healthy
            s.setState(new Healthy());
        }
    }

    /**
     * @see Object#toString()
     * 
     */
    public String toString() {

        return "R";
    }
}