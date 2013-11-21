package subjects;

/**
 * Type is an enumeration representing the class of an element.
 * 
 * @author Dorian LIZARRALDE
 * 
 */
public enum Type {

    HUMAN(Human.class.getName()), CHICKEN(Chicken.class.getName()), DUCK(
            Duck.class.getName()), PIG(Pig.class.getName());

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