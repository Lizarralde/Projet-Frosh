package states;

import subjects.Subject;

/**
 * Healthy is the default state of a subject.
 * 
 * @author Dorian LIZARRALDE
 * 
 */
public class Healthy extends State {

    /**
     * @see State#changeState(Subject)
     * 
     */
    @Override
    public void changeState(Subject s) {

        // The subject is infected
        if (s.isInfected()) {

            // The subject becomes sick
            s.setState(new Sick());
        }
    }

    /**
     * @see Object#toString()
     * 
     */
    public String toString() {

        return "H";
    }
}