package be.howest.ti.alhambra.webapi;

import be.howest.ti.alhambra.logic.*;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;

import java.util.Arrays;
import java.util.Map;

public class DefaultAlhambraOpenAPI3Bridge implements AlhambraOpenAPI3Bridge {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAlhambraOpenAPI3Bridge.class);
    public static final String GAME_ID = "gameId";
    private final AlhambraController controller;
    private final Alhambra alhambra;

    public DefaultAlhambraOpenAPI3Bridge() {
        this.alhambra = new Alhambra();
        this.controller = new AlhambraController();
    }

    @SuppressWarnings("squid:S2068")
    public boolean verifyAdminToken(String password) {
        String adminPassword = "Bedroefd";
        return adminPassword.equals(password);
    }

    public boolean verifyPlayerToken(String token, String gameId, String playerName) {
        LOGGER.info("verifyPlayerToken");
        if (playerName == null) {
            playerName = alhambra.findGame(gameId).getPlayers().get(token).getUsername();
        }
        String rightToken = gameId + "+" + playerName;
        return token.equals(rightToken);
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
        int round = Integer.parseInt(ctx.request().getParam("round"));

        return controller.getScoringTable(round);
    }

    public Object getGames(RoutingContext ctx) {
        Map<String, Game> listOfGames = alhambra.getGames();
        String prefix = ctx.request().getParam("prefix");
        String details = ctx.request().getParam("details");

        return controller.returnListGameDetails(listOfGames, prefix, details);
    }

    public Object createGame(RoutingContext ctx) {
        String body = ctx.getBodyAsString();
        JsonObject json = new JsonObject(body);
        String prefix = json.getString("prefix");
        Game newGame = alhambra.addGame(prefix);

        return newGame.getGameId();
    }

    public Object clearGames(RoutingContext ctx) {
        alhambra.clearGames();
        return null;
    }

    public Object joinGame(RoutingContext ctx) {
        LOGGER.info("joinGame");
        String gameId = ctx.request().getParam(GAME_ID);
        String body = ctx.getBodyAsString();
        Game game = alhambra.findGame(gameId);
        Player player = Json.decodeValue(body, Player.class);
        return controller.getPlayerToken(game, player);
    }


    public Object leaveGame(RoutingContext ctx) {
        LOGGER.info("leaveGame");
        String gameId = ctx.request().getParam(GAME_ID);
        Game game = alhambra.findGame(gameId);
        String token = ctx.request().getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        game.removePlayer(token);
        return null;
    }

    public Object setReady(RoutingContext ctx) {
        LOGGER.info("setReady");
        String id = ctx.request().getParam(GAME_ID);
        String token = ctx.request().getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        Game game = alhambra.findGame(id);
        game.setPlayerReady(token);
        return null;
    }

    public Object setNotReady(RoutingContext ctx) {
        LOGGER.info("setNotReady");
        String id = ctx.request().getParam(GAME_ID);
        String token = ctx.request().getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        Game game = alhambra.findGame(id);
        game.setPlayerNotReady(token);
        return null;
    }

    public Object takeMoney(RoutingContext ctx) {
        LOGGER.info("takeMoney");
        String gameId = ctx.request().getParam(GAME_ID);
        String token = ctx.request().getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        String body = ctx.getBodyAsString();
        Coin[] coins = Json.decodeValue(body, Coin[].class);
        Game game = alhambra.findGame(gameId);
        game.giveMoney(token, coins);
        return null;
    }

    public Object buyBuilding(RoutingContext ctx) {
        LOGGER.info("buyBuilding");
        String gameId = ctx.request().getParam(GAME_ID);
        Game game = alhambra.findGame(gameId);
        String token = ctx.request().getHeader(HttpHeaders.AUTHORIZATION).substring(7);

        JsonObject body = ctx.getBodyAsJson();
        Coin[] coins = Json.decodeValue(body.getJsonArray("coins").toString(), Coin[].class);
        Currency currency = Currency.valueOf(body.getString("currency").toUpperCase());
        game.buyBuilding(token, Arrays.asList(coins), currency);
        return null;
    }

    public Object redesign(RoutingContext ctx) {
        LOGGER.info("redesign");
        String gameId = ctx.request().getParam(GAME_ID);
        Game game = alhambra.findGame(gameId);
        String token = ctx.request().getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        JsonObject body = ctx.getBodyAsJson();
        JsonObject jsonLocation = body.getJsonObject("location");
        int row = Integer.parseInt(jsonLocation.getString("row"));
        int col = Integer.parseInt(jsonLocation.getString("col"));

        game.redesign(token, row, col);
        return null;
    }

    public Object build(RoutingContext ctx) {
        LOGGER.info("build");
        String gameId = ctx.request().getParam(GAME_ID);
        String token = ctx.request().getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        Game game = alhambra.findGame(gameId);
        JsonObject body = ctx.getBodyAsJson();
        Building building = Json.decodeValue((body.getJsonObject("building").toString()), Building.class);
        JsonObject jsonLocation = body.getJsonObject("location");
        if (jsonLocation != null) {
            int row = Integer.parseInt(jsonLocation.getString("row"));
            int col = Integer.parseInt(jsonLocation.getString("col"));
            game.buildBuilding(token, building, row, col);
        } else {
            game.buildBuilding(token, building, 0, 0);
        }
        return null;
    }

    public Object getGame(RoutingContext ctx) {
        LOGGER.info("getGame");
        String gameId = ctx.request().getParam(GAME_ID);

        return controller.getGameInfo(gameId, alhambra);
    }
}
