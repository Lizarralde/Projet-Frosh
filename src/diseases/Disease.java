package diseases;

/**
 * Disease is a enumeration representing the disease of a subject.
 * 
 * @author Dorian LIZARRALDE
 * 
 */
public enum Disease {

    H1N1("H1N1"), H5N1("H5N1");

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
    Disease(String s) {

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