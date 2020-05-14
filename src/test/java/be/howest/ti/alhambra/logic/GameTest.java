package be.howest.ti.alhambra.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Alhambra alhambra = new Alhambra();
    Game myGame;
    Player player1 = new Player("maarten");
    Player player2 = new Player("jos");
    Player player3 = new Player("Jef");

    @BeforeEach
    void fillAlhambra() {
        alhambra.addGame("group27"); //group27-000
        myGame = alhambra.findGame("group27-000");

        myGame.addPlayer("group27-000+maarten", player1);
        myGame.addPlayer("group27-000+jos", player2);
        myGame.addPlayer("group27-000+jef", player3);
    }

    @Test
    void startGame() {
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

    @Test
    void nextTurn() {
        myGame.setPlayerReady("group27-000+maarten");
        myGame.setPlayerReady("group27-000+jef");
        myGame.setPlayerReady("group27-000+jos");

        assertEquals(player2, myGame.getCurrentPlayer());
        myGame.nextTurn();
        assertEquals(player1, myGame.getCurrentPlayer());
        myGame.nextTurn();
        assertEquals(player3, myGame.getCurrentPlayer());
        myGame.nextTurn();
        assertEquals(player2, myGame.getCurrentPlayer());
    }
}