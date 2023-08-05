package selfish.deck;

/**
 * implementation and structre of Oxygen class is shown
 * 
 * @author Kavish Chawla
 * @ selfish
 * @version 32.0
 */
public class Oxygen extends Card {
    private static final long serialVersionUID = 1L;
    private int value;

    /**
     * represents the parameter constructor
     * 
     * @param v represents value of card
     */
    public Oxygen(int v) {
        super();
        this.value = v;
    }

    /**
     * converts to string
     * 
     * @return oxygen name with value
     */
    public String toString() {
        return "Oxygen(" + value + ")";
    }

    /**
     * gets the value
     * 
     * @return current oxygen card value
     */
    public int getValue() {
        return value;
    }

    @Override
    public int compareTo(Card d) {
        String card = d.toString();
        int val = 0;
        val = card.equals(GameDeck.OXYGEN_2) ? 2 : val;
        val = card.equals(GameDeck.OXYGEN_1) ? 1 : val;
        return this.value == val ? 0 : (this.value > val ? 1 : -1);
    }

}
