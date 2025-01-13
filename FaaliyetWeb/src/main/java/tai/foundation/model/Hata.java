package tai.foundation.model;

/**
 * Insert the type's description here.
 * Creation date: (28.05.2002 01:26:57)
 * @author: Yusuf Donmez
 */
public class Hata extends Exception implements java.io.Serializable {
    
    // Add the serialVersionUID to fix the warning
    private static final long serialVersionUID = 1L;

    /**
     * Hata constructor comment.
     */
    public Hata() {
        super();
    }

    /**
     * Hata constructor comment.
     * @param s java.lang.String
     */
    public Hata(String s) {
        super(s);
    }
}
