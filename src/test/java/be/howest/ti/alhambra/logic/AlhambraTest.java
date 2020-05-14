package be.howest.ti.alhambra.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlhambraTest {

    @Test
    void clearGames() {
        Alhambra alhambra = new Alhambra();
        for (int i=0; i<5; i++){
            alhambra.addGame("group27");
        }
        alhambra.clearGames();

        assertEquals(0, alhambra.getGames().size());
    }

    @Test
    void addGame() {
        Alhambra alhambra = new Alhambra();
        for (int i=0; i<5; i++){
            alhambra.addGame("group27");
        }

        assertEquals(5, alhambra.getGames().size());
    }
}