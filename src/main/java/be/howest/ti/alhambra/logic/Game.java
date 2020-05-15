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
    private Coin[] bank = new Coin[] {null, null, null, null};
    private List<Boolean> areAllCoinsInBank = new LinkedList<>();

    private BuildingFactory buildingFactory = new BuildingFactory();

    private Random rand = new Random();
    private List<Coin> remainingCoins = Coin.allCoins();
    private List<Building> remainingBuildings = buildingFactory.getAllBuildings();
    private Deque<Player> playerOrder = new LinkedList<>();

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

    private void checkIfGameMeetsRequirements(){
        if (playerCount >= 2 && readyCount == playerCount){
            startGame();
        }
    }

    public void nextTurn() {
        currentPlayer = playerOrder.pollFirst();
        playerOrder.addLast(currentPlayer);
    }

    public void startGame() {
        playerOrder.addAll(getPlayersList());
        currentPlayer = playerOrder.pollFirst();
        playerOrder.addLast(currentPlayer);

        for (int i = 0; i < bank.length; i++) {
            int randCoinInt = rand.nextInt(remainingCoins.size());
            Coin randCoin = remainingCoins.get(randCoinInt);
            bank[i] = randCoin;
            remainingCoins.remove(randCoinInt);
        }

        market.replaceAll((key, value) -> {
            int randBuildingInt = rand.nextInt(remainingBuildings.size());
            Building newBuilding = remainingBuildings.get(randBuildingInt);
            remainingBuildings.remove(randBuildingInt);
            return newBuilding;
        });

        for (Map.Entry<String, Player> entry : players.entrySet()) {
            Player player = entry.getValue();
            int totalValue = 0;
            while (totalValue < 20) {
                int randCoinInt = rand.nextInt(remainingCoins.size());
                Coin coinToAdd = remainingCoins.get(randCoinInt);
                remainingCoins.remove(randCoinInt);
                player.addCoinToWallet(coinToAdd);
                totalValue += coinToAdd.getAmount();
            }
        }

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

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Map<Currency, Building> getMarket() {
        return market;
    }

    public Coin[] getBank() {
        return bank;
    }

    public String getGroupNr() {
        return groupNr;
    }

    public void setPlayerReady(String token){
        players.get(token).setItselfReady();
        readyCount++;
        checkIfGameMeetsRequirements();
    }

    public void setPlayerNotReady(String token){
        players.get(token).setItselfNotReady();
        readyCount--;
        checkIfGameMeetsRequirements();
    }

    public void removePlayer(String token){
        players.remove(token);
        playerCount--;
        checkIfGameMeetsRequirements();
    }

    public void giveMoney(String token,Coin[] coins) {

        for (int k = 0 ; k < coins.length; k ++){
            areAllCoinsInBank.add(false);
        }
        for (int i = 0; i < coins.length;i ++){
            for (int j = 0; j < bank.length; j ++){
                if (bank[j].equals(coins[i])) {
                    areAllCoinsInBank.set(i, true);
                    j = bank.length + 1;
                }
            }
        }
        if (checkIfAllCoinsAreInTheBank(areAllCoinsInBank) && players.get(token).equals(getCurrentPlayer())){
            for (Coin coin : coins) {
                players.get(token).addCoinToWallet(coin);
                for (int j = 0; j < bank.length; j ++){
                    if (bank[j].equals(coin)) {
                        bank[j] = null;
                    }
                }
            }
        }
    }

    public boolean checkIfAllCoinsAreInTheBank(List<Boolean> list){
        return list.isEmpty() || list.stream()
                .allMatch(list.get(0)::equals);
    }

    private boolean checkIfCurrentPlayersTurn(Player player) {
        return player.equals(getCurrentPlayer());
    }

    public void buyBuilding(String token, List<Coin> coins, Currency currency) {
        Building building = market.get(currency);
        int randBuildingInt = rand.nextInt(remainingBuildings.size());
        Building newBuilding = remainingBuildings.get(randBuildingInt);
        market.replace(currency, newBuilding);
        remainingBuildings.remove(randBuildingInt);
        Player player = players.get(token);
        if (checkIfCurrentPlayersTurn(player)) {
            player.addBuilding(building, coins);
        }
        else {
            throw new IllegalArgumentException("It's not your turn!");
        }
        nextTurn();
    }
}
