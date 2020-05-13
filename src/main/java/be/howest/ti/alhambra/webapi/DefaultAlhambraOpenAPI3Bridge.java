package be.howest.ti.alhambra.webapi;

import be.howest.ti.alhambra.logic.*;
import io.vertx.core.http.HttpHeaders;
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
    private final Alhambra alhambra;

    public DefaultAlhambraOpenAPI3Bridge() {
        this.alhambra = new Alhambra();
        this.controller = new AlhambraController();
    }

    public boolean verifyAdminToken(String token) {
        LOGGER.info("verifyPlayerToken");
        return true;
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
        LOGGER.info("getScoringTable");
        return null;
    }

    public Object getGames(RoutingContext ctx) {
        List<Game>listOfGames = alhambra.getGames();
        List<String>listOfGamesInfo = new LinkedList<>();
        List<JsonObject>listOfGamesDetailed = new LinkedList<>();
        String prefix = ctx.request().getParam("prefix");
        String details = ctx.request().getParam("details");

        if (Boolean.parseBoolean(details)) {
            for (Game game : listOfGames) {
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
            for (Game game : listOfGames) {
                if (!game.getStarted() && game.getGroupNr().equals(prefix)) {
                    listOfGamesInfo.add(game.getGameId());
                }
            }
        }
        return listOfGamesInfo;
    }

    public Object createGame(RoutingContext ctx) {
        Game newGame = alhambra.addGame();

        return newGame.getGameId();
    }

    public Object clearGames(RoutingContext ctx) {
        alhambra.games.clear();
        return null;
    }

    public Object joinGame(RoutingContext ctx) {
        LOGGER.info("joinGame");
        String gameId = ctx.request().getParam("gameId");
        String body = ctx.getBodyAsString();
        Game game = alhambra.findGame(gameId);
        return controller.returnPlayerToken(game, body);
    }


    public Object leaveGame(RoutingContext ctx) {
        LOGGER.info("leaveGame");
        String gameId = ctx.request().getParam("gameId");
        Game game = alhambra.findGame(gameId);
        String token = ctx.request().getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        game.removePlayer(token);
        return null;
    }

    public Object setReady(RoutingContext ctx) {
        LOGGER.info("setReady");
        String id = ctx.request().getParam("gameId");
        String token = ctx.request().getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        Game game = alhambra.findGame(id);
        game.setReady(token);
        return null;
    }

    public Object setNotReady(RoutingContext ctx) {
        LOGGER.info("setNotReady");
        String id = ctx.request().getParam("gameId");
        String token = ctx.request().getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        Game game = alhambra.findGame(id);
        game.setNotReady(token);
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
        String gameId = ctx.request().getParam("gameId");
        Game gameToFind = alhambra.findGame(gameId);

        return new JsonObject()
                .put("gameId",gameToFind.getGameId())
                .put("players", gameToFind.getPlayers())
                .put("started", gameToFind.getStarted())
                .put("ended", gameToFind.getEnded())
                .put("playerCount", gameToFind.getPlayerCount())
                .put("readyCount", gameToFind.getReadyCount());
    }
}
