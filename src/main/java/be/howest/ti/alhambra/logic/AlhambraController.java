package be.howest.ti.alhambra.logic;

import io.vertx.core.json.JsonObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AlhambraController {

    public static final String WALLS = "walls";
    public static final String STARTED = "started";
    public static final String PLAYERS = "players";
    private BuildingFactory buildingFactory = new BuildingFactory();
    private ToJson toJson = new ToJson();

    public Currency[] getCurrencies() {
        return Currency.values();
    }

    public BuildingType[] getBuildingTypes() {
        return BuildingType.values();
    }

    public List<Building> getBuildings() {
        return buildingFactory.getAllBuildings();
    }

    public String getPlayerToken(Game game, Player player) {
        if (game != null) {
            String token = game.getGameId() + "+" + player.getUsername();
            game.addPlayer(token, player);
            return token;
        }
        throw new IllegalArgumentException("There is no game!");
    }

    public Map<BuildingType, List<Integer>> getScoringTable(int round) {
        return new ScoringTable().getScoringRound(round);
    }
}
