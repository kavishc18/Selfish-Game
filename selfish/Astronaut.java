package selfish;

import java.io.Serializable;
import selfish.deck.*;

import java.util.*;

/**
 * implementation and structre of Astronaut class
 * 
 * @author Kavish Chawla
 * @ selfish
 * @version 32.0
 */
public class Astronaut implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    private String name;
    private Collection<Card> track = new ArrayList<Card>();
    private List<Oxygen> oxygens = new ArrayList<Oxygen>();

    private List<Card> actions = new ArrayList<Card>();
    private GameEngine game;

    /**
     * adds to hand
     * 
     * @param c gives card to be added
     */
    public void addToHand(Card c) {
        if (c.toString().equals(GameDeck.OXYGEN_1) || c.toString().equals(GameDeck.OXYGEN_2))
            oxygens.add((Oxygen) c);
        else
            actions.add(c);
    }

    /**
     * default constructor
     * 
     * @param n represents name of the player
     * @param g represents current object of game engine!!
     */
    public Astronaut(String n, GameEngine g) {
        this.name = n;
        this.game = g;
    }

    /**
     * gives the distance from the ship
     * 
     * @return distance
     */
    public int distanceFromShip() {
        return (6 - track.size());
    }

    /**
     * gets the trace
     * 
     * @return track of cards
     */
    public Collection<Card> getTrack() {
        return track;
    }

    /**
     * adds to track
     * 
     * @param c card that is added
     */
    public void addToTrack(Card c) {
        track.add(c);
    }

    /**
     * gets the hand
     * 
     * @return the hands of crd
     */
    public List<Card> getHand() {
        List<Card> actionCard = new ArrayList<Card>();
        actionCard.addAll(getActions());
        actionCard.addAll(oxygens);
        List<Card> ab = new ArrayList<Card>();
        List<String> stringgg = new ArrayList<String>();
        stringgg.add("Hack suit");
        stringgg.add("Hole in suit");
        stringgg.add("Laser blast");
        stringgg.add("Oxygen(1)");
        stringgg.add("Oxygen(2)");
        stringgg.add("Oxygen siphon");
        stringgg.add("Rocket booster");
        stringgg.add("Shield");
        stringgg.add("Tether");
        stringgg.add("Tractor beam");

        for (String stringg : stringgg) {
            for (Card cardd : actionCard) {
                if (stringg.equals(cardd.toString())) {
                    ab.add(cardd);
                }
            }
        }
        return ab;
    }

    /**
     * from hand to string
     * 
     * @return gives hand string!!
     */
    public String getHandStr() {
        List<Card> allCards = getActions();
        for (Oxygen oxy : oxygens) {
            if (oxy.getValue() == 2)
                allCards.add(oxy);
        }
        for (Oxygen oxy : oxygens) {
            if (oxy.getValue() == 1)
                allCards.add(oxy);
        }

        if (!isAlive())
            return "oops!!! the Astronaut is dead!";
        if (allCards.isEmpty())
            return "";

        String nonOxygenStr = "";
        List<Card> uniqueCards = new ArrayList<>();
        List<Integer> cardQuantities = new ArrayList<>();
        for (Card currentCard : allCards) {
            boolean isDuplicate = false;
            for (Card uniqueCard : uniqueCards) {
                if (uniqueCard.toString().equals(currentCard.toString())) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                uniqueCards.add(currentCard);
                cardQuantities.add(hasCard(currentCard.toString()));
            }
        }

        String oxygenStr = "";
        for (int i = 0; i < uniqueCards.size(); i++) {
            String cardInfo = (cardQuantities.get(i) == 1 ? "" : cardQuantities.get(i).toString() + "x ")
                    + uniqueCards.get(i).toString();
            if (uniqueCards.get(i).toString().equals(GameDeck.OXYGEN_1)
                    || uniqueCards.get(i).toString().equals(GameDeck.OXYGEN_2)) {
                oxygenStr += (i != uniqueCards.size() - 1) ? cardInfo + ", " : cardInfo;
            } else {
                nonOxygenStr += (i != uniqueCards.size() - 1) ? cardInfo + ", " : cardInfo;
            }
        }

        String result = oxygenStr + "; " + nonOxygenStr;
        result = result.substring(0, result.length() - 2);
        return result;
    }

    /**
     * breath
     * 
     * @return oxygen left
     */
    public int breathe() {
        int oxygenOneCount = 0;
        for (Oxygen oxygen : oxygens) {
            if (oxygen.toString().equals(GameDeck.OXYGEN_1))
                oxygenOneCount += 1;
        }
        if (oxygenOneCount == 0) {
            Oxygen oxygenTwo = null;
            for (Oxygen oxygen : oxygens) {
                if (oxygen.toString().equals(GameDeck.OXYGEN_2)) {
                    oxygenTwo = oxygen;
                    break;
                }
            }
            Oxygen[] splitOxygen = game.getGameDeck().splitOxygen(oxygenTwo);
            oxygens.add(splitOxygen[0]);
            game.getGameDiscard().add(oxygenTwo);
            game.getGameDiscard().add(splitOxygen[1]);
            oxygens.remove(oxygenTwo);
            if (!isAlive())
                game.killPlayer(this);
        } else {
            for (Oxygen oxygen : oxygens) {
                if (oxygen.toString().equals(GameDeck.OXYGEN_1)) {
                    game.getGameDiscard().add(oxygen);
                    oxygens.remove(oxygen);
                    break;
                }
            }
            if (!isAlive())
                game.killPlayer(this);
        }
        return oxygenRemaining();
    }
    

    /**
     * gets action cards
     *
     * @return gives list of action cards
     */
    public List<Card> getActions() {
        List<Card> actionCards = new ArrayList<>();
        List<String> cardNames = Arrays.asList("Hack suit", "Hole in suit", "Laser blast", "Oxygen siphon",
                "Rocket booster", "Shield", "Tether", "Tractor beam");

        for (String cardName : cardNames) {
            for (Card card : actions) {
                if (cardName.equals(card.toString())) {
                    actionCards.add(card);
                }
            }
        }
        return actionCards;
    }

    /**
     * converts the actions to strings!!
     * 
     * @param emu  if enumeration is true or false
     * @param exsh if shields are present
     * @return string formated cards
     */
    public String getActionsStr(boolean emu, boolean exsh) {
        List<Card> cards = getActions();

        if (!isAlive()) {
            return "Astronaut is dead!";
        } else if (cards.isEmpty()) {
            return "";
        } else {
            StringBuilder str = new StringBuilder();
            List<Card> uniqueCards = new ArrayList<>();
            List<Integer> quantities = new ArrayList<>();
            String[] labels = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };

            for (Card currentCard : cards) {
                if (currentCard.toString().equals(GameDeck.SHIELD) && exsh) {
                    continue;
                }
                boolean cardExists = false;
                for (Card uniqueCard : uniqueCards) {
                    if (uniqueCard.toString().equals(currentCard.toString())) {
                        cardExists = true;
                        break;
                    }
                }
                if (!cardExists) {
                    uniqueCards.add(currentCard);
                    quantities.add(hasCard(currentCard.toString()));
                }
            }

            for (int i = 0; i < uniqueCards.size(); i++) {
                if (emu) {
                    str.append("[").append(labels[i]).append("] ").append(uniqueCards.get(i).toString());
                } else {
                    int quantity = quantities.get(i);
                    str.append(quantity > 1 ? quantity + "x " : "").append(uniqueCards.get(i).toString());
                }

                if (i != uniqueCards.size() - 1) {
                    str.append(", ");
                }
            }
            return str.toString();
        }
    }

    /**
     * hack method
     * 
     * @param targetCard the card which is hacked
     */
    public void hack(Card targetCard) {
        if (targetCard == null)
            throw new IllegalArgumentException("wrong argument");

        boolean isOxygenCard = targetCard instanceof Oxygen;
        if (isOxygenCard && !this.oxygens.contains(targetCard) || !isOxygenCard && !this.actions.contains(targetCard)) {
            throw new IllegalArgumentException("oops the Card is not found");
        }

        if (targetCard.toString().equals(GameDeck.OXYGEN_1) || targetCard.toString().equals(GameDeck.OXYGEN_2)) {
            oxygens.remove(targetCard);
        } else {
            actions.remove(targetCard);
        }

        if (!isAlive()) {
            actions.clear();
            game.killPlayer(this);
        }
    }

    /**
     * hacking card
     * 
     * @param cardName the card that will be hacked
     * @return hacked card
     */
    public Card hack(String cardName) {
        if (cardName == null)
            throw new IllegalArgumentException("no Argument");

        Card foundCard = null;
        if (hasCard(cardName) != 0) {
            if (cardName.equals(GameDeck.OXYGEN_2) || cardName.equals(GameDeck.OXYGEN_1)) {
                for (int i = 0; i < oxygenRemaining(); i++) {
                    if (cardName.equals(oxygens.get(i).toString())) {
                        foundCard = oxygens.get(i);
                        break;
                    }
                }
            } else {
                for (int i = 0; i < actions.size(); i++) {
                    if (cardName.equals(actions.get(i).toString())) {
                        foundCard = actions.get(i);
                        break;
                    }
                }
            }
            hack(foundCard);
        } else {
            throw new IllegalArgumentException("no Card is found");
        }
        return foundCard;
    }

    /**
     * has card!!
     * 
     * @param c card as param
     * @return the count of c
     */
    public int hasCard(String c) {
        List<Card> hand = getHand();
        int count = 0;
        for (Card i : hand) {
            count += i.toString().equals(c) ? 1 : 0;
        }
        return count;
    }

    /**
     * tells if alive or not
     * 
     * @return true if alive else false
     */
    public boolean isAlive() {
        return oxygenRemaining() > 0;
    }

    /**
     * melted eye balls
     * 
     * @return if melted eyeballs true or false
     */
    public boolean hasMeltedEyeballs() {
        Card c = peekAtTrack();
        return c.toString().equals(SpaceDeck.SOLAR_FLARE);
    }

    /**
     * siphon method
     * 
     * @return the oxygen cards that were stolen
     */
    public Oxygen siphon() {
        int haha = (int) Math.random() * oxygens.size();
        Oxygen op = oxygens.get(haha);
        if (op.toString().equals(GameDeck.OXYGEN_2)) {

            Oxygen[] opy = game.getGameDeck().splitOxygen(op);
            oxygens.add(opy[1]);

            game.getGameDiscard().add(op);
            hack(op);

            return opy[0];
        } else {
            hack(op);

            return op;
        }
    }

    /**
     * Tells about if has won
     * 
     * @return returns true if win the game
     */
    public boolean hasWon() {
        return distanceFromShip() == 0 && isAlive();
    }

    /**
     * gives laser blast
     * 
     * @return a card of Laser Blast
     */
    public Card laserBlast() {
        Card c = track.size() == 0 ? throwIllegalArgumentException() : peekAtTrack();
        track.remove(c);
        return c;
    }

    private Card throwIllegalArgumentException() {
        throw new IllegalArgumentException("player is at the start!");
    }

    /**
     * tells about remaining Oxygen!
     * 
     * @return the number of oxy cards left
     */
    public int oxygenRemaining() {
        int c = 0;
        for (Oxygen i : oxygens) {
            c += i.toString().equals(GameDeck.OXYGEN_2) ? 2 : (i.toString().equals(GameDeck.OXYGEN_1) ? 1 : 0);
        }
        return c;
    }

    /**
     * peek at track
     * 
     * @return card behind the last in trackof atronaut!
     */
    public Card peekAtTrack() {
        return track.size() > 0 ? ((ArrayList<Card>) track).get(track.size() - 1) : null;
    }

    /**
     * swaps the track
     * 
     * @param swp changes track
     */
    public void swapTrack(Astronaut swp) {
        List<Card> track = (List<Card>) this.track;

        this.track = swp.track;

        swp.track = track;
    }

    /**
     * stealing the card
     * 
     * @return gives the card that was stolen
     */
    public Card steal() {
        Random random = new Random();
        List<Card> crds = getHand();

        int haha = random.nextInt(crds.size());

        Card crd = null;
        crd = crds.get(haha);
        hack(crd);
        return crd;
    }

    @Override
    public int compareTo(Object o) {
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }

    /**
     * converts to string
     * 
     * @return the name
     */
    public String toString() {

        if (isAlive() == true)
            return name;
        else
            return name + " (is dead)";
    }
}
