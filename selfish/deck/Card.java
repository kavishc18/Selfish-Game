package selfish.deck;

import java.io.Serializable;

/**
 * 
 * Represents a single card with a name and description.
 * 
 * @author Kavish Chawla
 * 
 * @ selfish
 * 
 * @version 32.0
 */
public class Card implements Serializable, Comparable<Card> {
    private String name;
    private String description;
    private static final long serialVersionUID = 1L;

    /**
     * 
     * Constructs a card with the given name and description.
     * 
     * @param name        the name of the card
     * @param description the description of the card
     */
    public Card(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * 
     * Returns a string representation of the card.
     * 
     * @return the name of the card
     */
    public String toString() {
        return name;
    }

    /**
     * 
     * Constructs an empty card.
     */
    public Card() {
    }

    @Override
    public int compareTo(Card otherCard) {
        int comparison = this.name.compareTo(otherCard.toString());
        if (comparison == 0)
            return 0;
        else if (comparison > 0)
            return 1;
        else
            return -1;
    }

    /**
     * 
     * Retrieves the card's description.
     * 
     * @return the description of the card
     */
    public String getDescription() {
        return description;
    }

}