package states;

import subjects.Subject;

/**
 * State is an abstract class representing the state of a subject.
 * 
 * @author Dorian LIZARRALDE
 * 
 */
public abstract class State {

    /**
     * Defines if the state is contagious.
     * 
     */
    private boolean contagious;

    /**
     * Sets the value of contagious.
     * 
     * @param b
     */
    public void setContagious(boolean b) {

        contagious = b;
    }

    /**
     * Returns if the state is contagious.
     * 
     * @return
     */
    public boolean isContagious() {

        return contagious;
    }

    /**
     * Changes the state of a subject when conditions are completed.
     * 
     * @param s
     */
    public abstract void changeState(Subject s);
}