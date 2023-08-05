package selfish.deck;

import java.io.*;
import selfish.GameException;
import java.util.*;

/**
 * 
 * Represents a deck of cards with methods for loading, adding, removing, and
 * shuffling cards.
 * 
 * @author Kavish Chawla
 * @ selfish
 * @version 32.0
 */

public abstract class Deck implements Serializable {

    private static final long serialVersionUID = 1L;
    private Collection<Card> cards = new ArrayList<Card>();

    /**
     * Constructs an empty deck!
     */
    protected Deck() {
    }

    /**
     * Converts a string representation of cards to an array of Card objects
     * 
     * @param s a string representing cards
     * @return an array of Card objects
     */
    protected static Card[] stringToCards(String s) {
        String[] parts = s.split(";");
        parts[2] = parts[2].trim();
        ArrayList<Card> cardList = new ArrayList<Card>();
        for (int i = 0; i < Integer.valueOf(parts[2]); i++) {
            Card card = new Card(parts[0], parts[1].trim());
            cardList.add(card);
        }
        Card[] cardsArray = new Card[Integer.parseInt(parts[2])];
        return cardList.toArray(cardsArray);
    }

    /**
     * Loads cards from a file.
     * 
     * @param file the path of the file containing card data
     * @return a list of Card objects
     * @throws Exception if any exceptions occur during file reading
     */
    protected static List<Card> loadCards(String file) throws Exception {
        boolean eof = true;
        ArrayList<Card> c = new ArrayList<Card>();
        try {
            File f = new File(file);
            if (f.exists() == false)
                throw new Exception();
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String str;
            int count = 0;
            while ((str = br.readLine()) != null && eof == true) {
                if (count > 0) {
                    Card crd[] = stringToCards(str);
                    for (Card i : crd)
                        c.add(i);
                }
                count++;
            }
            br.close();
        } catch (Exception e) {
            throw new GameException("File Error!", new FileNotFoundException());
        }
        return c;
    }



    /**
     * This is the Draw method
     * 
     * @return the card drawn
     */
    public Card draw() {
        if (this.size() == 0)
            throw new IllegalStateException("Deck is empty!");
        List<Card> crd = (List<Card>) cards;
        Card c = crd.get(size() - 1);
        remove(c);
        return c;
    }

    /**
     * add 2 method is implemented here
     * 
     * @param c card to be added to the method
     * @return  size of card list
     */
    public int add(Card c) {
        cards.add(c);
        return cards.size();
    }    
    
    /**
     * Adds a card to the deck
     * 
     * @param cr card to be added in
     * @return the size of the deck after adding the card
     */
    protected int add(List<Card> cr) {
        for (Card i : cr)
            this.cards.add(i);
        return this.cards.size();
    }

    /**
     * it Removes a card from the deck
     * 
     * @param crds the card to remove
     */
    public void remove(Card crds) {
        List<Card> cardList = (List<Card>) cards;
        if (!cardList.isEmpty()) {
            boolean cardFound = cardList.stream().anyMatch(c -> c == crds);
            if (cardFound) {
                cards.remove(crds);
            }
        }
    } 
    
    
    /**
     * Calculates the size of method
     * 
     * @return the cardsize 
     */
    public int size() {
        return cards.size();
    }

    /**
     * Shuffling is implemented here 
     * 
     * @param rndm points to a random obj
     */
    public void shuffle(Random rndm) {
        Collections.shuffle((List<Card>) cards, rndm);
    }

   
}
