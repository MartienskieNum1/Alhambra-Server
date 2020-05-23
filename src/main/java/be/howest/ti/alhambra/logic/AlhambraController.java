package be.howest.ti.alhambra.logic;

import java.util.List;
import java.util.Map;

public class AlhambraController {

    public static final String WALLS = "walls";
    public static final String STARTED = "started";
    public static final String PLAYERS = "players";
    private BuildingFactory buildingFactory = new BuildingFactory();

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
            String encodedToken = encodeToken(game, player);
            game.addPlayer(encodedToken, player);
            return encodedToken;
        }
        throw new IllegalArgumentException("There is no game!");
    }

    public Map<BuildingType, List<Integer>> getScoringTable(int round) {
        return new ScoringTable().getScoringRound(round);
    }

    private String encodeToken(Game game, Player player) {
        String token = game.getGameId() + "+" + player.getUsername();
        char[] tokenArray = token.toCharArray();
        StringBuilder encodedToken = new StringBuilder();
        for (char character : tokenArray) {
            encodedToken.append(Integer.toOctalString(character));
            encodedToken.append("/");
        }
        return encodedToken.toString();
    }

    public String decodeToken(String token) {
        String[] splitToken = token.split("/");
        StringBuilder decodedToken = new StringBuilder();
        for (String sequence : splitToken) {
            decodedToken.append((char) Integer.parseInt(sequence, 8));
        }
        return decodedToken.toString();
    }
}
