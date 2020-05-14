package be.howest.ti.alhambra.logic;

import io.vertx.core.json.JsonObject;

import java.util.LinkedList;
import java.util.List;

public class AlhambraController {

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

    public Object returnListGameDetails(List<Game> allGames, String prefix, String details) {
        List<String>listOfGamesInfo = new LinkedList<>();
        List<JsonObject>listOfGamesDetailed = new LinkedList<>();

        if (Boolean.parseBoolean(details)) {
            for (Game game : allGames) {
                if (!game.getStarted() && game.getGroupNr().equals(prefix)) {
                    listOfGamesDetailed.add(new JsonObject()
                            .put("gameId",game.getGameId())
                            .put("players", game.getPlayersList())
                            .put("started", game.getStarted())
                            .put("ended", game.getEnded())
                            .put("playerCount", game.getPlayerCount())
                            .put("readyCount", game.getReadyCount()));
                }
            }
            return listOfGamesDetailed;
        } else {
            for (Game game : allGames) {
                if (!game.getStarted() && game.getGroupNr().equals(prefix)) {
                    listOfGamesInfo.add(game.getGameId());
                }
            }
        }
        return listOfGamesInfo;
    }
}
