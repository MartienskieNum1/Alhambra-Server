package be.howest.ti.alhambra.logic;

import io.vertx.core.json.Json;

import java.util.LinkedList;
import java.util.List;

public class AlhambraController {
    private List<Building> buildings = new LinkedList<>();

    public Currency[] getCurrencies() {
        return Currency.values();
    }

    public BuildingType[] getBuildingTypes() {
        return BuildingType.values();
    }

    public List<Building> getBuildings() {

        return BuildingFactory.getAllBuildings();
    }

    public String returnPlayerToken(Game game, String body) {
        Player player = Json.decodeValue(body, Player.class);

        if (game != null) {
            String token = game.getGameId() + "+" + player.getUsername();
            game.addPlayer(token, player);
            return token;
        }
        return null;
    }

}
