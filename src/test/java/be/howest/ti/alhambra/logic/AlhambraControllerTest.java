package be.howest.ti.alhambra.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlhambraControllerTest {
    Alhambra alhambra = new Alhambra();
    AlhambraController controller = new AlhambraController();

    @Test
    void returnPlayerToken() {
        alhambra.addGame("group27"); //group27-000
        Game myGame = alhambra.findGame("group27-000");

        Player player1 = new Player("maarten");

        assertEquals("group27-000+maarten", controller.getPlayerToken(myGame, player1));
        assertThrows(IllegalArgumentException.class, () -> controller.getPlayerToken(null, player1));

    }
}