package selfish;

import selfish.deck.*;
import java.util.*;
import java.io.*;

/**
 * implementation and structre of GameEngine class
 * 
 * @author Kavish Chawla
 * @ selfish
 * @version 32.0
 */

public class GameEngine implements Serializable {

    private Collection<Astronaut> activePlayers = new ArrayList<Astronaut>();
    private boolean hasStarted = false;
    private static final long serialVersionUID = 1L;
    private Astronaut currentPlayer;

    private Random random;
    private GameDeck gameDeck;
    private SpaceDeck spaceDiscard;
    private List<Astronaut> corpses = new ArrayList<Astronaut>();
    private GameDeck gameDiscard;
    private SpaceDeck spaceDeck;

    /**
     * main Constructor of GameEngine
     * 
     * @param seed          used for shuffling the decks
     * @param gameDeckPath  path for the gameDeck file
     * @param spaceDeckPath path for the spaceDeck file
     * @throws Exception any exception
     */
    public GameEngine(long seed, String gameDeckPath, String spaceDeckPath) throws Exception {
        File gameDeckFile = new File(gameDeckPath);
        File spaceDeckFile = new File(spaceDeckPath);

        if (gameDeckFile.exists() && spaceDeckFile.exists()) {
            this.gameDeck = new GameDeck(gameDeckPath);
            this.spaceDeck = new SpaceDeck(spaceDeckPath);
            this.gameDiscard = new GameDeck();
            this.spaceDiscard = new SpaceDeck();
        } else {
            throw new GameException("oops Error in File!", null);
        }

        this.random = new Random(seed);
        this.gameDeck.shuffle(this.random);
        this.spaceDeck.shuffle(this.random);
    }

    /**
     * basic constructor of the class game engine!!
     */
    private GameEngine() {

    }

    /**
     * gets the game discard
     * 
     * @return pile of discarded game
     */
    public GameDeck getGameDiscard() {
        return gameDiscard;
    }

    /**
     * Gets the Game Deck
     * 
     * @return the game deck
     */
    public GameDeck getGameDeck() {
        return gameDeck;
    }

    /**
     * Gets the Space Discard
     * 
     * @return the space discard
     */
    public SpaceDeck getSpaceDiscard() {
        return spaceDiscard;
    }

    /**
     * Gets the Space Deck
     * 
     * @return space deck pile
     */
    public SpaceDeck getSpaceDeck() {
        return spaceDeck;
    }

    /**
     * saves the state
     * 
     * @param path path of file that is saved
     * @throws Exception if exception is found
     */
    public void saveState(String path) throws Exception {
        try (FileOutputStream fileOutStream = new FileOutputStream(path);
                ObjectOutputStream objOutStream = new ObjectOutputStream(fileOutStream)) {
            objOutStream.writeObject(this);
        } catch (Exception e) {
            throw new GameException("File error!", e);
        }
    }

    /**
     * get all players
     * 
     * @return players that are playing the current game!
     */
    public List<Astronaut> getAllPlayers() {
        List<Astronaut> allplayers = new ArrayList<>(activePlayers);
        allplayers.addAll(corpses);
        if (currentPlayer != null && !corpses.contains(currentPlayer)) {
            allplayers.add(currentPlayer);
        }
        return allplayers;
    }

    /**
     * defines Load State
     * 
     * @param p of the file to save the game
     * @return the game engine object
     * @throws Exception any excpetion found
     */
    public static GameEngine loadState(String p) throws Exception {
        GameEngine gameEngine;

        try (FileInputStream fis = new FileInputStream(p);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameEngine = (GameEngine) ois.readObject();
        } catch (Exception e) {
            throw new GameException("oops error in file!", e);
        }

        return gameEngine;
    }

    /**
     * gets the current player
     * 
     * @return the current player
     */
    public Astronaut getCurrentPlayer() {
        return hasStarted ? currentPlayer : null;
    }

    /**
     * adds player
     * 
     * @param player which is added
     * @return number of player list
     */
    public int addPlayer(String player) {
        if (hasStarted) {
            throw new IllegalStateException("Cannot add player: game has already started.");
        }
        if (activePlayers.size() == 5) {
            throw new IllegalStateException("Cannot add player: maximum number of players reached.");
        }
        Astronaut ast = new Astronaut(player, this);
        activePlayers.add(ast);
        return activePlayers.size();
    }

    /**
     * tells if game is over
     * 
     * @return tells if game is over in boolean
     */
    public boolean gameOver() {
        return activePlayers.size() == 0 ? true : getWinner() != null;
    }

    /**
     * gets full player count
     * 
     * @return the number of players playing!
     */
    public int getFullPlayerCount() {
        return getAllPlayers().size();
    }

    /**
     * kills player
     * 
     * @param crps which is killed!
     */
    public void killPlayer(Astronaut crps) {
        corpses.add(crps);
        activePlayers.remove(crps);
        crps.getHand().forEach(i -> gameDiscard.add(i));
        crps.getHand().clear();
    }

    /**
     * starts the game
     */
    public void startGame() {
        if (activePlayers.size() > 5 || activePlayers.size() == 1 || hasStarted == true)
            throw new IllegalStateException("Illegal start to the game");
        for (Astronaut it : activePlayers) {
            it.addToHand(gameDeck.drawOxygen(2));
            it.addToHand(gameDeck.drawOxygen(1));
            it.addToHand(gameDeck.drawOxygen(1));
            it.addToHand(gameDeck.drawOxygen(1));
            it.addToHand(gameDeck.drawOxygen(1));

        }
        for (int i = 1; i <= 4; i++) {
            for (Astronaut j : activePlayers)
                j.addToHand(gameDeck.draw());
        }

        hasStarted = true;
    }

    /**
     * starts the turn
     */
    public void startTurn() {
        if (!hasStarted || activePlayers.isEmpty() || getWinner() != null || currentPlayer != null) {
            throw new IllegalStateException("Cannot start turn.");
        }
        currentPlayer = activePlayers.iterator().next();
        activePlayers.remove(currentPlayer);
    }

    /**
     * This is the get winner method
     * 
     * @return the winner of the game
     */
    public Astronaut getWinner() {
        for (Astronaut i : activePlayers) {
            if (i.hasWon() == true)
                return i;
        }
        return null;
    }

    /**
     * This is the end turn method
     * 
     * @return the number of alive players
     */
    public int endTurn() {
        if (corpses.contains(currentPlayer) == false && currentPlayer != null) {
            activePlayers.add(currentPlayer);
            currentPlayer = null;
        } else
            currentPlayer = null;
        return activePlayers.size();
    }

    /**
     * This is the merge decks method
     * 
     * @param deck1 to restocked
     * @param deck2 to be used to restock deck1
     */
    public void mergeDecks(Deck deck1, Deck deck2) {
        deck2.shuffle(random);
        while (deck2.size() != 0)
            deck1.add(deck2.draw());
    }

    /**
     * This is the split oxygen method
     * 
     * @param haha the value of double value oxygen card
     * @return 2 single value oxygens
     */
    public Oxygen[] splitOxygen(Oxygen haha) {
        Oxygen[] oxy = null;
        if ((gameDeck.size() == 1 && gameDiscard.size() == 0) || (gameDeck.size() == 0 && gameDiscard.size() == 1))
            throw new IllegalStateException();
        if (gameDeck.size() > 1) {
            oxy = gameDeck.splitOxygen(haha);

        } else if (gameDiscard.size() > 1) {
            oxy = gameDiscard.splitOxygen(haha);
            gameDiscard.shuffle(random);
        } else if (gameDeck.size() == 1 && gameDiscard.size() == 1) {
            mergeDecks(gameDeck, gameDiscard);
            oxy = gameDeck.splitOxygen(haha);
        }

        return oxy;
    }

    /**
     * travel method
     * 
     * @param trvlr method of travel
     * @return path to travel
     */
    public Card travel(Astronaut trvlr) {
        if (currentPlayer.oxygenRemaining() <= 1)
            throw new IllegalStateException("Travelling with a single oxygen card!");

        boolean usedOxygen2 = false;
        for (Card card : trvlr.getHand()) {
            if (card.toString().equals(GameDeck.OXYGEN_2)) {
                gameDiscard.add(card);
                trvlr.hack(card);
                usedOxygen2 = true;
                break;
            }
        }

        if (!usedOxygen2) {
            int count = 0;
            for (Card card : trvlr.getHand()) {
                if (card.toString().equals(GameDeck.OXYGEN_1)) {
                    gameDiscard.add(card);
                    trvlr.hack(card);
                    count++;
                }
                if (count == 2)
                    break;
            }
        }

        if (!trvlr.isAlive()) {
            killPlayer(trvlr);
        }

        Card drawnCard = spaceDeck.draw();
        if (drawnCard.toString().equals(SpaceDeck.GRAVITATIONAL_ANOMALY)) {
            spaceDiscard.add(drawnCard);
            return drawnCard;
        }

        if (drawnCard.toString().equals(SpaceDeck.HYPERSPACE)) {
            trvlr.addToTrack(drawnCard);
            drawnCard = spaceDeck.draw();
            if (drawnCard.toString().equals(SpaceDeck.GRAVITATIONAL_ANOMALY)) {
                spaceDiscard.add(drawnCard);
            }
            return drawnCard;
        }

        trvlr.addToTrack(drawnCard);
        return drawnCard;
    }

}
