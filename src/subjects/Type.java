package subjects;

/**
 * Type is an enumeration representing the type of a subject.
 * 
 * @author Dorian LIZARRALDE
 * 
 */
public enum Type {

    HUMAN("Human"), CHICKEN("Chicken"), DUCK("Duck"), PIG("Pig");

    /**
     * Defines the name of the type.
     * 
     */
    private String name;

    /**
     * Default constructor.
     * 
     * @param s
     */
    Type(String s) {

        name = s;
    }

    /**
     * @see Object#toString()
     * 
     */
    public String toString() {

        return name;
    }
}