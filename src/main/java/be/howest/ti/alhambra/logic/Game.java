package be.howest.ti.alhambra.logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Game {

    private final String gameId;
    private List<Player> players;
    private Boolean started;
    private Boolean ended;
    private int playerCount;
    private int readyCount;
    private Player currentPlayer;
    private Map<Currency, Building> market;
    private Map<Currency, Coin> bank;



    public Game(String gameId) {

        this.gameId = gameId;
        this.players = new LinkedList<>();
        this.readyCount = 0;
        this.started = false;
        this.ended = false;
        this.playerCount = 0;
    }

    public void addPlayer(Player playerToAdd){
        players.add(playerToAdd);
        playerCount++;
    }

    public String getGameId() {
        return gameId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Boolean getStarted() {
        return started;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public int getReadyCount() {
        return readyCount;
    }
}

