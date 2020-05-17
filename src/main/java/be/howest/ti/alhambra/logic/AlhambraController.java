package be.howest.ti.alhambra.logic;

import io.vertx.core.json.JsonObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AlhambraController {

    public static final String WALLS = "walls";
    public static final String STARTED = "started";
    public static final String PLAYERS = "players";

    public Currency[] getCurrencies() {
        return Currency.values();
    }

    public BuildingType[] getBuildingTypes() {
        return BuildingType.values();
    }

    private BuildingFactory buildingFactory = new BuildingFactory();
    public List<Building> getBuildings() {
        return buildingFactory.getAllBuildings();
    }

    public String returnPlayerToken(Game game, Player player) {
        if (game != null) {
            String token = game.getGameId() + "+" + player.getUsername();
            game.addPlayer(token, player);
            return token;
        }
        throw new IllegalArgumentException();
    }

    public Object returnListGameDetails(Map<String, Game> allGames, String prefix, String details) {
        List<String>listOfGamesInfo = new LinkedList<>();
        List<JsonObject>listOfGamesDetailed = new LinkedList<>();

        if (Boolean.parseBoolean(details)) {
            for (Map.Entry<String, Game> entry : allGames.entrySet()) {
                Game game = entry.getValue();
                if (Boolean.FALSE.equals(game.getStarted() && game.getGroupNr().equals(prefix))) {
                    listOfGamesDetailed.add(new JsonObject()
                            .put("gameId",game.getGameId())
                            .put(PLAYERS, game.getPlayersList())
                            .put(STARTED, game.getStarted())
                            .put("ended", game.getEnded())
                            .put("playerCount", game.getPlayerCount())
                            .put("readyCount", game.getReadyCount()));
                }
            }
            return listOfGamesDetailed;
        } else {
            for (Map.Entry<String, Game> entry : allGames.entrySet()) {
                Game game = entry.getValue();
                if (Boolean.FALSE.equals(game.getStarted() && game.getGroupNr().equals(prefix))) {
                    listOfGamesInfo.add(game.getGameId());
                }
            }
        }
        return listOfGamesInfo;
    }

    public static Map<BuildingType, List<Integer>> getScoringTable(int round) {
        if (round == 1){
            return new ScoringTable().makeRound1();
        }
        if (round == 2){
            return new ScoringTable().makeRound2();
        }
        else {return new ScoringTable().makeRound3();}
    }

    private JsonObject getBasicGameInfo(String gameId, Alhambra game) {
        Game gameToFind = game.findGame(gameId);
        List<String> playerUsernames = new LinkedList<>();

        for (Player player : gameToFind.getPlayersList()) {
            playerUsernames.add(player.getUsername());
        }

        return new JsonObject()
                .put("gameId",gameToFind.getGameId())
                .put(PLAYERS, playerUsernames)
                .put(STARTED, gameToFind.getStarted())
                .put("playerCount", gameToFind.getPlayerCount())
                .put("readyCount", gameToFind.getReadyCount());
    }

    private JsonObject playerInfo(Player player){
        List<JsonObject> reserve = new LinkedList<>();
        List<JsonObject> buildingsInHand = new LinkedList<>();
        List<List<JsonObject>> city = new LinkedList<>(); //placeholder
        List<JsonObject> coins = new LinkedList<>();

        for (Building building : player.getReserve()) {
            JsonObject walls = new JsonObject();
            for (Map.Entry<String, Boolean> entry : building.getWalls().entrySet()) {
                walls.put(entry.getKey(), entry.getValue());
            }
            JsonObject json = new JsonObject();
            json.put("type", building.getBuildingType())
                    .put("cost", building.getCost())
                    .put(WALLS, walls);
            reserve.add(json);
        }

        for (Building building : player.getBuildingsInHand()) {
            JsonObject walls = new JsonObject();
            for (Map.Entry<String, Boolean> entry : building.getWalls().entrySet()) {
                walls.put(entry.getKey(), entry.getValue());
            }
            JsonObject json = new JsonObject();
            json.put("type", building.getBuildingType().toString())
                    .put("cost", building.getCost())
                    .put(WALLS, walls);
            buildingsInHand.add(json);
        }

        for (List<Building> list : player.getCity()) {
            List<JsonObject> jsonList = new LinkedList<>();
            for (Building building : list) {
                if (building == null) {
                    jsonList.add(null);
                } else {
                    JsonObject walls = new JsonObject();
                    for (Map.Entry<String, Boolean> entry : building.getWalls().entrySet()) {
                        walls.put(entry.getKey(), entry.getValue());
                    }
                    String type = (building.getBuildingType() != null) ? building.getBuildingType().toString() : null;
                    jsonList.add(new JsonObject()
                            .put("type", type)
                            .put("cost", building.getCost())
                            .put(WALLS, walls));
                }
            }
            city.add(jsonList);
        }

        for (Coin coin : player.getCoins()) {
            JsonObject json = new JsonObject();
            json.put("currency", coin.getCurrency().toString())
                    .put("amount", coin.getAmount());
            coins.add(json);
        }

        return new JsonObject()
                .put("name", player.getUsername())
                .put("coins", coins)
                .put("reserve", reserve)
                .put("buildings-in-hand", buildingsInHand)
                .put("city", city)
                .put("virtual-score", player.getVirtualScore())
                .put("score", player.getScore());
    }

    public JsonObject getGameInfo(String gameId, Alhambra game) {
        Game gameToFind = game.findGame(gameId);

        if (Boolean.TRUE.equals(gameToFind.getStarted())) {
            return getDetailedGameInfo(gameId, game);
        }else {
            return getBasicGameInfo(gameId, game);

        }
    }

    public JsonObject getDetailedGameInfo(String gameId, Alhambra game){
        Game gameToFind = game.findGame(gameId);
        List<JsonObject> playerInfo = new LinkedList<>();
        List<JsonObject> bank = new LinkedList<>();
        JsonObject market = new JsonObject();

        for (Coin coin : gameToFind.getBank()) {
            JsonObject json = new JsonObject();
            json.put("currency", coin.getCurrency().toString())
                    .put("amount", coin.getAmount());
            bank.add(json);
        }

        for (Map.Entry<Currency, Building> entry : gameToFind.getMarket().entrySet()) {
            JsonObject walls = new JsonObject();
            for (Map.Entry<String, Boolean> entry1 : entry.getValue().getWalls().entrySet()) {
                walls.put(entry1.getKey(), entry1.getValue());
            }
            JsonObject building = new JsonObject()
                    .put("type", entry.getValue().getBuildingType().toString() )
                    .put("cost", entry.getValue().getCost())
                    .put(WALLS, walls);
            market.put(entry.getKey().toString(), building);
        }

        for (Player player : gameToFind.getPlayersList()) {
            playerInfo.add(playerInfo(player));
        }

        return new JsonObject()
                .put("bank", bank)
                .put("market", market)
                .put(PLAYERS, playerInfo)
                .put(STARTED, gameToFind.getStarted())
                .put("ended", gameToFind.getEnded())
                .put("currentPlayer", gameToFind.getCurrentPlayer().getUsername());
    }
}
