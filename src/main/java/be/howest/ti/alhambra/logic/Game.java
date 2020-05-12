package be.howest.ti.alhambra.logic;

import java.util.*;

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
    private Set<Coin> bank;

    Random rand = new Random();
    List<Coin> remainingCoins = Coin.allCoins();
    List<Building> remainingBuildings = BuildingFactory.getAllBuildings();

    public Game(String gameId, String groupNr) {
        this.market = new HashMap<>();
        this.market.put(Currency.BLUE, null);
        this.market.put(Currency.GREEN, null);
        this.market.put(Currency.ORANGE, null);
        this.market.put(Currency.YELLOW, null);
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

    public void removePlayer(String token){
        token = token.substring(7);
        players.remove(token);
        playerCount--;
    }

    public void startGame() {
        currentPlayer = getPlayersList().get(0);

        for (int i = 0; i < 4; i++) {
            int randCoinInt = rand.nextInt(remainingCoins.size());
            Coin randCoin = remainingCoins.get(randCoinInt);
            bank.add(randCoin);
            remainingCoins.remove(randCoinInt);
        }

        market.replaceAll((key, value) -> {
            int randBuildingInt = rand.nextInt(remainingBuildings.size());
            Building newBuilding = remainingBuildings.get(randBuildingInt);
            remainingBuildings.remove(randBuildingInt);
            return newBuilding;
        });

        started = true;
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

    public void setReady(String token){
        token = token.substring(7);
        players.get(token).setReady();
        readyCount++;
        if (readyCount == playerCount) {
            startGame();
        }
    }

    public void setNotReady(String token){
        token = token.substring(7);
        players.get(token).setNotReady();
        readyCount--;
    }

}

