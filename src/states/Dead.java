package states;

import subjects.Subject;

/**
 * Dead is the state of a subject who incubates some days while contagious and
 * gets not over the disease.
 * 
 * @author Dorian LIZARRALDE
 * 
 */
public class Dead extends State {

    /**
     * @see State#changeState(Subject)
     * 
     */
    @Override
    public void changeState(Subject s) {

        // The subject shouldn't be infected
        s.setDisease(null);
    }

    /**
     * @see Object#toString()
     * 
     */
    public String toString() {

        return "D";
    }
}