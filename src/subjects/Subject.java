package subjects;

import config.Config;

import diseases.Disease;
import states.Healthy;
import states.State;

/**
 * Subject is a class representing an element of the simulation.
 * 
 * @author Dorian LIZARRALDE
 * 
 */
public class Subject {

    /**
     * Defines the state of the subject.
     * 
     */
    private State state;

    /**
     * Defines the disease of the subject.
     * 
     */
    private Disease disease;

    /**
     * Defines the time of incubation of the subject.
     * 
     */
    private int incubationTime;

    /**
     * Default constructor.
     * 
     * @param s
     */
    public Subject() {

        // The subject starts Healthy
        state = new Healthy();
    }

    /**
     * Returns the value of state.
     * 
     * @return
     */
    public State getState() {

        return state;
    }

    /**
     * Sets the value of state.
     * 
     * @return
     */
    public void setState(State s) {

        state = s;
    }

    /**
     * @see State#isContagious()
     * 
     * @return
     */
    public boolean isContagious() {

        return state.isContagious();
    }

    /**
     * Returns the value of disease.
     * 
     * @return
     */
    public Disease getDisease() {

        return disease;
    }

    /**
     * Sets the value of disease.
     * 
     * @return
     */
    public void setDisease(Disease d) {

        disease = d;
    }

    /**
     * Returns if the subject is infected.
     * 
     * @return
     */
    public boolean isInfected() {

        // The subject is infected
        if (disease != null) {

            return true;
        }

        return false;
    }

    /**
     * Returns the value of incubationTime.
     * 
     * @return
     */
    public int getIncubationTime() {

        return incubationTime;
    }

    /**
     * Sets the value of incubationTime.
     * 
     * @return
     */
    public void setIncubationTime(int i) {

        incubationTime = i;
    }

    /**
     * @see State#changeState(Subject)
     * 
     */
    public void changeState() {

        state.changeState(this);
    }

    /**
     * The subject incubates if he's infected.
     * 
     */
    public void incubate() {

        // The subject is infected
        if (disease != null) {

            incubationTime++;
        }
    }

    /**
     * The subject meets his neighbor and infects him if he's contagious
     * depending on the infection rate of his disease.
     * 
     * @param s
     */
    public void contact(Subject s) {

        if (s == null) {

            return;
        }

        // The neighbor is not infected
        if (!s.isInfected()) {

            // The neighbor can be infected
            if (Config.getProperty(disease.toString().concat(".Infection.")
                    .concat(toString()).concat(".").concat(s.toString())) != null) {

                // The neighbor gets infected
                if ((int) (100.0 * Math.random()) + 1 <= Integer
                        .parseInt(Config.getProperty(disease.toString()
                                .concat(".Infection.").concat(toString())
                                .concat(".").concat(s.toString())))) {

                    // The neighbor gets the disease of the subject
                    s.setDisease(disease);

                    // The neighbor becomes Sick
                    s.changeState();
                }
            }
        }
    }
}