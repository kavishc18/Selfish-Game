package selfish.deck;

import selfish.GameException;

import java.util.ArrayList;

import java.io.FileNotFoundException;

/**
 * Represents a GameDeck containing various game-specific cards.
 * 
 * @author Kavish Chawla
 * @ selfish
 * @version 32.0
 */

public class GameDeck extends Deck {
    /**
     * this represents Hack Suit
     */
    public static final String HACK_SUIT = "Hack suit";
    /**
     * this represents Hole in Suit
     */
    public static final String HOLE_IN_SUIT = "Hole in suit";
    /**
     * this represents Laser Blast
     */
    public static final String LASER_BLAST = "Laser blast";
    /**
     * this represents Oxygen
     */
    public static final String OXYGEN = "Oxygen";
    /**
     * this represents Oxygen 1
     */
    public static final String OXYGEN_1 = "Oxygen(1)";
    /**
     * this represents Oxygen 2
     */
    public static final String OXYGEN_2 = "Oxygen(2)";
    /**
     * this represents Oxygen Siphon
     */
    public static final String OXYGEN_SIPHON = "Oxygen siphon";
    /**
     * this represents Rocket Booster
     */
    public static final String ROCKET_BOOSTER = "Rocket booster";
    /**
     * this represents Shield
     */
    public static final String SHIELD = "Shield";
    /**
     * this represents Tether
     */
    public static final String TETHER = "Tether";
    /**
     * this represents Tractor Beam
     */
    public static final String TRACTOR_BEAM = "Tractor beam";
    private static final long serialVersionUID = 1L;

    /**
     * drawOxygen
     * 
     * @param p of the amount of oxygen
     * @return the card of oxygen of the value provided
     */
    public Oxygen drawOxygen(int p) {
        if (this.size() == 0)
            throw new IllegalStateException("Deck is empty!");
        ArrayList<Card> crd = new ArrayList<Card>();
        Card dc = null;
        boolean flag = false;
        while (size() > 0) {
            dc = draw();
            if ((dc.toString().equals(OXYGEN_1) && p == 1) || (dc.toString().equals(OXYGEN_2) && p == 2)) {
                flag = true;
                break;
            } else
                crd.add(dc);
        }
        if (flag == false)
            throw new IllegalStateException("Cannot find card");
        for (int i = crd.size() - 1; i >= 0; i--)
            add(crd.get(i));
        Oxygen oo = (Oxygen) dc;
        return oo;
    }

    /**
     * just a public constructor
     */
    public GameDeck() {

    }

    /**
     * Splits an Oxygen(2) card into two Oxygen(1) cards.
     * 
     * @param doubleOxygen the Oxygen(2) card to split
     * @return an array containing two Oxygen(1) cards
     */
    public Oxygen[] splitOxygen(Oxygen doubleOxygen) {
        if (doubleOxygen.toString().equals(GameDeck.OXYGEN_1))
            throw new IllegalArgumentException("You gave the wrong value!");

        if (size() <= 1)
            throw new IllegalStateException();

        Oxygen[] sp = { drawOxygen(1), drawOxygen(1) };
        add(doubleOxygen);

        // Swapping the elements of the returned array
        Oxygen temp = sp[0];
        sp[0] = sp[1];
        sp[1] = temp;

        return sp;
    }

    /**
     * game deck constructor which takes in parameters
     * 
     * @param d path of gamedeck file
     * @throws GameException denotes the exception
     */
    public GameDeck(String d) throws GameException {
        try {
            add(super.loadCards(d));
            for (int i = 1; i <= 10; i++) {
                Oxygen oxygen2 = new Oxygen(2);
                add(oxygen2);
            }
            for (int i = 1; i <= 38; i++) {
                Oxygen oxygen1 = new Oxygen(1);
                add(oxygen1);
            }
        } catch (Exception e) {
            throw new GameException("File error!", new FileNotFoundException());
        }

    }

}
