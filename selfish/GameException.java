package selfish;

/**
 * 
 * An exception class for representing errors that can occur during a game.
 *
 * @author Kavish Chawla
 * @version 32.0
 */
public class GameException extends Exception {
    /**
     * 
     * Constructs a new GameException with the given error message.
     * 
     * @param message The error message.
     */

    public GameException(String message) {
        super(message);
    }

    /**
     * 
     * Constructs a new GameException with the given error message and cause.
     * 
     * @param message The error message.
     * @param cause   The cause of the error.
     */
    public GameException(String message, Throwable cause) {
        super(message, cause);
    }

}
