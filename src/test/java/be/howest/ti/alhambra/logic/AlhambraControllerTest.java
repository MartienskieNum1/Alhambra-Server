package be.howest.ti.alhambra.logic;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlhambraControllerTest {

    @Test
    void returnListGameDetails() {
        List<String> games = new LinkedList<>();
        games.add("group27-000");

        Alhambra alhambra = new Alhambra();
        AlhambraController controller = new AlhambraController();
        Player player1 = new Player("maarten");
        Player player2 = new Player("jos");
        Player player3 = new Player("Jef");
        alhambra.addGame(); //group27-000
        Game myGame = alhambra.findGame("group27-000");

        myGame.addPlayer("group27-000+maarten", player1);
        myGame.addPlayer("group27-000+jos", player2);
        myGame.addPlayer("group27-000+jef", player3);

        List<JsonObject> detailedGames = new LinkedList<>();
        detailedGames.add(new JsonObject()
                .put("gameId",myGame.getGameId())
                .put("players", myGame.getPlayersList())
                .put("started", myGame.getStarted())
                .put("ended", myGame.getEnded())
                .put("playerCount", myGame.getPlayerCount())
                .put("readyCount", myGame.getReadyCount()));

        assertEquals(games, controller.returnListGameDetails(alhambra.getGames(), "group27", "false"));
        assertEquals(detailedGames, controller.returnListGameDetails(alhambra.getGames(), "group27", "true"));

        myGame.setPlayerReady("group27-000+maarten");
        myGame.setPlayerReady("group27-000+jef");
        myGame.setPlayerReady("group27-000+jos");

        assertEquals(Collections.emptyList(), controller.returnListGameDetails(alhambra.getGames(), "group27", "false"));
        assertEquals(Collections.emptyList(), controller.returnListGameDetails(alhambra.getGames(), "group27", "true"));

    }
}