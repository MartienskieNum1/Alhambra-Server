package be.howest.ti.alhambra.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void startGame() {
        Alhambra alhambra = new Alhambra();
        Player player1 = new Player("maarten");
        Player player2 = new Player("jos");
        alhambra.addGame(); //group27-000
        Game myGame = alhambra.findGame("group27-000");

        myGame.addPlayer("group27-000+maarten", player1);
        myGame.addPlayer("group27-000+jos", player2);
        myGame.setReady("group27-000+maarten");
        myGame.setReady("group27-000+jos");

        assertEquals(player1, myGame.getCurrentPlayer());
    }
}