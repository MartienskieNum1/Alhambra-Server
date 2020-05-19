package be.howest.ti.alhambra.logic;

import java.util.*;

public class Game {

    public static final String NOT_YOUR_TURN = "It's not your turn!";
    private final String gameId;
    private String groupNr;
    private Map<String, Player> players;
    private Boolean started;
    private Boolean ended;
    private int playerCount;
    private int readyCount;
    private Player currentPlayer;
    private int roundNr = 1;
    private Map<Currency, Building> market;
    private Coin[] bank = new Coin[] {null, null, null, null};

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

    public void shuffleRandomScoringRoundsInCoins(){
        for (int i=0; i < 2; i++){
            int scoringRound = rand.nextInt(remainingCoins.size());
            remainingCoins.add(scoringRound, new Coin(Currency.BLUE, 0));
        }
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
        checkBank();
        currentPlayer = playerOrder.pollFirst();
        playerOrder.addLast(currentPlayer);
    }

    public Computations checkBank() {
        for (int i = 0; i<4; i++){
            int randCoinInt = rand.nextInt(remainingCoins.size());
            Coin randCoin = remainingCoins.get(randCoinInt);
            if (bank[i]==null){
                if (randCoin.getAmount()==0){
                    new Computations(roundNr, this, "score");
                    roundNr++;
                    Coin replacementRandCoin = remainingCoins.get(randCoinInt+1);
                    bank[i] = replacementRandCoin;
                    remainingCoins.remove(randCoinInt+1);
                }
                bank[i] = randCoin;
                remainingCoins.remove(randCoinInt);
                if (remainingCoins.isEmpty()) {
                    remainingCoins = Coin.allCoins();
                }
            }
        }
        return null;
    }

    public void startGame() {
        playerOrder.addAll(getPlayersList());
        currentPlayer = playerOrder.pollFirst();
        playerOrder.addLast(currentPlayer);

        shuffleRandomScoringRoundsInCoins();

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
            player.addBaseToCity();
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

    public Player findPlayer(String token) {
        return players.get(token);
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

    public void giveMoney(String token, Coin[] coins) {
        List<Boolean> areAllCoinsInBank = new LinkedList<>();
        if (checkIfCurrentPlayersTurn(players.get(token))) {
            if (checkIfPlayerDoesNotTakeToMuch(coins)){
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
            }
            else {
                throw new IllegalArgumentException("You took too much money!");
            }

            if (checkIfAllCoinsAreInTheBank(areAllCoinsInBank) && players.get(token).equals(getCurrentPlayer())) {
                for (Coin coin : coins) {
                    players.get(token).addCoinToWallet(coin);
                    for (int j = 0; j < bank.length; j ++){
                        if (bank[j] == null || bank[j].equals(coin)) {
                            bank[j] = null;
                        }
                    }
                }
                nextTurn();
            } else {
                throw new IllegalArgumentException("Not all your money exists!");
            }
        } else {
            throw new IllegalArgumentException(NOT_YOUR_TURN);
        }
    }


    public boolean checkIfPlayerDoesNotTakeToMuch(Coin[] coins){
        if (coins.length > 1){
            int totalMoneyValue = 0;
            for (Coin coin: coins){
                totalMoneyValue += coin.getAmount();
            }

            return totalMoneyValue <= 5;
        }
        else {
            return true;
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
        int totalAmount = 0;
        Building newBuilding = remainingBuildings.get(randBuildingInt);
        market.replace(currency, newBuilding);
        remainingBuildings.remove(randBuildingInt);
        Player player = players.get(token);
        if (checkIfCurrentPlayersTurn(player)) {
            for (Coin coin : coins) {
                if (!player.getCoins().contains(coin)) {
                    throw new IllegalArgumentException("You don't have this money!");
                }
            }
            for (Coin coin : coins) {
                totalAmount+= coin.getAmount();
            }
            if (building.getCost() > totalAmount) {
                throw new IllegalArgumentException("You paid not enough!");
            }
            player.buyBuilding(building, coins);
            new Computations(roundNr, this, "");
        } else {
            throw new IllegalArgumentException(NOT_YOUR_TURN);
        }
    }

    public void buildBuilding(String token, Building building, int row, int col) {
        Player player = players.get(token);
        if (checkIfCurrentPlayersTurn(player)) {
            if (row == 0 && col == 0) {
                player.placeInReserve(building);
            } else {
                player.buildBuilding(building, row, col);
            }
            nextTurn();
        } else {
            throw new IllegalArgumentException(NOT_YOUR_TURN);
        }
    }

    public void redesign(String token, int row, int col) {
        Player player = players.get(token);
        if (checkIfCurrentPlayersTurn(player)) {
            player.redesign(row, col);
            nextTurn();
        } else {
            throw new IllegalArgumentException(NOT_YOUR_TURN);
        }
    }

    public int getRemainingBuildings(){
        return remainingBuildings.size();
    }
}
