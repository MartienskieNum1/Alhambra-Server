package be.howest.ti.alhambra.logic;

import io.vertx.core.json.Json;
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
