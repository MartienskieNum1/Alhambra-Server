package be.howest.ti.alhambra.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlhambraControllerTest {
    Alhambra alhambra = new Alhambra();
    AlhambraController controller = new AlhambraController();

    @Test
    void getPlayerToken() {
        alhambra.addGame("group27"); //group27-000
        Game myGame = alhambra.findGame("group27-000");

        Player player1 = new Player("maarten");

        assertEquals("147/162/157/165/160/62/67/55/60/60/60/53/155/141/141/162/164/145/156/",
                controller.getPlayerToken(myGame, player1));
        assertThrows(IllegalArgumentException.class, () -> controller.getPlayerToken(null, player1));

    }
}