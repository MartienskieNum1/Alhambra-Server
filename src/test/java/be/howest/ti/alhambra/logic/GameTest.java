package be.howest.ti.alhambra.logic;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void startGame() {
        Alhambra alhambra = new Alhambra();
        Player player1 = new Player("maarten");
        Player player2 = new Player("jos");
        Player player3 = new Player("Jef");
        alhambra.addGame("group27"); //group27-000
        Game myGame = alhambra.findGame("group27-000");

        myGame.addPlayer("group27-000+maarten", player1);
        myGame.addPlayer("group27-000+jos", player2);
        myGame.addPlayer("group27-000+jef", player3);
        myGame.setPlayerReady("group27-000+maarten");
        myGame.setPlayerReady("group27-000+jef");
        myGame.setPlayerReady("group27-000+jos");

        assertNotNull(myGame.getCurrentPlayer());

        for (Map.Entry<Currency, Building> entry : myGame.getMarket().entrySet()) {
            assertNotNull(entry.getValue());
        }

        for (Coin coin: myGame.getBank()) {
            assertNotNull(coin);
        }
    }
}