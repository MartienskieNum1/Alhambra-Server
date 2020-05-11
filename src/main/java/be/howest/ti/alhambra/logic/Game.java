package be.howest.ti.alhambra.logic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Game {

    private final String gameId;
    private String groupNr;
    private Map<String, Player> players;
    private Boolean started;
    private Boolean ended;
    private int playerCount;
    private int readyCount;
    private Player currentPlayer;
    private Map<Currency, Building> market;
    private Map<Currency, Coin> bank;



    public Game(String gameId, String groupNr) {

        this.gameId = gameId;
        this.groupNr = groupNr;
        this.players = new HashMap<>();
        this.readyCount = 0;
        this.started = false;
        this.ended = false;
        this.playerCount = 0;
    }

    public void addPlayer(String token, Player playerToAdd){
        players.put(token, playerToAdd);
        playerCount++;
    }

    public void deletePlayer(Player playerToDelete){

        playerCount--;
    }

    public String getGameId() {
        return gameId;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public List<Player> getPlayersList() {
        return new LinkedList<>(players.values());
    }

    public Boolean getStarted() {
        return started;
    }

    public Boolean getEnded() { return ended; }

    public int getPlayerCount() {
        return playerCount;
    }

    public int getReadyCount() {
        return readyCount;
    }

    public String getGroupNr() {
        return groupNr;
    }
}
