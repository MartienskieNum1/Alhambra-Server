package be.howest.ti.alhambra.logic;

import java.util.List;

public class Game {

    private final String groupNr;
    private final int gameId;
    private List<Player> players;
    private Boolean started;
    private Boolean ended;
    private int playerCount;
    private int readyCount;
    private Player currentPlayer;
    private List<Building> market;
    private List<Coin> bank;



    public Game(String groupNr, int gameId, List<Player> players) {
        this.groupNr = groupNr;
        this.gameId = gameId;
        this.players = players;
        this.readyCount = 0;
        this.started = false;
        this.ended = false;
        this.playerCount = players.size();
        this.currentPlayer = players.get(0);
    }

    public int getGameId() {
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
