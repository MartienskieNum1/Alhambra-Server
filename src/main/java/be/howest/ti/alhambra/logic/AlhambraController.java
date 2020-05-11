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
        buildings.add(new Building(BuildingType.PAVILION, 2, true, true, false, true));
        buildings.add(new Building(BuildingType.PAVILION, 3, false, false, true, true));
        buildings.add(new Building(BuildingType.PAVILION, 4, false, true, true, false));
        buildings.add(new Building(BuildingType.PAVILION, 5, true, false, false, true));
        buildings.add(new Building(BuildingType.PAVILION, 6, true, false, false, false));
        buildings.add(new Building(BuildingType.PAVILION, 7, false, true, false, false));
        buildings.add(new Building(BuildingType.PAVILION, 8, false, false, false, false));
        buildings.add(new Building(BuildingType.SERAGLIO, 3, false, true, true, true));
        buildings.add(new Building(BuildingType.SERAGLIO, 4, true, true, false, false));
        buildings.add(new Building(BuildingType.SERAGLIO, 5, false, false, true, true));
        buildings.add(new Building(BuildingType.SERAGLIO, 6, false, true, true, false));
        buildings.add(new Building(BuildingType.SERAGLIO, 7, false, false, false, true));
        buildings.add(new Building(BuildingType.SERAGLIO, 8, false, false, true, false));
        buildings.add(new Building(BuildingType.SERAGLIO, 9, false, false, false, false));

        return this.buildings;
    }

    public String returnPlayerToken(String gameId, String body) {
        Player player = Json.decodeValue(body, Player.class);

        Game game = Alhambra.findGame(gameId);
        if (game != null) {
            game.addPlayer(player);
            return game.getGameId() + "+" + player.getUsername();
        }
        return null;
    }

    public Boolean setReady(String id, String playerName, String body) {



        return true;
    }
}
