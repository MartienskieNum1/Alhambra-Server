package be.howest.ti.alhambra.logic;

import io.vertx.core.json.JsonObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static be.howest.ti.alhambra.logic.AlhambraController.PLAYERS;
import static be.howest.ti.alhambra.logic.AlhambraController.STARTED;
import static be.howest.ti.alhambra.logic.AlhambraController.WALLS;

public class ToJson {

    private JsonObject playerInfo(Player player){
        return new JsonObject()
                .put("name", player.getUsername())
                .put("coins", getPlayerCoins(player))
                .put("reserve", getPlayerReserve(player))
                .put("buildings-in-hand", getBuildingInPlayerHand(player))
                .put("city", getPlayerCity(player))
                .put("virtual-score", player.getVirtualScore())
                .put("score", player.getScore());
    }

    public List<JsonObject> getPlayerCoins(Player player){
        List<JsonObject> coins = new LinkedList<>();
        for (Coin coin : player.getCoins()) {
            JsonObject json = new JsonObject();
            json.put("currency", coin.getCurrency().toString())
                    .put("amount", coin.getAmount());
            coins.add(json);
        }
        return coins;
    }
    public List<JsonObject> getBuildingInPlayerHand(Player player){
        List<JsonObject> buildingsInHand = new LinkedList<>();
        for (Building building : player.getBuildingsInHand()) {
            makeBuildingToJson(buildingsInHand, building);
        }
        return buildingsInHand;
    }

    public void makeBuildingToJson(List<JsonObject> reserve, Building building) {
        JsonObject walls = new JsonObject();
        for (Map.Entry<String, Boolean> entry : building.getWalls().entrySet()) {
            walls.put(entry.getKey(), entry.getValue());
        }
        JsonObject json = new JsonObject();
        json.put("type", building.getBuildingType().toString())
                .put("cost", building.getCost())
                .put(WALLS, walls);
        reserve.add(json);
    }

    public List <List<JsonObject>> getPlayerCity(Player player){
        List<List<JsonObject>> city = new LinkedList<>(); //placeholder
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
        return city;
    }

    public List<JsonObject> getPlayerReserve(Player player){
        List<JsonObject> reserve = new LinkedList<>();
        for (Building building : player.getReserve()) {
            makeBuildingToJson(reserve, building);
        }
        return reserve;
    }

    public Object returnListGameDetails(Map<String, Game> allGames, String prefix, String details) {
        List<String> listOfGamesInfo = new LinkedList<>();
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

    public JsonObject getBasicGameInfo(String gameId, Alhambra game) {
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
                .put("currentPlayer", gameToFind.getCurrentPlayer().getUsername())
                .put("remainingBuildings", gameToFind.getRemainingBuildings());
    }
    public JsonObject getGameInfo(String gameId, Alhambra game) {
        Game gameToFind = game.findGame(gameId);

        if (Boolean.TRUE.equals(gameToFind.getStarted())) {
            return getDetailedGameInfo(gameId, game);
        }else {
            return getBasicGameInfo(gameId, game);

        }
    }
}
