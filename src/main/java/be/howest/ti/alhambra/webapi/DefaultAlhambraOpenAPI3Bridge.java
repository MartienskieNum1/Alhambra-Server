package be.howest.ti.alhambra.webapi;

import be.howest.ti.alhambra.logic.*;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;

import java.util.LinkedList;
import java.util.List;

public class DefaultAlhambraOpenAPI3Bridge implements AlhambraOpenAPI3Bridge {


    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAlhambraOpenAPI3Bridge.class);
    private final AlhambraController controller;

    public DefaultAlhambraOpenAPI3Bridge() {
        this.controller = new AlhambraController();
    }

    public boolean verifyAdminToken(String token) {
        LOGGER.info("verifyPlayerToken");
        return true;
    }

    public boolean verifyPlayerToken(String token, String gameId, String playerName) {
        LOGGER.info("verifyPlayerToken");
        return true;
    }

    public Object getBuildings(RoutingContext ctx) {
        LOGGER.info("getBuildings");
        return controller.getBuildings();
    }

    public Object getAvailableBuildLocations(RoutingContext ctx) {
        LOGGER.info("getAvailableBuildLocations");
        return null;
    }

    public Object getBuildingTypes(RoutingContext ctx) {
        LOGGER.info("getBuildingTypes");
        return controller.getBuildingTypes();
    }

    public Object getCurrencies(RoutingContext ctx) {
        LOGGER.info("getCurrencies");
        return controller.getCurrencies();
    }

    public Object getScoringTable(RoutingContext ctx) {
        LOGGER.info("getScoringTable");
        return null;
    }

    public Object getGames(RoutingContext ctx) {
        List<Game>listOfGames = Alhambra.getGames();
        List<String>listOfGamesInfo = new LinkedList<>();

        for (Game game: listOfGames){
            listOfGamesInfo.add(game.getGameId());
        }

        return listOfGamesInfo;
    }

    public Object createGame(RoutingContext ctx) {
        Game newGame = Alhambra.addGame();

        return newGame.getGameId();
    }

    public Object clearGames(RoutingContext ctx) {
        LOGGER.info("clearGames");
        return null;
    }

    public Object joinGame(RoutingContext ctx) {
        LOGGER.info("joinGame");
        String gameId = ctx.request().getParam("gameId");

        String body = ctx.getBodyAsString();

        return controller.returnPlayerToken(gameId, body);
    }


    public Object leaveGame(RoutingContext ctx) {
        LOGGER.info("leaveGame");
        return null;
    }

    public Object setReady(RoutingContext ctx) {
        LOGGER.info("setReady");
        String id = ctx.request().getParam("gameId");
        String playerName = ctx.request().getParam("playerName");

        return controller.setReady(id, playerName);

    }

    public Object setNotReady(RoutingContext ctx) {
        LOGGER.info("setNotReady");
        return null;
    }

    public Object takeMoney(RoutingContext ctx) {
        LOGGER.info("takeMoney");
        String gameId = ctx.request().getParam("gameId");
        String playerName = ctx.request().getParam("playerName");

        String body = ctx.getBodyAsString();
        Coin[] coins = Json.decodeValue(body, Coin[].class);

        int totalAmount = 0;
        for (Coin coin : coins) {
            totalAmount += coin.getAmount();
        }


        return new JsonObject()
                .put("gameId", gameId)
                .put("playerName", playerName)
                .put("totalAmount", totalAmount);
    }

    public Object buyBuilding(RoutingContext ctx) {
        LOGGER.info("buyBuilding");
        return null;
    }


    public Object redesign(RoutingContext ctx) {
        LOGGER.info("redesign");
        return null;
    }

    public Object build(RoutingContext ctx) {
        LOGGER.info("build");
        return null;
    }

    public Object getGame(RoutingContext ctx) {
        LOGGER.info("getGame");
        return null;
    }

}
