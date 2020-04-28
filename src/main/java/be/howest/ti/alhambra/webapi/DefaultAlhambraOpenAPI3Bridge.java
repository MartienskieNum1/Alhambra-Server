package be.howest.ti.alhambra.webapi;

import be.howest.ti.alhambra.logic.AlhambraController;
import be.howest.ti.alhambra.logic.Coin;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;

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
        return null;
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
        LOGGER.info("getGames");
        return null;
    }



    public Object createGame(RoutingContext ctx) {
        LOGGER.info("createGame");
        return null;
    }

    public Object clearGames(RoutingContext ctx) {
        LOGGER.info("clearGames");
        return null;
    }

    public Object joinGame(RoutingContext ctx) {
        LOGGER.info("joinGame");
        return null;
    }


    public Object leaveGame(RoutingContext ctx) {
        LOGGER.info("leaveGame");
        return null;
    }

    public Object setReady(RoutingContext ctx) {
        LOGGER.info("setReady");
        return null;
    }

    public Object setNotReady(RoutingContext ctx) {
        LOGGER.info("setNotReady");
        return null;
    }

    public Object takeMoney(RoutingContext ctx) {

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
