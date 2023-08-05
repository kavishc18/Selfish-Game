package selfish.deck;
import selfish.GameException;
import java.io.FileNotFoundException;



/**
 * I have given the spacedeck here
 * 
 * @author Kavish Chawla
 * @ selfish
 * @version 32.0
 */

public class SpaceDeck extends Deck {
    /**
     * constructor of class
     */
    public SpaceDeck() {

    }

    private static final long serialVersionUID = 1L;
    /**
     * this is representing asteroid feild
     */
    public static final String ASTEROID_FIELD = "Asteroid field";

    /**
     * this is representing Gravitational Anomaly
     */
    public static final String GRAVITATIONAL_ANOMALY = "Gravitational anomaly";
    /**
     * this is representing Cosmic Radiation
     */
    public static final String COSMIC_RADIATION = "Cosmic radiation";

    /**
     * this is representing Hyperspace
     */
    public static final String HYPERSPACE = "Hyperspace";
    /**
     * this is representing Meteoroid
     */
    public static final String METEOROID = "Meteoroid";
    /**
     * this is representing Mysterious Nebula
     */
    public static final String MYSTERIOUS_NEBULA = "Mysterious nebula";
    /**
     * this is representing Solar Flare
     */
    public static final String SOLAR_FLARE = "Solar flare";
    /**
     * this is representing Wormhole
     */
    public static final String WORMHOLE = "Wormhole";
    /**
     * this is representing Useful Junk
     */
    public static final String USEFUL_JUNK = "Useful junk";
    /**
     * this is representing Blank Space
     */
    public static final String BLANK_SPACE = "Blank space";

    /**
     * constructor with parameter
     * 
     * @param p gives out path
     * @throws GameException represents exception
     */
    public SpaceDeck(String p) throws GameException {
        try {
            add(super.loadCards(p));
        } catch (Exception e) {
            throw new GameException("OOPS File error!", new FileNotFoundException());
        }
    }
}
